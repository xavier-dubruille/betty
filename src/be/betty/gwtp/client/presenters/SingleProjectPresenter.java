package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.action.DeleteProjectAction;
import be.betty.gwtp.client.action.DeleteProjectActionResult;
import be.betty.gwtp.client.event.PopupAreYouSureEvent;
import be.betty.gwtp.client.event.PopupAreYouSureEvent.PopupAreYouSureHandler;
import be.betty.gwtp.client.event.ProjectListModifyEvent;
import be.betty.gwtp.client.event.ShowPlacedCardEvent;
import be.betty.gwtp.client.event.ShowPlacedCardEvent.ShowPlacedCardHandler;
import be.betty.gwtp.client.model.Project;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class SingleProjectPresenter extends
		PresenterWidget<SingleProjectPresenter.MyView> {

	public interface MyView extends View {


		public Hyperlink getLinkProjectNameSem1();
		public Hyperlink getLinkProjectNameSem2();

		public Label getLabelProjectName();

		public Image getImageDelete();
		public Image getImageSettings();
	}

	private Project projectModel;
	private Storage stockStore;
	private EventBus myEventBus;
	private boolean yesIsCheck;


	@Inject
	public SingleProjectPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		stockStore = Storage.getLocalStorageIfSupported();
		myEventBus = eventBus;
	}

	@Inject
	DispatchAsync dispatcher;
	
	@Inject DeleteProjectPopupPresenter deleteProjectPresenter;

	@Override
	protected void onBind() {
		super.onBind();

		registerHandler(getEventBus().addHandler(
				PopupAreYouSureEvent.getType(), areYouSureHandler));
		getView().getImageDelete().setStyleName("clickable");
		getView().getImageSettings().setStyleName("clickable");

		getView().getImageDelete().addMouseMoveHandler(new MouseMoveHandler() {
			
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				// TODO Auto-generated method stub
				getView().getImageDelete().setTitle("mouseMoveHandler");
			}
		});
		
		getView().getImageDelete().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				addToPopupSlot(deleteProjectPresenter);
				
				
				//Window.alert("you are about to delete the project: "
				//		+ projectModel.getName());
				/*String sess_id = "";
				if (stockStore != null)
					sess_id = stockStore.getItem("session_id");
				DeleteProjectAction action = new DeleteProjectAction(
						projectModel.getId(), sess_id);
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
						});*/

			}
		});
		
		getView().getImageSettings().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("not implemented Yet" );
			}
		});
		
	}
	
	
	private PopupAreYouSureHandler areYouSureHandler = new PopupAreYouSureHandler() {
		@Override public void onPopupAreYouSure(PopupAreYouSureEvent event) {
			yesIsCheck = event.YesisCheck();
			test();
			//cardSelectionOptionPresenter.setShowPlacedCard(event.isChecked());
			//cardSelectionOptionPresenter.redrawAllCardsFromSelectionPanel();
		}
	};
	
	public void test(){
		if(yesIsCheck)
			System.out.println("yes is clicked on efface tout!");		
		
		if(!yesIsCheck)
			System.out.println("No is clicked");
	}
	
	public void deleteProject(){
		String sess_id = "";
		if (stockStore != null)
			sess_id = stockStore.getItem("session_id");
		DeleteProjectAction action = new DeleteProjectAction(
				projectModel.getId(), sess_id);
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

	public void init(Project project) {
		this.projectModel = project;
		getView().getLabelProjectName().setText(project.getName());
		getView().getLinkProjectNameSem1().setText("First semester");
		getView().getLinkProjectNameSem1().setTargetHistoryToken(
				"main;p=" + project.getId()+";s=1");
		getView().getLinkProjectNameSem2().setText("Second semester");
		getView().getLinkProjectNameSem2().setTargetHistoryToken(
				"main;p=" + project.getId()+";s=2");
		

	}
	
	/*public void setYesIsCheck(boolean yesIsCheck){
		this.yesIsCheck = yesIsCheck;
	}*/
}
