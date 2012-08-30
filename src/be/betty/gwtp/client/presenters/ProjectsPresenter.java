package be.betty.gwtp.client.presenters;

import java.util.ArrayList;

import be.betty.gwtp.client.action.GetProjectsAction;
import be.betty.gwtp.client.action.GetProjectsActionResult;
import be.betty.gwtp.client.event.ProjectListModifyEvent;
import be.betty.gwtp.client.event.ProjectListModifyEvent.ProjectListModifyHandler;
import be.betty.gwtp.client.place.NameTokens;
import be.betty.gwtp.shared.dto.Project_dto;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.widget.client.TextButton;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.common.client.IndirectProvider;
import com.gwtplatform.common.client.StandardProvider;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class ProjectsPresenter extends
		Presenter<ProjectsPresenter.MyView, ProjectsPresenter.MyProxy> {

	public static final Object SLOT_project = new Object();

	private final ProjectListModifyHandler modifyHandler = new ProjectListModifyHandler() {

		@Override
		public void onProjectListModify(ProjectListModifyEvent event) {
			ProjectsPresenter.this.onReset();

		}
	};

	public interface MyView extends View {

		public HTMLPanel getProjectField();

		public Label getInfo_label();
		
		public Image getLoadingPicture();
		
		TextButton getButtonNewProject();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.projects)
	public interface MyProxy extends ProxyPlace<ProjectsPresenter> {
	}

	@Inject
	NewProjectPresenter newProjectPopup;
	private IndirectProvider<SingleProjectPresenter> projectFactory;
	private Storage stockStore;

	@Inject
	public ProjectsPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, final Provider<SingleProjectPresenter> provider) {
		super(eventBus, view, proxy);
		projectFactory = new StandardProvider<SingleProjectPresenter>(provider);
		stockStore = Storage.getLocalStorageIfSupported();
	}

	@Override
	protected void revealInParent() {
		// RevealRootContentEvent.fire(this, this);
		RevealContentEvent.fire(this, HeaderPresenter.SLOT_CONTENT, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		
		getView().getLoadingPicture().setVisible(false);
		getView().getButtonNewProject().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				addToPopupSlot(newProjectPopup);
			}
		});

		registerHandler(getEventBus().addHandler(
				ProjectListModifyEvent.getType(), modifyHandler));

	}

	@Inject
	DispatchAsync dispatcher;

	@Override protected void onReveal()
	{
		super.onReveal();
		setInSlot(SLOT_project, null);
	}
	
	@Override
	protected void onReset() {
		super.onReset();

		System.out.println("On reset");
		
		
		getView().getLoadingPicture().setVisible(true);
		getView().getInfo_label().setText("Loading");
				
		String sess = null;
		if (stockStore != null)
			sess = stockStore.getItem("session_id");
		if (sess == null)
			getView().getInfo_label().setText("Please (re)log first");
		else {
			GetProjectsAction action = new GetProjectsAction(sess);
			dispatcher.execute(action, projectCallback);
		}

	}

	private AsyncCallback<GetProjectsActionResult> projectCallback = new AsyncCallback<GetProjectsActionResult>() {

		@Override
		public void onSuccess(GetProjectsActionResult result) {

			getView().getInfo_label().setText("");
			getView().getLoadingPicture().setVisible(false);
			writeProjectWidgets(result.getProjects());

		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}
	};

	private void writeProjectWidgets(final ArrayList<Project_dto> projects) {
		
		final ArrayList<Project_dto> projectsSorted = new ArrayList<Project_dto>();
		
		for (int i = projects.size(); i > 0; i--){
			projectsSorted.add(projects.get(i-1));
		}
		
		setInSlot(SLOT_project, null);
		for (int i = 0; i < projectsSorted.size(); i++) {
			final int myI = i;
			
			projectFactory.get(new AsyncCallback<SingleProjectPresenter>() {

				@Override
				public void onSuccess(SingleProjectPresenter result) {
					addToSlot(SLOT_project, result);
					
					result.init(projectsSorted.get(myI));
				}

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}
			});
		}

	}
}
