package core;

public abstract class StoryCard extends Card{
	
	public static final String EVENT = "Event";
	public static final String QUEST = "Quest";
	public static final String TOURNAMENT = "Tournament";

	protected String name;
	protected String subType;
	public String getSubType() {return this.subType;}
	
	StoryCard(String subType) {
		super(STORY);
		
		this.subType = subType;
	}

	
	@Override
	public String getImgName() {
		return this.subType + this.name;
	}
}
