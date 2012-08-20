package be.betty.gwtp.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.hibernate.Session;
import org.hibernate.Transaction;

import be.betty.gwtp.server.bdd.Activity_entity;
import be.betty.gwtp.server.bdd.Course;
import be.betty.gwtp.server.bdd.Group_entity;
import be.betty.gwtp.server.bdd.ProjectInstance;
import be.betty.gwtp.server.bdd.Project_entity;
import be.betty.gwtp.server.bdd.Room;
import be.betty.gwtp.server.bdd.Teacher;

public class CreateUserProject {

	private String activities_file;
	private String rooms_file;
	private String constraints_file;
	private int choice_sem = 1;
	private String csvDelemiter;
	private Index index;
	private Project_entity current_project;

	private Map<Integer, Activity_entity> cards;
	private Map<String, Teacher> teachers;
	private Map<String, Group_entity> groups;
	private Map<String, Course> courses;
	private Activity_entity c;
	private int cardMapId;
	private  Map<String, Room> rooms;

	public CreateUserProject(Project_entity projectToBeSaved) {
		index = new Index();
		this.activities_file = projectToBeSaved.getCourse_file();
		this.current_project = projectToBeSaved;
		this.rooms_file = projectToBeSaved.getRoom_file();

		cards = new HashMap<Integer, Activity_entity>();
		teachers = new HashMap<String, Teacher>();
		groups = new HashMap<String, Group_entity>();
		courses = new HashMap<String, Course>();
		rooms = new HashMap<String, Room>();

		cardMapId = 0;
	}

	/**
	 * Construct the state based on the first/main file containing all the
	 * courses/teachers/cards
	 * 
	 * @throws NoFileException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void createStateFromCardFile() throws NoFileException,
	FileNotFoundException, IOException {

		String filePath = this.activities_file;
		if (filePath == null || filePath.equals(""))
			throw new NoFileException();
		if ( !filePath.endsWith("xls"))
			throw new NotSuportedFileException();

		readCardFromXLS(filePath);

	}



	/**
	 * 
	 * Called for each lines of the file containing the courses,teachers, ect
	 * It's separeted from the rest because it can be called from any type of
	 * file (in our case: csv and xls)
	 * 
	 * @param line
	 *            the String table representing a line of the file
	 * @param cardId
	 *            the card id, must be different each time the method is called.
	 */
	private void constructCardStateFromLine(String[] line) {

		assert line != null && line.length > 0 : "You must provide a valid tab representing the line";

		index.setSingleLine(line);

		int periods_s1 = index.getPeriod1();
		assert periods_s1 >= 0 && periods_s1 < 50 : "You probably have a problem with your periods value ("
				+ periods_s1 + ")";

		//System.out.println("construct card state from line "+Arrays.toString(line));

		// si c'est un cours "sans cartons": on arr�te l�..
		if (index.getCourseName().equalsIgnoreCase("stages") ||
				index.getCoursesId().equalsIgnoreCase("T310T99")) 
			return;

		if (!teachers.containsKey(index.getTeacherId()))
			teachers.put(index.getTeacherId(), new Teacher(index.getTeacherId(), index.getTeacherFirstname(),
					index.getTeacherLastname(), current_project));


		if (!courses.containsKey(index.getCoursesId()))
			courses.put(index.getCoursesId(), new Course(index.getCoursesId(), index.getCourseName(),
					index.getMod(), index.getType(), index.getPeriod1(), index.getPeriod2(), current_project));

		String groupCode = index.getYear() + index.getSection() +index.getGroup();
		if (!groups.containsKey(groupCode))
			groups.put(groupCode, new Group_entity(index.getYear(),  index.getSection(),index.getGroup(), current_project));


		// detection suppleants ...
		c = getCardWhere(index.getCoursesId(), index.getGroup());
		if (c != null) {
			//c.getSupleant.add(teachers.get(index.getTeacherId()));

			return;
		}



		// si c'est une classe et que c'est un Zx avec x >1 on ne cr�e pas de carton, ms on update..
		c = getBelonginActivityIfExist(index.getCoursesId(), index.getMod(), index.getGroup(), index.getTeacherId());
		if (c != null) {
			c.getGroupSet().add(groups.get(groupCode));
			return;
		}


		// et sinon, on cree un nouveau carton..
		c = new Activity_entity(teachers.get(index.getTeacherId()),
				groups.get(groupCode),
				courses.get(index.getCoursesId()), current_project);
		cards.put(cardMapId++, c);


		//		// // faut pas oublier ca..
		//		// // if(teacher_lastName.equalsIgnoreCase("{N}"))
		//		// // teacher_lastName="Indefini";

	}

