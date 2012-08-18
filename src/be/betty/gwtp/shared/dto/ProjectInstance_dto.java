package be.betty.gwtp.shared.dto;

import java.io.Serializable;

public class ProjectInstance_dto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int bddId;
	private int localNum;
	private String description;
	
	
	public ProjectInstance_dto() {} // for serialisation
	
	public ProjectInstance_dto(int bddId, int localNum, String description) {
		super();
		this.bddId = bddId;
		this.localNum = localNum;
		this.description = description;
	}
	
	public int getBddId() {
		return bddId;
	}
	public void setBddId(int bddId) {
		this.bddId = bddId;
	}
	public int getLocalNum() {
		return localNum;
	}
	public void setLocalNum(int localNum) {
		this.localNum = localNum;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
