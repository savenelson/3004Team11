package core;


import org.apache.logging.log4j.LogManager;
import java.util.*;
import org.apache.logging.log4j.Logger;

public class QuestingStage {
	private static final Logger logger = LogManager.getLogger(QuestingStage.class);
	
	
	ArrayList<CardCollection<AdventureCard>>  stages;
	boolean stageResolved = false;
	boolean toggleForStages = false;
	int stagePlaceHolder = 0;
	static int stageOverCount = 0;
	int currentStage = 0;
	
	
	
	public QuestingStage() {
		
	stages = new ArrayList<CardCollection<AdventureCard>> ();
	
	for (int i = 0; i < 5; i++) {
		stages.add(new CardCollection<AdventureCard>());
		
	} 
		
		
		currentStage = 0;
	}
	
	
	public void resetCurrentStage(){
		logger.info("resetCurrentStage() called");
		
		this.currentStage = 0;

	}
	
	public void setCurrentStage(int num) {
		logger.debug("setCurrentStage(" + num + ") called");

		this.currentStage = num;
		
	}
	
	public CardCollection<AdventureCard> getStageAt(int stageNum) {
		
		
		return stages.get(stageNum);
		
		
		
	}
	
	public ArrayList<CardCollection<AdventureCard>>  getStage() {
		return stages;
	}
	
	public void nextStage() {
		currentStage++;
	}
	
	public int getCurrentStage() {
		
		return currentStage;
	}
	

}
