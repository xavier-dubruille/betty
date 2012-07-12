package be.betty.gwtp.client.action;

import java.util.ArrayList;

import be.betty.gwtp.shared.dto.Card_dto;

import com.gwtplatform.dispatch.shared.Result;

public class GetCardsResult implements Result {

	ArrayList<String> teachers;
	ArrayList<Card_dto> cards;
	ArrayList<String> groups;

	public GetCardsResult() {
		cards = new ArrayList<Card_dto>();
		teachers =  new ArrayList<String>();
		groups = new ArrayList<String>();
	}

	public void addCard(Card_dto card) {
		cards.add(card);
	}

	public ArrayList<Card_dto> getCards() {
		return cards;
	}


	public ArrayList<String> getTeachers() {
		return teachers;
	}
	
	public void addTeacher(String teacher) {
		// faudrait voir si on peu pas rajouter un id pour ne pas devoir trop manipuler de string..
		teachers.add(teacher);
	}

	public ArrayList<String> getGroups() {
		return groups;
	}

	public void addGroup(String code) {
		groups.add(code);
		
	}
}
