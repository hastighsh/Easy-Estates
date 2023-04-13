package machineLearning;

import java.util.ArrayList;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMOreg;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * SMO Regression Module, handles SVM Linear Regression using the 
 * SMO (Sequential Minimal Optimization) algorithm to train the SVM
 * component of this Regression Technique
 * 
 * @author James Prime
 * @author @JPrime1
 * @version 1.0
 */

public class SMORegressionModule extends MachineLearningModule{

    //variable
    SMOreg model = null;
    Instances data = null;

    public SMORegressionModule() {
    }

    @Override
    public Instances prediction(Instances data, int months) {
        SMOreg smo = null;
        Instances results = null;
        
        data.setClassIndex(data.numAttributes()-1);//need to set class index, to the last attribute

        try{
            smo = smoRegFormula(data);
            results = predictGivenModel(smo, data, months);
        }
        catch(Exception e) {
            System.out.println("some error occured here");
            System.out.println(e.toString());
        }

        modelSetter(smo);
        dataSetter(data);

        return results;
    }

    /**
     * Generates the SMO linear regression model given data
     * 
     * @param data The data must be of two columns, INDEX and VALUE
     * @return ML model
     */
    private SMOreg smoRegFormula(Instances data) throws Exception{        
        SMOreg smo = new SMOreg();
        smo.buildClassifier(data);
        return smo;
    }

    /**
     * Creates prediction of the data given the specific model instance
     * @param model
     * @param data
     * @param months
     * @return
     * @throws Exception
     */
    private Instances predictGivenModel(SMOreg model, Instances data, int months) throws Exception{
        int monthsLimit = data.numInstances();
        Instances results = createIndexList(monthsLimit+1, months); //creates a nx2 list        

        for (int i = 0; i < months; i++) {
            Instance newInst = results.instance(i);
            double prediction = model.classifyInstance(newInst);
            newInst.setValue(results.attribute(results.numAttributes()-1), prediction);            
        }
        return results;
    }

        /**
     * Generates a single attribute Instances list of only indices 
     * From starting index to n length
     * 
     * @param start - the starting index
     * @param length 
     * @return indexSet
     */
    private Instances createIndexList(int start, int length){
        ArrayList<Attribute> atts = new ArrayList<Attribute>(1);
        Attribute index = new Attribute("INDEX");
        Attribute values = new Attribute("VALUE");    
        atts.add(index);
        atts.add(values);
        Instances indexSet = new Instances("DATA",atts,0);

        for (int i = 0; i < length; i++) {
            double[] instanceVal = new double[indexSet.numAttributes()];
            instanceVal[0] = start+i;
            indexSet.add(new DenseInstance(1.0, instanceVal));
        }

        return indexSet;
    }

    
    private void dataSetter(Instances data){
        if (data == null) {
            throw new NullPointerException();
        }
        this.data = data;
    }

    private void modelSetter(SMOreg model){
        if (model==null) {
            throw new NullPointerException();
        }
        this.model = model;
    }

    public String stats(){
        if (this.model == null) {
            throw new NullPointerException();
        }

        Evaluation eval = null;

        try {
            eval = new Evaluation(this.data);
            eval.evaluateModel(this.model, this.data);     
        } catch (Exception e) {
            System.out.print(e);
        }

        return eval.toSummaryString();
    }
    
    
}
