package be.betty.gwtp.shared.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="project")
public class Project {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	@Column(name="name", nullable=false)
	private String name;
	@Column(name="file_course")
	private String course_file;
	@Column(name="file_rooms")
	private String room_file;
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
	
	
	
	

}
