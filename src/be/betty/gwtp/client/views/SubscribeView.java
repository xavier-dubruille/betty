package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.SubscribePresenter;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.widget.client.TextButton;
import com.google.gwt.user.client.ui.Hyperlink;

public class SubscribeView extends ViewImpl implements SubscribePresenter.MyView {

	private final Widget widget;
	
	@UiField AbsolutePanel absoluteSubscribePanel;
	@UiField DockPanel DockSubscribePanel;
	@UiField Label emailSubLabel;
	@UiField TextBox userSubTextbox;
	@UiField PasswordTextBox passSubTextbox;
	@UiField PasswordTextBox passVerSubTextbox;
	@UiField TextBox emailSubTextbox;
	@UiField Label userSubErrorLabel;
	@UiField Label passSubErrorLabel;
	@UiField Label passVerSubErrorLabel;
	@UiField Label emailSubErrorLabel;
	@UiField Image usernamePicture;
	@UiField Image passwordPicture;
	@UiField Image passwordVerPicture;
	@UiField Image emailPicture;
	@UiField TextButton buttonSubscribe;
	@UiField Hyperlink hyperlinkLogin;
	
	
	public interface Binder extends UiBinder<Widget, SubscribeView> {
	}

	@Inject
	public SubscribeView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	
	
	public AbsolutePanel getAbsoluteSubscribePanel() {
		return absoluteSubscribePanel;
	}

	public DockPanel getDockSubscribePanel() {
		return DockSubscribePanel;
	}

	public Label getEmailSubLabel() {
		return emailSubLabel;
	}

	public TextBox getUserSubTextbox() {
		return userSubTextbox;
	}

	public PasswordTextBox getPassSubTextbox() {
		return passSubTextbox;
	}

	public PasswordTextBox getPassVerSubTextbox() {
		return passVerSubTextbox;
	}

	public TextBox getEmailSubTextbox() {
		return emailSubTextbox;
	}	

	public Label getUserSubErrorLabel() {
		return userSubErrorLabel;
	}

	public Label getPassSubErrorLabel() {
		return passSubErrorLabel;
	}

	public Label getPassVerSubErrorLabel() {
		return passVerSubErrorLabel;
	}
	
	public Label getEmailSubErrorLabel(){
		return emailSubErrorLabel;
	}

	public Image getUsernamePicture() {
		return usernamePicture;
	}

	public Image getPasswordPicture() {
		return passwordPicture;
	}

	public Image getPasswordVerPicture() {
		return passwordVerPicture;
	}

	public Image getEmailPicture() {
		return emailPicture;
	}
	
	public TextButton getButtonSubscribe(){
		return buttonSubscribe;
	}
	
	public Hyperlink getHyperlinkLogin(){
		return hyperlinkLogin;
	}

}
