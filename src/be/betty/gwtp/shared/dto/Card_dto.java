package be.betty.gwtp.shared.dto;

import java.io.Serializable;

public class Card_dto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String teacher; //faudrait p-e créer un obj teacher_dto avec prenom, nom, mail, ...
	private String course;  //idem
	private String group;   //idem
	private int period_q1;
	private int period_q2;
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
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
	
	
	
}
