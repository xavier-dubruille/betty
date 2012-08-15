package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.ClientUtils;
import be.betty.gwtp.client.Storage_access;
import be.betty.gwtp.client.UiConstants;
import be.betty.gwtp.client.action.SolveIt;
import be.betty.gwtp.client.action.SolveItResult;
import be.betty.gwtp.client.event.InstancesModifiedEvent;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.PopupView;
import com.google.inject.Inject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.widget.client.TextButton;

public class SolveItPopupPresenter extends
PresenterWidget<SolveItPopupPresenter.MyView> {

	public interface MyView extends PopupView {
		TextButton getCancelButton();
		TextButton getCalculeButton();
		TextButton getCloseWindowButton();
		ListBox getInstance_choice();
		ListBox getTimeLimit();
	}

	@Inject DispatchAsync dispatcher;
	private EventBus eventBus;

	@Inject
	public SolveItPopupPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		this.eventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();

		// CancelButton handler
		getView().getCancelButton().addClickHandler(new ClickHandler() {
			@Override public void onClick(ClickEvent arg0) {
				getView().hide();
			}
		});
		
		getView().getCloseWindowButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getView().hide();
			}
		});

		// CalculeButton handler
		getView().getCalculeButton().addClickHandler(new ClickHandler() {
			@Override public void onClick(ClickEvent arg0) {
				ClientUtils.notifyUser("The Solver has Started ... ",UiConstants.getNotifCss(), getEventBus());
				getView().hide();
				dispatcher.execute(new SolveIt(Storage_access.getCurrentProjectInstanceBDDID(), -1),
						new AsyncCallback<SolveItResult>() {

					@Override public void onFailure(Throwable arg0) {
						ClientUtils.actionFailed("SolveIt");
						ClientUtils.notifyUser("Action failed", UiConstants.getNotifCssError(), getEventBus());
					}

					@Override public void onSuccess(SolveItResult result) {
						ClientUtils.notifyUser("Solving is over. (available in instance Num "+result.getInstanceNum()+")", UiConstants.getNotifCss(), getEventBus());
						ClientUtils.notifyUser("Post Info are: "+result.getSoluceInfo(), UiConstants.getNotifCss(), getEventBus());
						// TODO: faut aussi rajouter la nouvelle instance si il y a,
						// ou recharger la page, si c'est l'instance courante..
						if (true) {// in case of new instance, we add it
							eventBus.fireEvent( new InstancesModifiedEvent());
						}
						
					}

				});
			}
		});

		// Construct Instance ComboList
		getView().getInstance_choice().addItem("Nouvelle Instance");
		getView().getInstance_choice().setEnabled(false);

		// Construct TimeLimit ComboList
		getView().getTimeLimit().addItem("Aucune (deconseille)");
		getView().getTimeLimit().setSelectedIndex(0); //inutile :p
		getView().getTimeLimit().setEnabled(false);
	}

	@Override
	protected void onReset() {
		super.onReset();
	}
}
