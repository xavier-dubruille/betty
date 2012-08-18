package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.place.NameTokens;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.google.inject.Inject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.widget.client.TextButton;

public class PopupSubscribeRedirectPresenter extends
		PresenterWidget<PopupSubscribeRedirectPresenter.MyView> {

	public interface MyView extends PopupView {
		// TODO Put your view methods here
		
		public TextButton getButtonCloseWindow();

		public Label getLabel();

		public TextButton getButtonOk();
	}
	
	private int error;

	@Inject
	public PopupSubscribeRedirectPresenter(final EventBus eventBus,
			final MyView view) {
		super(eventBus, view);
	}
	
	@Inject PlaceManager placeManager;

	@Override
	protected void onBind() {
		super.onBind();
		
		getView().getButtonCloseWindow().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				getView().hide();
				if(error==0){
				placeManager.revealPlace(new PlaceRequest(NameTokens.login));
				}
				
			}
		});
		
		getView().getButtonOk().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getView().hide();
				if(error == 0){
				placeManager.revealPlace(new PlaceRequest(NameTokens.login));
				}
			}
		});
		
	}
	
	public void init(String text, int error){
		getView().getLabel().setText(text);
		this.error = error;
	}
}
