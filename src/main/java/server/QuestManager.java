package server;

import java.util.*;

import javax.print.attribute.standard.NumberOfDocuments;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.sun.jmx.remote.internal.ArrayQueue;

import core.*;
import javafx.event.ActionEvent;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

/*
 * The model will delegate the job to the Questmanager 
 * 
 *Has important Data structures:
 * -  QuesterQueque to hold all the questers who embark on the adventure 
 * -
 * 
 *
 * The class Purpose is to handle all Questing events 
 * Using the State Design Pattern 
 * Implements the StoryCardState to share similar roles 
 */

public class QuestManager implements StoryCardState {
	private static final Logger logger = LogManager.getLogger(QuestManager.class);
	private static final String ENDTURN = "End turn";
	ServerModel serverModel;
	Player[] players;

	private boolean hasSponsor = false;

	public boolean getHasSponsor() {
		return this.hasSponsor;
	}

	private boolean questersReady = false;


	public void setHasSponsor(boolean hasSponsor) {
		this.hasSponsor = hasSponsor;
	}


	private int numOfansewers;

	private int numberOfCardsToReturn = 0;

	private int numberOfEndTurnsCalled = 0;		//the number of end turns

	private QuesterQueque questers;

	private int numOfQuesterPotential;

	private int numOfQuester = 0;

	private int numOfRepsonders = 0;

	private int nextPersonToDraw = 0;

	/**
	 * Constructor Takes a model gets its player and copies it make a new
	 * Questerqueue
	 */

	public QuestManager(ServerModel serverModel) {
		this.serverModel = serverModel;
		this.players = serverModel.getPlayers();
		this.questers = new QuesterQueque();

	}

	/**
	 * 
	 * This will handle the Questing events
	 */

	public void handle() {

		numOfQuesterPotential = serverModel.getPlayers().length-1 ;

		// if I do not have a sponsor ask the person if they want to sponsor
		
logger.info("Handling questing info");
		
		numOfQuesterPotential = serverModel.getPlayers().length-1;
		
		// if I do not have a sponsor ask the person if they want to sponsor 
		if(!hasSponsor) {
			
		// if I haven't ask to sponsor yet then ask
		if(!this.serverModel.getActivePlayer().declinedToSponsor) {
		serverModel.server.getSponsorDecision();
		
		
		
		
		
		serverModel.getActivePlayer().declinedToSponsor = true;
		
		}else {// I have went around all the players and no want to sponsor 
			//go to next story Cards}
			logger.info(" No one wanted to Sponsor lets go to the next story card");
		}
	}
		
		
		// Questers asking
		// if I have a sponsor and the quester are not ready then ask the current player
		
		if(hasSponsor && !questersReady) {
			// if I have a sponsor and the quester are not ready then ask the current plater
			if(!this.serverModel.getActivePlayer().declinedToQuest && !this.serverModel.getActivePlayer().isSponsor) {
				serverModel.server.getQuesterDecison();
				
				//they have answered
				this.serverModel.getActivePlayer().declinedToQuest = true;
			}else {
				questersReady = true;
			}
			
			
		}
	

		if(questersReady &&numOfQuesterPotential == numOfRepsonders ) {
			//begins the stage
			resolveStage();
			serverModel.server.resolveStage();
			for (int i = 0; i < serverModel.getPlayers().length; ++i) {
				
				logger.info("player" + i + " passed stage?: " + players[i].passedStage + " number End  : "+ numOfRepsonders);
				
			}

			numOfRepsonders =0;
			}
		
		}
		
		

