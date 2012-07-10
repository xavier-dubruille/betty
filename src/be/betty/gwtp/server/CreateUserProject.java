package be.betty.gwtp.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.hibernate.Session;
import org.hibernate.Transaction;

import be.betty.gwtp.server.bdd.Activity;
import be.betty.gwtp.server.bdd.Course;
import be.betty.gwtp.server.bdd.Group_entity;
import be.betty.gwtp.server.bdd.Project_entity;
import be.betty.gwtp.server.bdd.Teacher;

public class CreateUserProject {

	private String activities_file;
	private String rooms_file;
	private String constraints_file;
	private int choice_sem = 1;
	private String csvDelemiter;
	private Index index;
	private Session sess;
	private Project_entity current_project;

	// doit tre supprimŽ, mais dans le doute, je le laisse encore un peu..
	private CreateUserProject(String activities_file, String rooms_file,
			String constraints_file, Project_entity p) {

		index = new Index();

		this.current_project = p;
		this.activities_file = activities_file;
		this.rooms_file = rooms_file;
		this.constraints_file = constraints_file;
	}

	public CreateUserProject(Project_entity projectToBeSaved) {
		index = new Index();
		this.activities_file = projectToBeSaved.getCourse_file();
		this.current_project = projectToBeSaved;
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
		if (!filePath.endsWith("csv") && !filePath.endsWith("xls"))
			throw new NotSuportedFileException();

		sess = HibernateUtils.getSession();
		if (filePath.endsWith("csv"))
			readCardFromCSV(filePath);
		else if (filePath.endsWith("xls"))
			readCardFromXLS(filePath);
		sess.close();
	}

	/**
	 * Read the csv file containing the courses (the cards) pre:filePath has to
	 * be a valid String
	 * 
	 * @param filePath
	 *            the file path of the csv file containing the course. has to be
	 *            a valid path.
	 * @throws FileNotFoundException
	 */
	private void readCardFromCSV(String filePath) throws FileNotFoundException {

		assert filePath != null : "filePath can't be null !";
		assert filePath.length() > 0 : "filePath can't be empty";

		String[] line;

		Scanner sc = new Scanner(new File(filePath));
		String firstLine = sc.nextLine();
		setDelemiter(firstLine);
		line = firstLine.split(csvDelemiter);

		index.putRightIndex(line, this.choice_sem);

		// should we save the rest of the info ?

		while (sc.hasNext()) {

			line = sc.nextLine().split(csvDelemiter);
			constructCardStateFromLine(line);
		}

	}

	/**
	 * will set the most probable delimiter for a csv file
	 * 
	 * @param line
	 *            a line of the file
	 */
	private void setDelemiter(String line) {
		assert line != null && line.length() > 0 : "You must provide a valid string delimitor";
		if (line.split(";").length > 2)
			this.csvDelemiter = ";";
		else if (line.split(",").length > 2)
			this.csvDelemiter = ",";
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

		int periods = Integer.parseInt(index.getPeriod());
		assert periods >= 0 && periods < 50 : "You probably have a problem with your periods value ("
				+ periods + ")";

		// System.out.println("construct card state from line "+Arrays.toString(line));
		if (periods == 0) {
			// System.out.println(line[indexLine.get("course_name")]+" "+line[indexLine.get("period")]);
			// System.out.println("index line pour les periods: "+indexLine.get("period"));
			return; // this course doesn't take place this semester
		}

		Transaction tran = sess.beginTransaction();

		// TODO: jpense pas que sauvegarder le (meme) projet ˆ chaque ligne soit
		// une bonne idŽe.. loin de lˆ !
		sess.update(current_project);

		List l = null;

		// System.out.println("teacher id= "+index.getTeacherId());
		l = sess.createQuery("from Teacher where code=? and project_id=?")
				.setString(0, index.getTeacherId())
				.setString(1, "" + current_project.getId()).list();

		// System.out.println("list = "+l);
		Teacher t;
		if (l.size() == 0) {
			t = new Teacher(index.getTeacherId(), index.getTeacherFirstname(),
					index.getTeacherLastname(), current_project);
			sess.save(t);
			current_project.getTeachers().add(t);
		} else
			t = (Teacher) l.get(0);

		String group_code = "" + index.getYear() + index.getSection()
				+ index.getGroup();
		Group_entity g;
		l = sess.createQuery("from Group_entity where code=? and project_id=?")
				.setString(0, group_code)
				.setString(1, "" + current_project.getId()).list();
		if (l.size() == 0) {
			g = new Group_entity(group_code, current_project);
			sess.save(g);
			current_project.getGroups().add(g);
		} else
			g = (Group_entity) l.get(0);

		Course c;
		l = sess.createQuery("from Course where code=? and project_id=?")
				.setString(0, index.getCoursesId())
				.setString(1, "" + current_project.getId()).list();
		if (l.size() == 0) {
			c = new Course(index.getCoursesId(), index.getCourseName(),
					index.getMod(), periods, current_project);
			sess.save(c);
			current_project.getCourses().add(c);
		} else
			c = (Course) l.get(0);

		Activity a = new Activity(t, g, c, current_project);
		sess.save(a);
		current_project.getActivities().add(a);
		tran.commit();
		//
		// // System.out.println(index.getCourseName());
		// // System.out.println(Arrays.asList(line));
		//
		// // faut pas oublier ca..
		// // if(teacher_lastName.equalsIgnoreCase("{N}"))
		// // teacher_lastName="Indefini";

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
			if (row.getRowNum() == 0)
				index.putRightIndex(line, this.choice_sem);
			else
				constructCardStateFromLine(line);

		}
		inp.close();

	}

}
