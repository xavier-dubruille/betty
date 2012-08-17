package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.Storage_access;
import be.betty.gwtp.client.event.DeleteCardEvent;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.PopupView;
import com.google.inject.Inject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.widget.client.TextButton;

public class PopupDeleteCardPresenter extends
PresenterWidget<PopupDeleteCardPresenter.MyView> {

	public interface MyView extends PopupView {

		public TextButton getButtonYes();

		public TextButton getButtonNo();

		public TextButton getButtonCloseWindow();

		public Label getLabel();

	}

	private int cardId;
	private EventBus myEventBus;

	@Inject
	public PopupDeleteCardPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);

		myEventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();

		getView().getButtonYes().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				myEventBus.fireEvent(new DeleteCardEvent(Storage_access.getSessId(), cardId));
				getView().hide();
			}
		});
		
		getView().getButtonNo().addClickHandler(new ClickHandler() {

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

	public void init(String text, int cardId){
		getView().getLabel().setText(text+cardId);
		this.cardId = cardId;


	}
}
