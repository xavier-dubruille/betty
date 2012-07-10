package be.betty.gwtp.client;

import java.util.ArrayList;

import com.google.gwt.storage.client.Storage;

/**
 * This is were all access to the local storage will be made
 */
public class Storage_access {
	public static Storage stockStore;

	// Constants
	private static final String PROJECT_ON = "O";
	private static final String NUMBER_OF_CARD = "n";
	private static final String CARD_PREFIX = "C";
	private static final String TEACHER_PREFIX = "T";
	private static final String NUMBER_OF_TEACHER = "t";

	static {
		stockStore = Storage.getLocalStorageIfSupported();
		if (stockStore == null) {
			// TODO: "You're browser is crap, get an other one" --> gestion
			// d'erreur !
		}
	}

	public static void setCards(String projectID, ArrayList<String> c) {
		setCards(projectID, c, false);
	}

	public static void setCards(String projectID, ArrayList<String> c,
			boolean force) {
		// TODO: il faut (mieu?) verifier si il y a déja des cartons dans le
		// local storage, si ce sont les bons, etc

		if (!force && projectID.equals(stockStore.getItem(PROJECT_ON)))
			return; // no need to update, not here

		int i = 0;
		for (String s : c) {
			stockStore.setItem(CARD_PREFIX  + i, s);
			i++;
		}
		stockStore.setItem(NUMBER_OF_CARD, "" + i);

	}
	
	public static String getCard(int i) {
		return stockStore.getItem(CARD_PREFIX+i);
	}
	
	public static int getNumberOfCard() {
		return Integer.parseInt(stockStore.getItem(NUMBER_OF_CARD));
	}
	
	public static void setTeachers(String projectID, ArrayList<String> t) {
		setTeachers(projectID, t, false);
	}

	public static void setTeachers(String projectID, ArrayList<String> t,
			boolean force) {
		// TODO Check le contenu du local storage ?
		if (!force && projectID.equals(stockStore.getItem(PROJECT_ON)))
			return; // no need to update, not here

		int i = 0;
		for (String s : t) {
			stockStore.setItem(TEACHER_PREFIX  + i, s);
			i++;
		}
		stockStore.setItem(NUMBER_OF_TEACHER, "" + i);
	}
	
	public static String getTeacher(int i) {
		return stockStore.getItem(TEACHER_PREFIX+i);
	}
	
	public static int getNumberOfTeacher() {
		return Integer.parseInt(stockStore.getItem(NUMBER_OF_TEACHER));
	}
	
}
