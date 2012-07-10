package be.betty.gwtp.server.bdd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Activity {

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

	public Activity() {
	}

	public Activity(Teacher t, Group_entity g, Course c,
			Project_entity current_project) {
		this.teacher = t;
		this.group = g;
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

}
