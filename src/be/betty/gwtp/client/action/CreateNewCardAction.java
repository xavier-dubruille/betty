package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.ActionImpl;
import be.betty.gwtp.client.action.CreateNewCardActionResult;

public class CreateNewCardAction extends ActionImpl<CreateNewCardActionResult> {
	
	private int teacherId;
	private int courseId;
	private int[] groupId;
	
	@Override
	public boolean isSecured() {
		return false;
	}
	
	@SuppressWarnings("unused")
	private CreateNewCardAction(){
		
	}
	
	public CreateNewCardAction(int teacherId, int courseId, int[] groupId) {
		this.teacherId = teacherId;
		this.courseId = courseId;
		this.groupId = groupId;	
	}

	public int getTeacherId() {
		return teacherId;
	}

	public int getCourseId() {
		return courseId;
	}

	public int[] getGroupId() {
		return groupId;
	}
	
	
}
