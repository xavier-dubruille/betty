package be.betty.gwtp.server;

import org.hibernate.Session;
import org.hibernate.Transaction;

import be.betty.gwtp.client.action.DeleteProjectAction;
import be.betty.gwtp.client.action.DeleteProjectActionResult;
import be.betty.gwtp.server.bdd.Project_entity;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class DeleteProjectActionActionHandler implements
ActionHandler<DeleteProjectAction, DeleteProjectActionResult> {

	@Inject
	public DeleteProjectActionActionHandler() {
	}

	@Override
	public DeleteProjectActionResult execute(DeleteProjectAction action,
			ExecutionContext context) throws ActionException {

		assert action.getProject_id() >= 0 : "You've got a problem with your project_id while trying to delete it";

		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();

		// System.out.println("server: deleting project id "+action.getProject_id());
		// TODO: Verifier si l'utilisateur essaye bien de supprimer l'un de ses
		// propres projets !!!
		// Session_id sess_id = (Session_id) s.get(Session_id.class,
		// action.getSession_id());

		Project_entity p = (Project_entity) s.get(Project_entity.class,
				action.getProject_id());
		s.delete(p);
		// TODO: il faut égalment supprimer tout les autres données de se
		// projets !!

		t.commit();
		s.close();

		// sqlHandler.exexute("delete from project where id ='"+action.getProject_id()+"'");
		// sqlHandler.exexute("delete from user_project where project_id ='"+action.getProject_id()+"'");
		return null;
	}

	@Override
	public void undo(DeleteProjectAction action,
			DeleteProjectActionResult result, ExecutionContext context)
					throws ActionException {
	}

	@Override
	public Class<DeleteProjectAction> getActionType() {
		return DeleteProjectAction.class;
	}
}
