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
import client.ClientModel;

public class QuestManager implements StoryCardState{
	private static final Logger logger = LogManager.getLogger(QuestManager.class);
	private static final String ENDTURN = "End turn";
	ClientModel  clientModel;
	Player[] players ; 
	
	private boolean hasSponsor = false;
	
	private boolean questersReady = false;
	
	private int numOfansewers;
	
	private int numberOfCardsToReturn= 0;
	
	private int numberOfrequests = 0;
	
	private QuesterQueque questers ; 
	
	private int numOfQuesterPotential;
	

	int numOfQuester = 0;
	
	int numOfRepsonders = 0 ;
	
	
	int nextPersonToDraw= 0;
	
	
	public QuestManager(ClientModel clientModel) {
		this.clientModel = clientModel;
		players = clientModel.getPlayers();
		
		questers = new QuesterQueque();	
		
		
	}
	
	
	
//	public void handle(ClientModel clientModel) {
//		
//		
//		numOfQuesterPotential = clientModel.getNumPlayers() -1;
//		
//		// if I do not have a sponsor ask the person if they want to sponsor 
//		if(!hasSponsor) {
//			
//		// if I haven't ask to sponsor yet then ask
//		if(!clientModel.getActivePlayer().declinedToSponsor) {
//		boolean wantToSponsor;
//		clientModel.server.getSponsorDecision();
//		}
//		}
//	/*	
//		if(numberOfrequests == 0 ) { nextPersonToDraw = model.getActivePlayer().getPlayerNumber() +1; 
//		if(nextPersonToDraw> model.getPlayers().length){nextPersonToDraw = 0;}
//		}
//		
//		numberOfrequests++;
//		
//		// if they do want to sponsor then make them the sponsors
//		if(wantToSponsor) {
//			logger.info("Found a sponsor ");
//			hasSponsor = true;
//			
//			model.getActivePlayer().isSponsor = true;
//			numberOfrequests = 0;
//			
//			wantToSponsor = false;
//			
//			model.control.updateViewState();
//			
//		}
//		// changed that they have asked 
//		model.getActivePlayer().declinedToSponsor = true;
//		
//		}else {// I have went around all the players and no want to sponsor 
//			//go to next story Cards}
//			logger.info(" No one wanted to Sponsor lets go to the NEXT STORY GUY");
//		}
//	}
//		
//		
//		// Questers asking
//		// if I have a sponsor and the quester are not ready then ask the current player
//		if(hasSponsor && !questersReady) {
//			// if I have a sponsor and the quester are not ready then ask the current plater
//			if(!this.model.getActivePlayer().declinedQuesting && !this.model.getActivePlayer().isSponsor) {
//				boolean isQuesting = model.control.getQuestingDecision();
//				
//				
//				if(isQuesting) {
//					logger.info("THe Player has decidied to quest ");
//					model.getActivePlayer().isQuesting = true;
//					questers.add(model.getActivePlayer().getPlayerNumber());
//					
//				}
//				//they have answered
//				this.model.getActivePlayer().declinedQuesting = true;
//				
//				numberOfrequests++;
//				
//			}
//			
//			
//		}if(numOfQuesterPotential == numberOfrequests ) {
//			// I return to the sponor 
//			if(questers.isEmpty()) {
//				numberOfrequests = 0;
//				
//				logger.info("I have no  any questers ");
//				// should go to the next story hard 
//			}else {
//				// The questers are ready adn we are ready to begin questing
//				
//				//logger.info("I do have some questers. Let us begin our adventures ");
//				questersReady = true;
//				logger.info("I do have some questers. Let us begin our adventures ");
//				
//				numberOfrequests = 0 ;
//				
//				
//			}
//			
//		}
//		if(questersReady ) {
//			//begins the stage
//			logger.info("Done  hh"+ numOfRepsonders);
//			
//			numOfQuester = questers.size();
//			if(numOfQuester==numOfRepsonders) {
//				//all the questers made their choice time to resolve stage;
//				logger.info("Done  hh"+ numOfRepsonders);
//				
//				
//				
//				
//			}else {
//				logger.info("Current "+ numOfRepsonders);
//				
//			}
//			
//			
//		}*/
//	
//	}
	
		
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
			
