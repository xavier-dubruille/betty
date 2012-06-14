package be.betty.gwtp.client.presenters;

import java.util.ArrayList;

import com.gwtplatform.common.client.IndirectProvider;
import com.gwtplatform.common.client.StandardProvider;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;

import be.betty.gwtp.client.Betty_gwtp;
import be.betty.gwtp.client.action.GetProjectsAction;
import be.betty.gwtp.client.action.GetProjectsActionResult;
import be.betty.gwtp.client.place.NameTokens;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class ProjectsPresenter extends
		Presenter<ProjectsPresenter.MyView, ProjectsPresenter.MyProxy> {

	public static final Object SLOT_project = new Object();
	
	public interface MyView extends View {

		public HTMLPanel getProjectField();
		public Label getInfo_label();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.projects)
	public interface MyProxy extends ProxyPlace<ProjectsPresenter> {
	}

	private IndirectProvider<SingleProjectPresenter> projectFactory;
	
	@Inject
	public ProjectsPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, final Provider<SingleProjectPresenter> provider) {
		super(eventBus, view, proxy);
		projectFactory = new StandardProvider<SingleProjectPresenter>(provider);
	}

	@Override
	protected void revealInParent() {
		//RevealRootContentEvent.fire(this, this);
		RevealContentEvent.fire(this, HeaderPresenter.SLOT_CONTENT, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	@Inject DispatchAsync dispatcher;
	@Override
	protected void onReset() {
		super.onReset();
		
		setInSlot(SLOT_project, null);
			
		getView().getInfo_label().setText("Fetching info ..");
		GetProjectsAction action = new GetProjectsAction(Betty_gwtp.session_id);
		dispatcher.execute(action, projectCallback);
		
	}
	
	private AsyncCallback<GetProjectsActionResult> projectCallback = new AsyncCallback<GetProjectsActionResult>() {
		
		@Override
		public void onSuccess(GetProjectsActionResult result) {
	
			getView().getInfo_label().setText("");
			writeProjectWidgets(result.getProjects());
			
		}
		
		

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void writeProjectWidgets(final ArrayList<String> projects) {
		
		for (int i=0; i<projects.size(); i++) {
			final int myI = i;
			projectFactory.get(new AsyncCallback<SingleProjectPresenter>() {
				
				@Override
				public void onSuccess(SingleProjectPresenter result) {
					addToSlot(SLOT_project, result);
					result.init(projects.get(myI));				
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
	}
}
