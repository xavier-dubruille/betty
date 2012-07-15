package be.betty.gwtp.shared.dto;

import java.io.Serializable;

public class Teacher_dto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lastName;
	private String firstName;
	private int bddId;
	
	public Teacher_dto() {} // for serialization purpose
	
	public Teacher_dto(String lastName, String firstName, int bddId) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.bddId = bddId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getBddId() {
		return bddId;
	}

	public void setBddId(int bddId) {
		this.bddId = bddId;
	}
	
	
}
