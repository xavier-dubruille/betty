package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.Betty_gwtp;
import be.betty.gwtp.client.action.LoginAction;
import be.betty.gwtp.client.action.LoginActionResult;
import be.betty.gwtp.client.place.NameTokens;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
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
		public Label getLogin_label() ;
		public TextBox getLogin_textbox() ;
		public Button getLogin_send() ;
		public TextBox getPwd_textbox();
		public Label getWrongPwd_label();
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

	@Inject	PlaceManager placeManager;
	@Inject DispatchAsync dispatch;
	
	@Override
	protected void onBind() {
		super.onBind();
	}

	
	@Override
	protected void onReset() {
		super.onReset();
		
		if (stockStore != null && stockStore.getItem("session_id") != null) 
			placeManager.revealPlace(new PlaceRequest(NameTokens.projects));
		
		getView().getLogin_textbox().setText("Admin");
		getView().getWrongPwd_label().setText("");
		getView().getLogin_send().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getView().getWrongPwd_label().setText(".. Be patient, I'm checkin' ..");
				
				String login = getView().getLogin_textbox().getText();
				if (stockStore != null) {stockStore.setItem("login", login);} //pk j'ai fais ca ?
				String pwd = getView().getPwd_textbox().getText();
				LoginAction action = new LoginAction(login, pwd);
				dispatch.execute(action, loginCallback);
				
			}
			
		});
	}

	private AsyncCallback<LoginActionResult> loginCallback = new AsyncCallback<LoginActionResult>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(LoginActionResult result) {
			String session_id = result.getSession_id();
			
			//System.out.println("client side: sessid= "+session_id);
			if (session_id != null) {
				if (stockStore != null) { stockStore.setItem("session_id", session_id);}
				// PlaceRequest request = new PlaceRequest(NameTokens.main).with("name", user);
				PlaceRequest request = new PlaceRequest(NameTokens.projects);
				placeManager.revealPlace(request);
			}
			else {
				getView().getWrongPwd_label().setText("Wrong Password (hint: try Admin admin)");
			}
			
		}
		
	};

}