	public void nextPlayer() {

		if (serverModel.getActivePlayer().isSponsor&& !questersReady ) {
			// making sure the Stage starts at 1

			serverModel.resetCurrentStage();
			numberOfCardsToReturn = numberOfCardsForSponsor();
		}
		if (!questersReady) {
			// If not ready for sponsor then loop

			serverModel.nextPlayer();
		}
		/*
		 * } else if (serverModel.isDoneQuestingMode()) {
		 * serverModel.setNextPlayer(nextPersonToDraw); } else if (questersReady) {
		 * logger.info("Number of TUrns is " + (numOfRepsonders++));
		 * 
		 * serverModel.setNextPlayer(questers.nextPlayer());
		 * 
		 * // should have looped and rady to do the next Player if (numOfRepsonders >
		 * questers.size()) { // all the players of made there turns lets move on to the
		 * next stage numOfRepsonders = 0;
		 * 
		 * // reolve stage // model.resolveStage(); this.resolveStage();
		 * 
		 * // fix later // serverModel.control.view.stageResolved();
		 * 
		 * this.questers.survivorsLeft(serverModel.getPlayers());
		 * 
		 * }
		 * 
		 * }
		 */
	}

	private boolean stageHarder() {
		logger.debug("stageHarder() called");

		int numStages = ((QuestCard) serverModel.getCurrentStoryCard()).getNumStages();

		if (numStages == 1)
			return true;

		else {
			for (int i = 0; i < numStages - 1; i++) {

				if (totalNumOfBP(serverModel.getStage().getStageAt(i)) >= totalNumOfBP(
						serverModel.getStage().getStageAt(i + 1))) {
					logger.info("stages ar not harder");
					return false;
				}
			}
		}
		return true;
	}

	private int totalNumOfBP(CardCollection<AdventureCard> stage) {
		logger.debug("totalNumOfBP() called");

		int numberOfBP = 0;

		for (int i = 0; i < stage.size(); i++) {
			if (((AdventureCard) stage.get(i)).getSubType().equals("Foe")) {
				numberOfBP += ((FoeCard) stage.get(i)).getBattlePoints();

			} else if (((AdventureCard) stage.get(i)).getSubType().equals("Weapon")) {

				numberOfBP += ((WeaponCard) stage.get(i)).getBattlePoints();
			}
		}

		return numberOfBP;

	}

	public boolean isfoeEachStage() {
		// check out if there a foe

		boolean foeInEachStage = true;
		boolean[] foesPresent = null;

		if (serverModel.getActivePlayer().isSponsor) {
			int numStages = ((QuestCard) serverModel.getCurrentStoryCard()).getNumStages();
			foesPresent = new boolean[numStages];
			for (int i = 0; i < numStages; ++i) {
				foesPresent[i] = false;
			}
			for (int i = 0; i < numStages; ++i) {
				for (int j = 0; j < serverModel.getStage().getStageAt(i).size(); ++j) {
					if (serverModel.getStage().getStageAt(i).get(j).getSubType().equals(AdventureCard.FOE)) {
						foesPresent[i] = true;
						break;
					}
				}
			}
			for (int i = 0; i < numStages; ++i) {
				if (foesPresent[i] == false) {
					foeInEachStage = false;
				}
			}
		}
		return foeInEachStage;
	}

	public int numberOfCardsForSponsor() {

		/**
		 * Functions used to count the number of cards to give to the sponsor : - check
		 * each stage if they is a foe card - check each stage if they is a test card -
		 * check each stage if they us a weapon card
		 * 
		 */
	
		int numOfCardsInStaging = 0;
		int numStages = ((QuestCard) serverModel.getCurrentStoryCard()).getNumStages();
		for (int i = 0; i < numStages; ++i) {
			for (int j = 0; j < serverModel.getStage().getStageAt(i).size(); ++j) {
				if (serverModel.getStage().getStageAt(i).get(j).getSubType().equals(AdventureCard.FOE)) {
					numOfCardsInStaging++;

				} else if (((AdventureCard) serverModel.getStage().getStageAt(i).get(j)).getSubType()
						.equals(AdventureCard.WEAPON)) {
					numOfCardsInStaging++;
				} else if (((AdventureCard) serverModel.getStage().getStageAt(i).get(j)).getSubType()
						.equals(AdventureCard.TEST)) {
					numOfCardsInStaging++;
				}
			}
		}
		logger.info("num for sponsors is "+ numOfCardsInStaging);
		return numOfCardsInStaging;
	}

