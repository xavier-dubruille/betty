package be.betty.gwtp.server;

import java.util.Arrays;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import be.betty.gwtp.client.action.CreateNewCardAction;
import be.betty.gwtp.client.action.CreateNewCardActionResult;
import be.betty.gwtp.server.bdd.Activity_entity;
import be.betty.gwtp.server.bdd.Course;
import be.betty.gwtp.server.bdd.Group_entity;
import be.betty.gwtp.server.bdd.Project_entity;
import be.betty.gwtp.server.bdd.Teacher;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class CreateNewCardActionActionHandler implements
		ActionHandler<CreateNewCardAction, CreateNewCardActionResult> {

	@Inject
	public CreateNewCardActionActionHandler() {
	}

	@Override
	public CreateNewCardActionResult execute(CreateNewCardAction action,
			ExecutionContext context) throws ActionException {
		//System.out.println("courseId="+action.getCourseId()+" , teacherId="+action.getTeacherId()+
		//		" , groupId="+Arrays.toString(action.getGroupId()));
		
		// faut verifier si il peu avec le action.getProjectId(); et le CheckSession
		
		int[] g = action.getGroupId();
		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();
		
		Project_entity p = (Project_entity) s.get(Project_entity.class, action.getProjectId());
		
		
		Course c = (Course) s.get(Course.class, Integer.parseInt(action.getCourseId()));
		Teacher te = (Teacher) s.get(Teacher.class, Integer.parseInt(action.getTeacherId()));
		Activity_entity a = new Activity_entity();
		System.out.println("activity created");
		a.setCourse(c);
		a.setTeacher(te);
		a.setOccurrence(1); // TODO: we should check first if the same card already exist and increment its occurence
		a.setSemestre(action.getSemester());
		
		
		//System.out.println("most of activited seted");
		a.setProject(p);
		
		//System.out.println("project seted to actyvity");
		
		//TODO: that's not pretty.. no error gestion! 
		Group_entity ge= (Group_entity) s.get(Group_entity.class, g[0]);
		a.getGroupSet().add(ge);
		a.setClass(ge.getCode().charAt(0)); 
		
		for(int i=1; i<g.length; i++){
			//System.out.println("adding group "+g[i]+" of "+g.length);
			Group_entity gee= (Group_entity) s.get(Group_entity.class, g[i]);
			a.getGroupSet().add(gee);
		}
		//System.out.println("done for the group");
		s.save(a);
		
		//System.out.println("activity saved");
		
		p.getActivities().add(a);
		
		//System.out.println("project add activity");
		t.commit();
		s.close();
		//System.out.println("action done");
		return null;
	}

	@Override
	public void undo(CreateNewCardAction action, CreateNewCardActionResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<CreateNewCardAction> getActionType() {
		return CreateNewCardAction.class;
	}
}
