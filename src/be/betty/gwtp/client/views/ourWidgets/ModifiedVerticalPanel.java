package be.betty.gwtp.client.views.ourWidgets;

import java.util.Iterator;

import be.betty.gwtp.client.Storage_access;
import be.betty.gwtp.client.UiConstants;
import be.betty.gwtp.client.presenters.MainPresenter;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ModifiedVerticalPanel extends VerticalPanel {

//	@Override
//	public boolean remove(Widget w) {
//		int index = getWidgetIndex(w);
//
//		if (index != -1 && w instanceof CardWidget) {
//			CardWidget clone = ((CardWidget)w).cloneWidget();
//			clone.setStyleName(UiConstants.CSS_PLACED_CARD);
//			MainPresenter.cardDragController.makeDraggable(clone);
//			insert(clone, index);
//		}
//
//		return super.remove(w);
//	}
//
//	@Override
//	public void clear() {
//		Iterator<Widget> it = iterator();
//		while (it.hasNext()) {
//			Object o = it.next();
//			if (o instanceof Widget)
//				super.remove((Widget)o);
//			//it.remove();
//		}
//	}

}
