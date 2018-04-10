package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {
	private static final Logger logger = LogManager.getLogger(Player.class);

	public int AllyBattlePoints = 0;
	public int AllyBidBonus = 0;
	
	
	private int playerNumber;
	public int getPlayerNumber(){return playerNumber;}

	public boolean isSponsor = false;
	public boolean declinedToSponsor = false;
	public boolean declinedToQuest =false;
	public boolean hasQueuedAgainstStage = false;
	public boolean isQuesting = false;
	public boolean passedStage = false;
	public boolean passedQuest = false;
	public boolean isTournamentWinner = false;
	//add any new booleans to the clearBooleans function below
	
	private RankCard rankCard;
	public RankCard getRank(){return rankCard;}
	
	private CardCollection<AdventureCard> hand;
	public CardCollection<AdventureCard> getHand(){return hand;}
	
	private CardCollection<AdventureCard> party;
	public CardCollection<AdventureCard> getParty() {return party;}
	
	private CardCollection<AdventureCard> queue;
	public CardCollection<AdventureCard> getQueue() {return queue;}
	
	private int shieldCount;
	public int getShieldCount() {return shieldCount;}
	
	public void addShields(int num) {
		
		logger.info(this.playerNumber + " gains " + num + " shields");
		this.shieldCount += num;
		promote();
		promote(); //leave this in! 
		
	}
	public void removeShields(int num) {
		logger.info(this.playerNumber + " loses " + num + " shields");

		this.shieldCount = this.shieldCount - num;
	}
	
	public Player(int playerNumber){
		
		
		hand = new CardCollection<AdventureCard>();
		
		queue = new CardCollection<AdventureCard>();
		
		party = new CardCollection<AdventureCard>();
		
		this.playerNumber = playerNumber;
		
		shieldCount = 0;
		
		rankCard = new RankCard(RankCard.SQUIRE);
	}
	
	public String toString(){
		return "Player " + this.playerNumber;
	}
	
	public void addToHand(AdventureCard c){
		logger.info(this.playerNumber + "s hand changed - added " + c.getName());
		hand.add(c);
	}
	
	public void addToQueue(AdventureCard c){
		logger.info(this.playerNumber + "s queue changed - added " + c.getName());
		queue.add(c);
	}
	
	public void addToParty(AdventureCard c){
		logger.info(this.playerNumber + "s party changed - added " + c.getName());
		party.add(c);
	}
	
	public void discardFromParty(AdventureCard c){
		logger.info(this.playerNumber + "s party changed - discarded " + c.getName());
		party.remove(c);
	}

	public int getBattlePoint() {
		
		int playerBP =this.getRank().getBattlePoints();
		if (this.getQueue() != null) {
			for(int j = 0; j < this.getQueue().size(); ++j){
				playerBP += (this.getQueue().get(j)).getBattlePoints();
			}
		}
		
		return playerBP;
	}
	
	public int getPartyBattlesPoint() {
		int allypoints = 0;
		if (this.getParty() != null) {
			for(int j = 0; j < this.getParty().size(); ++j){
				allypoints += (this.getParty().get(j)).getBattlePoints();
			}
		}
		
		return allypoints;
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
			logger.info(this.playerNumber + " promoted to Knight");

		}else if ((shieldCount >=7 && RankCard.KNIGHT.equals(rankCard.getSubType()))){
			shieldCount  = shieldCount -7;
			rankCard = new RankCard(RankCard.CHAMPION_KNIGHT);
			logger.info(this.playerNumber + " promoted to Champion Knight");
			
			
		}else if((shieldCount >=10 && RankCard.CHAMPION_KNIGHT.equals(rankCard.getSubType()))) {
			logger.info(this.playerNumber + " promoted to Knight of The Round Table - WINNER!");
			logger.info("Game Won by " + this.playerNumber);
		}
	}
	public  boolean canPromote(int potentialShields) {
		if (shieldCount +potentialShields >=5 && RankCard.SQUIRE.equals(rankCard.getSubType())){
			return true; 
		}else if ((shieldCount+ potentialShields >=7 && RankCard.KNIGHT.equals(rankCard.getSubType()))){
			return true;
		}else if((shieldCount + potentialShields >=10 && RankCard.CHAMPION_KNIGHT.equals(rankCard.getSubType()))) {
			
			return true;
			
		}
		else {return false;}
	}
}
