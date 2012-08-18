package be.betty.gwtp.server.bdd;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class Project_entity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "file_course")
	private String course_file;
	@Column(name = "file_rooms")
	private String room_file;

	@ManyToMany
	private Collection<User> users = new ArrayList<User>();

	@OneToMany
	private Collection<Activity_entity> activities = new ArrayList<Activity_entity>();

	@OneToMany
	private Collection<Teacher> teachers = new ArrayList<Teacher>();

	@OneToMany
	private Collection<Group_entity> groups = new ArrayList<Group_entity>();

	@OneToMany
	private Collection<Course> courses = new ArrayList<Course>();
	
	@OneToMany
	private Collection<ProjectInstance> projectInstances = new ArrayList<ProjectInstance>();

	
	@ManyToMany
	private Collection<Room> rooms = new ArrayList<Room>();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourse_file() {
		return course_file;
	}

	public void setCourse_file(String course_file) {
		this.course_file = course_file;
	}

	public String getRoom_file() {
		return room_file;
	}

	public void setRoom_file(String room_file) {
		this.room_file = room_file;
	}

	public Collection<User> getUsers() {
		return users;
	}


	public Collection<Activity_entity> getActivities() {
		return activities;
	}


	public Collection<Teacher> getTeachers() {
		return teachers;
	}


	public Collection<Group_entity> getGroups() {
		return groups;
	}



	public Collection<Course> getCourses() {
		return courses;
	}


	public Collection<Room> getRooms() {
		return rooms;
	}


	public Collection<ProjectInstance> getProjectInstances() {
		return projectInstances;
	}


}
