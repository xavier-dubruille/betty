package be.betty.gwtp.server;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import be.betty.gwtp.client.action.SaveCardDropAction;
import be.betty.gwtp.client.action.SaveCardDropActionResult;
import be.betty.gwtp.server.bdd.Activity;
import be.betty.gwtp.server.bdd.ActivityState;
import be.betty.gwtp.server.bdd.Project_entity;

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
		
		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();

		Activity activity = (Activity) s.get(Activity.class, action.getCardBddId());
		ActivityState as = new ActivityState();
		as.setActivity(activity);
		as.setDay(action.getDay());
		as.setPeriod(action.getPeriod());
		s.save(as);
		System.out.println("saveCardDropActionActionHandler: cardId="+action.getCardBddId()+", day="+action.getDay()+", period="+action.getPeriod());
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
