package core;

public abstract class RankCard extends Card{
	
	public static final String SQUIRE = "squire";
	public static final String KNIGHT = "knight";
	public static final String CHAMPIONKNIGHT = "championknight";

	
	private String subType;
	public String getSubType() {return this.subType;}
	
	RankCard(String subType) {
		super(RANK);
	}

}
