package server;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.*;

public class ServerModel extends AbstractModel {

	private static final Logger logger = LogManager.getLogger(ServerModel.class);

	public Server server;
	
	
	StoryCardState duelManager;
	StoryCardState questManager;
	StoryCardState eventManager;
	StoryCardState currentState;

	ServerModel(Server server) {
		
		logger.info("Model created");

		this.server = server;

		super.state = new State();

		eventManager = new EventManager(this);
	
		duelManager= new TournamentManger(this);
		questManager = new QuestManager(this);

		super.stage = new QuestingStage();

		super.adventureDeck = new AdventureDeck();
		super.storyDeck = new StoryDeck();
		super.adventureDeckDiscard = new CardCollection<AdventureCard>();
		super.storyDeckDiscard = new CardCollection<StoryCard>();

		super.currentStage = getStage().getCurrentStage();

		currentPlayer = 0;
		currentSponsor = 0;

	}
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

	private int numPlayers;
	int numStages;

	private boolean isKingRecognition = false;



	public ArrayList<CardCollection<AdventureCard>> getStages() {
		return super.getStages();
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

	public void draw(String iD, int currentPlayer) {
		logger.info("draw() called");
		super.draw(iD, currentPlayer);
	}
	public void removeFromParty(String iD, int currentPlayer) {
		logger.info("removeFromParty() called");
		super.removeFromParty(iD, currentPlayer);
	}
	public void removeShields(int numShields, int currentPlayer) {
		logger.info("removeShields() called");
		super.removeShields(numShields, currentPlayer);
	}
	public void party(String iD, int currentPlayer) {
		logger.info("party() called");
		super.party(iD, currentPlayer);
		

		
	}

	public boolean stage(String iD, int currentPlayer, int stageNumber) {
		
		return super.stage(iD, currentPlayer, stageNumber);
	}

	public void unstage(String iD, int currentPlayer, int stageNumber) {
		super.unstage(iD, currentPlayer, stageNumber);
	}

	public void discard(String iD, int currentPlayer) {
		super.discard(iD, currentPlayer);
	}

	public void assassinate(String iD, int currentPlayer) {
		super.assassinate(iD, currentPlayer);
	}


	public void queue(String id, int currentPlayer) {
		
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
		
	}

	public void endTurn() {
		

		currentState.increaseResponse();

		logger.info("I end turn called changing ");

		currentState.nextPlayer();
		
		currentState.handle();
	}

	public void stageOver() {
		super.stageOver();
		
	}
	
	/**
	 * This will go through all the allies in play, and update players
	 * 
	 * steps psuedo code: get an ally that's in play set boolean to T apply bonuses
	 * to Player
	 */
	public void allysInPlay() {
		logger.info("ServerModel - allysInPlay() called");
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
		currentState = questManager;
	}

	protected void playEvent() {
		currentState = eventManager;
	}
	private void playTournament() {
		currentState = duelManager;
		
	}
	public void playGame() {
		logger.info("  playGame() called");
		
		if (super.getCurrentStoryCard().getSubType().equals(StoryCard.QUEST)) {
			playQuest();
			currentState.handle();
		} else if (super.getCurrentStoryCard().getSubType().equals(StoryCard.EVENT)) {
			playEvent();
			currentState.handle();
		} else if (super.getCurrentStoryCard().getSubType().equals(StoryCard.TOURNAMENT)) {
			 playTournament();
			 currentState.handle();
		} else {
			adventureDeck = (AdventureDeck) adventureDeckDiscard;
			adventureDeck.shuffle();
		}
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
	public int getNumPlayers() {
		return numPlayers;
	}
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	public boolean isKingRecognition() {
		return isKingRecognition;
	}
	public void setKingRecognition(boolean isKingRecognition) {
		this.isKingRecognition = isKingRecognition;
	}
}