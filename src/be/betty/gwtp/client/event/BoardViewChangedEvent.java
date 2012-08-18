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

	private int comboViewIndex_1;
	private int comboViewIndex_2;

	private BoardViewChangedEvent() {
		// on peut p-e mettre d'autre valeurs "par default"... 
		// mais de tt facon, se constructeur "ne devrait pas" etre utilise.
		comboViewIndex_1 = 0;
		comboViewIndex_2 = 0;
	}
	
	

	public BoardViewChangedEvent(int comboViewIndex_1, int comboViewIndex_2) {
		super();
		this.comboViewIndex_1 = comboViewIndex_1;
		this.comboViewIndex_2 = comboViewIndex_2;
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

	public int getComboViewIndex_1() {
		return comboViewIndex_1;
	}

	public int getComboViewIndex_2() {
		return comboViewIndex_2 ;
	}
}
