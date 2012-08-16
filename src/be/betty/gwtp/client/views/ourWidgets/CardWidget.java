package be.betty.gwtp.client.views.ourWidgets;

import be.betty.gwtp.client.Storage_access;
import be.betty.gwtp.client.UiConstants;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CardWidget extends Composite implements HasMouseDownHandlers {

	private Label course;
	private Label teacher;
	private Label group;
	private Label room;
	private int cardId;
	private PickupDragController dragController;
	private boolean fromSelectionPanel;
	private int[] groupsId;
	private int teacherId;
	private MouseDownHandler handler;

	public CardWidget() {
		// use the boundary panel as this composite's widget
		final AbsolutePanel boundaryPanel = new AbsolutePanel();
		boundaryPanel.setPixelSize(UiConstants.getCardWidth(), UiConstants.getCardHeight());
		boundaryPanel.addStyleName("card");

		initWidget(boundaryPanel);

		fromSelectionPanel = true;
		//Create all new label
		course = new Label("Empty");
		teacher = new Label("Empty");
		group = new Label("Empty");
		room = new Label("LABO");

		//Add a specific CSS for the different label
		course.setStyleName("courseCard");
		teacher.setStyleName("teacherCard");
		group.setStyleName("groupCard");
		room.setStyleName("teacherCard");


		//Add a size for the different dockPanel
		course.setPixelSize(UiConstants.getCardWidth(), (UiConstants.getCardHeight()/4)*2);
		teacher.setPixelSize(((UiConstants.getCardWidth()/3)*2)-1, (UiConstants.getCardHeight()/4)+5);
		group.setPixelSize((UiConstants.getCardWidth()/3)-1, (UiConstants.getCardHeight()/4)+5);
		room.setPixelSize((UiConstants.getCardWidth())-1, UiConstants.getCardHeight()/4);

		//Create a new Dockpanel to
		DockPanel dockPanel = new DockPanel();
		DockPanel dockPanel2 = new DockPanel();
		DockPanel dockPanelRoomAndDelete = new DockPanel();

		//Add all label in different part of dockPanel to have a clear presentation
		dockPanel2.add(group, DockPanel.WEST);
		dockPanel2.add(teacher, DockPanel.EAST);
		
		dockPanelRoomAndDelete.add(room, DockPanel.EAST);
		
		dockPanel.add(dockPanel2, DockPanel.NORTH);
		dockPanel.add(dockPanelRoomAndDelete, DockPanel.SOUTH);
		dockPanel.add(course, DockPanel.CENTER);
		

		//Add a size to the dockPanel
		dockPanel.setPixelSize(UiConstants.getCardWidth()-1, UiConstants.getCardHeight()-1);

		// create a panel to hold all 

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(1);
		verticalPanel.add(dockPanel);

		//h = new Hidden("id");
		//verticalPanel.add(h);
		boundaryPanel.add(verticalPanel);
	}



	public boolean isPlaced() {
		String storageId = this.getElement().getTitle();
		return Storage_access.isCardPlaced(""+storageId);
	}

	
	public void setRightCss() {
		//System.out.println("isCardPlaced "+storageId+" placed ? "+isPlaced());
		if (isPlaced())
			this.setStyleName(UiConstants.CSS_PLACED_CARD);
		else
			this.setStyleName(UiConstants.CSS_CARD);
	}
	
	public Label getCourse() {
		return course;
	}


	public Label getTeacher() {
		return teacher;
	}
	
	public Label getGroup() {
		return group;
	}
	
	public Label getRoom() {
		return room;
	}


	public CardWidget cloneWidget(boolean fromSelectionPanel) {
		CardWidget clone = new CardWidget();
		
		clone.setFromSelectionPanel(fromSelectionPanel);
		clone.getCourse().setText(getCourse().getText());
		clone.getTeacher().setText(getTeacher().getText());
		clone.getGroup().setText(getGroup().getText());
		clone.getRoom().setText(getRoom().getText());
		clone.setTitle(getTitle());
		clone.setDragControler(getDragController());
		clone.makeItDraggable();
		clone.addMouseDownHandler(handler);
		
		return clone;
	}

	public void setFromSelectionPanel(boolean fromSelectionPanel) {
		this.fromSelectionPanel = fromSelectionPanel;
		
	}



	private PickupDragController getDragController() {
		return this.dragController;
	}


	public void setCardId(int myI) {
		this.cardId = myI;
	}


	public void setDragControler(PickupDragController cardDragController) {
		this.dragController = cardDragController;
		
	}


	public void makeItDraggable() {
		dragController.makeDraggable(this, getCourse());
		dragController.makeDraggable(this, getTeacher());
		dragController.makeDraggable(this, getGroup());
	}



	public boolean isFromSelectionPanel() {
		return fromSelectionPanel;
	}



	public void init(int myI) {
		String c = Storage_access.getCard(myI);
		String[] groups = Storage_access.getGroupCard(c);
		String groupsLabel = groups[0];
		for (int i = 1; i < groups.length; i++)
			groupsLabel +=  ", "+groups[i];
		
		
		groupsId = Storage_access.getGroupsIdCard(c);
		teacherId = Storage_access.getTeacherIdCard(c);
		group.setText(groupsLabel);
		teacher.setText(Storage_access.getTeacherCard(c));
		course.setText(Storage_access.getCourseCard(c));
		
		setCardId(myI);
		
	}



	public int getCardId() {
		return cardId;
	}



	public int[] getGroupsId() {
		return groupsId;
	}



	public int getTeacherId() {
		return teacherId;
	}



	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
        this.handler = handler;
		return addDomHandler(handler, MouseDownEvent.getType());
    }



	/*@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}*/
}
