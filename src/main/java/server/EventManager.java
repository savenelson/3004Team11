package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import server.ServerModel;
import core.AbstractModel;
import core.AdventureCard;
import core.AdventureDeck;
import core.Card;
import core.CardCollection;
import core.Player;
import core.StoryCardState;

// class to handle event cards


public class EventManager implements  StoryCardState {
	private static final Logger logger = LogManager.getLogger(EventManager.class);
	Player[] players;
	int numPlayers;
	AdventureDeck adventureDeck;
	CardCollection<AdventureCard> adventureDeckDiscard;
	boolean nextQ;
	int currentPlayer;
	ServerModel serverModel;

	public EventManager(AbstractModel model) {
		/*
		 * his.serverModel = clientModel;
		this.players = clientModel.getPlayers();
		this.numPlayers = clientModel.getNumPlayers();
		this.adventureDeck = clientModel.getAdventureDeck();
		this.nextQ = clientModel.inNextQ;
		this.currentPlayer = clientModel.currentPlayer;
		 */
	}
	
	public EventManager(ServerModel serverModel) {
		this.serverModel = serverModel;
		logger.info("THIS serverMODEL" + this.serverModel);

		this.players = serverModel.getPlayers();
		logger.info("THIS PLAYERS" + this.players);
	}
	
	private void setPlayers() {
		this.players = serverModel.getPlayers();
	}
	

	public void handleEvent(String eventName) {
		if (eventName.equals("KingsRecognition")) {
			KingsRecognition();
		} else if (eventName.equals("QueensFavor")) {
			QueenFavor();

		} else if (eventName.equals("Pox")) {
			Pox();
		} else if (eventName.equals("KingsCallToArms")) {
			KingCallToArms();
		} else if (eventName.equals("ProsperityThroughoutTheRealm")) {
			// ProsperityThroughoutTheRealm();

		} else if (eventName.equals("ChivalrousDeed")) {
			ChilvarousDeed();

		} else if (eventName.equals("Plague")) {
			Plague();

		} else if (eventName.equals("CourtCalled")) {
			CourtCalled();
		}

	}

	private void CourtCalled() {
		for (int i = 0; i < this.players.length; i++) {
			CardCollection<AdventureCard> hand = this.players[i].getHand();

			for (int j = 0; j < hand.size(); j++) {

				if (hand.getByID("100") != null) {
					AdventureCard c = hand.getByID("100");
					hand.remove("100");
					adventureDeckDiscard.add((AdventureCard) c);
				} else if (hand.getByID("101") != null) {
					AdventureCard c = hand.getByID("101");
					hand.remove("101");
					adventureDeckDiscard.add((AdventureCard) c);
				} else if (hand.getByID("102") != null) {
					AdventureCard c = hand.getByID("102");
					hand.remove("102");
					adventureDeckDiscard.add(c);
				} else if (hand.getByID("103") != null) {
					AdventureCard c = hand.getByID("103");
					hand.remove("103");
					adventureDeckDiscard.add(c);
				} else if (hand.getByID("104") != null) {
					AdventureCard c = hand.getByID("104");
					hand.remove("104");
					adventureDeckDiscard.add(c);
				} else if (hand.getByID("105") != null) {
					AdventureCard c = hand.getByID("105");
					hand.remove("105");
					adventureDeckDiscard.add(c);
				} else if (hand.getByID("106") != null) {
					AdventureCard c = hand.getByID("106");
					hand.remove("106");
					adventureDeckDiscard.add(c);
				} else if (hand.getByID("107") != null) {
					AdventureCard c = hand.getByID("107");
					hand.remove("107");
					adventureDeckDiscard.add(c);
				} else if (hand.getByID("108") != null) {
					AdventureCard c = hand.getByID("108");
					hand.remove("108");
					adventureDeckDiscard.add(c);
				} else if (hand.getByID("109") != null) {
					AdventureCard c = hand.getByID("109");
					hand.remove("109");
					adventureDeckDiscard.add(c);
				}
			}
		}
	}

