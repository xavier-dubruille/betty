package be.betty.gwtp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;

public class DeleteCardEvent extends
		GwtEvent<DeleteCardEvent.DeleteCardHandler> {

	public static Type<DeleteCardHandler> TYPE = new Type<DeleteCardHandler>();
	
	private String sessionId;
	private int cardId;
	
	public interface DeleteCardHandler extends EventHandler {
		void onDeleteCard(DeleteCardEvent event);
	}

	public DeleteCardEvent(String sessionId, int cardId) {
		this.sessionId = sessionId;
		this.cardId = cardId;
	}

	@Override
	protected void dispatch(DeleteCardHandler handler) {
		handler.onDeleteCard(this);
	}

	@Override
	public Type<DeleteCardHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<DeleteCardHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source,String sessionId, int cardId) {
		source.fireEvent(new DeleteCardEvent(sessionId, cardId));
	}
	
	public int getCardId(){
		return cardId;
	}
	
	public String getSessionId(){
		return sessionId;
	}
}