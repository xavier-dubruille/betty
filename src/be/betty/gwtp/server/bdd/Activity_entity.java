package be.betty.gwtp.server.bdd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import be.betty.gwtp.server.solver.Resource;

@Entity
@Table(name = "Activity")
public class Activity_entity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	private Project_entity project;

	@ManyToOne
	private Teacher teacher;

	@ManyToOne
	private Course course;

	@ManyToOne
	private Group_entity group;
	
	@ManyToMany
	private Collection<Group_entity> groupSet = new ArrayList<Group_entity>();

	private char classe;

	public Activity_entity() {
	}

	public Activity_entity(Teacher t, Group_entity g, Course c,
			Project_entity current_project) {
		this.teacher = t;
		classe =  g.getSubGroup().charAt(0);
		groupSet.add(g);
		this.course = c;
		this.project = current_project;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Project_entity getProject() {
		return project;
	}

	public void setProject(Project_entity project) {
		this.project = project;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Group_entity getGroup() {
		return group;
	}

	public void setGroup(Group_entity group) {
		this.group = group;
	}

	public int getNumberOfStudents() {
		// TODO faut grossierement calculer le nombre d'etudiants,
		// faute de mieux, on multiplira le nombre de groupe pas la
		// "taille par deffaut" d'un groupe..
		return 42;
	}

	public Collection<Group_entity> getGroupSet() {
		return this.groupSet;
	}

	public String toString() {
		return "Card ";
	}

	public char getClasse() {
		return classe;
	}


}
