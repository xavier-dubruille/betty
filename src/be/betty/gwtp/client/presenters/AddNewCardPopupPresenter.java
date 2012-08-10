package be.betty.gwtp.client.presenters;

import java.util.LinkedHashMap;

import be.betty.gwtp.client.Storage_access;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.widget.client.TextButton;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;

public class AddNewCardPopupPresenter extends
		PresenterWidget<AddNewCardPopupPresenter.MyView> {

	public interface MyView extends PopupView {

		public DynamicForm getGroupDynForm();
		public ListBox getCourseComboBox();
		public ListBox getTeacherComboBox();
		public TextButton getCreateButton();
		public TextButton getCancelButton();
		
	}
	
 	private SelectItem groupComboBox;
 	

	@Inject
	public AddNewCardPopupPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
		
		createComboBox();
		populateComboBox();
		
		getView().getCourseComboBox().addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
				System.out.println("Cours sélectionner dans popup: "+getView().getCourseComboBox().getItemText(getView().getCourseComboBox().getSelectedIndex()));
			}
		});
		
		getView().getCancelButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				getView().hide();
			}
		});
	}
	
	public void createComboBox() {
		
		groupComboBox = new SelectItem();
		groupComboBox.setWidth(80);
		getView().getGroupDynForm().setItems(groupComboBox);
		
	}
	
	public void populateComboBox(){
		
		// FOR GROUP
		LinkedHashMap<String, String> valueMapGroup = new LinkedHashMap<String, String>();
		for (int i = 0; i < Storage_access.getNumberOfGroup(); i++) {
			valueMapGroup.put(""+i, Storage_access.getGroup(i));
		}
		groupComboBox.setTitle("");
		groupComboBox.setMultiple(true);
		groupComboBox.setMultipleAppearance(MultipleAppearance.PICKLIST);
		groupComboBox.setValueMap(valueMapGroup);
		getView().getGroupDynForm().setItems(groupComboBox);
		
		for (int i = 0; i < Storage_access.getNumberOfTeacher(); i++) {
			getView().getTeacherComboBox().addItem(Storage_access.getTeacher(i), ""+i);
		}
		
		for (int i = 0; i < Storage_access.getNumberOfCourses(); i++) {
			getView().getCourseComboBox().addItem(Storage_access.getCourse(i), ""+i);
		}
		
	}
	
}
