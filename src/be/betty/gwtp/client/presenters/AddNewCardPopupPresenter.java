package be.betty.gwtp.client.presenters;

import java.util.LinkedHashMap;

import be.betty.gwtp.client.Storage_access;
import be.betty.gwtp.client.action.CreateNewCardAction;
import be.betty.gwtp.client.action.CreateNewCardActionResult;
import be.betty.gwtp.client.action.SubscribeUserAction;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.widget.client.TextButton;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
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
 	private String[] checkBoxTab;

 	@Inject MainPresenter mainPresenter;
 	
	@Inject
	public AddNewCardPopupPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Inject NewCardSuccessPopupPresenter newCardSuccessPopup;
	@Inject DispatchAsync dispacher;
	
	@Override
	protected void onBind() {
		super.onBind();
		createComboBox();
		
		getView().getCourseComboBox().addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
			}
		});
		
		getView().getCancelButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				getView().hide();
			}
		});
		
		getView().getCreateButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				String courseId = Storage_access.getCourseBDDID(""+getView().getCourseComboBox().getSelectedIndex());
				String teacherId = Storage_access.getTeacherBddId(getView().getTeacherComboBox().getSelectedIndex());
				String[] checkBoxTab = groupComboBox.getValues();
				String projectId = Storage_access.getProjectCurrentID();
				String sem = Storage_access.getSemester();
				int[] GroupId = new int[checkBoxTab.length];
				for (int i = 0; i < checkBoxTab.length; i++){
					int id = Integer.parseInt(checkBoxTab[i]);
					GroupId[i] = Storage_access.getGroupBddID(id);
				}
	
				CreateNewCardAction action = new CreateNewCardAction(teacherId, courseId, projectId, sem, GroupId);
				dispacher.execute(action, newCardCallback);
			}
		});
	}
	
	private AsyncCallback<CreateNewCardActionResult> newCardCallback = new AsyncCallback<CreateNewCardActionResult>() {

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(CreateNewCardActionResult result) {
			getView().hide();
			mainPresenter.onReset();
			
			
		}
	};
	
	@Override
	protected void onReveal() {
		// TODO Auto-generated method stub
		super.onReveal();
		populateComboBox();
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
		
		//System.out.println("on populate la combobox");
		for (int i = 0; i < Storage_access.getNumberOfTeacher(); i++) {
			getView().getTeacherComboBox().addItem(Storage_access.getTeacher(i), ""+i);
		}
		
		for (int i = 0; i < Storage_access.getNumberOfCourses(); i++) {
			getView().getCourseComboBox().addItem(Storage_access.getCourseName(""+i), ""+i);
		}
		
	}
	
}
