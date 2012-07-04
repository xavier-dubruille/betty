package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.Betty_gwtp;
import be.betty.gwtp.client.action.GetSpecificProject;
import be.betty.gwtp.client.action.GetSpecificProjectResult;
import be.betty.gwtp.client.place.NameTokens;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.client.drop.IndexedDropController;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class MainPresenter extends
		Presenter<MainPresenter.MyView, MainPresenter.MyProxy> {

	public interface MyView extends View {
		public Label getMainLabel();
		public AbsolutePanel getDndPanel();
		public AbsolutePanel getDropPanel();
		public Image getDndImage();
		void setHtml_panel(HTMLPanel html_panel);
		HTMLPanel getHtml_panel();
		Label getContent();
		void setContent(Label content);
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.main)
	public interface MyProxy extends ProxyPlace<MainPresenter> {
	}

	private Storage stockStore;

	@Inject
	public MainPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
		stockStore = Storage.getLocalStorageIfSupported();
	}

	@Override
	protected void revealInParent() {
		//RevealRootContentEvent.fire(this, this);
		RevealContentEvent.fire(this, HeaderPresenter.SLOT_CONTENT, this);
	}
	

	private String project_num;
	
	@Override
	public void prepareFromRequest(PlaceRequest request){
		super.prepareFromRequest(request);
		project_num = request.getParameter("p", "-1");
		//	System.out.println("prepare from request: "+name);
	}

	@Override
	protected void onBind() {
		super.onBind();
		set_dnd();
	}

	private void set_dnd() {
		  // create a DragController to manage drag-n-drop actions
	    // note: This creates an implicit DropController for the boundary panel
	    PickupDragController dragController = new PickupDragController(getView().getDndPanel(), false);

	    // add a new image to the boundary panel and make it draggable
	    
	    //dragController.makeDraggable(getView().getDndImage());
	    
	    AbsolutePositionDropController sp = new AbsolutePositionDropController(getView().getDropPanel());
	    //IndexedDropController dropController = new IndexedDropController(getView().getDropPanel());
	    
	    dragController.registerDropController(sp);
	    dragController.makeDraggable(getView().getDndImage());

	    
	}
	
	@Inject DispatchAsync dispatcher;

	@Override
	protected void onReset() {
		super.onReset();
		
		
		
		
		String login = "";
		String sess = "";
		if (stockStore != null ) {
			sess = stockStore.getItem("session_id");
			login = stockStore.getItem("login");
		}
		if (sess == null) {
			getView().getMainLabel().setText("Please (re)log first");
			return;
		}
		
		getView().getMainLabel().setText("Welcome "+login+ " *****  Projet num "+ project_num);
		
		GetSpecificProject info = new GetSpecificProject(project_num);
		dispatcher.execute(info, new AsyncCallback<GetSpecificProjectResult>() {

			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(GetSpecificProjectResult result) {
				getView().getContent().setText(result.getActivities().toString());
				
			}
		});
		
		
		
	}
}
