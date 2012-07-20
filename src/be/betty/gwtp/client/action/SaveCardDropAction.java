package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.ActionImpl;
import be.betty.gwtp.client.action.SaveCardDropActionResult;

public class SaveCardDropAction extends ActionImpl<SaveCardDropActionResult> {

	private int day;
	private int period;
	private int cardBddId;
	private int room;

	public SaveCardDropAction() {} //for serialisation purposes
	public SaveCardDropAction(int day, int period, int cardBddId, int room) {
		super();
		this.day = day;
		this.period = period;
		this.cardBddId = cardBddId;
		this.room = room;
	}
	
	@Override
	public boolean isSecured() {
		return false;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getCardBddId() {
		return cardBddId;
	}

	public void setCarBdddId(int cardId) {
		this.cardBddId = cardId;
	}

}