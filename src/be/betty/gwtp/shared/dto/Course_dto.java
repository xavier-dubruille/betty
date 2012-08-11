package be.betty.gwtp.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course_dto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private int bddId;
	private List<Integer> possibleRoom;
	
	public Course_dto() {} // for serialization purpose
	
	public Course_dto(String name, int bddId) {
		super();
		this.name = name;
		this.bddId = bddId;
		possibleRoom= new ArrayList<Integer>();
	}
	
	public void addPossibleRoom(int i) {
		possibleRoom.add(i);
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

	public List<Integer> getPossibleRoom() {
		return possibleRoom;
	}

	public void setPossibleRoom(List<Integer> possibleRoom) {
		this.possibleRoom = possibleRoom;
	}
	
	
}
