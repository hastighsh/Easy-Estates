package machineLearning;

import java.util.ArrayList;
import java.util.EmptyStackException;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.Instance;

public class MachineLearningAdapter {
    MachineLearningModule module;
    /**
     * 
     * @param module
     */
    public MachineLearningAdapter(MachineLearningModule module){
        if (module == null) {
            throw new NullPointerException("\nNo module detected.");
        }
        this.module = module;
    }

    /**
     * Predicts a forecast into the future housing index.
     * Depends on the Machine Learning Model given for forcasting.
     * 
     * @param data - one col value matrix
     * @param months - number of months to forecast
     * @return list of predicted indices
     */
    public ArrayList<Double> predict(ArrayList<Double> data, int months){
        //exceptions
        if (data==null) {
            throw new NullPointerException();
        }
        if (data.isEmpty()) {
            throw new EmptyStackException();
        }
        if (months<=0) {
            throw new IndexOutOfBoundsException("Months not positive");
        }

        Instances newData = convertDataIn(data);
        Instances prediction = module.prediction(newData, months);        
        return convertDataOut(prediction);
    }

    /**
     * Converts data into an instances data type
     * 
     * @param dataIn - expects a 1 col matrix of values
     * @return dataset - instances with INDEX and VALUE as attributes
     */
    private Instances convertDataIn(ArrayList<Double> dataIn){

        //exceptions
        if (dataIn==null) {
            throw new NullPointerException();
        }
        if (dataIn.isEmpty()) {
            throw new EmptyStackException();
        }

        //attributes for instances
        ArrayList<Attribute> atts = new ArrayList<Attribute>(2);
        Attribute index = new Attribute("INDEX");
        Attribute value = new Attribute("VALUE");
        atts.add(index);
        atts.add(value);

        //create instances data type
        Instances dataset = new Instances("DATA", atts, 0);

        //populate instances with data
        for (int i = 0; i < dataIn.size(); i++) {
            double[] instanceVal = new double[dataset.numAttributes()];
            instanceVal[0] = i;
            instanceVal[1] = dataIn.get(i).doubleValue();
            dataset.add(new DenseInstance(1.0, instanceVal));
        }

        return dataset;
    }

    /**
     * Converts the dataset outputed from the given module into a
     * one col matrix of values
     * 
     * @param dataOut
     * @return arraylist
     */
    private ArrayList<Double> convertDataOut(Instances dataOut){

        //exception
        if (dataOut==null) {
            throw new NullPointerException("Empty Instances type for output");
        }

        ArrayList<Double> values = new ArrayList<Double>();
        for (int i = 0; i < dataOut.numInstances(); i++) {
            Instance data = dataOut.instance(i);
            values.add(data.value(data.numAttributes()-1));
        }

        return values;
    }

}
