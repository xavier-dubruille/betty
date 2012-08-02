package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.CardInView;
import be.betty.gwtp.client.CellDropControler;
import be.betty.gwtp.client.ClientUtils;
import be.betty.gwtp.client.Storage_access;
import be.betty.gwtp.client.UiConstants;
import be.betty.gwtp.client.event.BoardViewChangedEvent;
import be.betty.gwtp.client.event.DropCardEvent;
import be.betty.gwtp.client.event.BoardViewChangedEvent.BoardViewChangedHandler;
import be.betty.gwtp.client.event.DropCardEvent.DropCardHandler;
import be.betty.gwtp.client.views.ourWidgets.CardWidget;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class BoardPresenter extends PresenterWidget<BoardPresenter.MyView> {

	public interface MyView extends View {

		FlexTable getFlexTable();
	}

	private CardInView[] cardInView;
	
	private DropCardHandler dropCardHandler = new DropCardHandler() {
		@Override public void onDropCard(DropCardEvent event) {
			clearSingleCardFromBoard(""+event.getCardID(), event.getDay(), event.getPeriod());
		} 
	};
	
	private BoardViewChangedHandler boardHandler = new BoardViewChangedHandler() {
		@Override public void onBoardViewChanged(BoardViewChangedEvent event) {
			redrawBoard(event.getComboViewIndex_1(), event.getComboViewIndex_2());
		}
	};
	
	
	@Inject
	public BoardPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		cardInView = new CardInView[1];
	}

	@Override
	protected void onBind() {
		super.onBind();

		constructBoard();



		registerHandler(getEventBus().addHandler(
				BoardViewChangedEvent.getType(), boardHandler));
		
		registerHandler(getEventBus().addHandler(
				DropCardEvent.getType(), dropCardHandler));

	}

	private void constructBoard() {	
		
		int COLUMNS = 6;
		int ROWS = 7 ;
		
		for (int i = 0; i < COLUMNS; i++) {
			for (int j = 0; j < ROWS; j++) {
				// create a simple panel drop target for the current cell
				SimplePanel simplePanel = new SimplePanel();
				simplePanel.setPixelSize((UiConstants.getCardWidth()+5)/2, (UiConstants.getCardHeight()+5)/4);

				simplePanel.setStyleName("flextable");
				getView().getFlexTable().setWidget(j, i, simplePanel);
				//flexTable.getCellFormatter().setStyleName();
				// CSS_DEMO_PUZZLE_CELL);

				if (i==0 && j>0){
					simplePanel.setPixelSize((UiConstants.getCardWidth()+5)/2, UiConstants.getCardHeight()+5);
					Label periode = new Label();
					periode.setText(UiConstants.getPeriode()+" "+j);
					simplePanel.add(periode);
				}

				if (j==0 && i>0){
					simplePanel.setPixelSize(UiConstants.getCardWidth()+5, (UiConstants.getCardHeight()+5)/4);
					Label day = new Label();
					day.setText(UiConstants.getWeekDay(i-1));
					simplePanel.add(day);
				}
				if (i> 0 && j>0){
					simplePanel.setPixelSize(UiConstants.getCardWidth()+5, UiConstants.getCardHeight()+5);

					// instantiate a drop controller of the panel in the current cell
					CellDropControler dropController = new CellDropControler(
							simplePanel, cardInView, getEventBus(),j,i);
					MainPresenter.cardDragController.registerDropController(dropController);
				}
			}
		}
	}



	/**
	 * 
	 * Redraw the board, depending on de view (cf parameters)
	 * and and the instance (witch is stored in local Storage,
	 * so it has to be the right instance..)
	 * 
	 * @param comboIndex1 If it's a teacher, a room or a group
	 * @param comboIndex2 witch tearcher, room or group
	 */
	public void redrawBoard(int comboIndex1, int comboIndex2) {

		// 1) on va parcourir tt le board, vider les cases
		System.out.println("Let's redrawwww Board");

		int COLUMNS = Storage_access.getNbDays() + 1;
		int ROWS = Storage_access.getNbPeriods() +1 ;
		for (int i = 1; i < COLUMNS; i++) 
			for (int j = 1; j < ROWS; j++) {
				//System.out.println("class "+j+" "+i+"==> "+getView().getFlexTable().getWidget(j, i).getClass());

				if (!(getView().getFlexTable().getWidget(j, i) instanceof SimplePanel))
					continue;
				SimplePanel s = (SimplePanel) getView().getFlexTable().getWidget(j, i);
				s.clear();
				//getView().getFlexTable().getWidget(j, i).getClass()
				//getView().getFlexTable().getWidget(j, i).getElement().removeFromParent();
			}
		
		//System.out.println("all cards ==== "+MainPresenter.allCards);
		// 2) on va parcourir tt les cartons et placer ceux qui doivent l'etre
		for (int i=0; i< Storage_access.getNumberOfCard(); i++){
			String c = Storage_access.getCard(i);
			int slot = Storage_access.getSlotCard(c);
			if (slot != 0) {
				if (!cardInView[0].cardBelongToActualView(c)) continue;
				int col = ClientUtils.storageSlotToFlexCol(slot);
				int row = ClientUtils.storageSlotToFlexRow(slot);
				SimplePanel s = (SimplePanel) getView().getFlexTable().getWidget(row, col);

				s.clear();
				s.add( MainPresenter.allCards.get(i).cloneWidget());
				//getView().getFlexTable().setWidget(row,col, MainPresenter.allCards.get(i).getWidget());
			}

		}

	}
	

	public void clearSingleCardFromBoard(String cardId, int day, int period) {
		
		
		int COLUMNS = Storage_access.getNbDays() + 1;
		int ROWS = Storage_access.getNbPeriods() +1 ;
		for (int i = 1; i < COLUMNS; i++) 
			for (int j = 1; j < ROWS; j++) {
				//System.out.println("class "+j+" "+i+"==> "+getView().getFlexTable().getWidget(j, i).getClass());

				if (i == day && j == period) continue;
				if (!(getView().getFlexTable().getWidget(j, i) instanceof SimplePanel))
					continue; // ca arrive ?
				SimplePanel s = (SimplePanel) getView().getFlexTable().getWidget(j, i);
				Widget w = s.getWidget();
				if (w == null || w.getElement() == null) continue;
				if(cardId.equals( w.getElement().getTitle()))
						s.clear();
				//getView().getFlexTable().getWidget(j, i).getClass()
				//getView().getFlexTable().getWidget(j, i).getElement().removeFromParent();
			}
	}
	
	@Override protected void onReset() {
		super.onReset();
		//redrawBoard();
	}

	public void init(CardInView cardInView) {
		this.cardInView[0] = cardInView;	
	}
}