	public boolean checkHandSize() {
		logger.debug("checkHandSize() called");

		// if the hand is bigger then 12 then reurn false
		if (serverModel.getActivePlayer().getHand().size() > 12) {
			// serverModel.control.alert("Hand Size is too large, please discard");
			logger.info("Player " + serverModel.getActivePlayer().getPlayerNumber() + " hand too large");
			return false;

		}
		return true;
	}

	public boolean canEndTurn() {
		logger.info("canEndTurn() called");

		// check out if they can end a turn
		boolean isHarder = stageHarder();
		boolean foeInEachStage = isfoeEachStage();
		boolean isHandSizeOk = checkHandSize();

		// check if its a sponsor and if it is then check if they sponsor correctly
		if (serverModel.getActivePlayer().isSponsor && !foeInEachStage) {
			// serverModel.control.alert("Foe not present in every stage.");
			return false;
			// check if its a sponsor and if the stages are Harder
		} else if (serverModel.getActivePlayer().isSponsor && !isHarder) {
			// serverModel.control.alert("The stages are not progressively harder");
			return false;

			// if their hand size id ok
		} else if (!isHandSizeOk) {
			// serverModel.control.alert("Your hand size is to big with "+
			// serverModel.getActivePlayer().getHand().size());
			return false;

			// let start the quest baby
		} else if (serverModel.getActivePlayer().isSponsor) {

			numberOfCardsToReturn = numberOfCardsForSponsor();
			// serverModel.control.stagesSet();
			return true;

			// The person was a quester its true
		} else {
			return true;
		}

	}

	public void reset() {

		hasSponsor = false;

		questersReady = false;

		numOfansewers = 0;

		numberOfEndTurnsCalled = 0;

		// questers ;

		numOfQuesterPotential = 0;

		numOfQuester = 0;

		numOfRepsonders = 0;

	}

	public void setPlayer() {
		// if my questers are ready then get the first person in my quest
		// model.setNextPlayer(questers.nextPlayer());

	}

	public int resolveQuest() {
		// Left it here because of one of the event cards
		
		players = serverModel.getPlayers();

		/**
		 * To resolve a Quest, we need to count the following data structures: - players
		 * Queue - players Party - players Rank - get a card if they pass
		 */
		logger.info("resolveQuest() called");


		boolean isKingRecognition = serverModel.isKingRecognition();
		if(isKingRecognition) {
			
			for (int i = 0; i <serverModel.getPlayers().length; i++) {
				if(!players[i].isSponsor && players[i].passedStage && players[i].isQuesting){

				this.players[i].addShields(2);
			}
				serverModel.setKingRecognition(false);;
			}
		}

		

		int numShields = ((QuestCard) serverModel.getCurrentStoryCard()).getNumStages();
		logger.info("Number of Stages: " + numShields);

		// TODO ADD THE BOOLEAN SETTING FOR PASSING QUEST HERE
		for (int i = 0; i < serverModel.getPlayers().length; ++i) {
			logger.info("Sponsor is getting " + numberOfCardsToReturn);
			if (players[i].isSponsor) {
				
				for (int j = 0; j < numberOfCardsToReturn; j++) {
					AdventureCard c = this.serverModel.getAdventureDeck().peek();
					String ID = c.getID();
					serverModel.draw(ID,players[i].getPlayerNumber());
					serverModel.server.sendServerMessage("SERVERMESSAGE--DRAW--" + players[i].getPlayerNumber() + "--" + ID);
				}
			}
			if (!players[i].isSponsor && players[i].passedStage && players[i].isQuesting) {
		
				players[i].passedQuest = true;
				if (players[i].passedQuest) {
					logger.info(players[i] +" has passed the quest and is receving  shields :"+ numShields );
					players[i].addShields(numShields);
					
				}

			}
		}

		

		return 0;
	}

