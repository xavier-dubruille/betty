package be.betty.gwtp.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import be.betty.gwtp.client.action.GetCardsResult;
import be.betty.gwtp.shared.BettyUtils;
import be.betty.gwtp.shared.dto.Card_dto;
import be.betty.gwtp.shared.dto.Course_dto;
import be.betty.gwtp.shared.dto.Group_dto;
import be.betty.gwtp.shared.dto.ProjectInstance_dto;
import be.betty.gwtp.shared.dto.Room_dto;
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
	private static ArrayList<Integer> rooms_map;

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
	private static final String CURRENT_INSTANCE_BDDID = "u";
	private static final String NUMBER_OF_INSTANCE = "U";
	private static final String INSTANCE_PREFIX = "I";
	private static final String NUMBER_OF_DAYS = "d";
	private static final String NUMBER_OF_PERIODS = "p";
	private static final String NUMBER_OF_ROOM = "r";
	private static final String ROOM_PREFIX = "R";
	private static final String SESSID_PREFIX = "session_id"; // can't be change before you're sure no one use the old way
	private static final String PROJECT_NAME_PREFIX = "P";
	private static final String SEMESTER_PREFIX = "s";
	private static final String ALL_PLACED_CARD = "a";


	// if one of the following constants come to change,
	// then the method setCards() HAS TO be changed also !!
	private static final int COURSE_INDEX = 0;
	private static final int TEACHER_INDEX = 1;
	private static final int GROUP_SIZE_INDEX = 5;
	private static final int SLOT_INDEX = 2;
	private static final int ROOM_INDEX = 3;
	private static final int BDDID_INDEX = 4;
	private static final int GROUPS_INDEX = 6;

	// if one of the following constants come to change,
	// then the method setProjectInstance() HAS TO be changed also !!
	private static final int PI_BDDID_INDEX = 0;
	private static final int PI_STATUS_INDEX = 1; //e.g. if the instance has been deleted and have to be disabled
	private static final int PI_NUMBER = 2;
	private static final int PI_DESC = 3;

	// if one of the following constants come to change,
	// then the method setCourse() HAS TO be changed also !!
	private static final int CO_NAME_INDEX = 0;
	private static final int CO_PR_SIZE_INDEX = 1; //e.g. if the instance has been deleted and have to be disabled
	private static final int CO_PR_INDEX = 2;

	


	static {
		stockStore = Storage.getLocalStorageIfSupported();
		if (stockStore == null) {
			// TODO: "You're browser is crap, get an other one" --> gestion
			// d'erreurs !
			ClientUtils.actionFailed("Fatal Error. Youre browser doesn't seem to be compatible.. You should get the last version.");
		}
		groups_map = new ArrayList<Integer>();
		teachers_map = new ArrayList<Integer>();
		courses_map = new ArrayList<Integer>();
		rooms_map = new ArrayList<Integer>();

	}



	/**
	 *  Put in localStorage some default values.. 
	 */
	private static void setDefaultValues() {
		stockStore.setItem(NUMBER_OF_DAYS, "5");
		stockStore.setItem(NUMBER_OF_PERIODS, "6");
		stockStore.setItem(ALL_PLACED_CARD, "");

	}

	public static void clearPlacedCard() {
		stockStore.setItem(ALL_PLACED_CARD, "");
	}

	public static void clear() {
		stockStore.removeItem(PROJECT_NAME_PREFIX);
		stockStore.clear();
	}

	public static String getProjectName() {
		return stockStore.getItem(PROJECT_NAME_PREFIX);
	}
	
	public static void setProjectName(String projectName) {
		stockStore.setItem(PROJECT_NAME_PREFIX, projectName);
	}
	
	public static String getSemester() {
		return stockStore.getItem(SEMESTER_PREFIX);
	}
	
	public static void setSemester(String semester) {
		stockStore.setItem(SEMESTER_PREFIX, semester);
	}
	
	/**
	 * Main method to populate the local Storage
	 * @param project_num
	 * @param result
	 */
	public static void populateStorage(String project_num, GetCardsResult result) {
		// TODO: il faut (mieu?) verifier si il y a d�ja des cartons dans le
		// local storage, si ce sont les bons, etc

		// TODO : si rajoute-t-on ici le choix de mettre � jour ou pas le local storage ?

		stockStore.setItem(PROJECT_ON, project_num);
		//System.out.println("cards a la population du storage = "+result.getCards());

		setDefaultValues(); // if we don't set some values, we need some default one

		Storage_access.setTeachers(project_num, result.getTeachers());
		Storage_access.setGroups(project_num, result.getGroups());
		Storage_access.setCourses(project_num, result.getCourses());

		Storage_access.setCards(project_num, result.getCards());
		System.out.println("getRoom = "+result.getRooms());
		Storage_access.setRooms(project_num, result.getRooms());

		//System.out.println("***--** Instances to be populated: "+result.getProjectInstances());
		//System.out.println("***--** Default Instance ="+result.getDefaultInstance());
		Storage_access.setProjectInstances(result.getProjectInstances(), 0); //result.getDefaultInstance());

		//printStorage();

	}

	private static void setRooms(String project_num, ArrayList<Room_dto> rooms) {
		rooms_map.clear();
		int i = 0;
		for (Room_dto r: rooms) {
			stockStore.setItem(ROOM_PREFIX  + i, r.getCode()); 
			rooms_map.add(r.getBddId());
			i++;
		}
		stockStore.setItem(NUMBER_OF_ROOM, "" + i);

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
			String groups = "";
			for (int j=0; j< c.getGroupSet().size(); j++)
				groups+= s+ groups_map.indexOf(c.getGroupSet().get(j));


			// if the order change, some static constant HAVE to be changed also !!
			stockStore.setItem(CARD_PREFIX + i,
					courses_map.indexOf(c.getCourse()) + s + 
					teachers_map.indexOf(c.getTeacher())+ s + 
					c.getSlot() + s +
					c.getRoom() + s+
					c.getBddId() + s +
					c.getGroupSet().size() +
					groups
					);
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
		String possRoom ;
		String s = STORAGE_SEPARATOR;
		List<Integer> ps;
		int i = 0;
		for (Course_dto c : courses) {
			ps = c.getPossibleRoom();
			possRoom = "";
			for (int j=0; j<ps	.size(); j++)
				possRoom += s+ rooms_map.indexOf(ps.get(j));

			// if this order come to change, then the corresponding
			// Constants HAVE to change !!
			stockStore.setItem(COURSE_PREFIX  + i, c.getName() + s+ 
					ps.size() + 
					possRoom);
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
	 * if this come to change, then some constants HAS TO be changed !! (see above)
	 * 
	 * @param pis
	 * @param defaultInstance the index of instance (so between 0 and the number of instances) to be the current instance
	 */
	public static void setProjectInstances(ArrayList<ProjectInstance_dto> pis, int defaultInstance) {
		int i = 0;
		String s = STORAGE_SEPARATOR;

		for (ProjectInstance_dto in : pis) {

			stockStore.setItem(INSTANCE_PREFIX +i,""
					+in.getBddId() +s
					+"T"+s
					+in.getLocalNum() +s
					+in.getDescription() );
			i++;
		}
		stockStore.setItem(NUMBER_OF_INSTANCE, "" + i);

		int instance = (defaultInstance > 0 && defaultInstance < i) ? defaultInstance : 0;
		stockStore.setItem(CURRENT_INSTANCE_BDDID, ""+getInstanceBddId(instance));
	}

	public static String getSessId() {
		return stockStore.getItem(SESSID_PREFIX);
	}
	
	public static void setSessId(String sessId) {
		stockStore.setItem(SESSID_PREFIX, sessId);
	}
	
	
	public static int getNumberOfInstance() {
		return Integer.parseInt(stockStore.getItem(NUMBER_OF_INSTANCE));
	}

	public static String getInstance(int i) {
		return stockStore.getItem(INSTANCE_PREFIX+i);
	}

	/**
	 * 
	 * @param cardID
	 * @param day From 1 to 7
	 * @param per period, from 1 to inf.
	 */
	public static void placeCard(int cardID, int day, int per, int room) {
		assert day >= 1 && day <=7 ;
		assert per >= 1 ;
		int slot = per*10 + day;
		String[] s = getCard(cardID).split(STORAGE_SEPARATOR);
		s[SLOT_INDEX]=""+slot;
		stockStore.setItem(CARD_PREFIX+cardID, BettyUtils.join(s, STORAGE_SEPARATOR));
		
		stockStore.setItem(ALL_PLACED_CARD,stockStore.getItem(ALL_PLACED_CARD)+STORAGE_SEPARATOR+cardID);
		System.out.println("all placed card = "+Arrays.toString(getAllPlacedCard()));
	}

	
	public static void revoveFromSlot(int cardID) {
		String card = getCard(cardID);
		if (card == null) {
			// TODO: en cas d'erreur comme celle-ci, ca pourrait etre bien de recharger le Local Storage.
			return;
		}
		String[] s = card.split(STORAGE_SEPARATOR);
		s[SLOT_INDEX]="0";
		s[ROOM_INDEX]="0";
		stockStore.setItem(CARD_PREFIX+cardID, BettyUtils.join(s, STORAGE_SEPARATOR));
	}

	public static String[] getAllPlacedCard() {
		return stockStore.getItem(ALL_PLACED_CARD).substring(1).split(STORAGE_SEPARATOR);
	}
	/**
	 * 
	 * @param currentProjectInstance the index (in localStorage) of the Instance
	 */
	public static void setCurrentProjectInstanceBddId(int currentProjectInstance) {
		setCurrentProjectInstanceBddId_fromBddID(getInstanceBddId(currentProjectInstance));
	}
	
	public static String getBddIdFromCardId(int cardId) {
		return stockStore.getItem(CARD_PREFIX+cardId).split(STORAGE_SEPARATOR)[BDDID_INDEX];
	}

	/**
	 * 
	 * @param currInstanceBddId the bddId of the instance
	 */
	public static void setCurrentProjectInstanceBddId_fromBddID(
			int currInstanceBddId) {
		stockStore.setItem(CURRENT_INSTANCE_BDDID, ""+currInstanceBddId);
	}

	private static String getWholeCourse(int i) {
		return stockStore.getItem(COURSE_PREFIX+i);
	}
	
	public static int getCurrentProjectInstanceBDDID() {
		String current = stockStore.getItem(CURRENT_INSTANCE_BDDID);
		assert current != null;

		return Integer.parseInt(current);
	}

	public static int getInstanceBddId(int i) {
		String instance = stockStore.getItem(INSTANCE_PREFIX+i);
		return Integer.parseInt(instance.split(STORAGE_SEPARATOR)[PI_BDDID_INDEX]);
	}

	public static int getInstanceLocalNum(int i) {
		String instance = stockStore.getItem(INSTANCE_PREFIX+i);
		return Integer.parseInt(instance.split(STORAGE_SEPARATOR)[PI_NUMBER]);
	}

	public static String getInstanceDesc(int i) {
		String instance = stockStore.getItem(INSTANCE_PREFIX+i);
		return instance.split(STORAGE_SEPARATOR)[PI_DESC];
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
		String course = stockStore.getItem(COURSE_PREFIX+i);
		return course.split(STORAGE_SEPARATOR)[CO_NAME_INDEX];
	}

	public static int getNumberOfCourses() {
		return Integer.parseInt(stockStore.getItem(NUMBER_OF_COURSES));
	}

	public static String getCard(int i) {
		return stockStore.getItem(CARD_PREFIX+i);
	}
	



	public static String getCard(String i) {
		return stockStore.getItem(CARD_PREFIX+i);
	}


	public static String getRoom(int i) {
		return stockStore.getItem(ROOM_PREFIX+i);
	}

	
	public static int getNumberOfCard() {
		return Integer.parseInt(stockStore.getItem(NUMBER_OF_CARD));
	}


	public static int getNbDays() {
		return Integer.parseInt(stockStore.getItem(NUMBER_OF_DAYS));
	}
	
	public static int getNumberOfRoom() {
		return Integer.parseInt(stockStore.getItem(NUMBER_OF_ROOM));
	}

	public static int getNbPeriods() {
		return Integer.parseInt(stockStore.getItem(NUMBER_OF_PERIODS));
	}

	public static boolean isCardPlaced(String cardId) {
		String card = stockStore.getItem(CARD_PREFIX+cardId);
		return (getSlotCard(card) !=0);
	}


	/**
	 * 
	 * @return the bddId of the current project
	 */
	public static int getSavedProject() {
		return Integer.parseInt(stockStore.getItem(PROJECT_ON));
	}

	// these folowing methods do not depend directly on local storage, but it's easer this way
	public static String getCourseCard(String card) {
		return getCourse(Integer.parseInt(card.split(STORAGE_SEPARATOR)[COURSE_INDEX]));
	}
	public static String getTeacherCard(String card) {
		return getTeacher(Integer.parseInt(card.split(STORAGE_SEPARATOR)[TEACHER_INDEX]));
	}
	public static int getDayCard(String card) {
		return Integer.parseInt(card.split(STORAGE_SEPARATOR)[SLOT_INDEX])%10;
	}
	public static int getPeriodCard(String card) {
		return Integer.parseInt(card.split(STORAGE_SEPARATOR)[SLOT_INDEX])/10;
	}
	public static int getTeacherIdCard(String card) {
		return Integer.parseInt(card.split(STORAGE_SEPARATOR)[TEACHER_INDEX]);
	}
	public static String[] getGroupCard(String card) {
		String[] c = card.split(STORAGE_SEPARATOR);
		int groupSize = Integer.parseInt(c[GROUP_SIZE_INDEX]);
		String[] group_codes = new String[groupSize];
		//System.out.println("debug: groupsize ="+groupSize);
		for (int i=0; i< groupSize; i++) {
			group_codes[i] = getGroup(Integer.parseInt(c[GROUPS_INDEX + i]));
			//System.out.println(group_codes[i]);
		}
		return group_codes;
	}
	public static int[] getGroupsIdCard(String card) {
		String[] c = card.split(STORAGE_SEPARATOR);
		int groupSize = Integer.parseInt(c[GROUP_SIZE_INDEX]);
		int [] groupIds = new int[groupSize];
		for (int i=0; i< groupSize; i++)
			groupIds[i] = Integer.parseInt(c[GROUPS_INDEX + i]);
		return groupIds;
	}
	public static int getSlotCard(String card) {
		return Integer.parseInt(card.split(STORAGE_SEPARATOR)[SLOT_INDEX]);
	}
	public static int getRoomCard(String card) {
		return Integer.parseInt(card.split(STORAGE_SEPARATOR)[ROOM_INDEX]);
	}
	public static int getBddIdCard(String card) {
		return Integer.parseInt(card.split(STORAGE_SEPARATOR)[BDDID_INDEX]);
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

		System.out.println("** Then all the Rooms **");
		for(int i=0;i < Storage_access.getNumberOfRoom(); i++)
			System.out.println("Room "+i+" = "+Storage_access.getRoom(i));

		System.out.println("** Then all the courses **");
		for(int i=0;i < Storage_access.getNumberOfCourses(); i++)
			System.out.println("Course "+i+" = "+Storage_access.getWholeCourse(i));

		System.out.println("** Then all the teacher **");
		for(int i=0;i < Storage_access.getNumberOfTeacher(); i++)
			System.out.println("Teacher "+i+" = "+Storage_access.getTeacher(i));

		System.out.println("** Then all the groups **");
		for(int i=0;i < Storage_access.getNumberOfGroup(); i++)
			System.out.println("Group "+i+" = "+Storage_access.getGroup(i));

		System.out.println("** Then all the projects instances **");
		for(int i=0;i < Storage_access.getNumberOfInstance(); i++)
			System.out.println("Instance"+i+" = "+Storage_access.getInstance(i));

		System.out.println("**All cards in a nicer way...**");

		for(int i=0;i < Storage_access.getNumberOfCard(); i++) {
			String card = Storage_access.getCard(i);
			System.out.println();
			System.out.println("Card "+i+" = ");
			System.out.println("    CouseName = "+Storage_access.getCourseCard(card));
			System.out.println("    TeacherName = "+Storage_access.getTeacherCard(card));
			System.out.println("    Group codes = "+Arrays.toString(Storage_access.getGroupCard(card)));
			System.out.println("    Slot = "+Storage_access.getSlotCard(card));
			System.out.println("    Room = "+Storage_access.getRoomCard(card));

		}
	}









}
