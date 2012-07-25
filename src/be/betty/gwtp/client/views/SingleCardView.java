package be.betty.gwtp.client.views;

import be.betty.gwtp.client.presenters.SingleCardPresenter;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
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

	@Inject
	public SingleCardView() {
		// use the boundary panel as this composite's widget
		final AbsolutePanel boundaryPanel = new AbsolutePanel();
		boundaryPanel.setPixelSize(100, 60);
		boundaryPanel.addStyleName("card");
		
		widget = boundaryPanel;

		
		//Create all new label
		course = new Label("Empty");
		teacher = new Label("Empty");
		group = new Label("Empty");
		
		//Add a specific CSS for the different label
		course.setStyleName("courseCard");
		teacher.setStyleName("teacherCard");
		group.setStyleName("teacherCard");
		
		//Add a size for the different dockPanel
		course.setPixelSize(99, 40);
		teacher.setPixelSize(99, 19);
		
		//Create a new Dockpanel to
		dockPanel = new DockPanel();
		
		//Add all label in different part of dockPanel to have a clear presentation
		dockPanel.add(course, dockPanel.CENTER);
		dockPanel.add(teacher, dockPanel.SOUTH);
		
		//Add a size to the dockPanel
		dockPanel.setPixelSize(99, 59);
		
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
	
	public DockPanel getDockPanel(){
		return dockPanel;
	}
	
}
