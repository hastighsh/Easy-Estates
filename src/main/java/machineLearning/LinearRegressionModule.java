package machineLearning;

import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;

/**
 * Linear Regression Module that handles machine learning with linear regression as the ML technique
 * @author James Prime
 * @author @JPrime1
 * @version 1.2
 */

public class LinearRegressionModule extends MachineLearningModule{

    public LinearRegressionModule() {
        super();
    }

    /**
     * Generates linear regression model given data
     * @param data The data must be of two columns, INDEX and VALUE
     * @return ML model
     */
    @Override
    protected Classifier formula(Instances data) throws Exception{        
        LinearRegression lr = new LinearRegression();
        lr.buildClassifier(data);
        return lr;
    }
}
