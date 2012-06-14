package be.betty.gwtp.client.presenters;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;

public class SingleProjectPresenter extends
		PresenterWidget<SingleProjectPresenter.MyView> {


	
	public interface MyView extends View {

		public Hyperlink getProject();
		public Label getLabel();
		public Button getDeleteButton();
	}

	@Inject
	public SingleProjectPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	public void init(String name) {
		getView().getProject().setText(name);
		
	}
}
