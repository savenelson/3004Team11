package core;

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
	
	
	public void survivorsLeft() {
		numberOfTurns = 0;
		 int numOfCurrentQuesters = size();
		 oldQuesters = new ArrayDeque<Integer>(currentQuesters);
		 
		for(int i =0; i< numOfCurrentQuesters ; i++) {
			Player player = this.players[currentQuesters.pop()];
			if(player.isQuesting) {
			currentQuesters.addLast(player.getPlayerNumber());
			}
			
		}
		
	}
	
	
	public boolean isDone() {
		
		
		return numberOfTurns == currentQuesters.size();
		
	}
	

}

