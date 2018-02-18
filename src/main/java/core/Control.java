package core;

public class Control {

	static Model model;
	static View view;
	
	// args[0]: number of players
	public static void main(String args []){
	
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
			model = new Model(numPlayers);
		}
		else{// Maybe make a view constructor that displays invalid number of players type thing...??????????????
			 // POP UP MESSAGE????? from control?
			System.out.println("Number of players invalid ");
		}
		
		model.initialShuffle();
		
		model.deal();

		
		////TEST
		
		model.CardsTest();
		
		////END TEST
	}
}
