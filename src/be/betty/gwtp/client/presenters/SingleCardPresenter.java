package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.Storage_access;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SingleCardPresenter extends
		PresenterWidget<SingleCardPresenter.MyView> {

	public interface MyView extends View {

		VerticalPanel getVerticalPanel();

		HTML getHeader();
	}

	@Inject
	public SingleCardPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	public void init(int myI) {
		getView().getHeader().setText(Storage_access.getCard(myI));

	}
}
