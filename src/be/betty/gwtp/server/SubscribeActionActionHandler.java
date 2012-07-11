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
		System.out.println(action.getEmail());
		InsertDB(action.getLogin(), action.getPwd(), action.getEmail());
		return null;
	}

	private boolean InsertDB(String user, String password, String email) {
		
		try{
		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();
		
		User usr = new User();
		usr.setName(user);
		usr.setPwd(password);
		usr.setEmail(email);
		
		
		s.save(usr);
		t.commit();
		s.close();
		}
		catch (Exception e){
			//TODO
			System.out.println(e);
			return false;
		}
		System.out.println("Send to DATABASE OK!!");
		return true;
	}

	// TODO
	private boolean VerifExisting(String user, String email) {
		return false;
	}

	// TODO
	private void SendMail(String email) {

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
