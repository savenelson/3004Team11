package core;

import javafx.application.Application;

public class Control{

	static Model model;
	static View view;
	
	private static String testString;

	public static void main(String args []){
	
		Control control = new Control();
		
		testString = "I'm the private test string";
		
		gameInit(args, control);
		
		mainLoop();

		Application.launch(View.class, args);
		
		////TEST
		 	
		//model.CardsTest();
		
		////END TEST
	}
	
	public static void gameInit(String args [], Control control){
		
		int numPlayers;
		
		if(args.length == 0){
			numPlayers = 4;
		}
		else{
			numPlayers = Integer.parseInt(args[0]);
		}
		if(numPlayers >= 2 && numPlayers <= 4)
		{
			//View view = new View(null);

			model = new Model(control);
		}
		else{// Maybe make a view constructor that displays invalid number of players type thing...??????????????
			 // POP UP MESSAGE????? from control?
			System.out.println("Number of players invalid ");
		}
		
		model.instantiatePlayers(numPlayers);
		
		model.initialShuffle();
		
		model.deal();
	}

	public static void mainLoop(){
		
		boolean win = false;
		
		StoryCardAndCurrentPlayer curr = new StoryCardAndCurrentPlayer();
		
		while(!win){
			
			curr = model.drawStoryCard(curr);
			
			System.out.println("Current player: " + curr.currentPlayer.toString() + ", Current story card: " + curr.currentStoryCard.toString());
			
			win = !win;
			
//			int i;
//			for(i = 0; i < model.getPlayers().length; ++i){
//				if(i == 2){
//					win = true;
//					break;
//				}
//				System.out.println(i);
//			}
			
		}
	}
	
	public static class StoryCardAndCurrentPlayer{
		Player currentPlayer;	
		Card currentStoryCard;
	}
	

	
	public void printTestString(){System.out.println(testString);}

}
