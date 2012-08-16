package be.betty.gwtp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;

public class NewCardPopupEvent extends
		GwtEvent<NewCardPopupEvent.NewCardPopupHandler> {

	public static Type<NewCardPopupHandler> TYPE = new Type<NewCardPopupHandler>();

	public interface NewCardPopupHandler extends EventHandler {
		void onNewCardPopup(NewCardPopupEvent event);
	}

	public NewCardPopupEvent() {
	}

	@Override
	protected void dispatch(NewCardPopupHandler handler) {
		handler.onNewCardPopup(this);
	}

	@Override
	public Type<NewCardPopupHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<NewCardPopupHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source) {
		source.fireEvent(new NewCardPopupEvent());
	}
}
