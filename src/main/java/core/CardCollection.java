package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public abstract class CardCollection {
	
	public static final int DISTICT_ADV_CARDS = 32;

	protected ArrayList<Card> cards;
	
	public Card get(int i){return cards.get(i);}
	
	public Card pop(){return cards.get(0);}
	
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
	
	public String toString(){
		
		String s = "";
		
		for (int i = 0; i < cards.size(); ++i){
			s += cards.get(i).toString();
			s += "\n";
		}
		
		return s;
	}
	
}
