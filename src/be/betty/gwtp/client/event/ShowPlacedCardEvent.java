package be.betty.gwtp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;

public class ShowPlacedCardEvent extends
		GwtEvent<ShowPlacedCardEvent.ShowPlacedCardHandler> {

	public static Type<ShowPlacedCardHandler> TYPE = new Type<ShowPlacedCardHandler>();
	private boolean checked;

	public interface ShowPlacedCardHandler extends EventHandler {
		void onShowPlacedCard(ShowPlacedCardEvent event);
	}

	public ShowPlacedCardEvent(boolean checked) {
		this.checked = checked;
	}

	public boolean isChecked() {
		return checked;
	}

	@Override
	protected void dispatch(ShowPlacedCardHandler handler) {
		handler.onShowPlacedCard(this);
	}

	@Override
	public Type<ShowPlacedCardHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<ShowPlacedCardHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, boolean checked) {
		source.fireEvent(new ShowPlacedCardEvent(checked));
	}
}
