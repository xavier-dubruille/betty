package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.Result;
import java.lang.String;
import java.util.ArrayList;

public class GetProjectsActionResult implements Result {

	private ArrayList<String> projects;

	@SuppressWarnings("unused")
	private GetProjectsActionResult() {
		// For serialization only
	}

	public GetProjectsActionResult(ArrayList<String> projects) {
		this.projects = projects;
	}

	public ArrayList<String> getProjects() {
		return projects;
	}
}
