package be.betty.gwtp.client;

import com.google.gwt.user.client.ui.ListBox;


/**
 * This class has only one purpose: 
 * tell if a card belong to the selected View
 *
 */
public class CardInView {
	ListBox combo_viewChoice1;
	ListBox combo_viewChoice2;

	/**
	 * Constructor.
	 * It has to be constructed with the 2 combobox used to determined if the 
	 * designed card is actualy in the view.
	 * @param combo_viewChoice1
	 * @param combo_viewChoice2
	 */
	public CardInView(ListBox combo_viewChoice1, ListBox combo_viewChoice2) {
		super();
		this.combo_viewChoice1 = combo_viewChoice1;
		this.combo_viewChoice2 = combo_viewChoice2;
	}

	/**
	 * The purpose is decide if a card belong to that view
	 * 
	 * @param card the card to check
	 * @return if it belong to
	 */
	public boolean cardBelongToActualView(String card, boolean askOrPrint) {
		//System.out.println("Should we print this card ? =>"+card);
		//System.out.println("Teacher = num "+Storage_access.getTeacherCard(card));
		//System.out.println("Group   = num "+Storage_access.getGroupCard(card));
		//System.out.println("Teacher= "+Storage_access.getTeacher(Integer.parseInt(Storage_access.getTeacherCard(card) ) ));
		//System.out.println("Group= "+Storage_access.getGroup(Integer.parseInt(Storage_access.getGroupCard(card))));

		int c2 = combo_viewChoice2.getSelectedIndex();
		//System.out.println("   Selected choice = "+c2);
		switch (combo_viewChoice1.getSelectedIndex()){
		case 0: // teacher
			if (Storage_access.getTeacherIdCard(card) == c2) return true;

			break;
		case 1: // room
			if(askOrPrint) { // asking if the card *can* go in this room
				return true;
			}else // asking if the card *is* in this room
				if (Storage_access.getRoomCard(card) == c2) return true;
			
			break;
		case 2: // group
			for (int i:Storage_access.getGroupsIdCard(card))
				if ( i== c2) return true;

			break;
		}
		//System.out.println("==>no");
		return false;
	}

	public String getRoom() {
		//String s = isRoomView() ? ""+combo_viewChoice2.getSelectedIndex() : "-1";
		//System.out.println("normalement on est ds la room view, et la roomId = "+s);
		return isRoomView() ? ""+combo_viewChoice2.getSelectedIndex() : "-1";
	}

	public boolean isRoomView() {
		return combo_viewChoice1.getSelectedIndex() == 1;
	}



}
