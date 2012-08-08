package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.ClientUtils;
import be.betty.gwtp.client.Filter_kind;
import be.betty.gwtp.client.Storage_access;
import be.betty.gwtp.client.UiConstants;
import be.betty.gwtp.client.views.ourWidgets.CardWidget;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.google.inject.Inject;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SingleCardPresenter extends
PresenterWidget<SingleCardPresenter.MyView> {

	public interface MyView extends View {
		CardWidget getCardWidget();

	}

	private int teacherId;
	private int groupId;
	private int storageId;

	@Inject
	public SingleCardPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	public void init(int myI) {
		storageId = myI;
		final int ii = myI;
		// TODO trouver un meilleur moyen de transmettre l'id au widget..
		getView().asWidget().setTitle(""+myI);
		
		CardWidget cardW = getView().getCardWidget();
		cardW.init(myI);
		cardW.addMouseDownHandler(new MouseDownHandler() {
			
			@Override
			public void onMouseDown(MouseDownEvent event) {
				// TODO Auto-generated method stub
				System.out.println("on click handler yezzu");
				int button = event.getNativeEvent().getButton();
		        if (button == NativeEvent.BUTTON_LEFT) {
		            return;
		        }

		        if (button == NativeEvent.BUTTON_RIGHT) {
		            event.preventDefault();
		            ClientUtils.notifyUser("Jack est le meilleur! "+ii, getEventBus());
		            System.out.println("right click");
		            //doRightClick();
		        }	
			
			}
		});
	}

	/**
	 * Not used, but, in a proper version, should be.
	 * @param filter_kind
	 * @return
	 */
	public int getKindId(Filter_kind filter_kind) {
		switch (filter_kind) {
		case TEACHER: return teacherId;
		case GROUP: return groupId;
		}
		return 0;
	}

/*	public String getKindString(Filter_kind filter_kind) {
		switch (filter_kind) {
		case TEACHER: return teacher;
		case GROUP: return group;
		}
		return "";
	}*/
	
	@Override public String toString() {
		CardWidget cardW = getView().getCardWidget();
		if (cardW == null) return "widget Null";
		return cardW.getTeacher().getText();
	}



}
