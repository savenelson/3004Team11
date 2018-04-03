package server;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class QuestingStage {
	private static final Logger logger = LogManager.getLogger(QuestingStage.class);
	
	
	private ArrayList<CardCollection<AdventureCard>>  stages;        		// Used to store all the Card Collection in staging process 

	private int currentStage = 0;
	
	
	/****
	 * Class Purpose to Hold the cards for staging 
	 * 
	 */
	public QuestingStage() {
		
	stages = new ArrayList<CardCollection<AdventureCard>> ();
	
	for (int i = 0; i < 5; i++) {stages.add(new CardCollection<AdventureCard>());} 		
	
	
	}
	
	/****
	 * 
	 * To start from Stage One 
	 * Used after a Sponsor has submit their staging cards 
	 * 
	 */
	public void resetCurrentStage(){
		logger.info("resetCurrentStage() called");
		
		this.currentStage = 0;

	}
	/****
	 * 
	 * Used to switch between the stage
	 * 
	 */
	
	public void setCurrentStage(int num) {
		logger.debug("setCurrentStage(" + num + ") called");

		this.currentStage = num;
		
	}
	/****
	 * 
	 * return the The Cards at a particular stage 
	 * 
	 */
	public CardCollection<AdventureCard> getStageAt(int stageNum) {
		
		return stages.get(stageNum);
			
	}
	
	/****
	 * 
	 * Return all the stages 
	 * 
	 */
	public ArrayList<CardCollection<AdventureCard>>  getStage() {
		return stages;
	}
	
	public void add(AdventureCard Card) {
		getStageAt(currentStage).add(Card);
		
		
	
	}
	/****
	 * 
	 *Move to the next Stage 
	 *	Typically used after a stage has completed 
	 * 
	 */
	
	public void nextStage() {
		logger.info("Moving onwards to the NextStage ");
		currentStage++;
	}
	
	/****
	 * 
	 * Return what the current Stage Numbers
	 *	
	 * 
	 */
	public int getCurrentStage() {return currentStage;	}
	
}
