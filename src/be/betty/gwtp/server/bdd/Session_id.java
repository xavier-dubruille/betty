package be.betty.gwtp.server.bdd;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

@Entity
public class Session_id {
	@Id
	@Column(name = "sess_id")
	private String sess_id;

	@ManyToOne
	private User user_id;
	
	@Column
	@Type(type="date")
	private Date creationDate;

	public String getSess_id() {
		return sess_id;
	}

	public void setSess_id(String session_id) {
		this.sess_id = session_id;
	}

	public User getUser_id() {
		return user_id;
	}

	public void setUser_id(User user_id) {
		this.user_id = user_id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
