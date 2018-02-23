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
	
	int currentPlayer;
	int currentStage;

	Card currentStoryCard;
	
	
	private int numPlayers;
	
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
	
	public State getState(){
		
		state.players = this.players;
		
		state.currentPlayer = this.currentPlayer;
		
		state.currentStoryCard = this.currentStoryCard;
		
		if (stages[currentStage]!=null) {
			state.stage = this.stages[currentStage];
		}
		
		state.setNumPlayers(control.view.menu.numberSelected());
		
		return state;
	}

	public void party(String iD) {
		System.out.println("Model: playing to party");		
		CardCollection hand = this.players[this.currentPlayer].getHand();
		Card c = hand.getByID(iD);
		
		if((((AdventureCard) c).getSubType().equals(AdventureCard.AMOUR)) 
				&& containsAmour(this.players[this.currentPlayer].getParty())) {
			control.alert("Cannot have more than one amour in party.");
			return;
		}
		
		hand.remove(c);
		this.players[this.currentPlayer].addToParty(c);
	}
	
	public void stage(String iD) {
		System.out.println("Model: IN STAGE");
		CardCollection hand = this.players[this.currentPlayer].getHand();
		Card c = hand.getByID(iD);
		System.out.println("c=" + c.getImgName());
		System.out.println("containsFoe = " + containsFoe(this.stages[currentStage]));
		System.out.println("containsWeapon = " + containsWeapon(this.stages[currentStage], c.getImgName()));
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
		System.out.println(stages[currentStage].toString());
	}
	
	public void discard(String iD) {
		System.out.println("Model: IN DISCARD");
		CardCollection hand = this.players[this.currentPlayer].getHand();
		Card c = hand.getByID(iD);
		hand.remove(c);
		adventureDeckDiscard.add(c);
	}
	
	public void queue(String iD) {
		System.out.println("Model: IN QUEUE");
		CardCollection hand = this.players[this.currentPlayer].getHand();
		Card c = hand.getByID(iD);
		hand.remove(c);
		players[currentPlayer].addToQueue(c);
	}
	
	public void dequeue(String iD) {
		System.out.println("Model: IN HAND");
		CardCollection hand = this.players[this.currentPlayer].getQueue();
		Card c = hand.getByID(iD);
		hand.remove(c);
		players[currentPlayer].addToHand(c);
	}
	
	public void setCurrentStage(int num) {
		currentStage = num;
		System.out.println("Model: Current Stage set to: "+ (currentStage+1));
	}
	
	public void endTurn() {
		//this will be how a player can chose to pass his turn to the next player
		//also where we'll intercept the call at the Control to POPUP a blocker
		// so that the previous and next players can't peek eachothers hands
		this.currentPlayer = ((currentPlayer+1) % getState().getNumPlayers());
		
		
		
		System.out.println("\n\n\nNum players: " + state.getNumPlayers());
		System.out.println("Current Player: " + (currentPlayer+1));
	}
	
	
	public int resolveQuest(){
		return 0;
	}
	
	private int resolveStage(){
		return 0;
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
		this.currentStoryCard = this.storyDeck.getByID("126");
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
		System.out.println("Adventure Deck: \n" + this.storyDeck.toString());
		//set current StoryCard to SearchForHolyGrail
	}
	
	public String getSubType(String ID, int currentPlayer){
		return ((AdventureCard) players[currentPlayer].getHand().getByID(ID)).getSubType();
	}

}
