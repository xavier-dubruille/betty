package be.betty.gwtp.client;

/**
 * 
 * Caracterise the state of a cell. 
 * It's use to print color on the user screen
 *
 */
public class CellState {
	
	public CellState(){
		
	}
	
	public CellState(int i) {
		color = i;
	}

	private int color;
	private String reason;
	
	public int getColor(){
		return color;
	}
	
	public String toString() {
		return "Color="+color;
	}

	public void setColor(int i) {
		color = i;
		
	}
	
	public String getReason() {
		return reason;
	}

	/**
	 * Use to explain the reason of the color.
	 * @param reason
	 */
	public void setReason(String reason) {
		this.reason = reason;
		
	}
}
