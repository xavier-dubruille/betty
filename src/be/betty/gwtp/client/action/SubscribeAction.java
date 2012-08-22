package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.ActionImpl;
import be.betty.gwtp.client.action.SubscribeActionResult;

/**
 * 
 * Action used when a user is subscribing
 *
 */
public class SubscribeAction extends ActionImpl<SubscribeActionResult> {

	private String login;
	private String pwd;
	private String email;

	@SuppressWarnings("unused")
	private SubscribeAction() {
		// For serialization only
	}
	@Override
	public boolean isSecured() {
		return false;
	}

	public SubscribeAction(String login, String pwd, String email) {
		this.login = login;
		this.pwd = pwd;
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}
	public String getLogin() {
		return login;
	}
	
	public String getEmail(){
		return email;
	}
	
}
