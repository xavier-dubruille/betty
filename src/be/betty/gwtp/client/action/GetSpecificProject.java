package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.ActionImpl;
import be.betty.gwtp.client.action.GetSpecificProjectResult;
import java.lang.String;

public class GetSpecificProject extends ActionImpl<GetSpecificProjectResult> {

	private String project_id;

	@SuppressWarnings("unused")
	private GetSpecificProject() {
		// For serialization only
	}
	
	@Override
	public boolean isSecured() {
		return false;
	}

	public GetSpecificProject(String project_id) {
		this.project_id = project_id;
	}

	public String getProject_id() {
		return project_id;
	}
}
