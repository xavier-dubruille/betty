/**
 * test pour xaxa
 */

package be.betty.gwtp.client.presenters;


import be.betty.gwtp.client.ClientUtils;
import be.betty.gwtp.client.action.LoginAction;
import be.betty.gwtp.client.action.LoginActionResult;
import be.betty.gwtp.client.action.SubscribeAction;
import be.betty.gwtp.client.action.SubscribeActionResult;
import be.betty.gwtp.client.action.SubscribeUserAction;
import be.betty.gwtp.client.action.SubscribeUserActionResult;
import be.betty.gwtp.client.place.NameTokens;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.widget.client.TextButton;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class SubscribePresenter extends Presenter<SubscribePresenter.MyView, SubscribePresenter.MyProxy> {

	public interface MyView extends View {

		public AbsolutePanel getAbsoluteSubscribePanel();

		public DockPanel getDockSubscribePanel();

		public Label getEmailSubLabel();

		public TextBox getUserSubTextbox();

		public PasswordTextBox getPassSubTextbox();

		public PasswordTextBox getPassVerSubTextbox();

		public TextBox getEmailSubTextbox();

		public Label getUserSubErrorLabel();

		public Label getPassSubErrorLabel();

		public Label getPassVerSubErrorLabel();

		public Label getEmailSubErrorLabel();

		public Image getUsernamePicture();

		public Image getPasswordPicture();

		public Image getPasswordVerPicture();

		public Image getEmailPicture();

		public TextButton getButtonSubscribe();
		
		public Hyperlink getHyperlinkLogin();

	}




	@ProxyCodeSplit
	@NameToken(NameTokens.subscribe)
	public interface MyProxy extends ProxyPlace<SubscribePresenter> {
	}

	@Inject
	public SubscribePresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Inject DispatchAsync dispacher;
	@Inject PopupSubscribeRedirectPresenter SubscribeRedirectPresenter;

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	@Override
	protected void onBind() {

		super.onBind();
		
		Image image = new Image("arrowLeft.png");
		getView().getHyperlinkLogin().getElement().appendChild(image.getElement());
		image.addStyleName("clickable");
		getView().getHyperlinkLogin().setTitle("Login");
		
		getView().getUsernamePicture().setVisible(false);
		getView().getPasswordVerPicture().setVisible(false);
		getView().getPasswordPicture().setVisible(false);
		getView().getEmailPicture().setVisible(false);
		//TODO Vérifier si le nom d'utilisateur n'existe pas dans la bdd
		getView().getUserSubTextbox().addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				//EXEMPLE
				//				if(!getView().getUserSubTextbox().getText().isEmpty()){
				//					if (getView().getUserSubTextbox().getText() n'est pas dans la base de donnée){
				//						getView().getUserSubErrorLabel().setText("");
				//						getView().getUsernamePicture().setUrl("ok.png");
				//						getView().getUsernamePicture().setVisible(true);
				//
				//					}else{
				//						getView().getUserSubErrorLabel().setText("Already exist");
				//						getView().getUsernamePicture().setUrl("cancel.png");
				//						getView().getUsernamePicture().setVisible(true);
				//					}
				//				}else{
				//					getView().getUserSubErrorLabel().setText("");
				//					getView().getUsernamePicture().setVisible(false);
				//				}
			}
		});
		
		getView().getUserSubTextbox().addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
				fireUsername();
			}
		});

		getView().getEmailSubTextbox().addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				if(!getView().getEmailSubTextbox().getText().isEmpty()){
					if (ClientUtils.CheckEmail(getView().getEmailSubTextbox().getText().toString())){
						getView().getEmailSubErrorLabel().setText("");
						getView().getEmailPicture().setUrl("ok.png");
						getView().getEmailPicture().setVisible(true);

					}else{
						getView().getEmailSubErrorLabel().setText("not valid");
						getView().getEmailPicture().setUrl("cancel.png");
						getView().getEmailPicture().setVisible(true);
					}
				}else{
					getView().getEmailSubErrorLabel().setText("");
					getView().getEmailPicture().setVisible(false);
				}
			}
		});

		getView().getPassSubTextbox().addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				if(!getView().getPassSubTextbox().getText().isEmpty()){
					if(getView().getPassVerSubTextbox().getText().isEmpty()){
						getView().getPassVerSubErrorLabel().setText("complete me");
						getView().getPasswordVerPicture().setUrl("cancel.png");
						getView().getPasswordVerPicture().setVisible(true);
					}else{
						if(!getView().getPassSubTextbox().getText().equals(getView().getPassVerSubTextbox().getText())){
							getView().getPassVerSubErrorLabel().setText("wrong password");
							getView().getPasswordVerPicture().setUrl("cancel.png");
							getView().getPasswordVerPicture().setVisible(true);
						}else{
							getView().getPassVerSubErrorLabel().setText("");
							getView().getPasswordVerPicture().setUrl("ok.png");
							getView().getPasswordVerPicture().setVisible(true);
						}
					}
					getView().getPassSubErrorLabel().setText("");

				}else{
					getView().getPassVerSubErrorLabel().setText("");
					getView().getPasswordVerPicture().setVisible(false);
				}


			}
		});

		getView().getPassVerSubTextbox().addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				if(!getView().getPassSubTextbox().getText().isEmpty()){
					if (!getView().getPassVerSubTextbox().getText().toString().equals(getView().getPassSubTextbox().getText().toString())){
						getView().getPassVerSubErrorLabel().setText("wrong password");
						getView().getPasswordVerPicture().setUrl("cancel.png");
						getView().getPasswordPicture().setVisible(true);
					}else{
						getView().getPasswordVerPicture().setUrl("ok.png");
						getView().getPassVerSubErrorLabel().setText("");
					}
				}else{
					getView().getPasswordVerPicture().setVisible(false);
					getView().getPassVerSubErrorLabel().setText("");
				}


			}
		});

	}

	public void errorCompleteField(boolean bool, Label lb, Image img) {
		if (bool) {
			lb.setText("Complete me");
			img.setUrl("cancel.png");
			img.setVisible(true);
		}
	}

	public void errorSameStr(String pass, String passVer, Label lb) {
		if (!pass.isEmpty() || !passVer.isEmpty()) {
			if (!pass.equals(passVer)) {
				lb.setText("Not the same");
			}
		}

	}

	
	public void fireUsername(){
		
		getView().getUsernamePicture().setUrl("LoadingSubscribe");
		getView().getUsernamePicture().setVisible(true);
		getView().getUserSubErrorLabel().setText("Checking");

		String username = getView().getUserSubTextbox().getText();
		
		SubscribeUserAction action = new SubscribeUserAction(username);
		dispacher.execute(action, SubscribeUserCallback);
	}
	
	private AsyncCallback<SubscribeUserActionResult> SubscribeUserCallback = new AsyncCallback<SubscribeUserActionResult>() {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("pas possible d'effectuer l'action.. la raison est : "+caught.getMessage()+"***** et le toString:"
					+caught.toString() );
			
			getView().getUserSubErrorLabel().setText("Connexion problem");
			getView().getUsernamePicture().setUrl("cancel.png");
			getView().getUsernamePicture().setVisible(true);

		}

		@Override
		public void onSuccess(SubscribeUserActionResult result) {
			boolean userExist = result.getUserExist();

			// System.out.println("client side: sessid= "+session_id);
			if (!userExist) {
				getView().getUserSubErrorLabel().setText("");
				getView().getUsernamePicture().setUrl("ok.png");
				getView().getUsernamePicture().setVisible(true);

				//getView()
				
				// PlaceRequest request = new
				// PlaceRequest(NameTokens.main).with("name", user);
				
			} else {
				getView().getUserSubErrorLabel().setText("already taken");
				getView().getUsernamePicture().setUrl("cancel.png");
				getView().getUsernamePicture().setVisible(true);
			}

		}

	};

	@Override
	protected void onReset() {
		super.onReset();

		getView().getButtonSubscribe().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				errorCompleteField(	getView().getUserSubTextbox().getText().isEmpty(), 
									getView().getUserSubErrorLabel(),
									getView().getUsernamePicture());
				errorCompleteField(	getView().getPassSubTextbox().getText().isEmpty(), 
									getView().getPassSubErrorLabel(),
									getView().getPasswordPicture());
				errorCompleteField(	getView().getPassVerSubTextbox().getText().isEmpty(), 
									getView().getPassVerSubErrorLabel(),
									getView().getPasswordVerPicture());
				errorCompleteField(	getView().getEmailSubTextbox().getText().isEmpty(), 
									getView().getEmailSubErrorLabel(),
									getView().getEmailPicture());
				/*
				errorSameStr(getView().getPassSubTextbox().getText(), getView().getPassVerSubTextbox().getText(), getView().getPassVerSubErrorLabel());
				 */

				if(
						getView().getUserSubErrorLabel().getText().isEmpty() &&
						getView().getPassSubErrorLabel().getText().isEmpty() &&
						getView().getPassVerSubErrorLabel().getText().isEmpty() &&
						getView().getEmailSubErrorLabel().getText().isEmpty() 
						){

					String login = getView().getUserSubTextbox().getText();
					String pwd = getView().getPassSubTextbox().getText();
					String email = getView().getEmailSubTextbox().getText();
					SubscribeAction action = new SubscribeAction(login, pwd, email);

					dispacher.execute(action , new AsyncCallback<SubscribeActionResult>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							SubscribeRedirectPresenter.init("Failure, try again", 1);
							addToPopupSlot(SubscribeRedirectPresenter);

						}

						@Override
						public void onSuccess(SubscribeActionResult result) {
							// TODO Auto-generated method stub
							SubscribeRedirectPresenter.init("success. you will now be redirect on login page. Click Ok to finish", 0);
							addToPopupSlot(SubscribeRedirectPresenter);

						}
					});
					//System.out.println("plus d'erreur");				


				}else{
					//System.out.println("encore des erreurs");
				}
			}
		});
	}
}
