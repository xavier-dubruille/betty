package be.betty.gwtp.server;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;

import be.betty.gwtp.client.action.GetProjectsAction;
import be.betty.gwtp.client.action.GetProjectsActionResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetProjectsActionActionHandler implements
		ActionHandler<GetProjectsAction, GetProjectsActionResult> {

	SQLHandler sqlHandler = new SQLHandler();
	@Inject
	public GetProjectsActionActionHandler() {
	}

	@Override
	public GetProjectsActionResult execute(GetProjectsAction action,
			ExecutionContext context) throws ActionException {
		int session_id = action.getSession_id();
		String projects = "  Vos projects sont: ";
		String name = "";
		ResultSet stm = sqlHandler.executeQuery("select project.name from project, users, user_project, session_ids " +
				"where session_ids.user_id = users.id and user_project.user_id = users.id " +
				"and project.id = user_project.project_id and session_ids.id = '"+session_id+"'");
		try {
			while (stm.next()){
				name = stm.getString("name");
				
				System.out.println(" project name =  "+name);
				if (name == null)
					break;
				projects += name+" / ";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new GetProjectsActionResult(projects);
	}

	@Override
	public void undo(GetProjectsAction action, GetProjectsActionResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<GetProjectsAction> getActionType() {
		return GetProjectsAction.class;
	}
}
