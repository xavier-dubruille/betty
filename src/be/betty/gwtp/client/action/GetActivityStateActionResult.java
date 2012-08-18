package be.betty.gwtp.client.action;

import be.betty.gwtp.shared.dto.ActivityState_dto;

import com.gwtplatform.dispatch.shared.Result;
import java.util.HashMap;

public class GetActivityStateActionResult implements Result {

	private HashMap<String, ActivityState_dto> activitiesState;

	@SuppressWarnings("unused")
	private GetActivityStateActionResult() {
		// For serialization only
	}

	public GetActivityStateActionResult(HashMap<String, ActivityState_dto> activitiesState) {
		this.activitiesState = activitiesState;
	}

	public HashMap<String, ActivityState_dto> getActivitiesState() {
		return activitiesState;
	}
}
