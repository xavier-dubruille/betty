package be.betty.gwtp.client.views.ourWidgets;

import java.util.ArrayList;
import java.util.Iterator;

import be.betty.gwtp.client.Storage_access;
import be.betty.gwtp.client.UiConstants;
import be.betty.gwtp.client.presenters.MainPresenter;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ModifiedVerticalPanel extends VerticalPanel {

	@Override
	public boolean remove(Widget w) {
		int index = getWidgetIndex(w);

		if (index != -1 && w instanceof CardWidget) {
			CardWidget clone = ((CardWidget)w).cloneWidget();
			clone.setStyleName(UiConstants.CSS_PLACED_CARD);
			MainPresenter.allCards.put(clone.getElement().getTitle(), clone);
			insert(clone, index);
		}

		return super.remove(w);
	}

	@Override
	public void clear() {
//		for (Widget w : this.getChildren()) {
//			System.out.println("widget num:"+w.getElement().getTitle()+" toString= "+w);
//			super.remove(w);
//		}

//		for (int i=0; i< this.getWidgetCount(); i++)
//			super.remove(0);
		
		ArrayList<Widget> a = new ArrayList<Widget> ();
		Iterator<Widget> it = iterator();
		while (it.hasNext()) {
			
			a.add( it.next());
			//super.remove(w);
			//it.remove();
		}
		
		for (Widget w: a)
			super.remove(w);
	}

	public boolean realRemove(Widget w) {
		return super.remove(w);
	}
	
}
