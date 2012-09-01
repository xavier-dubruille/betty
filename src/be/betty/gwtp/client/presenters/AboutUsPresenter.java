package be.betty.gwtp.client.presenters;

import be.betty.gwtp.shared.Constants;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.PopupView;
import com.google.inject.Inject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.widget.client.TextButton;

/**
 * Presenter for the popup called "about"
 *
 */
public class AboutUsPresenter extends PresenterWidget<AboutUsPresenter.MyView> {

	public interface MyView extends PopupView {
		// TODO Put your view methods here
		public TextButton getOkButton();
		public TextButton getCloseWindowButton();
		public Label getAboutUsLabel();
	}

	@Inject
	public AboutUsPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
		
		getView().getOkButton().addClickHandler(new ClickHandler() {
			@Override public void onClick(ClickEvent event) {
				getView().hide();
			}
		});
		
		getView().getCloseWindowButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getView().hide();
			}
		});
		
		getView().getAboutUsLabel().setText("Copyright © 2012 Free Betty Software\nJACOBY Kevin - DUBRUILLE Xavier\n\nVersion "+Constants.VERSION+"\nThis application is under GPL3 Licence");
		
	}
}
