package be.betty.gwtp.client;

/**
 * 
 * The purpose of this class, is to optimize some actions normally made on local Storage.
 * 
 * So, it has to be constructed from local storage, and not contain informations not present in LocalStorage
 * (in order to keep the application working off-line) 
 *
 */
public class LocalOptimisation {

	static boolean[][][] busyRooms; // true if busy, false if available
	public static void initAvailableRoom(){
		busyRooms= new boolean[Storage_access.getNbDays()][Storage_access.getNbPeriods()][Storage_access.getNumberOfRoom()];
		System.out.println("init local optimisation, num of room= "+Storage_access.getNumberOfRoom());
		for (String cardId: Storage_access.getAllPlacedCard()) {
			String c = Storage_access.getCard(cardId);
			int day = Storage_access.getDayCard(c);
			int period = Storage_access.getPeriodCard(c);
			int room = Storage_access.getRoomCard(c);
			placeCard(0, day, period, ""+room);
		}
	}
	public static void placeCard(int cardId, int day, int period, String room) {
		if (room != null && !room.equals("-1")) {
			System.out.println("place card in local optimisation, array day size = "+ busyRooms.length);
			System.out.println("place card in local optimisation, array rooms size= "+busyRooms[0][0].length);
			busyRooms[day-1][period-1][Integer.parseInt(room)] = true;
		}
	}

	public static void removeCard(int cardId, int day, int period, int room) {
		busyRooms[day][period][room] = false;
	}

	public static int availableRoomForAt(String cardId, int day, int period) {
		boolean b[] = busyRooms[day-1][period-1];
		String[] p = Storage_access.getPossibleRooms(cardId);
		int count = 0;
		for (int i= 0; i<p.length; i++) 
			if (b[Integer.parseInt(p[i])])
				count++;

		return count;
	}

}
