package core;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import core.QuestingStage;

public abstract class AbstractModel {
	protected static final Logger logger = LogManager.getLogger(AbstractModel.class);

	protected State state;

	protected Player[] players;

	public Player[] getPlayers() {
		
		return players;
	}

	protected AdventureDeck adventureDeck;

	public AdventureDeck getAdventureDeck() {
		return this.adventureDeck;
	}

	protected CardCollection<AdventureCard> adventureDeckDiscard;

	public CardCollection<AdventureCard> getAdventureDeckDiscard() {
		return this.adventureDeck;
	}

	protected StoryDeck storyDeck;

	public StoryDeck getStoryDeck() {
		return storyDeck;
	}

	protected CardCollection<StoryCard> storyDeckDiscard;

	public CardCollection<StoryCard> getStoryDeckDiscard() {
		return storyDeckDiscard;
	}

	protected boolean inNextQ = false;

	protected int currentViewer;
	protected int currentPlayer = 0;
	protected int currentStage;
	private int currentSponsor;
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

	private StoryCard currentStoryCard;

	protected int numPlayers;
	protected int numStages;

	protected ArrayList<CardCollection<AdventureCard>> stages;

	public ArrayList<CardCollection<AdventureCard>> getStages() {
		return stages;
	}

	protected QuestingStage stage;

	protected StoryCardState questManager;
	protected StoryCardState eventManager;
	protected core.StoryCardState currentState;
	private boolean isDoneQuestingMode = false;


	public Player getActivePlayer() {

		return players[this.currentPlayer];
	}

	
	public void instantiatePlayers(int numPlayers) {
		logger.debug("instantiatePlayers(" + numPlayers + ") called");
		players = new Player[numPlayers];
		
		this.numPlayers = numPlayers;
		for (int i = 0; i < numPlayers; ++i) {
			players[i] = new Player(i);
		}
	}

	
	public  StoryCardState getCurrentState() {
		return this.currentState;
	}
	public void instantiateStages() {
		logger.debug("instantiateStages() called - hard coded to 5");

		setStage(new QuestingStage());
	}

	public void initialShuffle() {
		logger.debug("initialShuffle() called");
		this.adventureDeck.shuffle();
		this.storyDeck.shuffle();
	}

	public void deal() {
		logger.info("deal() called");
		// 12 cards in hand
		for (int i = 0; i < 12; ++i) {
			for (int j = 0; j < players.length; ++j) {

				players[j].addToHand(this.adventureDeck.pop());
			}
			this.setCurrentStoryCard(storyDeck.pop());
			logger.info("setting current story card to" + this.getCurrentStoryCard());
		}
	}

	public void CardsTest() {
		logger.debug("CardsTest() called");
		System.out.println("Adventure Deck: \n" + this.adventureDeck.toString());
		System.out.println("Story Deck: \n" + this.storyDeck.toString());
		System.out.println("Players Hands: \n\n");
		for (int i = 0; i < players.length; ++i) {
			System.out.println("Player " + (i + 1) + " Hand: \n" + players[i].getHand().toString());
		}
	}

	public void resetCurrentStage() {
		logger.debug("resetCurrentStage() called");

		getStage().resetCurrentStage();
	}

	public State getState() {
		logger.debug("getState() called");

		this.state.players = this.players;

		state.currentPlayer = this.currentPlayer;

		state.isQuesting = this.players[currentPlayer].isQuesting;

		state.currentSponsor = this.getCurrentSponsor();

		state.inNextQ = this.inNextQ;

		state.currentStoryCard = this.getCurrentStoryCard();

		state.currentViewer = this.currentViewer;

		if (getStage().getStageAt(getStage().getCurrentStage()) != null) {
			state.stage = this.getStage().getStageAt(getStage().getCurrentStage());
		}

		state.currentStage = this.getStage().getCurrentStage();

		state.stages = this.getStage().getStage();

		state.numPlayers = this.numPlayers;

		state.numStages = this.numStages;

		

		return state;
	}
	
	public void draw(String iD, int currentPlayer) {
		logger.info("draw() called");
		AdventureCard c;
		c = this.adventureDeck.getByID(iD);
		Player p = this.players[currentPlayer];
		p.addToHand(c);
		this.adventureDeck.remove(c);
		logger.debug("Player " + currentPlayer + " drew " + c.getName() + " from AdventureDeck to hand");
	}
	
