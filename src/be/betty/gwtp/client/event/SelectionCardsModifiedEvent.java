package be.betty.gwtp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;

public class SelectionCardsModifiedEvent extends
		GwtEvent<SelectionCardsModifiedEvent.SelectionCardsModifiedHandler> {

	public static Type<SelectionCardsModifiedHandler> TYPE = new Type<SelectionCardsModifiedHandler>();

	public interface SelectionCardsModifiedHandler extends EventHandler {
		void onSelectionCardsModified(SelectionCardsModifiedEvent event);
	}

	public SelectionCardsModifiedEvent() {
	}

	@Override
	protected void dispatch(SelectionCardsModifiedHandler handler) {
		handler.onSelectionCardsModified(this);
	}

	@Override
	public Type<SelectionCardsModifiedHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<SelectionCardsModifiedHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source) {
		source.fireEvent(new SelectionCardsModifiedEvent());
	}
}
