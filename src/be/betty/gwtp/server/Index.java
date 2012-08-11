package be.betty.gwtp.server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// TODO: met-t-on des assert ˆ toutes les methodes ?
public class Index {

	private final static String group = "group";
	private final static String section = "section";
	private final static String year = "year";
	private final static String course_name = "course_name";
	private final static String teacher_firstName = "teacher_firstName";
	private final static String courses_id = "courses_id";
	private final static String teacher_id = "teacher_id";
	private final static String period1 = "period1";
	private final static String period2 = "period2";
	private final static String teacher_lastName = "teacher_lastName";
	private final static String mod = "mod";
	private final static String section_name = "section_name";
	private final static String type = "type";
	
	// these following (and only these) are used for the room file
	public static final int ROOM_CODE = 0;
	public static final int ROOM_TYPE = 2;
	public static final int ROOM_CONTENANCE = 1;
	public static final int ROOM_PROJO = 4;
	public static final int ROOM_SLIDE = 5;
	public static final int ROOM_RECORDER = 6;
	public static final int ROOM_BOARD = 7;
	public static final int ROOM_FLOOR = 3;

	private Map<String, Integer> indexLine;
	private String[] singleLine;
	

	public Index() {
		indexLine = new HashMap<String, Integer>();
	}

	public Map<String, Integer> getIndexLine() {
		return indexLine;
	}

	public void setIndexLine(Map<String, Integer> indexLine) {
		this.indexLine = indexLine;
	}

	public String getGroup() {
		String gr = singleLine[indexLine.get(group)];
		assert gr != null;
		return gr;
	}

	public String getSection() {
		String sec = generic_get(section);
		assert sec != null;
		return sec;
	}

	public String getYear() {
		return generic_get(year);
	}

	public String getCourseName() {
		return generic_get(course_name);
	}

	public String getTeacherFirstname() {
		return generic_get(teacher_firstName);
	}

	public String getCoursesId() {
		return generic_get(courses_id);
	}

	public String getTeacherId() {
		return generic_get(teacher_id);
	}

	public int getPeriod1() {
		return Integer.parseInt(generic_get(period1));
	}
	
	public int getPeriod2() {
		return Integer.parseInt(generic_get(period2));
	}

	public String getTeacherLastname() {
		return generic_get(teacher_lastName);
	}

	public String getMod() {
		return generic_get(mod);
	}

	public String getType() {
		return generic_get(type);
	}

	public String getSectionName() {
		return generic_get(section_name);
	}

	public String[] getSingleLine() {
		return singleLine;
	}

	public void setSingleLine(String[] singleLine) {
		this.singleLine = singleLine;
	}

	private String generic_get(String arg) {
		return singleLine[indexLine.get(arg)];
	}

	/**
	 * Construct the index place
	 * 
	 * @param line
	 * @param choice_sem
	 */
	public void putRightIndex(String[] line, int choice_sem) {

		for (int i = 0; i < line.length; i++) {

			if (line[i].equalsIgnoreCase("annŽe"))
				indexLine.put(year, i);

			else if (line[i].equalsIgnoreCase("IntitulŽ cours"))
				indexLine.put(course_name, i);

			else if (line[i].equalsIgnoreCase("PrŽnom"))
				indexLine.put(teacher_firstName, i);

			else if (line[i].equalsIgnoreCase("nom"))
				indexLine.put(teacher_lastName, i);

			else if (line[i].equalsIgnoreCase("ORCO_NombrePeriodeSemaineSemestre1")) {
				indexLine.put(period1, i);
				// System.out.println("on a fixŽ l'index des periodes ˆ: "+i);
			}
			else if (line[i].equalsIgnoreCase("ORCO_NombrePeriodeSemaineSemestre2"))
				indexLine.put(period2, i);

			else if (line[i].equalsIgnoreCase("CodeCours"))
				indexLine.put(courses_id, i);

			else if (line[i].equalsIgnoreCase("PERS_Id"))
				indexLine.put(teacher_id, i);

			else if (line[i].equalsIgnoreCase("groupe"))
				indexLine.put(group, i);

			else if (line[i].equalsIgnoreCase("IntitulŽ Section"))
				indexLine.put(section_name, i);

			else if (line[i].equalsIgnoreCase("Section"))
				indexLine.put(section, i);

			else if (line[i].equalsIgnoreCase("Mode"))
				indexLine.put(mod, i);

			else if (line[i].equalsIgnoreCase("ORCO_SalleInformatique") ||
					line[i].equalsIgnoreCase("type"))
				indexLine.put(type, i);

		}
		// System.out.println("indexline :"+indexLine);
		// System.out.println("line      :"+Arrays.toString(line));
	}


}
