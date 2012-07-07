package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.CardPresenter;
import be.betty.gwtp.client.presenters.CardPresenter.MyView;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HTMLPanel;

public class CardView extends ViewImpl implements CardPresenter.MyView {

	private final Widget widget;
	@UiField Label mainLabel;
	@UiField HTMLPanel wholePanel;

	public interface Binder extends UiBinder<Widget, CardView> {
	}

	@Inject
	public CardView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	@Override
	public Label getMainLabel() {
		return mainLabel;
	}

	@Override
	public void setMainLabel(Label mainLabel) {
		this.mainLabel = mainLabel;
	}
	@Override
	public HTMLPanel getWholePanel() {
		return wholePanel;
	}

	public void setWholePanel(HTMLPanel wholePanel) {
		this.wholePanel = wholePanel;
	}
	
	
}