	public void removeFromParty(String iD, int currentPlayer) {
		logger.info("removeFromParty() called");
		AdventureCard c;
		c = this.players[currentPlayer].getParty().getByID(iD);
		getAdventureDeckDiscard().add(c);
		players[currentPlayer].discardFromParty(c);
		logger.debug("Player " + currentPlayer + " drew " + c.getName() + " from AdventureDeck to hand");
	}
	
	public void removeShields(int numShields, int currentPlayer) {
		players[currentPlayer].removeShields(numShields);
	}

	public void party(String iD, int currentPlayer) {
		logger.debug("party() called");

		CardCollection<AdventureCard> hand = players[currentPlayer].getHand();
		AdventureCard c = hand.getByID(iD);

		hand.remove(c);
		players[currentPlayer].addToParty(c);
		logger.debug("Player " + currentPlayer + " moved " + c.getName() + " from hand to party");

	}

	public boolean stage(String iD, int currentPlayer, int stageNumber) {
		logger.debug("stage() called");

		CardCollection<AdventureCard> hand = this.players[currentPlayer].getHand();
		AdventureCard c = hand.getByID(iD);
		
		hand.remove(c);

		// Change To add to my new Stages
		this.getStage().getStageAt(stageNumber).add(c);
		
		logger.info("Player " + currentPlayer + " moves " + c.getName() + " from hand to Stage " + stageNumber);
		return true;
	}

	public void unstage(String iD, int currentPlayer, int stageNumber) {
		logger.debug("unstage() called");

		AdventureCard c = this.getStage().getStageAt(stageNumber).getByID(iD);

		this.getStage().getStageAt(stageNumber).remove(iD);
		

		this.players[currentPlayer].getHand().add(c);

		logger.info("Player " + currentPlayer + " moves " + c.getName() + " from Stage back to Hand");
	}

