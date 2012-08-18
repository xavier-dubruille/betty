package be.betty.gwtp.server;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;

import be.betty.gwtp.client.action.LoginActionResult;
import be.betty.gwtp.client.action.SubscribeUserAction;
import be.betty.gwtp.client.action.SubscribeUserActionResult;
import be.betty.gwtp.server.bdd.Session_id;
import be.betty.gwtp.server.bdd.User;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class SubscribeUserActionActionHandler implements
		ActionHandler<SubscribeUserAction, SubscribeUserActionResult> {
	
	private static final Logger logger = Logger.getLogger(SubscribeUserActionActionHandler.class);

	@Inject
	public SubscribeUserActionActionHandler() {
	}

	@Override
	public SubscribeUserActionResult execute(SubscribeUserAction action,
			ExecutionContext context) throws ActionException {
		logger.trace("entering execute() from SubscribeUserActionActionHandler");
		boolean userExist = checkUserExist(action.getUsername());
		return new SubscribeUserActionResult(userExist);
	}

	
	
	private boolean checkUserExist(String username){
		assert username.length() > 0;

		System.out.println("Check if user exist");

		Session s = HibernateUtils.getSession();
		List q = s.createQuery("from User where name = :name")
				.setString("name", username).list();
		s.close();
		
		if (q.size() == 0)
			return false;
		else
			return true;
	}
	
	
	@Override
	public void undo(SubscribeUserAction action, SubscribeUserActionResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<SubscribeUserAction> getActionType() {
		return SubscribeUserAction.class;
	}
}
