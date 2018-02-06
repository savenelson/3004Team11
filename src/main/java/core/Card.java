package core;

public abstract class Card {
	private static int nextID = 0001;
	
	public static final String ADVENTURE = "adventure";
	public static final String STORY     = "story";
	public static final String RANK      = "rank";
	
	private int id;
	public int getID() {return this.id;}
	
	private String type;
	public String getType() {return this.type;}
	
	//private String imgName = "WeaponSword.gif";

	Card(String type){
		this.id = nextID++;
		
		this.type = type;
	}
}
