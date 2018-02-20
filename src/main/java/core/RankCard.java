package core;

public class RankCard extends Card{
	
	public static final String SQUIRE = "Squire";
	public static final String KNIGHT = "Knight";
	public static final String CHAMPION_KNIGHT = "ChampionKnight";
	
	public static final int SQUIRE_BATTLE_POINTS = 5;
	public static final int KNIGHT_BATTLE_POINTS = 10;
	public static final int CHAMPION_KNIGHT_BATTLE_POINTS = 20;
	
	private int battlePoints;
	
	private String subType;
	public String getSubType() {return this.subType;}
	public int getBattlePoints() {return this.battlePoints;}
	
	RankCard(String subType) {
		super(RANK);
		
		this.subType = subType;
		if(this.subType.equals(SQUIRE)){
			
		}
		else if(this.subType.equals(KNIGHT)){
			
		}
		else if(this.subType.equals(CHAMPION_KNIGHT)){
			
		}
		else{
			
			System.out.println("YOURE BREAKING THE GAME!");	
		}
		
	}
	@Override
	public String toString() {
		
		return 
				"ID: " + this.id 
			  + ", type: " + this.type 
			  + ", subtype: " + this.subType;
	}
	@Override
	public String getImgName() {
		return this.type + this.subType;
	}
	
	

}
