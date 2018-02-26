package aIDecisons;

import core.Player;
import core.QuestCard;
import core.State;

public class Strategy1doIParticipateInQuest implements DoIParticipateInQuest {
	Player player;
	
	State state; 
	

	@Override
	public void move() {
		
		int numberOfStages = ((QuestCard) state.currentStoryCard).getNumStages();
		
		int numberOfAlly = player.getHand().getNumberOfAllies() + player.getParty().size();
		
		int numberOfWeakFoe = player.getHand().getFoecardsLessthan(20);
		
		int numberOfWeapon = player.getHand().getnumberOfWeapons();
		
		
		int result = numberOfStages - numberOfAlly - numberOfWeapon/2  ;
		
		//if the results is less then 0 and i have o weak cards then particpate
		if (result<0 && numberOfWeakFoe>=2) {
			
			
			
		}else {
			//decline
		}
		
		
		
	}

}
