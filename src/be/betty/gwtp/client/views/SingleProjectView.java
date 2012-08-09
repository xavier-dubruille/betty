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
import com.google.gwt.user.client.ui.Image;

public class SingleProjectView extends ViewImpl implements
		SingleProjectPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, SingleProjectView> {
	}

	@UiField Hyperlink linkProjectNameSem1;
	@UiField Hyperlink linkProjectNameSem2;
	@UiField Label labelProjectName;
	@UiField Image imageDelete;
	@UiField Image imageSettings;
	
	@Inject
	public SingleProjectView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	public Hyperlink getLinkProjectNameSem1(){
		return linkProjectNameSem1;
	}
	
	public Hyperlink getLinkProjectNameSem2(){
		return linkProjectNameSem2;
	}
	
	public Label getLabelProjectName(){
		return labelProjectName;
	}
	
	public Image getImageDelete(){
		return imageDelete;
	}
	
	public Image getImageSettings(){
		return imageSettings;
	}

}
