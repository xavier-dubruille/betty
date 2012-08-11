package be.betty.gwtp.client.action;

import java.util.ArrayList;

import be.betty.gwtp.shared.dto.Card_dto;
import be.betty.gwtp.shared.dto.Course_dto;
import be.betty.gwtp.shared.dto.Group_dto;
import be.betty.gwtp.shared.dto.ProjectInstance_dto;
import be.betty.gwtp.shared.dto.Room_dto;
import be.betty.gwtp.shared.dto.Teacher_dto;

import com.gwtplatform.dispatch.shared.Result;

public class GetCardsResult implements Result {

	ArrayList<Teacher_dto> teachers;
	ArrayList<Card_dto> cards;
	ArrayList<Group_dto> groups;
	ArrayList<Course_dto> courses;
	ArrayList<Room_dto> rooms;
	private ArrayList<ProjectInstance_dto> projectInstances;
	private int defaultInstance;

	public GetCardsResult() {
		cards = new ArrayList<Card_dto>();
		teachers =  new ArrayList<Teacher_dto>();
		groups = new ArrayList<Group_dto>();
		courses = new ArrayList<Course_dto>();
		projectInstances = new ArrayList<ProjectInstance_dto>();
		rooms = new ArrayList<Room_dto>();
	}

	public void addCard(Card_dto card) {
		cards.add(card);
	}

	public ArrayList<Card_dto> getCards() {
		return cards;
	}


	public ArrayList<Teacher_dto> getTeachers() {
		return teachers;
	}
	
	public void addTeacher(Teacher_dto teacher_dto) {
		// faudrait voir si on peu pas rajouter un id pour ne pas devoir trop manipuler de string..
		teachers.add(teacher_dto);
	}

	public ArrayList<Group_dto> getGroups() {
		return groups;
	}

	public void addGroup(Group_dto code) {
		groups.add(code);
		
	}

	public ArrayList<Course_dto> getCourses() {
		return courses;
	}
	
	public void addCourse(Course_dto c) {
		courses.add(c);
	}
	
	public void addProjectInstance(ProjectInstance_dto ins) {
		projectInstances.add(ins);
	}

	public ArrayList<ProjectInstance_dto> getProjectInstances() {
		return projectInstances;
	}

	public void setDefaultInstance(int default_instance) {
		this.defaultInstance = default_instance;
	}
	
	public int getDefaultInstance() {
		return defaultInstance;
	}

	public ArrayList<Room_dto> getRooms() {
		return rooms;
	}
	
	public void addRoom(Room_dto r) {
		rooms.add(r);
	}
}
