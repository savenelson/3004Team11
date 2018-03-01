package core;

public abstract class AdventureCard extends Card{
	
	public static final String WEAPON = "Weapon";
	public static final String TEST = "Test";
	public static final String ALLY = "Ally";
	public static final String FOE = "Foe";
	public static final String AMOUR = "Amour";
	
	protected String name;
	
	protected String subType;
	public String getSubType() {return this.subType;}
	
	AdventureCard(String subType) {
		super(ADVENTURE);
		
		this.subType = subType;
	}
	
	public abstract int getBattlePoints();
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String getImgName() {
		return this.subType + this.name;
	}
}
