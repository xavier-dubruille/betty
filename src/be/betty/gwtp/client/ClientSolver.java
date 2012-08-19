package be.betty.gwtp.client;

public class ClientSolver {

	public static String findBestRoom(int cardId, int day, int period) {
		//String[] possRooms = Storage_access.getPossibleRooms(cardId);
		
		//if (possRooms.length >0)
		//	return possRooms[0];
		//else
			return "";
	}

	public static int getColor(String cardID, int period, int day) {
		
		
		int cId = Integer.parseInt(cardID);
		int color = 0;

		String card = Storage_access.getCard(cardID);
		String t = Storage_access.getTeacherCard(card);
		String[] g = Storage_access.getGroupCard(card);
		//String[] r = Storage_access.get (card);

		
		// We'll go through all cards and see with are in conflict
		for (int i = 0; i<Storage_access.getNumberOfCard(); i++) {
			
			if (i == cId) continue;
			
			String c = Storage_access.getCard(i);
			if (Storage_access.getDayCard(c) != day || Storage_access.getPeriodCard(c) != period) continue;
			// let's check if there is teacher conflict
			if (Storage_access.getTeacherCard(c).equalsIgnoreCase(t))
				return 8;
			
			// let's check if there is room conflict
			
			// let's check if there is groups conflict
			for (String groupSource:g) 
				for (String groupDest:Storage_access.getGroupCard(c))
					if (groupSource.equals(groupDest)) return 8;
			
		}
		
	
		
		// let's check teacher and activity desiderata
		
		// let's check info from the server side solver
		
		
		return color;
	}	

}
