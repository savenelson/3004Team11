package core;

public class State {
	
	public Player [] players;	
	
	public Card currentStoryCard;
	
	public int currentPlayer;

	public CardCollection stage;
	
	public int currentSponsor;
	
	private int numPlayers;
	
	public void setNumPlayers(int i){this.numPlayers = i;}
	public int getNumPlayers(){return this.numPlayers;}
}
