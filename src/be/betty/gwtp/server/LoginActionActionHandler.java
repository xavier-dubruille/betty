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
		System.out.println("execute server");
		
		int user_id = checkLogin(action.getLogin(), action.getPwd());
		//sqlHandler.exexute("insert into test values (6669)");
		return new LoginActionResult(user_id);
	}

	private int checkLogin(String login, String pwd) {
		int id=-1;
		ResultSet stm = sqlHandler.executeQuery("select id from users where login = \""+login + "\" and pwd = \"" +pwd+"\"");
		try {
			if (stm.next())
				id = stm.getInt("id");
		} catch (SQLException e) {
			id = -2;
			//e.printStackTrace();
		}
		return id;
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
