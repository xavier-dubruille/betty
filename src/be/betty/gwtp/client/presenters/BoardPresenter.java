package be.betty.gwtp.client.presenters;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;

public class BoardPresenter extends PresenterWidget<BoardPresenter.MyView> {

	public interface MyView extends View {
	}

	@Inject
	public BoardPresenter(final EventBus eventBus, final MyView view) {
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
}
