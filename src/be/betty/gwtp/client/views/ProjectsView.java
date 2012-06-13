package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.ProjectsPresenter;
import be.betty.gwtp.client.presenters.ProjectsPresenter.MyView;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;

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
	@UiField Label project_field;
	
	@Override
	public Label getProjectField() {
		return project_field;
	}
}
