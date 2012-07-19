package be.betty.gwtp.shared.dto;

import java.io.Serializable;

public class Card_dto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	private int teacher; // Actualy it's the id of teacher in bdd
	private int course;  
	private int group;   
	private int period_q1;
	private int period_q2;
	private int slot = 0; //  The value "0" is for the case where is not placed 
    private int room = 0;
	private int bddId;
	
    public int getTeacher() {
		return teacher;
	}
	public void setTeacher(int teacher) {
		this.teacher = teacher;
	}
	public int getCourse() {
		return course;
	}
	public void setCourse(int course) {
		this.course = course;
	}
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	public int getPeriod_q1() {
		return period_q1;
	}
	public void setPeriod_q1(int period_q1) {
		this.period_q1 = period_q1;
	}
	public int getPeriod_q2() {
		return period_q2;
	}
	public void setPeriod_q2(int period_q2) {
		this.period_q2 = period_q2;
	}
	public int getSlot() {
		return slot;
	}
	public int getRoom() {
		return room;
	}
	public void setSlot(int slot) {
		this.slot = slot;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public int getBddId() {
		return bddId;
	}
	public void setBddId(int bddId) {
		this.bddId = bddId;
	}
	
	
	
}
