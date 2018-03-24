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
	
	boolean inNextQ = false;
	
	int currentViewer;
	int currentPlayer;
	int currentStage;
	int currentSponsor;
	int endTurnCounter = 0;
//	boolean currentPlayerNotSponsoring;
	boolean gameWon = false;


	int stagePlaceHolder = 0;
	static int stageOverCount = 0;

	Card currentStoryCard;
	
	int numPlayers;
	int numStages;
	
	//CardCollection [] stages;
	
	QuestingStage stage;
	//CardCollection [] getStages() {return stages;}
	
	StoryCardState questManger;
	StoryCardState eventManger;
	StoryCardState currentState;
	boolean isDoneQuestingMode = false;
	
	
	Model(Control control){
		
		logger.info("Model created");

		this.control = control;
		
		questManger= new QuestManager(this);
		eventManger = new EventManger(this);
		
		
		stage = new QuestingStage();
		
		this.adventureDeck = new AdventureDeck();
		this.storyDeck = new StoryDeck();
		
		this.adventureDeckDiscard = new AdventureDeck();
		this.storyDeckDiscard = new StoryDeck();
		
		state = new State();
		
		currentPlayer = 0;
		currentSponsor = 0;
		
		currentStage = stage.getCurrentStage();

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


		
		stage = new QuestingStage();
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

		
		stage.resetCurrentStage();
	}
	
	public State getState(){
		logger.debug("getState() called");

		state.players = this.players;
		
		state.currentPlayer = this.currentPlayer;
		
		state.isQuesting = this.getActivePlayer().isQuesting;
		
		state.currentSponsor = this.currentSponsor;
		
		state.inNextQ = this.inNextQ;
		
		state.currentStoryCard = this.currentStoryCard;
		
		state.currentViewer = this.currentViewer;
		
		/*if (stages[currentStage]!=null) {
			state.stage = this.stages[currentStage];
		}*/
		
		
		if (stage.getStageAt(currentStage)!=null) {
		state.stage = this.stage.getStageAt(stage.getCurrentStage());
		}
		state.currentStage = this.stage.getCurrentStage();
		
		state.stages = this.stage.getStage();
		
		state.numPlayers = this.numPlayers;
		
		state.numStages = this.numStages;
		
		state.stagePlaceHolder = this.stagePlaceHolder;

		return state;
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
				&& containsFoe(this.stage.getStageAt(currentStage))) {
			control.alert("Cannot stage more than one foe per quest stage.");
			return;
		}
		if(containsWeapon(this.stage.getStageAt(currentStage), c.getImgName())) {
			control.alert("Cannot stage duplicate weapons.");
			return;
		}
		hand.remove(c);
		
		//Change To add to my new Stages
		this.stage.getStageAt(currentStage).add(c);
		logger.info("Player " + this.currentPlayer + " moves " + c.getName() + " from hand to Stage " + currentStage);
		
		
	}	
	
	public void unstage(String iD) {
		logger.debug("unstage() called");

		
		Card c = this.stage.getStageAt(currentStage).getByID(iD);
		
		this.stage.getStageAt(currentStage).remove(c);
		
		this.players[this.currentPlayer].getHand().add(c);
		

		logger.info("Player " + this.currentPlayer + " moves " + c.getName() + " from Stage back to Hand");

	}
	
	public Player getActivePlayer(){
		logger.debug("getActivePlayer() called");

		
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
		
		
		this.currentStage= num;
		this.stage.setCurrentStage(num);
	
		control.updateViewState();
	}
	

	

	
	
	public void endTurn() {
		logger.debug("endTurn() called");
		logger.info("I end turn called changing s ");
		
		
		
		currentState.nextPlayer();
		
	

	
		
	}
	
	
	
	
	public int resolveQuest(){
		//Left it here because of  one of the event cards 
		
		/**
		 * To resolve a Quest, we need to count the following data structures:
		 *    - players Queue
		 *    - players Party
		 *    - players Rank
		 *    - get a card if they pass
		 */
		logger.info("resolveQuest() called");

		int numStages = this.state.numStages;

		
		if(inNextQ) {
			
			for (int i = 0; i < this.state.players[i].getQueue().size(); i++) {
				this.players[i].addShields(2);
			}
			inNextQ = false;
			}

		int numShields = ((QuestCard) state.currentStoryCard).getNumStages();
		logger.info("Number of Stages: " + numShields);

		//TODO ADD THE BOOLEAN SETTING FOR PASSING QUEST HERE
		for (int i = 0; i < state.numPlayers; ++i){
			if(!players[i].isSponsor){

				

				if(players[i].passedQuest) {
					players[i].addShields(numShields);
					for(int j=0;j<numStages;j++) {
						Card c = this.adventureDeck.pop();
						this.players[i].addToHand(c);
						adventureDeckDiscard.add(c);
					}
				}

			} else {
				//TODO GIVE SPONSOR CARDS BACK 
				
				
			}
		}
		
		//TODO ADD SHIELDS HERE
		
		control.resolveQuest();
		
		return 0;
	}
	

		
	public void stageOver(){
		logger.info("stageOver() called");

		for(int i = 0; i < this.numPlayers; ++i){
			if(!this.players[i].isSponsor){
				
				int size = this.players[i].getQueue().size();
				
				for(int j = 0; j < size; ++j){
					adventureDeckDiscard.add(this.players[i].getQueue().pop());
				}
				players[i].passedStage = false;
			}
		}
	
		//this.currentViewer--;// TODO ??? MAYBE A REALLY BAD FIX MAYBE NOT, WHO KNOWS ANYMORE...
	


		
		state.stage = this.stage.getStageAt(currentStage);
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
		logger.info("playQuest() called");
		
		currentState = questManger;	

	}
	
	private void playEvent() {
		logger.debug("playEvent() called");
		
		currentState = eventManger;

	
	}
	
	public void playGame() {
		logger.info("playGame() called");

		if (((StoryCard) currentStoryCard).getSubType().equals(StoryCard.QUEST)){
			playQuest();
			currentState.handle();
		} else if (((StoryCard) currentStoryCard).getSubType().equals(StoryCard.EVENT)){
			playEvent();
			currentState.handle();
		} else if (((StoryCard) currentStoryCard).getSubType().equals(StoryCard.TOURNAMENT)){
//			playTournament();
		} else {
			adventureDeck = adventureDeckDiscard;
			adventureDeck.shuffle();
		}
	
	}
	
	public void nextPlayer(){
		logger.info("nextPlayer() called");
		
		if(this.currentPlayer == numPlayers - 1){
			this.currentPlayer = 0;

		}
		else{
			this.currentPlayer++;
			this.currentSponsor = this.currentPlayer;
		}
		logger.info("Player changed to " + this.currentPlayer);
		
		//control.nextPlayer();
		control.view.update();
		
	}
	public void setNextPlayer(int nextplayer) {
		
		currentPlayer = nextplayer;
		
		control.view.update();
		
	}
	public void nextStory() {
		logger.info("nextStory() called");
		//get ready for the next person
		for(int i = 0; i < numPlayers; ++i){
			
			players[i].isSponsor = false;
			players[i].isQuesting = false;
			players[i].passedQuest = false;
			players[i].passedStage = false;
			
			
			//remove stage cards
			instantiateStages(); //TODO - DO PROPERLY
			
			//remove amours
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
		logger.info("Story card up next "+ currentStoryCard.getName());

		this.currentStage = 0;
		stage.resetCurrentStage();
		
		this.currentSponsor = -1;
		

		

		
		this.stagePlaceHolder = 0;
		
		
		this.currentViewer = this.currentPlayer;
		control.updateViewState();
		playGame();
		currentState.handle();
	}
	
	
	public void setScenario1() {
		logger.debug("setScenario1() called - Setting up SCENARIO ONE");

		/**
		 * Scenario 1
		 * Story Card: 			Board Hunt
		 * Number of Players: 	2
		 * Current Player: 		Player 1
		 * Rules that need to be implimented:
			//player1	gets	12	cards	including	saxons,	boar	and	sword
			//players	2,	3,	and	4	get	12	cards	(with	some	specific	ones	as	seen	below)
			//first	story	card	is	Boar	Hunt
			//player1	sponsors
			//player1	sets	up	stage	1:	saxons	(worth	10	not	20)	stage	2:	boar	+	dagger	+	sword	(worth	15+5+10)
			//other	3	players	accept	and	get	an	A	card:	must	discard	to	stay	at	12	cards	in	their	hands
			//player	2	plays	nothing,	player	3	plays	a	horse;	player	4	plays	an	axe	(ie	battle-ax)
			//player	2	is	eliminated,	players	3	and	4	continue	to	stage	2
			//players	3	and	4	get	an	A	card	(their	12th),	player3	plays	excalibur;	Player4	plays	a	lance
			//player	3	wins	and	gets	2	Shields,	player	4	does	not	get	shields
			//player	1	discards	all	4	cards	of	the	quest,	gets	6	new	cards,then	discards	to	get	back	to	12.
			//second	story	card	is	Prosperity
			//all	players	draw	2	cards	and	must	discard	correctly.	In	particular:
			//						player2	discards	a	weapon,	player	3	plays	amour,	player	4	discards	a	foe
			//third	story	card	is	Chivalrous	deed
			//all	players	BUT	p3	get	3	shields
		 */
//		ID: 74, type: Adventure, subtype: Foe, name: SaxonKnight, battle points: 15, alternative battle points: 25, special: <NO SPECIAL>
		this.currentPlayer = 0;
		this.currentStoryCard = this.storyDeck.getByID("126"); //BOAR  hUNT
		Card c = this.getStoryDeck().pop();
		storyDeckDiscard.add(c);
//		ID: 58, type: Adventure, subtype: Foe, name: Boar, battle points: 5, alternative battle points: 15, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("58");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 1, type: Adventure, subtype: Weapon, name: Sword, battle points: 10
		c = this.adventureDeck.getByID("1");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 93, type: Adventure, subtype: Foe, name: Mordred, battle points: 30, alternative battle points: 30, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("93");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 100, type: Adventure, subtype: Ally, name: SirGalahad, battle points: 15, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("100");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 110, type: Adventure, subtype: Test, name: TestOfValor
		c = this.adventureDeck.getByID("110");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 119, type: Adventure, subtype: Amour, battle points: 10, special: <Bid Special: adds 1 extra bid(s)>
		c = this.adventureDeck.getByID("119");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 51, type: Adventure, subtype: Foe, name: Thieves, battle points: 5, alternative battle points: 5, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("51");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);

//		ID: 65, type: Adventure, subtype: Foe, name: Saxons, battle points: 10, alternative battle points: 20, special: <NO SPECIAL>

		c = this.adventureDeck.getByID("65");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 67, type: Adventure, subtype: Foe, name: RobberKnight, battle points: 15, alternative battle points: 15, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("67");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 41, type: Adventure, subtype: Weapon, name: BattleAx, battle points: 15
		c = this.adventureDeck.getByID("41");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 10, type: Adventure, subtype: Weapon, name: Sword, battle points: 10
		c = this.adventureDeck.getByID("10");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 17, type: Adventure, subtype: Weapon, name: Dagger, battle points: 5
		c = this.adventureDeck.getByID("17");
		this.players[0].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 2, type: Adventure, subtype: Weapon, name: Sword, battle points: 10
		c = this.adventureDeck.getByID("2");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 62, type: Adventure, subtype: Foe, name: Saxons, battle points: 10, alternative battle points: 20, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("62");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 50, type: Adventure, subtype: Foe, name: Thieves, battle points: 5, alternative battle points: 5, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("50");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 94, type: Adventure, subtype: Foe, name: Mordred, battle points: 30, alternative battle points: 30, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("94");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 52, type: Adventure, subtype: Foe, name: Thieves, battle points: 5, alternative battle points: 5, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("52");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 101, type: Adventure, subtype: Ally, name: SirLancelot, battle points: 15, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("101");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 120, type: Adventure, subtype: Amour, battle points: 10, special: <Bid Special: adds 1 extra bid(s)>
		c = this.adventureDeck.getByID("120");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 111, type: Adventure, subtype: Test, name: TestOfValor
		c = this.adventureDeck.getByID("111");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 47, type: Adventure, subtype: Weapon, name: Lance, battle points: 20
		c = this.adventureDeck.getByID("47");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 24, type: Adventure, subtype: Weapon, name: Horse, battle points: 10
		c = this.adventureDeck.getByID("24");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 33, type: Adventure, subtype: Weapon, name: Horse, battle points: 10
		c = this.adventureDeck.getByID("33");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 9, type: Adventure, subtype: Weapon, name: Sword, battle points: 10
		c = this.adventureDeck.getByID("9");
		this.players[1].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 23, type: Adventure, subtype: Weapon, name: Horse, battle points: 10
		c = this.adventureDeck.getByID("23");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 48, type: Adventure, subtype: Weapon, name: Excalibur, battle points: 30
		c = this.adventureDeck.getByID("48");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 118, type: Adventure, subtype: Amour, battle points: 10, special: <Bid Special: adds 1 extra bid(s)>	
		c = this.adventureDeck.getByID("118");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 95, type: Adventure, subtype: Foe, name: Mordred, battle points: 30, alternative battle points: 30, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("95");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 102, type: Adventure, subtype: Ally, name: KingArthur, battle points: 10, special: <Bid Special: adds 4 extra bid(s)>
		c = this.adventureDeck.getByID("102");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 103, type: Adventure, subtype: Ally, name: SirTristan, battle points: 10, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("103");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 112, type: Adventure, subtype: Test, name: TestOfTemptation
		c = this.adventureDeck.getByID("112");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 68, type: Adventure, subtype: Foe, name: RobberKnight, battle points: 15, alternative battle points: 15, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("68");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 40, type: Adventure, subtype: Weapon, name: BattleAx, battle points: 15
		c = this.adventureDeck.getByID("40");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 28, type: Adventure, subtype: Weapon, name: Horse, battle points: 10
		c = this.adventureDeck.getByID("28");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 43, type: Adventure, subtype: Weapon, name: Lance, battle points: 20
		c = this.adventureDeck.getByID("43");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 53, type: Adventure, subtype: Foe, name: Thieves, battle points: 5, alternative battle points: 5, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("53");
		this.players[2].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 34, type: Adventure, subtype: Weapon, name: BattleAx, battle points: 15
		c = this.adventureDeck.getByID("34");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 42, type: Adventure, subtype: Weapon, name: Lance, battle points: 20
		c = this.adventureDeck.getByID("42");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 59, type: Adventure, subtype: Foe, name: Boar, battle points: 5, alternative battle points: 15, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("59");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 96, type: Adventure, subtype: Foe, name: Mordred, battle points: 30, alternative battle points: 30, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("96");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 61, type: Adventure, subtype: Foe, name: Boar, battle points: 5, alternative battle points: 15, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("61");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 78, type: Adventure, subtype: Foe, name: SaxonKnight, battle points: 15, alternative battle points: 25, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("78");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 49, type: Adventure, subtype: Weapon, name: Excalibur, battle points: 30
		c = this.adventureDeck.getByID("49");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 108, type: Adventure, subtype: Ally, name: QueenIseult, battle points: 0, special: <Bid Special: adds 2 extra bid(s)>
		c = this.adventureDeck.getByID("108");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 106, type: Adventure, subtype: Ally, name: SirPercival, battle points: 5, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("106");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 113, type: Adventure, subtype: Test, name: TestOfTemptation
		c = this.adventureDeck.getByID("113");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 99, type: Adventure, subtype: Foe, name: Dragon, battle points: 50, alternative battle points: 70, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("99");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
//		ID: 16, type: Adventure, subtype: Weapon, name: Sword, battle points: 10
		c = this.adventureDeck.getByID("16");
		this.players[3].addToHand(c);
		adventureDeckDiscard.add(c);
		this.adventureDeck.remove(c);
		
	
		
		this.adventureDeck.shuffle();
		
		
	} //end set scenario 1
	
	
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
		
		//stages[0].add(this.adventureDeck.getByID("57"));
		//stages[1].add(this.adventureDeck.getByID("86"));

		this.currentStoryCard = this.storyDeck.getByID("126"); //BOAR  hUNT 		
		this.players[0].addToParty(this.adventureDeck.getByID("100"));
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
	
	public void eventTesting() {
		logger.info("seve");


		this.currentPlayer = 0;

		this.players[0].addShields(10);
		this.players[1].addShields(6);
		this.players[2].addShields(14);
		this.players[3].addShields(10);
		
		//stages[0].add(this.adventureDeck.getByID("57"));
		//stages[1].add(this.adventureDeck.getByID("86"));

		this.currentStoryCard = this.storyDeck.getByID("151");
		this.players[0].addToParty(this.adventureDeck.getByID("100"));
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


