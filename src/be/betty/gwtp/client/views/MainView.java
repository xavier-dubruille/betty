package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.MainPresenter;
import be.betty.gwtp.client.presenters.ProjectsPresenter;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.user.client.ui.HTMLPanel;

public class MainView extends ViewImpl implements MainPresenter.MyView {

	private final Widget widget;

	@UiField Label mainLabel;
	@UiField AbsolutePanel dndpanel;
	@UiField AbsolutePanel dropPanel;
	@UiField Image dndImage;
	@UiField HTMLPanel html_panel;
	@UiField Label content;
	public interface Binder extends UiBinder<Widget, MainView> {}

	@Inject
	public MainView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == MainPresenter.SLOT_Card) {
			html_panel.clear();
			if (content != null)
				html_panel.add(content);
		}
		else {
			super.setInSlot(slot, content);
		}
	}
	
	@Override
	public void addToSlot(Object slot, Widget content) {
		if (slot == MainPresenter.SLOT_Card)
			if (content != null)
				html_panel.add(content);
		else 
			super.addToSlot(slot, content);
		
	}
	@Override
	public Widget asWidget() {
		return widget;
	}
	
	public Label getMainLabel(){
		return mainLabel;
	}
	
	public Image getDndImage(){
		return dndImage;
	}
	
	public AbsolutePanel getDndPanel(){
		return dndpanel;
	}
	
	public AbsolutePanel getDropPanel(){
		return dropPanel;
	}

	@Override
	public HTMLPanel getHtml_panel() {
		return html_panel;
	}

	@Override
	public void setHtml_panel(HTMLPanel html_panel) {
		this.html_panel = html_panel;
	}

	@Override
	public Label getContent() {
		return content;
	}

	@Override
	public void setContent(Label content) {
		this.content = content;
	}
}
