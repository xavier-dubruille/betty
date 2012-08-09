package be.betty.gwtp.client;

import be.betty.gwtp.client.presenters.MainPresenter;
import be.betty.gwtp.client.views.ourWidgets.CardWidget;

import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandler;
import com.allen_sauer.gwt.dnd.client.DragStartEvent;
import com.allen_sauer.gwt.dnd.client.VetoDragException;

public class CardHandler implements DragHandler {

	/**
	 * the main (and only ?) purpose is to chose what to do 
	 * in a case of a drop failure from a cardWiget from the selectionPanel
	 */
	@Override public void onDragEnd(DragEndEvent event) {
		System.out.println("on drag end: "+ event);

		if (event.getContext().finalDropController == null 
				&& event.getSource() instanceof CardWidget 
				&& ((CardWidget)event.getSource()).isFromSelectionPanel()) {
			CardWidget w1 = (CardWidget) event.getSource();

			int id = Integer.parseInt(w1.getElement().getTitle()); //TODO faut un meilleur moyen que le titre!
			CardWidget w2 =  MainPresenter.allCards.get(""+id);
			
			w1.setVisible(false);
//			if (w2 != null && w2.getParent() instanceof ModifiedVerticalPanel)
//				((ModifiedVerticalPanel)w2.getParent()).realRemove(w2);
//			MainPresenter.allCards.put(""+id, w1);
//			


			if (w2.isPlaced())
				w2.setStyleName(UiConstants.CSS_PLACED_CARD);
			else 
				w2.setStyleName(UiConstants.CSS_CARD);
			
		}

	}

	@Override
	public void onDragStart(DragStartEvent event) {
		System.out.println("on drag start: "+ event);

	}

	@Override
	public void onPreviewDragEnd(DragEndEvent event) throws VetoDragException {
		System.out.println("on preview drag end: "+ event);
		System.out.println("*** drag end :"+event.getSource().toString());
		//getEventBus().fireEvent( new DropCardEvent());

	}

	@Override
	public void onPreviewDragStart(DragStartEvent event)
			throws VetoDragException {
		System.out.println("on preview drag start: "+ event);

	}

}
