package core;

public class Model {

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
	boolean currentPlayerNotSponsoring;
	boolean gameWon = false;
	boolean stageResolved = false;
	boolean toggleForStages = false;
	int stagePlaceHolder = 0;
	
	Card currentStoryCard;
	
	int numPlayers;
	int numStages;
	
	CardCollection [] stages;
	CardCollection [] getStages() {return stages;}
	
	Model(Control control){
	
		this.control = control;
		
		this.adventureDeck = new AdventureDeck();
		this.storyDeck = new StoryDeck();
		
		this.adventureDeckDiscard = new AdventureDeck();
		this.storyDeckDiscard = new StoryDeck();
		
		state = new State();
		
		currentPlayer = 0;
		currentSponsor = 0;
		currentPlayerNotSponsoring = false;
	}
	
	public void instantiatePlayers(int numPlayers){
		players = new Player[numPlayers];
		
		for(int i = 0; i < numPlayers; ++i){
			players[i] = new Player(i);
		}
		currentPlayer = 0;
	}
	
	public void instantiateStages(int numStages){
		stages = new CardCollection[numStages];
		
		for(int i = 0; i < numStages; ++i){
			stages[i] = new CardCollection();
		}
		
		currentStage = 0;
	}
	
	public void initialShuffle(){

		this.adventureDeck.shuffle();
		
		this.storyDeck.shuffle();
		
	}
	
	public void deal(){
		
			            // 12 cards in hand
		for(int i = 0; i < 12; ++i){
			for(int j = 0; j < players.length; ++j){
				
				players[j].addToHand(this.adventureDeck.pop()); 
			}
			
			this.currentStoryCard = storyDeck.pop();
		}
	}
	
	public void CardsTest(){
		
		System.out.println("Adventure Deck: \n" + this.adventureDeck.toString());

		System.out.println("Story Deck: \n" + this.storyDeck.toString());
		
		System.out.println("Players Hands: \n\n");
			
		for(int i = 0; i < players.length; ++i){
			System.out.println("Player " + (i+1) + " Hand: \n" + players[i].getHand().toString());
		}
	}
	
	public void resetCurrentStage(){
		setCurrentStage(0);
		
		//this.currentStage = 0;
	}
	
	public State getState(){
		
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
		
		state.currentPlayerNotSponsoring = this.currentPlayerNotSponsoring; 
		
		state.stagesSet = this.stagesSet;
		
		state.stageResolved = this.stageResolved;
		
		state.toggleForStages = this.toggleForStages;
		
		state.stagePlaceHolder = this.stagePlaceHolder;
		
		return state;
	}

	public void party(String iD) {
		//System.out.println("Model: playing to party");		
		CardCollection hand = getActivePlayer().getHand();
		Card c = hand.getByID(iD);
		
		if((((AdventureCard) c).getSubType().equals(AdventureCard.AMOUR)) 
				&& containsAmour(getActivePlayer().getParty())) {
			control.alert("Cannot have more than one amour in party.");
			return;
		}
		
		hand.remove(c);
		getActivePlayer().addToParty(c);
	}
	
	public void stage(String iD) {
		//if(state.players[currentPlayer].isSponsor) {
			//System.out.println("Model: IN STAGE");
			CardCollection hand = this.players[this.currentPlayer].getHand();
			Card c = hand.getByID(iD);
			//System.out.println("c=" + c.getImgName());
			//System.out.println("containsFoe = " + containsFoe(this.stages[currentStage]));
			//System.out.println("containsWeapon = " + containsWeapon(this.stages[currentStage], c.getImgName()));
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
			//System.out.println(stages[currentStage].toString());
		//}
	}
	
	public Player getActivePlayer(){
		if(this.currentPlayer != this.currentViewer)
			return this.players[this.currentViewer];
		return this.players[this.currentPlayer];
	}
	
	public void discard(String iD) {
		System.out.println("Model: IN DISCARD");
		CardCollection hand = getActivePlayer().getHand();
		Card c = hand.getByID(iD);
		
		
		hand.remove(c);
		adventureDeckDiscard.add(c);
	}
	
	public void queue(String iD) {
		System.out.println("Model: IN QUEUE");
		CardCollection hand = getActivePlayer().getHand();
		Card c = hand.getByID(iD);
		
		if(containsSameWeapon(getActivePlayer().getQueue(), ((WeaponCard) c).getName())) {
			control.alert("Cannot have duplicate weapons in queue.");
			return;
		}
		
		hand.remove(c);
		getActivePlayer().addToQueue(c);
	}
	
