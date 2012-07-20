package be.betty.gwtp.server;

import org.hibernate.Session;
import org.hibernate.Transaction;

import be.betty.gwtp.client.action.GetCards;
import be.betty.gwtp.client.action.GetCardsResult;
import be.betty.gwtp.server.bdd.Activity;
import be.betty.gwtp.server.bdd.Course;
import be.betty.gwtp.server.bdd.Group_entity;
import be.betty.gwtp.server.bdd.ProjectInstance;
import be.betty.gwtp.server.bdd.Project_entity;
import be.betty.gwtp.server.bdd.Teacher;
import be.betty.gwtp.shared.dto.Card_dto;
import be.betty.gwtp.shared.dto.Course_dto;
import be.betty.gwtp.shared.dto.Group_dto;
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

		GetCardsResult result = new GetCardsResult();

		System.out.println("execute getcard.. projet num: "
				+ action.getProjectId());
		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();

		Project_entity p = (Project_entity) s.get(Project_entity.class,
				Integer.parseInt(action.getProjectId()));
		// result.setName(p.getName());
		for (Activity a : p.getActivities()) {
			// System.out.println("***  Teacher:"+a.getTeacher().getName()+" Course:"+a.getCourse().getName()+" Group:"+a.getGroup().getCode());
			Card_dto card = new Card_dto();
			card.setCourse(a.getCourse().getId());
			card.setGroup(a.getGroup().getId());
			card.setTeacher(a.getTeacher().getId());
			card.setBddId(a.getId());
			result.addCard(card);
		}
		
		for (Teacher te : p.getTeachers())
			result.addTeacher(new Teacher_dto(te.getName(), te.getFirstName(), te.getId()));
		
		
		for (Group_entity g : p.getGroups())
			result.addGroup(new Group_dto(g.getCode(), g.getId()));
		
		for (Course c: p.getCourses())
			result.addCourse(new Course_dto(c.getName(), c.getId()));
		
		//System.out.println("**<<<** bdd instances:"+p.getProjectInstances());
		for (ProjectInstance ins: p.getProjectInstances())
			result.addProjectInstance(""+ins.getId());

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
