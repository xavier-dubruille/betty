package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.Betty_gwtp;
import be.betty.gwtp.client.place.NameTokens;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class MainPresenter extends
		Presenter<MainPresenter.MyView, MainPresenter.MyProxy> {

	public interface MyView extends View {
		public Label getMainLabel();
		public AbsolutePanel getDndPanel();
		public Image getDndImage();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.main)
	public interface MyProxy extends ProxyPlace<MainPresenter> {
	}

	@Inject
	public MainPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}
	
	private String namex = "";
	
	@Override
	public void prepareFromRequest(PlaceRequest request){
		super.prepareFromRequest(request);
		namex = request.getParameter("name", "Default_Value");
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
	    PickupDragController dragController = new PickupDragController(getView().getDndPanel(), true);

	    // add a new image to the boundary panel and make it draggable
	    
	    dragController.makeDraggable(getView().getDndImage());
		
	}

	@Override
	protected void onReset() {
		super.onReset();
		getView().getMainLabel().setText("Welcome "+namex +" (session_id = "+Betty_gwtp.session_id +")");
		
		
	}
}
