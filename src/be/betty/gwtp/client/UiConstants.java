package be.betty.gwtp.client;


public class UiConstants {

	private static final int CARD_WIDTH = 120;
	private static final int CARD_HEIGHT = 72;
	private static final String PERIODE = "periode";
	private static final String [] WEEK_DAY = {"Monday", "Tuesday", "Wednesday","Thursday","Friday","Saturday","Sunday"};
	public static final String CSS_PLACED_CARD = "cardPlaced";
	public static final String CSS_CARD = "card";
	
	public static int getCardWidth() {
		return CARD_WIDTH;
	}
	public static int getCardHeight() {
		return CARD_HEIGHT;
	}
	
	public static String getPeriode(){
		return PERIODE;
	}
	
	public static String getWeekDay(int MyI){
		return WEEK_DAY[MyI];
	}
	
	public static int getNumberOfDay(){
		return WEEK_DAY.length;
	}
	
	
}
