package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.SingleCardPresenter;
import be.betty.gwtp.client.views.ourWidgets.CardWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

// This is not constructed by a xml uibinder.. this is because the dnd wasn't working on the 
// widget return by the uiBinder's way.  (with the method asWidget)
public class SingleCardView extends ViewImpl implements
SingleCardPresenter.MyView {

	CardWidget widget;

	@Inject
	public SingleCardView() {
		widget = new CardWidget();
	}

	@Override
	public CardWidget getCardWidget() {
		return widget;
	}
	
	@Override
	public Widget asWidget() {
		return this.widget;
	}

}
