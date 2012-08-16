package be.betty.gwtp.client;


public class UiConstants {

	private static final int CARD_WIDTH = 120;
	private static final int CARD_HEIGHT = 72;
	private static final String PERIODE = "periode";
	private static final String [] WEEK_DAY = {"Monday", "Tuesday", "Wednesday","Thursday","Friday","Saturday","Sunday"};
	public static final String CSS_PLACED_CARD = "cardPlaced";
	public static final String CSS_CARD = "card";
	public static final String CSS_SINGLE_CELL = "panelFlextable";
	public static final int NOTIF_CSS = 0;
	public static final int NOTIF_CSS_ERROR = 1;
	public static final int DAY_NUMBER= 6;
	public static final int PERIODE_NUMBER = 7;
	//TODO remplir le tableau avec les bon css
	public static final String[] SOLVERCOLORCSS = {"lowgreen", "middlegreen", "highgreen", "loworange", "middleorange", "highorange", "lowred", "middlered", "highred"};
	
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
	
	public static int getNotifCss(){
		return NOTIF_CSS;
	}
	
	public static int getNotifCssError(){
		return NOTIF_CSS_ERROR;
	}
	
	public static int getDayNumber(){
		return DAY_NUMBER;
	}
	
	public static int getPeriodeNumber(){
		return PERIODE_NUMBER;
	}
	
	public static String getColorCss(int index){
		return SOLVERCOLORCSS[index];
	}
}
