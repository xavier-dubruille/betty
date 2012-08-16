package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.PopupDeleteCardPresenter;

import com.gwtplatform.mvp.client.PopupViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.widget.client.TextButton;
import com.google.gwt.user.client.ui.Label;

public class PopupDeleteCardView extends PopupViewImpl implements
		PopupDeleteCardPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, PopupDeleteCardView> {
	}

	@Inject
	public PopupDeleteCardView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	@UiField TextButton buttonYes;
	@UiField TextButton buttonNo;
	@UiField TextButton buttonCloseWindow;
	@UiField Label label;
	
	public TextButton getButtonYes() {
		return buttonYes;
	}

	public TextButton getButtonNo() {
		return buttonNo;
	}

	public TextButton getButtonCloseWindow() {
		return buttonCloseWindow;
	}

	public Label getLabel() {
		return label;
	}
	
	
	
}
