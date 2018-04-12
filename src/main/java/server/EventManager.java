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


	public EventManager(ServerModel serverModel) {
		this.serverModel = serverModel;
		this.players = serverModel.getPlayers();
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
	
	//all allys are discarded
	private void CourtCalled() {
		
		setPlayers();

		for (int i = 0; i < serverModel.getPlayers().length; i++) {

			int partySize = serverModel.getPlayers()[i].getParty().size();
			
			for(int j = 0 ; j < partySize;j++) {
				if((players[i].getParty().get(j)).getSubType().equals("Ally")) {

					logger.info("Event Card: Court Called - Player " + i + " discards " + (players[i].getParty().get(j).getName()));
					String id = players[i].getParty().get(j).getID();
					serverModel.removeFromParty(id, i);
					serverModel.server.sendServerMessage("SERVERMESSAGE--REMOVEFROMPARTY--" + i + "--" + id);
					partySize--;
					j--;
				}
			}
		}
	}

	// execute the special in event here
	public void KingsRecognition() {
		logger.info("KingsRecognition is in play and gives the next players to complete a quest 2 shields");
	     serverModel.setKingRecognition(true);

	}

	public void QueenFavor() {
		logger.info("QueenFavor is in play and gives player 1 and 2 two adventure cards");
		int squireCount = 0;
		int championCount = 0;
		int championKnightCount = 0;

		
		setPlayers();

		for (int i = 0; i < players.length; i++) {

			if ((players[i].getRank()).getSubType().equals("Squire")) {
				squireCount++;
			}
			if ((players[i].getRank()).getSubType().equals("Knight")) {
				championCount++;
			}
			if ((players[i].getRank()).getSubType().equals("ChampionKnight")) {
				championKnightCount++;
			}

		}

		if (squireCount != 0) {
			logger.info("in (squireCount != 0)");
			for (int i = 0; i < players.length; i++) {
				if ((this.players[i].getRank()).getSubType().equals("Squire")) {
					AdventureCard c = serverModel.getAdventureDeck().peek();
					String ID = c.getID();
					serverModel.draw(ID,i);
					serverModel.server.sendServerMessage("SERVERMESSAGE--DRAW--" + i + "--" + ID);
					c = serverModel.getAdventureDeck().peek();
					ID = c.getID();
					serverModel.draw(ID,i);
					serverModel.server.sendServerMessage("SERVERMESSAGE--DRAW--" + i + "--" + ID);
				}
			}

		} // we then know there is one squire. give him 2 adventure cards

		if (championCount < championKnightCount && (squireCount == 0)) {
			logger.info("in (championCount < championKnightCount && (squireCount == 0))");
			for (int i = 0; i < players.length; i++) {
				if ((this.players[i].getRank()).getSubType().equals("Champion")) {
					AdventureCard c = serverModel.getAdventureDeck().peek();
					String ID = c.getID();
					serverModel.draw(ID,i);
					serverModel.server.sendServerMessage("SERVERMESSAGE--DRAW--" + i + "--" + ID);
					c = serverModel.getAdventureDeck().peek();
					ID = c.getID();
					serverModel.draw(ID,i);
					serverModel.server.sendServerMessage("SERVERMESSAGE--DRAW--" + i + "--" + ID);
				}
			}
			// there are less champions than championKnights
		}
		if ((championKnightCount == players.length) || (championCount == players.length)) {
			logger.info("in (championKnightCount == players.length) || (championCount == players.length)");

			for (int i = 0; i < players.length; i++) {
				AdventureCard c = serverModel.getAdventureDeck().peek();
				String ID = c.getID();
				serverModel.draw(ID,i);
				serverModel.server.sendServerMessage("SERVERMESSAGE--DRAW--" + i + "--" + ID);
				c = serverModel.getAdventureDeck().peek();
				ID = c.getID();
				serverModel.draw(ID,i);
				serverModel.server.sendServerMessage("SERVERMESSAGE--DRAW--" + i + "--" + ID);
			}
		}
	}
	
	//Pox in play and all players except  the next players to complete a quest 2 shields
	public void Pox() {
		
		logger.info("Pox - all other players lose 1 shield");

		setPlayers();

		int currentPlayer = serverModel.getActivePlayer().getPlayerNumber();
		
		for (int i = 0; i < this.players.length; i++) {
			if (currentPlayer != players[i].getPlayerNumber()) {
				logger.info("Player " + players[i] + " loses one shield");
				players[i].removeShields(1);
			}
			logger.info("Player " + players[i] + " now has " + players[i].getShieldCount() + " shields.");
		}
	}

	public void KingCallToArms() {
	logger.info("KingCallToArms in play and player 1 placed one weapon in the discard pile");

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

	//all players draw 2
	public void ProsperityThroughoutTheRealm() {
		logger.info("ProsperityThroughoutTheRealm() called ");
		
		setPlayers();
		for (int i = 0; i <players.length; i++) {
			AdventureCard c = serverModel.getAdventureDeck().peek();
			String ID = c.getID();
			serverModel.draw(ID,i);
			serverModel.server.sendServerMessage("SERVERMESSAGE--DRAW--" + i + "--" + ID);
			c = serverModel.getAdventureDeck().peek();
			ID = c.getID();
			serverModel.draw(ID,i);
			serverModel.server.sendServerMessage("SERVERMESSAGE--DRAW--" + i + "--" + ID);
		}
	}

	//Players with both lowest rank and least amount of shields, get 3 shields.
	public void ChilvarousDeed() {
	logger.info("ChilvarousDeed event in play");

		int squireCount = 0;
		int championCount = 0;
		int championKnightCount = 0;

		int lowestShieldSquire = 0;
		int lowestShieldKnight = 0;
		int lowestShieldChamp = 0;
		
		setPlayers();
		lowestShieldSquire = players[0].getShieldCount();
		for (int i = 0; i < players.length; i++) {

			if ((players[i].getRank()).getSubType().equals("Squire")) {
				squireCount++;
				
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
//		logger.info("P3 ranks is " + (players[3].getRank()).getSubType());

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
						serverModel.server.sendServerMessage("SERVERMESSAGE--ADDSHIELDS--" +i + "--" + 3);
					}
				}
			}
		}

		if (championCount <= championKnightCount && (squireCount == 0) && (championCount != 0)) {
			for (int i = 0; i < players.length; i++) {
				if ((players[i].getRank()).getSubType().equals("Champion")) {
					if (players[i].getShieldCount() == lowestShieldKnight) {
						players[i].addShields(3);
						serverModel.server.sendServerMessage("SERVERMESSAGE--ADDSHIELDS--" +i + "--" + 3);
					}
				}
			}
		}
		if (championKnightCount == numPlayers) {
			for (int i = 0; i < players.length; i++) {
				if ((players[i].getRank()).getSubType().equals("ChampionKnight")) {
					if (players[i].getShieldCount() == lowestShieldChamp) {
						players[i].addShields(3);
						serverModel.server.sendServerMessage("SERVERMESSAGE--ADDSHIELDS--" +i + "--" + 3);
					}
				}
			}
		}
	}
	//Drawer loses 2 shields

	public void Plague() {
		setPlayers();
		int curPlay = serverModel.getActivePlayer().getPlayerNumber();
		if (players[curPlay].getShieldCount() >= 2) {
			players[curPlay].removeShields(2);
		logger.info("Plague event in play and player " + curPlay + " looses two shields");
		serverModel.server.sendServerMessage("SERVERMESSAGE--REMOVESHIELDS--" + curPlay + "--" + 2);
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
		
		serverModel.nextPlayer();
		serverModel.nextStory();
		

		
	}

	@Override
	public void setPlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canEndTurn() {
		return true;
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

	@Override
	public void resolveTournament() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}


}
