package be.betty.gwtp.server;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import be.betty.gwtp.client.action.GetSpecificProject;
import be.betty.gwtp.client.action.GetSpecificProjectResult;
import be.betty.gwtp.server.bdd.Activity;
import be.betty.gwtp.server.bdd.Project_entity;
import be.betty.gwtp.server.bdd.Session_id;
import be.betty.gwtp.server.bdd.User;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetSpecificProjectActionHandler implements
		ActionHandler<GetSpecificProject, GetSpecificProjectResult> {

	@Inject
	public GetSpecificProjectActionHandler() {
	}

	@Override
	public GetSpecificProjectResult execute(GetSpecificProject action,
			ExecutionContext context) throws ActionException {
		
		GetSpecificProjectResult result = new GetSpecificProjectResult();
		
		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();

		Project_entity p = (Project_entity) s.get(Project_entity.class, Integer.parseInt(action.getProject_id()));
		result.setName(p.getName());
		for (Activity a: p.getActivities()){
			System.out.println("***  Teacher:"+a.getTeacher().getName()+" Course:"+a.getCourse().getName()+" Group:"+a.getGroup().getCode());
			result.getActivities().add("Teacher:"+a.getTeacher().getName()+" Course:"+a.getCourse().getName()+" Group:"+a.getGroup().getCode());
		}
	
		t.commit();
		s.close();
		return result;
	}

	@Override
	public void undo(GetSpecificProject action, GetSpecificProjectResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<GetSpecificProject> getActionType() {
		return GetSpecificProject.class;
	}
}
