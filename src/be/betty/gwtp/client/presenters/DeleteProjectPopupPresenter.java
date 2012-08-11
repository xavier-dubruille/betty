package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.event.PopupAreYouSureEvent;
import be.betty.gwtp.client.event.ShowPlacedCardEvent;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.PopupView;
import com.google.inject.Inject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.widget.client.TextButton;

public class DeleteProjectPopupPresenter extends
		PresenterWidget<DeleteProjectPopupPresenter.MyView> {

	public interface MyView extends PopupView {
		
		public TextButton getButtonYes();
		public TextButton getButtonNo();
		public TextButton getButtonCloseBar();
	}
	
	private EventBus myEventBus;
	private boolean yesIsChek = false;

	
	@Inject
	public DeleteProjectPopupPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		myEventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();

		getView().getButtonYes().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getEventBus().fireEvent(new PopupAreYouSureEvent(true));
			}
		});
		
		getView().getButtonNo().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				getEventBus().fireEvent(new PopupAreYouSureEvent(false));
			}
		});
		
		getView().getButtonCloseBar().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getEventBus().fireEvent(new PopupAreYouSureEvent(false));
				getView().hide();
			}
		});
		
	}
}
