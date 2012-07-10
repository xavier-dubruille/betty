package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.SingleCardPresenter;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class SingleCardView extends ViewImpl implements
		SingleCardPresenter.MyView {

	Widget widget;
	public VerticalPanel verticalPanel;
	public HTML header;

	@Inject
	public SingleCardView() {
		// use the boundary panel as this composite's widget
		final AbsolutePanel boundaryPanel = new AbsolutePanel();
		boundaryPanel.setPixelSize(100, 60);
		boundaryPanel.addStyleName("card");
		widget = boundaryPanel;

		// create the title bar
		header = new HTML("Empty");

		// create a panel to hold all our widgets
		verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(1);
		verticalPanel.add(header);
		boundaryPanel.add(verticalPanel);
	}

	@Override
	public Widget asWidget() {
		return this.widget;
	}

	@Override
	public VerticalPanel getVerticalPanel() {
		return verticalPanel;
	}

	@Override
	public HTML getHeader() {
		return header;
	}
}
