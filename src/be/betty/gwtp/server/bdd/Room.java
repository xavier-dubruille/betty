package be.betty.gwtp.server.bdd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	private String code;
	private String type; // i.e. info, auditoire, labo, etc
	private String Contenance;
	private int projo;
	private int board;
	private int slide;
	private int recorder;
	private int floor;

	@ManyToOne
	private Project_entity project;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Project_entity getProject() {
		return project;
	}

	public void setProject(Project_entity project) {
		this.project = project;
	}

	public int getContenance() {
		return Integer.parseInt(Contenance);
	}

	public void setContenance(String contenance) {
		Contenance = contenance;
	}

	public int getProjo() {
		return projo;
	}

	public void setProjo(int projo) {
		this.projo = projo;
	}

	public int getBoard() {
		return board;
	}

	public void setBoard(int board) {
		this.board = board;
	}

	public int getSlide() {
		return slide;
	}

	public void setSlide(int slide) {
		this.slide = slide;
	}

	public int getRecorder() {
		return recorder;
	}

	public void setRecorder(int recorder) {
		this.recorder = recorder;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	
	

}
