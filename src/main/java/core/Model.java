package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Model {

	private static final Logger logger = LogManager.getLogger(Model.class);

	public Control control;
	
	State state;
	
	private Player [] players;
	public Player [] getPlayers(){return players;}
	
	private AdventureDeck adventureDeck;
	public AdventureDeck getAdventureDeck(){return this.adventureDeck;}
	
	private AdventureDeck adventureDeckDiscard;
	public AdventureDeck getAdventureDeckDiscard(){return this.adventureDeck;}

	private StoryDeck storyDeck;
	public StoryDeck getStoryDeck(){return storyDeck;}

	private StoryDeck storyDeckDiscard;
	public AdventureDeck getStoryDeckDiscard(){return this.adventureDeck;}

	boolean stagesSet = false;
	int currentViewer;
	int currentPlayer;
	int currentStage;
	int currentSponsor;
	int endTurnCounter = 0;
//	boolean currentPlayerNotSponsoring;
	boolean gameWon = false;
	boolean stageResolved = false;
	boolean toggleForStages = false;
	int stagePlaceHolder = 0;
	static int stageOverCount = 0;

	Card currentStoryCard;
	
	int numPlayers;
	int numStages;
	
	CardCollection [] stages;
	CardCollection [] getStages() {return stages;}
	
	Model(Control control){
		logger.info("Model created");

		this.control = control;
		
		this.adventureDeck = new AdventureDeck();
		this.storyDeck = new StoryDeck();
		
		this.adventureDeckDiscard = new AdventureDeck();
		this.storyDeckDiscard = new StoryDeck();
		
		state = new State();
		
		currentPlayer = 0;
		currentSponsor = 0;
//		currentPlayerNotSponsoring = false;
	}
	
	public void instantiatePlayers(int numPlayers){
		logger.debug("instantiatePlayers(" + numPlayers + ") called");
		players = new Player[numPlayers];

		for(int i = 0; i < numPlayers; ++i){
			players[i] = new Player(i);
		}
		currentPlayer = 0;
	}
	
	public void instantiateStages(){
		logger.debug("instantiateStages() called - hard coded to 5");

		stages = new CardCollection[5];
		
		for(int i = 0; i < 5; ++i){
			stages[i] = new CardCollection();
		}
		
		currentStage = 0;
	}
	
	public void initialShuffle(){
		logger.debug("initialShuffle() called");

		this.adventureDeck.shuffle();
		
		this.storyDeck.shuffle();
		
	}
	
	public void deal(){
		logger.info("deal() called");

			            // 12 cards in hand
		for(int i = 0; i < 12; ++i){
			for(int j = 0; j < players.length; ++j){
				
				players[j].addToHand(this.adventureDeck.pop()); 
			}
			
			this.currentStoryCard = storyDeck.pop();
			logger.info("setting current story card to" + this.currentStoryCard);
		}
	}
	
	public void CardsTest(){
		logger.debug("CardsTest() called");

		System.out.println("Adventure Deck: \n" + this.adventureDeck.toString());

		System.out.println("Story Deck: \n" + this.storyDeck.toString());
		
		System.out.println("Players Hands: \n\n");
			
		for(int i = 0; i < players.length; ++i){
			System.out.println("Player " + (i+1) + " Hand: \n" + players[i].getHand().toString());
		}
	}
	
	public void resetCurrentStage(){
		logger.debug("resetCurrentStage() called");

		setCurrentStage(0);
	}
	
	public State getState(){
		logger.debug("getState() called");

		state.players = this.players;
		
		state.currentPlayer = this.currentPlayer;
		
		state.currentSponsor = this.currentSponsor;
		
		state.currentStoryCard = this.currentStoryCard;
		
		state.currentViewer = this.currentViewer;
		
		if (stages[currentStage]!=null) {
			state.stage = this.stages[currentStage];
		}
		
		state.currentStage = this.currentStage;
		
		state.stages = this.stages;
		
		state.numPlayers = this.numPlayers;
		
		state.numStages = this.numStages;
		
		state.stagesSet = this.stagesSet;
		
		state.stageResolved = this.stageResolved;
		
		state.toggleForStages = this.toggleForStages;
		
		state.stagePlaceHolder = this.stagePlaceHolder;

		return state;
	}
	
	public boolean checkHandSize() {
		logger.debug("checkHandSize() called");

		for(int i=0;i<state.numPlayers;i++) {
			if(players[i].getHand().size() > 12) {
				control.alert("Hand Size is too large, please discard");
				logger.info("Player " + i + " hand too large");

				return false;
			}
		}
		return true;
	}

	public void party(String iD) {
		logger.debug("party() called");

		CardCollection hand = getActivePlayer().getHand();
		Card c = hand.getByID(iD);
		
		if((((AdventureCard) c).getSubType().equals(AdventureCard.AMOUR)) 
				&& containsAmour(getActivePlayer().getParty())) {
			control.alert("Cannot have more than one amour in party.");
			return;
		}
		
		hand.remove(c);
		getActivePlayer().addToParty(c);
		logger.info("Player " + this.currentPlayer + " moved " + c.getName() + " from hand to party");

	}
	
	public void stage(String iD) {
		logger.debug("stage() called");

		CardCollection hand = this.players[this.currentPlayer].getHand();
		Card c = hand.getByID(iD);
		if((((AdventureCard) c).getSubType().equals(AdventureCard.FOE)) 
				&& containsFoe(this.stages[currentStage])) {
			control.alert("Cannot stage more than one foe per quest stage.");
			return;
		}
		if(containsWeapon(this.stages[currentStage], c.getImgName())) {
			control.alert("Cannot stage duplicate weapons.");
			return;
		}
		hand.remove(c);
		stages[currentStage].add(c);
		logger.info("Player " + this.currentPlayer + " moves " + c.getName() + " from hand to Stage " + currentStage);

	}	
	
	public void unstage(String iD) {
		logger.debug("unstage() called");

		
		Card c = this.stages[currentStage].getByID(iD);
		
		this.stages[currentStage].remove(c);
		
		this.players[this.currentPlayer].getHand().add(c);
		
//		for (int i = 0; i < this.stages[currentStage].size(); ++i){
//			
//		}
//		
//		CardCollection hand = this.players[this.currentPlayer].getHand();
//		Card c = hand.getByID(iD);
//		if((((AdventureCard) c).getSubType().equals(AdventureCard.FOE)) 
//				&& containsFoe(this.stages[currentStage])) {
//			control.alert("Cannot stage more than one foe per quest stage.");
//			return;
//		}
//		if(containsWeapon(this.stages[currentStage], c.getImgName())) {
//			control.alert("Cannot stage duplicate weapons.");
//			return;
//		}
//		hand.remove(c);
//		stages[currentStage].add(c);
		logger.info("Player " + this.currentPlayer + " moves " + c.getName() + " from Stage back to Hand");

	}
	
	public Player getActivePlayer(){
		logger.debug("getActivePlayer() called");

		if(this.currentPlayer != this.currentViewer) {
			return this.players[this.currentViewer];
		}
		return this.players[this.currentPlayer];
	}
	
	public void discard(String iD) {
		logger.debug("discard() called");

		CardCollection hand = getActivePlayer().getHand();
		Card c = hand.getByID(iD);
		
		
		hand.remove(c);
		adventureDeckDiscard.add(c);
		logger.info("Player " + this.currentPlayer + " discarded " + c.getName());

	}
	
	public void assassinate(String iD) {
		logger.debug("assassinate() called");
		
		boolean hasMordred = false;
		int indexMordred = 0;
		CardCollection hand = getActivePlayer().getHand();
		
		for(int i=0;i< hand.size();i++) {
			if(hand.get(i).getName().equals("Mordred")){
				hasMordred = true;
				indexMordred = i;
			}
		}
		
		if(hasMordred) {
			int playerHoldingAlly = 0;
			//find who is holding the Ally
			for(int i = 0; i < this.numPlayers; ++i){
				CardCollection party = state.players[i].getParty();
				for(int j=0;j< party.size();j++) {
					if(party.get(j).getID().equals(iD)){
						playerHoldingAlly = i;
					}
				}
			}
			//remove the ally 
			CardCollection party = state.players[playerHoldingAlly].getParty();
			Card c = party.getByID(iD);
			party.remove(c);
			adventureDeckDiscard.add(c);
			
			//remove mordred
			Card mordred = hand.get(indexMordred);
			hand.remove(mordred);
			adventureDeckDiscard.add(mordred);

			logger.info("Player " + this.currentPlayer + " assaniated Player " + playerHoldingAlly + "s ally " + c.getName());
		} else {
			control.alert("You do not have Mordred in your hand!");
		}
		
	}
	
	public void queue(String iD) {
		logger.debug("queue() called");

		CardCollection hand = getActivePlayer().getHand();
		Card c = hand.getByID(iD);
		
		if(containsSameWeapon(getActivePlayer().getQueue(), ((WeaponCard) c).getName())) {
			control.alert("Cannot have duplicate weapons in queue.");
			return;
		}
		
		hand.remove(c);
		getActivePlayer().addToQueue(c);
		logger.info("Player " + this.currentPlayer + " moved " + c.getName() + " from hand to queue");

	}
	
	public boolean containsSameWeapon(CardCollection collection, String cardName) {
		logger.debug("containsSameWeapon(" + cardName + ") called");

		for (int i=0; i<collection.size(); i++) {
			if(((WeaponCard) collection.get(i)).getName().equals(cardName)) {
				//TODO need to ALERT the View
				return true;
			}
		}
		
		return false;
	}

	public void dequeue(String iD) {
		logger.debug("dequeue(" + iD + ") called");
		CardCollection queue = getActivePlayer().getQueue();
		Card c = queue.getByID(iD);
		queue.remove(c);
		getActivePlayer().addToHand(c);
		logger.info("Player " + this.currentPlayer + " moved " + c.getName() + " from queue to hand");
	}
	
	public void setCurrentStage(int num) {
		logger.debug("setCurrentStage(" + num + ") called");

		this.currentStage = num;
		control.updateViewState();
	}
	
	public void endTurn() {
		logger.debug("endTurn() called");

		if(players[currentPlayer].isSponsor){
			viewerChanged();	
		}
		else{
			nextPlayer();
			endTurnCounter++;
		}
	}
	
	public void viewerChanged(){
		logger.debug("viewerChanged() called");

		if (currentViewer == ((numPlayers-1)%numPlayers)){
			currentViewer = 0;
		} else {
			currentViewer++;
		}
		
		if(players[currentPlayer].isSponsor && currentPlayer == currentViewer){
			currentViewer++;
			this.stageResolved = true;
		}
	}
	
	public void stagesSet(){
		logger.debug("stagesSet() called");

		this.stagesSet = true;
		control.updateViewState();
	}
	
	public int resolveQuest(){
		logger.debug("resolveQuest() called");

		int numShields = ((QuestCard) state.currentStoryCard).getNumStages();
		logger.info("Number of Stages: " + numShields);

		//TODO ADD THE BOOLEAN SETTING FOR PASSING QUEST HERE
		for (int i = 0; i < state.numPlayers; ++i){
			if(!players[i].isSponsor){
				System.out.println("Players "+ i+1+ players[i].isQuesting + players[i].passedQuest);

				if(players[i].passedQuest) {
					players[i].addShields(numShields);
					
				}
			} else {
				//TODO GIVE SPONSOR CARDS BACK 
			}
		}
		
		//TODO ADD SHIELDS HERE
		
		control.resolveQuest();
		
		return 0;
	}
	

	public void resolveStage(){
		/**
		 * To resolve a stage, we need to count the following data structures:
		 *    - players Queue
		 *    - players Party
		 *    - players Rank
		 *    - get a card if they pass
		 */
		logger.debug("resolveStage() called");

		
		CardCollection currStage = this.stages[this.currentStage+stageOverCount];
		
		int stageBP = 0;

		for (int i = 0; i < currStage.size(); ++i){
			stageBP += ((AdventureCard)currStage.get(i)).getBattlePoints();
		}
		
		for(int i = 0; i < numPlayers; ++i){
			int playerBP = players[i].getRank().getBattlePoints();
			if (players[i].getQueue() != null) {
				for(int j = 0; j < players[i].getQueue().size(); ++j){
					playerBP += ((AdventureCard) players[i].getQueue().get(j)).getBattlePoints();
				}
			}
			if (players[i].getParty() != null) {
				for(int j = 0; j < players[i].getParty().size(); ++j){
					playerBP += ((AdventureCard) players[i].getParty().get(j)).getBattlePoints();
				}
			}
			
			//Check if player passed quest

			if(playerBP >= stageBP && (! players[i].isSponsor) && stageBP > 0){
				players[i].passedStage = true;
				if(state.currentStage +1==((QuestCard)state.currentStoryCard).getNumStages() ) {
					players[i].passedQuest =true;
					System.out.println("true turned ");

					Card c = this.adventureDeck.pop();
					this.players[i].addToHand(c);
					adventureDeckDiscard.add(c);
				}
			
			this.toggleForStages = true;
		}
			
		}
		if(stageOverCount == ((QuestCard)currentStoryCard).getNumStages()&& stageOverCount != 0){
			resolveQuest();
		}
		
		
	


	}
	
	
	public void stageOver(){
		logger.debug("stageOver() called");

		for(int i = 0; i < this.numPlayers; ++i){
			if(!this.players[i].isSponsor){
				
				int size = this.players[i].getQueue().size();
				
				for(int j = 0; j < size; ++j){
					System.out.println("\n\nthis.players[i].getQueue().size(): " + this.players[i].getQueue().size()); 
					System.out.println("queue popping: "+  this.players[i].getQueue().toString());
					adventureDeckDiscard.add(this.players[i].getQueue().pop());
				}
				players[i].passedStage = false;
			}
		}
		stageOverCount++;
		
		this.currentViewer--;// TODO ??? MAYBE A REALLY BAD FIX MAYBE NOT, WHO KNOWS ANYMORE...
		this.stagesSet = false;
		this.stageResolved = false;
		this.toggleForStages = true;
		this.stagePlaceHolder = this.currentStage + stageOverCount;
		state.stage = this.stages[currentStage];
		control.updateViewState();
	}
	
	public boolean containsFoe(CardCollection collection) {
		logger.debug("containsFoe() called");

		for (int i=0; i<collection.size(); i++) {
			if(((AdventureCard) collection.get(i)).getSubType().equals(AdventureCard.FOE)) {
				//TODO need to ALERT the View

				return true;
			}
		}
		
		return false;
	}
	
	public boolean containsAmour(CardCollection collection) {
		logger.debug("containsAmour() called");

		for (int i=0; i<collection.size(); i++) {
			if(((AdventureCard) collection.get(i)).getSubType().equals(AdventureCard.AMOUR)) {
				//TODO need to ALERT the View

				return true;
			}
		}
		
		return false;
	}
	
	public boolean containsWeapon(CardCollection collection, String cardName) {
		logger.debug("containsWeapon() called");

		for (int i=0; i<collection.size(); i++) {
			if(collection.get(i).getImgName().equals(cardName)) {
				return true;
			}
		}
		
		return false;
	}
	

	public String getSubType(String ID, int currentPlayer){
		logger.debug("getSubType() called");
		String result = "";
		
		if (((AdventureCard)getActivePlayer().getHand().getByID(ID)) != null) {
			result = ((AdventureCard)getActivePlayer().getHand().getByID(ID)).getSubType();
		}
		return result;
	}


	private void playQuest(){
		logger.debug("playQuest() called");
		boolean decision = control.getSponsorDecision();
		if(decision){
			players[currentPlayer].isSponsor = true;
			logger.info("Player " + currentPlayer + " will sponsor");
			control.updateViewState();
			for(int i=0;i<numPlayers;i++) {
				if(!players[i].isSponsor) {
					players[i].getHand().add(this.getAdventureDeck().pop());
				}
			}
		} else {
			players[currentPlayer].isSponsor = false;
			logger.info("Player " + currentPlayer + " will not sponsor");
			control.updateViewState();
			endTurn();
		}
	}
	
	private void playEvent() {
		logger.debug("playEvent() called");

		if (((StoryCard) currentStoryCard).getName().equals("KingsRecognition")) {
//			boolean inNextQ = true;
			// using for loop through this.state.players, in a quest function, if inNextQ =
			// true,
			// this.players[i].addShields(2);

		} else if (((StoryCard) currentStoryCard).getName().equals("QueensFavor")) {
			int squireCount = 0;
			int championCount = 0;
			int championKnightCount = 0;

			for (int i = 0; i < this.state.players[i].getQueue().size(); i++) {

				if ((this.state.players[i].getRank()).getSubType().equals("Squire")) {
					squireCount++;
				}
				if ((this.state.players[i].getRank()).getSubType().equals("Knight")) {
					championCount++;
				}
				if ((this.state.players[i].getRank()).getSubType().equals("ChampionKnight")) {
					championKnightCount++;
				}

			}

			if (squireCount < championCount) {
				for (int i = 0; i < this.state.players[i].getQueue().size(); i++) {
					if ((this.state.players[i].getRank()).getSubType().equals("Squire")) {
						this.players[i].addToHand(this.adventureDeck.getByID("6"));
						this.players[i].addToHand(this.adventureDeck.getByID("7"));
					}
				}

			} // we then know there is one squire. give him 2 adventure cards

			if (championCount < championKnightCount && (squireCount == 0)) {
				for (int i = 0; i < this.state.players[i].getQueue().size(); i++) {
					if ((this.state.players[i].getRank()).getSubType().equals("Champion")) {
						this.players[i].addToHand(this.adventureDeck.getByID("6"));
						this.players[i].addToHand(this.adventureDeck.getByID("7"));
					}
				}
				// there are less champions than championKnights
			}
			if (championKnightCount == numPlayers || squireCount == numPlayers || championCount == numPlayers) {
				for (int i = 0; i < this.state.players[i].getQueue().size(); i++) {
					this.players[i].addToHand(this.adventureDeck.getByID("6"));
					this.players[i].addToHand(this.adventureDeck.getByID("7"));
				}
			}
		} else if (((StoryCard) currentStoryCard).getName().equals("CourtCalled")) {
			for (int i = 0; i < this.state.players[i].getQueue().size(); i++) {
				CardCollection hand = this.players[i].getHand();

				for (int j = 0; j < hand.size(); j++) {

					if (hand.getByID("100") != null) {
						Card c = hand.getByID("100");
						hand.remove(c);
						adventureDeckDiscard.add(c);
					} else if (hand.getByID("101") != null) {
						Card c = hand.getByID("101");
						hand.remove(c);
						adventureDeckDiscard.add(c);
					} else if (hand.getByID("102") != null) {
						Card c = hand.getByID("102");
						hand.remove(c);
						adventureDeckDiscard.add(c);
					} else if (hand.getByID("103") != null) {
						Card c = hand.getByID("103");
						hand.remove(c);
						adventureDeckDiscard.add(c);
					} else if (hand.getByID("104") != null) {
						Card c = hand.getByID("104");
						hand.remove(c);
						adventureDeckDiscard.add(c);
					} else if (hand.getByID("105") != null) {
						Card c = hand.getByID("105");
						hand.remove(c);
						adventureDeckDiscard.add(c);
					} else if (hand.getByID("106") != null) {
						Card c = hand.getByID("106");
						hand.remove(c);
						adventureDeckDiscard.add(c);
					} else if (hand.getByID("107") != null) {
						Card c = hand.getByID("107");
						hand.remove(c);
						adventureDeckDiscard.add(c);
					} else if (hand.getByID("108") != null) {
						Card c = hand.getByID("108");
						hand.remove(c);
						adventureDeckDiscard.add(c);
					} else if (hand.getByID("109") != null) {
						Card c = hand.getByID("109");
						hand.remove(c);
						adventureDeckDiscard.add(c);
					}
				}
			}
		} else if (((StoryCard) currentStoryCard).getName().equals("Pox")) {
			for (int i = 0; i < this.state.players[i].getQueue().size(); i++) {
				this.players[i].removeShields(1);
			}
			this.players[currentPlayer].addShields(1); // adds shield that was not supposed to be taken away
		} else if (((StoryCard) currentStoryCard).getName().equals("Plague")) {
			if (this.players[currentPlayer].getShieldCount() >= 2) {
				this.players[currentPlayer].removeShields(2);
			}
		} else if (((StoryCard) currentStoryCard).getName().equals("ChivalrousDeed")) {
		} else if (((StoryCard) currentStoryCard).getName().equals("ProsperityThroughoutTheRealm")) {
			for (int i = 0; i < this.state.players[i].getQueue().size(); i++) {
				this.players[i].addToHand(this.adventureDeck.getByID("6"));
				this.players[i].addToHand(this.adventureDeck.getByID("7"));
			}
		} else if (((StoryCard) currentStoryCard).getName().equals("KingsCallToArms")) {
		}
	}
	
	public void playGame() {
		logger.debug("playGame() called");

		if (((StoryCard) currentStoryCard).getSubType().equals(StoryCard.QUEST)){
			playQuest();
		} else if (((StoryCard) currentStoryCard).getSubType().equals(StoryCard.EVENT)){
			playEvent();
		} else if (((StoryCard) currentStoryCard).getSubType().equals(StoryCard.TOURNAMENT)){
//			playTournament();
		} else {
			adventureDeck = adventureDeckDiscard;
			adventureDeck.shuffle();
		}
		checkHandSize();
	}
	
	private void nextPlayer(){
		logger.debug("nextPlayer() called");

		if(this.currentPlayer == numPlayers - 1){
			this.currentPlayer = 0;

		}
		else{
			this.currentPlayer++;
			this.currentSponsor = this.currentPlayer;
		}
		logger.info("Player changed to " + this.currentPlayer);
	}

	public void nextStory() {
		logger.debug("nextStory() called");
		//get ready for the next person
		for(int i = 0; i < numPlayers; ++i){
			
			players[i].isSponsor = false;
			players[i].isQuesting = false;
			players[i].passedQuest = false;
			players[i].passedStage = false;
			stagesSet = false;
			
			
			
			CardCollection queue = players[i].getQueue();
			for(int j = 0; j < queue.size(); ++j){
				if(((AdventureCard) queue.get(j)).getSubType().equals(AdventureCard.AMOUR)){
					Card c = queue.get(j);
					queue.remove(j);
					adventureDeckDiscard.add(c);
				}
			}
		}
		
		
		storyDeckDiscard.add(this.currentStoryCard);
		this.currentStoryCard = storyDeck.pop();

		this.currentStage = 0;
		
		this.currentSponsor = -1;
		
		this.stageResolved = false;
		
		this.toggleForStages = false;
		
		this.stagePlaceHolder = 0;
		
		nextPlayer();
		this.currentViewer = this.currentPlayer;
		System.out.println(currentStoryCard.getName());
		control.updateViewState();
		playGame();
	}
	
	
	public void setScenario1() {
		logger.debug("setScenario1() called - Setting up SCENARIO ONE");

		/**
		 * Scenario 1
		 * Story Card: 			Board Hunt
		 * Number of Players: 	2
		 * Current Player: 		Player 1
		 * Rules that need to be implimented:
		 * -	Only 1 Foe per stage - Nelson
		 * -	No repeated weapon/type in a stage - Nelson
		 * -	UI notification of above No repeated weapon/type in a stage
		 * -	(stageN's BP) < (stageN+1's BP)
		 * -	UI notification of above (stageN's BP) < (stageN+1's BP)
		 * -	After stages set, ask players 2,3,4 if they will play
		 * -	Each participant draw card
		 * -	Hand size limitation
		 * -	Hotseat play popup "Is this player 2?"
		 * -	Revealing stages (line 38 of grid)
		 * -	
		 * -	
		 */
		this.currentPlayer = 0;
		this.currentStoryCard = this.storyDeck.getByID("126"); //BOAR  hUNT
		Card c = this.getStoryDeck().pop();
		storyDeckDiscard.add(c);
		
		c = this.adventureDeck.getByID("42");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		
		c = this.adventureDeck.getByID("43");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		
		c = this.adventureDeck.getByID("1");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		
		c = this.adventureDeck.getByID("2");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		
		c = this.adventureDeck.getByID("23");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);

		c = this.adventureDeck.getByID("48");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		
		c = this.adventureDeck.getByID("118");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		
		c = this.adventureDeck.getByID("119");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);

		c = this.adventureDeck.getByID("120");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		
		c = this.adventureDeck.getByID("91");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
	
		c = this.adventureDeck.getByID("50");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("88");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		
		c = this.adventureDeck.getByID("124");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);

		c = this.adventureDeck.getByID("3");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);

		c = this.adventureDeck.getByID("4");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);

		c = this.adventureDeck.getByID("5");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);

		c = this.adventureDeck.getByID("17");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);

		c = this.adventureDeck.getByID("18");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);

		c = this.adventureDeck.getByID("62");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);

		c = this.adventureDeck.getByID("51");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);

		c = this.adventureDeck.getByID("52");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);

		c = this.adventureDeck.getByID("53");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);

		c = this.adventureDeck.getByID("67");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);

		c = this.adventureDeck.getByID("89");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);

		c = this.adventureDeck.getByID("24");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);

		c = this.adventureDeck.getByID("25");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("26");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("27");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("6");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("121");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("122");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("54");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("82");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("90");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("104");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("102");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("34");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("28");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("19");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("7");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("8");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("9");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("123");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("68");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("63");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("93");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("100");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		c = this.adventureDeck.getByID("101");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
	}
	
	
	public void setScenario2() {
		logger.debug("setScenario2() called - Setting up SCENARIO TWO");

		this.currentPlayer = 0;
		this.currentStoryCard = this.storyDeck.getByID("138");
		this.players[0].addToHand(this.adventureDeck.getByID("50"));  //thief
		this.players[0].addToHand(this.adventureDeck.getByID("91"));  //green knight
		this.players[0].addToHand(this.adventureDeck.getByID("88"));
		this.players[0].addToHand(this.adventureDeck.getByID("118"));
		this.players[0].addToHand(this.adventureDeck.getByID("119"));
		this.players[0].addToHand(this.adventureDeck.getByID("120"));
		this.players[0].addToHand(this.adventureDeck.getByID("42"));
		this.players[0].addToHand(this.adventureDeck.getByID("43"));
		this.players[0].addToHand(this.adventureDeck.getByID("1"));
		this.players[0].addToHand(this.adventureDeck.getByID("2"));
		this.players[0].addToHand(this.adventureDeck.getByID("23"));
		this.players[0].addToHand(this.adventureDeck.getByID("48"));
		this.players[0].addToHand(this.adventureDeck.getByID("22"));
		this.players[1].addToHand(this.adventureDeck.getByID("82")); //evil knight
		this.players[1].addToHand(this.adventureDeck.getByID("74")); // Saxon knight
		this.players[1].addToHand(this.adventureDeck.getByID("51")); // thief 1
		this.players[1].addToHand(this.adventureDeck.getByID("52"));// thief 2
		this.players[1].addToHand(this.adventureDeck.getByID("53")); // thief 3
		this.players[1].addToHand(this.adventureDeck.getByID("67")); //robber knight
		this.players[1].addToHand(this.adventureDeck.getByID("3"));
		this.players[1].addToHand(this.adventureDeck.getByID("4"));
		this.players[1].addToHand(this.adventureDeck.getByID("5"));
		this.players[1].addToHand(this.adventureDeck.getByID("17"));
		this.players[1].addToHand(this.adventureDeck.getByID("18"));
		this.players[1].addToHand(this.adventureDeck.getByID("89"));
		this.players[2].addToHand(this.adventureDeck.getByID("24"));
		this.players[2].addToHand(this.adventureDeck.getByID("25"));
		this.players[2].addToHand(this.adventureDeck.getByID("26"));
		this.players[2].addToHand(this.adventureDeck.getByID("27"));
		this.players[2].addToHand(this.adventureDeck.getByID("6"));
		this.players[2].addToHand(this.adventureDeck.getByID("121"));
		this.players[2].addToHand(this.adventureDeck.getByID("122"));
		this.players[2].addToHand(this.adventureDeck.getByID("54"));
		this.players[2].addToHand(this.adventureDeck.getByID("82"));
		this.players[2].addToHand(this.adventureDeck.getByID("90"));
		this.players[2].addToHand(this.adventureDeck.getByID("104"));
		this.players[2].addToHand(this.adventureDeck.getByID("125"));
		this.players[3].addToHand(this.adventureDeck.getByID("34"));
		this.players[3].addToHand(this.adventureDeck.getByID("28"));
		this.players[3].addToHand(this.adventureDeck.getByID("19"));
		this.players[3].addToHand(this.adventureDeck.getByID("7"));
		this.players[3].addToHand(this.adventureDeck.getByID("8"));
		this.players[3].addToHand(this.adventureDeck.getByID("9"));
		this.players[3].addToHand(this.adventureDeck.getByID("123"));
		this.players[3].addToHand(this.adventureDeck.getByID("68"));
		this.players[3].addToHand(this.adventureDeck.getByID("63"));
		this.players[3].addToHand(this.adventureDeck.getByID("93"));
		this.players[3].addToHand(this.adventureDeck.getByID("100"));
		this.players[3].addToHand(this.adventureDeck.getByID("101"));
	}
	
	public void setScenarioTest() {
		logger.info("setScenarioTest() called - Setting up TEST SCENARIO");


		this.currentPlayer = 0;

		this.players[0].addShields(10);
		this.players[1].addShields(6);
		this.players[2].addShields(14);
		
		stages[0].add(this.adventureDeck.getByID("57"));
		stages[1].add(this.adventureDeck.getByID("86"));

		this.currentStoryCard = this.storyDeck.getByID("126"); //BOAR  hUNT 		this.players[0].addToParty(this.adventureDeck.getByID("100"));
		this.players[0].addToParty(this.adventureDeck.getByID("101"));
		this.players[0].addToParty(this.adventureDeck.getByID("122"));
		this.players[1].addToParty(this.adventureDeck.getByID("103"));
		this.players[1].addToParty(this.adventureDeck.getByID("104"));
		this.players[1].addToParty(this.adventureDeck.getByID("123"));
		this.players[2].addToParty(this.adventureDeck.getByID("106"));
		this.players[2].addToParty(this.adventureDeck.getByID("107"));
		this.players[2].addToParty(this.adventureDeck.getByID("124"));
		this.players[3].addToParty(this.adventureDeck.getByID("109"));
		this.players[3].addToParty(this.adventureDeck.getByID("102"));
		this.players[3].addToParty(this.adventureDeck.getByID("125"));
		this.players[0].addToQueue(this.adventureDeck.getByID("13"));
		this.players[0].addToQueue(this.adventureDeck.getByID("30"));
		this.players[0].addToQueue(this.adventureDeck.getByID("37"));
		this.players[0].addToQueue(this.adventureDeck.getByID("45"));
		this.players[1].addToQueue(this.adventureDeck.getByID("14"));
		this.players[1].addToQueue(this.adventureDeck.getByID("31"));
		this.players[1].addToQueue(this.adventureDeck.getByID("38"));
		this.players[1].addToQueue(this.adventureDeck.getByID("46"));
		this.players[2].addToQueue(this.adventureDeck.getByID("15"));
		this.players[2].addToQueue(this.adventureDeck.getByID("32"));
		this.players[2].addToQueue(this.adventureDeck.getByID("38"));
		this.players[2].addToQueue(this.adventureDeck.getByID("47"));
		this.players[3].addToQueue(this.adventureDeck.getByID("16"));
		this.players[3].addToQueue(this.adventureDeck.getByID("33"));
		this.players[3].addToQueue(this.adventureDeck.getByID("39"));
		this.players[3].addToQueue(this.adventureDeck.getByID("48"));
		this.players[0].addToHand(this.adventureDeck.getByID("1"));
		this.players[0].addToHand(this.adventureDeck.getByID("2"));
		this.players[0].addToHand(this.adventureDeck.getByID("23"));
		this.players[0].addToHand(this.adventureDeck.getByID("50"));
		this.players[0].addToHand(this.adventureDeck.getByID("58"));
		this.players[0].addToHand(this.adventureDeck.getByID("93"));
		this.players[0].addToHand(this.adventureDeck.getByID("118"));
		this.players[0].addToHand(this.adventureDeck.getByID("105"));
		this.players[0].addToHand(this.adventureDeck.getByID("67"));
		this.players[0].addToHand(this.adventureDeck.getByID("99"));
		this.players[0].addToHand(this.adventureDeck.getByID("98"));
		this.players[0].addToHand(this.adventureDeck.getByID("97"));
//		this.players[0].addToHand(this.adventureDeck.getByID("92")); //13th card for hand!
		this.players[1].addToHand(this.adventureDeck.getByID("3"));
		this.players[1].addToHand(this.adventureDeck.getByID("4"));
		this.players[1].addToHand(this.adventureDeck.getByID("24"));
		this.players[1].addToHand(this.adventureDeck.getByID("51"));
		this.players[1].addToHand(this.adventureDeck.getByID("59"));
		this.players[1].addToHand(this.adventureDeck.getByID("94"));
		this.players[1].addToHand(this.adventureDeck.getByID("119"));
		this.players[1].addToHand(this.adventureDeck.getByID("108"));
		this.players[1].addToHand(this.adventureDeck.getByID("68"));
		this.players[1].addToHand(this.adventureDeck.getByID("27"));
		this.players[1].addToHand(this.adventureDeck.getByID("34"));
		this.players[1].addToHand(this.adventureDeck.getByID("42"));
		this.players[2].addToHand(this.adventureDeck.getByID("5"));
		this.players[2].addToHand(this.adventureDeck.getByID("6"));
		this.players[2].addToHand(this.adventureDeck.getByID("25"));
		this.players[2].addToHand(this.adventureDeck.getByID("52"));
		this.players[2].addToHand(this.adventureDeck.getByID("60"));
		this.players[2].addToHand(this.adventureDeck.getByID("95"));
		this.players[2].addToHand(this.adventureDeck.getByID("120"));
		this.players[2].addToHand(this.adventureDeck.getByID("69"));
		this.players[2].addToHand(this.adventureDeck.getByID("28"));
		this.players[2].addToHand(this.adventureDeck.getByID("35"));
		this.players[2].addToHand(this.adventureDeck.getByID("43"));
		this.players[2].addToHand(this.adventureDeck.getByID("48"));
		this.players[3].addToHand(this.adventureDeck.getByID("7"));
		this.players[3].addToHand(this.adventureDeck.getByID("8"));
		this.players[3].addToHand(this.adventureDeck.getByID("26"));
		this.players[3].addToHand(this.adventureDeck.getByID("53"));
		this.players[3].addToHand(this.adventureDeck.getByID("61"));
		this.players[3].addToHand(this.adventureDeck.getByID("87"));
		this.players[3].addToHand(this.adventureDeck.getByID("121"));
		this.players[3].addToHand(this.adventureDeck.getByID("70"));
		this.players[3].addToHand(this.adventureDeck.getByID("29"));
		this.players[3].addToHand(this.adventureDeck.getByID("36"));
		this.players[3].addToHand(this.adventureDeck.getByID("44"));
	}
}


