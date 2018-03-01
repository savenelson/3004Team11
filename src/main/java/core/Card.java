package core;

public abstract class Card {
	private static int nextID = 0001;
	
	public static final String ADVENTURE = "Adventure";
	public static final String STORY     = "Story";
	public static final String RANK      = "Rank";
	
	protected String id;
	public String getID() {return this.id;}
	
	protected String type;
	public String getType() {return this.type;}
	
	//private String imgPath = "WeaponSword";
	public abstract String getImgName();
	
	Card(String type){
		this.id = String.valueOf(nextID++);
		
		this.type = type;
	}
	
	public abstract String getName();
	
	public abstract String toString();
}
