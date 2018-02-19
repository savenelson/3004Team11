package core;

public class FoeCard extends AdventureCard{
	
	public static final String THIEVES_NAME = "Thieves";
	public static final String BOAR_NAME = "Boar";
	public static final String SAXONS_NAME = "Saxons";
	public static final String ROBBER_KNIGHT_NAME = "Robber Knight";
	public static final String SAXON_KNIGHT_NAME = "Saxon Knight";
	public static final String EVIL_KNIGHT_NAME = "Evil Knight";
	public static final String BLACK_KNIGHT_NAME = "Black Knight";
	public static final String GREEN_KNIGHT_NAME = "Green Knight";	
	public static final String MORDRED_NAME = "Mordred";
	public static final String GIANT_NAME = "Giant";
	public static final String DRAGON_NAME = "Dragon";
	
	public static final int THIEVES_BATTLE_POINTS = 5;
	public static final int BOAR_BATTLE_POINTS = 5;
	public static final int SAXONS_BATTLE_POINTS = 10;
	public static final int ROBBER_KNIGHT_BATTLE_POINTS = 15;
	public static final int SAXON_KNIGHT_BATTLE_POINTS = 15;
	public static final int EVIL_KNIGHT_BATTLE_POINTS = 20;
	public static final int BLACK_KNIGHT_BATTLE_POINTS = 25;
	public static final int GREEN_KNIGHT_BATTLE_POINTS = 25;	
	public static final int MORDRED_BATTLE_POINTS = 30;
	public static final int GIANT_BATTLE_POINTS = 40;
	public static final int DRAGON_BATTLE_POINTS = 50;

	public static final int THIEVES_ALT_BATTLE_POINTS = 5;
	public static final int BOAR_ALT_BATTLE_POINTS = 15;
	public static final int SAXONS_ALT_BATTLE_POINTS = 20;
	public static final int ROBBER_KNIGHT_ALT_BATTLE_POINTS = 15;
	public static final int SAXON_KNIGHT_ALT_BATTLE_POINTS = 25;
	public static final int EVIL_KNIGHT_ALT_BATTLE_POINTS = 30;
	public static final int BLACK_KNIGHT_ALT_BATTLE_POINTS = 35;
	public static final int GREEN_KNIGHT_ALT_BATTLE_POINTS = 40;	
	public static final int MORDRED_ALT_BATTLE_POINTS = 30;
	public static final int GIANT_ALT_BATTLE_POINTS = 40;
	public static final int DRAGON_ALT_BATTLE_POINTS = 70;
	
	public static final int NUMBER_THIEVES = 8;
	public static final int NUMBER_BOAR = 4;
	public static final int NUMBER_SAXONS = 5;
	public static final int NUMBER_ROBBER_KNIGHT = 7;
	public static final int NUMBER_SAXON_KNIGHT = 8;
	public static final int NUMBER_EVIL_KNIGHT = 6;
	public static final int NUMBER_BLACK_KNIGHT = 3;
	public static final int NUMBER_GREEN_KNIGHT = 2;	
	public static final int NUMBER_MORDRED = 4;
	public static final int NUMBER_GIANT = 2;
	public static final int NUMBER_DRAGON = 1;
	
	private String name;
	private int battlePoints;
	private int altBattlePoints;
	private Special specialAbility;

	public void doSpecial(){specialAbility.doSpecial();}
	
	public String getName(){return this.name;}
	
	public int getBattlePoints(){return this.battlePoints;}
	
	public int getAltBattlePoints(){return this.altBattlePoints;}
	
	FoeCard(String name, int battlePoints, int altBattlePoints, Special specialAbility) {
		super(FOE);
		
		this.name = name;
		this.battlePoints = battlePoints;	
		this.altBattlePoints = altBattlePoints;
		this.specialAbility = specialAbility;
	}

	@Override
	public String toString() {
		
		return "ID: " + this.id 
			 + ", type: " + this.type 
			 + ", subtype: " + this.subType 
			 + ", name: " + this.name 
			 + ", battle points: " + this.battlePoints
			 + ", alternative battle points: " + this.altBattlePoints
			 + ", special: " + this.specialAbility.toString();

	}


}
