package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.event.ProjectListModifyEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class NewProjectPresenter extends
		PresenterWidget<NewProjectPresenter.MyView> {

	public interface MyView extends PopupView {

		Button getCancel_button();

		TextBox getProject_name();

		Button getSend_button();

		FormPanel getFormPanel();

		Hidden get_idSess_field();
	}

	private EventBus myEventBus;

	@Inject
	public NewProjectPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		myEventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();
		// cancel_button handler
		getView().getCancel_button().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getView().hide();
			}
		});

		// send_button handler
		getView().getSend_button().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Storage stockStore = Storage.getLocalStorageIfSupported();
				if (stockStore != null)
					getView().get_idSess_field().setValue(
							stockStore.getItem("session_id"));
				getView().getFormPanel().submit();
			}
		});

		// form handler
		getView().getFormPanel().addSubmitCompleteHandler(
				new SubmitCompleteHandler() {
					@Override
					public void onSubmitComplete(SubmitCompleteEvent event) {
						// Window.alert(event.getResults());
						getView().hide();
						myEventBus.fireEvent(new ProjectListModifyEvent()); // TODO:
																			// add
																			// the
																			// project
																			// in
																			// parameter
					}
				});

	}

	@Override
	protected void onReveal() {
		super.onReveal();
	}
}
