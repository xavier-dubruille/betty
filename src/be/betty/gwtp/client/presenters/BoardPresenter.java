package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.CardInView;
import be.betty.gwtp.client.CellDropControler;
import be.betty.gwtp.client.CellState;
import be.betty.gwtp.client.ClientSolver;
import be.betty.gwtp.client.ClientUtils;
import be.betty.gwtp.client.Storage_access;
import be.betty.gwtp.client.UiConstants;
import be.betty.gwtp.client.event.BoardViewChangedEvent;
import be.betty.gwtp.client.event.DropCardEvent;
import be.betty.gwtp.client.event.BoardViewChangedEvent.BoardViewChangedHandler;
import be.betty.gwtp.client.event.DropCardEvent.DropCardHandler;
import be.betty.gwtp.client.event.PaintCssEvent;
import be.betty.gwtp.client.event.PaintCssEvent.PaintCssHandler;

import com.allen_sauer.gwt.dnd.client.DragController;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class BoardPresenter extends PresenterWidget<BoardPresenter.MyView> {

	public interface MyView extends View {

		FlexTable getFlexTable();
	}


	private CardInView[] cardInView;
	private CellState[][] cellState;

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

	private PaintCssHandler paintCssHandler = new PaintCssHandler() {
		@Override public void onPaintCss(PaintCssEvent event) {
			System.out.println("ON dragggg !!");
			if (event.getOnOff())
				constructCellState(event.getCardId());
			paintSolverCss(event.getOnOff());
		}
	};


	@Inject
	public BoardPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		cardInView = new CardInView[1];
		cellState = new CellState[5][6];
	}

	@Override
	protected void onBind() {
		super.onBind();

		constructBoard();

		registerHandler(getEventBus().addHandler(
				BoardViewChangedEvent.getType(), boardHandler));

		registerHandler(getEventBus().addHandler(
				DropCardEvent.getType(), dropCardHandler));

		registerHandler(getEventBus().addHandler(
				PaintCssEvent.getType(), paintCssHandler));

	}

	private void constructBoard() {	

		int COLUMNS = 6;// Storage_access.getNbDays() + 1;
		int ROWS = 7; //Storage_access.getNbPeriods() +1 ;

		for (int i = 0; i < COLUMNS; i++) {
			for (int j = 0; j < ROWS; j++) {
				// create a simple panel drop target for the current cell
				SimplePanel simplePanel = new SimplePanel();
				simplePanel.setPixelSize((UiConstants.getCardWidth() + 5) / 2,(UiConstants.getCardHeight() + 5) / 4);

				simplePanel.setStyleName("panelFlextable");
				getView().getFlexTable().setWidget(j, i, simplePanel);
				// flexTable.getCellFormatter().setStyleName();
				// CSS_DEMO_PUZZLE_CELL);


				if (i==0 && j>0){
					simplePanel.setPixelSize((UiConstants.getCardWidth()+5)/2, UiConstants.getCardHeight()+5);
					Label periode = new Label();
					periode.setText(UiConstants.getPeriode() + " " + j);
					simplePanel.setStyleName("titleFlextable");
					simplePanel.add(periode);
				}


				if (j==0 && i>0){
					simplePanel.setPixelSize(UiConstants.getCardWidth()+5, (UiConstants.getCardHeight()+5)/4);
					Label day = new Label();
					day.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
					day.setText(UiConstants.getWeekDay(i - 1));
					simplePanel.setStyleName("titleFlextable");
					simplePanel.add(day);
				}
				
				if(j==0 && i==0){
					simplePanel.setStyleName("titleFlextable");
				}

				//if we are not on the first line/column, register the panel as dropControler
				if (i > 0 && j > 0) {
					simplePanel.setPixelSize(UiConstants.getCardWidth() + 5,UiConstants.getCardHeight() + 5);


					// instantiate a drop controller of the panel in the current cell
					CellDropControler dropController = new CellDropControler(
							simplePanel, cardInView, getEventBus(),j,i);
					MainPresenter.cardDragController.registerDropController(dropController);
				}
			}
		}
	}



	//		@Override public void onBoardViewChanged(BoardViewChangedEvent event) {
	//			redrawBoard(event.getComboViewIndex_1(), event.getComboViewIndex_2());
	//
	//		}
	//	};

	/**
	 * 
	 * Redraw the board, depending on de view (cf parameters) and and the
	 * instance (witch is stored in local Storage, so it has to be the right
	 * instance..)
	 * 
	 * @param comboIndex1
	 *            If it's a teacher, a room or a group
	 * @param comboIndex2
	 *            witch tearcher, room or group
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
				//s.addStyleName(UiConstants.CSS_SINGLE_CELL);

				//getView().getFlexTable().getWidget(j, i).getClass()
				//getView().getFlexTable().getWidget(j, i).getElement().removeFromParent();
			}


		//System.out.println("all cards ==== "+MainPresenter.allCards);
		// 2) on va parcourir tt les cartons et placer ceux qui doivent l'etre
		for (int i = 0; i < Storage_access.getNumberOfCard(); i++) {
			String c = Storage_access.getCard(i);
			int slot = Storage_access.getSlotCard(c);
			if (slot != 0) {

				if (!cardInView[0].cardBelongToActualView(c)) continue;
				int col = ClientUtils.storageSlotToFlexCol(slot);
				int row = ClientUtils.storageSlotToFlexRow(slot);
				SimplePanel s = (SimplePanel) getView().getFlexTable().getWidget(row, col);

				s.clear();
				//System.out.println("je veu rajouter, dans la case "+row+"-"+col+" le carton num "+i);
				//System.out.println("allcard = "+MainPresenter.allCards);
				//Storage_access.printStorage();
				s.add( MainPresenter.allCards.get(""+i).cloneWidget(false));
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
		// redrawBoard();
	}

	public void init(CardInView cardInView) {
		this.cardInView[0] = cardInView;	
	}

	public void constructCellState(String cardID) {
		
		int cId = Integer.parseInt(cardID);
		for (int i = 0; i < cellState.length; i++){
			for (int j = 0; j < cellState[0].length; j++){
				//System.out.println("pour le cellState "+i+"-"+j+" la couleur va etre de "+ClientSolver.getColor(cardID, i, j));
				cellState[i][j] = ClientSolver.getColor(cardID, j+1, i+1);
				//if ((cId+i+j) %2 ==0 ) cellState[i][j].setColor(8); //juste pour tester ;)

			}
		}
	}

	public void paintSolverCss(boolean on_off) {

		//ClientUtils.notifyUser("debug: paintCss "+on_off, 1, getEventBus());
		final int COLUMNS = Storage_access.getNbDays() + 1;
		final int ROWS = Storage_access.getNbPeriods() +1 ;

		assert COLUMNS == cellState.length +1;
		assert ROWS == cellState[0].length +1;

		if (on_off)
			for (int i = 1; i < COLUMNS; i++){
				for (int j = 1; j < ROWS; j++){
					Widget w = getView().getFlexTable().getWidget(j, i);
					//CellState c = cellState[i-1][j-1];
					//System.out.println("cellState num "+i+ " "+j+" cet ca donne: "+c);
					//System.out.println("color to be painted = "+c.getColor());
					//ystem.out.println("la couleur 1 coorespond en css a: "+UiConstants.getColorCss(1));
					CellState cs = cellState[i-1][j-1];
					int color = cs.getColor();
					w.setStyleName(UiConstants.getColorCss(color));
					if (color !=0 ) {
						SimplePanel cell = ((SimplePanel)w);
						cell.clear();
						cell.add(new Label(cs.getReason()));
					}

				}
			}
		else 
			for (int i = 1; i < COLUMNS; i++){
				for (int j = 1; j < ROWS; j++){
					Widget w = getView().getFlexTable().getWidget(j, i);
					w.setStyleName(UiConstants.CSS_SINGLE_CELL);
					redrawBoard(0,0);

				}
			}
	}
}
