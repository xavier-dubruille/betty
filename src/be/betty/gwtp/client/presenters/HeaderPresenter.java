package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.place.NameTokens;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.google.inject.Inject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class HeaderPresenter extends
		Presenter<HeaderPresenter.MyView, HeaderPresenter.MyProxy> {

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_CONTENT = new Type<RevealContentHandler<?>>();
	
	public interface MyView extends View {

		Button getDeco();
		public MenuBar getHelpMenuBar();
		MenuItem getCalculeMenu();
		
	}

	@ProxyCodeSplit
	public interface MyProxy extends Proxy<HeaderPresenter> {
	}

	private Storage stockStore;
	@Inject	PlaceManager placeManager;
	
	@Inject AboutUsPresenter aboutUsPresenter;
	@Inject SolveItPopupPresenter solveItPresenter;

	@Inject
	public HeaderPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
		stockStore = Storage.getLocalStorageIfSupported();
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}
	
	//Create something to do when we click on the aboutUs MenuItem
	Command aboutUsCommand = new Command(){
		public void execute(){
			addToPopupSlot(aboutUsPresenter);
		}
	};
	
	Command solveItCommand = new Command(){
		public void execute(){
			addToPopupSlot(solveItPresenter);
		}
	};

	@Override
	protected void onBind() {
		super.onBind();
		
		getView().getDeco().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (stockStore != null) {
					stockStore.removeItem("login");
					stockStore.removeItem("session_id");
					placeManager.revealPlace(new PlaceRequest(NameTokens.login));
				}
				
			}
		});
		
		// ------ Menu Bar ----
		// Create some new MenuItem and add commands
		MenuItem about = new MenuItem("About us", aboutUsCommand);	
		//MenuItem solveIt = new MenuItem("Sovle it", solveItCommand);
		
		//add the new items to the specific bar
		getView().getHelpMenuBar().addItem(about);
		getView().getCalculeMenu().setCommand(solveItCommand);
			
	}

 	@Override
	protected void onReset() {
		super.onReset();
	}
}
