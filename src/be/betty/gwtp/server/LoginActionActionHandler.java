package be.betty.gwtp.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import be.betty.gwtp.client.action.LoginAction;
import be.betty.gwtp.client.action.LoginActionResult;
import be.betty.gwtp.server.bdd.Project_entity;
import be.betty.gwtp.server.bdd.Session_id;
import be.betty.gwtp.server.bdd.User;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class LoginActionActionHandler implements
		ActionHandler<LoginAction, LoginActionResult> {

	private static final Logger logger = Logger.getLogger(LoginActionActionHandler.class);
	
	@Inject
	public LoginActionActionHandler() {
	}

	@Override
	public LoginActionResult execute(LoginAction action,
			ExecutionContext context) throws ActionException {
		// System.out.println("DEBUG: execute server call"); //DEBUG:

		logger.trace("entering execute() from LoginActionActionHandler");
		String session_id = checkLogin(action.getLogin(), action.getPwd());
		logger.debug("The server call is almost done, and the sess_id for this user is: "+session_id);
		return new LoginActionResult(session_id);
	}

	private String checkLogin(String login, String pwd) {
		assert login.length() > 0 && pwd.length() > 0;

		logger.trace("entering check login method");
		System.out.println("Check login method");
		String sess_uuid = null;

		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();
		System.out.println("just aprs ouvertur");
		List q = s.createQuery("from User where name = :name and pwd= :pwd")
				.setString("name", login).setString("pwd", pwd).list();
		System.out.println("just aprs query");

		logger.debug("query to get the user is done, there is "+q.size()+" results (it should be 1 or 0)");
		if (q.size() == 0)
			return null;

		User user = (User) q.get(0);

		sess_uuid = UUID.randomUUID().toString();

		Session_id sess = new Session_id();
		sess.setSess_id(sess_uuid);
		sess.setUser_id(user);
		s.save(sess);

		
		t.commit();
		s.close();
		// TODO: on supprime les sessions id plus valide ? ou tous --> mais pas
		// de double loggin alors..

		return sess_uuid;
	}

	@Override
	public void undo(LoginAction action, LoginActionResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<LoginAction> getActionType() {
		return LoginAction.class;
	}
}
