package client;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.*;
import server.EventManger;
import server.QuestManager;
import server.Server;

public class ClientModel extends AbstractModel {

	private static final Logger logger = LogManager.getLogger(ClientModel.class);

	public Client control;



	public Player[] getPlayers() {
		return super.getPlayers(); 
	}



	public AdventureDeck getAdventureDeck() {
		return super.adventureDeck;
	}

	private CardCollection<AdventureCard> adventureDeckDiscard;

	public CardCollection<AdventureCard> getAdventureDeckDiscard() {
		return super.adventureDeck;
	}



	public StoryDeck getStoryDeck() {
		return super.storyDeck;
	}

	

	public CardCollection<StoryCard> getStoryDeckDiscard() {
		return super.storyDeckDiscard;
	}

	boolean inNextQ = false;

	int currentViewer;
	int currentPlayer = 0;
	int currentStage;
	int currentSponsor;
	int endTurnCounter = 0;
	boolean gameWon = false;

	boolean AllyInPlaySirGalahad =  false;
	boolean AllyInPlaySirLancelot =  false;
	boolean AllyInPlayKingArthur =  false;
	boolean AllyInPlaySirTristan =  false;
	boolean AllyInPlayKingPellinore =  false;
	boolean AllyInPlaySirGawain =  false;
	boolean AllyInPlaySirPercival =  false;
	boolean AllyInPlayQueenGuinevere =  false;
	boolean AllyInPlayQueenIseult =  false;
	boolean AllyInPlayMerlin =  false;

	StoryCard currentStoryCard;

	int numPlayers;
	int numStages;

	private ArrayList<CardCollection<AdventureCard>> stages;

	protected ArrayList<CardCollection<AdventureCard>> getStages() {
		return super.stages;
	}



	

	ClientModel(Client control) {

		logger.info("Client Model created");

		this.control = control;
		super.state = new State();
		
		super.questManger = new QuestManager(this);
		super.eventManger = new EventManger(this);

		super.stage = new QuestingStage();

		super.adventureDeck = new AdventureDeck();
		super.storyDeck = new StoryDeck();

		super.adventureDeckDiscard = new CardCollection<AdventureCard>();
		super.storyDeckDiscard = new CardCollection<StoryCard>();

	
		super.currentStage = stage.getCurrentStage();
	}
	
	public Player getActivePlayer() {

		return super.players[super.currentPlayer];
	}

	
	public void instantiatePlayers(int numPlayers) {
		super.instantiatePlayers(numPlayers);
	}

	
	public StoryCardState getCurrentState() {
		return super.currentState;
	}
	public void instantiateStages() {
		

		super.instantiateStages();
	}

	public void initialShuffle() {
		super.initialShuffle();
	}

	public void deal() {
	super.deal();
	}

	public void CardsTest() {
		super.CardsTest();
	}

	public void resetCurrentStage() {
	super.resetCurrentStage();
	}

	public State getState() {
		return super.getState();
	}

	public void party(String iD, int currentPlayer) {
		
		logger.debug("party() called");
		CardCollection<AdventureCard> hand = players[currentPlayer].getHand();
		AdventureCard c = hand.getByID(iD);

		if ( c.getSubType().equals(AdventureCard.AMOUR)
				&& containsAmour(players[currentPlayer].getParty())) {
			control.alert("Cannot have more than one amour in party.");
			return;
		}

		super.party(iD, currentPlayer);
		

		
	}

	public boolean stage(String iD, int currentPlayer, int stageNumber) {
		logger.debug("stage() called");

		CardCollection<AdventureCard> hand = super.players[currentPlayer].getHand();
		AdventureCard c = hand.getByID(iD);
		if ((c.getSubType().equals(AdventureCard.FOE))
				&& containsFoe(super.stage.getStageAt( stageNumber))) {
			logger.info("The Sponsor tried to stage more than one Foe . NOT ALLOWED");
			
			control.alert("Cannot stage more than one foe per quest stage.");
			return false;
		}
		if (containsWeapon(super.stage.getStageAt( stageNumber), c.getName())) {
			logger.info("The Sponsor tried to stage duplicate weapons. NOT ALLOWED");
			control.alert("Cannot stage duplicate weapons.");
			return false;
		}
		return super.stage(iD, currentPlayer, stageNumber);
	}

