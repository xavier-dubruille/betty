package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.Result;
import java.lang.String;

public class LoginActionResult implements Result {

	private String session_id;

	@SuppressWarnings("unused")
	private LoginActionResult() {
		// For serialization only
	}

	public LoginActionResult(String session_id2) {
		this.session_id = session_id2;
	}

	public String getSession_id() {
		return session_id;
	}


}
