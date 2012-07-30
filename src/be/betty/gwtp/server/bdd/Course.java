package be.betty.gwtp.server.bdd;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private int nbPeriod; // per week
	// section?

	@ManyToOne
	private Project_entity project;

	@OneToMany
	private Collection<Activity_entity> activities = new ArrayList<Activity_entity>();

	public Course() {
	}

	public Course(String coursesId, String courseName, String mod, int periods,
			Project_entity current_project) {
		this.project = current_project;
		this.code = coursesId;
		this.name = courseName;
		this.mode = mod;
		this.nbPeriod = periods;
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

	public int getNbPeriod() {
		return nbPeriod;
	}

	public void setNbPeriod(int nbPeriod) {
		this.nbPeriod = nbPeriod;
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

}
