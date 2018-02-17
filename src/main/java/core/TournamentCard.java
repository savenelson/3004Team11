
package core;

public class TournamentCard extends StoryCard{
	
	public static final int CAMELOT_SHIELDS = 3;
	public static final int ORKNEY_SHIELDS = 2;
	public static final int TINTAGEL_SHIELDS = 1;
	public static final int YORK_SHIELDS = 0;

	
	
	
	private String subType;
	public String getSubType() {return this.subType;}
	


	private int numberShield;
	public int getNumStages(){return this.numberShield;}

	
	TournamentCard(int numberShield) {
		super(TOURNAMENT);
		this.numberShield = numberShield;
	}
	


	
	


}
