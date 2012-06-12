package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.SubscribePresenter;
import be.betty.gwtp.client.presenters.SubscribePresenter.MyView;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class SubscribeView extends ViewImpl implements
		SubscribePresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, SubscribeView> {
	}

	@Inject
	public SubscribeView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
}
