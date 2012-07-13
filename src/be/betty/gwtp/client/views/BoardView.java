package be.betty.gwtp.client.views;

import be.betty.gwtp.client.CellDropControler;
import be.betty.gwtp.client.presenters.BoardPresenter;
import be.betty.gwtp.client.presenters.BoardPresenter.MyView;
import be.betty.gwtp.client.presenters.MainPresenter;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlexTable;

public class BoardView extends ViewImpl implements BoardPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, BoardView> {
	}

	@Inject
	public BoardView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		int COLUMNS = 5;
		int ROWS = 6;
		for (int i = 0; i < COLUMNS; i++) {

			for (int j = 0; j < ROWS; j++) {
				// create a simple panel drop target for the current cell
				SimplePanel simplePanel = new SimplePanel();
				simplePanel.setPixelSize(100, 60);
				simplePanel.setStyleName("card");
				flexTable.setWidget(j, i, simplePanel);
				//flexTable.getCellFormatter().setStyleName();
				// CSS_DEMO_PUZZLE_CELL);

				// instantiate a drop controller of the panel in the current
				// cell
				CellDropControler dropController = new CellDropControler(
						simplePanel);
				MainPresenter.cardDragController.registerDropController(dropController);
			}
		}
		
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	@UiField FlexTable flexTable;
}
