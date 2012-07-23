package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.CellDropControler;
import be.betty.gwtp.client.Storage_access;
import be.betty.gwtp.client.event.BoardViewChangedEvent;
import be.betty.gwtp.client.event.DropCardEvent;
import be.betty.gwtp.client.event.BoardViewChangedEvent.BoardViewChangedHandler;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimplePanel;

public class BoardPresenter extends PresenterWidget<BoardPresenter.MyView> {

	public interface MyView extends View {

		FlexTable getFlexTable();
	}


	@Inject
	public BoardPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
		
		constructBoard();
		
		
		
		registerHandler(getEventBus().addHandler(
				BoardViewChangedEvent.getType(), boardHandler));

	}

	private void constructBoard() {
		int COLUMNS = 5;
		int ROWS = 6;
		for (int i = 0; i < COLUMNS; i++) {

			for (int j = 0; j < ROWS; j++) {
				// create a simple panel drop target for the current cell
				SimplePanel simplePanel = new SimplePanel();
				simplePanel.setPixelSize(100, 60);
				simplePanel.setStyleName("card");
				getView().getFlexTable().setWidget(j, i, simplePanel);
				//flexTable.getCellFormatter().setStyleName();
				// CSS_DEMO_PUZZLE_CELL);

				// instantiate a drop controller of the panel in the current
				// cell
				CellDropControler dropController = new CellDropControler(
						simplePanel, getEventBus(),j+1,i+1);
				MainPresenter.cardDragController.registerDropController(dropController);
			}
		}
	}
	
	private BoardViewChangedHandler boardHandler = new BoardViewChangedHandler() {
		
		@Override public void onBoardViewChanged(BoardViewChangedEvent event) {
			redrawBoard(event.getComboViewIndex_1(), event.getComboViewIndex_2());
			
		}
	};

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
		System.out.println("Let's redraqwwww Board");
		
		int COLUMNS = 5;
		int ROWS = 6;
		for (int i = 0; i < COLUMNS; i++) {

			for (int j = 0; j < ROWS; j++) {
				//System.out.println("class "+j+" "+i+"==> "+getView().getFlexTable().getWidget(j, i).getClass());
				
				if (!(getView().getFlexTable().getWidget(j, i) instanceof SimplePanel))
					continue;
				SimplePanel s = (SimplePanel) getView().getFlexTable().getWidget(j, i);
				s.clear();
				//getView().getFlexTable().getWidget(j, i).getClass()
				//getView().getFlexTable().getWidget(j, i).getElement().removeFromParent();
				
			}
		}
		//System.out.println("all cards ==== "+MainPresenter.allCards);
		// 2) on va parcourir tt les cartons et placer ceux qui doivent l'etre
		for (int i=0; i< Storage_access.getNumberOfCard(); i++){
			String c = Storage_access.getCard(i);
			int slot = Storage_access.getSlotCard(c);
			if (slot != 0) {
				if (!cardBelongToActualView(c, comboIndex1, comboIndex2)) continue;
				int col = slot%10 -1;
				int row = slot/10 -1;
				SimplePanel s = (SimplePanel) getView().getFlexTable().getWidget(row, col);
				
				s.clear();
				s.add(MainPresenter.allCards.get(i).getWidget());
				//getView().getFlexTable().setWidget(row,col, MainPresenter.allCards.get(i).getWidget());
			}
				
		}
		
	}
	
	private boolean cardBelongToActualView(String card, int index1, int index2) {
		System.out.println("Should we print this card ? =>"+card);
		System.out.println("Teacher = num "+Storage_access.getTeacherCard(card));
		System.out.println("Group   = num "+Storage_access.getGroupCard(card));
		//System.out.println("Teacher= "+Storage_access.getTeacher(Integer.parseInt(Storage_access.getTeacherCard(card) ) ));
		//System.out.println("Group= "+Storage_access.getGroup(Integer.parseInt(Storage_access.getGroupCard(card))));
		int c2 = index2;
		System.out.println("   Selected choice = "+c2);
		switch (index1){
		case 0:
			if (Storage_access.getTeacherIdCard(card) == c2) return true;
			
			break;
		case 1:
			break;
		case 2:
			if (Storage_access.getGroupIdCard(card) == c2) return true;
			break;
		}
		System.out.println("==>no");
		return false;
	}

	@Override
	protected void onReset() {
		super.onReset();
		//redrawBoard();
	}
}
