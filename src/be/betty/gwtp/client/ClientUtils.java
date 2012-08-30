package be.betty.gwtp.client;


//import java.util.regex.Pattern;

import be.betty.gwtp.client.event.AddNotifEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;

/**
 * This class is to put all utilities needed in client side
 *
 */
public class ClientUtils {


	
	public static boolean DONT_REPEAT_YOURSELF = true;
	
//	private static final Pattern rfc2822 = Pattern.compile(
//	        "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$"
//	);
	
	/**
	 * Use to check if the email entered by the user is
	 * in a right syntax.
	 * @param email the input email
	 * @return if it's valid
	 */
	public static boolean CheckEmail(String email){
		
//		if (!rfc2822.matcher(email).matches()) {
//			return false;
//		}else{
//			return true;
		if (!email.contains("@") || !email.contains(".")) {
			return false;
		}else
			return true;
		
	}
	
	
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


	/**
	 * This method allows to make a notification on the UI
	 * 
	 * @param notif the string to be notified to the user
	 * @param error the level of the notif importance
	 * @param eventBus
	 */
	public static void notifyUser(String notif, int error, EventBus eventBus) {
		System.out.println("Notif = "+notif);
		// should we log it too ?
		eventBus.fireEvent(new AddNotifEvent(notif, error));
	}
	
	/**
	 * 
	 * @param slot the slot in localStorage representing the day and period
	 * @return the index of the flexTable column 
	 */
	public static int storageSlotToFlexCol(int slot) {
		return slot%10;
	}
	
	/**
	 * 
	 * @param slot the slot in localStorage representing the day and period
	 * @return the index of the flexTable row
	 */
	public static int storageSlotToFlexRow(int slot) {
		return slot/10;
	}
	
	

}
