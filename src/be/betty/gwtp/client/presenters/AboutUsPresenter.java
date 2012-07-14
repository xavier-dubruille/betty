package be.betty.gwtp.client.presenters;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.PopupView;
import com.google.inject.Inject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Button;

public class AboutUsPresenter extends PresenterWidget<AboutUsPresenter.MyView> {

	public interface MyView extends PopupView {
		// TODO Put your view methods here
		public Button getOkButton();
	}

	@Inject
	public AboutUsPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
		
		getView().getOkButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				getView().hide();
				
			}
		});
	}
}
