package client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.AdventureCard;
import core.AdventureDeck;
import core.CardCollection;
import core.Player;
import core.StoryCardState;

// class to handle event cards
public class EventManager implements StoryCardState{
private static final Logger logger = LogManager.getLogger(EventManager.class);
Player [] players;
int numPlayers;
AdventureDeck adventureDeck;
AdventureDeck adventureDeckDiscard;
boolean nextQ;
int currentPlayer;
ClientModel clientModel; 
public EventManager(ClientModel clientModel) {
	this.clientModel = clientModel;
	this.players= clientModel.getPlayers();
	this.numPlayers = clientModel.numPlayers;
	this.adventureDeck = clientModel.getAdventureDeck();
	this.nextQ = clientModel.inNextQ;
	this.currentPlayer = clientModel.currentPlayer;
	
}

	
public void handleEvent(String eventName) {
			if(eventName.equals("KingsRecognition")) {
				KingsRecognition();
			}else if(eventName.equals("QueensFavor")) {
				QueenFavor();
				
			}else if(eventName.equals("Pox")) {
				Pox();
			}else if(eventName.equals("KingsCallToArms")) {
				KingCallToArms();
			}else if(eventName.equals("ProsperityThroughoutTheRealm")) {
			//	ProsperityThroughoutTheRealm();
				
			}else if(eventName.equals("ChivalrousDeed")) {
				ChilvarousDeed();
			
			}else if(eventName.equals("Plague")) {
				Plague();
				
			}else if(eventName.equals("CourtCalled")) {
				CourtCalled();
			}
			
		
	}
private void CourtCalled() {
	for (int i = 0; i < this.players.length; i++) {
		CardCollection<AdventureCard> hand = this.players[i].getHand();

			// cards from 100 -109
			for (int k = 0; k<10; k++) {
				if(hand.getByID("10"+k)!=null) {
					adventureDeckDiscard.add(hand.getByID("10"+k));
					hand.remove(hand.getByID("10"+k));
					
				}
			}
	}
			

}


//execute the special in event here 
public void KingsRecognition() {
	this.nextQ = true;
	
}
public void QueenFavor() {
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
	Player currentPlayer = players[this.currentPlayer];
	for (int i = 0; i < this.players.length; i++) {
		System.out.println("Players " +players[i]+ "with this many "+players[i].getShieldCount());
		if(currentPlayer.getPlayerNumber()!=players[i].getPlayerNumber())
			this.players[i].removeShields(1);
		System.out.println("After Players " +players[i]+ "with this many "+players[i].getShieldCount());
	}
	
}
public void KingCallToArms() {
	//TODO : implement into the model boolean ?
	Player currentplay = players[this.currentPlayer];
	int numOfweapons = currentplay.getHand().getnumberOfWeapons();
	//number of foes i put a number really high to get all of them 
	int numOfFoes = currentplay.getHand().getNumberOfFoecardsLessthan(100);
	if(numOfweapons >=1) {
		System.out.println("Please discard a weapon card");
		
	}else if(numOfFoes>=1) {
		System.out.println("Please discard 2 weapond cards ");
	}
	
}
public void ProsperityThroughoutTheRealm() {
	logger.info("Handling ProsperityThroughoutTheRealm event ");
	
	int nummOfPlayers = clientModel.getPlayers().length;
	for (int i = 0; i < nummOfPlayers; i++) {
		// need to draw from  story card 
		logger.info(players[i].getPlayerNumber());
		
		//need to add cards to adventure deck will give erroe 
		this.players[i].addToHand(this.adventureDeck.pop());
		this.players[i].addToHand(this.adventureDeck.pop());
	}

}
public void ChilvarousDeed() {
	int squireCount = 0;
	int championCount = 0;
	int championKnightCount = 0;
	
	int lowestShieldSquire = 0;
	int lowestShieldKnight = 0;
	int lowestShieldChamp = 0;
	

	for (int i = 0; i < this.players.length; i++) {

		if ((this.players[i].getRank()).getSubType().equals("Squire")) {
			squireCount++;
			 lowestShieldSquire = this.players[i].getShieldCount();
			 if (lowestShieldSquire > this.players[i].getShieldCount()){lowestShieldSquire = this.players[i].getShieldCount();}
		}
		if ((this.players[i].getRank()).getSubType().equals("Knight")) {
			championCount++;
			lowestShieldKnight = this.players[i].getShieldCount();
			 if (lowestShieldKnight > this.players[i].getShieldCount()){lowestShieldKnight = this.players[i].getShieldCount();}
		}
		if ((this.players[i].getRank()).getSubType().equals("ChampionKnight")) {
			championKnightCount++;
			lowestShieldChamp = this.players[i].getShieldCount();
			 if (lowestShieldChamp > this.players[i].getShieldCount()){lowestShieldChamp = this.players[i].getShieldCount();}
		}

	}

	if (squireCount != 0) {  // squire is the lowest, so as long as it there are more than 0 squires, we know the lowest rank is that of the squire
		for (int i = 0; i < this.players[i].getQueue().size(); i++) {
			if ((this.players[i].getRank()).getSubType().equals("Squire")) {
				
				if (this.players[i].getShieldCount() == lowestShieldSquire ) {
					this.players[i].addShields(6);
					
				}

			}
		}

	} 

	if (championCount <= championKnightCount && (squireCount == 0) && (championCount != 0)) {
		for (int i = 0; i < this.players[i].getQueue().size(); i++) {
			if ((this.players[i].getRank()).getSubType().equals("Champion")) {
				if (this.players[i].getShieldCount() == lowestShieldKnight ) {
					this.players[i].addShields(3);
					
				}
				
			}
		}
	}
	if (championKnightCount == numPlayers) {
		for (int i = 0; i < this.players[i].getQueue().size(); i++) {
			if ((this.players[i].getRank()).getSubType().equals("ChampionKnight")) {
				if (this.players[i].getShieldCount() == lowestShieldChamp ) {
					this.players[i].addShields(3);
					
				}
				
			}

		}
	}
	
}
public void Plague() {
	if (this.players[currentPlayer].getShieldCount() >= 2) {
		this.players[currentPlayer].removeShields(2);
	}
	
}


public void handle() {
	// TODO Auto-generated method stub
	

	
}


public Player nextPlayer1() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void nextPlayer() {
	// TODO Auto-generated method stub		
			
	//clientModel.nextPlayer();

	
}


@Override
public void setPlayer() {
	// TODO Auto-generated method stub
	
}


@Override
public boolean canEndTurn() {
	// TODO Auto-generated method stub

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

	
	
	
