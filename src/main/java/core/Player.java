package core;

public class Player {

	private int playerNumber;
	
	private RankCard rankCard;
	public RankCard getRank(){return rankCard;}
	
	private Hand hand;
	public Hand getHand(){return hand;}
	
	private Hand party;
	public Hand getParty() {return party;}
	
	private int battlePoints;
	public int getBattlePoints() {return battlePoints;}
	
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
	
}
