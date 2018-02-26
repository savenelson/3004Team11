package aIDecisons;
import core.Player;
import core.State;
import core.TournamentCard;
public class Strategy1DoIParticipateInTournament implements DoIParticipateInTournament {
	Player me ;
	
	
	State state; 


	public boolean decide() {
		
		
		if (this.me.canPromote(((TournamentCard) (state.currentStoryCard)).getNumShields())) {
			System.out.println("I want to partciapte because I can win ");
			
			return true;
			
		}
		
		for (Player aplayer: state.players) {
			if(aplayer.canPromote(((TournamentCard) (state.currentStoryCard)).getNumShields())) {
				System.out.println("I want to partciapte because someone else can win ");
			
				return true;
				
			}
			
			
		}
		
		return false;
	 
		
		
	}
	

}
