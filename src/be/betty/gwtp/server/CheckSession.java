package be.betty.gwtp.server;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import be.betty.gwtp.server.bdd.Project_entity;
import be.betty.gwtp.server.bdd.Session_id;
import be.betty.gwtp.server.bdd.User;

public class CheckSession {

	/**
	 * 
	 * This method is to check if a requested action is allowed
	 * 
	 * @param projectOrInstance_id
	 * @param isInstanceId true if the id is a instanceId, false if it's a project ID
	 * @param session_id
	 * @return if the operation is permitted
	 */
	public static boolean isProjectActionPermited(int projectOrInstance_id, boolean isInstanceId, String session_id) {
		
	
		int project_id = -1 ;
		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();

		// put the right projectid (depending if it's a instanceProjectId or a projectId)
		if (isInstanceId) {
			List<Project_entity> qi = s.createQuery("select p from Project_entity p join p.projectInstances i where i.id =:instance")
					.setString("instance", ""+projectOrInstance_id).list();
			if (qi == null || qi.size() == 0) {
				t.commit();
				s.close();
				return false;
			}
			project_id = qi.get(0).getId();
			System.out.println("project id = "+project_id);
			
		} else 
			project_id = projectOrInstance_id;
		
		
		// let's first check if this sess_id is still valid (less than 2 weeks old)
		List<Session_id> q0 = s.createQuery("from Session_id s where s.sess_id =:sessId").setString("sessId", session_id).list();
		if (q0 == null || q0.size() == 0) {
			t.commit();
			s.close();
			return false;
		}
		
		if (q0.get(0).getCreationDate().getTime()+1209600000 < new Date().getTime()){
			System.out.println("plus valide");
			t.commit();
			s.close();
			return false;
		}
		
		// then let's see if the user (with this sessId) can modify this project

		List<User> q = s.createQuery("select s.user_id from Session_id s join s.user_id where s.sess_id =:sessId").setString("sessId", session_id).list();
		
		if (q == null || q.size() == 0) {
			t.commit();
			s.close();
			return false;
		}
		
		
		List<Project_entity> q2 = s.createQuery("select p from User u join u.projects p where u.id = :userId").setString("userId", ""+q.get(0).getId()).list();

		if (q2 == null || q2.size() == 0){
			t.commit();
			s.close();
			return false;
		}
		
		for (int i=0; i<q2.size(); i++)
			if (q2.get(i).getId() == project_id){
				t.commit();
				s.close();
				return true;
			}
		
		
		
		t.commit();
		s.close();

		return false;
	}

}
