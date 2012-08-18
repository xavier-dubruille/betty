package be.betty.gwtp.client.event;

import be.betty.gwtp.client.Filter_kind;
import be.betty.gwtp.client.presenters.MainPresenter;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;
import com.google.inject.Inject;

public class CardFilterEvent extends
		GwtEvent<CardFilterEvent.CardFilterHandler> {

	public static Type<CardFilterHandler> TYPE = new Type<CardFilterHandler>();

	public interface CardFilterHandler extends EventHandler {
		void onCardFilter(CardFilterEvent event);
	}

	public CardFilterEvent() {
	}
	
	@Inject MainPresenter mainPresenter;
	private int filterObjID;
	private Filter_kind filterType;
	
	public CardFilterEvent(Filter_kind filterType, int filterObjID) {
		this.filterObjID = filterObjID;
		this.filterType = filterType;
	}

	@Override
	protected void dispatch(CardFilterHandler handler) {
		handler.onCardFilter(this);
	}

	@Override
	public Type<CardFilterHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<CardFilterHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source) {
		source.fireEvent(new CardFilterEvent());
	}

	public int getFilterObjId() {
		return filterObjID;
	}

	public Filter_kind getFilterType() {
		return filterType;
	}
}
