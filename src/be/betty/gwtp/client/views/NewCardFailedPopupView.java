package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.NewCardSuccessPopupPresenter;
import be.betty.gwtp.client.presenters.NewCardSuccessPopupPresenter.MyView;

import com.gwtplatform.mvp.client.PopupViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.widget.client.TextButton;

public class NewCardFailedPopupView extends PopupViewImpl implements
		NewCardSuccessPopupPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, NewCardFailedPopupView> {
	}

	@Inject
	public NewCardFailedPopupView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	@UiField TextButton buttonOk;
	@UiField TextButton buttonCloseWindow;
	
	public TextButton getButtonOk() {
		return buttonOk;
	}

	public TextButton getButtonCloseWindow() {
		return buttonCloseWindow;
	}
	
}
