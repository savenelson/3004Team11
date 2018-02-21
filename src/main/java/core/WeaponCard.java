package core;

public class WeaponCard extends AdventureCard{
	
	// Weapon names
	public static final String SWORD_NAME = "Sword";
	public static final String DAGGER_NAME = "Dagger";
	public static final String HORSE_NAME = "Horse";
	public static final String BATTLE_AX_NAME = "BattleAx";
	public static final String LANCE_NAME = "Lance";
	public static final String EXCALIBUR_NAME = "Excalibur";
	
	// Weapon battle points
	public static final int SWORD_BATTLE_POINTS = 10;
	public static final int DAGGER_BATTLE_POINTS = 5;
	public static final int HORSE_BATTLE_POINTS = 10;
	public static final int BATTLE_AX_BATTLE_POINTS = 15;
	public static final int LANCE_BATTLE_POINTS = 20;
	public static final int EXCALIBUR_BATTLE_POINTS = 30;
	
	// Number of weapons
	public static final int NUMBER_SWORDS = 16;
	public static final int NUMBER_DAGGER = 6;
	public static final int NUMBER_HORSE = 11;
	public static final int NUMBER_BATTLE_AX = 8;
	public static final int NUMBER_LANCE = 6;
	public static final int NUMBER_EXCALIBUR = 2;
	
	private int battlePoints;
	
	public int getBattlePoints(){return this.battlePoints;}
		
	public String getName(){return this.name;}
	
	WeaponCard(String name, int battlePoints) {
		super(WEAPON);
		this.name = name;
		this.battlePoints = battlePoints;
	}

	@Override
	public String toString() {
		return "ID: " + this.id 
				 + ", type: " + this.type 
				 + ", subtype: " + this.subType 
				 + ", name: " + this.name 
				 + ", battle points: " + this.battlePoints;

	}



}
