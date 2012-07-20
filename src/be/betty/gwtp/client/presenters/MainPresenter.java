package be.betty.gwtp.client.presenters;

import java.util.ArrayList;

import be.betty.gwtp.client.CardHandler;
import be.betty.gwtp.client.CellDropControler;
import be.betty.gwtp.client.Filter_kind;
import be.betty.gwtp.client.Storage_access;
import be.betty.gwtp.client.action.GetCards;
import be.betty.gwtp.client.action.GetCardsResult;
import be.betty.gwtp.client.action.SaveCardDropAction;
import be.betty.gwtp.client.action.SaveCardDropActionResult;
import be.betty.gwtp.client.event.CardFilterEvent;
import be.betty.gwtp.client.event.DropCardEvent;
import be.betty.gwtp.client.event.CardFilterEvent.CardFilterHandler;
import be.betty.gwtp.client.event.DropCardEvent.DropCardHandler;
import be.betty.gwtp.client.event.ProjectListModifyEvent;
import be.betty.gwtp.client.place.NameTokens;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.VerticalPanelDropController;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.gwtplatform.common.client.IndirectProvider;
import com.gwtplatform.common.client.StandardProvider;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class MainPresenter extends
		Presenter<MainPresenter.MyView, MainPresenter.MyProxy> {

	public interface MyView extends View {
		public Label getMainLabel();

		public AbsolutePanel getDndPanel();

		public Image getDndImage();

		Label getContent();

		void setContent(Label content);

		VerticalPanel getCards_panel();

		void constructFlex(PickupDragController cardDragController);
	}

	public static final Object SLOT_Card = new Object();
	public static final Object SLOT_BOARD = new Object();
	public static final Object SLOT_OPTION_SELECION = new Object();
	
	@Inject CardSelectionOptionPresenter cardSelectionOptionPresenter;
	@Inject BoardPresenter boardPresenter;
	
	private IndirectProvider<SingleCardPresenter> cardFactory;
	@Inject DispatchAsync dispatcher;

	protected ArrayList<SingleCardPresenter> allCards;

	@ProxyCodeSplit
	@NameToken(NameTokens.main)
	public interface MyProxy extends ProxyPlace<MainPresenter> {
	}
	
	
	private Storage stockStore;

	@Inject
	public MainPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, final Provider<SingleCardPresenter> provider) {
		super(eventBus, view, proxy);
		cardFactory = new StandardProvider<SingleCardPresenter>(provider);
		stockStore = Storage.getLocalStorageIfSupported();
		
		allCards = new ArrayList<SingleCardPresenter>();
	}

	@Override
	protected void revealInParent() {
		// RevealRootContentEvent.fire(this, this);
		RevealContentEvent.fire(this, HeaderPresenter.SLOT_CONTENT, this);
	}

	private String project_num;
	public static PickupDragController cardDragController;
	public static VerticalPanelDropController cardDropPanel;
	private CardFilterHandler filterHandler = new CardFilterHandler() {

		@Override
		public void onCardFilter(CardFilterEvent event) {

			// TODO : attention, si on decide de faire 2 combobox, les id venan
			// de la combox et
			// du local storage ne seront plus les mêmes... et donc on pourra pas
			// faire ainsi.

			switch (event.getFilterType()) {
			case TEACHER:
				writeCardWidgets(Filter_kind.TEACHER,
						Storage_access.getTeacher(event.getFilterObjId()));
				break;
			case GROUP:
				writeCardWidgets(Filter_kind.GROUP,
						Storage_access.getGroup(event.getFilterObjId()));
			}
		}
	};
	
	private DropCardHandler dropCardHandler = new DropCardHandler() {
		@Override public void onDropCard(DropCardEvent event) {
			System.out.println("$$$$$ Catch event.. day="+event.getDay()+" and period= "+event.getPeriod()+" cardid="+event.getCardID());
			int activity_bddId = Storage_access.getBddIdCard(Storage_access.getCard(event.getCardID()));
			
			// "first", save to bdd (it's asynchronous)
			dispatcher.execute(new SaveCardDropAction(event.getDay(), event.getPeriod(), activity_bddId, event.getRoom()),
					new AsyncCallback<SaveCardDropActionResult>() {
						@Override public void onFailure(Throwable arg0) {
							System.out.println("save 'dropped card' failed !!");
							System.out.println("tostring"+arg0);
							System.out.println("get message:"+arg0.getMessage());

						}

						@Override public void onSuccess(SaveCardDropActionResult result) {
							System.out.println("save card drop action sucess!!!");

						}
					});

			// Then save in local Storage
			if (event.getDay() != 0) {
				//System.out.println("tiiiittllee"+allCards.get(event.getCardID()).getWidget().getTitle());
				//System.out.println(allCards.size());
				allCards.get(event.getCardID()).getWidget().addStyleName("cardPlaced");
				Storage_access.setSlotCard(event.getCardID(), event.getDay(), event.getPeriod());
				//TODO: faut aussi l'envoyer � la bdd, ou un truc du genre
			}
			else {
				allCards.get(event.getCardID()).getWidget().addStyleName("card");
				Storage_access.revoveFromSlot(event.getCardID());
				// faut aussi l'envoyer � la bdd, ou un truc du genre
			}
		}
	};
	

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		project_num = request.getParameter("p", "-1");
		// System.out.println("prepare from request: "+name);
	}

	@Override
	protected void onBind() {
		super.onBind();
		
		
		
		set_dnd();
		registerHandler(getEventBus().addHandler(CardFilterEvent.getType(),
				filterHandler));
		
		registerHandler(getEventBus().addHandler(
				DropCardEvent.getType(), dropCardHandler));

	}

	private void set_dnd() {

		// create a DragController to manage drag-n-drop actions
		// note: This creates an implicit DropController for the boundary panel
		cardDragController = new PickupDragController(RootPanel.get(), false);
		cardDragController.addDragHandler(new CardHandler());
		cardDropPanel = new VerticalPanelDropController(getView().getCards_panel());

		// TODO v�rifier si il y a des lag en utilisant l'application sur le
		// serveur
		// mettre en commentaire ces deux lignes

		// VerticalPanelDropController dropController = new
		// VerticalPanelDropController(getView().getDrop_cards_panel());
		// cardDragController.registerDropController(dropController);

		// dragController.makeDraggable(getView().getDndImage());

		// AbsolutePositionDropController sp = new
		// AbsolutePositionDropController(
		// getView().getDropPanel());
		// IndexedDropController dropController = new
		// IndexedDropController(getView().getDropPanel());

		// dragController.registerDropController(sp);
		// dragController.makeDraggable(getView().getMainLabel());
		// dragController.makeDraggable(getView().getHtml_panel());
		// for (CardPresenter c : allCards)
		// dragController.makeDraggable(c.getView().getWholePanel());

	}

	@Override
	protected void onReset() {
		super.onReset();

		String login = "";
		String sess = "";
		if (stockStore != null) {
			sess = stockStore.getItem("session_id");
			login = stockStore.getItem("login");
		}
		if (sess == null) {
			getView().getMainLabel().setText("Please (re)log first");
			return;
		}

		getView().getMainLabel().setText(
				"Welcome " + login + " *****  Projet num " + project_num);

		GetCards action = new GetCards(project_num);
		dispatcher.execute(action, new AsyncCallback<GetCardsResult>() {

			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
				// arg0.printStackTrace();
				System.err.println("***failure:" + arg0);

			}

			@Override
			public void onSuccess(GetCardsResult result) {


				Storage_access.populateStorage(project_num,result);
				
				//Storage_access.printStorage();
				
				print_da_page();
				// getView().getContent().setText(result.getActivities().toString());

			}

		});

	}

	/**
	 * PRE: local storage Must (already) be filled with the right info POST: The
	 * page is drawed on screen
	 * 
	 * Print the cards, the board, with the information in the local storage
	 */
	private void print_da_page() {
		System.out.println("**** Hell yeah, print da page");
		// des Assert ici pour verifier qq truc sur le local storage serait p-e
		// bien..

		setInSlot(SLOT_OPTION_SELECION, cardSelectionOptionPresenter);
		cardSelectionOptionPresenter.init();
		
		setInSlot(SLOT_BOARD, boardPresenter);

		cardDragController.registerDropController(cardDropPanel);
		writeCardWidgetsFirstTime();
		//getView().constructFlex(cardDragController);
	
		
		
		//CellDropControler dropController = new CellDropControler(simplePanel);
	   //	cardDragController.registerDropController(dropController);

	}

	private void writeCardWidgetsFirstTime() {
		setInSlot(SLOT_Card, null);
		allCards.clear();
		for (int i = 0; i < Storage_access.getNumberOfCard(); i++) {
			final int myI = i;
			cardFactory.get(new AsyncCallback<SingleCardPresenter>() {

				@Override
				public void onSuccess (SingleCardPresenter result) {
					addToSlot(SLOT_Card, result);
					result.init(myI);
					cardDragController.makeDraggable(result.getWidget(), result.getView().getCourse());
					cardDragController.makeDraggable(result.getWidget(), result.getView().getTeacher());
					allCards.add(result);

				}

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}
			});
		}

	}

	private void writeCardWidgets(Filter_kind filter_kind, String toFilter) {

		for (SingleCardPresenter c : allCards) {
			//System.out.println(c.getView().getHeader());
			 if(c.getKindString(filter_kind).equals(toFilter))
				 c.getWidget().setVisible(false);
		}

	}
}
