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
 * 
 * 
 * 
 * 
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
	 * 
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

		numOfQuesterPotential = serverModel.getPlayers().length;

		// if I do not have a sponsor ask the person if they want to sponsor
		if (!hasSponsor) {

			// if I haven't ask to sponsor yet then ask ORIGINAL
			if (!this.serverModel.getActivePlayer().declinedToSponsor) {
			
				serverModel.server.getSponsorDecision();
			}
		} else if(!questersReady && hasSponsor) {
			
			// if I haven't ask to quester yet then ask
			if (!this.serverModel.getActivePlayer().declinedToQuest) {
		
				serverModel.server.getQuesterDecison();
				serverModel.getActivePlayer().declinedToQuest = true;
			}
			// numOfQuesterPotential
			if (numberOfEndTurnsCalled == numOfQuesterPotential) {
				numberOfEndTurnsCalled = 0 ;
				questersReady = true;
			}

			if (questersReady) {
				resolveStage();
				for (int i = 0; i < serverModel.getNumPlayers(); ++i) {
					logger.info("player" + i + " passed stage?: " + players[i].passedStage);
				}

				serverModel.server.resolveStage();
				numberOfEndTurnsCalled = 0;
			}
		}

		/*
		 * if(numberOfEndTurnsCalled == 0 ) { nextPersonToDraw =
		 * model.getActivePlayer().getPlayerNumber() +1; if(nextPersonToDraw>
		 * model.getPlayers().length){nextPersonToDraw = 0;} }
		 * 
		 * numberOfEndTurnsCalled++;
		 * 
		 * // if they do want to sponsor then make them the sponsors if(wantToSponsor) {
		 * logger.info("Found a sponsor "); hasSponsor = true;
		 * 
		 * model.getActivePlayer().isSponsor = true; numberOfEndTurnsCalled = 0;
		 * 
		 * wantToSponsor = false;
		 * 
		 * model.control.updateViewState();
		 * 
		 * } // changed that they have asked model.getActivePlayer().declinedToSponsor =
		 * true;
		 * 
		 * }else {// I have went around all the players and no want to sponsor //go to
		 * next story Cards}
		 * logger.info(" No one wanted to Sponsor lets go to the NEXT STORY GUY"); } }
		 * 
		 * 
		 * // Questers asking // if I have a sponsor and the quester are not ready then
		 * ask the current player if(hasSponsor && !questersReady) { // if I have a
		 * sponsor and the quester are not ready then ask the current plater
		 * if(!this.model.getActivePlayer().declinedQuesting &&
		 * !this.model.getActivePlayer().isSponsor) { boolean isQuesting =
		 * model.control.getQuestingDecision();
		 * 
		 * 
		 * if(isQuesting) { logger.info("THe Player has decidied to quest ");
		 * model.getActivePlayer().isQuesting = true;
		 * questers.add(model.getActivePlayer().getPlayerNumber());
		 * 
		 * } //they have answered this.model.getActivePlayer().declinedQuesting = true;
		 * 
		 * numberOfEndTurnsCalled++;
		 * 
		 * }
		 * 
		 * 
		 * }if(numOfQuesterPotential == numberOfEndTurnsCalled ) { // I return to the sponor
		 * if(questers.isEmpty()) { numberOfEndTurnsCalled = 0;
		 * 
		 * logger.info("I have no  any questers "); // should go to the next story hard
		 * }else { // The questers are ready adn we are ready to begin questing
		 * 
		 * //logger.info("I do have some questers. Let us begin our adventures ");
		 * questersReady = true;
		 * logger.info("I do have some questers. Let us begin our adventures ");
		 * 
		 * numberOfEndTurnsCalled = 0 ;
		 * 
		 * 
		 * }
		 * 
		 * } if(questersReady ) { //begins the stage logger.info("Done  hh"+
		 * numOfRepsonders);
		 * 
		 * numOfQuester = questers.size(); if(numOfQuester==numOfRepsonders) { //all the
		 * questers made their choice time to resolve stage; logger.info("Done  hh"+
		 * numOfRepsonders);
		 * 
		 * 
		 * 
		 * 
		 * }else { logger.info("Current "+ numOfRepsonders);
		 * 
		 * }
		 * 
		 * 
		 * }
		 */
	}

	public void nextPlayer() {

		if (serverModel.getActivePlayer().isSponsor) {
			// making sure the Stage starts at 1

			serverModel.resetCurrentStage();

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

		/**
		 * To resolve a Quest, we need to count the following data structures: - players
		 * Queue - players Party - players Rank - get a card if they pass
		 */
		logger.info("resolveQuest() called");

		int numStages = serverModel.numStages;

		boolean inNextQ = false;
		if (inNextQ) {

			for (int i = 0; i < serverModel.getNumPlayers(); i++) {
				if (!players[i].isSponsor) {

					this.players[i].addShields(2);
				}
				inNextQ = false;
			}
		}

		int numShields = ((QuestCard) serverModel.getCurrentStoryCard()).getNumStages();
		logger.info("Number of Stages: " + numShields);

		// TODO ADD THE BOOLEAN SETTING FOR PASSING QUEST HERE
		for (int i = 0; i < serverModel.getNumPlayers(); ++i) {
			if (players[i].isSponsor) {
				logger.info("Sponsor is getting " + numberOfCardsToReturn);
				for (int j = 0; j < numberOfCardsToReturn; j++) {

					// players[i].addToHand(new WeaponCard(WeaponCard.SWORD_NAME,
					// WeaponCard.SWORD_BATTLE_POINTS));
					players[j].addToHand(serverModel.getAdventureDeck().pop());
				}
			}
			if (!players[i].isSponsor && players[i].passedStage) {

				if (players[i].passedQuest) {
					players[i].addShields(numShields);
					for (int j = 0; j < numStages; j++) {
						AdventureCard c = serverModel.getAdventureDeck().pop();
						this.players[i].addToHand(c);
						serverModel.getAdventureDeckDiscard().add(c);
					}
				}

			}
		}

		// serverModel.control.resolveQuest();

		return 0;
	}

	public void resolveStage() {
		/**
		 * To resolve a stage, we need to count the following data structures: - players
		 * Queue - players Party - players Rank - get a card if they pass
		 */
		CardCollection<AdventureCard> currStage = serverModel.getStage().getStageAt(serverModel.getStage().getCurrentStage());
		
		serverModel.allysInPlay();
		logger.info("RESOLVING STAGE");
		
		int stageBP = 0;
		
		for (int i = 0; i < currStage.size(); ++i){
			logger.info(currStage.size());
			stageBP += currStage.get(i).getBattlePoints();
		}
		// serverModel.allysInPlay();
		logger.info("STAGES POINTS "+stageBP);
		players = serverModel.getPlayers();
		for(int i = 0; i < serverModel.getPlayers().length; ++i){
			logger.info(serverModel.getPlayers()[i]);
			logger.info("Player " + i + "'s QUEUE BPs = " + serverModel.getPlayers()[i].getBattlePoint());
			logger.info("Player " + i + "'s PARTY BPs = " + serverModel.getPlayers()[i].getPartyBattlesPoint());
			logger.info("Player " + i + "'s BONUS BPs = " + serverModel.getPlayers()[i].allyBonusBattlePoints);
		
				int playerBP = serverModel.getPlayers()[i].getBattlePoint();
				playerBP += serverModel.getPlayers()[i].getPartyBattlesPoint();
				playerBP += serverModel.getPlayers()[i].allyBonusBattlePoints;
				
				logger.info( "Player ally battle points "+serverModel.getPlayers()[i].getPartyBattlesPoint());
				if (playerBP>=stageBP && serverModel.getPlayers()[i].isQuesting) {
					players[i].passedStage = true;
					if(serverModel.getState().currentStage+1 ==((QuestCard)serverModel.getState().currentStoryCard).getNumStages()) {
						players[i].isQuesting = true;
					}
					
					
				
					}else {
						
						players[i].passedStage = false;
						players[i].isQuesting = false;
						
					}
				
					
				}
	
		if(serverModel.getState().currentStage+1 ==((QuestCard)serverModel.getState().currentStoryCard).getNumStages() ) {
			
			resolveQuest();
		
			
		
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

	}

	@Override
	public void increaseResponse() {
		
		if(hasSponsor) {
			numberOfEndTurnsCalled++;
		}
	}
}