	// execute the special in event here
	public void KingsRecognition() {
		logger.info("KingsRecognition is in play and gives the next players to complete a quest 2 shields");
		this.nextQ = true;

	}

	public void QueenFavor() {
		logger.info("QueenFavor is in play and gives player 1 and 2 two adventure cards");
		int squireCount = 0;
		int championCount = 0;
		int championKnightCount = 0;

		for (int i = 0; i < this.players.length; i++) {

			if ((this.players[i].getRank()).getSubType().equals("Squire")) {
				squireCount++;
			}
			if ((this.players[i].getRank()).getSubType().equals("Knight")) {
				championCount++;
			}
			if ((this.players[i].getRank()).getSubType().equals("ChampionKnight")) {
				championKnightCount++;
			}

		}

		if (squireCount != 0) {
			for (int i = 0; i < this.players[i].getQueue().size(); i++) {
				if ((this.players[i].getRank()).getSubType().equals("Squire")) {
					this.players[i].addToHand(this.adventureDeck.pop());
					this.players[i].addToHand(this.adventureDeck.pop());
				}
			}

		} // we then know there is one squire. give him 2 adventure cards

		if (championCount < championKnightCount && (squireCount == 0)) {
			for (int i = 0; i < this.players[i].getQueue().size(); i++) {
				if ((this.players[i].getRank()).getSubType().equals("Champion")) {
					this.players[i].addToHand(this.adventureDeck.getByID("6"));
					this.players[i].addToHand(this.adventureDeck.getByID("7"));
				}
			}
			// there are less champions than championKnights
		}
		if (championKnightCount == numPlayers || squireCount == numPlayers || championCount == numPlayers) {
			for (int i = 0; i < this.players[i].getQueue().size(); i++) {
				this.players[i].addToHand(this.adventureDeck.pop());
				this.players[i].addToHand(this.adventureDeck.pop());
			}
		}

	}

	public void Pox() {
	logger.info("Pox in play and all players except  the next players to complete a quest 2 shields");

		Player currentPlayer = players[this.currentPlayer];
		for (int i = 0; i < this.players.length; i++) {
			System.out.println("Players " + players[i] + "with this many " + players[i].getShieldCount());
			if (currentPlayer.getPlayerNumber() != players[i].getPlayerNumber())
				this.players[i].removeShields(1);
			System.out.println("After Players " + players[i] + "with this many " + players[i].getShieldCount());
		}

	}

	public void KingCallToArms() {
	logger.info("KingCallToArms in play and player 1 placed one weapon in the discard pile");

		// TODO : implement into the model boolean ?
		Player currentplay = players[this.currentPlayer];
		int numOfweapons = currentplay.getHand().getnumberOfWeapons();
		// number of foes i put a number really high to get all of them
		int numOfFoes = currentplay.getHand().getNumberOfFoecardsLessthan(100);
		if (numOfweapons >= 1) {
			System.out.println("Please discard a weapon card");

		} else if (numOfFoes >= 1) {
			System.out.println("Please discard 2 weapond cards ");
		}

	}

	public void ProsperityThroughoutTheRealm() {
		logger.info("Handling ProsperityThroughoutTheRealm event ");

		int nummOfPlayers = serverModel.getPlayers().length;
		for (int i = 0; i < nummOfPlayers; i++) {
			// need to draw from story card
			logger.info(players[i].getPlayerNumber());

			// need to add cards to adventure deck will give erroe
			this.players[i].addToHand(this.adventureDeck.pop());
			this.players[i].addToHand(this.adventureDeck.pop());
		}

	}

