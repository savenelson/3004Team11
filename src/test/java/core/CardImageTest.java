package core;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

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
		
		//GET ALL CARD GIF FILES
		File folder = new File(PATH);
		
		File[] listOfFiles = folder.listFiles();
		
		ArrayList<String> strArr = new ArrayList<String>();

	    for (int i = 0; i < listOfFiles.length; i++) {
	        if (listOfFiles[i].isFile()) {
	        	//System.out.println(listOfFiles[i].getName());
	        	strArr.add(listOfFiles[i].getName());
	        }
	      }
		
		
		File image;
		
		//ADVENTURE CARDS
		for (int i = 0; i < advDeck.size(); ++i){
			
			String s = PATH + advDeck.get(i).getImgName() + GIF;
			
			System.out.println(s);
			
			image = new File(s);
			
			System.out.println("Image.getName(): " + image.getName());
			
			boolean inList = false;
			
			for(int j = 0; j < strArr.size(); ++j){
				if(strArr.get(j).equals(image.getName())){
					inList = true;
					break;
				}
			}
			
			assertEquals(inList, true);
		}
		
		//STORY CARDS
		for (int i = 0; i < stoDeck.size(); ++i){
			
			String s = PATH + stoDeck.get(i).getImgName() + GIF;
			
			System.out.println(s);
			
			image = new File(s);
			
			System.out.println("Image.getName(): " + image.getName());
			
			boolean inList = false;
			
			for(int j = 0; j < strArr.size(); ++j){
				if(strArr.get(j).equals(image.getName())){
					inList = true;
					break;
				}
			}
			
			assertEquals(inList, true);
		}
		
		//RANK CARDS
		String s1 = PATH + RankCard.RANK + RankCard.SQUIRE + GIF;
		String s2 = PATH + RankCard.RANK + RankCard.SQUIRE + GIF;
		String s3 = PATH + RankCard.RANK + RankCard.SQUIRE + GIF;

		String [] rankArr = {s1, s2, s3};
		
		boolean inList = false;
		
		for(int i = 0; i < rankArr.length; ++i){
			
			System.out.println(rankArr[i]);

			image = new File(rankArr[i]);
			
			System.out.println("Image.getName(): " + image.getName());

			for(int j = 0; j < strArr.size(); ++j){
				if(strArr.get(j).equals(image.getName())){
					inList = true;
					break;
				}
			}
			assertEquals(inList, true);
		}		
	}

}
