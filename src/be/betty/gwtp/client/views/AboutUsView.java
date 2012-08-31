package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.AboutUsPresenter;

import com.gwtplatform.mvp.client.PopupViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.gwt.widget.client.TextButton;

public class AboutUsView extends PopupViewImpl implements
		AboutUsPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, AboutUsView> {
	}

	@UiField TextButton okButton;
	@UiField TextButton closeWindowButton;
	@UiField Label aboutUsLabel;
	
	@Inject
	public AboutUsView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	public TextButton getOkButton() {
		return okButton;
	}
	
	public TextButton getCloseWindowButton() {
		return closeWindowButton;
	}
	
	public Label getAboutUsLabel() {
		return aboutUsLabel;
	}
	
}
