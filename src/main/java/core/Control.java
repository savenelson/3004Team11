package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;




public class Control{
	
	private static final Logger logger = LogManager.getLogger(Control.class);

	Model model;
	View view;
	
	private static String testString;
	
	public Control(View view) {
		
		this.view = view;
		gameInit(null);

		//TEST
		//model.CardsTest();
		//END TEST
	}
	


	public void gameInit(String args []){
		
		logger.info("game initialized");
		
		int numPlayers;

		if(args == null || args.length == 0){
			numPlayers = 4;
		} else {
			numPlayers = Integer.parseInt(args[0]);
		}
		
		if(numPlayers >= 2 && numPlayers <= 4)
		{

			model = new Model(this);
			logger.info("passing numPlayers = " + numPlayers + " to model");
			
		} else {
			logger.fatal("number of players");
		}
		
		model.instantiatePlayers(numPlayers);
		
		model.instantiateStages(); //TODO set properly
		
//		model.initialShuffle(); //COMMENT OUT FOR SET SCENEARIOS

//		model.deal(); 			//COMMENT OUT FOR SET SCENEARIOS
		
//		model.setScenario1();	//UNCOMMENT FOR SCEN 1
		
//		model.setScenario2();	//UNCOMMENT FOR SCEN 2
		
		model.setScenarioTest();
		
	}

	public void mainLoop(){
		logger.info("mainLoop() running");

		boolean win = false;
		while(!win){
			model.playGame();
			win = !win;
		}
	}
	
	public void updateViewState(){
		logger.info("updateViewState() called");

		view.updateState();
		
	}
	
	public void stageIncrement(){
		logger.info("stageIncrement() called");

		model.state.currentStage = model.state.stagePlaceHolder;
		model.state.toggleForStages = false;
		model.resolveStage();
	}
	
	public void stageOver(){
		logger.info("stageOver() called");

		model.stageOver();
	}

	public boolean getSponsorDecision(){
		logger.info("getSponsorDecision() called");

		return view.popup("Would you like to sponsor this quest?");
	}
	
	public State getState(){
		logger.info("getState() called");

		return model.getState();
		
	}
	
	public void viewerChanged(){
		logger.info("viewerChanged() called");

		model.viewerChanged();
	}
	
	public void setNumPlayers(int i){
		logger.info("setNumPlayers() called");

		model.numPlayers = i;
	}
	
	public Player getActivePlayer(){
		logger.info("getActivePlayer() called");

		return model.getActivePlayer();
	}
	
	public void printTestString(){System.out.println(testString);}

	public void handClick(String clickType, String ID) {
		logger.info("handClick() called");

		if(clickType.equals(View.PARTY)){
			model.party(ID);
		} 
		else if (clickType.equals(View.STAGE)) {
			model.stage(ID);
		} 
		else if (clickType.equals(View.QUEUE)) {
			model.queue(ID);
		} 
		else if (clickType.equals(View.DEQUEUE)) {
			model.dequeue(ID);
		}
		else if(clickType.equals(View.DISCARD)){
			model.discard(ID);
		}
	}
	
	public void startStageCycle(){
		logger.info("startStageCycle() called");

		model.resetCurrentStage();
	}

	public void nextStory(){
		logger.info("nextStory() called");

		model.nextStory();
	}
	
	public void resolveStage(){
		logger.info("resolveStage() called");

		model.resolveStage();
	}
	
	public void buttonClick(String clickType) {
		logger.info("buttonClick() called");

		if(clickType.equals(View.STAGE1)){
			model.setCurrentStage(0);
		} else if (clickType.equals(View.STAGE2)) {
			model.setCurrentStage(1);
		} else if (clickType.equals(View.STAGE3)) {
			model.setCurrentStage(2);
		} else if (clickType.equals(View.STAGE4)) {
			model.setCurrentStage(3);
		} else if (clickType.equals(View.STAGE5)) {
			model.setCurrentStage(4);
		} else if(clickType.equals(View.ENDTURN)){
		
			model.endTurn();
		}
	}
	
	public String getSubType(String ID, int currentPlayer){
		logger.info("getSubType() called");

		return model.getSubType(ID, currentPlayer);
	}

	public void resolveQuest(){
		logger.info("resolveQuest() called");

		view.resolveQuest();
	}
	
	public void stagesSet(){
		logger.info("stagesSet() called");

		startStageCycle();

		model.stagesSet();
	}
	
	public void alert(String message){
		logger.info("alert() called");

		view.alert(message);
	}



	public View getView() {
		logger.info("getView() called");

		return view;
	}
	
}
