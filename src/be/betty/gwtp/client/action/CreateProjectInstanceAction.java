package be.betty.gwtp.client.action;


import com.gwtplatform.dispatch.shared.ActionImpl;
import be.betty.gwtp.client.action.CreateProjectInstanceActionResult;

public class CreateProjectInstanceAction extends
		ActionImpl<CreateProjectInstanceActionResult> {

	private String projectId;

	public CreateProjectInstanceAction() {
	}
	
	public CreateProjectInstanceAction(String projectCurrentID) {
		this.projectId = projectCurrentID;
	}

	@Override public boolean isSecured() {
		return false;
	}

	public String getProjectId() {
		return this.projectId;
	}
}
