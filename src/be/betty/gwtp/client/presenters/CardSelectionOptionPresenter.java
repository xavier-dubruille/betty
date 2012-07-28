package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.Filter_kind;
import be.betty.gwtp.client.Storage_access;
import be.betty.gwtp.client.UiConstants;
import be.betty.gwtp.client.event.CardFilterEvent;
import be.betty.gwtp.client.views.SingleCardView;

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
	
	@Inject SingleCardPresenter singleCardPresenter;

	@Override
	protected void onBind() {
		super.onBind();
		
		//init();
		
		getView().getTeacher_choice().addChangeHandler(new ChangeHandler() {
			@Override public void onChange(ChangeEvent arg0) {
				
				if (getView().getTeacher_choice().getSelectedIndex()!=0){
					getView().getGroup_choice().setVisible(true);
					printSecondComboBxView(getView().getGroup_choice(), getView().getTeacher_choice().getSelectedIndex());
					getView().getGroup_choice().setSelectedIndex(0);
					
				}else{
					getView().getGroup_choice().setVisible(false);
				}
				
			}});
		
		getView().getGroup_choice().addChangeHandler(new ChangeHandler() {
			
			@Override public void onChange(ChangeEvent arg0) {
				//myEventBus.fireEvent(new CardFilterEvent(Filter_kind.GROUP, getView().getGroup_choice().getSelectedIndex()));
				String test = getView().getGroup_choice().getItemText(getView().getGroup_choice().getSelectedIndex());
				System.out.println("nom du prof: "+test);
				
				//TODO ici je ne prend que le cas d'un prof, il faut le faire pour le reste...
				for (int i=0; i<MainPresenter.allCards.size(); i++){
					
					MainPresenter.allCards.get(i).getWidget().setVisible(false);
				}
				if (getView().getTeacher_choice().getItemText(getView().getTeacher_choice().getSelectedIndex()).equalsIgnoreCase("professor")){
					for (int i=0; i<MainPresenter.allCards.size(); i++){
						if (MainPresenter.allCards.get(i).getView().getTeacher().getText().equalsIgnoreCase(test))
							MainPresenter.allCards.get(i).getWidget().setVisible(true);
					}
					
					
				}
				
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

				
		setStaticFirstComboView(getView().getTeacher_choice());
		getView().getTeacher_choice().setSelectedIndex(0);
		if (getView().getTeacher_choice().getSelectedIndex()!=0){
			getView().getGroup_choice().setVisible(true);
			printSecondComboBxView(getView().getGroup_choice(), getView().getTeacher_choice().getSelectedIndex());
		}else{
			getView().getGroup_choice().setVisible(false);
		}
			
		/*getView().getTeacher_choice().clear();
		for (int i=0; i< Storage_access.getNumberOfTeacher(); i++)
			getView().getTeacher_choice().addItem(Storage_access.getTeacher(i));
		
		getView().getGroup_choice().clear();
		for (int i=0; i< Storage_access.getNumberOfGroup(); i++)
			getView().getGroup_choice().addItem(Storage_access.getGroup(i));
		
		*/
	}
	
	public static void setStaticFirstComboView(ListBox box) {
		box.clear();
		box.addItem("All card");
		box.addItem("Professor");
		box.addItem("Room");
		box.addItem("Group");
	
	}
	
	public static void printSecondComboBxView(ListBox box, int selectedIndex) {
		assert selectedIndex >=0 && selectedIndex<=3;
		box.clear();
		
		switch (selectedIndex) {
		case 1: 
			for (int i=0; i< Storage_access.getNumberOfTeacher(); i++){
				box.addItem(Storage_access.getTeacher(i));
			}
			break;
		case 2: 
			
			break;
		case 3: 
			for (int i=0; i< Storage_access.getNumberOfGroup(); i++){
				box.addItem(Storage_access.getGroup(i)	);
			}
			break;
		default: return;
		}
	}
	
	
}
