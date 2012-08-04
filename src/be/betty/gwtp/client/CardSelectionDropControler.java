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
import com.google.gwt.user.client.Window;
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
		/*
		 * Petite explication du code..
		 * Il y a w(/w1) qui est le widget "droppe" et w2 qui est le widget fesant partie de la liste des widgets (ds la selection)
		 * 
		 * Plusieurs cas peuvent arriver : 
		 * 1) le carton est originaire de la view et donc, sont homologue w2 doit etre enleve grafiquement
		 *    mais aussi de la map "allCards". Le carton doit etre retire du storage et la bdd (cf DropCardEvent).
		 * 2) le carton provient deja des cartons de selections et ne dois alors rien faire, sauf
		 * 	  si le carton est un carton place, alors on demande a l'utilisateur que faire
		 */
		Widget w = context.selectedWidgets.get(0);
		if ( w != null && w instanceof CardWidget) {
			int id = Integer.parseInt(w.getElement().getTitle()); //TODO faut un meilleur moyen que le titre!
			CardWidget w2 =  MainPresenter.allCards.get(""+id);
			CardWidget w1 = (CardWidget) w;
			
			// dans tt les cas, faut supprimer la version dans le selectionPanel
			// et mettre le bon widget dans la map (allcards)
			if (w2 != null && w2.getParent() instanceof ModifiedVerticalPanel)
				((ModifiedVerticalPanel)w2.getParent()).realRemove(w2);
			MainPresenter.allCards.put(""+id, w1);
			
			// si ca vient de la vue, on drop la card
			if (!w1.isFromSelectionPanel()) 
				eventBus.fireEvent(new DropCardEvent(id,0,0,0));
			
			// si ca vient du panneau de selection, on demande a l'utilisateur
			if ( w1.isFromSelectionPanel() && w1.isPlaced()) {
				boolean b = Window.confirm("Are you sure you want to unset this card ?");
				if (b) {
					eventBus.fireEvent(new DropCardEvent(id,0,0,0));
					w1.setStyleName(UiConstants.CSS_CARD);
				}
			}
			
			// et sinon on ne fait rien :p 

		}
		super.onDrop(context);

	}
}
