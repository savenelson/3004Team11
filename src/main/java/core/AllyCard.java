package core;

public class AllyCard extends AdventureCard{
	
	public static final int NUMBER_ALLY = 1;
	
	public static final int SIR_GALAHAD_BATTLE_POINTS = 15;
	public static final int SIR_LANCELOT_BATTLE_POINTS = 15;
	public static final int KING_ARTHUR_BATTLE_POINTS = 10;
	public static final int SIR_TRISTAN_BATTLE_POINTS = 10;
	public static final int SIR_PELLINORE_BATTLE_POINTS = 10;
	public static final int SIR_GAWAIN_BATTLE_POINTS = 10;
	public static final int SIR_PERCIVAL_BATTLE_POINTS = 5;
	public static final int QUEEN_GUINEVERE_BATTLE_POINTS = 0;	
	public static final int QUEEN_ISEULT_BATTLE_POINTS = 0;
	public static final int MERLIN_BATTLE_POINTS = 0;
	
	public static final int KING_ARTHUR_BIDS = 4;	
	public static final int QUEEN_GUINEVERE_BIDS = 3;	
	public static final int QUEEN_ISEULT_BIDS = 2;
	
	private int battlePoints;
	private Special specialAbility;
	
	public void doSpecial(){specialAbility.doSpecial();}
	
	public int getBattlePoints(){return this.battlePoints;}
	
	public AllyCard(int battlePoints, Special specialAbility) {
		super(ALLY);
		
		this.battlePoints = battlePoints;
		this.specialAbility = specialAbility;
	}
	
}