package aIDecisons;

import server.*;

import logger

public class Strategy1DoISponsorAQuest implements DoISponsorAQuest {
	
	State state;
	
	Player player;
	
	@Override
	public  boolean decide() {
		
		for (Player aplayer: state.players) {
			if(aplayer.canPromote(((TournamentCard) (state.currentStoryCard)).getNumShields()) && state.players[state.currentPlayer] != player) {
				System.out.println("I do not want to sponsor  because someone else can win ");
			
				return false;
				
			}
			
			//TODO need to check if the number of foes card and if its bigger 
			//else if()
			
			else {
				System.out.println("I decline sponsoring");
			}
			
			
			
		}
		return false;
	}

}
