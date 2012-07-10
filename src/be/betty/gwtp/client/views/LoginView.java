package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.LoginPresenter;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class LoginView extends ViewImpl implements LoginPresenter.MyView {

	private final Widget widget;

	@UiField Label login_label;
	@UiField Label wrong_pwd;
	@UiField TextBox login_textbox;
	@UiField TextBox pwd_textbox;
	@UiField Button login_send;
	@UiField HTMLPanel Html_panel;
	@UiField AbsolutePanel Absolute_panel;
	@UiField DockPanel Dock_panel;
	
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

	public TextBox getPwd_textbox() {
		return pwd_textbox;
	}

	@Override
	public Label getWrongPwd_label() {
		return wrong_pwd;
		
	}

	public HTMLPanel getHtml_panel() {
		return Html_panel;
	}

	public AbsolutePanel getAbsolute_panel() {
		return Absolute_panel;
	}

	public DockPanel getDock_panel() {
		return Dock_panel;
	}
	
	
	
	
	
}
