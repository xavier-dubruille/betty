package be.betty.gwtp.shared.dto;

import java.io.Serializable;

public class ActivityState_dto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int day;
	private int period;
	
	public ActivityState_dto() {} //for serialisation
	
	public ActivityState_dto(int day, int period) {
		super();
		this.day = day;
		this.period = period;
	}
	
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}

	
	
	

}
