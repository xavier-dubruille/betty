package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.ActionImpl;
import be.betty.gwtp.client.action.SubscribeUserActionResult;

public class SubscribeUserAction extends ActionImpl<SubscribeUserActionResult> {

	
	private String user;

	@SuppressWarnings("unused")
	private SubscribeUserAction() {
		// For serialization only
	}
	@Override
	public boolean isSecured() {
		return false;
	}

	public SubscribeUserAction(String user) {
		this.user = user;

	}

	public String getUsername() {
		return user;
	}
}
