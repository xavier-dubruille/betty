package be.betty.gwtp.client.presenters;

import be.betty.gwtp.client.ClientUtils;
import be.betty.gwtp.client.Filter_kind;
import be.betty.gwtp.client.views.ourWidgets.CardWidget;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.google.inject.Inject;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;

public class SingleCardPresenter extends
PresenterWidget<SingleCardPresenter.MyView> {

	public interface MyView extends View {
		CardWidget getCardWidget();

	}

	private int teacherId;
	private int groupId;
	private int storageId;
	final private PopupPanel popupPanel = new PopupPanel(true);
	
	
	@Inject
	public SingleCardPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}
	@Inject AddNewCardPopupPresenter addNewCardPresenter;
	@Override
	protected void onBind() {
		super.onBind();
		createPopupMenu();
	}

	public void init(int myI) {
		storageId = myI;
		// TODO trouver un meilleur moyen de transmettre l'id au widget..
		getView().asWidget().setTitle(""+myI);
		
		CardWidget cardW = getView().getCardWidget();
		cardW.init(myI);
		cardW.addMouseDownHandler(new MouseDownHandler() {
			
			@Override
			public void onMouseDown(MouseDownEvent event) {
				event.preventDefault();
	            event.stopPropagation();
	            event.getNativeEvent().stopPropagation();
	            event.getNativeEvent().preventDefault();
				System.out.println("on click handler yezzu");
				int button = event.getNativeEvent().getButton();
		        if (button == NativeEvent.BUTTON_LEFT) {
		            return;
		        }

		        if (button == NativeEvent.BUTTON_RIGHT) {
		            
//		            ClientUtils.notifyUser("Jack est le meilleur! "+storageId, getEventBus());
		            System.out.println("right click");
					onRightClick(event.getClientX(), event.getClientY());

		        }	
			
			}
		});
	
	}

	/**
	 * Not used, but, in a proper version, should be.
	 * @param filter_kind
	 * @return
	 */
	public int getKindId(Filter_kind filter_kind) {
		switch (filter_kind) {
		case TEACHER: return teacherId;
		case GROUP: return groupId;
		}
		return 0;
	}

/*	public String getKindString(Filter_kind filter_kind) {
		switch (filter_kind) {
		case TEACHER: return teacher;
		case GROUP: return group;
		}
		return "";
	}*/
	
	@Override public String toString() {
		CardWidget cardW = getView().getCardWidget();
		if (cardW == null) return "widget Null";
		return cardW.getTeacher().getText();
	}
	
	Command AddCardCommand = new Command() {
		  public void execute() {
		    //deckPanel.showWidget(0);
		    popupPanel.hide();
		    addToPopupSlot(addNewCardPresenter);
		  }
		};
		 
		Command ModifiedCardCommand = new Command() {
		  public void execute() {
		    //deckPanel.showWidget(1);
		    popupPanel.hide();
		    Window.alert("Modified");
		  }
		};
		 
		Command DeleteCardCommand = new Command() {
		  public void execute() {
		    //deckPanel.showWidget(2);
		    popupPanel.hide();
		    Window.alert("Delete");
		  }
		};

		private void createPopupMenu() {
			  MenuBar popupMenuBar = new MenuBar(true);
			  MenuItem alertItem = new MenuItem("Add", true, AddCardCommand);
			  MenuItem sponserItem = new MenuItem("Delete ", true, DeleteCardCommand);
			 
			  popupPanel.setStyleName("popup");
			  alertItem.addStyleName("popup-item");
			  sponserItem.addStyleName("popup-item");
			 
			  popupMenuBar.addItem(alertItem);
			  popupMenuBar.addItem(sponserItem);
			 
			  popupMenuBar.setVisible(true);
			  popupPanel.add(popupMenuBar);
			}
			 
			public void onRightClick(int x, int y) {	  
			  popupPanel.setPopupPosition(x, y);
			  popupPanel.show();
			}


}
