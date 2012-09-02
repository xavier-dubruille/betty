package be.betty.gwtp.server;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import be.betty.gwtp.client.action.CreateProjectInstanceAction;
import be.betty.gwtp.client.action.CreateProjectInstanceActionResult;
import be.betty.gwtp.server.bdd.ProjectInstance;
import be.betty.gwtp.server.bdd.Project_entity;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class CreateProjectInstanceActionActionHandler
implements
ActionHandler<CreateProjectInstanceAction, CreateProjectInstanceActionResult> {

	@Inject
	public CreateProjectInstanceActionActionHandler() {
	}

	@Override
	public CreateProjectInstanceActionResult execute(
			CreateProjectInstanceAction action, ExecutionContext context)
					throws ActionException {
		System.out.println("execute IntanceProject !");
		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();

		Project_entity p = (Project_entity) s.get(Project_entity.class, Integer.parseInt(action.getProjectId()));
		ProjectInstance pi = new ProjectInstance();

		p.getProjectInstances().add(pi);
		s.save(p);

		int instanceMax = 0;
		for (ProjectInstance proIn : p.getProjectInstances())
			if (proIn.getNum() > instanceMax)
				instanceMax = proIn.getNum();
		int instanceNum = instanceMax+1;
		
		pi.setNum(instanceNum);
		pi.setDescription("Solver");
		s.save(pi);

		s.save(pi);

		t.commit();
		s.close();

		return null;
	}

	@Override
	public void undo(CreateProjectInstanceAction action,
			CreateProjectInstanceActionResult result, ExecutionContext context)
					throws ActionException {
	}

	@Override
	public Class<CreateProjectInstanceAction> getActionType() {
		return CreateProjectInstanceAction.class;
	}
}
