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
	
}
