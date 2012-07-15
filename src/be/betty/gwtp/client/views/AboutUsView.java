package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.AboutUsPresenter;
import be.betty.gwtp.client.presenters.AboutUsPresenter.MyView;

import com.gwtplatform.mvp.client.PopupViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class AboutUsView extends PopupViewImpl implements
		AboutUsPresenter.MyView {

	private final Widget widget;
	@UiField Button okButton;

	public interface Binder extends UiBinder<Widget, AboutUsView> {
	}

	@Inject
	public AboutUsView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	public Button getOkButton(){
		return okButton;
	}
	
}
