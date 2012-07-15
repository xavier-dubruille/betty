package be.betty.gwtp.client;

import be.betty.gwtp.client.event.DropCardEvent;
import be.betty.gwtp.client.event.ProjectListModifyEvent;

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
		System.out.println("*** drag end :"+event.getSource().toString());
		//getEventBus().fireEvent( new DropCardEvent());

	}

	@Override
	public void onPreviewDragStart(DragStartEvent event)
			throws VetoDragException {
		System.out.println("on preview drag start: "+ event);

	}

}