	public void resolveStage() {
		/**
		 * To resolve a stage, we need to count the following data structures: - players
		 * Queue - players Party - players Rank - get a card if they pass
		 */
		CardCollection<AdventureCard> currStage = serverModel.getStage().getStageAt(serverModel.getStage().getCurrentStage());
		
		
		//serverModel.allysInPlay();
		logger.info("Current Stage num"+serverModel.getStage().getCurrentStage());
		
		int stageBP = 0;
		
		for (int i = 0; i < currStage.size(); ++i){
			logger.info(currStage.size());
			stageBP += currStage.get(i).getBattlePoints();
		}

		logger.info("STAGES POINTS "+stageBP);
		players = serverModel.getPlayers();
		for(int i = 0; i < serverModel.getPlayers().length; ++i){
			logger.info("Player " + i + "'s QUEUE BPs = " + serverModel.getPlayers()[i].getBattlePoint());
			logger.info("Player " + i + "'s PARTY BPs = " + serverModel.getPlayers()[i].getPartyBattlesPoint());
			logger.info("Player " + i + "'s BONUS BPs = " + serverModel.getPlayers()[i].getAllyBonusBattlePoints());
		
				int playerBP = serverModel.getPlayers()[i].getBattlePoint();
				playerBP += serverModel.getPlayers()[i].getPartyBattlesPoint();
				playerBP += serverModel.getPlayers()[i].getAllyBonusBattlePoints();
				
				logger.info( serverModel.getPlayers()[i].getTotalBattlePoint()+" TotalPoints: " + serverModel.getPlayers()[i].getTotalBattlePoint()+ "vs Stage BP :" + stageBP);
				if (playerBP>=stageBP && serverModel.getPlayers()[i].isQuesting&& !serverModel.getPlayers()[i].isSponsor) {
					logger.info(serverModel.getPlayers()[i] +" has passed the stage ");
					players[i].passedStage = true;
					
						
						logger.info("Player i"+ i + " is receving a card for the new stage");
						AdventureCard c = this.serverModel.getAdventureDeck().peek();
						String ID = c.getID();
						serverModel.draw(ID,players[i].getPlayerNumber());
						serverModel.server.sendServerMessage("SERVERMESSAGE--DRAW--" + players[i].getPlayerNumber() + "--" + ID);
					}else {
						logger.info("Did not make it ");
						players[i].passedStage = false;
						players[i].isQuesting = false;
						
						
					
					}
				
					
				}
		
		serverModel.stageOver();
		
		
	
		if(serverModel.getStage().getCurrentStage()+1==((QuestCard)serverModel.getState().currentStoryCard).getNumStages() ) {
		
			resolveQuest();
		
		
			}else {
				serverModel.getStage().nextStage();
			}
			
		}


		/*
		 * if (serverModel.getStage().getCurrentStage() + 1 == ((QuestCard)
		 * serverModel.currentStoryCard).getNumStages()) {
		 * 
		 * // restart the Questmanger hasSponsor = false; questersReady = false;
		 * 
		 * numOfansewers = 0;
		 * 
		 * numberOfEndTurnsCalled = 0;
		 * 
		 * questers.clear(); numOfQuesterPotential = 0;
		 * 
		 * numOfQuester = 0; numOfRepsonders = 0; nextPersonToDraw = 0;
		 * 
		 * serverModel.setDoneQuestingMode(true);
		 * 
		 * resolveQuest();
		 * 
		 * }
		 */

	

	@Override
	public void increaseResponse() {
		
		if(hasSponsor && !questersReady) {
			numOfRepsonders++;
		}
		if(hasSponsor && questersReady) {
			numOfRepsonders++;
		}
	}

	@Override
	public void resolveTournament() {
		// TODO Auto-generated method stub
		
	}
}
