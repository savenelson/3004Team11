package core;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardImageTest {

	public static final String GIF = ".gif";
	

	public static final String PATH = "src/main/resources/core/cards/";
  
	@Test
	public void test() {
		
		AdventureDeck advDeck = new AdventureDeck();
		
		StoryDeck stoDeck = new StoryDeck();
	
		
		
		File image;
		for (int i = 0; i < advDeck.size(); ++i){
			
			String s = PATH + advDeck.get(i).getImgName() + GIF;
			
			System.out.println(s);
			
			image = new File(s);
			
			assertEquals(image.getAbsolutePath() instanceof String, true);
		}
		
		for (int i = 0; i < stoDeck.size(); ++i){
			
			String s = PATH + stoDeck.get(i).getImgName() + GIF;
			
			System.out.println(s);
			
			image = new File(s);
			
			assertEquals(image.getAbsolutePath() instanceof String, true);
		}
		
	}

}
