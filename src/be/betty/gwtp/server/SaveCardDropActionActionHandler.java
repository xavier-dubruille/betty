package be.betty.gwtp.server;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;

import be.betty.gwtp.client.Storage_access;
import be.betty.gwtp.client.action.SaveCardDropAction;
import be.betty.gwtp.client.action.SaveCardDropActionResult;
import be.betty.gwtp.server.bdd.Activity_entity;
import be.betty.gwtp.server.bdd.ActivityState;
import be.betty.gwtp.server.bdd.ProjectInstance;
import be.betty.gwtp.server.bdd.Project_entity;
import be.betty.gwtp.server.bdd.Room;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class SaveCardDropActionActionHandler implements
		ActionHandler<SaveCardDropAction, SaveCardDropActionResult> {

	@Inject
	public SaveCardDropActionActionHandler() {
	}

	@Override
	public SaveCardDropActionResult execute(SaveCardDropAction action,
			ExecutionContext context) throws ActionException {

		if (!CheckSession.isProjectActionPermited(action.getProjectInstance(), true, action.getSessId()))
			throw new ActionException("Invalid Action, try to re-log");
		
		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();
		
		System.out.println("droping card:"+action.getCardBddId()+ " in room:"+action.getRoom());
		
		
		Activity_entity activity = (Activity_entity) s.get(Activity_entity.class, action.getCardBddId());
		System.out.println("**project instance="+action.getProjectInstance());
		ProjectInstance pi = (ProjectInstance) s.get(ProjectInstance.class, action.getProjectInstance());
		assert (pi != null): "probleme annormal dans la reception de l'instance dans laquelle poser les cartons";
		ActivityState as = new ActivityState();
		as.setActivity(activity);
		as.setDay(action.getDay());
		as.setPeriod(action.getPeriod());
	
		if (action.getRoom() != null && !action.getRoom().equals("-1"))
			as.setRoom((Room) s.get(Room.class, Integer.parseInt(action.getRoom())));
		
		as.setProjectInstance(pi);
		s.save(as);
		pi.getActivitiesState().add(as);
		s.save(pi);
		//System.out.println("saveCardDropActionActionHandler: cardId="+action.getCardBddId()+", day="+action.getDay()+", period="+action.getPeriod());
		t.commit();
		s.close();
		return null;
	}

	@Override
	public void undo(SaveCardDropAction action, SaveCardDropActionResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<SaveCardDropAction> getActionType() {
		return SaveCardDropAction.class;
	}
}
