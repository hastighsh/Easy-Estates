package LogicAndComparsion;

import backend.DataBase;

import java.sql.SQLException;
import java.util.*;

import backend.DataBase;
import backend.MySql;
import org.apache.commons.math3.stat.inference.TTest;

public class Logic implements LogicIF{
	
	//must have attributes inputed by the user:
//	ArrayList<Location> loc;			// No, these should not be fields of the Logic class. They should be passed in a method as parameter
//	Time startTime;
//	Time endTime;
	
	int forecastMonths;
	
	//must have attributes by default:
	DataBase dataBase;
	static int visualizations_running;
	
	//to checkExistence of a timeSeries:
	Set<TimeSeries> existingTS;

	public Logic() {

		existingTS = new HashSet<TimeSeries>();
		visualizations_running = 0;
	}
	
	public void AddTimeSeries(Location place, Time startTime, Time endTime) throws SQLException {	// use case 1
		
		ArrayList<Double> data = new ArrayList<Double>(fetchData(place, startTime, endTime));	// fetch data and get it in the form of an arrayList for that location, and time period
		
		TimeSeries Ts = new TimeSeries(data, startTime, endTime);		
				
		existingTS.add(Ts);
		
	}
	
	public void DeleteTimeSeries(Location place, Time startTime, Time endTime) throws SQLException {	// use case 1
		
		ArrayList<Double> data = new ArrayList<Double>(fetchData(place, startTime, endTime));	// fetch data and get it in the form of an arrayList for that location, and time period
		
		TimeSeries Ts = new TimeSeries(data);		// put that arrayList into the first parameter here
		
		existingTS.remove(Ts);
		
	}
	
	public void loadData(){		// not sure if this function should exist. Probably a UI function where the user simply passes the value 
		
		
	}
	
	public ArrayList<Double> fetchData(Location place, Time start, Time end) throws SQLException {	// fetch data from DB     	for use case 1, and multiple other uses
		TimeSeries timeSeries = new TimeSeries(start,end);
		dataBase = new MySql(place,timeSeries);

		ArrayList<Double> data =dataBase.getIndex();
		
		return data;
	
	}
	
	public DataTable dataFrame(boolean summaryTable){	// called by UI using the toggle button   // for use case 2		// JAYANT IS ON THIS. WILL FINISH SOON
		
		DataTable data = new DataTable();
		
		// call to db to get raw data  fetchData() again
		
		return data;
		
//		if(summaryTable) {	 //return summary of raw data
//			
//		}
//		else {	//return raw data
//			
//		}
		
	}
	
	public DataTable configureVisualization(String VisualizationType){ // for use case 3		@HASIT, @LI will make this (UI people job)
		
		DataTable configuredData = new DataTable(); 
		
		return configuredData;
	}
	
	public DataTable visualize(DataTable configuredData){	// for use case 3		@HASIT, @LI will make this (UI people job)
		
		int threshold = 3;
		
		if(visualizations_running > threshold){
			return null; //	RETURN SOME KIND OF AN EXCEPTION. MODIFY THIS NULL TO SOMETHING MEANINGFUL
		}
		
		DataTable data = new DataTable();	// will hold the data required by UI to visualized
		
		return data;
		
	}
	
	public DataTable configureStats(String StatsType) {  // for use case 4				// NOT REQUIRED FOR DELIVERABLE 1 @JAYANT WILL DO THIS
		
		DataTable configuredData = new DataTable(); 
		
		return configuredData;
	}
	
	
	public StatsComparison compareTimeSeries(TimeSeries ts1, TimeSeries ts2) { // for use case 4			FINISHED!!
		
		boolean exists = checkExistence(ts1, ts2);
		try {
		if (!exists) 
			throw new MissingTimeSeriesException("Seleceted Time Series does not exist in the system. Kindly add it first.");
			
		}
		 catch(MissingTimeSeriesException e) {
			 
			 System.out.println(e.toString());
			 
		 }
		
		
		StatsComparison sc = new StatsComparison(ts1, ts2);
		
		TTest tt1 = new TTest();
		
		ArrayList<Double> timeS1 = ts1.getNHPIndices();
		ArrayList<Double> timeS2 = ts2.getNHPIndices();
		
		//converting arraylist<Double> into array[Double]:
		  
		
		double[] timeSeries1 = new double[timeS1.size()];
		
		for(int i = 0; i <timeS1.size(); i++) {
			
			timeSeries1[i] = timeS1.get(i);
		}
		
		double[] timeSeries2 = new double[timeS2.size()];
		
		for(int i = 0; i <timeS2.size(); i++){
			
			timeSeries2[i] = timeS2.get(i);
		}
		//----------- conversion complete
				
		sc.setPValue(tt1.tTest(timeSeries1, timeSeries2));
		
		double pVal = sc.getPValue();
		
		if(pVal < 0.05)
				sc.setConclusion("Conclusion of the test: We can reject the null hypthesis");
		
		else 
			sc.setConclusion("Concusion of the test: We cannot reject the null hypthesis");
		
		return sc;
	}

	private boolean checkExistence(TimeSeries ts1, TimeSeries ts2) {  //FINISHED!!!		//helper method for StatsComparison. Similar helper can be used for use case 5
		
		boolean ts1Existence = existingTS.contains(ts1);
		boolean ts2Existence = existingTS.contains(ts2);

		return ts1Existence && ts2Existence;		
	}
	
	public DataTable forecast(int forecastMonths, TimeSeries ts) {	// for use case 5 	NOT REQUIRED FOR 1ST DELIVERABLE
		
		return new DataTable();
	}
	
}

