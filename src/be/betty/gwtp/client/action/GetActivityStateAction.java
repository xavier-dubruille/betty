package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.ActionImpl;

import be.betty.gwtp.client.action.GetActivityStateActionResult;

public class GetActivityStateAction extends
		ActionImpl<GetActivityStateActionResult> {

	private int instanceBddId;
	private String sessId;

	@SuppressWarnings("unused")
	private GetActivityStateAction() {
		// For serialization only
	}
	
	@Override public boolean isSecured() {
		return false;
	}

	public GetActivityStateAction(int instanceBddId, String sessId) {
		this.instanceBddId = instanceBddId;
		this.sessId = sessId;
	}

	public int getInstanceBddId() {
		return instanceBddId;
	}

	public String getSessId() {
		return sessId;
	}
}
