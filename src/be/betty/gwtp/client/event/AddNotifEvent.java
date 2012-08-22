package be.betty.gwtp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import java.lang.String;
import com.google.gwt.event.shared.HasHandlers;

/**
 * 
 * 
 */
public class AddNotifEvent extends GwtEvent<AddNotifEvent.AddNotifHandler> {

	public static Type<AddNotifHandler> TYPE = new Type<AddNotifHandler>();
	private String notif;
	private int error;

	public interface AddNotifHandler extends EventHandler {
		void onAddNotif(AddNotifEvent event);
	}

	public AddNotifEvent(String notif, int error) {
		this.notif = notif;
		this.error = error;
	}

	public String getNotif() {
		return notif;
	}
	
	public int getError(){
		return error;
	}
	

	@Override
	protected void dispatch(AddNotifHandler handler) {
		handler.onAddNotif(this);
	}

	@Override
	public Type<AddNotifHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<AddNotifHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, String notif, int error) {
		source.fireEvent(new AddNotifEvent(notif, error));
	}
}
