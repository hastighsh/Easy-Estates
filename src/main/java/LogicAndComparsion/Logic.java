package LogicAndComparsion;

import backend.DataBase;

import java.sql.SQLException;
import java.util.*;

import backend.DataBase;
import backend.MySql;
import machineLearning.LinearRegressionModule;
import machineLearning.MachineLearningAdapter;
import machineLearning.MachineLearningModule;
import machineLearning.SMORegressionModule;
import org.apache.commons.math3.stat.inference.TTest;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

/*
 * @author Jayant Varma:
 * */
public class Logic implements LogicIF{
		
	
	//must have attributes by default:
	DataBase dataBase;
	MachineLearningModule module;
	
	public Logic() {

	}
	
	public ArrayList<Double> fetchData(Location place, Time start, Time end) throws SQLException {	// fetch data from DB     	for use case 1, and multiple other uses
		TimeSeries timeSeries = new TimeSeries(start,end);
		dataBase = new MySql(place,timeSeries);

		ArrayList<Double> data =dataBase.getIndex();
		
		return data;
	}

	public SummaryStatistics getSummary(ArrayList<Double> rawData) {
		SummaryStatistics summaryData = new SummaryStatistics();
		
		for(int i = 0; i < rawData.size(); i++) {
			
			summaryData.addValue(rawData.get(i));
		}
		
		return summaryData;
	}
		
	public StatsComparison compareTimeSeries(TimeSeries ts1, TimeSeries ts2) { // for use case 4			complete
					
		StatsComparison sc = new StatsComparison(ts1, ts2);
								
		sc.setPValue((new TTest()).tTest(convertListToArray(ts1.getNHPIndices()), convertListToArray(ts2.getNHPIndices())));
		
		if(sc.getPValue() < 0.05)
				sc.setConclusion("Conclusion of the test: We can reject the null hypthesis");
		
		else 
			sc.setConclusion("Concusion of the test: We cannot reject the null hypthesis");
		
		return sc;
	}
	
	private double[] convertListToArray (ArrayList<Double> ts){
		
		double[] timeSeries = new double[ts.size()];
		
		for(int i = 0; i <ts.size(); i++)			
			timeSeries[i] = ts.get(i);
				
		return timeSeries;
			
		}
	
	public ArrayList<Double> forecast(ArrayList<Double> data, int months,String model) {	// for use case 5

		
		if(model.equals("Linear Regression Module"))		{
			module = new LinearRegressionModule();
		}
		else{
			module = new SMORegressionModule();
		}
		
		MachineLearningAdapter adapter = new MachineLearningAdapter(module);
		
		return adapter.predict(data,months);
	}
	public MachineLearningModule getModule(){
		return module;
	}
	
}