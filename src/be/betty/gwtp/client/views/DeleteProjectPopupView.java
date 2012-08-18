package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.DeleteProjectPopupPresenter;

import com.gwtplatform.mvp.client.PopupViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widget.client.TextButton;
import com.google.gwt.user.client.ui.Label;

public class DeleteProjectPopupView extends PopupViewImpl implements
		DeleteProjectPopupPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, DeleteProjectPopupView> {
	}

	@Inject
	public DeleteProjectPopupView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	@UiField VerticalPanel verticalPanel;
	@UiField TextButton buttonCloseBar;
	@UiField Label LabelInfo;
	@UiField TextButton buttonYes;
	@UiField TextButton ButtonNo;
	
	public Label getLabelInfo() {
		return LabelInfo;
	}

	public void setLabelInfo(Label labelInfo) {
		LabelInfo = labelInfo;
	}

	public TextButton getButtonCloseBar() {
		return buttonCloseBar;
	}

	public TextButton getButtonYes() {
		return buttonYes;
	}

	public TextButton getButtonNo() {
		return ButtonNo;
	}
	
	
}
