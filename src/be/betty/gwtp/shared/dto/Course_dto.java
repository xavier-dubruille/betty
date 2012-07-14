package be.betty.gwtp.shared.dto;

import java.io.Serializable;

public class Course_dto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private int bddId;
	
	public Course_dto() {} // for serialization purpose
	
	public Course_dto(String name, int bddId) {
		super();
		this.name = name;
		this.bddId = bddId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBddId() {
		return bddId;
	}

	public void setBddId(int bddId) {
		this.bddId = bddId;
	}
	
	
}
