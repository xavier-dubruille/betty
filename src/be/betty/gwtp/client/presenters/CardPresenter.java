package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.Storage_access;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;

public class CardPresenter extends PresenterWidget<CardPresenter.MyView> {

	public interface MyView extends View {

		Label getMainLabel();

		void setMainLabel(Label mainLabel);

		HTMLPanel getWholePanel();
	}

	@Inject
	public CardPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	@Override
	protected void onReset() {
		super.onReset();
	}
	
	

	public void init(int myI) {
		getView().getMainLabel().setText(Storage_access.getCard(myI));

	}
}
