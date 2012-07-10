package be.betty.gwtp.server;

import java.util.HashMap;
import java.util.Map;

// TODO: met-t-on des assert ˆ toutes les methodes ?
public class Index {

	public final static String group = "group";
	public final static String section = "section";
	public final static String year = "year";
	public final static String course_name = "course_name";
	public final static String teacher_firstName = "teacher_firstName";
	public final static String courses_id = "courses_id";
	public final static String teacher_id = "teacher_id";
	public final static String period = "period";
	public final static String teacher_lastName = "teacher_lastName";
	public final static String mod = "mod";
	public final static String section_name = "section_name";

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

	public String getPeriod() {
		return generic_get(period);
	}

	public String getTeacherLastname() {
		return generic_get(teacher_lastName);
	}

	public String getMod() {
		return generic_get(mod);
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

		String sem = ("ORCO_NombrePeriodeSemaineSemestre" + choice_sem);
		// System.out.println("choice sem: "+choice_sem+" "+sem);

		for (int i = 0; i < line.length; i++) {

			if (line[i].equalsIgnoreCase("annŽe"))
				indexLine.put("year", i);

			else if (line[i].equalsIgnoreCase("IntitulŽ cours"))
				indexLine.put("course_name", i);

			else if (line[i].equalsIgnoreCase("PrŽnom"))
				indexLine.put("teacher_firstName", i);

			else if (line[i].equalsIgnoreCase("nom"))
				indexLine.put("teacher_lastName", i);

			else if (line[i].equalsIgnoreCase(sem)) {
				indexLine.put("period", i);
				// System.out.println("on a fixŽ l'index des periodes ˆ: "+i);
			}

			else if (line[i].equalsIgnoreCase("CodeCours"))
				indexLine.put("courses_id", i);

			else if (line[i].equalsIgnoreCase("PERS_Id"))
				indexLine.put("teacher_id", i);

			else if (line[i].equalsIgnoreCase("groupe"))
				indexLine.put("group", i);

			else if (line[i].equalsIgnoreCase("IntitulŽ Section"))
				indexLine.put("section_name", i);

			else if (line[i].equalsIgnoreCase("Section"))
				indexLine.put("section", i);

			else if (line[i].equalsIgnoreCase("Mode"))
				indexLine.put("mod", i);

			else if (line[i].equalsIgnoreCase("ORCO_SalleInformatique"))
				indexLine.put("info", i);

		}
		// System.out.println("indexline :"+indexLine);
		// System.out.println("line      :"+Arrays.toString(line));
	}

}
