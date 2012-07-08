package be.betty.gwtp.client;

import com.google.gwt.user.client.ui.SimplePanel;
import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;

/**
 * DropController which allows a widget to be dropped on a SimplePanel drop
 * target when the drop target does not yet have a child widget.
 */
public class CellDropControler extends SimpleDropController {

	private final SimplePanel dropTarget;

	public CellDropControler(SimplePanel dropTarget) {
		super(dropTarget);
		this.dropTarget = dropTarget;
	}

	@Override
	public void onDrop(DragContext context) {
		dropTarget.setWidget(context.draggable);
		super.onDrop(context);
	}

	@Override
	public void onPreviewDrop(DragContext context) throws VetoDragException {
		if (dropTarget.getWidget() != null) {
			throw new VetoDragException();
		}
		super.onPreviewDrop(context);
	}
}
