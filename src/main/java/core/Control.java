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
		
		model.initialShuffle();
		
		model.deal();
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

}
