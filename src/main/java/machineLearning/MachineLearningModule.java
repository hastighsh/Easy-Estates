package machineLearning;

import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Machine Learning Module interface for the ML technique implementations
 * @author James Prime
 * @author @JPrime1
 * @version 1.1
 */

public abstract class MachineLearningModule {
    /**
     * Private properties of the LAST machine learning model created by this module
     * Used for creating the evaluation statistics summary
     * 
     * model - for the ML technique
     * data - for storing 
     */
    protected Classifier model;
    protected Instances data;

    public MachineLearningModule() {
        setModel(null);
        setData(null);
    }

    /**
     * Creates a prediction of n months to the future
     * @param data Instances
     * @param months int
     * @return results Instances
     */
    public Instances prediction(Instances data, int months) {
        Instances results = null;
        
        data.setClassIndex(data.numAttributes()-1);//need to set class index, to the last attribute

        try{
            setModel(formula(data));
            setData(data);
            results = predictGivenModel(getModel(), data, months);
        }
        catch(Exception e) {
            System.out.println("some error occured here");
            System.out.println(e.toString());
        }

        return results;
    }
    
    /**
     * Create Machine Learning Model given data
     * @param data - training data
     * @return model
     */
    protected abstract Classifier formula(Instances data) throws Exception;

    /**
     * Generates a single attribute Instances list of only indices 
     * From starting index to n length
     * 
     * @param start - the starting index
     * @param length 
     * @return indexSet
     */
    protected Instances createIndexList(int start, int length){
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

    /**
     * Creates prediction of the data given the specific model instance
     * @param model your ML model
     * @param data your data set
     * @param months months into the future
     * @return results of your prediction
     * @throws Exception
     */
    protected Instances predictGivenModel(Classifier model, Instances data, int months) throws Exception{
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
     * Creates the statistics summary of the previous model created. 
     * Such statistics come from WEKA's Evaluation model.
     * @return Evaluation Summary as a String
     * @throws java.lang.NullPointerException - if no model was created
     */
    public String stats(){
        if (getModel() == null) {
            throw new NullPointerException();
        }
        else if (getData() == null) {
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

    /**
     * Gets the last model stored in module
     * @return model
     */
    public Classifier getModel() {
        return model;
    }

    /**
     * Sets model into the module
     * @param model
     */
    public void setModel(Classifier model) {
        this.model = model;
    }

    /**
     * Get data last stored in module
     * @return data
     */
    public Instances getData() {
        return data;
    }

    /**
     * Sets data into the module
     * @param data
     */
    public void setData(Instances data) {
        this.data = data;
    }
}