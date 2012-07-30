package be.betty.gwtp.server.solver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.sf.cpsolver.ifs.model.Constraint;
import net.sf.cpsolver.ifs.model.Model;
import net.sf.cpsolver.ifs.solution.Solution;
import net.sf.cpsolver.ifs.util.DataProperties;
import net.sf.cpsolver.ifs.util.ToolBox;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hibernate.Session;
import org.hibernate.Transaction;

import be.betty.gwtp.server.HibernateUtils;
import be.betty.gwtp.server.bdd.ActivityState;
import be.betty.gwtp.server.bdd.Activity_entity;
import be.betty.gwtp.server.bdd.Group_entity;
import be.betty.gwtp.server.bdd.ProjectInstance;
import be.betty.gwtp.server.bdd.Project_entity;
import be.betty.gwtp.server.bdd.Room;
import be.betty.gwtp.server.bdd.Teacher;

/**
 * Simple Timetabling Problem. <br>
 * <br>
 * The problem is modelled in such a way that every lecture was represented by a
 * variable, resource as a constraint and every possible location of an activity
 * in the time and space was represented by a single value. It means that a
 * value stands for a selection of the time (starting time slot), and one of the
 * available rooms. Binary dependencies are of course represented as constraints
 * as well.
 * 
 * This class, like all the classes in this package are greatly inspired from the 
 * Amazing work of "Tomas Muller" for is "Iterative Forward Search".
 * His original Copyright has, of course, been removed, but it can be found in
 * the related doc.
 * 
 * <br>
 *          This library is free software; you can redistribute it and/or modify
 *          it under the terms of the GNU Lesser General Public License as
 *          published by the Free Software Foundation; either version 3 of the
 *          License, or (at your option) any later version. <br>
 * <br>
 *          This library is distributed in the hope that it will be useful, but
 *          WITHOUT ANY WARRANTY; without even the implied warranty of
 *          MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *          Lesser General Public License for more details. <br>
 * <br>
 *          You should have received a copy of the GNU Lesser General Public
 *          License along with this library; if not see
 *          <a href='http://www.gnu.org/licenses/'>http://www.gnu.org/licenses/</a>.
 */
public class TimetableModel extends Model<Activity, Location> {
	private final String GROUP_PREFIX = "g";
	private final String TEACHER_PREFIX = "t";
	private final String ROOM_PREFIX = "r";

	private static org.apache.log4j.Logger sLogger = org.apache.log4j.Logger.getLogger(TimetableModel.class);
	private int iNrDays, iNrHours, projectBddId;

	private TimetableModel(int nrDays, int nrHours, int projectBddId) {
		super();
		iNrDays = nrDays;
		iNrHours = nrHours;
		this.projectBddId = projectBddId;
	}

	public int getNrDays() {
		return iNrDays;
	}

	public int getNrHours() {
		return iNrHours;
	}


	public static TimetableModel loadFromHibernate (int projectBddId) {

		TimetableModel m = new TimetableModel(5, 6, projectBddId); //TODO faudrait prendre les jour/period de la bdd..

		HashMap<String, Resource> resTab = new HashMap<String, Resource>();

		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();


		Project_entity p = (Project_entity) s.get(Project_entity.class, projectBddId);


		for (Teacher tea: p.getTeachers()) {
			Resource r = new Resource("t"+tea.getId(), Resource.TYPE_INSTRUCTOR, tea.getName());
			//ici on rajoutera ses contraintes..
			resTab.put(r.getResourceId(), r);
			m.addConstraint(r);
		}


		for (Room ro: p.getRooms()) {
			Resource r = new Resource("r"+ro.getId(), Resource.TYPE_ROOM, ro.getCode());
			// ici on pourra rajouter ses contraintes
			resTab.put(r.getResourceId(), r);
			m.addConstraint(r);
		}


		for (Group_entity g: p.getGroups()) {
			Resource r = new Resource("g"+g.getId(), Resource.TYPE_CLASS, g.getCode());
			// ici on pourra rajouter ses contraintes
			resTab.put(r.getResourceId(), r);
			m.addConstraint(r);
		}

		System.out.println("restab ("+resTab.size()+")==> "+resTab);
		int i=0;
		for (be.betty.gwtp.server.bdd.Activity_entity ac: p.getActivities()) {
			i++;
			Activity a = new Activity(1, ""+ac.getId(), ac.getCourse().getName());
			// ici on pourra rajouter ses contraintes

			// let's add the teachers
			a.addResourceGroup(resTab.get("t"+ac.getTeacher().getId()));

			// then the possible rooms
			List<Resource> gr = new ArrayList<Resource>();
			String mode = ac.getCourse().getMode();
			int nbStudent = ac.getNumberOfStudents();
			for (Room ro: p.getRooms()) {
				if (true) //TODO faut rajouter les bonnes verif :)
					gr.add(resTab.get("r"+ro.getId()));
			}
			a.addResourceGroup(gr);

			// and finally the group(s)
			a.addResourceGroup(resTab.get("g"+ac.getGroup().getId()));

			m.addVariable(a);
			a.init();
			System.out.println("ressourceGroupFrom activity n¡ "+ac.getId()+" = "+a.getResourceGroups());


		}

		System.out.println("nb of activity (variable) = "+i);
		t.commit();
		s.close();

		return m;
	}

	public void saveToHibernate(Solution<Activity, Location> solution, int instanceBddIdToSaveIn) {



		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();

		Project_entity p = (Project_entity) s.get(Project_entity.class, this.projectBddId);
		ProjectInstance pi = new ProjectInstance();
		
		
		p.getProjectInstances().add(pi);
		s.save(p);

		for (Activity a : variables()) {
			if (a.getAssignment() != null) {
				Location location = a.getAssignment();
				//System.out.println("	StartTime="+String.valueOf(location.getSlot()));
				ActivityState as = new ActivityState();
				as.setDay(location.getSlot()/iNrHours+1);
				as.setPeriod(location.getSlot()%iNrHours+1);
				as.setActivity((be.betty.gwtp.server.bdd.Activity_entity) s.get(Activity_entity.class, Integer.parseInt(a.getActivityId())));

				for (int i = 0; i < location.getResources().length; i++) {
					String ressource = location.getResources()[i].getResourceId();
					if (ressource.charAt(0) == ROOM_PREFIX.toCharArray()[0]) {
						Room r = (Room) s.get(Room.class, Integer.parseInt(ressource.substring(1)));
						as.setRoom(r);
					}
				}

				s.save(as);
				pi.getActivitiesState().add(as);

			}
		}
		
		int instanceId = instanceBddIdToSaveIn;
		if (instanceBddIdToSaveIn < 0) {
			int instanceMax = 0;
			for (ProjectInstance proIn : p.getProjectInstances())
				if (proIn.getNum() > instanceMax)
					instanceMax = proIn.getNum();
			instanceId = instanceMax+1;
		}
		pi.setNum(instanceId);
		pi.setDescription("Solver");
		s.save(pi);

		s.save(pi);
		
		t.commit();
		s.close();

	}
}
