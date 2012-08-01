package be.betty.gwtp.client.presenters;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

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
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ibm.icu.impl.Trie2.ValueMapper;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

public class CardSelectionOptionPresenter extends PresenterWidget<CardSelectionOptionPresenter.MyView> implements ValueChangeHandler<Boolean> {

	public interface MyView extends View {

		ListBox getComboBoxFilterType();

		SimplePanel getSimplePanel();

		AbsolutePanel getFilterAbsolutePanel();

	}

	private EventBus myEventBus;
	private CheckBox myCheckBox;
	private boolean firstPass = true;
	private final SelectItem selectItemMultiplePickList = new SelectItem();
	private DynamicForm selectComboForm = new DynamicForm();
	private String[] checkBoxTab;

	@Inject
	public CardSelectionOptionPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		myEventBus = eventBus;

	}

	@Inject
	SingleCardPresenter singleCardPresenter;

	@Override
	protected void onBind() {
		super.onBind();

		// init();

		//Add a Handler to the first ComboBox
		getView().getComboBoxFilterType().addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent arg0) {

				//If not allcard selected
				if (getView().getComboBoxFilterType().getSelectedIndex() != 0) {
					printSecondComboBxView(getView().getComboBoxFilterType().getSelectedIndex());
					for (int i = 0; i < MainPresenter.allCards.size(); i++) {
						MainPresenter.allCards.get(i).getWidget().setVisible(false);
					}	
				}else {
					for (int i = 0; i < MainPresenter.allCards.size(); i++) {
						MainPresenter.allCards.get(i).getWidget().setVisible(true);
					}
					//getView().getCardFilterVerticalPanel().clear();
					selectComboForm.hide();
				}

			}
		});

		selectItemMultiplePickList.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub

				for (int i = 0; i < MainPresenter.allCards.size(); i++) {
					MainPresenter.allCards.get(i).getWidget().setVisible(false);
				}
				try{
					checkBoxTab = event.getValue().toString().split(",");
					for(int i=0; i< checkBoxTab.length; i++){
						System.out.println("dans le tableau: "+checkBoxTab[i]);
						String str = checkBoxTab[i];
						for (int j = 0; j < MainPresenter.allCards.size(); j++){
							if (getView().getComboBoxFilterType().getItemText(getView().getComboBoxFilterType().getSelectedIndex()).equalsIgnoreCase("professor")) {
								if (MainPresenter.allCards.get(j).getView().getTeacher().getText().equalsIgnoreCase(str))
									MainPresenter.allCards.get(j).getWidget().setVisible(true);
							}else if (getView().getComboBoxFilterType().getItemText(getView().getComboBoxFilterType().getSelectedIndex()).equalsIgnoreCase("Group")) {
								if (MainPresenter.allCards.get(j).getView().getGroup().getText().equalsIgnoreCase(str))
									MainPresenter.allCards.get(j).getWidget().setVisible(true);



							}
						}	
					}
				}catch (Exception E){
					System.out.println(E);
				}
			}
		});
	}

	@Override
	protected void onReset() {
		super.onReset();
	}

	/**
	 * PRE: the local storage must be filled
	 */
	public void init() {
		getView().getSimplePanel().clear();
		setStaticFirstComboView(getView().getComboBoxFilterType());
		getView().getComboBoxFilterType().setSelectedIndex(0);
		selectComboForm.setWidth(200);
	}

	public static void setStaticFirstComboView(ListBox box) {
		box.clear();
		box.addItem("All card");
		box.addItem("Professor");
		box.addItem("Group");
		box.addItem("Type");

	}

	//TODO Changer le nom de cette fonction pour que ca soit plus adapte a la nouvelle configuration (comboBox)
	public void printSecondComboBxView(int selectedIndex) {
		assert selectedIndex >= 0 && selectedIndex <= 3;
		//box.clear();
		//getView().getCardFilterVerticalPanel().clear();
		selectComboForm.setWidth(200);
		selectComboForm.clearValues();
		selectComboForm.clear();

		switch (selectedIndex) {

		case 1:
			LinkedHashMap<String, String> valueMapTeach = new LinkedHashMap<String, String>();
			for (int i = 0; i < Storage_access.getNumberOfTeacher(); i++) {
				valueMapTeach.put(Storage_access.getTeacher(i), Storage_access.getTeacher(i));
			}
			getView().getSimplePanel().clear();
			selectItemMultiplePickList.setTitle("prof");
			//selectItemMultiplePickList.setTitleStyle(titleStyle);
			selectItemMultiplePickList.setMultiple(true);  
			selectItemMultiplePickList.setMultipleAppearance(MultipleAppearance.PICKLIST);  
			selectItemMultiplePickList.setValueMap(valueMapTeach);
			selectComboForm.setItems(selectItemMultiplePickList);
			getView().getSimplePanel().add(selectComboForm);
			selectComboForm.show();
			break;

		case 2:
			LinkedHashMap<String, String> valueMapGroup = new LinkedHashMap<String, String>();
			for (int i = 0; i < Storage_access.getNumberOfGroup(); i++) {
				valueMapGroup.put(Storage_access.getGroup(i), Storage_access.getGroup(i));
			}
			getView().getSimplePanel().clear();
			selectItemMultiplePickList.setTitle("Group");
			selectItemMultiplePickList.setMultiple(true);
			selectItemMultiplePickList.setMultipleAppearance(MultipleAppearance.PICKLIST);
			selectItemMultiplePickList.setValueMap(valueMapGroup);
			selectComboForm.setItems(selectItemMultiplePickList);
			getView().getSimplePanel().add(selectComboForm);
			selectComboForm.show();
			break;

		case 3:
			getView().getSimplePanel().clear();
			selectItemMultiplePickList.setTitle("Type");
			selectItemMultiplePickList.setMultiple(true);
			selectItemMultiplePickList.setMultipleAppearance(MultipleAppearance.PICKLIST);
			selectItemMultiplePickList.setValueMap("Informatic", "group", "class");
			selectComboForm.setItems(selectItemMultiplePickList);
			getView().getSimplePanel().add(selectComboForm);
			selectComboForm.show();
			break;

		default:

			return;
		}
	}

	// TODO Ce que je fais est VRAIMENT DEGUEULASSE. Soit on parse le code xml,
	// soit on trouve un autre moyen
	// de récupérer la valeur de la checkBox. Ensuite, le reste du code est pas
	// forcement plus sexy, mais ça fonctionne comme sa
	@Override
	public void onValueChange(ValueChangeEvent<Boolean> event) {

		// Partie horible pour recuperer le nom du prof dans la comboBox
		// selectionnee
		System.out.println("event source: " + event.getSource());
		String str = event.getSource().toString();
		String spt[] = str.split("</label></span>");
		int test = spt[0].lastIndexOf(">");
		String fin = spt[0].substring(test + 1);
		// fin de la partie degueulasse

		// Le moyen de filtrer est pas forcement mieux non plus... peut mieux
		// faire
		// (mettre les valeur de la comboBox en static dans UiConstants?)
		// Le faire de facon asynchrone et ajouter les cartes petit a petit...
		//
		for (int i = 0; i < MainPresenter.allCards.size(); i++) {
			if (getView().getComboBoxFilterType().getItemText(getView().getComboBoxFilterType().getSelectedIndex()).equalsIgnoreCase("professor")) {
				if (MainPresenter.allCards.get(i).getView().getTeacher().getText().equalsIgnoreCase(fin))
					MainPresenter.allCards.get(i).getWidget().setVisible(event.getValue());
			} else {
				if (MainPresenter.allCards.get(i).getView().getGroup().getText().equalsIgnoreCase(fin))
					MainPresenter.allCards.get(i).getWidget().setVisible(event.getValue());
			}

		}
	}

}
