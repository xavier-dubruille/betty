package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.ActionImpl;
import be.betty.gwtp.client.action.CreateNewCardActionResult;

/**
 * This action is called when a new card is created
 *
 */
public class CreateNewCardAction extends ActionImpl<CreateNewCardActionResult> {
	
	private String teacherId;
	private String courseId;
	private int[] groupId;
	private String projectId;
	private String semester;
	
	@Override
	public boolean isSecured() {
		return false;
	}
	
	@SuppressWarnings("unused")
	private CreateNewCardAction(){
		
	}
	
	public CreateNewCardAction(String teacherId, String courseId, String projectId, String  semester, int[] groupId) {
		this.teacherId = teacherId;
		this.courseId = courseId;
		this.groupId = groupId;	
		this.projectId = projectId;
		this.semester = semester;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public String getCourseId() {
		return courseId;
	}

	public int[] getGroupId() {
		return groupId;
	}

	public int getProjectId() {
		return Integer.parseInt(projectId);
		
	}
	
	public String getSemester() {
		return semester;
	}
	
	
}
