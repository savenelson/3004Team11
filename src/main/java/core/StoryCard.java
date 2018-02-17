package core;

public abstract class StoryCard extends Card{
	
	public static final String EVENT = "event";
	public static final String QUEST = "quest";
	public static final String TOURNAMENT = "tournament";

	
	private String subType;
	public String getSubType() {return this.subType;}
	
	StoryCard(String subType) {
		super(STORY);
	}

}
