package be.betty.gwtp.client.presenters;


import be.betty.gwtp.client.Storage_access;
import be.betty.gwtp.client.place.NameTokens;
import be.betty.gwtp.shared.dto.Project_dto;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class SingleProjectPresenter extends
		PresenterWidget<SingleProjectPresenter.MyView> {

	public interface MyView extends View {


		public Hyperlink getLinkProjectNameSem1();
		public Hyperlink getLinkProjectNameSem2();

		public Label getLabelProjectName();

		public Image getImageDelete();
		public Image getImageSettings();
	}

	private Project_dto projectModel;
	private String mouseState = "init";
	
	@Inject PlaceManager placeManager;

	@Inject
	public SingleProjectPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Inject
	DispatchAsync dispatcher;
	
	@Inject DeleteProjectPopupPresenter deleteProjectPresenter;

	@Override
	protected void onBind() {
		super.onBind();

		getView().getImageDelete().setStyleName("clickable");
		getView().getImageSettings().setStyleName("clickable");

		getView().getImageDelete().addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				if (!mouseState.equalsIgnoreCase("mouseOut")) {
						System.out.println("mouseOut");
						mouseState="mouseOut";
				}
			}
		});
		
		getView().getImageDelete().addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if (!mouseState.equalsIgnoreCase("mouseOver")) {
					System.out.println("mouseOver");
					mouseState="mouseOver";
			}
				
			}
		});
				
		getView().getImageDelete().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				System.out.println("ouverture du popup");
				deleteProjectPresenter.init("are you sure to delete this project?", projectModel.getId());
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

	@SuppressWarnings("deprecation")
	public void init(final Project_dto project) {
		this.projectModel = project;
		final Project_dto proj = project;
		final String nameproj = project.getName();
		getView().getLabelProjectName().setText(project.getName());
		
		getView().getLinkProjectNameSem1().setText("First semester");
		getView().getLinkProjectNameSem1().addStyleName("clickable");
		getView().getLinkProjectNameSem1().setTargetHistoryToken(
				"main;p=" + project.getId()+";s=1");
		
		getView().getLinkProjectNameSem1().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				
				Storage_access.setProjectName(proj.getName());
			}
		});
		
		getView().getLinkProjectNameSem2().setText("Second semester");
		getView().getLinkProjectNameSem2().addStyleName("clickable");
		getView().getLinkProjectNameSem2().setTargetHistoryToken(
				"main;p=" + project.getId()+";s=2");
		getView().getLinkProjectNameSem2().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				
				Storage_access.setProjectName(proj.getName());
				//Storage_access.setProjectCurrentID(""+project.getId());
			}
		});
		

	}
	

}
