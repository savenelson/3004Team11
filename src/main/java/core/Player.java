package core;

public class Player {

	private int playerNumber;
	
	private RankCard rankCard;
	
	private Hand hand;
	
	private Hand party;
	
	private int battlePoints;
	
	
	public Player(int playerNumber){
		
		hand = new Hand();
		
		this.playerNumber = playerNumber;
		
		rankCard = new RankCard(RankCard.SQUIRE);
	}
	
	public String toString(){
		return "Player " + this.playerNumber;
	}
	
	public void pickUp(Card c){
		hand.add(c);
	}
	
	public Hand getHand(){return hand;}
}
