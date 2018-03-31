package client;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QuestingStage {
	private static final Logger logger = LogManager.getLogger(QuestingStage.class);
	
	ArrayList<CardCollection<AdventureCard>> stages;
	boolean stageResolved = false;
	boolean toggleForStages = false;
	int stagePlaceHolder = 0;
	static int stageOverCount = 0;
	int currentStage = 0;

	@SuppressWarnings("unchecked")
	public QuestingStage() {
		
		stages = new ArrayList<CardCollection<AdventureCard>>();
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
		logger.debug("getStageAt(int stageNum = " + stageNum + ")");
		if(stages.isEmpty()) {
			logger.debug("Error: stages is null");
			return null;
		} else {
			System.out.println("stageNum: " + stageNum);
			return stages.get(stageNum);
		}
	}
	
	public ArrayList<CardCollection<AdventureCard>> getStage() {
		return stages;
	}
	
	public void nextStage() {
		currentStage++;
	}
	
	public int getCurrentStage() {
		
		return currentStage;
	}
	

}
