package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.*;

public class QuestManager implements StoryCardState{
	private static final Logger logger = LogManager.getLogger(QuestManager.class);
	Model  model;
	Player[] players ; 
	
	boolean hasSponsor = false;
	
	boolean questersReady = false;
	
	int numberOfquesters = 0;
	
	
	int numberOfrequests = 0;
	public QuestManager(Model model) {
		this.model = model;
		players = model.getPlayers();
		
		
		
		
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
		}
		
		model.getActivePlayer().declinedToSponsor = true;
		}
		}
		/*
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
		}*/
	}



	public Player nextPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

}
