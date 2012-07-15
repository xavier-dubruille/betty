package be.betty.gwtp.client;

import java.util.ArrayList;


import be.betty.gwtp.client.action.GetCardsResult;
import be.betty.gwtp.shared.BettyUtils;
import be.betty.gwtp.shared.dto.Card_dto;
import be.betty.gwtp.shared.dto.Course_dto;
import be.betty.gwtp.shared.dto.Group_dto;
import be.betty.gwtp.shared.dto.Teacher_dto;

import com.google.gwt.storage.client.Storage;

/**
 * This is were all access to the local storage will be made
 */
public class Storage_access {
	public static Storage stockStore;

	private static ArrayList<Integer> groups_map;
	private static ArrayList<Integer> teachers_map;
	private static ArrayList<Integer> courses_map;

	// Constants
	private static final String PROJECT_ON = "O";
	private static final String NUMBER_OF_CARD = "n";
	private static final String CARD_PREFIX = "K";
	private static final String TEACHER_PREFIX = "T";
	private static final String NUMBER_OF_TEACHER = "t";
	private static final String GROUP_PREFIX = "G";
	private static final String NUMBER_OF_GROUPS = "g";
	private static final String COURSE_PREFIX = "C";
	private static final String NUMBER_OF_COURSES = "c";
	private static final String STORAGE_SEPARATOR = "#";

	// if the folowing constant have to change:
	// then the method setCards() HAS TO be changed also !!
	private static final int COURSE_INDEX = 0;
	private static final int TEACHER_INDEX = 1;
	private static final int GROUP_INDEX = 2;
	private static final int SLOT_INDEX = 3;
	private static final int ROOM_INDEX = 4;
	
	static {
		stockStore = Storage.getLocalStorageIfSupported();
		if (stockStore == null) {
			// TODO: "You're browser is crap, get an other one" --> gestion
			// d'erreurs !
		}
		groups_map = new ArrayList<Integer>();
		teachers_map = new ArrayList<Integer>();
		courses_map = new ArrayList<Integer>();
		
		setDefaultValues();
	}



	/**
	 *  Put in localStorage some default values.. 
	 */
	private static void setDefaultValues() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Main method to populate the local Storage
	 * @param project_num
	 * @param result
	 */
	public static void populateStorage(String project_num, GetCardsResult result) {
		// TODO: il faut (mieu?) verifier si il y a déja des cartons dans le
		// local storage, si ce sont les bons, etc

		// TODO : si rajoute-t-on ici le choix de mettre à jour ou pas le local storage ?

		stockStore.setItem(PROJECT_ON, project_num);

		Storage_access.setTeachers(project_num, result.getTeachers());
		Storage_access.setGroups(project_num, result.getGroups());
		Storage_access.setCourses(project_num, result.getCourses());
		
		Storage_access.setCards(project_num, result.getCards());

	}

	/**
	 * 
	 * @param projectID
	 * @param arrayList
	 */
	private static void setCards(String projectID, ArrayList<Card_dto> cards) {
		String s = STORAGE_SEPARATOR;
		int i = 0;
		for (Card_dto c : cards) {
			// if the order change, some static constant HAVE to be changed also !!
			stockStore.setItem(CARD_PREFIX + i,
					courses_map.indexOf(c.getCourse()) + s + 
					teachers_map.indexOf(c.getTeacher())+ s + 
					groups_map.indexOf(c.getGroup()) +s +
					c.getSlot() + s +
					c.getRoom());
			i++;
		}
		stockStore.setItem(NUMBER_OF_CARD, "" + i);
	}
	

	private static void setTeachers(String projectID, ArrayList<Teacher_dto> teachers) {
		teachers_map.clear();
		int i = 0;
		for (Teacher_dto t: teachers) {
			stockStore.setItem(TEACHER_PREFIX  + i, t.getLastName()); // TODO: a completer..
			teachers_map.add(t.getBddId());
			i++;
		}
		stockStore.setItem(NUMBER_OF_TEACHER, "" + i);
	}

	
	private static void setCourses(String project_num, ArrayList<Course_dto> courses) {	
		courses_map.clear();
		int i = 0;
		for (Course_dto c : courses) {
			stockStore.setItem(COURSE_PREFIX  + i, c.getName());
			courses_map.add(c.getBddId());
			i++;
		}
		stockStore.setItem(NUMBER_OF_COURSES, "" + i);
	}
	
	
	private static void setGroups(String project_num, ArrayList<Group_dto> groups) {
		groups_map.clear();
		int i = 0;
		for (Group_dto g : groups) {
			stockStore.setItem(GROUP_PREFIX  + i, g.getCode());
			groups_map.add(g.getBddId());
			i++;
		}
		stockStore.setItem(NUMBER_OF_GROUPS, "" + i);
	}
	
