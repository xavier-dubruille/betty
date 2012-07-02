package be.betty.gwtp.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import be.betty.gwtp.client.action.LoginAction;
import be.betty.gwtp.client.action.LoginActionResult;
import be.betty.gwtp.server.bdd.Project_entity;
import be.betty.gwtp.server.bdd.User;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class LoginActionActionHandler implements
		ActionHandler<LoginAction, LoginActionResult> {

	public static SQLHandler sqlHandler;
	@Inject
	public LoginActionActionHandler() {
		sqlHandler = new SQLHandler();
	}

	@Override
	public LoginActionResult execute(LoginAction action, ExecutionContext context)
			throws ActionException {
		//System.out.println("DEBUG: execute server call");  //DEBUG: 
		
		String session_id = checkLogin(action.getLogin(), action.getPwd());
		return new LoginActionResult(session_id);
	}

	private String checkLogin(String login, String pwd) {
		System.out.println("Check login method");
		String session_id=null;
		int user_id = -1;

		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();
		System.out.println("just aprs ouvertur");
//		List q = s.createQuery("from User where name = :name, pwd= :pwd")
//				.setString("name", login).setString("pwd", pwd).list();
//		System.out.println("just aprs query");
//		
//		User user = null;
//		if ( q != null)
//			user=(User) q.get(0);
//		System.out.println("user is:"+user);
//		
		t.commit();
		s.close();
		// on supprime les sessions id plus valide ? ou tous --> mais pas de double loggin alors..
		
		session_id = UUID.randomUUID().toString();
		System.out.println("sess: "+session_id);
		if (sqlHandler.exexuteUpdate("insert into session_ids( id, user_id ) " +
				"values ('"+session_id +"', '"+user_id +"')"))
			return session_id;
		else 
			return null;
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
