package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.ActionImpl;
import be.betty.gwtp.client.action.GetInstancesOnlyResult;

public class GetInstancesOnly extends ActionImpl<GetInstancesOnlyResult> {

	private int projectId;

	@SuppressWarnings("unused")
	private GetInstancesOnly() {
		// For serialization only
	}

	public GetInstancesOnly(int projectId) {
		this.projectId = projectId;
	}
	
	@Override public boolean isSecured() {
		return false;
	}

	public int getProjectId() {
		return projectId;
	}
}
