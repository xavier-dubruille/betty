package be.betty.gwtp.client.views.ourWidgets;

import be.betty.gwtp.client.UiConstants;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CardWidget extends Composite {

	private Label course;
	private Label teacher;
	private Label group;
	private int cardId;
	private PickupDragController dragController;

	public CardWidget() {
		// use the boundary panel as this composite's widget
		final AbsolutePanel boundaryPanel = new AbsolutePanel();
		boundaryPanel.setPixelSize(UiConstants.getCardWidth(), UiConstants.getCardHeight());
		boundaryPanel.addStyleName("card");

		initWidget(boundaryPanel);

		//Create all new label
		course = new Label("Empty");
		teacher = new Label("Empty");
		group = new Label("Empty");

		//Add a specific CSS for the different label
		course.setStyleName("courseCard");
		teacher.setStyleName("teacherCard");
		group.setStyleName("groupCard");


		//Add a size for the different dockPanel
		course.setPixelSize(UiConstants.getCardWidth(), (UiConstants.getCardHeight()/3)*2);
		teacher.setPixelSize((UiConstants.getCardWidth()/2)-1, UiConstants.getCardHeight()/3);
		group.setPixelSize((UiConstants.getCardWidth()/2)-1, UiConstants.getCardHeight()/3);

		//Create a new Dockpanel to
		DockPanel dockPanel = new DockPanel();
		DockPanel dockPanel2 = new DockPanel();


		//Add all label in different part of dockPanel to have a clear presentation
		dockPanel2.add(group, DockPanel.WEST);
		dockPanel2.add(teacher, DockPanel.EAST);

		dockPanel.add(dockPanel2, DockPanel.NORTH);
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


	public Label getCourse() {
		return course;
	}


	public Label getTeacher() {
		return teacher;
	}
	
	public Label getGroup() {
		return group;
	}


	public CardWidget cloneWidget() {
		CardWidget clone = new CardWidget();
		
		clone.getCourse().setText(getCourse().getText());
		clone.getTeacher().setText(getTeacher().getText());
		clone.getGroup().setText(getGroup().getText());
		clone.setTitle(getTitle());
		clone.setDragControler(getDragController());
		clone.makeItDraggable();
		
		return clone;
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
}