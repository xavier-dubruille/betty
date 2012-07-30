package be.betty.gwtp.server;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import be.betty.gwtp.client.action.GetInstancesOnly;
import be.betty.gwtp.client.action.GetInstancesOnlyResult;
import be.betty.gwtp.server.bdd.Activity_entity;
import be.betty.gwtp.server.bdd.Course;
import be.betty.gwtp.server.bdd.Group_entity;
import be.betty.gwtp.server.bdd.ProjectInstance;
import be.betty.gwtp.server.bdd.Project_entity;
import be.betty.gwtp.server.bdd.Teacher;
import be.betty.gwtp.shared.dto.Card_dto;
import be.betty.gwtp.shared.dto.Course_dto;
import be.betty.gwtp.shared.dto.Group_dto;
import be.betty.gwtp.shared.dto.ProjectInstance_dto;
import be.betty.gwtp.shared.dto.Teacher_dto;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetInstancesOnlyActionHandler implements
		ActionHandler<GetInstancesOnly, GetInstancesOnlyResult> {

	@Inject
	public GetInstancesOnlyActionHandler() {
	}

	@Override
	public GetInstancesOnlyResult execute(GetInstancesOnly action,
			ExecutionContext context) throws ActionException {
		
		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();

		Project_entity p = (Project_entity) s.get(Project_entity.class, action.getProjectId());
		GetInstancesOnlyResult result = new GetInstancesOnlyResult();
		for (ProjectInstance ins: p.getProjectInstances())
			result.addProjectInstance(new ProjectInstance_dto(ins.getId(), ins.getNum(), ins.getDescription()));

		t.commit();   
		s.close();
		return result;
	}

	@Override
	public void undo(GetInstancesOnly action, GetInstancesOnlyResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<GetInstancesOnly> getActionType() {
		return GetInstancesOnly.class;
	}
}
