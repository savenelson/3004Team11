package core;

public class AmourCard extends AdventureCard{

	public static final int NUMBER_AMOUR = 8;
	public static final int AMOUR_BIDS = 1;
	
	private int battlePoints = 10;
	
	private Special specialAbility;

	public void doSpecial(){specialAbility.doSpecial();}
	
	public int getBattlePoints(){return this.battlePoints;}
	
	
	
	AmourCard(Special specialAbility) {
		super(AMOUR);
		
		this.specialAbility = specialAbility;
		this.name = "Amour";
	}

	@Override
	public String toString() {
		
		return "ID: " + this.id 
				 + ", type: " + this.type 
				 + ", subtype: " + this.subType 
				 + ", battle points: " + this.battlePoints
				 + ", special: " + this.specialAbility.toString();
	}

}
