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
import javax.persistence.Table;

@Entity
@Table(name = "teacher")
public class Teacher {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	private String code;
	@Column(name = "name", nullable = false)
	private String name;

	@ManyToOne
	private Project_entity project;
	private String firstName;
	private boolean sem1;
	private boolean sem2;

	private Teacher() { // for serialisation
	}

	public Teacher(String teacherId, String teacherFirstname,
			String teacherLastname, Project_entity current_project) {
		this.firstName = teacherFirstname;
		this.code = teacherId;
		this.name = teacherLastname;
		this.project = current_project;
		sem1 = false;
		sem2 = false;
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

	public Project_entity getProject() {
		return project;
	}

	public void setProject(Project_entity project) {
		this.project = project;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public boolean giveCoursesThisSem(String semestre) {
		if (semestre.equals("1") && sem1)
			return true;
		if (semestre.equals("2") && sem2)
			return true;
		
		return false;
	}

	public boolean isSem1() {
		return sem1;
	}

	public void setSem1(boolean sem1) {
		this.sem1 = sem1;
	}

	public boolean isSem2() {
		return sem2;
	}

	public void setSem2(boolean sem2) {
		this.sem2 = sem2;
	}

}
