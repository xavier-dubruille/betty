package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.ActionImpl;
import be.betty.gwtp.client.action.SolveItResult;

/**
 * This action is used to ask (with all necessary options)
 * the server-solveur  to calculate a solutions
 *
 */
public class SolveIt extends ActionImpl<SolveItResult> {

	private int projectBddID;
	private int projectInstanceId;
	
	public SolveIt() {
	}
	
	public SolveIt(int projectBddID, int projectInstanceId) {
		super();
		this.projectBddID = projectBddID;
		this.projectInstanceId = projectBddID;
	}
	
	@Override public boolean isSecured() {
		return false;
	}
	
	public int getProjectBddID() {
		return projectBddID;
	}
	public void setProjectBddID(int projectBddID) {
		this.projectBddID = projectBddID;
	}

	public int getInstanceId() {
		return projectInstanceId;
	}
}