	/**
	 * 
	 * @param cardID
	 * @param day From 1 to 7
	 * @param per period, from 1 to inf.
	 */
	public static void setSlotCard(int cardID, int day, int per) {
		assert day >= 1 && day <=7 ;
		assert per >= 1 ;
		int slot = per*10 + day;
		String[] s = getCard(cardID).split(STORAGE_SEPARATOR);
		s[SLOT_INDEX]=""+slot;
		stockStore.setItem(CARD_PREFIX+cardID, BettyUtils.join(s, STORAGE_SEPARATOR));
	}
	

	public static void revoveFromSlot(int cardID) {
		String[] s = getCard(cardID).split(STORAGE_SEPARATOR);
		s[SLOT_INDEX]="0";
		s[ROOM_INDEX]="0";
		stockStore.setItem(CARD_PREFIX+cardID, BettyUtils.join(s, STORAGE_SEPARATOR));
	}

	
	public static String getTeacher(int i) {
		return stockStore.getItem(TEACHER_PREFIX+i);
	}
	
	public static int getNumberOfTeacher() {
		return Integer.parseInt(stockStore.getItem(NUMBER_OF_TEACHER));
	}
	
	public static String getGroup(int i) {
		return stockStore.getItem(GROUP_PREFIX+i);
	}
	
	public static int getNumberOfGroup() {
		return Integer.parseInt(stockStore.getItem(NUMBER_OF_GROUPS));
	}
	
	public static String getCourse(int i) {
		return stockStore.getItem(COURSE_PREFIX+i);
	}
	
	public static int getNumberOfCourses() {
		return Integer.parseInt(stockStore.getItem(NUMBER_OF_COURSES));
	}

	public static String getCard(int i) {
		return stockStore.getItem(CARD_PREFIX+i);
	}
	
	public static int getNumberOfCard() {
		return Integer.parseInt(stockStore.getItem(NUMBER_OF_CARD));
	}
	
	public static String getSavedProject() {
		return stockStore.getItem(PROJECT_ON);
	}
	
	// these folowing methods do not depend directly on local storage, but it's easer this way
	public static String getCourseCard(String card) {
		return getCourse(Integer.parseInt(card.split(STORAGE_SEPARATOR)[COURSE_INDEX]));
	}
	public static String getTeacherCard(String card) {
		return getTeacher(Integer.parseInt(card.split(STORAGE_SEPARATOR)[TEACHER_INDEX]));
	}
	public static String getGroupCard(String card) {
		return getGroup(Integer.parseInt(card.split(STORAGE_SEPARATOR)[GROUP_INDEX]));
	}
	public static int getSlotCard(String card) {
		return Integer.parseInt(card.split(STORAGE_SEPARATOR)[SLOT_INDEX]);
	}
	public static int getRoomCard(String card) {
		return Integer.parseInt(card.split(STORAGE_SEPARATOR)[ROOM_INDEX]);
	}
	
	/**
	 * print to console the content of localStorage..
	 * For Debuggin' purposes !
	 */
	public static void printStorage() {
		System.out.println("*** Here is local storage content for project num "+ Storage_access.getSavedProject() + "****");
		
		System.out.println("** First, all the cards: **");
		for(int i=0;i < Storage_access.getNumberOfCard(); i++)
			System.out.println("Card "+i+" = "+Storage_access.getCard(i));
		
		
		System.out.println("** Then all the courses **");
		for(int i=0;i < Storage_access.getNumberOfCourses(); i++)
			System.out.println("Course "+i+" = "+Storage_access.getCourse(i));
		
		System.out.println("** Then all the teacher **");
		for(int i=0;i < Storage_access.getNumberOfTeacher(); i++)
			System.out.println("Teacher "+i+" = "+Storage_access.getTeacher(i));
		
		System.out.println("** Then all the groups **");
		for(int i=0;i < Storage_access.getNumberOfGroup(); i++)
			System.out.println("Group "+i+" = "+Storage_access.getGroup(i));
		
		System.out.println("**All cards in a nicer way...**");

		for(int i=0;i < Storage_access.getNumberOfCard(); i++) {
			String card = Storage_access.getCard(i);
			System.out.println();
			System.out.println("Card "+i+" = ");
			System.out.println("    CouseName = "+Storage_access.getCourseCard(card));
			System.out.println("    TeacherName = "+Storage_access.getTeacherCard(card));
			System.out.println("    GroupName = "+Storage_access.getGroupCard(card));
			System.out.println("    Slot = "+Storage_access.getSlotCard(card));
			System.out.println("    Room = "+Storage_access.getRoomCard(card));
			
		}
	}


}
