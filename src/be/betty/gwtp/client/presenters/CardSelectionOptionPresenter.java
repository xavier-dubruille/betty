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
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CardSelectionOptionPresenter extends
		PresenterWidget<CardSelectionOptionPresenter.MyView> implements ValueChangeHandler<Boolean> {

	public interface MyView extends View {

		ListBox getComboBoxFilterType();

		ListBox getGroup_choice();
		
		public VerticalPanel getCardFilterVerticalPanel();

	}

	private EventBus myEventBus;
	private CheckBox myCheckBox;
	private boolean firstPass = true;
	
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
		
		getView().getComboBoxFilterType().addChangeHandler(new ChangeHandler() {
			@Override public void onChange(ChangeEvent arg0) {
				
				if (getView().getComboBoxFilterType().getSelectedIndex()!=0){
					getView().getGroup_choice().setVisible(true);
					printSecondComboBxView(getView().getGroup_choice(), getView().getComboBoxFilterType().getSelectedIndex());
					getView().getGroup_choice().setSelectedIndex(0);
					for (int i=0; i<MainPresenter.allCards.size(); i++){
						MainPresenter.allCards.get(i).getWidget().setVisible(false);
					}					
				}else{
					getView().getGroup_choice().setVisible(false);
					for (int i=0; i<MainPresenter.allCards.size(); i++){
						MainPresenter.allCards.get(i).getWidget().setVisible(true);
					}
					getView().getCardFilterVerticalPanel().clear();
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
				if (getView().getComboBoxFilterType().getItemText(getView().getComboBoxFilterType().getSelectedIndex()).equalsIgnoreCase("professor")){
					for (int i=0; i<MainPresenter.allCards.size(); i++){
						if (MainPresenter.allCards.get(i).getView().getTeacher().getText().equalsIgnoreCase(test))
							MainPresenter.allCards.get(i).getWidget().setVisible(true);
					}
				}else if (getView().getComboBoxFilterType().getItemText(getView().getComboBoxFilterType().getSelectedIndex()).equalsIgnoreCase("group")){
					for (int i=0; i<MainPresenter.allCards.size(); i++){
						if (MainPresenter.allCards.get(i).getView().getGroup().getText().equalsIgnoreCase(test))
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

				
		setStaticFirstComboView(getView().getComboBoxFilterType());
		getView().getComboBoxFilterType().setSelectedIndex(0);
		if (getView().getComboBoxFilterType().getSelectedIndex()!=0){
			getView().getGroup_choice().setVisible(true);
			printSecondComboBxView(getView().getGroup_choice(), getView().getComboBoxFilterType().getSelectedIndex());
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
		box.addItem("Group");
	
	}
	
	public void printSecondComboBxView(ListBox box, int selectedIndex) {
		assert selectedIndex >=0 && selectedIndex<=3;
		box.clear();
		
		switch (selectedIndex) {
		
		case 1: 
			getView().getCardFilterVerticalPanel().clear();
			for (int i=0; i< Storage_access.getNumberOfTeacher(); i++){
				box.addItem(Storage_access.getTeacher(i));
				myCheckBox = new CheckBox();
				myCheckBox.addValueChangeHandler(this);
				myCheckBox.setText(Storage_access.getTeacher(i));
				getView().getCardFilterVerticalPanel().add(myCheckBox);				
			}
			System.out.println("NbTextBox: "+getView().getCardFilterVerticalPanel().getWidgetCount());
			System.out.println("truc de test: "+ getView().getCardFilterVerticalPanel().getWidget(2).getTitle());
			
			break;
		case 2: 
			getView().getCardFilterVerticalPanel().clear();
			for (int i=0; i< Storage_access.getNumberOfGroup(); i++){
				box.addItem(Storage_access.getGroup(i)	);
				myCheckBox = new CheckBox();
				myCheckBox.addValueChangeHandler(this);
				myCheckBox.setText(Storage_access.getGroup(i));
				getView().getCardFilterVerticalPanel().add(myCheckBox);
			}
			break;
		default: return;
		}
	}

	//TODO 	Ce que je fais est VRAIMENT DEGUEULASSE. Soit on parse le code xml, soit on trouve un autre moyen 
	//		de récupérer la valeur de la checkBox. Ensuite, le reste du code est pas forcement plus sexy, mais ça fonctionne comme sa
	@Override
	public void onValueChange(ValueChangeEvent<Boolean> event) {
		
		// Partie horible pour recuperer le nom du prof dans la comboBox selectionnee
		System.out.println("event source: "+event.getSource());
		String str = event.getSource().toString();
		String spt[] = str.split("</label></span>");		
		int test = spt[0].lastIndexOf(">");
		String fin = spt[0].substring(test+1);
		// fin de la partie degueulasse
		
		// Le moyen de filtrer est pas forcement mieux non plus... peut mieux faire
		// (mettre les valeur de la comboBox en static dans UiConstants?)
		for (int i=0; i<MainPresenter.allCards.size(); i++){
			if (getView().getComboBoxFilterType().getItemText(getView().getComboBoxFilterType().getSelectedIndex()).equalsIgnoreCase("professor")){
				if (MainPresenter.allCards.get(i).getView().getTeacher().getText().equalsIgnoreCase(fin))
					MainPresenter.allCards.get(i).getWidget().setVisible(event.getValue());
			}else{
				if (MainPresenter.allCards.get(i).getView().getGroup().getText().equalsIgnoreCase(fin))
					MainPresenter.allCards.get(i).getWidget().setVisible(event.getValue());
			}
				
		}		
	}
	
}
