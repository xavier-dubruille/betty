package be.betty.gwtp.client;

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

	public void setReason(String reason) {
		this.reason = reason;
		
	}
}
