package be.betty.gwtp.client;

import java.util.Arrays;


import be.betty.gwtp.client.presenters.MainPresenter;
import be.betty.gwtp.server.FileUpServlet;

public class ClientSolver {


	/**
	 * This is called to find the best room for a specified card.
	 * It's used when droping a card on a "non-room" view
	 * 
	 * @param cardId the card
	 * @param day
	 * @param period
	 * @return
	 */
	public static String findBestRoom(int cardId, int day, int period) {
		String[] possRooms = Storage_access.getPossibleRooms(cardId);
//		ClientUtils.notifyUser("Possible room for card "+cardId+" ="+Arrays.toString(possRooms), 1, MainPresenter.eventBus);
		System.out.println("Possible room for card "+cardId+" ="+Arrays.toString(possRooms));
		if (possRooms.length >0) {
			return possRooms[0];
//			for (String i: Storage_access.getAllPlacedCard()) {
//		
//				
//				String c = Storage_access.getCard(i);
//				if (Storage_access.getDayCard(c) != day || Storage_access.getPeriodCard(c) != period) continue;
//			}
	}
		else
			return "-1";
	}

	/**
	 * Construct a cellstate Object. 
	 * It will be colled when a card-drag is started, it will then be called for each Cell
	 * 
	 * @param cardID the card witch is dragged
	 * @param period
	 * @param day
	 * @return
	 */
	public static CellState getCellState(String cardID, int period, int day) {
		
		
		//int cId = Integer.parseInt(cardID);
		int color = 0;

		CellState cellState = new CellState(0);
		String card = Storage_access.getCard(cardID);
		String t = Storage_access.getTeacherCard(card);
		String[] g = Storage_access.getGroupCard(card);
		//String[] r = Storage_access.get (card);

		
		// We'll go through all cards and see with are in conflict
		for (String i: Storage_access.getAllPlacedCard()) {
			
			if (i.equals(cardID)) continue;
			
			String c = Storage_access.getCard(i);
			if (Storage_access.getDayCard(c) != day || Storage_access.getPeriodCard(c) != period) continue;
			// let's check if there is teacher conflict
			if (Storage_access.getTeacherCard(c).equalsIgnoreCase(t)) {
				cellState.setColor(8);
				cellState.setReason("Teacher conflict");
				return cellState;
			}
			
			// let's check if there is room conflict
			
			// let's check if there is groups conflict
			for (String groupSource:g) 
				for (String groupDest:Storage_access.getGroupCard(c))
					if (groupSource.equals(groupDest)) {
						cellState.setColor(8);
						cellState.setReason("Group conflict");
						return cellState;
					}
			
		}
		
	
		
		// let's check teacher and activity desiderata
		
		// let's check info from the server side solver
		
		
		return cellState;
	}	

}
