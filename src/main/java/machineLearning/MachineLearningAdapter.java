package machineLearning;

import java.util.List;

import weka.core.Instances;

public class MachineLearningAdapter {
    MachineLearningModule module;

    public MachineLearningAdapter(MachineLearningModule module) {
        this.module = module;
    }

    public List predict(List data, int months){
        Instances newData = convertDataIn(data);
        List prediction = module.prediction(newData, months);
        return prediction;        
    }

    private Instances convertDataIn(List dataIn){
        return null;
    }

    private List convertDataout(Instances dataOut){
        return null;
    }

}
