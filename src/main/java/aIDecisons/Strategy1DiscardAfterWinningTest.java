package aIDecisons;
import 

public class Strategy1DiscardAfterWinningTest implements DiscardAfterWinningTest {
	
	Player player ; 

	@Override
	public void move() {
		
		// while i have a foe card that less then 20 
		while(player.getHand().getFoecardsLessthan(20)!=-1) {
			
			
			
			//discard this card
			player.getHand().remove(player.getHand().getFoecardsLessthan(20));
			
		
		}
s
		
		
		
		
	}

}
