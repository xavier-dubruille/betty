package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.ActionImpl;


public class GetProjectsAction extends ActionImpl<GetProjectsActionResult> {

	private int session_id;

	@SuppressWarnings("unused")
	private GetProjectsAction() {
		// For serialization only
	}

	@Override
	public boolean isSecured() {
		return false;
	}
	public GetProjectsAction(int session_id) {
		this.session_id = session_id;
	}

	public int getSession_id() {
		return session_id;
	}
}
