package be.betty.gwtp.client.presenters;

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
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class ProjectsPresenter extends
		Presenter<ProjectsPresenter.MyView, ProjectsPresenter.MyProxy> {

	public interface MyView extends View {

		Label getProjectField();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.projects)
	public interface MyProxy extends ProxyPlace<ProjectsPresenter> {
	}

	@Inject
	public ProjectsPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
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
		GetProjectsAction action = new GetProjectsAction(Betty_gwtp.session_id);
		dispatcher.execute(action, projectCallback);
		
	}
	
	private AsyncCallback<GetProjectsActionResult> projectCallback = new AsyncCallback<GetProjectsActionResult>() {
		
		@Override
		public void onSuccess(GetProjectsActionResult result) {
			getView().getProjectField().setText(result.getProjects());
			
		}
		
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}
	};
}
