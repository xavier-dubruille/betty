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

public class CardSelectionOptionPresenter extends PresenterWidget<CardSelectionOptionPresenter.MyView> {

	public interface MyView extends View {

		SimplePanel getSimplePanel();
		SimplePanel getSimplePanelFirstFilter();


	}

	private EventBus myEventBus;
	private CheckBox myCheckBox;

	private final SelectItem selectItemMultiplePickList = new SelectItem();
	private ComboBoxItem firstComboBox = new ComboBoxItem();  


	private DynamicForm multiSelectComboForm = new DynamicForm();
	private DynamicForm selectComboForm = new DynamicForm();

	private String[] checkBoxTab;

	private String indexFirstComboBox;

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


		firstComboBox.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				System.out.println(event.getValue().toString());
				System.out.println(event.getSource().toString());
				indexFirstComboBox = event.getValue().toString();
				System.out.println("*************END*************");
				if (!event.getValue().toString().equalsIgnoreCase("0")) {
					printSecondComboBxView(Integer.parseInt(event.getValue().toString()));
					for (int i = 0; i < MainPresenter.allCards.size(); i++) {
						MainPresenter.allCards.get(i).getWidget().setVisible(false);
					}	
				}else {
					for (int i = 0; i < MainPresenter.allCards.size(); i++) {
						MainPresenter.allCards.get(i).getWidget().setVisible(true);
					}
					multiSelectComboForm.hide();
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
						String str = checkBoxTab[i];
						for (int j = 0; j < MainPresenter.allCards.size(); j++){
							if (indexFirstComboBox.equalsIgnoreCase("1")) {
								if (MainPresenter.allCards.get(j).getView().getTeacher().getText().equalsIgnoreCase(str))
									MainPresenter.allCards.get(j).getWidget().setVisible(true);
							}else if (indexFirstComboBox.equalsIgnoreCase("2")) {
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
		getView().getSimplePanelFirstFilter().clear();
		setStaticFirstComboBox();
		multiSelectComboForm.setWidth(200);
	}


	private void setStaticFirstComboBox(){

		selectComboForm.setWidth(200); 
		firstComboBox.setTitle("Option");
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("0", "All card");
		valueMap.put("1", "Professor");
		valueMap.put("2","Group");
		valueMap.put("3", "Type");
		firstComboBox.setValueMap(valueMap);
		selectComboForm.setItems(firstComboBox);
		getView().getSimplePanelFirstFilter().add(selectComboForm);

	}

	//TODO Changer le nom de cette fonction pour que ca soit plus adapte a la nouvelle configuration (comboBox)
	public void printSecondComboBxView(int selectedIndex) {
		assert selectedIndex >= 0 && selectedIndex <= 3;
		
		multiSelectComboForm.setWidth(200);
		multiSelectComboForm.clearValues();
		multiSelectComboForm.clear();
		
		getView().getSimplePanel().clear();

		switch (selectedIndex) {

		case 1:
			LinkedHashMap<String, String> valueMapTeach = new LinkedHashMap<String, String>();
			for (int i = 0; i < Storage_access.getNumberOfTeacher(); i++) {
				valueMapTeach.put(Storage_access.getTeacher(i), Storage_access.getTeacher(i));
			}
			selectItemMultiplePickList.setTitle("prof");
			//selectItemMultiplePickList.setTitleStyle(titleStyle);
			selectItemMultiplePickList.setMultiple(true);  
			selectItemMultiplePickList.setMultipleAppearance(MultipleAppearance.PICKLIST);  
			selectItemMultiplePickList.setValueMap(valueMapTeach);
			multiSelectComboForm.setItems(selectItemMultiplePickList);
			getView().getSimplePanel().add(multiSelectComboForm);
			multiSelectComboForm.show();
			break;

		case 2:
			LinkedHashMap<String, String> valueMapGroup = new LinkedHashMap<String, String>();
			for (int i = 0; i < Storage_access.getNumberOfGroup(); i++) {
				valueMapGroup.put(Storage_access.getGroup(i), Storage_access.getGroup(i));
			}
			selectItemMultiplePickList.setTitle("Group");
			selectItemMultiplePickList.setMultiple(true);
			selectItemMultiplePickList.setMultipleAppearance(MultipleAppearance.PICKLIST);
			selectItemMultiplePickList.setValueMap(valueMapGroup);
			multiSelectComboForm.setItems(selectItemMultiplePickList);
			getView().getSimplePanel().add(multiSelectComboForm);
			multiSelectComboForm.show();
			break;

		case 3:
			selectItemMultiplePickList.setTitle("Type");
			selectItemMultiplePickList.setMultiple(true);
			selectItemMultiplePickList.setMultipleAppearance(MultipleAppearance.PICKLIST);
			selectItemMultiplePickList.setValueMap("Informatic", "group", "class");
			multiSelectComboForm.setItems(selectItemMultiplePickList);
			getView().getSimplePanel().add(multiSelectComboForm);
			multiSelectComboForm.show();
			break;

		default:

			return;
		}
	}
}
