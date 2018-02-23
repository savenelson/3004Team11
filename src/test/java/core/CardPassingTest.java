package core;

import static org.junit.Assert.*;

import org.junit.Test;

public class CardPassingTest {

	@Test
	public void test() {
		AdventureDeck d = new AdventureDeck();
		
		Player p = new Player(1);
		
		
		Card [] cards= {d.pop(), d.pop(), d.pop()};
		
		p.addToHand(cards[0]);
		p.addToHand(cards[1]);
		p.addToHand(cards[2]);
		
		String s = "";
		
		for(int i = 0; i < 3; ++i){
			s = cards[i].toString();
			assertEquals(s, p.getHand().get(i).toString());
			System.out.println(s);
		}
	}

}
