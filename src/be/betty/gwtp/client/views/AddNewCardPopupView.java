package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.AddNewCardPopupPresenter;
import be.betty.gwtp.client.presenters.AddNewCardPopupPresenter.MyView;

import com.gwtplatform.mvp.client.PopupViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.widget.client.TextButton;

public class AddNewCardPopupView extends PopupViewImpl implements
		AddNewCardPopupPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, AddNewCardPopupView> {
	}

	@Inject
	public AddNewCardPopupView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	@UiField DynamicForm groupDynForm;
	@UiField ListBox teacherComboBox;
	@UiField ListBox courseComboBox;
	@UiField TextButton createButton;
	@UiField TextButton cancelButton;
	
	public DynamicForm getGroupDynForm() {
		return groupDynForm;
	}
	
	public ListBox getTeacherComboBox() {
		return teacherComboBox;
	}
	
	public ListBox getCourseComboBox() {
		return courseComboBox;
	}
	
	public TextButton getCreateButton() {
		return createButton;
	}
	
	public TextButton getCancelButton() {
		return cancelButton;
	}
	
}