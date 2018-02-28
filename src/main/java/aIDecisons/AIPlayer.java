package aIDecisons;
import core.Player;
import core.State;
public class AIPlayer  {
	public int currentTurn;
	State state;
	Player player;
	boolean isTurn;
	
	public AIPlayer(State state, int currentPosition, Player player) {
		
		System.out.println("Ai player is created and this is my turn number "+ currentPosition);
		
		isTurn =false;
		this.state = state;
		this.player = player;
		
		
		

		
		
	
		
	}
	
	public void play(){
		
		System.out.println("ready");
		
		
		
	
		
		if(player.getHand().size()>0) {
			for(int i=0; i<5; i++) {
				
			}
				
			
		}
		
			
	}
	
	
	

}
