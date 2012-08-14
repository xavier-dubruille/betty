package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.action.DeleteProjectAction;
import be.betty.gwtp.client.action.DeleteProjectActionResult;
import be.betty.gwtp.client.event.ProjectListModifyEvent;

import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.PopupView;
import com.google.inject.Inject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.widget.client.TextButton;
import com.google.gwt.storage.client.Storage;


public class DeleteProjectPopupPresenter extends
		PresenterWidget<DeleteProjectPopupPresenter.MyView> {

	public interface MyView extends PopupView {
		
		public TextButton getButtonYes();
		public TextButton getButtonNo();
		public TextButton getButtonCloseBar();
		public Label getLabelInfo();
	}
	
	private int idProject;
	private Storage stockStore;

	
	@Inject
	public DeleteProjectPopupPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		stockStore = Storage.getLocalStorageIfSupported();

	}
	
	@Inject
	DispatchAsync dispatcher;
	
	@Override
	protected void onBind() {
		super.onBind();

		getView().getButtonYes().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				deleteProject();
				getView().hide();

			}
		});
		
		getView().getButtonNo().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getView().hide();

			}
		});
		
		getView().getButtonCloseBar().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getView().hide();
			}
		});
		
	}
	
	public void init(String text, int idProject) {
		getView().getLabelInfo().setText(text);
		this.idProject = idProject;
	}
	
	public void deleteProject() {
		String sess_id = "";
		if (stockStore != null)
			sess_id = stockStore.getItem("session_id");
		DeleteProjectAction action = new DeleteProjectAction(
				idProject, sess_id);
		dispatcher.execute(action,
				new AsyncCallback<DeleteProjectActionResult>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(
							DeleteProjectActionResult result) {
						getEventBus().fireEvent(
								new ProjectListModifyEvent()); // TODO:
						// add
						// the
						// project
						// in
						// parameter

					}
				});
	}

	
	
}
