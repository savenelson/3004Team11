package core;

import static org.junit.Assert.*;

import org.junit.Test;

public class DeckTest {

	@Test
	public void test() {
		AdventureDeck advDeck = new AdventureDeck();
		
		StoryDeck stoDeck = new StoryDeck();
	
		assertEquals(125, advDeck.size());
		
		assertEquals(28, stoDeck.size());

	}

}
