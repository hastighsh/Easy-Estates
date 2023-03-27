package machineLearning;

import java.util.ArrayList;

import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class testML {
    public static void main(String[] args) throws Exception{

        //module
        LinearRegressionModule module = new LinearRegressionModule();

        //load data set
        DataSource source = new DataSource("/Users/James/Downloads/CanadaTestData.csv");
        Instances dataset = source.getDataSet();

        //remove every attribute except house index
        String[] opts = new String[]{"-V"};
        Remove remove = new Remove();
        remove.setOptions(opts);
        remove.setAttributeIndices(String.valueOf(dataset.numAttributes()));
        remove.setInputFormat(dataset);

        //apply filter
        Instances newData = Filter.useFilter(dataset, remove);

        //add new attribute, but empty
        ArrayList<Attribute> atts = new ArrayList<Attribute>(1);
        Attribute index = new Attribute("INDEX");
        atts.add(index);
        Instances indexSet = new Instances("TestInstances",atts,0);
        
        for (int i = 0; i < newData.numInstances(); i++) {
            double[] instanceVal = new double[indexSet.numAttributes()];
            instanceVal[0] = i+1;
            indexSet.add(new DenseInstance(1.0, instanceVal));
        }

        //join instances
        Instances finalData = Instances.mergeInstances(indexSet, newData);
        finalData.setClassIndex(1);

        //build model
        LinearRegression lr = module.linearRegressionForumula(finalData);

        System.out.println("New Dataset:\n");
        System.out.println(newData.size());
        System.out.println(indexSet.size());
        System.out.println(finalData);
        System.out.println(lr);    
    }
}
