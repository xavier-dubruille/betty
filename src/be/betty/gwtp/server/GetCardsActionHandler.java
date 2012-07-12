package be.betty.gwtp.server;

import org.hibernate.Session;
import org.hibernate.Transaction;

import be.betty.gwtp.client.action.GetCards;
import be.betty.gwtp.client.action.GetCardsResult;
import be.betty.gwtp.server.bdd.Activity;
import be.betty.gwtp.server.bdd.Group_entity;
import be.betty.gwtp.server.bdd.Project_entity;
import be.betty.gwtp.server.bdd.Teacher;
import be.betty.gwtp.shared.dto.Card_dto;

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
			card.setCourse(a.getCourse().getName());
			card.setGroup(a.getGroup().getCode());
			card.setTeacher(a.getTeacher().getFirstName()+" "+a.getTeacher().getName());
			result.addCard(card);
		}
		
		for (Teacher te : p.getTeachers()) {
			result.addTeacher(te.getFirstName()+" "+te.getName());
		}
		
		for (Group_entity g : p.getGroups())
			result.addGroup(g.getCode());
			

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
