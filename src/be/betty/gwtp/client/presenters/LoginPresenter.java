package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.Storage_access;
import be.betty.gwtp.client.action.LoginAction;
import be.betty.gwtp.client.action.LoginActionResult;
import be.betty.gwtp.client.place.NameTokens;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.widget.client.TextButton;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class LoginPresenter extends
		Presenter<LoginPresenter.MyView, LoginPresenter.MyProxy> {

	public interface MyView extends View {
		public Label getLogin_label();
		public TextBox getLogin_textbox();
		public TextBox getPwd_textbox();
		public Label getWrongPwd_label();
		public HTMLPanel getHtml_panel();
		public AbsolutePanel getAbsolute_panel();
		public DockPanel getDock_panel();
		public Image getLoadingPicture();
		public TextButton getButtonLogin();
		//public Image getImageGoSubscribe();
		public Hyperlink getHyperlinkSubscribe();
		Hyperlink getCacheProject();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.login)
	public interface MyProxy extends ProxyPlace<LoginPresenter> {
	}

	private Storage stockStore;

	@Inject
	public LoginPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
		stockStore = Storage.getLocalStorageIfSupported();

	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	@Inject PlaceManager placeManager;
	@Inject DispatchAsync dispatch;

	@Override
	protected void onBind() {
		super.onBind();
		
		Image image = new Image("arrowRight.png");
		getView().getHyperlinkSubscribe().getElement().appendChild(image.getElement());
		image.addStyleName("clickable");
		getView().getHyperlinkSubscribe().setTitle("Subscribe");
		
		//getView().getImageGoSubscribe().addStyleName("clickable");
		
		//getView().getDock_panel().setWidth(width);
		//getView().getDock_panel().setHeight(height);
		getView().getLoadingPicture().setVisible(false);
		if (stockStore != null){
			if(stockStore.getItem("session_id") != null){
				System.out.println(stockStore.getItem("session_id").toString());
				//placeManager.revealPlace(new PlaceRequest(NameTokens.projects));	
			}
		}
			

		getView().getLogin_textbox().setText("jack");
		getView().getWrongPwd_label().setText("");
		
		// Add "EnterKey" handler on login textBox
		getView().getLogin_textbox().addKeyDownHandler(new KeyDownHandler() {
			@Override public void onKeyDown(KeyDownEvent event) {
			      if(KeyCodes.KEY_ENTER == event.getNativeKeyCode())
			    	  fireLogin();
			}});
		
		// Add "EnterKey" handler on Pwd textBox
		getView().getPwd_textbox().addKeyDownHandler(new KeyDownHandler() {
			@Override public void onKeyDown(KeyDownEvent event) {
			      if(KeyCodes.KEY_ENTER == event.getNativeKeyCode())
			    	  fireLogin();
			}});
		
		// Add clickHandler
		getView().getButtonLogin().addClickHandler(new ClickHandler() {
			@Override public void onClick(ClickEvent event) {
				fireLogin();
			}});
		
	        
		
	}

	private void fireLogin() {
		getView().getLoadingPicture().setVisible(true);
		getView().getWrongPwd_label().setText(
				"Connexion");

		String login = getView().getLogin_textbox().getText();
		if (stockStore != null) {
			stockStore.setItem("login", login);
		} // pk j'ai fais ca ?
		String pwd = getView().getPwd_textbox().getText();
		LoginAction action = new LoginAction(login, pwd);
		dispatch.execute(action, loginCallback);
		
	}
	
	@Override
	protected void onReset() {
		super.onReset();

		getView().getWrongPwd_label().setText("");
		getView().getLoadingPicture().setVisible(false);
    	getView().getLogin_textbox().setFocus(true);
    	
    	getView().getCacheProject().setText("");
    	if (Storage_access.getProjectName() != null) {
    		getView().getCacheProject().setText("Vous avez un projet en cours, voulez vous le reprendre? "+Storage_access.getProjectName());
    		getView().getCacheProject().setTargetHistoryToken("main;p="+Storage_access.getSavedProject()+";s="+Storage_access.getSemester());
    	}
    		//getView().getWrongPwd_label().setText("Vous avez un projet en cours, voulez vous le reprendre? "+Storage_access.getProjectName());
	}

	
	private AsyncCallback<LoginActionResult> loginCallback = new AsyncCallback<LoginActionResult>() {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("pas possible d'effectuer l'action.. la raison est : "+caught.getMessage()+"***** et le toString:"
					+caught.toString() );
			getView().getLoadingPicture().setVisible(false);
		}

		@Override
		public void onSuccess(LoginActionResult result) {
			String session_id = result.getSession_id();

			// System.out.println("client side: sessid= "+session_id);
			if (session_id != null) {
				if (stockStore != null) {
					stockStore.setItem("session_id", session_id);
				}
				// PlaceRequest request = new
				// PlaceRequest(NameTokens.main).with("name", user);
				if (stockStore != null) {
				}
				PlaceRequest request = new PlaceRequest(NameTokens.projects);
				placeManager.revealPlace(request);
			} else {
				getView().getLoadingPicture().setVisible(false);
				getView().getWrongPwd_label().setText(
						"Wrong Password");
			}

		}

	};

}
