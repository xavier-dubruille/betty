package be.betty.gwtp.server.bdd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ProjectInstance {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id; //faut voire si on peut mettre une combinaison de parentProject+num comme cle primaire
	private String description;
	@ManyToOne private Project_entity parentProject;
	private int num;
	public ProjectInstance() {}
	public ProjectInstance(String description, int num) {
		this.description = description;
		this.num = num;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Project_entity getParentProject() {
		return parentProject;
	}
	public void setParentProject(Project_entity parentProject) {
		this.parentProject = parentProject;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Integer getId() {
		return id;
	}
	
	
}
