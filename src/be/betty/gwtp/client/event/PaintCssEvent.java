package be.betty.gwtp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import java.lang.String;
import com.google.gwt.event.shared.HasHandlers;

public class PaintCssEvent extends GwtEvent<PaintCssEvent.PaintCssHandler> {

	public static Type<PaintCssHandler> TYPE = new Type<PaintCssHandler>();
	private String cardId;
	private boolean onOff;

	public interface PaintCssHandler extends EventHandler {
		void onPaintCss(PaintCssEvent event);
	}

	public PaintCssEvent(String cardId, boolean onOff) {
		this.cardId = cardId;
		this.onOff = onOff;
	}

	public String getCardId() {
		return cardId;
	}

	@Override
	protected void dispatch(PaintCssHandler handler) {
		handler.onPaintCss(this);
	}

	@Override
	public Type<PaintCssHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<PaintCssHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, String cardId, boolean onOff) {
		source.fireEvent(new PaintCssEvent(cardId, onOff));
	}

	public boolean getOnOff() {
		return onOff;
	}
}
