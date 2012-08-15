package be.betty.gwtp.shared.dto;

import java.io.Serializable;
import java.lang.String;

public class Project_dto implements Serializable {

	private static final long serialVersionUID = 2914802826215166433L;
	private String name;
	private int id;

	public Project_dto() {
	}

	public Project_dto(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
}
