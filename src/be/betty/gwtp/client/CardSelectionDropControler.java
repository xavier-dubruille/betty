/*
 * This class has been modified by us (Xavier Dubruille and Kevin Jacoby), but 
 * the original was :
 * 
 * Copyright 2009 Fred Sauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package be.betty.gwtp.client;

import be.betty.gwtp.client.event.DropCardEvent;
import be.betty.gwtp.client.presenters.MainPresenter;
import be.betty.gwtp.client.views.ourWidgets.CardWidget;
import be.betty.gwtp.client.views.ourWidgets.ModifiedVerticalPanel;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.drop.AbstractInsertPanelDropController;
import com.allen_sauer.gwt.dnd.client.util.DOMUtil;
import com.allen_sauer.gwt.dnd.client.util.DragClientBundle;
import com.allen_sauer.gwt.dnd.client.util.LocationWidgetComparator;

/**
 * A {@link DropController} for instances of {@link VerticalPanel}.
 * 
 * TODO VerticalPanel performance is slow because of positioner DOM manipulation
 */
public class CardSelectionDropControler extends AbstractInsertPanelDropController {

	/**
	 * Label for IE quirks mode workaround.
	 */
	private static final Label DUMMY_LABEL_IE_QUIRKS_MODE_OFFSET_HEIGHT = new Label("x");
	private EventBus eventBus;

	/**
	 * Construct an {@link CardSelectionDropControler}.
	 * 
	 * @param dropTarget the {@link VerticalPanel} drop target
	 */
	public CardSelectionDropControler(VerticalPanel dropTarget, EventBus eventBus) {
		super(dropTarget);
		this.eventBus = eventBus;
	}

	@Override
	protected LocationWidgetComparator getLocationWidgetComparator() {
		return LocationWidgetComparator.BOTTOM_HALF_COMPARATOR;
	}

	@Override
	protected Widget newPositioner(DragContext context) {
		// Use two widgets so that setPixelSize() consistently affects dimensions
		// excluding positioner border in quirks and strict modes
		SimplePanel outer = new SimplePanel();
		outer.addStyleName(DragClientBundle.INSTANCE.css().positioner());

		// place off screen for border calculation
		RootPanel.get().add(outer, -500, -500);

		// Ensure IE quirks mode returns valid outer.offsetHeight, and thus valid
		// DOMUtil.getVerticalBorders(outer)
		outer.setWidget(DUMMY_LABEL_IE_QUIRKS_MODE_OFFSET_HEIGHT);

		int width = 0;
		int height = 0;
		for (Widget widget : context.selectedWidgets) {
			width = Math.max(width, widget.getOffsetWidth());
			height += widget.getOffsetHeight();
		}

		SimplePanel inner = new SimplePanel();
		inner.setPixelSize(width - DOMUtil.getHorizontalBorders(outer), height
				- DOMUtil.getVerticalBorders(outer));

		outer.setWidget(inner);

		return outer;
	}

	@Override
	public void onDrop(DragContext context) {

		Widget w = context.selectedWidgets.get(0);
		if ( w != null && w instanceof CardWidget) {
			int id = Integer.parseInt(w.getElement().getTitle()); //TODO faut un meilleur moyen que le titre!
			Widget w2 =  MainPresenter.allCards.get(""+id);
			if (w2 != null && w2.getParent() instanceof ModifiedVerticalPanel)
				((ModifiedVerticalPanel)w2.getParent()).realRemove(w2);
			MainPresenter.allCards.put(""+id, (CardWidget) w);
			eventBus.fireEvent(new DropCardEvent(id,0,0,0));
		}
		super.onDrop(context);

	}
}
