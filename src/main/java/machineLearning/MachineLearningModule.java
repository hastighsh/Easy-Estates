package machineLearning;

import weka.core.Instances;

public interface MachineLearningModule {
    /**
     * Creates a prediction of n months to the future
     * @param data
     * @param months
     * @return
     */
    public Instances prediction(Instances data, int months);
}
