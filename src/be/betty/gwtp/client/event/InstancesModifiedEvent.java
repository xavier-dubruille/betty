package be.betty.gwtp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;

public class InstancesModifiedEvent extends
		GwtEvent<InstancesModifiedEvent.InstancesModifiedHandler> {

	public static Type<InstancesModifiedHandler> TYPE = new Type<InstancesModifiedHandler>();

	public interface InstancesModifiedHandler extends EventHandler {
		void onInstancesModified(InstancesModifiedEvent event);
	}

	public InstancesModifiedEvent() {
	}

	@Override
	protected void dispatch(InstancesModifiedHandler handler) {
		handler.onInstancesModified(this);
	}

	@Override
	public Type<InstancesModifiedHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<InstancesModifiedHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source) {
		source.fireEvent(new InstancesModifiedEvent());
	}
}