	public boolean containsSameWeapon(CardCollection collection, String cardName) {
		
		for (int i=0; i<collection.size(); i++) {
			if(((WeaponCard) collection.get(i)).getName().equals(cardName)) {
				//TODO need to ALERT the View
				return true;
			}
		}
		
		return false;
	}
	
	public void dequeue(String iD) {
		System.out.println("Model: IN HAND");
		CardCollection hand = getActivePlayer().getHand();
		Card c = hand.getByID(iD);
		hand.remove(c);
		getActivePlayer().addToHand(c);
	}
	
	public void setCurrentStage(int num) {
		this.currentStage = num;
		control.updateViewState();
		//System.out.println("Model: Current Stage set to: "+ (currentStage+1));
	}
	
	public void endTurn() {
		//this will be how a player can chose to pass his turn to the next player
		//also where we'll intercept the call at the Control to POPUP a blocker
		// so that the previous and next players can't peek eachothers hands
		
		if(players[currentPlayer].isSponsor){
			//System.out.println("HOLA");
			viewerChanged();
			
			//System.out.println("stageResolved: " + this.stageResolved);
		}
		else{
			nextPlayer();
			endTurnCounter++;
		}
		
//		System.out.println("\n\n\nNum players: " + state.numPlayers);
//		System.out.println("Current Player: " + (currentPlayer+1));
	}
	
	public void viewerChanged(){
		
		//System.out.println("in viewerChanged");

		
		if (currentViewer == numPlayers-1){
			currentViewer = 0;
//			this.stageResolved = true;
//			control.updateViewState();
		}
		
		else{
			currentViewer++;
		}
		
		if(players[currentPlayer].isSponsor && currentPlayer == currentViewer){
			currentViewer++;
			this.stageResolved = true;
			//control.resolveStage();

		}
		System.out.println("stage resolved from viewer changed: " + this.stageResolved);
	}
	
	public void stagesSet(){
		this.stagesSet = true;
		control.updateViewState();
	}
	
	public int resolveQuest(){
		return 0;
	}
	
	static int stageOverCount = 0;
	
