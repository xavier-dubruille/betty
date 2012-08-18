package be.betty.gwtp.server.bdd;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	private String name;
	private String code;
	private String mode; // i.e class or group
	private String type; // i.e. does it require a special room
	// section?

	@ManyToOne
	private Project_entity project;

	@OneToMany
	private Collection<Activity_entity> activities = new ArrayList<Activity_entity>();
	
	@OneToMany
	private Collection<Room> possibleRooms = new ArrayList<Room>();

	private int nbPeriod_s2;
	private int nbPeriod_s1;


	private Course() { //serialisation ..
	} 

	public Course(String coursesId, String courseName, String mod, String type, int periods_s1,
			int periods_s2, Project_entity current_project) {
		this.project = current_project;
		this.code = coursesId;
		this.name = courseName;
		this.mode = mod;
		this.type = type;
		this.nbPeriod_s1 = periods_s1;
		this.nbPeriod_s2 = periods_s2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNbPeriodS1() {
		return nbPeriod_s1;
	}

	public void setNbPeriodS1(int nbPeriod) {
		this.nbPeriod_s1 = nbPeriod;
	}

	public int getNbPeriodS2() {
		return nbPeriod_s2;
	}

	public void setNbPeriodS2(int nbPeriod) {
		this.nbPeriod_s2 = nbPeriod;
	}

	
	public Project_entity getProject() {
		return project;
	}

	public void setProject(Project_entity project) {
		this.project = project;
	}

	public Collection<Activity_entity> getActivities() {
		return activities;
	}

	public void setActivities(Collection<Activity_entity> activities) {
		this.activities = activities;
	}

	public int getNbPeriodSX(String semestre) {
		if (semestre.equals("1"))
			return getNbPeriodS1();
		else if(semestre.equals("2"))
			return getNbPeriodS2();
		else return 0;
	}


	public Collection<Room> getPossibleRooms() {
		return possibleRooms;
	}

	public void setPossibleRooms(Collection<Room> possibleRooms) {
		this.possibleRooms = possibleRooms;
	}


}
