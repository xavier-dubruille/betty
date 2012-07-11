package be.betty.gwtp.server;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import be.betty.gwtp.client.action.SubscribeAction;
import be.betty.gwtp.client.action.SubscribeActionResult;
import be.betty.gwtp.server.bdd.Session_id;
import be.betty.gwtp.server.bdd.User;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class SubscribeActionActionHandler implements
		ActionHandler<SubscribeAction, SubscribeActionResult> {

	@Inject
	public SubscribeActionActionHandler() {
	}

	@Override
	public SubscribeActionResult execute(SubscribeAction action,
			ExecutionContext context) throws ActionException {
		System.out.println("you did it	");
		return null;
	}
	
	@Override
	public void undo(SubscribeAction action, SubscribeActionResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<SubscribeAction> getActionType() {
		return SubscribeAction.class;
	}
}
