package core;

public class Player {

	private int playerNumber;

	public boolean passedStage = false;
	public boolean passedQuest = false;
	public boolean isSponsor = false;
	public boolean declinedToSponsor = false;
	
	private RankCard rankCard;
	public RankCard getRank(){return rankCard;}
	
	private CardCollection hand;
	public CardCollection getHand(){return hand;}
	
	private CardCollection party;
	public CardCollection getParty() {return party;}
	
	private CardCollection queue;
	public CardCollection getQueue() {return queue;}
	
	private int shieldCount;
	public int getShieldCount() {return shieldCount;}
	
	public void addShields(int num) {
		this.shieldCount += num;
	}
	public void removeShields(int num) {
		this.shieldCount = this.shieldCount - num;
	}
	
	public Player(int playerNumber){
		
		hand = new CardCollection();
		
		queue = new CardCollection();
		
		party = new CardCollection();
		
		this.playerNumber = playerNumber;
		
		shieldCount = 0;
		
		rankCard = new RankCard(RankCard.SQUIRE);
	}
	
	public String toString(){
		return "Player " + this.playerNumber;
	}
	
	public void addToHand(Card c){
		hand.add(c);
	}
	
	public void addToQueue(Card c){
		queue.add(c);
	}
	
	public void addToParty(Card c){
		party.add(c);
	}
	
}