	/**
	 *  
	 *  it will set to each courses all the possible room for him
	 *  
	 *  pre: rooms and activities has to be full-filled !
	 */
	public void setRoomToCourse() {
		for (Course c: courses.values()) {
			
			c.setPossibleRooms(getRoomsLike( c.getMode(), c.getType()));
		}

	}


	/**
	 * 
	 * It will find all rooms corresponding to the criteria
	 * 
	 * @param rooms
	 * @param mode
	 * @param type
	 * @return
	 */
	private ArrayList<Room> getRoomsLike( String mode, String type) {
		ArrayList<Room> possRoom = new ArrayList<Room>();
		//System.out.println("trouvons des locaux pour un cours en: "+mode+" et de type: "+type);
		for (Room r: rooms.values()) {
		
			
			//System.out.print("==> qd est-il du local "+r.getCode()+" ?");
			if (!type.equalsIgnoreCase(r.getType())) {
				//System.out.println(" .. pas du bon type");
				continue;
			}
			if (mode.equalsIgnoreCase("classe") && r.getContenance() < 42) {
				//System.out.println(" .. trop petit");
				continue;
			}
			if (mode.equalsIgnoreCase("groupe") && r.getContenance() < 24) {
				//System.out.println(" .. trop petit");
				continue;
			}

			//System.out.println(" .. parfait.");
			possRoom.add(r);
		}
		System.out.println("poss room = "+possRoom);
		return possRoom;
	}


	/**
	 * 
	 * 
	 * @param coursesId
	 * @param mod
	 * @param group
	 * @param teacherId
	 * @return
	 */	
	private Activity_entity getBelonginActivityIfExist(String coursesId,
			String mod, String group, String teacherId) {
		char classe = group.charAt(0);
		char num = group.charAt(1);

		if (!mod.equalsIgnoreCase("classe"))
			return null;

		// we want to keep only the "second"(maybe third or fourth) group 
		// of a class (e.g. L2, L3, M2, ..)
		if (num <= 49 || num >= 55)
			return null;

		for (Activity_entity a: cards.values()) {
			if (a.getCourse().getCode().equalsIgnoreCase(coursesId) &&
					a.getTeacher().getCode().equalsIgnoreCase(teacherId) &&
					a.getClasse() == classe) {
				return a;
			}
		}
		return null;
	}

	private Activity_entity getCardWhere(String coursesId, String group) {
		for (Activity_entity a: cards.values()) {
			if (a.getCourse().getCode().equalsIgnoreCase(coursesId) &&
					a.getGroupSet().contains(groups.get(group))) {
				return a;
			}
		}
		return null;
	}

