
package core;

public class TournamentCard extends StoryCard{
	
	public static final int CAMELOT_SHIELDS = 3;
	public static final int ORKNEY_SHIELDS = 2;
	public static final int TINTAGEL_SHIELDS = 1;
	public static final int YORK_SHIELDS = 0;

	
	public static final int NUMBER_TOURNAMENT = 1;
	
	
	private String subType;
	public String getSubType() {return this.subType;}
	
	TournamentCard(String subType) {
		super(TOURNAMENT);
	}



	
	


}
