package be.betty.gwtp.client.action;

import be.betty.gwtp.client.model.Project;

import com.gwtplatform.dispatch.shared.Result;
import java.lang.String;
import java.util.ArrayList;

public class GetProjectsActionResult implements Result {

	private ArrayList<Project> projects;

	@SuppressWarnings("unused")
	private GetProjectsActionResult() {
		// For serialization only
	}

	public GetProjectsActionResult(ArrayList<Project> projects) {
		this.projects = projects;
	}

	public ArrayList<Project> getProjects() {
		return projects;
	}
}
