package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.ActionImpl;

public class GetCards extends ActionImpl<GetCardsResult> {

	private String project_id;
	private String semestre;
	private String sessId;

	@SuppressWarnings("unused")
	private GetCards() {
	} // for serialisation

	public GetCards(String project_id, String sem, String sessId) {
		this.project_id = project_id;
		this.semestre = sem;
		this.sessId = sessId;
	}

	@Override
	public boolean isSecured() {
		return false;
	}

	public String getProjectId() {
		return this.project_id;
	}

	public String getSemestre() {
		return semestre;
	}

	public String getSessId() {
		return sessId;
	}

}
