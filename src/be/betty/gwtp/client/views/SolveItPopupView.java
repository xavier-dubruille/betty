package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.SolveItPopupPresenter;

import com.gwtplatform.mvp.client.PopupViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.widget.client.TextButton;
import com.google.gwt.user.client.ui.ListBox;

public class SolveItPopupView extends PopupViewImpl implements
		SolveItPopupPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, SolveItPopupView> {
	}

	@Inject
	public SolveItPopupView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	@UiField TextButton cancelButton;
	@UiField TextButton calculeButton;
	@UiField ListBox instanceChoice;
	@UiField ListBox timeLimit;
	
	@Override
	public TextButton getCancelButton() {
		return cancelButton;
	}

	@Override
	public TextButton getCalculeButton() {
		return calculeButton;
	}

	@Override
	public ListBox getInstance_choice() {
		return instanceChoice;
	}

	@Override
	public ListBox getTimeLimit() {
		return timeLimit;
	}
}
