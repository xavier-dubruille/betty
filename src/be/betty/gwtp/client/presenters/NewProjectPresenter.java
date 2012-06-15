package be.betty.gwtp.client.presenters;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.PopupView;
import com.google.inject.Inject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;

public class NewProjectPresenter extends
PresenterWidget<NewProjectPresenter.MyView> {

	public interface MyView extends PopupView {

		Button getCancel_button();

		TextBox getProject_name();

		Button getSend_button();

		FormPanel getFormPanel();
	}

	@Inject
	public NewProjectPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
		//cancel_button handler
		getView().getCancel_button().addClickHandler(new ClickHandler() {
			@Override public void onClick(ClickEvent event) {
				getView().hide(); }});

		//send_button handler
		getView().getSend_button().addClickHandler(new ClickHandler() {
			@Override public void onClick(ClickEvent event) {
				getView().getFormPanel().submit(); }});

		//form handler
		getView().getFormPanel().addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override public void onSubmitComplete(SubmitCompleteEvent event) {
				//Window.alert(event.getResults());
				getView().hide();	}});
		
	}

	@Override
	protected void onReveal() {
		super.onReveal();
	}
}
