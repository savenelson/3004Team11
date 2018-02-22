package core;

public class Player {

	private int playerNumber;
	
	private RankCard rankCard;
	public RankCard getRank(){return rankCard;}
	
	private CardCollection hand;
	public CardCollection getHand(){return hand;}
	
	private CardCollection party;
	public CardCollection getParty() {return party;}
	
	private CardCollection queue;
	public CardCollection getQueue() {return queue;}
	
	private int battlePoints;
	public int getBattlePoints() {return battlePoints;}
	
	public Player(int playerNumber){
		
		hand = new CardCollection();
		
		queue = new CardCollection();
		
		party = new CardCollection();
		
		this.playerNumber = playerNumber;
		
		rankCard = new RankCard(RankCard.SQUIRE);
	}
	
	public String toString(){
		return "Player " + this.playerNumber;
	}
	
	public void pickUp(Card c){
		hand.add(c);
	}
	
	public void queueUp(Card c){
		queue.add(c);
	}
	
}
