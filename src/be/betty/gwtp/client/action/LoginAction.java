package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.ActionImpl;

public class LoginAction extends ActionImpl<LoginActionResult> {

	private String login;
	private String pwd;

	@SuppressWarnings("unused")
	private LoginAction() {
		// For serialization only
	}
	@Override
	public boolean isSecured() {
		return false;
	}

	public LoginAction(String login, String pwd) {
		this.login = login;
		this.pwd = pwd;
	}

	public String getPwd() {
		return pwd;
	}
	public String getLogin() {
		return login;
	}
}
