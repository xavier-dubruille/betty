package be.betty.gwtp.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import be.betty.gwtp.client.action.LoginAction;
import be.betty.gwtp.client.action.LoginActionResult;
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
		//sqlHandler.exexute("insert into test values (6669)");
		return new LoginActionResult(session_id);
	}

	private String checkLogin(String login, String pwd) {
		String session_id=null;
		int user_id = -1;
		ResultSet stm = sqlHandler.executeQuery("select id from users where login = \""+login + "\" and pwd = \"" +pwd+"\"");
		try {
			if (stm.next())
				user_id = stm.getInt("id");
				System.out.println("DEBUG: user id = .. "+user_id); //DEBUG
				if (user_id < 1)
					return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
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
