package be.betty.gwtp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;

public class BoardViewChangedEvent extends
		GwtEvent<BoardViewChangedEvent.BoardViewChangedHandler> {

	public static Type<BoardViewChangedHandler> TYPE = new Type<BoardViewChangedHandler>();

	public interface BoardViewChangedHandler extends EventHandler {
		void onBoardViewChanged(BoardViewChangedEvent event);
	}

	public BoardViewChangedEvent() {
	}

	@Override
	protected void dispatch(BoardViewChangedHandler handler) {
		handler.onBoardViewChanged(this);
	}

	@Override
	public Type<BoardViewChangedHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<BoardViewChangedHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source) {
		source.fireEvent(new BoardViewChangedEvent());
	}
}
