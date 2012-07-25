package be.betty.gwtp.client.views;

import be.betty.gwtp.client.UiConstants;
import be.betty.gwtp.client.presenters.SingleCardPresenter;

import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

// This is not constructed by a xml uibinder.. this is because the dnd wasn't working on the 
// widget return by the uiBinder's way.  (with the method asWidget)
public class SingleCardView extends ViewImpl implements
		SingleCardPresenter.MyView {

	Widget widget;
	public VerticalPanel verticalPanel;
	private Hidden h;
	private Label course;
	private Label teacher;
	private Label group;
	private DockPanel dockPanel;
	private DockPanel dockPanel2;

	@Inject
	public SingleCardView() {
		// use the boundary panel as this composite's widget
		final AbsolutePanel boundaryPanel = new AbsolutePanel();
		boundaryPanel.setPixelSize(UiConstants.getCardWidth(), UiConstants.getCardHeight());
		boundaryPanel.addStyleName("card");
		
		widget = boundaryPanel;

		
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
		dockPanel = new DockPanel();
		dockPanel2 = new DockPanel();
		
		
		//Add all label in different part of dockPanel to have a clear presentation
		dockPanel2.add(group, DockPanel.WEST);
		dockPanel2.add(teacher, DockPanel.EAST);
		
		dockPanel.add(dockPanel2, DockPanel.NORTH);
		dockPanel.add(course, DockPanel.CENTER);
		
		
		//Add a size to the dockPanel
		dockPanel.setPixelSize(UiConstants.getCardWidth()-1, UiConstants.getCardHeight()-1);
		
		// create a panel to hold all 
		
		verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(1);
		verticalPanel.add(dockPanel);
		
		//h = new Hidden("id");
		//verticalPanel.add(h);
		boundaryPanel.add(verticalPanel);
	}

	@Override
	public Widget asWidget() {
		return this.widget;
	}

	@Override
	public VerticalPanel getVerticalPanel() {
		return verticalPanel;
	}

	@Override
	public Hidden getH() {
		return h;
	}
	
	public Label getCourse(){
		return course;
	}
	
	public Label getTeacher(){
		return teacher;
	}
	
	public Label getGroup(){
		return group;
	}
	
	public DockPanel getDockPanel(){
		return dockPanel;
	}
	
}