	public void saveStateToBdd() {
		System.out.println("save in bdd");
		//System.out.println("activities = "+cards);

		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();

		s.save(current_project);

		ProjectInstance pi = new ProjectInstance("Default instance", 0);
		s.save(pi);
		current_project.getProjectInstances().add(pi);

		for (Room r: rooms.values()) {
			s.save(r);
			current_project.getRooms().add(r);
		}

		for (Teacher te : teachers.values()) {
			s.save(te);
			current_project.getTeachers().add(te);
		}


		for (Course c : courses.values()) {
			System.out.println("course about to be saved ==> "+c.getPossibleRooms());
			s.save(c);
			current_project.getCourses().add(c);
		}

		for (Group_entity g: groups.values()) {
			s.save(g);
			current_project.getGroups().add(g);
		}



		for (Activity_entity a: cards.values()) {


			Activity_entity ac;
			int p1 = a.getCourse().getNbPeriodS1();
			int p2 = a.getCourse().getNbPeriodS2();

			if (p1 != 0) {
				a.getTeacher().setSem1(true);
				for (int i=1; i<=p1; i++) {
					ac = a.clone(i,"1");
					s.save(ac);
					current_project.getActivities().add(ac);
				}
			}

			if (p2 != 0) {
				a.getTeacher().setSem2(true);
				for (int i=1; i<=p2; i++) {
					ac = a.clone(i,"2");
					s.save(ac);
					current_project.getActivities().add(ac);
				}
			}
		}

		t.commit();
		s.close();
	}
	/**
	 * 
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void readCardFromXLS(String filePath) throws FileNotFoundException,
	IOException {

		assert filePath != null : "filePath can't be null !";
		assert filePath.length() > 0 : "filePath can't be empty";

		String[] line;

		InputStream inp = new FileInputStream(filePath);
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));
		Sheet sheet1 = wb.getSheetAt(0);
		line = new String[sheet1.getRow(0).getPhysicalNumberOfCells()];

		for (Row row : sheet1) {
			for (Cell cell : row) {

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					line[cell.getColumnIndex()] = cell.getRichStringCellValue().getString();
					break; 
				case Cell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(cell)) {
						line[cell.getColumnIndex()] = cell.getDateCellValue()
								.toString();
					} else {

						line[cell.getColumnIndex()] = ""
								+ (int) Math.ceil( cell.getNumericCellValue());
					}
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					line[cell.getColumnIndex()] = ""
							+ cell.getBooleanCellValue();

				default:
					line[cell.getColumnIndex()] = "";
				}
			}
			// System.out.println(Arrays.asList(line));
			if (row.getRowNum() == 0)
				index.putRightIndex(line, this.choice_sem);
			else 
				constructCardStateFromLine(line);

		}
		inp.close();

	}

	/**
	 * PRE: the file must exist and point to the right file !
	 * PRE: The file must be xls type !
	 * 
	 * @throws NoFileException
	 * @throws FileNotFoundException
	 */
	public void createStateFromRoomFile() throws NoFileException, FileNotFoundException, IOException {
		System.out.println("######### entering create state from room file with file: "+this.rooms_file);
		String[] line;

		InputStream inp = new FileInputStream(this.rooms_file);
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));
		Sheet sheet1 = wb.getSheetAt(0);
		line = new String[sheet1.getRow(0).getPhysicalNumberOfCells()];

		for (Row row : sheet1) {
			for (Cell cell : row) {

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					line[cell.getColumnIndex()] = cell.getRichStringCellValue()
					.getString();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(cell)) {
						line[cell.getColumnIndex()] = cell.getDateCellValue()
								.toString();
					} else {

						line[cell.getColumnIndex()] = ""
								+ (int) cell.getNumericCellValue();
					}
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					line[cell.getColumnIndex()] = ""
							+ cell.getBooleanCellValue();

				default:
					line[cell.getColumnIndex()] = "";
				}
			}
			// System.out.println(Arrays.asList(line));
			if (row.getRowNum() == 0) {
				// index.putRightIndex(line, this.choice_sem);
				// faut voir si c necessaire, car ce fichier a ete creer par nous, et ne dont pas s'adapter a
				// plusieur config differente
			}
			else
				constructRoomStateFromLine(line);

		}


		inp.close();


	}

	private void constructRoomStateFromLine(String[] line) {

		//System.out.println("##@@ trying to save line : "+Arrays.asList(line));



		try {
			Room r = new Room();
			r.setCode(line[Index.ROOM_CODE]);
			r.setType(line[Index.ROOM_TYPE]);
			r.setContenance(line[Index.ROOM_CONTENANCE]);
			r.setProject(current_project);
			r.setBoard(Integer.parseInt(line[Index.ROOM_BOARD]));
			r.setFloor(Integer.parseInt(line[Index.ROOM_FLOOR]));
			r.setProjo(Integer.parseInt(line[Index.ROOM_PROJO]));
			r.setRecorder(Integer.parseInt(line[Index.ROOM_RECORDER]));
			r.setSlide(Integer.parseInt(line[Index.ROOM_SLIDE]));
			rooms.put(r.getCode(),r);
		} catch (NumberFormatException e) {
			System.out.println("la lecture de cette ligne a foire: "+Arrays.toString(line));
			return; 
		}



	}

}
