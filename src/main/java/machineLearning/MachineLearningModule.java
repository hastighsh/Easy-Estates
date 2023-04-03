package machineLearning;

import weka.core.Instances;

/**
 * Machine Learning Module interface for the ML technique implementations
 * @author James Prime
 * @author @JPrime1
 * @version 1.0
 */

public interface MachineLearningModule {
    /**
     * Creates a prediction of n months to the future
     * @param data Instances
     * @param months int
     * @return results Instances
     */
    public Instances prediction(Instances data, int months);

    /**
     * Create stats of last model passed through module.
     * 
     * @return summary string of stats
     */
    public String stats();
}
