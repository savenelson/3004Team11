package server;

import java.util.*;

import javax.print.attribute.standard.NumberOfDocuments;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.jmx.remote.internal.ArrayQueue;

import client.ClientModel;
import core.*;
import javafx.event.ActionEvent;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;


/*
 * The model will delegate the job to the Questmanger 
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
public class QuestManager implements StoryCardState{
	private static final Logger logger = LogManager.getLogger(QuestManager.class);
	private static final String ENDTURN = "End turn";
	ServerModel  serverModel;
	Player[] players ; 
	
	private boolean hasSponsor = false;
	
	private boolean questersReady = false;
	
	private int numOfansewers;
	
	private int numberOfCardsToReturn= 0;
	
	
	private int numberOfrequests = 0;
	
	
	private QuesterQueque questers ; 
	
	private int  numOfQuesterPotential;
	
	private int numOfQuester = 0;
	
	private int numOfRepsonders = 0 ;
	
	
	private int nextPersonToDraw= 0;
	
	
	
	
	/**
	 * 
	 * Constructor Takes a model 
	 * gets its player and copies it 
	 * make a new Questerquue 
	 */
	public QuestManager(ServerModel serverModel) {
		this.serverModel = serverModel;
		this.players = serverModel.getPlayers();
		this.questers = new QuesterQueque();		
		
	}
	
	public QuestManager(AbstractModel a) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * This will handle the Questing events 
	 * 
	 * 
	 */
	
	public void handle() {
	
		
		numOfQuesterPotential = serverModel.getNumPlayers() -1;
		
		

		/
		
		if(!this.serverModel.getActivePlayer().declinedQuesting) {
		boolean isQuesting;
		
		//This will send the messafe to the client model to ask client if they would like to quest 
		serverModel.server.getQuesterDecison();
		
		
		}
		
		
		// if I do not have a sponsor ask the person if they want to sponsor 
		/*if(!hasSponsor) {
			
		// if I haven't ask to sponsor yet then ask
		if(!this.serverModel.getActivePlayer().declinedToSponsor) {
		boolean wantToSponsor;
		serverModel.server.getSponsorDecision();
		}
		
		}*/
	/*	
		if(numberOfrequests == 0 ) { nextPersonToDraw = model.getActivePlayer().getPlayerNumber() +1; 
		if(nextPersonToDraw> model.getPlayers().length){nextPersonToDraw = 0;}
		}
		
		numberOfrequests++;
		
		// if they do want to sponsor then make them the sponsors
		if(wantToSponsor) {
			logger.info("Found a sponsor ");
			hasSponsor = true;
			
			model.getActivePlayer().isSponsor = true;
			numberOfrequests = 0;
			
			wantToSponsor = false;
			
			model.control.updateViewState();
			
		}
		// changed that they have asked 
		model.getActivePlayer().declinedToSponsor = true;
		
		}else {// I have went around all the players and no want to sponsor 
			//go to next story Cards}
			logger.info(" No one wanted to Sponsor lets go to the NEXT STORY GUY");
		}
	}
		
		
		// Questers asking
		// if I have a sponsor and the quester are not ready then ask the current player
		if(hasSponsor && !questersReady) {
			// if I have a sponsor and the quester are not ready then ask the current plater
			if(!this.model.getActivePlayer().declinedQuesting && !this.model.getActivePlayer().isSponsor) {
				boolean isQuesting = model.control.getQuestingDecision();
				
				
				if(isQuesting) {
					logger.info("THe Player has decidied to quest ");
					model.getActivePlayer().isQuesting = true;
					questers.add(model.getActivePlayer().getPlayerNumber());
					
				}
				//they have answered
				this.model.getActivePlayer().declinedQuesting = true;
				
				numberOfrequests++;
				
			}
			
			
		}if(numOfQuesterPotential == numberOfrequests ) {
			// I return to the sponor 
			if(questers.isEmpty()) {
				numberOfrequests = 0;
				
				logger.info("I have no  any questers ");
				// should go to the next story hard 
			}else {
				// The questers are ready adn we are ready to begin questing
				
				//logger.info("I do have some questers. Let us begin our adventures ");
				questersReady = true;
				logger.info("I do have some questers. Let us begin our adventures ");
				
				numberOfrequests = 0 ;
				
				
			}
			
		}
		if(questersReady ) {
			//begins the stage
			logger.info("Done  hh"+ numOfRepsonders);
			
			numOfQuester = questers.size();
			if(numOfQuester==numOfRepsonders) {
				//all the questers made their choice time to resolve stage;
				logger.info("Done  hh"+ numOfRepsonders);
				
				
				
				
			}else {
				logger.info("Current "+ numOfRepsonders);
				
			}
			
			
		}*/
	}
	
		


	public void  nextPlayer() {
		
		if( serverModel.getActivePlayer().isSponsor) {
			//making sure the Stage starts at 1 
			
			serverModel.resetCurrentStage();
			
		}
		if(!questersReady) {
			// If not ready for sponsor then loop
			
			serverModel.nextPlayer();
			
			}
		else if(serverModel.isDoneQuestingMode()) {
			serverModel.setNextPlayer(nextPersonToDraw);
		}
		else if (questersReady) {
			logger.info("Number of TUrns is "+(numOfRepsonders++));
			
			serverModel.setNextPlayer(questers.nextPlayer());
			
			//should have looped and rady to do the next Player
			if(numOfRepsonders  >questers.size()) {
			//all the players of made there turns lets move on to the next stage
			numOfRepsonders = 0;
			
			//reolve stage
			//model.resolveStage();
			this.resolveStage();
			
			
			// fix later 
			//serverModel.control.view.stageResolved();
			
			
			this.questers.survivorsLeft(serverModel.getPlayers());
			
			
		}
			
	}
	}
	private boolean stageHarder() {
		logger.debug("stageHarder() called");

		int numStages = ((QuestCard) serverModel.currentStoryCard).getNumStages();
		
		
		if (numStages == 1) return true;
		
		else {
			for(int i =0; i<numStages-1; i++) {
				
				if(totalNumOfBP(serverModel.getStage().getStageAt(i))>=totalNumOfBP(serverModel.getStage().getStageAt(i+1))) {
					logger.info("stages ar not harder");
					return false;
				}
			}	
		}
		return true;
	}
	private int totalNumOfBP(CardCollection<AdventureCard> stage) {
		logger.debug("totalNumOfBP() called");
		
		int numberOfBP =  0;

		for(int i=0; i<stage.size(); i++) {
		  if (((AdventureCard) stage.get(i)).getSubType().equals("Foe")) {
		    numberOfBP+=((FoeCard) stage.get(i)).getBattlePoints();
		  
		    
		  }else if (((AdventureCard) stage.get(i)).getSubType().equals("Weapon")) {
		    
		    numberOfBP+=((WeaponCard) stage.get(i)).getBattlePoints();
		  }
		}

		return numberOfBP;
		
	}
	
	public boolean isfoeEachStage() {
		// check out if there a  foe 
		
		boolean foeInEachStage = true;
		boolean [] foesPresent = null;
		
	
	  	if(serverModel.getActivePlayer().isSponsor){
    		int numStages = ((QuestCard)serverModel.currentStoryCard).getNumStages();
    		foesPresent  = new boolean [numStages];
    		for (int i = 0; i < numStages; ++i){
    			foesPresent[i] = false;
			}		    		
    		for (int i = 0; i < numStages; ++i){
    			for (int j = 0; j < serverModel.getStage().getStageAt(i).size(); ++j){
    				if(serverModel.getStage().getStageAt(i).get(j).getSubType().equals(AdventureCard.FOE)){
    					foesPresent[i] = true;
    					break;
    				}
    			}
    		}
    		for (int i = 0; i < numStages; ++i){
    			if(foesPresent[i] == false){
    				foeInEachStage = false;
    			}
			}
    	}
	  return foeInEachStage;
	  	}
	
	
	public int  numberOfCardsForSponsor() {
		
		/**
		 *  Functions used to count the number of cards to give to the sponsor :
		 *    - check each stage if they is a foe card
		 *    - check each stage if they is a test card
		 *    - check each stage if they us a weapon card
		 *   
		 */
		int numOfCardsInStaging = 0;
		int numStages = ((QuestCard)serverModel.currentStoryCard).getNumStages();
		for (int i = 0; i < numStages; ++i){
			for (int j = 0; j < serverModel.getStage().getStageAt(i).size(); ++j){
				if(((AdventureCard) serverModel.getStage().getStageAt(i).get(j)).subType.equals(AdventureCard.FOE)){
				numOfCardsInStaging++;
					
				}else if(((AdventureCard) serverModel.getStage().getStageAt(i).get(j)).subType.equals(AdventureCard.WEAPON)){
				numOfCardsInStaging++;
				}else if(((AdventureCard) serverModel.getStage().getStageAt(i).get(j)).subType.equals(AdventureCard.TEST)){
				numOfCardsInStaging++;	
				}
			}
		}
		return numOfCardsInStaging;
	}

	public boolean checkHandSize() {
		logger.debug("checkHandSize() called");

		// if the hand is bigger then 12 then reurn false 
		if(serverModel.getActivePlayer().getHand().size() > 12) {
			serverModel.control.alert("Hand Size is too large, please discard");
			logger.info("Player " + serverModel.getActivePlayer().getPlayerNumber()+ " hand too large");
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
		if(serverModel.getActivePlayer().isSponsor && !foeInEachStage){	    			
			serverModel.control.alert("Foe not present in every stage.");
			return false;
		//check if its a sponsor and if the stages are Harder
		} else if(serverModel.getActivePlayer().isSponsor && !isHarder) {
			serverModel.control.alert("The stages are not progressively harder");
			return false;
			
		// if their hand size id ok
		} else if(!isHandSizeOk){	
			serverModel.control.alert("Your hand size is to big with "+ serverModel.getActivePlayer().getHand().size());
			return false;
			
		// let start the quest baby
		}else if (serverModel.getActivePlayer().isSponsor)  {
			
				numberOfCardsToReturn = numberOfCardsForSponsor();
				serverModel.control.stagesSet();
			return true;
			
		// The person was a quester its true 
		}else {
			return true;
		}
		
	

			
	}
	
	public void reset() {
		
		hasSponsor = false;
		
		
		questersReady = false;
		
		numOfansewers =0;
		
		
		numberOfrequests = 0;
		
		
		//questers ; 
		
		numOfQuesterPotential = 0;
		
		
		numOfQuester = 0;
		
		numOfRepsonders = 0 ;
		
	}
	


	public void setPlayer() {
		// if my questers are ready then get the first person in my 	quest
		//model.setNextPlayer(questers.nextPlayer());
		
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

		int numStages = serverModel.numStages;

		
		boolean inNextQ = false;
		if(inNextQ) {
			
			for (int i = 0; i <serverModel.getNumPlayers(); i++) {
				if(!players[i].isSponsor){

				this.players[i].addShields(2);
			}
			inNextQ = false;
			}
		}

		int numShields = ((QuestCard) serverModel.currentStoryCard).getNumStages();
		logger.info("Number of Stages: " + numShields);

		//TODO ADD THE BOOLEAN SETTING FOR PASSING QUEST HERE
		for (int i = 0; i < serverModel.getNumPlayers(); ++i){
			if(players[i].isSponsor) {
				logger.info("Sponsor is getting "+ numberOfCardsToReturn);
				for(int j = 0 ; j< numberOfCardsToReturn; j ++) {
	
					//players[i].addToHand(new WeaponCard(WeaponCard.SWORD_NAME, WeaponCard.SWORD_BATTLE_POINTS));
					players[j].addToHand(serverModel.getAdventureDeck().pop());
				}
			}
			if(!players[i].isSponsor){

				

				if(players[i].passedQuest) {
					players[i].addShields(numShields);
					for(int j=0;j<numStages;j++) {
						Card c = serverModel.getAdventureDeck().pop();
						this.players[i].addToHand(c);
						serverModel.getAdventureDeckDiscard().add(c);
					}
				}

			}
		}
		

		
		serverModel.control.resolveQuest();
		
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
		logger.info("resolveStage() called");

		
		CardCollection currStage = serverModel.getStage().getStageAt(serverModel.getStage().getCurrentStage());
		
		int stageBP = 0;

		for (int i = 0; i < currStage.size(); ++i){
			stageBP += ((AdventureCard)currStage.get(i)).getBattlePoints();
		}
		players = serverModel.getPlayers();
		for(int i = 0; i < serverModel.getNumPlayers(); ++i){
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
			

			if(playerBP >= stageBP && (players[i].isQuesting) && stageBP > 0){
				
				players[i].passedStage = true;
				logger.info("Player " + players[i].getPlayerNumber() +"and has passed ");
				if(serverModel.getState().currentStage +1==((QuestCard)serverModel.getState().currentStoryCard).getNumStages() ) {
					
					players[i].passedQuest =true;

				
					Card c = serverModel.getAdventureDeck().pop();
					this.players[i].addToHand(c);
					serverModel.getAdventureDeckDiscard().add(c);
				}
			
		//
				
		}else {players[i].isQuesting = false;
		
		
		}
			
		}
		if(serverModel.getStage().getCurrentStage()+1== ((QuestCard) serverModel.currentStoryCard).getNumStages()){
			//restart the Questmanger
			hasSponsor = false;
			questersReady = false;
			
			numOfansewers =0 ;
			
			numberOfrequests = 0;
			
			questers.clear(); 
			numOfQuesterPotential = 0;
			
			
			numOfQuester = 0;
			numOfRepsonders = 0 ;
			nextPersonToDraw= 0;
			
			
			
			
			serverModel.setDoneQuestingMode(true);
			

			resolveQuest();

		}
	


	}


	




	

}
