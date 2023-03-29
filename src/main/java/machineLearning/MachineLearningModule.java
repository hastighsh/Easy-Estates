package machineLearning;

import weka.core.Instances;

public interface MachineLearningModule {
    /**
     * Creates a prediction of n months to the future
     * @param data Instances
     * @param months int
     * @return results Instances
     */
    public Instances prediction(Instances data, int months);
}
