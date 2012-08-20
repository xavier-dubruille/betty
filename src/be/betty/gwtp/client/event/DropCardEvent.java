package be.betty.gwtp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;

public class DropCardEvent extends GwtEvent<DropCardEvent.DropCardHandler> {

	public static Type<DropCardHandler> TYPE = new Type<DropCardHandler>();

	public interface DropCardHandler extends EventHandler {
		void onDropCard(DropCardEvent event);
	}

	private int cardID;
	private int day;
	private int period;
	private String room;

	public DropCardEvent(int cardID, int period, int day, String room) {
		super();
		this.cardID = cardID;
		this.day = day;
		this.period = period;
		this.room = room;
	}

	public DropCardEvent() {
	}

	@Override
	protected void dispatch(DropCardHandler handler) {
		handler.onDropCard(this);
	}

	@Override
	public Type<DropCardHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<DropCardHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source) {
		source.fireEvent(new DropCardEvent());
	}

	public int getCardID() {
		return cardID;
	}

	public int getDay() {
		return day;
	}

	public int getPeriod() {
		return period;
	}

	public void setCardID(int cardID) {
		this.cardID = cardID;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public String getRoom() {
		return room;
	}
}
