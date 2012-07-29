package be.betty.gwtp.client;

import com.google.gwt.user.client.Window;

public class ClientUtils {

	/**
	 * This method is meant to be called whenever 
	 * a failure occurs..
	 * 
	 * We save into the log, the console and a popup for the user
	 * 
	 * @param description
	 */
	public static void actionFailed(String description) {
		System.err.println("** action failed, description = \""+description+"\" **");
		
		Window.alert("** action failed, description = \""+description+"\" **");
		
	}

	public static void notifyUser(String soluceInfo) {
		System.out.println("SoluceInfo = "+soluceInfo);
		
	}

}
