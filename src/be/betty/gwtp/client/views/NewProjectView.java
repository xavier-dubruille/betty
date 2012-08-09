package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.NewProjectPresenter;

import com.gwtplatform.mvp.client.PopupViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.FileUpload;

public class NewProjectView extends PopupViewImpl implements
		NewProjectPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, NewProjectView> {
	}

	@Inject
	public NewProjectView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		widget = binder.createAndBindUi(this);
		
		//TODO: the first "setAction()" is not working on tomcat (but does on eclipse), 
		// and but the second one is ugly!! but works on Tomcat (only ?)� 
		formPanel.setAction("upload");
		//formPanel.setAction("http://betty.sytes.net/betty/upload");
		
		
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_POST);

		
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	
	@Override
	public Button getCancel_button() {
		return cancel_button;
	}
	
	@UiField Button cancel_button;
	@UiField TextBox project_name;
	@UiField Button send_button;
	@UiField FormPanel formPanel;
	@UiField Hidden sess_id;
	@UiField FileUpload courseFile_field;
	@UiField FileUpload roomFile_field;
	
	@Override
	public TextBox getProject_name() {
		return project_name;
	}

	@Override
	public Button getSend_button() {
		return send_button;
	}

	@Override
	public FormPanel getFormPanel() {
		return formPanel;
	}

	@Override
	public Hidden get_idSess_field() {
		return sess_id;
		
	}

	@Override
	public FileUpload getCourseFile_field() {
		return courseFile_field;
	}

	@Override
	public FileUpload getRoomFile_field() {
		return roomFile_field;
	}

}
