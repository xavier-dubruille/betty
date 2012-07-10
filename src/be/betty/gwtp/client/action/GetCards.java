package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.ActionImpl;

public class GetCards extends ActionImpl<GetCardsResult> {

	private String project_id;

	@SuppressWarnings("unused")
	private GetCards() {
	} // for serialisation

	public GetCards(String project_id) {
		this.project_id = project_id;
	}

	@Override
	public boolean isSecured() {
		return false;
	}

	public String getProjectId() {
		return this.project_id;
	}
}
