package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.AddNewCardPopupPresenter;
import be.betty.gwtp.client.presenters.AddNewCardPopupPresenter.MyView;

import com.gwtplatform.mvp.client.PopupViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class AddNewCardPopupView extends PopupViewImpl implements
		AddNewCardPopupPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, AddNewCardPopupView> {
	}

	@Inject
	public AddNewCardPopupView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
}
