package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.Result;
import java.lang.String;

public class LoginActionResult implements Result {

	private int user_id;

	@SuppressWarnings("unused")
	private LoginActionResult() {
		// For serialization only
	}

	public LoginActionResult(int user_id) {
		this.user_id = user_id;
	}

	public int getUser_id() {
		return user_id;
	}


}
