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
	
	@Override
	public boolean isSecured() {
		return false;
	}
	
	@SuppressWarnings("unused")
	private CreateNewCardAction(){
		
	}
	
	public CreateNewCardAction(String teacherId, String courseId, int[] groupId) {
		this.teacherId = teacherId;
		this.courseId = courseId;
		this.groupId = groupId;	
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
	
	
}
