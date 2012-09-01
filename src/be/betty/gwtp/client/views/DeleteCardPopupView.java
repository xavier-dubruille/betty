package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.DeleteCardPopupPresenter;
import be.betty.gwtp.client.presenters.DeleteCardPopupPresenter.MyView;

import com.gwtplatform.mvp.client.PopupViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widget.client.TextButton;

public class DeleteCardPopupView extends PopupViewImpl implements
		DeleteCardPopupPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, DeleteCardPopupView> {
	}

	@Inject
	public DeleteCardPopupView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	@UiField TextButton closeButton;
	@UiField TextButton windowCloseButton;
	@UiField VerticalPanel cardVerticalPanel;
	@UiField VerticalPanel deleteVerticalPanel;
	@UiField TextButton deleteCardButton;
	
	public TextButton getCloseButton() {
		return closeButton;
	}

	public TextButton getWindowCloseButton() {
		return windowCloseButton;
	}

	public VerticalPanel getCardVerticalPanel() {
		return cardVerticalPanel;
	}

	public VerticalPanel getDeleteVerticalPanel() {
		return deleteVerticalPanel;
	}

	public TextButton getDeleteCardButton() {
		return deleteCardButton;
	}
	
}
