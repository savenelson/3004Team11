package aIDecisons;

import server.*;



public class Strategy1doIParticipateInQuest implements DoIParticipateInQuest {
	Player player;
	
	State state; 
	
	Model model;
	
	
	
	
	/**
	 * Decision to participate  into a Quest
	 *    Condition 1 I have 2 weapons and allies per stage 
	 *    
	 *    Condition 2 :  I have at least 2 foes of less and 20 points (to discard for a test )
	 *    
	 *    If  Conditon1 and Conditon 2 then PArtciapte to play 1
	 *    
	 *    else do not participate 
	 *    
	 */

	@Override
	public void move() {
		
		int numberOfStages = ((QuestCard) state.currentStoryCard).getNumStages();
		
		int numberOfAlly = player.getHand().getNumberOfAllies() + player.getParty().size();
		int numberOfAllyHand = player.getHand().
		
		int numberOfWeakFoe = player.getHand().getFoecardsLessthan(20);
		
		int numberOfWeapon = player.getHand().getnumberOfWeapons();
		
		
		int result = numberOfStages - numberOfAlly - (numberOfWeapon/2)  ;
		
		//if the results is less then 0 and i have o weak cards then participate
		if (result<0 && numberOfWeakFoe>=2) {
	
		// Say that I want to participate 	
		
			
		}else {
			//decline
		}
		
		
		
	}
	
	/**
	 *  	The implementation of Strategy 1 playing a move :
	 *  
	 *  if current Stage is a test then 
	 *   
	 *    
	 *   else 
	 *    
	 *   
	 *    
	 *    
	 *    
	 */
	
	
	public void play() {
		// if my 
		
		
	}

}
