
package core;

public class TournamentCard extends StoryCard{

	public static final String CAMELOT_NAME = "AtCamelot";
	public static final String ORKNEY_NAME = "AtOrkney";
	public static final String TINTAGEL_NAME = "AtTintagel";
	public static final String YORK_NAME = "AtYork";
	
	public static final int CAMELOT_SHIELDS = 3;
	public static final int ORKNEY_SHIELDS = 2;
	public static final int TINTAGEL_SHIELDS = 1;
	public static final int YORK_SHIELDS = 0;
	

	private int numberShield;
	public int getNumShields(){return this.numberShield;}

	
	TournamentCard(String name, int numberShield) {
		super(TOURNAMENT);
		this.name = name;
		this.numberShield = numberShield;
	}


	@Override
	public String toString() {
		return "ID: " + this.id 
			 + ", type: " + this.type 
			 + ", subtype: " + this.subType 
			 + ", name: " + this.name;
	}


	public String getName() {
		return this.name;
	}
	


	
	


}
