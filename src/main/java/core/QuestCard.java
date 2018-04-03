
package core;

public class QuestCard extends StoryCard{
	//name
	public static final String HOLY_GRAIL = "SearchForTheHolyGrail";
	public static final String GREEN_KNIGHT = "TestOfTheGreenKnight";
	public static final String QUESTING_BEAST = "SearchForTheQuestingBeast";
	public static final String QUEENS_HONOR = "DefendTheQueensHonor";
	public static final String FAIR_MAIDEN = "RescueTheFairMaiden";
	public static final String ENCHANTED_FORST = "JourneyThroughTheEnchantedForest";
	public static final String ARTHURS_ENEMIES = "VanquishKingArthursEnemies";
	public static final String SLAY_DRAGON = "SlayTheDragon";
	public static final String BOAR_HUNT = "BoarHunt";
	public static final String SAXON_RAIDERS = "RepelTheSaxonRaiders";
	
	
	//quantity
	public static final int HOLY_GRAIL_NUM = 1;
	public static final int GREEN_KNIGHT_NUM = 2;
	public static final int QUESTING_BEAST_NUM = 2;
	public static final int QUEENS_HONOR_NUM = 1;
	public static final int FAIR_MAIDEN_NUM = 1;
	public static final int ENCHANTED_FORST_NUM = 1;
	public static final int ARTHURS_ENEMIES_NUM = 2;
	public static final int SLAY_DRAGON_NUM = 1;
	public static final int BOAR_HUNT_NUM = 2;
	public static final int SAXON_RAIDERS_NUM = 2;

//num stages
	public static final int HOLY_GRAIL_STAGES = 5;	
	public static final int GREEN_KNIGHT_STAGES = 4;
	public static final int QUESTING_BEAST_STAGES  = 4;
	public static final int QUEENS_HONOR_STAGES  = 4;
	public static final int FAIR_MAIDEN_STAGES  = 3;
	public static final int ENCHANTED_FORST_STAGES  = 3;
	public static final int ARTHURS_ENEMIES_STAGES  = 3;
	public static final int SLAY_DRAGON_STAGES  = 3;
	public static final int BOAR_HUNT_STAGES  = 2;
	public static final int SAXON_RAIDERS_STAGES  = 2;
	//foe
	public static final String HOLY_GRAIL_FOE = "All";	
	public static final String GREEN_KNIGHT_FOE = "Green Knight";
	public static final String QUESTING_BEAST_FOE  = "None";
	public static final String QUEENS_HONOR_FOE  = "All";
	public static final String FAIR_MAIDEN_FOE  = "Black Knight";
	public static final String ENCHANTED_FORST_FOE  = "Evil Knight";
	public static final String ARTHURS_ENEMIES_FOE  = "None";
	public static final String SLAY_DRAGON_FOE  = "Dragon";
	public static final String BOAR_HUNT_FOE  = "Boar";
	public static final String SAXON_RAIDERS_FOE  = "All Saxons";
	
	public boolean hasSponsor = false;
	
	private int numberStages;
	public int getNumStages(){return this.numberStages;}
	
	public String getName(){return this.name;}
	
	private String foe;
	
	public String getFoe(){return this.name;}
	
	QuestCard(String name, int numberStages, String foe) {
		super(QUEST);
		this.name = name;
		this.numberStages = numberStages;
		this.foe = foe;
	}

	@Override
	public String toString() {
		
		return "ID: " + this.id 
			 + ", type: " + this.type 
			 + ", subtype: " + this.subType 
			 + ", name: " + this.name
			 + ", number of stages: " + this.numberStages
			 + ", special foe: " + this.foe;
	}
}