	public void discard(String iD, int currentPlayer) {
		logger.debug("discard() called");
		CardCollection<AdventureCard> hand = players[currentPlayer].getHand();
		AdventureCard c = hand.getByID(iD);
		hand.remove(c);
		adventureDeckDiscard.add(c);
		logger.info("Player " + currentPlayer + " discarded " + c.getName());
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
			
		}
			
		
	}


	public void queue(String id, int currentPlayer) {
		logger.debug("queue() called");
		System.out.println("Current player, in queue called" + currentPlayer);

		CardCollection<AdventureCard> hand = new CardCollection<AdventureCard>();
		hand = players[currentPlayer].getHand();
		AdventureCard c = hand.getByID(id);

		hand.remove(c);
		players[currentPlayer].addToQueue(c);
		logger.info("Player " + this.currentPlayer + " moved " + c.getName() + " from hand to queue");
	}

	public void dequeue(String iD, int currentPlayer) {
		logger.debug("dequeue(" + iD + ") called");
		CardCollection<AdventureCard> queue = players[currentPlayer].getQueue();
		AdventureCard c = queue.getByID(iD);
		queue.remove(c);
		players[currentPlayer].addToHand(c);
		logger.info("Player " + currentPlayer + " moved " + c.getName() + " from queue to hand");
	}
	
	public boolean containsSameWeapon(CardCollection<AdventureCard> collection, String cardName) {
		logger.debug("containsSameWeapon(" + cardName + ") called");

		for (int i = 0; i < collection.size(); i++) {
			if (((WeaponCard) collection.get(i)).getName().equals(cardName)) {
				return true;
			}
		}

		return false;
	}


	public void setCurrentStage(int num) {
		logger.debug("setCurrentStage(" + num + ") called");
		this.currentStage = num;
		this.getStage().setCurrentStage(num);
		
	}

	public void endTurn() {
		logger.debug("endTurn() called");
		logger.info("I end turn called changing s ");
		currentState.nextPlayer();
	}

	public void stageOver() {
		logger.info("stageOver() called");

		for (int i = 0; i < this.numPlayers; ++i) {
			if (!this.players[i].isSponsor) {

				int size = this.players[i].getQueue().size();

				for (int j = 0; j < size; ++j) {
					adventureDeckDiscard.add(this.players[i].getQueue().pop());
				}
				players[i].passedStage = false;
			}
		}
		state.stage = this.getStage().getStageAt(currentStage);
		
	}
	
	/**
	 * This will go through all the allies in play, and update players
	 * 
	 * steps psuedo code: get an ally that's in play set boolean to T apply bonuses
	 * to Player
	 */
	public void allysInPlay() {
		logger.debug("allysInPlay() called");

		for (int i = 0; i < numPlayers; ++i) {
			CardCollection<AdventureCard> party = players[i].getParty();
			for (int j = 0; j < party.size(); j++) {
				if (party.get(j).getID().equals("SirGalahad") && AllyInPlaySirGalahad == false) {
					AllyInPlaySirGalahad = true;
					logger.info("SirGalahad is in play and gives +15 Battle points to player " + i);
					players[i].AllyBattlePoints += 15;
				}
				if (party.get(j).getID().equals("SirLancelot") && AllyInPlaySirLancelot == false) {
					AllyInPlaySirLancelot = true;
					if (getCurrentStoryCard().getName().equals("DefendTheQueensHonor")) {
						logger.info(
								"SirLancelot is in play and on quest Queens honor so, gives +25 Battle points to player "
										+ i);
						players[i].AllyBattlePoints += 25;
					} else {
						logger.info("SirLancelot is in play and gives +25 Battle points to player " + i);
						players[i].AllyBattlePoints += 15;
					}
				}
				if (party.get(j).getID().equals("KingArthur") && AllyInPlayKingArthur == false) {
					AllyInPlayKingArthur = true;

					logger.info("KingArthur is in play and gives +10 Battle Points, and +2 bids to player " + i);
					players[i].AllyBattlePoints += 10;
					players[i].AllyBidBonus += 2;
				}
				if (party.get(j).getID().equals("SirTristan") && AllyInPlaySirTristan == false) {
					AllyInPlaySirTristan = true;

					if (AllyInPlayQueenIseult) {
						logger.info(
								"SirTristan and Queen Iseult are in play and gives +20 Battle Points to player " + i);
						players[i].AllyBattlePoints += 20;
					} else {
						logger.info("SirTristan is in play and gives +10 Battle Points to player " + i);
						players[i].AllyBattlePoints += 10;
					}
				}
				if (party.get(j).getID().equals("KingPellinore") && AllyInPlayKingPellinore == false) {
					AllyInPlayKingPellinore = true;

					if (getCurrentStoryCard().getName().equals("SearchForTheQuestingBeast")) {
						logger.info(
								"KingPellinore is in play on Questing Beast and gives +10 Battle Points, +4 Bids to player "
										+ i);
						players[i].AllyBattlePoints += 10;
						players[i].AllyBidBonus += 4;
					} else {
						logger.info("KingPellinore is in play and gives +10 Battle Points to player " + i);
						players[i].AllyBattlePoints += 10;
					}
				}
				if (party.get(j).getID().equals("SirGawain") && AllyInPlaySirGawain == false) {
					AllyInPlaySirGawain = true;

					if (getCurrentStoryCard().getName().equals("TestOfTheGreenKnight")) {
						logger.info(
								"SirGawain is in play and on TestOfTheGreenKnight and gives +20 Battle Points to player "
										+ i);
						players[i].AllyBattlePoints += 20;
					} else {
						logger.info("SirGawain is in play and gives +10 Battle Points to player " + i);
						players[i].AllyBattlePoints += 10;
					}
				}
				if (party.get(j).getID().equals("SirPercival") && AllyInPlaySirPercival == false) {
					AllyInPlaySirPercival = true;

					if (getCurrentStoryCard().getName().equals("TestOfTheGreenKnight")) {
						logger.info(
								"SirGawain is in play and on SearchForTheHolyGrail and gives +20 Battle Points to player "
										+ i);
						players[i].AllyBattlePoints += 20;
					} else {
						logger.info("SirGawain is in play and gives +5 Battle Points to player " + i);
						players[i].AllyBattlePoints += 5;
					}
				}
				if (party.get(j).getID().equals("QueenGuinevere") && AllyInPlayQueenGuinevere == false) {
					AllyInPlayQueenGuinevere = true;
					logger.info("QueenGuinevere is in play and gives +3 Bids to player " + i);
					players[i].AllyBidBonus += 3;
				}
				if (party.get(j).getID().equals("QueenIseult") && AllyInPlayQueenIseult == false) {
					AllyInPlayQueenIseult = true;
					if (AllyInPlaySirTristan) {
						logger.info(
								"AllyInPlayQueenIseult is in play and SirTristan is in play and gives 4 Bids to player "
										+ i);
						players[i].AllyBidBonus += 4;
					} else {
						logger.info("AllyInPlayQueenIseult is in play and gives 2 Bids to player " + i);
						players[i].AllyBidBonus += 2;
					}
				}
				if (party.get(j).getID().equals("Merlin") && AllyInPlayMerlin == false) {
					AllyInPlayMerlin = true;
					logger.info("Merlin is in play and gives magical powers to player " + i);
				}
			}
		}
	}

	public boolean containsFoe(CardCollection<AdventureCard> collection) {
		logger.debug("containsFoe() called");

		for (int i = 0; i < collection.size(); i++) {
			if (collection.get(i).getSubType().equals(AdventureCard.FOE)) {
				return true;
			}
		}

		return false;
	}

	public boolean containsAmour(CardCollection<AdventureCard> collection) {
		logger.debug("containsAmour() called");

		for (int i = 0; i < collection.size(); i++) {
			if (collection.get(i).getSubType().equals(AdventureCard.AMOUR)) {
				return true;
			}
		}

		return false;
	}

	public boolean containsWeapon(CardCollection<AdventureCard> collection, String cardName) {
		logger.debug("containsWeapon() called");

		for (int i = 0; i < collection.size(); i++) {
			if (collection.get(i).getName().equals(cardName)) {
				return true;
			}
		}
		return false;
	}

	public String getSubType(String ID, int currentPlayer) {
		logger.debug("getSubType() called");
		String result = "";

		if ( players[currentPlayer].getHand().getByID(ID) != null) {
			result = players[currentPlayer].getHand().getByID(ID).getSubType();
		}
		return result;
	}

	protected void playQuest() {
		logger.info("playQuest() called");

		currentState = questManager;
	}

	protected void playEvent() {
		logger.debug("playEvent() called");

		currentState = eventManager;
	}

	public void playGame() {
//		logger.info("playGame() called");
//
//		if (((StoryCard) currentStoryCard).getSubType().equals(StoryCard.QUEST)) {
//			playQuest();
//			currentState.handle();
//		} else if (((StoryCard) currentStoryCard).getSubType().equals(StoryCard.EVENT)) {
//			playEvent();
//			currentState.handle();
//		} else if (((StoryCard) currentStoryCard).getSubType().equals(StoryCard.TOURNAMENT)) {
//			// playTournament();
//		} else {
//			adventureDeck = (AdventureDeck) adventureDeckDiscard;
//			adventureDeck.shuffle();
//		}
	}

	public void nextPlayer() {
		logger.info("nextPlayer() called");

		if (this.currentPlayer == numPlayers - 1) {
			this.currentPlayer = 0;
		} else {
			this.currentPlayer++;
			this.setCurrentSponsor(this.currentPlayer);
		}
		logger.info("Player changed to " + this.currentPlayer);

	}

	public void setNextPlayer(int nextplayer) {

		currentPlayer = nextplayer;

	

	}

	public void nextStory() {
		logger.info("nextStory() called");
		// get ready for the next person

		this.setDoneQuestingMode(false);
		
		for (int i = 0; i < numPlayers; ++i) {

			players[i].isSponsor = false;
			players[i].isQuesting = false;
			players[i].passedQuest = false;
			players[i].passedStage = false;

			// remove stage cards
			instantiateStages(); 

			// remove amours
			CardCollection<AdventureCard> queue = players[i].getQueue();
			for (int j = 0; j < queue.size(); ++j) {
				if (queue.get(j).getSubType().equals(AdventureCard.AMOUR)) {
					AdventureCard c = queue.get(j);
					queue.remove(j);
					adventureDeckDiscard.add(c);
				}
			}
		}

		storyDeckDiscard.add(this.getCurrentStoryCard());

		this.setCurrentStoryCard(storyDeck.pop());
		logger.info("Story card up next " + getCurrentStoryCard().getName());

		this.currentStage = 0;
		getStage().resetCurrentStage();

		
		
		playGame();
		currentState.handle();
	}

	public void setScenario1() {
		logger.debug("setScenario1() called - Setting up SCENARIO ONE");

		/**
		 * Scenario 1 Story Card: Board Hunt Number of Players: 2 Current Player: Player
		 * 1 Rules that need to be implimented: //player1 gets 12 cards including
		 * saxons, boar and sword //players 2, 3, and 4 get 12 cards (with some specific
		 * ones as seen below) //first story card is Boar Hunt //player1 sponsors
		 * //player1 sets up stage 1: saxons (worth 10 not 20) stage 2: boar + dagger +
		 * sword (worth 15+5+10) //other 3 players accept and get an A card: must
		 * discard to stay at 12 cards in their hands //player 2 plays nothing, player 3
		 * plays a horse; player 4 plays an axe (ie battle-ax) //player 2 is eliminated,
		 * players 3 and 4 continue to stage 2 //players 3 and 4 get an A card (their
		 * 12th), player3 plays excalibur; Player4 plays a lance //player 3 wins and
		 * gets 2 Shields, player 4 does not get shields //player 1 discards all 4 cards
		 * of the quest, gets 6 new cards,then discards to get back to 12. //second
		 * story card is Prosperity //all players draw 2 cards and must discard
		 * correctly. In particular: // player2 discards a weapon, player 3 plays amour,
		 * player 4 discards a foe //third story card is Chivalrous deed //all players
		 * BUT p3 get 3 shields
		 */
		// ID: 74, type: Adventure, subtype: Foe, name: SaxonKnight, battle points: 15,
		// alternative battle points: 25, special: <NO SPECIAL>
		this.currentPlayer = 0;
		this.setCurrentStoryCard(this.storyDeck.getByID("126")); // BOAR hUNT
		StoryCard sC = this.getStoryDeck().pop();
		storyDeckDiscard.add(sC);
		// ID: 58, type: Adventure, subtype: Foe, name: Boar, battle points: 5,
		// alternative battle points: 15, special: <NO SPECIAL>
		AdventureCard c;
		c = this.adventureDeck.getByID("58");
		this.players[0].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 1, type: Adventure, subtype: Weapon, name: Sword, battle points: 10
		c = this.adventureDeck.getByID("1");
		this.players[0].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 93, type: Adventure, subtype: Foe, name: Mordred, battle points: 30,
		// alternative battle points: 30, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("93");
		this.players[0].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 100, type: Adventure, subtype: Ally, name: SirGalahad, battle points: 15,
		// special: <NO SPECIAL>
		c = this.adventureDeck.getByID("100");
		this.players[0].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 110, type: Adventure, subtype: Test, name: TestOfValor
		c = this.adventureDeck.getByID("110");
		this.players[0].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 119, type: Adventure, subtype: Amour, battle points: 10, special: <Bid
		// Special: adds 1 extra bid(s)>
		c = this.adventureDeck.getByID("119");
		this.players[0].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 51, type: Adventure, subtype: Foe, name: Thieves, battle points: 5,
		// alternative battle points: 5, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("51");
		this.players[0].addToHand(c);
		this.adventureDeck.remove(c);

		// ID: 65, type: Adventure, subtype: Foe, name: Saxons, battle points: 10,
		// alternative battle points: 20, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("65");
		this.players[0].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 67, type: Adventure, subtype: Foe, name: RobberKnight, battle points: 15,
		// alternative battle points: 15, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("67");
		this.players[0].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 41, type: Adventure, subtype: Weapon, name: BattleAx, battle points: 15
		c = this.adventureDeck.getByID("41");
		this.players[0].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 10, type: Adventure, subtype: Weapon, name: Sword, battle points: 10
		c = this.adventureDeck.getByID("10");
		this.players[0].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 17, type: Adventure, subtype: Weapon, name: Dagger, battle points: 5
		c = this.adventureDeck.getByID("17");
		this.players[0].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 2, type: Adventure, subtype: Weapon, name: Sword, battle points: 10
		c = this.adventureDeck.getByID("2");
		this.players[1].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 62, type: Adventure, subtype: Foe, name: Saxons, battle points: 10,
		// alternative battle points: 20, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("62");
		this.players[1].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 50, type: Adventure, subtype: Foe, name: Thieves, battle points: 5,
		// alternative battle points: 5, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("50");
		this.players[1].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 94, type: Adventure, subtype: Foe, name: Mordred, battle points: 30,
		// alternative battle points: 30, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("94");
		this.players[1].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 52, type: Adventure, subtype: Foe, name: Thieves, battle points: 5,
		// alternative battle points: 5, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("52");
		this.players[1].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 101, type: Adventure, subtype: Ally, name: SirLancelot, battle points:
		// 15, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("101");
		this.players[1].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 120, type: Adventure, subtype: Amour, battle points: 10, special: <Bid
		// Special: adds 1 extra bid(s)>
		c = this.adventureDeck.getByID("120");
		this.players[1].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 111, type: Adventure, subtype: Test, name: TestOfValor
		c = this.adventureDeck.getByID("111");
		this.players[1].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 47, type: Adventure, subtype: Weapon, name: Lance, battle points: 20
		c = this.adventureDeck.getByID("47");
		this.players[1].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 24, type: Adventure, subtype: Weapon, name: Horse, battle points: 10
		c = this.adventureDeck.getByID("24");
		this.players[1].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 33, type: Adventure, subtype: Weapon, name: Horse, battle points: 10
		c = this.adventureDeck.getByID("33");
		this.players[1].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 9, type: Adventure, subtype: Weapon, name: Sword, battle points: 10
		c = this.adventureDeck.getByID("9");
		this.players[1].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 23, type: Adventure, subtype: Weapon, name: Horse, battle points: 10
		c = this.adventureDeck.getByID("23");
		this.players[2].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 48, type: Adventure, subtype: Weapon, name: Excalibur, battle points: 30
		c = this.adventureDeck.getByID("48");
		this.players[2].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 118, type: Adventure, subtype: Amour, battle points: 10, special: <Bid
		// Special: adds 1 extra bid(s)>
		c = this.adventureDeck.getByID("118");
		this.players[2].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 95, type: Adventure, subtype: Foe, name: Mordred, battle points: 30,
		// alternative battle points: 30, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("95");
		this.players[2].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 102, type: Adventure, subtype: Ally, name: KingArthur, battle points: 10,
		// special: <Bid Special: adds 4 extra bid(s)>
		c = this.adventureDeck.getByID("102");
		this.players[2].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 103, type: Adventure, subtype: Ally, name: SirTristan, battle points: 10,
		// special: <NO SPECIAL>
		c = this.adventureDeck.getByID("103");
		this.players[2].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 112, type: Adventure, subtype: Test, name: TestOfTemptation
		c = this.adventureDeck.getByID("112");
		this.players[2].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 68, type: Adventure, subtype: Foe, name: RobberKnight, battle points: 15,
		// alternative battle points: 15, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("68");
		this.players[2].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 40, type: Adventure, subtype: Weapon, name: BattleAx, battle points: 15
		c = this.adventureDeck.getByID("40");
		this.players[2].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 28, type: Adventure, subtype: Weapon, name: Horse, battle points: 10
		c = this.adventureDeck.getByID("28");
		this.players[2].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 43, type: Adventure, subtype: Weapon, name: Lance, battle points: 20
		c = this.adventureDeck.getByID("43");
		this.players[2].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 53, type: Adventure, subtype: Foe, name: Thieves, battle points: 5,
		// alternative battle points: 5, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("53");
		this.players[2].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 34, type: Adventure, subtype: Weapon, name: BattleAx, battle points: 15
		c = this.adventureDeck.getByID("34");
		this.players[3].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 42, type: Adventure, subtype: Weapon, name: Lance, battle points: 20
		c = this.adventureDeck.getByID("42");
		this.players[3].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 59, type: Adventure, subtype: Foe, name: Boar, battle points: 5,
		// alternative battle points: 15, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("59");
		this.players[3].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 96, type: Adventure, subtype: Foe, name: Mordred, battle points: 30,
		// alternative battle points: 30, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("96");
		this.players[3].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 61, type: Adventure, subtype: Foe, name: Boar, battle points: 5,
		// alternative battle points: 15, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("61");
		this.players[3].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 78, type: Adventure, subtype: Foe, name: SaxonKnight, battle points: 15,
		// alternative battle points: 25, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("78");
		this.players[3].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 49, type: Adventure, subtype: Weapon, name: Excalibur, battle points: 30
		c = this.adventureDeck.getByID("49");
		this.players[3].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 108, type: Adventure, subtype: Ally, name: QueenIseult, battle points: 0,
		// special: <Bid Special: adds 2 extra bid(s)>
		c = this.adventureDeck.getByID("108");
		this.players[3].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 106, type: Adventure, subtype: Ally, name: SirPercival, battle points: 5,
		// special: <NO SPECIAL>
		c = this.adventureDeck.getByID("106");
		this.players[3].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 113, type: Adventure, subtype: Test, name: TestOfTemptation
		c = this.adventureDeck.getByID("113");
		this.players[3].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 99, type: Adventure, subtype: Foe, name: Dragon, battle points: 50,
		// alternative battle points: 70, special: <NO SPECIAL>
		c = this.adventureDeck.getByID("99");
		this.players[3].addToHand(c);
		this.adventureDeck.remove(c);
		// ID: 16, type: Adventure, subtype: Weapon, name: Sword, battle points: 10
		c = this.adventureDeck.getByID("16");
		this.players[3].addToHand(c);
		this.adventureDeck.remove(c);

	} // end set scenario 1

	public void setScenario2() {
		logger.debug("setScenario2() called - Setting up SCENARIO TWO");

		this.currentPlayer = 0;
		this.setCurrentStoryCard(this.storyDeck.getByID("138"));
		this.players[0].addToHand(this.adventureDeck.getByID("50")); // thief
		this.players[0].addToHand(this.adventureDeck.getByID("91")); // green knight
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
		this.players[1].addToHand(this.adventureDeck.getByID("82")); // evil knight
		this.players[1].addToHand(this.adventureDeck.getByID("74")); // Saxon knight
		this.players[1].addToHand(this.adventureDeck.getByID("51")); // thief 1
		this.players[1].addToHand(this.adventureDeck.getByID("52"));// thief 2
		this.players[1].addToHand(this.adventureDeck.getByID("53")); // thief 3
		this.players[1].addToHand(this.adventureDeck.getByID("67")); // robber knight
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

		this.players[0].addShields(3);
		this.players[1].addShields(4);
		this.players[2].addShields(4);

		// stages[0].add(this.adventureDeck.getByID("57"));
		// stages[1].add(this.adventureDeck.getByID("86"));

//		this.setCurrentStoryCard(this.storyDeck.getByID("151")); // ChivalrousDeed
//		this.setCurrentStoryCard(this.storyDeck.getByID("152")); // ProsparityTHroughTheLand
//		this.setCurrentStoryCard(this.storyDeck.getByID("145")); // QueensFavor
//		this.setCurrentStoryCard(this.storyDeck.getByID("147")); // CourtCalled
//		this.setCurrentStoryCard(this.storyDeck.getByID("149")); // Pox
		this.setCurrentStoryCard(this.storyDeck.getByID("150")); // Plague

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
		// this.players[0].addToHand(this.adventureDeck.getByID("92")); //13th card for
		// hand!
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
		logger.info("eventTesting()");

		this.currentPlayer = 0;

		this.players[0].addShields(10);
		this.players[1].addShields(6);
		this.players[2].addShields(14);
		this.players[3].addShields(10);

		// stages[0].add(this.adventureDeck.getByID("57"));
		// stages[1].add(this.adventureDeck.getByID("86"));

		this.setCurrentStoryCard(this.storyDeck.getByID("151"));
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
		// this.players[0].addToHand(this.adventureDeck.getByID("92")); //13th card for
		// hand!
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


	public boolean isDoneQuestingMode() {
		return isDoneQuestingMode;
	}


	public void setDoneQuestingMode(boolean isDoneQuestingMode) {
		this.isDoneQuestingMode = isDoneQuestingMode;
	}


	public QuestingStage getStage() {
		return stage;
	}


	public void setStage(QuestingStage stage) {
		this.stage = stage;
	}


	public int getCurrentSponsor() {
		return currentSponsor;
	}


	public void setCurrentSponsor(int currentSponsor) {
		this.currentSponsor = currentSponsor;
	}


	public StoryCard getCurrentStoryCard() {
		return currentStoryCard;
	}


	public void setCurrentStoryCard(StoryCard currentStoryCard) {
		this.currentStoryCard = currentStoryCard;
	}

	

}
