package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.ActionImpl;
import be.betty.gwtp.client.action.DeleteCardActionResult;

public class DeleteCardAction extends ActionImpl<DeleteCardActionResult> {

	
	int cardId;
	String sessionId;
	
	@Override
	public boolean isSecured() {
		return false;
	}

	
	@SuppressWarnings("unused")
	private DeleteCardAction() {
	}
	
	public DeleteCardAction(String sessionId, int cardId) {
		this.sessionId = sessionId;
		this.cardId = cardId;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public int getCardId() {
		return cardId;
	}
}
