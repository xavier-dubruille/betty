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
import com.google.gwt.user.client.ui.Hidden;

public class BoardView extends ViewImpl implements BoardPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, BoardView> {
	}

	@Inject
	public BoardView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		
		
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	@UiField FlexTable flexTable;

	@Override
	public FlexTable getFlexTable() {
		return flexTable;
	}
}
