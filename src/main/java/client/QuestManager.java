package client;

import java.util.*;

import javax.print.attribute.standard.NumberOfDocuments;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.jmx.remote.internal.ArrayQueue;

import core.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class QuestManager implements StoryCardState{
	private static final Logger logger = LogManager.getLogger(QuestManager.class);
	private static final String ENDTURN = "End turn";
	Model  model;
	Player[] players ; 
	
	boolean hasSponsor = false;
	
	boolean questersReady = false;
	
	int numOfansewers;
	
	private int numberOfCardsToReturn= 0;
	
	
	int numberOfrequests = 0;
	
	
	QuesterQueque questers ; 
	
	int numOfQuesterPotential;
	

	int numOfQuester = 0;
	
	int numOfRepsonders = 0 ;
	
	
	int nextPersonToDraw= 0;
	
	
	public QuestManager(Model model) {
		this.model = model;
		players = model.getPlayers();
		
		questers = new QuesterQueque();	
		
		
	}
	
	
	
	public void handle() {
		
		numOfQuesterPotential = model.numPlayers -1; 									// Get the number of Questers To Ask
		
		
		/** Sponsor Asking stage
		 * Will move to the Quest asking stage once we find a sponsor  
		 * 
		 */
		
		
		
		if(!hasSponsor) {																// If I do not have a Quester yet Ask The current Player 							
	
			if(!this.model.getActivePlayer().declinedToSponsor) {								//If they haven't declined to be a sponsor 
				
			boolean wantToSponsor= model.control.getSponsorDecision();						// Get a pop up to ask them if they would like to 
			
			logger.info("Would you like to sponsor? "+ model.getActivePlayer());
			
			if(numberOfrequests == 0 ) { 
				nextPersonToDraw = model.getActivePlayer().getPlayerNumber() +1;  			//get the nextPlayer to Draw after the quest If they are first to ask 
				if(nextPersonToDraw> model.numPlayers){nextPersonToDraw = 0;}
			}
		
			numberOfrequests++;																//increase the the number of requester 
		
		
			if(wantToSponsor) {																//If they would like to sponsor
				logger.info("Found a sponsor ");
				hasSponsor = true;															// change the hasSponort to true 
				
				model.getActivePlayer().isSponsor = true; 									//change the person to say his a sponsor 
				numberOfrequests = 0;														//number of requests ask to zero to ask questers 
				
				wantToSponsor = false;														
				
				model.control.updateViewState();												//update their View to being questing 
				
			}
			model.getActivePlayer().declinedToSponsor = true;									// changed that they have asked 

			// TODO: GEt the model to go to the next story crd
		}else {logger.info(" No one wanted to Sponsor lets go to the NEXT STORY"); }
		}
		
		
		/** Questing  Asking stage
		 * Will move to handling questing event once asked all the other players if they would like to join 
		 * 
		 */
	
		if(hasSponsor && !questersReady) {						
			// if I have a sponsor and the quester are not ready then ask the current player																								
			if(!this.model.getActivePlayer().declinedQuesting && !this.model.getActivePlayer().isSponsor) {
				
				boolean isQuesting = model.control.getQuestingDecision();							// ask the player If They would like to 
				
				
				if(isQuesting) {
					logger.info("THe Player has decidied to quest "); 
					model.getActivePlayer().isQuesting = true;
					questers.add(model.getActivePlayer().getPlayerNumber());					// this player would like to join Questerqueque 
					}
				
				this.model.getActivePlayer().declinedQuesting = true;                    //they have answered
				
				numberOfrequests++;
				
			}
			
			
		}if(numOfQuesterPotential == numberOfrequests ) {									// If we ask all the other players to quest 
		
			
			/*Questing Stage asking is over 
			 * 
			 * Check if I have any brave souls ready to quest 
			 * 
			 * 
			 * If I do lets Begin staging 
			 * 
			 * 
			 */
			
			if(questers.isEmpty()) {
				numberOfrequests = 0;
				
				logger.info("I have no  any questers ");
				
			}else {
				/* We begin our adventure of Questing
				 * 
				 * 
				 */
				questersReady = true;
				logger.info( "Let us begin our adventures ");
				
				numberOfrequests = 0 ;
				}
			}
			if(questersReady ) {	   											// We are ready to being questing 
				numOfQuester = questers.size();
				
				//if(numOfQuester==numOfRepsonders) {
					//all the questers made their choice time to resolve stage;
			}
	
	}
	
		
	/*The Next Player will depend where we are current
	 *  On our Asking stage 
	 *  
	 *  
	 *  Are we TRying to get a sponsor 
	 *  
	 *  
	 *  Are we currently in Questing ?
	 * 
	 * 
	 */

	public void  nextPlayer() {
			
		if(canEndTurn() && model.getActivePlayer().isSponsor) { model.resetCurrentStage();}				//making sure the Stage starts at 1
		
		if(canEndTurn() && !questersReady) {model.nextPlayer();}          								//Move onto the nest player still asking people
	
		else if(model.isDoneQuestingMode) {model.setNextPlayer(nextPersonToDraw);}  						// are we done with Questing
		
		else if (questersReady) {																		//Currently we are questing 
		
			model.setNextPlayer(questers.nextPlayer());													//set the NextPlayer
			
			
			if(numOfRepsonders  >questers.size()) {														// every Quester Made their turn 
					
				numOfRepsonders = 0; 																	
				this.resolveStage();																		// resolve the Stage 
				model.control.view.stageResolved();														//resolve the stage View 
				this.questers.survivorsLeft(model.getPlayers());											//see the Quester who is left 
			}
		}
	}
	
	/* The purpose is that  to see If the stage are progressively harder
	 * 
	 * 
	 */
	
	private boolean stageHarder() {
		logger.debug("stageHarder() called");

		int numStages = ((QuestCard) model.currentStoryCard).getNumStages(); 
		if (numStages == 1) { return true;
		}else {
			for(int i =0; i<numStages-1; i++) {
				
				if(totalNumOfBP(model.stage.getStageAt(i))>=totalNumOfBP(model.stage.getStageAt(i+1))) {
					logger.info("stages are not harder");
					return false;
				}
			}	
		}
		return true;
	}
	
	/* Calculate the total Of Batte points in a stage
	 * 
	 * 
	 */
	private int totalNumOfBP(CardCollection<AdventureCard> stage) {
		logger.debug("totalNumOfBP() called");
		
		int numberOfBP =  0;

		for(int i=0; i<stage.size(); i++) {
		  if ((stage.get(i)).getSubType().equals("Foe")) {
		    numberOfBP+=((FoeCard) stage.get(i)).getBattlePoints();
		 
		  }else if (stage.get(i).getSubType().equals("Weapon")) {
		    numberOfBP+=((WeaponCard) stage.get(i)).getBattlePoints();
		  }
		}

		return numberOfBP;
		
	}
	
	/* The purpose is that  to see If there is a foe at each stage 
	 * If they are a spponsor 
	 * 
	 */
	
	public boolean isfoeEachStage() {
		
		
		boolean foeInEachStage = true;
		boolean [] foesPresent = null;
		
	
	  	if(model.getActivePlayer().isSponsor){
	    		int numStages = ((QuestCard)model.currentStoryCard).getNumStages();
	    		foesPresent  = new boolean [numStages];
	    		for (int i = 0; i < numStages; ++i){
	    				foesPresent[i] = false;
			}		    		
	    		for (int i = 0; i < numStages; ++i){
	    			
	    			for (int j = 0; j < model.stage.getStageAt(i).size(); ++j){
	    				if(( model.stage.getStageAt(i).get(j)).getSubType().equals(AdventureCard.FOE)){
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
	
	
	/**
	 *  Functions used to count the number of cards to give to the sponsor :
	 *    - check each stage if they is a foe card
	 *    - check each stage if they is a test card
	 *    - check each stage if they us a weapon card
	 *   
	 */
	
	public int  numberOfCardsForSponsor() {
		
		
		int numOfCardsInStaging = 0;
		int numStages = ((QuestCard)model.currentStoryCard).getNumStages();
		for (int i = 0; i < numStages; ++i){
			for (int j = 0; j < model.stage.getStageAt(i).size(); ++j){
				if((model.stage.getStageAt(i).get(j)).getSubType().equals(AdventureCard.FOE)){
					numOfCardsInStaging++;
				}else if(( model.stage.getStageAt(i).get(j)).getSubType().equals(AdventureCard.WEAPON)){
					numOfCardsInStaging++;
				}else if((model.stage.getStageAt(i).get(j)).getSubType().equals(AdventureCard.TEST)){
					numOfCardsInStaging++;	
				}
			}
		}
		return numOfCardsInStaging;
	}
	/* Check if the player has more then 12 cards in their hand
	 * 
	 * 
	 */
	public boolean checkHandSize() {
		logger.info("Player " + model.getActivePlayer().getPlayerNumber()+ " hand too large");

		if(model.getActivePlayer().getHand().size() > 12) {
			
			return false;
			}
		return true;
	}
	
	/* Check if the player can End their turn 
	 * 
	 * 
	 */
	public boolean canEndTurn() {
		logger.info("canEndTurn() called");
		
		// check out if they can end a turn
		boolean isHarder = stageHarder();
		boolean foeInEachStage = isfoeEachStage();
		boolean isHandSizeOk = checkHandSize();

		// check if its a sponsor and if it is then check if they sponsor correctly 
		if(model.getActivePlayer().isSponsor && !foeInEachStage){	    			
			model.control.alert("Foe not present in every stage.");
			return false;
		//check if its a sponsor and if the stages are Harder
		} else if(model.getActivePlayer().isSponsor && !isHarder) {
			model.control.alert("The stages are not progressively harder");
			return false;
			
		// if their hand size id ok
		} else if(!isHandSizeOk){	
			model.control.alert("Your hand size is to big with "+ model.getActivePlayer().getHand().size());
			return false;
			
		// let start the quest baby
		}else if (model.getActivePlayer().isSponsor)  {

			numberOfCardsToReturn = numberOfCardsForSponsor();
			model.control.stagesSet();
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

		int numStages = model.numStages;

		
		boolean inNextQ = false;
		if(inNextQ) {
			
			for (int i = 0; i <model.numPlayers; i++) {
				if(!players[i].isSponsor){

				this.players[i].addShields(2);
			}
			inNextQ = false;
			}
		}

		int numShields = ((QuestCard) model.currentStoryCard).getNumStages();
		logger.info("Number of Stages: " + numShields);

		//TODO ADD THE BOOLEAN SETTING FOR PASSING QUEST HERE
		for (int i = 0; i < model.numPlayers; ++i){
			if(players[i].isSponsor) {
				logger.info("Sponsor is getting "+ numberOfCardsToReturn);
				for(int j = 0 ; j< numberOfCardsToReturn; j ++) {
	
					
					players[j].addToHand(model.getAdventureDeck().pop());
				}
			}
			if(!players[i].isSponsor){

				

				if(players[i].passedQuest) {
					players[i].addShields(numShields);
					for(int j=0;j<numStages;j++) {
						AdventureCard c = model.getAdventureDeck().pop();
						this.players[i].addToHand(c);
						model.getAdventureDeckDiscard().add(c);
					}
				}

			}
		}
		

		
		model.control.resolveQuest();
		
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

		
		CardCollection<AdventureCard> currStage = model.stage.getStageAt(model.stage.getCurrentStage());
		
		int stageBP = 0;

		for (int i = 0; i < currStage.size(); ++i){
			stageBP += ((AdventureCard)currStage.get(i)).getBattlePoints();
		}
		players = model.getPlayers();
		for(int i = 0; i < model.numPlayers; ++i){
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
				if(model.state.currentStage +1==((QuestCard)model.state.currentStoryCard).getNumStages() ) {
					
					players[i].passedQuest =true;

				
					Card c = model.getAdventureDeck().pop();
					this.players[i].addToHand(c);
					model.getAdventureDeckDiscard().add(c);
				}
			
		//
				
		}else {players[i].isQuesting = false;}
			
		}
		if(model.stage.getCurrentStage()+1== ((QuestCard) model.currentStoryCard).getNumStages()){
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
			
			
			
			
			model.isDoneQuestingMode = true;
			

			resolveQuest();

		}
	


	}


	




	

}
