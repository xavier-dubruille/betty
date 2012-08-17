package be.betty.gwtp.server;


import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import be.betty.gwtp.client.action.GetCards;
import be.betty.gwtp.client.action.GetCardsResult;
import be.betty.gwtp.server.bdd.Activity_entity;
import be.betty.gwtp.server.bdd.Course;
import be.betty.gwtp.server.bdd.Group_entity;
import be.betty.gwtp.server.bdd.ProjectInstance;
import be.betty.gwtp.server.bdd.Project_entity;
import be.betty.gwtp.server.bdd.Room;
import be.betty.gwtp.server.bdd.Teacher;
import be.betty.gwtp.shared.dto.Card_dto;
import be.betty.gwtp.shared.dto.Course_dto;
import be.betty.gwtp.shared.dto.Group_dto;
import be.betty.gwtp.shared.dto.ProjectInstance_dto;
import be.betty.gwtp.shared.dto.Room_dto;
import be.betty.gwtp.shared.dto.Teacher_dto;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetCardsActionHandler implements
ActionHandler<GetCards, GetCardsResult> {

	@Inject
	public GetCardsActionHandler() {
	}

	@Override
	public GetCardsResult execute(GetCards action, ExecutionContext context)
			throws ActionException {

		assert Integer.parseInt(action.getProjectId()) >= 0;
		int projectId = Integer.parseInt(action.getProjectId());
		if (!CheckSession.isProjectActionPermited(projectId, false, action.getSessId()))
			throw new ActionException("Invalid Action, try to erase your cookies and re-log");

		GetCardsResult result = new GetCardsResult();

		System.out.println("execute getcard.. projet num: "
				+ action.getProjectId());
		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();

		Project_entity p = (Project_entity) s.get(Project_entity.class,
				projectId);
		// result.setName(p.getName());
	
		for (Activity_entity a : p.getActivities()) {
			 if (!a.getSemestre().equals(action.getSemestre()))
				 continue;
			//System.out.println("***  Teacher:"+a.getTeacher().getName()+" Course:"+a.getCourse().getName()+" Group:"+a.getGroup().getCode());
			Card_dto card = new Card_dto();
			card.setCourse(a.getCourse().getId());
			for (Group_entity g: a.getGroupSet())
				card.addGroup(g.getId());
			card.setTeacher(a.getTeacher().getId());
			card.setBddId(a.getId());
			result.addCard(card);
		}
		List<Teacher> lt = (List<Teacher>) p.getTeachers();
		Collections.sort(lt);
		for (Teacher te : lt) {
			System.out.println(te.getName());
			if (te.giveCoursesThisSem(action.getSemestre()))
				result.addTeacher(new Teacher_dto(te.getName(), te.getFirstName(), te.getId()));
		}
		
		List<Group_entity> lg = (List<Group_entity>) p.getGroups();
		Collections.sort(lg);
		for (Group_entity g : lg)
			result.addGroup(new Group_dto(g.getCode(), g.getId()));
		
		List<Room> lr = (List<Room>)p.getRooms();
		Collections.sort(lr);
		for (Room r: lr)
			result.addRoom(new Room_dto(r.getCode(), r.getId()));
		
		Course_dto co;
		for (Course c: p.getCourses())
			if (c.getNbPeriodSX(action.getSemestre()) !=0 ) {
				co = new Course_dto(c.getName(), c.getId());
				for (Room r : c.getPossibleRooms())
					co.addPossibleRoom(r.getId());
				result.addCourse(co);
			}
		
		//System.out.println("**<<<** bdd instances:"+p.getProjectInstances());
		for (ProjectInstance ins: p.getProjectInstances())
			result.addProjectInstance(new ProjectInstance_dto(ins.getId(), ins.getNum(), ins.getDescription()));

		result.setDefaultInstance(0);
		t.commit();   
		s.close();
		return result;
	}

	@Override
	public void undo(GetCards action, GetCardsResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<GetCards> getActionType() {
		return GetCards.class;
	}
}
