package be.betty.gwtp.server;

import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;

import be.betty.gwtp.client.action.GetActivityStateAction;
import be.betty.gwtp.client.action.GetActivityStateActionResult;
import be.betty.gwtp.server.bdd.Activity_entity;
import be.betty.gwtp.server.bdd.ActivityState;
import be.betty.gwtp.server.bdd.ProjectInstance;
import be.betty.gwtp.shared.dto.ActivityState_dto;
import be.betty.gwtp.shared.dto.Card_dto;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetActivityStateActionActionHandler implements
		ActionHandler<GetActivityStateAction, GetActivityStateActionResult> {

	@Inject
	public GetActivityStateActionActionHandler() {
	}

	@Override
	public GetActivityStateActionResult execute(GetActivityStateAction action,
			ExecutionContext context) throws ActionException {
		
		if (!CheckCookies.checkProjectInstance(action.getInstanceBddId()))
			throw new ActionException("Invalid Action, try to erase your cookies and re-log");
		
		HashMap<String, ActivityState_dto> h = new HashMap<String, ActivityState_dto>();

		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();
		
		ProjectInstance pi = (ProjectInstance) s.get(ProjectInstance.class, action.getInstanceBddId());
		for (ActivityState as : pi.getActivitiesState()) {
			h.put(""+as.getActivity().getId(), new ActivityState_dto(as.getDay(), as.getPeriod()));
		}
		
		t.commit();   
		s.close();
		
		return new GetActivityStateActionResult(h);
	}

	@Override
	public void undo(GetActivityStateAction action,
			GetActivityStateActionResult result, ExecutionContext context)
			throws ActionException {
	}

	@Override
	public Class<GetActivityStateAction> getActionType() {
		return GetActivityStateAction.class;
	}
}
