package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.Result;
import java.lang.String;

public class LoginActionResult implements Result {

	private int session_id;

	@SuppressWarnings("unused")
	private LoginActionResult() {
		// For serialization only
	}

	public LoginActionResult(int session_id) {
		this.session_id = session_id;
	}

	public int getSession_id() {
		return session_id;
	}


}
