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
public class Group_entity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	private String code;

	@OneToMany
	private Collection<Activity_entity> activities = new ArrayList<Activity_entity>();

	@ManyToOne
	private Project_entity project;

	public Group_entity() {
	}

	public Group_entity(String groupCode, Project_entity current_project) {
		this.code = groupCode;
		this.project = current_project;
	}

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

	public Collection<Activity_entity> getActivities() {
		return activities;
	}

	public void setActivities(Collection<Activity_entity> activities) {
		this.activities = activities;
	}

	public Project_entity getProject() {
		return project;
	}

	public void setProject(Project_entity project) {
		this.project = project;
	}

}
