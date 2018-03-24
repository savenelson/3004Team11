
package client;

public class AllyCard extends AdventureCard{
	
	public static final int NUMBER_ALLY = 1;
	
	public static final String SIR_GALAHAD_NAME = "SirGalahad";
	public static final String SIR_LANCELOT_NAME = "SirLancelot";
	public static final String KING_ARTHUR_NAME = "KingArthur";
	public static final String SIR_TRISTAN_NAME = "SirTristan";
	public static final String KING_PELLINORE_NAME = "KingPellinore";
	public static final String SIR_GAWAIN_NAME = "SirGawain";
	public static final String SIR_PERCIVAL_NAME = "SirPercival";
	public static final String QUEEN_GUINEVERE_NAME = "QueenGuinevere";	
	public static final String QUEEN_ISEULT_NAME = "QueenIseult";
	public static final String MERLIN_NAME = "Merlin";
	
	public static final int SIR_GALAHAD_BATTLE_POINTS = 15;
	public static final int SIR_LANCELOT_BATTLE_POINTS = 15;
	public static final int KING_ARTHUR_BATTLE_POINTS = 10;
	public static final int SIR_TRISTAN_BATTLE_POINTS = 10;
	public static final int KING_PELLINORE_BATTLE_POINTS = 10;
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
	public String getName(){return this.name;}
	
	public AllyCard(String name, int battlePoints, Special specialAbility) {
		super(ALLY);
		
		this.name = name;
		this.battlePoints = battlePoints;
		this.specialAbility = specialAbility;
	}
	
	public String toString(){
		
		return "ID: " + this.id 
			 + ", type: " + this.type 
			 + ", subtype: " + this.subType 
			 + ", name: " + this.name 
			 + ", battle points: " + this.battlePoints
			 + ", special: " + this.specialAbility.toString();
	}
	
}