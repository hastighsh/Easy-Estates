package machineLearning;

import java.util.ArrayList;

import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Linear Regression Module that handles machine learning with linear regression as the ML technique
 * @author James Prime
 * @author @JPrime1
 * @version 1.0
 */

public class LinearRegressionModule implements MachineLearningModule{

    public LinearRegressionModule() {
    }

    /**
     * Generates linear regression model given data
     * 
     * @param data The data must be of two columns, INDEX and VALUE
     * @return ML model
     */
    public LinearRegression linearRegressionForumula(Instances data) throws Exception{
        LinearRegression lr = new LinearRegression();
        lr.buildClassifier(data);
        return lr;
    }

    
    @Override    
    public Instances prediction(Instances data, int months){
        LinearRegression lr = null;
        Instances results = null;
        try{
            lr = linearRegressionForumula(data);
            results = predictGivenModel(lr, data, months);
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
        return results;
    }

    /**
     * creates prediction of the data given the specific model instance
     * @param model
     * @param data
     * @param months
     * @return
     * @throws Exception
     */
    public Instances predictGivenModel(LinearRegression model, Instances data, int months) throws Exception{
        int monthsLimit = data.numInstances();
        Instances results = createIndexList(monthsLimit+1, months); //creates a nx1 list
        Attribute values = new Attribute("VALUE");      
        results.insertAttributeAt(values, 1);              //extends a new col for VALUES

        for (int i = 0; i < months; i++) {
            Instance newInst = results.instance(i);
            double prediction = model.classifyInstance(newInst);
            newInst.setValue(values, prediction);            
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
    public Instances createIndexList(int start, int length){
        ArrayList<Attribute> atts = new ArrayList<Attribute>(1);
        Attribute index = new Attribute("INDEX");
        atts.add(index);
        Instances indexSet = new Instances("TestInstances",atts,0);

        for (int i = 0; i < length; i++) {
            double[] instanceVal = new double[indexSet.numAttributes()];
            instanceVal[0] = start+i;
            indexSet.add(new DenseInstance(1.0, instanceVal));
        }

        return indexSet;
    }
    
}
