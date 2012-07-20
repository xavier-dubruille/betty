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
	public HTML header;
	private Hidden h;
	private Label course;
	private Label teacher;

	@Inject
	public SingleCardView() {
		// use the boundary panel as this composite's widget
		final AbsolutePanel boundaryPanel = new AbsolutePanel();
		boundaryPanel.setPixelSize(100, 60);
		boundaryPanel.addStyleName("card");
		//final DockPanel dockPanel = new DockPanel();
		//dockPanel.setPixelSize(100, 60);
		//dockPanel.addStyleName("card");

		//boundaryPanel.addStyleName("card");
		//widget = boundaryPanel;
		widget = boundaryPanel;

		// create the title bar
		header = new HTML("Empty");
		header.setPixelSize(99, 59);
		header.setStyleName("courseCard");
		
		/*course = new Label("Empty");
		teacher = new Label("Empty");
		
		course.setStyleName("courseCard");
		teacher.setStyleName("teacherCard");
		
		course.setPixelSize(99, 40);
		teacher.setPixelSize(99, 19);*/
		
		// create a panel to hold all 
		
		verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(1);
		verticalPanel.add(header);
		
		/*verticalPanel.add(course);
		veticalPanel.add(teacher);*/
		
		//h = new Hidden("id");
		//verticalPanel.add(h);
		boundaryPanel.add(verticalPanel);
		//boundaryPanel.add(verticalPanel);
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
	public HTML getHeader() {
		return header;
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
	
}
