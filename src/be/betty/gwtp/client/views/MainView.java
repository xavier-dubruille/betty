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
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainView extends ViewImpl implements MainPresenter.MyView {

	private final Widget widget;

	@UiField Label mainLabel;
	@UiField AbsolutePanel dndpanel;
	@UiField Image dndImage;
	@UiField Label content;
	@UiField VerticalPanel cards_panel;
	public interface Binder extends UiBinder<Widget, MainView> {}

	@Inject
	public MainView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == MainPresenter.SLOT_Card) {
			cards_panel.clear();
			if (content != null)
				cards_panel.add(content);
		}
		else {
			super.setInSlot(slot, content);
		}
	}
	
	@Override
	public void addToSlot(Object slot, Widget content) {
		if (slot == MainPresenter.SLOT_Card)
			if (content != null)
				cards_panel.add(content);
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
	

	@Override
	public Label getContent() {
		return content;
	}

	@Override
	public void setContent(Label content) {
		this.content = content;
	}

	@Override
	public VerticalPanel getCards_panel() {
		return cards_panel;
	}

	public void setCards_panel(VerticalPanel cards_panel) {
		this.cards_panel = cards_panel;
	}
}