	public void stageOver(){
		
		for(int i = 0; i < this.numPlayers; ++i){
			if(!this.players[i].isSponsor){
				for(int j = 0; j < this.players[i].getQueue().size(); ++j){
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
	
	public void resolveStage(){
		/**
		 * To resolve a stage, we need to count the following data structures:
		 *    - players Queue
		 *    - players Party
		 *    - players Rank
		 *    vs
		 */
		
		CardCollection currStage = this.stages[this.currentStage];
		
		int stageBP = 0;
		
		for (int i = 0; i < currStage.size(); ++i){
			stageBP += ((AdventureCard)currStage.get(i)).getBattlePoints();
		}
		
		for(int i = 0; i < numPlayers; ++i){
			int playerBP = players[i].getRank().getBattlePoints();
			if (players[i].getQueue() != null) {
				for(int j = 0; j < players[i].getQueue().size(); ++j){
					System.out.println("count QUEUE BPs player" + (i+1));
					playerBP += ((AdventureCard) players[i].getQueue().get(j)).getBattlePoints();
				}
			}
			if (players[i].getParty() != null) {
				for(int j = 0; j < players[i].getParty().size(); ++j){
					System.out.println("count PARTY BPs player" + (i+1));
					System.out.println(((AdventureCard) players[i].getParty().get(j)).getImgName());
					System.out.println(((AdventureCard) players[i].getParty().get(j)).getSubType());
					playerBP += ((AdventureCard) players[i].getParty().get(j)).getBattlePoints();
				}
			}
			if(playerBP >= stageBP && (! players[i].isSponsor)){
				players[i].passedStage = true;
			}
			
			//System.out.println("Player " + (i+1) + " battlePoints: " + playerBP);
			//System.out.println("StageBP: "  + stageBP);
			this.toggleForStages = true;
		}

//		int stageTotal = 0;
//		
//		//count BP's in the stage
//		for (int i=0;i<this.stages[currentStage].size(); i++) {
//			stageTotal += ((AdventureCard)this.stages[currentStage].get(i)).getBattlePoints();
//		}
//		
//		int playerTotal = 0;
//		
//		for(int j=0;j<this.numPlayers;j++) {
//			for (int i=0; i<this.state.players[j].getParty().size(); i++) {
//				playerTotal += ((AdventureCard) this.state.players[j].getParty().get(i)).getBattlePoints();
//			}
//			for (int i=0; i<this.state.players[j].getQueue().size(); i++) {
//				playerTotal += ((AdventureCard) this.state.players[j].getQueue().get(i)).getBattlePoints();
//			}
//			for (int i=0; i<this.state.players[j].getQueue().size(); i++) {
//				playerTotal += (this.state.players[j].getRank()).getBattlePoints();
//			}
//			if(playerTotal>=stageTotal) {
//				this.state.players[j].passedStage = true;
//			}
//			playerTotal = 0;
//		}
//		
//		//TODO CALL THE RESOLVE SCREEN FOR VIEW
//		control.alert("Stage Finished");
	}
	
	public boolean containsFoe(CardCollection collection) {
		
		for (int i=0; i<collection.size(); i++) {
			System.out.println(((AdventureCard) collection.get(i)).getSubType().toString());
			System.out.println(((AdventureCard) collection.get(i)).getSubType().equals(AdventureCard.FOE));
			if(((AdventureCard) collection.get(i)).getSubType().equals(AdventureCard.FOE)) {
				//TODO need to ALERT the View

				return true;
			}
		}
		
		return false;
	}
	
	public boolean containsAmour(CardCollection collection) {
		
		for (int i=0; i<collection.size(); i++) {
			if(((AdventureCard) collection.get(i)).getSubType().equals(AdventureCard.AMOUR)) {
				//TODO need to ALERT the View

				return true;
			}
		}
		
		return false;
	}
	
	public boolean containsWeapon(CardCollection collection, String cardName) {
		
		for (int i=0; i<collection.size(); i++) {
			if(collection.get(i).getImgName().equals(cardName)) {
				//TODO need to ALERT the View
				return true;
			}
		}
		
		return false;
	}
	

	
	public void setScenario1() {
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
//		this.currentStoryCard = this.storyDeck.getByID("129"); //Quest of the green knight
		this.players[0].addToHand(this.adventureDeck.getByID("42"));
		this.players[0].addToHand(this.adventureDeck.getByID("43"));
		this.players[0].addToHand(this.adventureDeck.getByID("1"));
		this.players[0].addToHand(this.adventureDeck.getByID("2"));
		this.players[0].addToHand(this.adventureDeck.getByID("23"));
		this.players[0].addToHand(this.adventureDeck.getByID("48"));
		this.players[0].addToHand(this.adventureDeck.getByID("118"));
		this.players[0].addToHand(this.adventureDeck.getByID("119"));
		this.players[0].addToHand(this.adventureDeck.getByID("120"));
		this.players[0].addToHand(this.adventureDeck.getByID("91"));
		this.players[0].addToHand(this.adventureDeck.getByID("50"));
		this.players[0].addToHand(this.adventureDeck.getByID("88"));
		this.players[1].addToHand(this.adventureDeck.getByID("44"));
		this.players[1].addToHand(this.adventureDeck.getByID("3"));
		this.players[1].addToHand(this.adventureDeck.getByID("4"));
		this.players[1].addToHand(this.adventureDeck.getByID("5"));
		this.players[1].addToHand(this.adventureDeck.getByID("17"));
		this.players[1].addToHand(this.adventureDeck.getByID("18"));
		this.players[1].addToHand(this.adventureDeck.getByID("62"));
		this.players[1].addToHand(this.adventureDeck.getByID("51"));
		this.players[1].addToHand(this.adventureDeck.getByID("52"));
		this.players[1].addToHand(this.adventureDeck.getByID("53"));
		this.players[1].addToHand(this.adventureDeck.getByID("67"));
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
		this.players[2].addToHand(this.adventureDeck.getByID("102"));
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
	
	public void setScenario2() {
		initialShuffle();
		//set current StoryCard to SearchForHolyGrail
		this.currentPlayer = 0;

		//set the quest to be of type "repel the saxon raiders"
		this.currentStoryCard = this.storyDeck.getByID("138");

//the two stages the sponsor will play
		//foes
		this.players[0].addToHand(this.adventureDeck.getByID("50"));  //thief
		this.players[0].addToHand(this.adventureDeck.getByID("91"));  //green knight
		this.players[0].addToHand(this.adventureDeck.getByID("88"));
		//amour
		this.players[0].addToHand(this.adventureDeck.getByID("118"));
		this.players[0].addToHand(this.adventureDeck.getByID("119"));
		this.players[0].addToHand(this.adventureDeck.getByID("120"));
		//Weapon
		this.players[0].addToHand(this.adventureDeck.getByID("42"));
		this.players[0].addToHand(this.adventureDeck.getByID("43"));
		this.players[0].addToHand(this.adventureDeck.getByID("1"));
		this.players[0].addToHand(this.adventureDeck.getByID("2"));
		this.players[0].addToHand(this.adventureDeck.getByID("23"));
		this.players[0].addToHand(this.adventureDeck.getByID("48"));


		//Player 2

		//weapon
		this.players[0].addToHand(this.adventureDeck.getByID("22"));

		//amour

		//foes
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


		//player 3
		//weapon
		this.players[2].addToHand(this.adventureDeck.getByID("24"));
		this.players[2].addToHand(this.adventureDeck.getByID("25"));
		this.players[2].addToHand(this.adventureDeck.getByID("26"));
		this.players[2].addToHand(this.adventureDeck.getByID("27"));
		this.players[2].addToHand(this.adventureDeck.getByID("6"));

		//Amour
		this.players[2].addToHand(this.adventureDeck.getByID("121"));
		this.players[2].addToHand(this.adventureDeck.getByID("122"));

		//foe
		this.players[2].addToHand(this.adventureDeck.getByID("54"));
		this.players[2].addToHand(this.adventureDeck.getByID("82"));
		this.players[2].addToHand(this.adventureDeck.getByID("90"));
		this.players[2].addToHand(this.adventureDeck.getByID("104"));
		this.players[2].addToHand(this.adventureDeck.getByID("125"));

		//player 4
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
	
	public String getSubType(String ID, int currentPlayer){
//		System.out.println("getActivePlayer().getPlayerNumber(): " + getActivePlayer().getPlayerNumber());
//		System.out.println("CARD ID: " + ID);
//		System.out.println();
//		System.out.println();
		return ((AdventureCard)getActivePlayer().getHand().getByID(ID)).getSubType();
		/*
		String ret = "";
		if (currentPlayer != currentViewer){
			ret = ((AdventureCard) players[currentViewer].getHand().getByID(ID)).getSubType();
		}
		else{
			ret = ((AdventureCard) players[currentPlayer].getHand().getByID(ID)).getSubType();
		}
//		
//		if((AdventureCard) players[currentPlayer].getHand().getByID(ID) == null){
//			
//			System.out.println("currentPlayer: " + currentPlayer) ;
//			System.out.println("hand: \n" + players[currentPlayer].getHand().toString());
//			System.out.println("id: " + ID);
//
//		}
		return ret;
		*/
	}


	private void playQuest(){
		if(control.getSponsorDecision()){
			players[currentPlayer].isSponsor = true;
			control.updateViewState();
		} else {
			endTurn();
		}
	}
	
	public void playGame() {
		if (((StoryCard) currentStoryCard).getSubType().equals(StoryCard.QUEST)){
			playQuest();
		} else if (((StoryCard) currentStoryCard).getSubType().equals(StoryCard.EVENT)){
//			playEvent();
		} else if (((StoryCard) currentStoryCard).getSubType().equals(StoryCard.TOURNAMENT)){
//			playTournament();
		} else {
			//shuffle the deck - it's empty
		}
	}

	private void nextPlayer(){
		if(this.currentPlayer == numPlayers - 1){
			this.currentPlayer = 0;
		}
		else{
			this.currentPlayer++;
			this.currentSponsor = this.currentPlayer;
		}
	}
}

/*
Different Game states for banner:
QUEST STAGE 1 FOE
QUEST STAGE 2 FOE
QUEST STAGE 3 FOE
QUEST STAGE 4 FOE
QUEST STAGE 5 FOE
QUEST STAGE 1 TEST
QUEST STAGE 2 TEST
QUEST STAGE 3 TEST
QUEST STAGE 4 TEST
QUEST STAGE 5 TEST
EVENT
TOURNAMENT
*/

