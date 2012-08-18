package be.betty.gwtp.client.action;

import java.util.ArrayList;

import be.betty.gwtp.shared.dto.Project_dto;

import com.gwtplatform.dispatch.shared.Result;

public class GetProjectsActionResult implements Result {

	private ArrayList<Project_dto> projects;

	@SuppressWarnings("unused")
	private GetProjectsActionResult() {
		// For serialization only
	}

	public GetProjectsActionResult(ArrayList<Project_dto> projects) {
		this.projects = projects;
	}

	public ArrayList<Project_dto> getProjects() {
		return projects;
	}
}
