package core;

public abstract class AdventureCard extends Card{
	
	public static final String WEAPON = "weapon";
	public static final String TEST = "test";
	public static final String ALLY = "ally";
	public static final String FOE = "foe";
	public static final String AMOUR = "amour";
	
	private String subType;
	public String getSubType() {return this.subType;}
	
	AdventureCard(String subType) {
		super(ADVENTURE);
	}

}
