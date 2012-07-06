package be.betty.gwtp.client.action;

import java.util.ArrayList;

import com.gwtplatform.dispatch.shared.Result;

public class GetCardsResult implements Result {

	ArrayList<String> cards;
	
	public GetCardsResult() {
		cards = new ArrayList<String>();
	}
	
	public void addCard(String card) {
		cards.add(card);
	}
	
	public ArrayList<String> getCards() {
		return cards;
	}
}
