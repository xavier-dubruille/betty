package be.betty.gwtp.server;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;

import be.betty.gwtp.client.action.GetProjectsAction;
import be.betty.gwtp.client.action.GetProjectsActionResult;
import be.betty.gwtp.client.model.Project;
import be.betty.gwtp.server.bdd.Project_entity;
import be.betty.gwtp.server.bdd.User;

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
		String session_id = action.getSession_id();
		ArrayList<Project> projects = new ArrayList<Project>();
		
		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();
		User user = (User) s.get(User.class, 1);  
		//faudra pluto mettre une querry avec le session id, pr retrouver l'utilisateur
		//List l = s.createQuery("from Course where code=?").setString(0, "D111C02").list();

		System.out.println("user = "+user.getName());
		Collection<Project_entity> list = user.getProjects();
		System.out.println("number of user project = "+list.size());
		
		
		for (Project_entity e: list)
			projects.add( new Project(e.getName(), e.getId() ));
		
		t.commit();
		s.close();
		
		
		System.out.println("projects to be return = "+ projects);
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
