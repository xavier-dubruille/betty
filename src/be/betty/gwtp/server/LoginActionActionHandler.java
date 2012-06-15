package be.betty.gwtp.server;

import java.sql.ResultSet;
import java.sql.SQLException;

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
		//System.out.println("execute server call");
		
		int session_id = checkLogin(action.getLogin(), action.getPwd());
		//sqlHandler.exexute("insert into test values (6669)");
		return new LoginActionResult(session_id);
	}

	private int checkLogin(String login, String pwd) {
		int session_id=-1;
		int user_id = -1;
		ResultSet stm = sqlHandler.executeQuery("select id from users where login = \""+login + "\" and pwd = \"" +pwd+"\"");
		try {
			if (stm.next())
				user_id = stm.getInt("id");
				//System.out.println("user id = .. "+user_id);
				if (user_id < 1)
					return -3;
		} catch (SQLException e) {
			//e.printStackTrace();
			return -2;
		}
		
		// on supprime les sessions id plus valide ? ou tous --> mais pas de double loggin alors..
		
		session_id = (int) (System.currentTimeMillis()); //TODO: ca doit être alleatoire !
		//System.out.println("sess: "+session_id);
		if (sqlHandler.exexuteUpdate("insert into session_ids( id, user_id ) " +
				"values ('"+session_id +"', '"+user_id +"')"))
			return session_id;
		else 
			return -1;
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
