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
@Table(name = "site_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	private String name;
	private String pwd;
	private String email;

	@ManyToMany
	private Collection<Project_entity> projects = new ArrayList<Project_entity>();

	@OneToMany
	private Collection<Session_id> sess_id = new ArrayList<Session_id>();

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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Project_entity> getProjects() {
		return projects;
	}

	public void setProjects(Collection<Project_entity> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "name = " + this.name;
	}

	public Collection<Session_id> getSess_id() {
		return sess_id;
	}

	public void setSess_id(Collection<Session_id> sess_id) {
		this.sess_id = sess_id;
	}

}
