package core;

public class WeaponCard extends AdventureCard{
	
	// Weapon names
	public static final String SWORD = "sword";
	public static final String DAGGER = "dagger";
	public static final String HORSE = "horse";
	public static final String BATTLE_AX = "battle-ax";
	public static final String LANCE = "lance";
	public static final String EXCALIBUR = "excalibur";
	
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
	
	private String name;
	
	public String getName(){return this.name;}
	
	WeaponCard(String name, int battlePoints) {
		super(WEAPON);
		this.name = name;
		this.battlePoints = battlePoints;
	}

}
