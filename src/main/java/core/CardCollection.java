package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CardCollection {
	
	public static final int DISTICT_ADV_CARDS = 32;

	protected ArrayList<Card> cards;
	

	
	public Card get(int i){return cards.get(i);}
	
	public Card pop(){
		Card c = cards.get(0);
		cards.remove(0);
		return c;
	}
	
	// get a card from the deck by name
//	public Card popByID(String ID){
//		int cardIndex = 0;
//		for (int i = 0; i<cards.size();i++) {
//			if(cards.get(i).getID() == ID ) {
//				cardIndex = i;
//				break;
//			}
//		}
//		Card c = cards.get(cardIndex);
//		cards.remove(cardIndex);
//		return c;
//	}
	
	// returns cards array size after adding 
	public int add(Card c){cards.add(c); return cards.size();}
	
	// returns cards array size after adding
	public void addAll(Collection<? extends Card> c){cards.addAll(c);}
	
	public int size(){return cards.size();}
	
	protected CardCollection(){
		cards = new ArrayList<Card>();
	}
	
	public void shuffle(){
		Collections.shuffle(cards);
	}
	
	public Card getByID(String ID){
		
		for (int i = 0; i < cards.size(); ++i){
			if(ID.equals(cards.get(i).getID())){
				return cards.get(i);
			}
		}
		return null;
	}
	
	public void remove(String ID){
		for (int i = 0; i < cards.size(); ++i){
			if(ID.equals(cards.get(i).getID())){
				cards.remove(i);
			}
		}	
		
	}
	public void remove(Card c){
		for (int i = 0; i < cards.size(); ++i){
			if(c.equals(cards.get(i))){
				cards.remove(i);
			}
		}	
		
	}
	public void remove(int index){
		cards.remove(index);
	}
	
	public String toString(){
		
		String s = "";
		
		for (int i = 0; i < cards.size(); ++i){
			s += cards.get(i).toString();
			s += "\n";
		}
		
		return s;
	}
	
	public int getHighestFoecard() {
		
		int indexOfhighestFoe =-1;
		
		FoeCard highestFoeCard = null;
		
		for(int i=0; i<cards.size(); i++) {
			boolean isFoe = ((AdventureCard) cards.get(i)).getSubType().equals("Foe");
			if(isFoe){
				if(highestFoeCard==null) {
					highestFoeCard = (FoeCard) cards.get(i);
					indexOfhighestFoe = i;
					
				}
				
				else if(highestFoeCard.getBattlePoints()<((FoeCard) cards.get(i)).getBattlePoints()){
					highestFoeCard = (FoeCard) cards.get(i);
					indexOfhighestFoe = i;
					
				}
			
				
				
			}
		}
		return indexOfhighestFoe;
				
		
		
	}
	
	public int getTestCard() {
		
		int indexOfTestCard = -1;
		
		for(int i=0; i<cards.size(); i++) {
			boolean isTest = ((AdventureCard) cards.get(i)).getSubType().equals("Test");
			if(isTest) {
				indexOfTestCard = i;
			}
		}
		
		
		
		
		
		
		
		return indexOfTestCard;
		
		
	}
	


public int getFoecardsLessthan(int bp) {
	
	int indexOfFoe =-1;
	
	FoeCard Foe = null;
	
	for(int i=0; i<cards.size(); i++) {
		if (((AdventureCard) cards.get(i)).getSubType().equals("FOE")) {
			 Foe = ((FoeCard) cards.get(i));
			if(Foe.getBattlePoints() < bp){
				
				return i;
			}
			}
		}
		
		
	return indexOfFoe;
			
	
	
}

public int getNumberOfFoecardsLessthan(int bp) {
//	int numberOfFoes = 0;
	
	int counter =0;
//	int indexOfFoe =-1;
	
	FoeCard Foe = null;
	
	for(int i=0; i<cards.size(); i++) {
		if (((AdventureCard) cards.get(i)).getSubType().equals("Foe")) {
			 Foe = ((FoeCard) cards.get(i));
			if(Foe.getBattlePoints() < bp){
				counter++;

			}
		}
	}

	return counter;
	
}

public int getDupilicates() {
	

	int indexOfdupilcate=-1;
	
	AdventureCard card1 = null;
	
	AdventureCard card2 = null;
	
	for(int i=0; i<cards.size()-1; i++) {
		card1 = (AdventureCard) cards.get(i);
		for(int j=cards.size(); j!=i; j--) {
			card2 = (AdventureCard) cards.get(j);
			
			if(card1.toString().equals(card2.toString())) {
				indexOfdupilcate = j;
				return indexOfdupilcate;
			}
					
				
			
			
		}
	}
	return indexOfdupilcate;
}

public int getNumberOfAllies() {
	int numberOfAllies =  0;
	
	for(int i=0; i<cards.size(); i++) {
		if (((AdventureCard) cards.get(i)).getSubType().equals("Ally")) {
			
				numberOfAllies++;
				
				
				
			
			
		}
	}
	
	System.out.println(numberOfAllies);
	return numberOfAllies;
	
}
public int getnumberOfWeapons() {
	int numberOfWeapon =  0;
	
	for(int i=0; i<cards.size(); i++) {
		if (((AdventureCard) cards.get(i)).getSubType().equals("Weapon")) {
			
				numberOfWeapon++;
				
			
			
		}
	}
	
	
	return numberOfWeapon;
	
}
}
