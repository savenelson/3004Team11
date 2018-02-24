package core;

public class Player {

	private int playerNumber;
	public int getPlayerNumber(){return playerNumber;}

	public boolean isSponsor = false;
	public boolean declinedToSponsor = false;
	public boolean hasQueuedAgainstStage = false;
	public boolean isQuesting = false;
	public boolean passedStage = false;
	public boolean passedQuest = false;
	//add any new booleans to the clearBooleans function below
	
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
		promote();
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

	public void clearBooleans() {
		this.passedStage = false;
		this.passedQuest = false;
		this.isSponsor = false;
		this.declinedToSponsor = false;
	}
	
	public void promote() {
		if (shieldCount >=5 && RankCard.SQUIRE.equals(rankCard.getSubType())){
			shieldCount  = shieldCount -5;
			rankCard = new RankCard(RankCard.KNIGHT);
			System.out.println("Promoted to a Knight");
		}else if ((shieldCount >=7 && RankCard.KNIGHT.equals(rankCard.getSubType()))){
			shieldCount  = shieldCount -7;
			rankCard = new RankCard(RankCard.CHAMPION_KNIGHT);
			System.out.println("Promoted to a Champion Knight");
			
			
		}else if((shieldCount >=10 && RankCard.CHAMPION_KNIGHT.equals(rankCard.getSubType()))) {
			System.out.println("Winner");
			
		}
	}
}
