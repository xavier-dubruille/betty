package be.betty.gwtp.server.bdd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Session_id {
	@Id
	@Column(name = "sess_id")
	private String sess_id;

	@ManyToOne
	private User user_id;

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

}
