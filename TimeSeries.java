package _project;

import java.util.*;

public class TimeSeries {
	
	private ArrayList<Double> NHPIndices;
	
	private Time startTime;
	private Time endTime;
	
	public TimeSeries(ArrayList<Double> data) {
		
		NHPIndices = data;	
	}
	
	public TimeSeries(ArrayList<Double> data,Time startTime, Time endTime){
		
		NHPIndices = data;
		this.startTime = new Time(startTime);
		this.endTime = new Time(endTime);
	
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

}
