package be.betty.gwtp.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

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
		System.out.println("SoluceInfo = "+soluceInfo);
		Label notification = new Label();
		notification.setText("notif: "+soluceInfo);
		notificationBarVerticalPanel.add(notification);
	}

}
