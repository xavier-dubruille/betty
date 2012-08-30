package be.betty.gwtp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;

public class PaintAllRedEvent extends
		GwtEvent<PaintAllRedEvent.PaintAllRedHandler> {

	public static Type<PaintAllRedHandler> TYPE = new Type<PaintAllRedHandler>();

	public interface PaintAllRedHandler extends EventHandler {
		void onPaintAllRed(PaintAllRedEvent event);
	}

	public PaintAllRedEvent() {
	}

	@Override
	protected void dispatch(PaintAllRedHandler handler) {
		handler.onPaintAllRed(this);
	}

	@Override
	public Type<PaintAllRedHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<PaintAllRedHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source) {
		source.fireEvent(new PaintAllRedEvent());
	}
}
