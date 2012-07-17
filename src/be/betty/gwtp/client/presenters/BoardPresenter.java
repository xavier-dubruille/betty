package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.CellDropControler;

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

	@Override
	protected void onReset() {
		super.onReset();
	}
}
