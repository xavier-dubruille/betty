package be.betty.gwtp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;

public class PopupAreYouSureEvent extends
		GwtEvent<PopupAreYouSureEvent.PopupAreYouSureHandler> {

	public static Type<PopupAreYouSureHandler> TYPE = new Type<PopupAreYouSureHandler>();
	private boolean yesIsCheck;

	public interface PopupAreYouSureHandler extends EventHandler {
		void onPopupAreYouSure(PopupAreYouSureEvent event);
	}

	public PopupAreYouSureEvent(boolean yesIsCheck) {
		this.yesIsCheck = yesIsCheck;
	}
	
	public boolean YesisCheck(){
		return yesIsCheck;
	}

	@Override
	protected void dispatch(PopupAreYouSureHandler handler) {
		handler.onPopupAreYouSure(this);
	}

	@Override
	public Type<PopupAreYouSureHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<PopupAreYouSureHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, boolean yesIsCheck) {
		source.fireEvent(new PopupAreYouSureEvent(yesIsCheck));
	}
}
