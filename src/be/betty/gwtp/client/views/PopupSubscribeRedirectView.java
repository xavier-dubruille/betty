package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.PopupSubscribeRedirectPresenter;

import com.gwtplatform.mvp.client.PopupViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.widget.client.TextButton;
import com.google.gwt.user.client.ui.Label;

public class PopupSubscribeRedirectView extends PopupViewImpl implements
		PopupSubscribeRedirectPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, PopupSubscribeRedirectView> {
	}

	@Inject
	public PopupSubscribeRedirectView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	@UiField TextButton buttonCloseWindow;
	@UiField Label label;
	@UiField TextButton buttonOk;
	
	
	public TextButton getButtonCloseWindow() {
		return buttonCloseWindow;
	}

	public Label getLabel() {
		return label;
	}

	public TextButton getButtonOk() {
		return buttonOk;
	}
	
	
}
