package be.betty.gwtp.client;

import be.betty.gwtp.client.event.DropCardEvent;
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

	public CellDropControler(SimplePanel dropTarget, CardInView[] cardInView, EventBus eventBus, int day, int period) {
		super(dropTarget);
		this.eventBus = eventBus;
		this.dropTarget = dropTarget;
		this.day = day;
		this.period = period;
		this.cardInView = cardInView;
	}

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
			
			//TODO faut voire si c'est la meilleur maniere, notement pour la methode static
			int room = Integer.parseInt(ClientSolver.findBestRoom(id, day, period)); 
			eventBus.fireEvent( new DropCardEvent(id,day,period, room));
			//System.out.println("drop..."+w.g);
			//Storage_access.printStorage();
			((CardWidget)w).setFromSelectionPanel(false);
		}
		super.onDrop(context);
		
	}

	@Override
	public void onPreviewDrop(DragContext context) throws VetoDragException {
		
		
		CardWidget w = (CardWidget) context.selectedWidgets.get(0);
		String cardId = w.getElement().getTitle();
		String card = Storage_access.getCard(cardId);
		//CardWidget w2 = MainPresenter.allCards.get(cardId);
		if (dropTarget.getWidget() != null || !cardInView[0].cardBelongToActualView(card)) {
			ClientUtils.notifyUser("This Card can't be placed there", eventBus);
			throw new VetoDragException();
		}
		super.onPreviewDrop(context);
	}
}
