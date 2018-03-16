package core;

import java.util.*;

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
	
	
	int numberOfrequests = 0;
	
	
	QuesterQueque questers ; 

	
	
	
	public QuestManager(Model model) {
		this.model = model;
		players = model.getPlayers();
		
		questers = new QuesterQueque();
		
		
		
		
	}
	
	
	
	public void handle() {
		logger.info("Handling questing info");
		
		// if I do not have a sponsor ask the person if they want to sponsor 
		if(!hasSponsor) {
			
		// if I haven't ask to sponsor yet then ask
		if(!this.model.getActivePlayer().declinedToSponsor) {
		boolean wantToSponsor= model.control.getSponsorDecision();
		// if they do want to sponsor then make them the sponsors
		if(wantToSponsor) {
			logger.info("Found a sponsor ");
			hasSponsor = true;
			
			model.getActivePlayer().isSponsor = true;
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
			}else {
				// I return to the sponor 
				if(questers.isEmpty()) {
					
					logger.info("I have no  any questers ");
					// should go to the next story hard 
				}else {
					logger.info("I do have some questers. Let us begin our adventures ");
				}
				
			}
			
			
		}
	}



	public void nextPlayer() {
		if(canEndTurn()) {
			model.nextPlayer();
			}
		
	}
	private boolean stageHarder() {
		logger.debug("stageHarder() called");

		int numStages = ((QuestCard) model.currentStoryCard).getNumStages();
		
		
		if (numStages == 1) return true;
		
		else {
			for(int i =0; i<numStages-1; i++) {
				
				if(totalNumOfBP(model.stages[i])>=totalNumOfBP(model.stages[i+1])) {
					logger.info("stages ar not harder");
					return false;
				}
			}	
		}
		return true;
	}
	private int totalNumOfBP(CardCollection stage) {
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
		boolean foeInEachStage = true;
		boolean [] foesPresent = null;
		
	
	  	if(model.getActivePlayer().isSponsor){
    		int numStages = ((QuestCard)model.currentStoryCard).getNumStages();
    		foesPresent  = new boolean [numStages];
    		for (int i = 0; i < numStages; ++i){
    			foesPresent[i] = false;
			}		    		
    		for (int i = 0; i < numStages; ++i){
    			for (int j = 0; j < model.stages[i].size(); ++j){
    				if(((AdventureCard) model.stages[i].get(j)).subType.equals(AdventureCard.FOE)){
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
	
	
	
	
	public boolean canEndTurn() {
		logger.info("canEndTurn() called");
		boolean isHarder = false;
		isHarder = stageHarder();
		
		boolean foeInEachStage = isfoeEachStage();
	

		// check if its a sponsor and if it is then check if they sponsor correctly 
		if(model.getActivePlayer().isSponsor && !foeInEachStage){	    			
			model.control.alert("Foe not present in every stage.");
			return false;
		} else if(model.getActivePlayer().isSponsor && !isHarder) {
			model.control.alert("The stages are not progressively harder");
			return false;
		// if they put the right material then go time and thye can end there turn
		} else if(model.getActivePlayer().isSponsor){	    			
			model.control.stagesSet();
			return true;
			
		}else  {
			return true;
		}

		

			
	}



	public void setPlayer() {
		// TODO Auto-generated method stub
		
	}   	 

}
