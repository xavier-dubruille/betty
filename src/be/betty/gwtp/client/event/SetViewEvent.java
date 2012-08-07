package be.betty.gwtp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;

public class SetViewEvent extends GwtEvent<SetViewEvent.SetViewHandler> {

	public static Type<SetViewHandler> TYPE = new Type<SetViewHandler>();
	private String index1;
	private String index2;

	public interface SetViewHandler extends EventHandler {
		void onSetView(SetViewEvent event);
	}

	public SetViewEvent(String index12, String index22) {
		this.index1 = index12;
		this.index2 = index22;
	}

	public String getIndex1() {
		return index1;
	}

	public String getIndex2() {
		return index2;
	}

	@Override
	protected void dispatch(SetViewHandler handler) {
		handler.onSetView(this);
	}

	@Override
	public Type<SetViewHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<SetViewHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, String index1, String index2) {
		source.fireEvent(new SetViewEvent(index1, index2));
	}
}
