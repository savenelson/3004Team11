

//package core;
//
//public class AllyCard extends AdventureCard{}

package core;

public class AllyCard extends AdventureCard{
	
	public static final int NUMBER_ALLY = 1;
	
	public static final String SIR_GALAHAD_NAME = "Sir Galahad";
	public static final String SIR_LANCELOT_NAME = "Sir Lancelot";
	public static final String KING_ARTHUR_NAME = "King Arthur";
	public static final String SIR_TRISTAN_NAME = "Sir Tristan";
	public static final String SIR_PELLINORE_NAME = "Sir Pellinore";
	public static final String SIR_GAWAIN_NAME = "Sir Gawain";
	public static final String SIR_PERCIVAL_NAME = "Sir Percival";
	public static final String QUEEN_GUINEVERE_NAME = "Queen Guinevere";	
	public static final String QUEEN_ISEULT_NAME = "Queen Iseult";
	public static final String MERLIN_NAME = "Merlin";
	
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
	
	private String name;
	private int battlePoints;
	private Special specialAbility;
	
	public void doSpecial(){specialAbility.doSpecial();}
	
	public int getBattlePoints(){return this.battlePoints;}
	
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