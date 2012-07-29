package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.CardSelectionOptionPresenter;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CardSelectionOptionView extends ViewImpl implements
		CardSelectionOptionPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, CardSelectionOptionView> {
	}

	@Inject
	public CardSelectionOptionView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	@UiField ListBox comboBoxFilterType;
	@UiField ListBox group_choice;
	@UiField ScrollPanel cardFilterScrollPanel;
	@UiField VerticalPanel cardFilterVerticalPanel;
	

	@Override
	public ListBox getComboBoxFilterType() {
		return comboBoxFilterType;
	}

	@Override
	public ListBox getGroup_choice() {
		return group_choice;
	}

	public VerticalPanel getCardFilterVerticalPanel(){
		return cardFilterVerticalPanel;
	}


	
}
