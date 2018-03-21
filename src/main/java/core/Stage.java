package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Stage {
	private static final Logger logger = LogManager.getLogger(Stage.class);
	
	CardCollection [] stages;
	boolean stageResolved = false;
	boolean toggleForStages = false;
	int stagePlaceHolder = 0;
	static int stageOverCount = 0;
	int currentStage = 0;
	
	public Stage() {
	stages = new CardCollection[5];
		
		for(int i = 0; i < 5; ++i){
			stages[i] = new CardCollection();
		}
		
		currentStage = 0;
	}
	
	
	public void resetCurrentStage(){
		logger.info("resetCurrentStage() called");
		

	}
	
	public void setCurrentStage(int num) {
		logger.debug("setCurrentStage(" + num + ") called");

		this.currentStage = num;
		control.updateViewState();
	}
	

}
