package core;

import java.util.ArrayList;

public abstract class CardCollection {
	
	public static final int DISTICT_ADV_CARDS = 32;

	protected ArrayList<Card> cards;
	
	public Card get(int i){return cards.get(i);}
	
	public Card pop(){return cards.get(0);}
	
	// returns cards array size after adding 
	public int add(Card c){cards.add(c); return cards.size();}
	
	public int size(){return cards.size();}
	
	protected CardCollection(){
		cards = new ArrayList<Card>();
	}
	
}
