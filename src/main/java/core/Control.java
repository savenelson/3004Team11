package core;

public class Control{

	Model model;
	View view;
	
	private static String testString;
	
	public Control(View view) {
		
		this.view = view;
		
		gameInit(null);
		
		mainLoop();

		//TEST
  
		//model.CardsTest();
		
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
		
		model.initialShuffle();
		
		model.deal();
		
		//model.setScenario1();
		
		//model.setScenario2();
	}

	public void mainLoop(){
		
		boolean win = false;
		
		
		while(!win){
			
			win = !win;
			
		}
	}
	
	public State getState(){
		State state = model.getState();
		return state;
	}
	
	public void printTestString(){System.out.println(testString);}


	public void handClick(String clickType, String ID) {
		if(clickType.equals(View.PLAY)){
			model.play(ID);
		} else if (clickType.equals(View.STAGE)) {
			model.stage(ID);
		} else if (clickType.equals(View.QUEUE)) {
			model.queue(ID);
		} else if (clickType.equals(View.DEQUEUE)) {
			model.dequeue(ID);
		}
		else if(clickType.equals(View.DISCARD)){
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

}
