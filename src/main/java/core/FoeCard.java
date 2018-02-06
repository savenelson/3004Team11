package core;

public class FoeCard extends AdventureCard{
	
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
	
	private Special specialAbility;
	
	//special 
	public void doSpecial(){specialAbility.doSpecial();}
	
	FoeCard(int battlePoints, int altBattlePoints, Special specialAbility) {
		super(FOE);
		
		this.specialAbility = specialAbility;
		// TODO Auto-generated constructor stub
	}

}
