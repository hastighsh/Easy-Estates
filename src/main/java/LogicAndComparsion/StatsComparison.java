package LogicAndComparsion;

//@author Jayant
public class StatsComparison {
	
	private TimeSeries Ts1;
	private TimeSeries Ts2;
	
	private double p_value;
	private String conclusion;
	
	public StatsComparison(TimeSeries ts1, TimeSeries ts2){
		this.Ts1 = ts1;
		this.Ts2 = ts2;
		
		p_value = 0;
		conclusion = "conclusion undecided";

	}
	
	public String getConclusion(){
		
		return this.conclusion;
	}
	
	public double getPValue(){
		
		return this.p_value;
	}
	
	public void setPValue(double p) {
		this.p_value = p;
	}
	
	public void setConclusion(String s) {
		this.conclusion = s;
	}

}
	
	

