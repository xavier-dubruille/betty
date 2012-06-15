package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.ActionImpl;
import be.betty.gwtp.client.action.DeleteProjectActionResult;

public class DeleteProjectAction extends ActionImpl<DeleteProjectActionResult> {

	private int project_id;
	private String session_id;

	@SuppressWarnings("unused")
	private DeleteProjectAction() {
		// For serialization only
	}

	public DeleteProjectAction(int project_id, String sess) {
		this.project_id = project_id;
		this.session_id = sess;
	}
	
	@Override
	public boolean isSecured() {
		return false;
	}

	public int getProject_id() {
		return project_id;
	}

	public String getSession_id() {
		return session_id;
	}
}
