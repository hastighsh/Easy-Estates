package machineLearning;

import weka.classifiers.Classifier;
import weka.classifiers.functions.SMOreg;
import weka.core.Instances;

/**
 * SMO Regression Module, handles SVM Linear Regression using the 
 * SMO (Sequential Minimal Optimization) algorithm to train the SVM
 * component of this Regression Technique
 * 
 * @author James Prime
 * @author @JPrime1
 * @version 1.1
 */

public class SMORegressionModule extends MachineLearningModule{

    public SMORegressionModule() {
        super();
    }

    /**
     * Generates the SMO linear regression model given data
     * @param data The data must be of two columns, INDEX and VALUE
     * @return ML model
     */
    @Override
    protected Classifier formula(Instances data) throws Exception{        
        SMOreg smo = new SMOreg();
        smo.buildClassifier(data);
        return smo;
    }
    
}
