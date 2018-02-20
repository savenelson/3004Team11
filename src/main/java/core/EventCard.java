
package core;

public class EventCard extends StoryCard{
	
	public static final int KINGS_RECOGNITION = 2;
	public static final int QUEENS_FAVOR = 2;
	public static final int COURT_CAMELOT = 2;
	public static final int POX = 1;
	public static final int PLAGUE = 1;
	public static final int CHIVALROUS_DEED = 1;
	public static final int PROSPERITY = 1;
	public static final int CALL_ARMS = 1;
	
	public static final String KINGS_RECOGNITION_NAME = "Kings Recognition";
	public static final String QUEENS_FAVOR_NAME = "Queens Favor";
	public static final String COURT_CAMELOT_NAME = "Court Camelot";
	public static final String POX_NAME = "Pox";
	public static final String PLAGUE_NAME = "Plague";
	public static final String CHIVALROUS_DEED_NAME = "Chivalrous Deed";
	public static final String PROSPERITY_NAME = "Prosperity";
	public static final String CALL_ARMS_NAME = "Call Arms";
	//conditional allowance or removal of shields based on certain conditions being met..don't know to integrate yet	

	public String getName(){return this.name;}
	
	EventCard(String name) {
		super(EVENT);
		this.name = name;
	}

	@Override
	public String toString() {
		
		return "ID: " + this.id 
			 + ", type: " + this.type 
			 + ", subtype: " + this.subType 
			 + ", name: " + this.name;
	}
	
}
