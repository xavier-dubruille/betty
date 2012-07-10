package be.betty.gwtp.client.action;

import java.util.ArrayList;

import com.gwtplatform.dispatch.shared.Result;

public class GetCardsResult implements Result {

	ArrayList<String> cards;
	ArrayList<String> teachers;

	public GetCardsResult() {
		cards = new ArrayList<String>();
		teachers = new ArrayList<String>();
	}

	public void addCard(String card) {
		cards.add(card);
	}

	public ArrayList<String> getCards() {
		return cards;
	}

	public ArrayList<String> getTeachers() {
		return teachers;
	}
	
	public void addTeacher(String teacher) {
		// faudrait voir si on peu pas rajouter un id pour ne pas devoir trop manipuler de string..
		teachers.add(teacher);
	}
}
