package server;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.*;

public class ServerModel extends AbstractModel {

	private static final Logger logger = LogManager.getLogger(ServerModel.class);

	public Server server;
	
	StoryCardState questManager;
	

	protected StoryCardState eventManger;
	protected core.StoryCardState currentState;

	
	ServerModel(Server server) {
		
		
		logger.info("Model created");

		this.server = server;

		super.state = new State();
		
		eventManger = new EventManger(this);

		questManager = new QuestManager(this);
		currentStoryCard =questManager;

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

	StoryCardState currentStoryCard;

	private int numPlayers;
	int numStages;

	private ArrayList<CardCollection<AdventureCard>> stages;

	

	protected ArrayList<CardCollection<AdventureCard>> getStages() {
		return super.stages;
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
		logger.debug("endTurn() called");
		logger.info("I end turn called changing s ");
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
		currentState = questManger;
	}

	public void playGame() {
		logger.info(" Sever playGame() called" + currentStoryCard);
		
		if (super.currentStoryCard.getSubType().equals(StoryCard.QUEST)) {
			playQuest();
			currentState.handle();
		} else if (super.currentStoryCard.getSubType().equals(StoryCard.EVENT)) {
			playEvent();
			currentState.handle();
		} else if (super.currentStoryCard.getSubType().equals(StoryCard.TOURNAMENT)) {
			// playTournament();
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
}