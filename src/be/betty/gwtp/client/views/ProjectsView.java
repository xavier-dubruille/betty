package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.ProjectsPresenter;
import be.betty.gwtp.client.presenters.ProjectsPresenter.MyView;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Image;

public class ProjectsView extends ViewImpl implements ProjectsPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, ProjectsView> {
	}

	@Inject
	public ProjectsView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	@UiField HTMLPanel project_field;
	@UiField Label info_label;
	@UiField Button new_project;
	@UiField Image loadingPicutre;
	
	@Override
	public HTMLPanel getProjectField() {
		return project_field;
	}
	
	
	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == ProjectsPresenter.SLOT_project) {
			project_field.clear();
			if (content != null)
				project_field.add(content);
		}
		else {
			super.setInSlot(slot, content);
		}
	}
	
	@Override
	public void addToSlot(Object slot, Widget content) {
		if (slot == ProjectsPresenter.SLOT_project)
			if (content != null)
				project_field.add(content);
		else 
			super.addToSlot(slot, content);
		
	}

	public Label getInfo_label() {
		return info_label;
	}

	@Override
	public Button getNew_project() {
		return new_project;
	}
	
	public Image getLoadingPicture(){
		return loadingPicutre;
	}
	
}
