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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.AbsolutePanel;

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
	@UiField SimplePanel simplePanel;
	@UiField AbsolutePanel filterAbsolutePanel;
	

	@Override
	public ListBox getComboBoxFilterType() {
		return comboBoxFilterType;
	}

	public SimplePanel getSimplePanel(){
		return simplePanel;
	}
	
	public AbsolutePanel getFilterAbsolutePanel(){
		return filterAbsolutePanel;
	}
}
