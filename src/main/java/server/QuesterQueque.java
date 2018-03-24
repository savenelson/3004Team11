package server;

import java.util.*;

public class QuesterQueque {
	Player[] players; 
	ArrayDeque<Integer> currentQuesters;
	
	ArrayDeque<Integer> oldQuesters;
	
	int numberOfTurns= 0;
	
	
	public QuesterQueque() {
		currentQuesters = new ArrayDeque<Integer>();
		oldQuesters = new ArrayDeque<Integer>();
		
	}
	
	public void add(int playerID) {
		currentQuesters.addLast(playerID);
		oldQuesters.addLast(playerID);
	}
	
	public boolean isEmpty() {		
		return currentQuesters.isEmpty();
		
	}
	public int nextPlayer() {
		
		int next = currentQuesters.getFirst();
		int lastPlayer = currentQuesters.pop();
		currentQuesters.addLast(lastPlayer);
		
		numberOfTurns++;
		
		
		return next;
	}
	
	
	public int size() {
		return currentQuesters.size();
	
	}
	
	
	public void survivorsLeft(Player players[]) {
		this.players = players;
		numberOfTurns = 0;
		 
		 oldQuesters = new ArrayDeque<Integer>(currentQuesters);
		 
		 currentQuesters.clear();
		 
		for(int  quester : oldQuesters) {
			Player player = this.players[quester];
			if(player.isQuesting) {
			currentQuesters.addLast(player.getPlayerNumber());
			}
			
		}
		
	}
	
	
	public void clear() {
		
		currentQuesters.clear();
	
	
	
	
	}
	
	


}