	//Players with both lowest rank and least amount of shields, get 3 shields.
	public void ChilvarousDeed() {
	logger.info("ChilvarousDeed event in play");

	
	//get lowest rank
	
	
	
	
		int squireCount = 0;
		int championCount = 0;
		int championKnightCount = 0;

		int lowestShieldSquire = 0;
		int lowestShieldKnight = 0;
		int lowestShieldChamp = 0;
		
		setPlayers();

		for (int i = 0; i < players.length; i++) {

			if ((players[i].getRank()).getSubType().equals("Squire")) {
				squireCount++;
				lowestShieldSquire = players[i].getShieldCount();
				if (lowestShieldSquire > players[i].getShieldCount()) {
					lowestShieldSquire = players[i].getShieldCount();
				}
			}
			if ((players[i].getRank()).getSubType().equals("Knight")) {
				championCount++;
				lowestShieldKnight = players[i].getShieldCount();
				if (lowestShieldKnight > players[i].getShieldCount()) {
					lowestShieldKnight = players[i].getShieldCount();
				}
			}
			if ((players[i].getRank()).getSubType().equals("ChampionKnight")) {
				championKnightCount++;
				lowestShieldChamp = players[i].getShieldCount();
				if (lowestShieldChamp > players[i].getShieldCount()) {
					lowestShieldChamp = players[i].getShieldCount();
				}
			}
		}
		logger.info("P0 ranks is " + (players[0].getRank()).getSubType());
		logger.info("P1 ranks is " + (players[1].getRank()).getSubType());
		logger.info("P2 ranks is " + (players[2].getRank()).getSubType());
		logger.info("P3 ranks is " + (players[3].getRank()).getSubType());

		logger.info("squireCount " + squireCount);
		logger.info("championCount " + championCount);

		logger.info("championKnightCount " + championKnightCount);

		logger.info("lowestShieldSquire " + lowestShieldSquire);

		logger.info("lowestShieldKnight " + lowestShieldKnight);

		logger.info("lowestShieldChamp" + lowestShieldChamp);

		if (squireCount != 0) { // squire is the lowest, so as long as it there are more than 0 squires, we know
			// the lowest rank is that of the squire
			for (int i = 0; i < players.length; i++) {
				if ((players[i].getRank()).getSubType().equals("Squire")) {

					if (players[i].getShieldCount() == lowestShieldSquire) {
						players[i].addShields(3);
					}
				}
			}
		}

		if (championCount <= championKnightCount && (squireCount == 0) && (championCount != 0)) {
			for (int i = 0; i < players.length; i++) {
				if ((players[i].getRank()).getSubType().equals("Champion")) {
					if (players[i].getShieldCount() == lowestShieldKnight) {
						players[i].addShields(3);
					}
				}
			}
		}
		if (championKnightCount == numPlayers) {
			for (int i = 0; i < players.length; i++) {
				if ((players[i].getRank()).getSubType().equals("ChampionKnight")) {
					if (players[i].getShieldCount() == lowestShieldChamp) {
						players[i].addShields(3);
					}
				}
			}
		}
	}

	public void Plague() {
		if (this.players[currentPlayer].getShieldCount() >= 2) {
			this.players[currentPlayer].removeShields(2);
		logger.info("Plague event in play and player " + currentPlayer + " looses two shields");

		}
	}


	public void handle() {
		String eventName = serverModel.getCurrentStoryCard().getName();
		System.out.println(eventName);

		if (eventName.equals("KingsRecognition")) {
			KingsRecognition();
		} else if (eventName.equals("QueensFavor")) {
			QueenFavor();
		} else if (eventName.equals("Pox")) {
			Pox();
		} else if (eventName.equals("KingsCallToArms")) {
			KingCallToArms();
		} else if (eventName.equals("ProsperityThroughoutTheRealm")) {
			ProsperityThroughoutTheRealm();
		} else if (eventName.equals("ChivalrousDeed")) {
			ChilvarousDeed();
		} else if (eventName.equals("Plague")) {
			Plague();
		} else if (eventName.equals("CourtCalled")) {
			CourtCalled();
		}
	}

	public Player nextPlayer1() {
		return null;
	}

	@Override
	public void nextPlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canEndTurn() {
		return false;
	}

	@Override
	public void setHasSponsor(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resolveStage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void increaseResponse() {
		// TODO Auto-generated method stub
		
	}


}
