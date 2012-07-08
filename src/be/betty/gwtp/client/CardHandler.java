package be.betty.gwtp.client;

import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandler;
import com.allen_sauer.gwt.dnd.client.DragStartEvent;
import com.allen_sauer.gwt.dnd.client.VetoDragException;

public class CardHandler implements DragHandler {

	@Override
	public void onDragEnd(DragEndEvent event) {
		System.out.println("on drag end: "+ event);

	}

	@Override
	public void onDragStart(DragStartEvent event) {
		System.out.println("on drag start: "+ event);

	}

	@Override
	public void onPreviewDragEnd(DragEndEvent event) throws VetoDragException {
		System.out.println("on preview drag end: "+ event);

	}

	@Override
	public void onPreviewDragStart(DragStartEvent event)
			throws VetoDragException {
		System.out.println("on preview drag start: "+ event);

	}

}
