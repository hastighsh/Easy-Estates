package LogicAndComparsion;

public class Time extends Parameter{

	public Time() {		
	}
	
	public Time(Time t1){
		this();
		this.name = t1.name;
	}
	public Time(String s){
		this();
		this.name = s;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean equals(Object o) {
		
		if(this == o)
			return true;
		
		if(o instanceof Time) {
			
			Time t = new Time();
			t = (Time)o;
			boolean equals = this.getName().equals(t.getName());
			
			return equals;
		}
		return false;
	}

}
