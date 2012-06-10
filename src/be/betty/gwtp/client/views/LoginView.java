package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.LoginPresenter;
import be.betty.gwtp.client.presenters.LoginPresenter.MyView;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class LoginView extends ViewImpl implements LoginPresenter.MyView {

	private final Widget widget;

	@UiField Label login_label;
	@UiField TextBox login_textbox;
	@UiField Button login_send;
	
	public interface Binder extends UiBinder<Widget, LoginView> {
	}

	@Inject
	public LoginView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	public Label getLogin_label() {
		return login_label;
	}

	public TextBox getLogin_textbox() {
		return login_textbox;
	}

	public Button getLogin_send() {
		return login_send;
	}
	
	
}
