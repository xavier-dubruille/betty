package be.betty.gwtp.server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Statement;
import java.sql.ResultSet;


public class SQLHandler {

	private Connection c;

	public SQLHandler() {

		final String CONNECTION = "jdbc:mysql://178.170.112.6/betty";
		try {
			// The newInstance() call is a work around for some
			// broken Java implementations

			Class.forName("com.mysql.jdbc.Driver").newInstance();

		} catch (Exception ex) {
			// handle the error
		}


		try {
			Properties p = new Properties();
			p.put("user","betty");
			p.put("password","aqzsed");
			// Now try to connect
			c = DriverManager.getConnection(CONNECTION,"betty","aqzsed");


		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());



		}
		System.out.println("sql Connection sucess");

	}

	public boolean exexute (String req){
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			stmt.execute(req);

		}
		catch (SQLException ex){
			return false;
		}
		finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) { } // ignore

				stmt = null;
			}
		}

		return true;
	}


	public ResultSet executeQuery(String req){
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = c.createStatement();
			rs = stmt.executeQuery(req);

			//System.out.println(rs.getInt(1));

			// Now do something with the ResultSet ....
		}
		catch (SQLException ex){
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed

			//			if (rs != null) {
			//				try {
			//					rs.close();
			//				} catch (SQLException sqlEx) { } // ignore
			//
			//				rs = null;
			//			}

//			if (stmt != null) {
//				try {
//					stmt.close();
//				} catch (SQLException sqlEx) { } // ignore
//
//				stmt = null;
//			}
		}

		return rs;
	}

	public boolean exexuteUpdate(String req) {
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(req);

		}
		catch (SQLException ex){
			return false;
		}
		finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) { } // ignore

				stmt = null;
			}
		}

		return true;
	}

	public String executeSingleQuery(String req, String col) {
		ResultSet stm =executeQuery(req);
		String ret = null;
		try {
			if (stm.next())
				ret = stm.getString(col);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return ret;
	}


}
