package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.SingleProjectPresenter;
import be.betty.gwtp.client.presenters.SingleProjectPresenter.MyView;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;

public class SingleProjectView extends ViewImpl implements
		SingleProjectPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, SingleProjectView> {
	}

	@UiField Hyperlink project;
	@UiField Label label;
	@UiField Button deleteButton;
	
	@Inject
	public SingleProjectView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public Hyperlink getProject() {
		return project;
	}

	@Override
	public Label getLabel() {
		return label;
	}

	@Override
	public Button getDeleteButton() {
		return deleteButton;
	}

}
