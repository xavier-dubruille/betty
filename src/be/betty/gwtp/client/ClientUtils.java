package be.betty.gwtp.client;


import be.betty.gwtp.client.event.AddNotifEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Inject;

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

	public static void notifyUser(VerticalPanel notificationBarVerticalPanel, String soluceInfo) {
		
		
	}

	public static void notifyUser(String notif, EventBus eventBus) {
		System.out.println("Notif = "+notif);
		// should we log it too ?
		eventBus.fireEvent(new AddNotifEvent(notif));
		
		
	}

}