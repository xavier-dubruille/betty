package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.Filter_kind;
import be.betty.gwtp.client.Storage_access;
import be.betty.gwtp.client.event.CardFilterEvent;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.google.inject.Inject;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.ListBox;

public class CardSelectionOptionPresenter extends
		PresenterWidget<CardSelectionOptionPresenter.MyView> {

	public interface MyView extends View {

		ListBox getTeacher_choice();

		ListBox getGroup_choice();

	}

	private EventBus myEventBus;
	
	@Inject
	public CardSelectionOptionPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		myEventBus = eventBus;
		
	}

	@Override
	protected void onBind() {
		super.onBind();
		
		getView().getTeacher_choice().addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent arg0) {
				myEventBus.fireEvent(new CardFilterEvent(Filter_kind.TEACHER, getView().getTeacher_choice().getSelectedIndex()));
			}});
		
		getView().getGroup_choice().addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent arg0) {
				myEventBus.fireEvent(new CardFilterEvent(Filter_kind.GROUP, getView().getGroup_choice().getSelectedIndex()));
			}});
		}

	@Override
	protected void onReset() {
		super.onReset();
	}

	/**
	 * PRE: the local storage must be filled
	 */
	public void init() {

		
		getView().getTeacher_choice().clear();
		for (int i=0; i< Storage_access.getNumberOfTeacher(); i++)
			getView().getTeacher_choice().addItem(Storage_access.getTeacher(i));
		
		getView().getGroup_choice().clear();
		for (int i=0; i< Storage_access.getNumberOfGroup(); i++)
			getView().getGroup_choice().addItem(Storage_access.getGroup(i));
		
		
	}
}
