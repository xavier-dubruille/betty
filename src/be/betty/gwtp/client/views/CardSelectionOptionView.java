package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.CardSelectionOptionPresenter;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;

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
	
	@UiField ListBox teacher_choice;
	@UiField ListBox group_choice;

	@Override
	public ListBox getTeacher_choice() {
		return teacher_choice;
	}

	@Override
	public ListBox getGroup_choice() {
		return group_choice;
	}

	
}
