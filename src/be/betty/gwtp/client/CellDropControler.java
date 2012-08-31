package be.betty.gwtp.client;

import be.betty.gwtp.client.event.DropCardEvent;
import be.betty.gwtp.client.presenters.MainPresenter;
import be.betty.gwtp.client.views.ourWidgets.CardWidget;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;

/**
 * DropController which allows a widget to be dropped on a SimplePanel drop
 * target when the drop target does not yet have a child widget.
 */
public class CellDropControler extends SimpleDropController {

	private final SimplePanel dropTarget;
	private EventBus eventBus;
	private int day;
	private int period;
	private CardInView[] cardInView;

	/**
	 * 
	 * @param dropTarget
	 * @param cardInView
	 * @param eventBus
	 * @param day
	 * @param period
	 */
	public CellDropControler(SimplePanel dropTarget, CardInView[] cardInView, EventBus eventBus, int day, int period) {
		super(dropTarget);
		this.eventBus = eventBus;
		this.dropTarget = dropTarget;
		this.day = day;
		this.period = period;
		this.cardInView = cardInView;
	}


	/**
	 *  @Override
	 */
	@Override
	public void onDrop(DragContext context) {
		dropTarget.setWidget(context.draggable);
		//dropTarget.add(context.draggable);
		
		Widget w = context.selectedWidgets.get(0);
		if ( w != null) {
			w.setStyleName(UiConstants.CSS_CARD);
			//String s = w.getElement().getAttribute("id");
			//System.out.println(w.getElement().getTitle());
			int id = Integer.parseInt(w.getElement().getTitle()); //TODO faut un meilleur moyen!
			// TODO si on fait un sorte de petit popup pour choisir grafiquement le local, c ici.
			
			//TODO faut voire si "findBestRoom" est la meilleur maniere de faire..
			String roomId = cardInView[0].isRoomView() ?
					cardInView[0].getRoom() : ClientSolver.findBestRoom(id, day, period);
			MainPresenter.allCards.get(""+id).setRoom(roomId);
			((CardWidget)w).setRoom(roomId);
			// faut aussi mettre le local sur le carton actuel i.e. w
			eventBus.fireEvent( new DropCardEvent(id,day,period, roomId));
			//System.out.println("drop..."+w.g);
			//Storage_access.printStorage();
			((CardWidget)w).setFromSelectionPanel(false);
		}
		super.onDrop(context);
		
	}


	/**
	 *  @Override
	 */
	@Override
	public void onPreviewDrop(DragContext context) throws VetoDragException {
		
		
		CardWidget w = (CardWidget) context.selectedWidgets.get(0);
		String cardId = w.getElement().getTitle();
		String card = Storage_access.getCard(cardId);
		//CardWidget w2 = MainPresenter.allCards.get(cardId);
		if ( dropTarget.getWidget() instanceof CardWidget || !cardInView[0].cardBelongToActualView(card, true)) {
			ClientUtils.notifyUser("The Card \""+Storage_access.getCourseCardName(card)+"\"can't be placed there..", 1, eventBus);
			throw new VetoDragException();
		}
		super.onPreviewDrop(context);
	}
}
