package core;

public class Control{

	Model model;
	View view;
	
	private static String testString;
	
	public Control(View view) {
		
		this.view = view;
		
		gameInit(null);
		

		//TEST
  
		model.CardsTest();
		
		//END TEST
	}
	


	public void gameInit(String args []){
		
		int numPlayers;
		
		if(args == null || args.length == 0){
			numPlayers = 4;
		}
		else{
			numPlayers = Integer.parseInt(args[0]);
		}
		if(numPlayers >= 2 && numPlayers <= 4)
		{

			model = new Model(this);
		}
		else{// Maybe make a view constructor that displays invalid number of players type thing...??????????????
			 // POP UP MESSAGE????? from control?
			System.out.println("Number of players invalid ");
		}
		
		model.instantiatePlayers(numPlayers);
		
		model.instantiateStages(5); //TODO set properly
		
//		model.initialShuffle(); //COMMENT OUT FOR SET SCENEARIOS

//		model.deal(); 			//COMMENT OUT FOR SET SCENEARIOS
		
		model.setScenario1();	//UNCOMMENT FOR SCEN 1
		
//		model.setScenario2();	//UNCOMMENT FOR SCEN 2
		
	}

	public void mainLoop(){
		
		boolean win = false;
		
		
		while(!win){
			
			model.playGame();
			
			win = !win;
			
		}
	}
	
	public void updateViewState(){
		
		view.updateState();
		
	}

	public boolean getSponsorDecision(){
		return view.popup("Would you like to sponsor this quest?");
	}
	
	public State getState(){
		return model.getState();
		
	}
	
	public void viewerChanged(){
		model.viewerChanged();
	}
	
	public void setNumPlayers(int i){
		model.numPlayers = i;
	}
	
	public void printTestString(){System.out.println(testString);}

	public void handClick(String clickType, String ID) {
		if(clickType.equals(View.PARTY)){
			model.party(ID);
		} else if (clickType.equals(View.STAGE)) {
			model.stage(ID);
		} else if (clickType.equals(View.QUEUE)) {
			model.queue(ID);
		} else if (clickType.equals(View.DEQUEUE)) {
			model.dequeue(ID);
		}else if(clickType.equals(View.DISCARD)){
			model.discard(ID);
		}
	}
	
	public void buttonClick(String clickType) {
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
		
		return model.getSubType(ID, currentPlayer);
		
		
	}

	public void stagesSet(){
		model.stagesSet();
	}
	
	public void alert(String message){
		view.alert(message);
	}
	
}
