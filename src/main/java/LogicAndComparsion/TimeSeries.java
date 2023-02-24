package LogicAndComparsion;

import java.util.*;

public class TimeSeries {
	
	private ArrayList<Double> NHPIndices;
	
	private Time startTime;
	private Time endTime;
	private String tempStart;
	private String tempEnd;
	
	public TimeSeries(ArrayList<Double> data) {
		
		NHPIndices = data;	
	}
	
	public TimeSeries(ArrayList<Double> data,Time startTime, Time endTime){
		
		NHPIndices = data;
		this.startTime = new Time(startTime);
		this.endTime = new Time(endTime);
	
	}
	public TimeSeries(Time startTime,Time endTime){
		tempStart = startTime.getName();
		tempEnd = endTime.getName();
	}

	public ArrayList<Double> getNHPIndices() {
		return NHPIndices;
	}

	public Time getStartTime() {
		return startTime;
	}

	public Time getEndTime() {
		return endTime;
	}
	
	//@overwrite
	public boolean equals(Object t1) {
		
		if(this == t1)
			return true;
		
		if(t1 instanceof TimeSeries) {
			TimeSeries tss = (TimeSeries)t1;
			
			boolean equals = this.getStartTime().equals((tss).getStartTime()) && this.getEndTime().equals((tss).getEndTime());
			
			return equals;
		}
		
		return false;
	}
	public boolean increasement(){
		int year = 0;
		int month = 0;
		year = Integer.parseInt(tempStart.substring(0,4));
		month = Integer.parseInt((tempStart.substring(tempStart.length()-2)));
		month++;

		if(month<10){
			tempStart = year + "-0" +month;
		}
		else if(month>12){
			month = 1;
			year++;
			tempStart = year + "-0" + month;
		}
		else{
			tempStart = year + "-" +month;
		}
		return tempStart.equals(tempEnd);

	}
	public String getTempStart(){
		return tempStart;
	}
}
