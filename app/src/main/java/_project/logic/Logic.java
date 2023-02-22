package _project.logic;

import java.util.*;

public class Logic implements LogicIF{
	
	//must have attributes inputed by the user:
	ArrayList<Location> loc;
	Time startTime;
	Time endTime;
	
	int forecastMonths;
	
	//must have attributes by default:
	DataBaseIF db;
	int visualizations_running;
	
	// another one is <<parameter>>, but since we get what we need from the constructor, we ommit having it here.

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public Logic(ArrayList<Location> loc, Time startTime, Time endTime){
		
		this.loc = loc;
		this.startTime = startTime;
		this.endTime = endTime;
		
	}
	
	public void AddTimeSeries(Location place, Time startTime, Time endTime){	// use case 1
		
		
		
	}
	
	public void loadData(){		// not sure if this function should exist. Probably a UI function where the user simply passes the value 
		
		
	}
	
	public ArrayList<Float> fetchData(){	// fetch data from DB     	for use case 1, and multiple other uses
		
		ArrayList<Float> data = new ArrayList<Float>();
		
		return data;
	
	}
	
	public DataTable dataFrame(boolean summaryTable) {	// called by UI using the toggle button   // for use case 2
		
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
	
	public DataTable configureVisualization(String VisualizationType) { // for use case 3
		
		DataTable configuredData = new DataTable(); 
		
		return configuredData;
	}
	
	public DataTable visualize(DataTable configuredData) {	// for use case 3
		
		int threshold = 3;
		
		if(visualizations_running > threshold){
			return null; //	RETURN SOME KIND OF AN EXCEPTION. MODIFY THIS NULL TO SOMETHING MEANINGFUL
		}
		
		DataTable data = new DataTable();	// will hold the data required by UI to visualized
		
		return data;
		
	}
	
	public DataTable configureStats(String StatsType) {  // for use case 4
		
		DataTable configuredData = new DataTable(); 
		
		return configuredData;
	}
	
	
	public StatsComparison compareTimeSeries(TimeSeries ts1, TimeSeries ts2) { // for use case 4
		
		boolean exists = checkExistence(ts1, ts2);
		
		StatsComparison s = new StatsComparison(ts1, ts2);
		return s;
	}

	private boolean checkExistence(TimeSeries ts1, TimeSeries ts2) {		//helper method for StatsComparison. Similar helper can be used for use case 5
		
		return false;		
	}
	
	public DataTable forecast(int forecastMonths, TimeSeries ts) {	// for use case 5
		
		return new DataTable();
	}
	
}
