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
	
	int numberOfquesters = 0;
	
	
	int numberOfrequests = 0;
	
	
	
	ArrayQueue<Integer> questers;
	
	
	
	public QuestManager(Model model) {
		this.model = model;
		players = model.getPlayers();
		this. questers = new ArrayQueue<Integer>(5);
		
		
		
		
	}
	
	
	
	public void handle() {
		logger.info("Handling questing info");
		// if I do not have a sponsor askk the person if they want to sponsor 
		if(!hasSponsor) {
		if(!this.model.getActivePlayer().declinedToSponsor) {
		boolean wantToSponsor= model.control.getSponsorDecision();
		if(wantToSponsor) {
			logger.info("Found a sponsor ");
			hasSponsor = true;
			
			model.getActivePlayer().isSponsor = true;
			model.control.updateViewState();

			for(int i=0; i<model.numPlayers;i++) {
				players = model.getPlayers();
				if(!players[i].isSponsor) {
					logger.info("adding Player" + players[i].getPlayerNumber());
					this.questers.add(players[i].getPlayerNumber());
				}
			}
			
		}
		
		model.getActivePlayer().declinedToSponsor = true;
		}
		
		
		}
		//if I haveent asked everyone then keep on asking next player
		//if(!questersReady)
		
		if(hasSponsor && !questersReady &&!this.model.getActivePlayer().isSponsor ) {
			if(!this.model.getActivePlayer().declinedQuesting) {
				boolean isQuesting = model.control.getQuestingDecision();
				if(isQuesting) {
					logger.info("THe Player has decidied to quest ");
					model.getActivePlayer().isQuesting = true;
					
					numberOfrequests++;
					
				}
				this.model.getActivePlayer().declinedQuesting = true;
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

}
