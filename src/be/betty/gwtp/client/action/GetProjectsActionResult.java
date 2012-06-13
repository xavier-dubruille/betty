package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.Result;
import java.lang.String;

public class GetProjectsActionResult implements Result {

	private String projects;

	@SuppressWarnings("unused")
	private GetProjectsActionResult() {
		// For serialization only
	}

	public GetProjectsActionResult(String projects) {
		this.projects = projects;
	}

	public String getProjects() {
		return projects;
	}
}
