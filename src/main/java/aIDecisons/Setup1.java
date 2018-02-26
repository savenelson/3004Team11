package aIDecisons;

import core.FoeCard;
import core.Player;
import core.QuestCard;
import core.State;

public class Setup1 implements SponsorBehaviour {
	
	Player player;
	
	State state;

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
		int numBerstages = ((QuestCard) state.currentStoryCard).getNumStages();
		
		int indexOfHighestFoe = player.getHand().getHighestFoecard();
		
		int indexOfTestCard = player.getHand().getTestCard();
		
		//add 
		
		FoeCard highestFoe = ((FoeCard) player.getHand().get(indexOfHighestFoe));
		
		while(highestFoe.getBattlePoints()<50) {
			// add a weapon card  to the stage 
			
			
		}
		
		
		if(((QuestCard)state.currentStoryCard).getNumStages()==1) {
			//end your term
		}
		
		else if(((QuestCard)state.currentStoryCard).getNumStages()==2) {
			
			if(player.getHand().getTestCard()!=-1){
				player.getHand().get(player.getHand().getTestCard());
				//add test card 
				
			}else {
			//add story card
			}
			
			
		}else if (((QuestCard)state.currentStoryCard).getNumStages()==2) {
				if(player.getHand().getTestCard()!=-1){
					player.getHand().get(player.getHand().getTestCard());
					//add test card 
					
				}else {
				//add story card
				}
				
		
		
		

	}
		}

}
