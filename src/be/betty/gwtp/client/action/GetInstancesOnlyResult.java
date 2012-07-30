package be.betty.gwtp.client.action;

import be.betty.gwtp.shared.dto.ProjectInstance_dto;

import com.gwtplatform.dispatch.shared.Result;
import java.util.ArrayList;

public class GetInstancesOnlyResult implements Result {

	private ArrayList<ProjectInstance_dto> projectInstances;

	public GetInstancesOnlyResult() {
		projectInstances = new ArrayList<ProjectInstance_dto>();
	}

	public ArrayList<ProjectInstance_dto> getProjectInstances() {
		return projectInstances;
	}
	
	public void addProjectInstance(ProjectInstance_dto pi) {
		projectInstances.add(pi);
	}
}
