package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.Filter_kind;
import be.betty.gwtp.client.Storage_access;
import be.betty.gwtp.client.UiConstants;
import be.betty.gwtp.client.views.ourWidgets.CardWidget;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
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
	private String group;
	private String course;
	private String teacher;
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
		String c = Storage_access.getCard(myI);
		
		group = Storage_access.getGroupCard(c);
		teacher = Storage_access.getTeacherCard(c);
		course = Storage_access.getCourseCard(c);
		int toto = Storage_access.getNumberOfGroup();
		int tata = Storage_access.getNumberOfCard();
		// TODO trouver un meilleur moyen de transmettre l'id au widget..
		getView().asWidget().setTitle(""+myI);
		//getView().getH().setValue(""+myI); //serait mieu que le titre.. si ca marchait..
		
		CardWidget cardW = getView().getCardWidget();
		cardW.getCourse().setText(course);
		cardW.getTeacher().setText(teacher);
		cardW.getGroup().setText(group);
		cardW.setCardId(myI);
		//System.out.println(group);
		//System.out.println("numberOfGroup: "+toto+"   \t numberOfCard: "+tata);
		
		//System.out.println("****");
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

	public String getKindString(Filter_kind filter_kind) {
		switch (filter_kind) {
		case TEACHER: return teacher;
		case GROUP: return group;
		}
		return "";
	}

	public boolean isPlaced() {
		return Storage_access.isCardPlaced(""+storageId);
	}


	public void setRightCss() {
		//System.out.println("isCardPlaced "+storageId+" placed ? "+isPlaced());
		if (isPlaced())
			getWidget().setStyleName(UiConstants.CSS_PLACED_CARD);
		else
			getWidget().setStyleName(UiConstants.CSS_CARD);
	}
}
