package be.betty.gwtp.client;

public class CellState {
	
	public CellState(){
		
	}
	
	public CellState(int i) {
		color = i;
	}

	private int color;
	
	public int getColor(){
		return color;
	}
	
	public String toString() {
		return "Color="+color;
	}

	public void setColor(int i) {
		color = i;
		
	}
}
