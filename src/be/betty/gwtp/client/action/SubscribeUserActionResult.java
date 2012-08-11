package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.Result;

public class SubscribeUserActionResult implements Result {

	private boolean exist;

	@SuppressWarnings("unused")
	private SubscribeUserActionResult() {
		// For serialization only
	}

	public SubscribeUserActionResult(boolean exist) {
		this.exist = exist;
	}

	public boolean getUserExist() {
		return exist;
	}
}
