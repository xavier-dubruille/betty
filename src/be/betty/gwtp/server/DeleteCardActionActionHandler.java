package be.betty.gwtp.server;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import be.betty.gwtp.client.action.DeleteCardAction;
import be.betty.gwtp.client.action.DeleteCardActionResult;
import be.betty.gwtp.server.bdd.Activity_entity;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class DeleteCardActionActionHandler implements
		ActionHandler<DeleteCardAction, DeleteCardActionResult> {

	@Inject public DeleteCardActionActionHandler() {
	}

	@Override public DeleteCardActionResult execute(DeleteCardAction action,
			ExecutionContext context) throws ActionException {
		System.out.println("delete action: cardId="+action.getCardId()+" sessId	"+action.getSessionId());
		
		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();
		Activity_entity a = (Activity_entity) s.get(Activity_entity.class, action.getCardId());
		s.delete(a);
		t.commit();
		s.close();
		
		
		return null;
	}

	@Override public void undo(DeleteCardAction action, DeleteCardActionResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override public Class<DeleteCardAction> getActionType() {
		return DeleteCardAction.class;
	}
}
