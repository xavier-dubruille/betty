package be.betty.gwtp.client;

import java.sql.ResultSet;
import java.sql.SQLException;

import be.betty.gwtp.server.SQLHandler;

public class Just_test_stuff {

	private static SQLHandler sqlHandler ;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		sqlHandler = new SQLHandler();
		String login ="Admin";
		String pwd = "admin";
		int id= checkLogin(login, pwd);
		
		System.out.println("id = "+id);
	}
	
	private static int checkLogin(String login, String pwd) {
		int session_id=-1;
		int user_id = -1;
		ResultSet stm = sqlHandler.executeQuery("select id from users where login = \""+login + "\" and pwd = \"" +pwd+"\"");
		try {
			if (stm.next())
				user_id = stm.getInt("id");
		} catch (SQLException e) {
			//e.printStackTrace();
			return -2;
		}
		
		// on supprime les sessions id plus valide ? ou tous --> mais pas de double loggin alors..
		
		session_id = (int) (Math.random() * 1000000);
		System.out.println("sess: "+session_id);
		if (sqlHandler.exexuteUpdate("insert into session_ids( session_id, user_id ) " +
				"values ('"+session_id +"', '"+user_id +"')"))
			return session_id;
		else 
			return -1;
	}

}
