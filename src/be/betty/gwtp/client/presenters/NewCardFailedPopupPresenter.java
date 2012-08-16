package be.betty.gwtp.client.presenters;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.PopupView;
import com.google.inject.Inject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.widget.client.TextButton;

public class NewCardFailedPopupPresenter extends
		PresenterWidget<NewCardFailedPopupPresenter.MyView> {

	public interface MyView extends PopupView {
		
		public TextButton getButtonCloseWindow();
		public TextButton getButtonOk();
	}

	@Inject
	public NewCardFailedPopupPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		
	}

	@Override
	protected void onBind() {
		super.onBind();
		
		getView().getButtonOk().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getView().hide();
			}
		});
		
		getView().getButtonCloseWindow().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				getView().hide();
			}
		});
	}
}
