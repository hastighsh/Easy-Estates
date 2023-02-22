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
	// another one is <<parameter>>, but since we get what we need from the constructor, we ommit having it here.

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public Logic(ArrayList<Location> loc, Time startTime, Time endTime){
		
		this.loc = loc;
		this.startTime = startTime;
		this.endTime = endTime;
		
	}
	
	public void AddTimeSeries(ArrayList<Location> loc, Time startTime, Time endTime){
		
		
		
	}
	
	public void loadData(){
		
		
	}
	
	public DataTable dataFrame(boolean summaryTable) {	// called by UI using the toggle button
		
		DataTable data = new DataTable();
		
		// call to db to get raw data
		
		return data;
		
//		if(summaryTable) {	 //return summary of raw data
//			
//		}
//		else {	//return raw data
//			
//		}
		
	}
	
	public DataTable configureVisualization(String VisualizationType) {
		
		DataTable configuredData = new DataTable(); 
		
		return configuredData;
	}
	
	public DataTable visualize(DataTable configuredData) {
		
		DataTable data = new DataTable();	// will hold the data required by UI to visualized
		
		return data;
		
	}
	
	public DataTable configureStats(String StatsType) {
		
		DataTable configuredData = new DataTable(); 
		
		return configuredData;
	}
	
	
	public StatsComparison CompareTimeSeries(TimeSeries ts1, TimeSeries ts2) { 
		
		boolean exists = checkExistence(ts1, ts2);
		
		StatsComparison s = new StatsComparison(ts1, ts2);
		return s;
	}

	private boolean checkExistence(TimeSeries ts1, TimeSeries ts2) {		//helper method for StatsComparison
		
		return false;		
	}
	
	public DataTable forecast(int forecastMonths, TimeSeries ts, Location l) {
		
		return new DataTable();
	}
	
}