		if(clientModel.getActivePlayer().isSponsor) { clientModel.resetCurrentStage();}				//making sure the Stage starts at 1
		
		if (!questersReady) {clientModel.nextPlayer();}          								//Move onto the nest player still asking people
	
		else if(clientModel.isDoneQuestingMode()) {clientModel.setNextPlayer(nextPersonToDraw);}  						// are we done with Questing
		
		else if (questersReady) {																		//Currently we are questing 
		
			clientModel.setNextPlayer(questers.nextPlayer());													//set the NextPlayer
			
			if(numOfRepsonders  >questers.size()) {														// every Quester Made their turn 
					
				numOfRepsonders = 0; 																	
				this.resolveStage();																		// resolve the Stage 
				clientModel.control.view.stageResolved();														//resolve the stage View 
				this.questers.survivorsLeft(clientModel.getPlayers());											//see the Quester who is left 
			}
		}
	}
	
	/* The purpose is that  to see If the stage are progressively harder
	 * 
	 * 
	 */
	
	private boolean stageHarder() {
		logger.debug("stageHarder() called");

		int numStages = ((QuestCard) clientModel.getCurrentStoryCard()).getNumStages(); 
		if (numStages == 1) { return true;
		}else {
			for(int i =0; i<numStages-1; i++) {
				
				if(totalNumOfBP(clientModel.getStage().getStageAt(i))>=totalNumOfBP(clientModel.getStage().getStageAt(i+1))) {
					logger.info("stages are not harder");
					return false;
				}
			}	
		}
		return true;
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
		
	
	  	if(clientModel.getActivePlayer().isSponsor){
	    		int numStages = ((QuestCard)clientModel.getCurrentStoryCard()).getNumStages();
	    		foesPresent  = new boolean [numStages];
	    		for (int i = 0; i < numStages; ++i){
	    				foesPresent[i] = false;
			}		    		
	    		for (int i = 0; i < numStages; ++i){
	    			
	    			for (int j = 0; j < clientModel.getStage().getStageAt(i).size(); ++j){
	    				if(( clientModel.getStage().getStageAt(i).get(j)).getSubType().equals(AdventureCard.FOE)){
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
		int numStages = ((QuestCard)clientModel.getCurrentStoryCard()).getNumStages();
		for (int i = 0; i < numStages; ++i){
			for (int j = 0; j < clientModel.getStage().getStageAt(i).size(); ++j){
				if((clientModel.getStage().getStageAt(i).get(j)).getSubType().equals(AdventureCard.FOE)){
					numOfCardsInStaging++;
				}else if(( clientModel.getStage().getStageAt(i).get(j)).getSubType().equals(AdventureCard.WEAPON)){
					numOfCardsInStaging++;
				}else if((clientModel.getStage().getStageAt(i).get(j)).getSubType().equals(AdventureCard.TEST)){
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
		logger.info("Player " + clientModel.getActivePlayer().getPlayerNumber()+ " hand too large");

		if(clientModel.getActivePlayer().getHand().size() > 12) {
			
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
//		boolean isThereTwoWeapons = containsSameWeapon();
		
		// check if its a sponsor and if it is then check if they sponsor correctly 
		if(clientModel.getActivePlayer().isSponsor && !foeInEachStage){	    			
			clientModel.control.alert("Foe not present in every stage.");
			return false;
		//check if its a sponsor and if the stages are Harder
		} else if(clientModel.getActivePlayer().isSponsor && !isHarder) {
			clientModel.control.alert("The stages are not progressively harder");
			return false;
			
		// if their hand size id ok
		} else if(!isHandSizeOk){	
			clientModel.control.alert("Your hand size is to big with "+ clientModel.getActivePlayer().getHand().size());
			return false;
			
		// let start the quest baby
		}else if (clientModel.getActivePlayer().isSponsor)  {

			numberOfCardsToReturn = numberOfCardsForSponsor();
			clientModel.control.stagesSet();
			return true;
			
		// The person was a quester its true 
		}else {
			
			return true;
		}
		
	
		//if nothing makes this true, return false
	
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
/*
		int numStages, numShields = ((QuestCard) clientModel.currentStoryCard).getNumStages();

		
		boolean inNextQ = false;
		if(inNextQ) {
			
			for (int i = 0; i <clientModel.getPlayers().length; i++) {
				if(!players[i].isSponsor){

				this.players[i].addShields(2);
			}
			inNextQ = false;
			}
		}


		logger.info("Number of Stages: " + numShields);*/

		//TODO ADD THE BOOLEAN SETTING FOR PASSING QUEST HERE
		
		for (int i = 0; i < clientModel.getPlayers().length; ++i){
			if(players[i].isSponsor) {
				logger.info("Sponsor is getting "+ numberOfCardsToReturn);
				for(int j = 0 ; j< numberOfCardsToReturn; j ++) {
	
					
					//players[j].addToHand(clientModel.getAdventureDeck().pop());
				}
			}	
			

			if(!players[i].isSponsor){

				

				//if(players[i].passedQuest) {
//				//	players[i].addShields(numShields);
				//	for(int j=0;j<numStages;j++) {
						//AdventureCard c = clientModel.getAdventureDeck().pop();
						//this.players[i].addToHand(c);
						//clientModel.getAdventureDeckDiscard().add(c);
					
				//}

			}
			
		}
	
		clientModel.setDoneQuestingMode(true);
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
	CardCollection<AdventureCard> currStage = clientModel.getStage().getStageAt(clientModel.getStage().getCurrentStage());
		
		clientModel.allysInPlay();
		logger.info("RESOLVING STAGE");
		
		int stageBP = 0;
		
		for (int i = 0; i < currStage.size(); ++i){
			logger.info(currStage.size());
			stageBP += currStage.get(i).getBattlePoints();
		}
		// clientModel.allysInPlay();
		logger.info("STAGES POINTS "+stageBP);
		players = clientModel.getPlayers();
		for(int i = 0; i < clientModel.getPlayers().length; ++i){
			logger.info("Player " + i + "'s QUEUE BPs = " + clientModel.getPlayers()[i].getBattlePoint());
			logger.info("Player " + i + "'s PARTY BPs = " + clientModel.getPlayers()[i].getPartyBattlesPoint());
			logger.info("Player " + i + "'s BONUS BPs = " + clientModel.getPlayers()[i].getAllyBonusBattlePoints());
		
				int playerBP = clientModel.getPlayers()[i].getBattlePoint();
				playerBP += clientModel.getPlayers()[i].getPartyBattlesPoint();
				playerBP += clientModel.getPlayers()[i].getAllyBonusBattlePoints();
				
				logger.info( "Player ally battle points "+clientModel.getPlayers()[i].getPartyBattlesPoint());
				if (playerBP>=stageBP && clientModel.getPlayers()[i].isQuesting) {
					players[i].passedStage = true;
					if(clientModel.getState().currentStage+1 ==((QuestCard)clientModel.getState().currentStoryCard).getNumStages()) {
						players[i].isQuesting = true;
					}
					
					
				
					}else {
						
						players[i].passedStage = false;
						players[i].isQuesting = false;
						
					}
				
					
				}
	
		if(clientModel.getState().currentStage+1 ==((QuestCard)clientModel.getState().currentStoryCard).getNumStages() ) {
			
			resolveQuest();
		
			
		
		}

		//
				

			
		/*
		if(clientModel.getStage().getCurrentStage()+1== ((QuestCard) clientModel.currentStoryCard).getNumStages()){
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
			
			
			
			
			clientModel.setDoneQuestingMode(true);
			

			resolveQuest();

		}*/
	}
	



	@Override
	public void handle() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void setHasSponsor(boolean b) {
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


	




	

}
