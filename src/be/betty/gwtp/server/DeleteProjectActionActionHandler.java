package be.betty.gwtp.server;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import be.betty.gwtp.client.action.DeleteProjectAction;
import be.betty.gwtp.client.action.DeleteProjectActionResult;
import be.betty.gwtp.server.bdd.Teacher;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class DeleteProjectActionActionHandler implements
		ActionHandler<DeleteProjectAction, DeleteProjectActionResult> {

	SQLHandler sqlHandler = new SQLHandler();
	
	@Inject
	public DeleteProjectActionActionHandler() {
	}

	@Override
	public DeleteProjectActionResult execute(DeleteProjectAction action,
			ExecutionContext context) throws ActionException {
		
		
		Session s = HibernateUtils.getSession();
		
		Transaction t = s.beginTransaction();
		
		Teacher te = new Teacher();
		te.setName("badaboum");

		s.save(te);
		t.commit();
		s.close();
		
		//System.out.println("server: deleting project id "+action.getProject_id());
		//TODO: Verifier si l'utilisateur essaye bien de supprimer l'un de ses propres projets !!!
		
		sqlHandler.exexute("delete from project where id ='"+action.getProject_id()+"'");
		sqlHandler.exexute("delete from user_project where project_id ='"+action.getProject_id()+"'");
		return null;
	}

	@Override
	public void undo(DeleteProjectAction action, DeleteProjectActionResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<DeleteProjectAction> getActionType() {
		return DeleteProjectAction.class;
	}
}