	public void unstage(String iD, int currentPlayer, int stageNumber) {
		super.unstage(iD, currentPlayer, stageNumber);
	}

	public void discard(String iD, int currentPlayer) {
		super.discard(iD, currentPlayer);
	}

	public void assassinate(String iD, int currentPlayer) {
		logger.debug("assassinate() called");

		boolean hasMordred = false;
		int indexMordred = 0;
		CardCollection<AdventureCard> hand = players[currentPlayer].getHand();

		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getName().equals("Mordred")) {
				hasMordred = true;
				indexMordred = i;
			}
		}

		if (hasMordred) {
			int playerHoldingAlly = -1;
			// find who is holding the Ally
			for (int i = 0; i < numPlayers; ++i) {
				CardCollection<AdventureCard> party = players[i].getParty();
				for (int j = 0; j < party.size(); j++) {
					if (party.get(j).getID().equals(iD)) {
						playerHoldingAlly = i;
						logger.info("playerHoldingAlly is:" + playerHoldingAlly);
					}
				}
			}
			
			// remove the ally
			CardCollection<AdventureCard> party = players[playerHoldingAlly].getParty();
			AdventureCard c = party.getByID(iD);
			party.remove(c);
			adventureDeckDiscard.add(c);

			// remove Mordred
			AdventureCard mordred = hand.get(indexMordred);
			hand.remove(mordred);
			adventureDeckDiscard.add(mordred);

			logger.info("Player " + currentPlayer + " assaniated Player " + playerHoldingAlly + "s ally "
					+ c.getName());
			
		} else {
			control.alert("You do not have Mordred in your hand!");
		}
	}


	public void queue(String id, int currentPlayer) {
		logger.debug("queue() called");
		System.out.println("Current player, in queue called" + currentPlayer);

		CardCollection<AdventureCard> hand = new CardCollection<AdventureCard>();
		hand = players[currentPlayer].getHand();
		AdventureCard c = hand.getByID(id);

		if (containsSameWeapon(players[currentPlayer].getQueue(), ((WeaponCard) c).getName())) {
			control.alert("Cannot have duplicate weapons in queue.");
			return;
		}

		super.queue(id, currentPlayer);
	}

	public void dequeue(String iD, int currentPlayer) {
		super.dequeue(iD, currentPlayer);
	}
	
	public boolean containsSameWeapon(CardCollection<AdventureCard> collection, String cardName) {
		return super.containsSameWeapon(collection, cardName);
	}


	public void setCurrentStage(int num) {
		super.setCurrentStage(num);
		control.updateViewState();
	}

	public void endTurn() {
		logger.debug("endTurn() called");
		logger.info("I end turn called changing s ");
		currentState.nextPlayer();
	}

	public void stageOver() {
		super.stageOver();
		control.updateViewState();
	}
	
	/**
	 * This will go through all the allies in play, and update players
	 * 
	 * steps psuedo code: get an ally that's in play set boolean to T apply bonuses
	 * to Player
	 */
	public void allysInPlay() {
		super.allysInPlay();
	}

	public boolean containsFoe(CardCollection<AdventureCard> collection) {
		return super.containsFoe(collection);
	}

	public boolean containsAmour(CardCollection<AdventureCard> collection) {
		return super.containsAmour(collection);
	}

	public boolean containsWeapon(CardCollection<AdventureCard> collection, String cardName) {
		return super.containsAmour(collection);
	}

	public String getSubType(String ID, int currentPlayer) {
		return super.getSubType(ID, currentPlayer);
	}

	protected void playQuest() {
		super.playQuest();
	}

	protected void playEvent() {
		super.playEvent();
	}

	public void playGame() {
	super.playGame();
	}

	public void nextPlayer() {
		super.nextPlayer();
	}

	/*
	public void setNextPlayer(int nextplayer) {

		super.info();
	}*/

	public void nextStory() {
		logger.info("nextStory() called");
		// get ready for the next person

		super.nextStory();
		control.updateViewState();
	}

	public void setScenario1() {
		
		super.setScenario1();
	} // end set scenario 1

	public void setScenario2() {
		super.setScenario2();
	}

	public void setScenarioTest() {
		super.setScenarioTest();
	}

	public void eventTesting() {
	super.eventTesting();
	}
}

