package core;

public abstract class Card {
	private static int nextID = 0001;
	
	public static final String ADVENTURE = "Adventure";
	public static final String STORY     = "Story";
	public static final String RANK      = "Rank";
	
	protected int id;
	public int getID() {return this.id;}
	
	protected String type;
	public String getType() {return this.type;}
	
	//private String imgName = "WeaponSword.gif";

	Card(String type){
		this.id = nextID++;
		
		this.type = type;
	}
	
	public abstract String toString();
}
