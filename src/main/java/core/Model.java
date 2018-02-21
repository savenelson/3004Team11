package core;

import core.Control.StoryCardAndCurrentPlayer;

public class Model {

	public static Control control;
	
	private Player [] players;
	public Player [] getPlayers(){return players;}
	
	private AdventureDeck adventureDeck;
	public AdventureDeck getAdventureDeck(){return this.adventureDeck;}
	
	private AdventureDeck adventureDeckDiscard;
	public AdventureDeck getAdventureDeckDiscard(){return this.adventureDeck;}

	private StoryDeck storyDeck;
	public StoryDeck getStoryDeck(){return storyDeck;}

	private StoryDeck storyDeckDiscard;
	public AdventureDeck getStoryDeckDiscard(){return this.adventureDeck;}
	
	int currentPlayer;

	Model(Control control){
	
		this.control = control;
		
		this.adventureDeck = new AdventureDeck();
		this.storyDeck = new StoryDeck();
		
		this.adventureDeckDiscard = new AdventureDeck();
		this.storyDeckDiscard = new StoryDeck();
		
		currentPlayer = 0;
	}
	
	public void instantiatePlayers(int numPlayers){
		players = new Player[numPlayers];
		
		for(int i = 0; i < numPlayers; ++i){
			players[i] = new Player(i+1);
		}
	}
	
	public void initialShuffle(){
		
		this.adventureDeck.shuffle();
		
		this.storyDeck.shuffle();
	}
	
	public void deal(){
		
			            // 12 cards in hand
		for(int i = 0; i < 12; ++i){
			for(int j = 0; j < players.length; ++j){
				
				players[j].pickUp(this.adventureDeck.pop()); 
			}
			
		}
	}
	
	public StoryCardAndCurrentPlayer drawStoryCard(StoryCardAndCurrentPlayer ret){
		
		
		
		ret.currentStoryCard = storyDeck.pop();
		
		//TEST
		//control.printTestString();
		
		ret.currentPlayer = players[currentPlayer];
		
		return ret;
	}
	
	public void CardsTest(){
		
		System.out.println("Adventure Deck: \n" + this.adventureDeck.toString());

		System.out.println("Story Deck: \n" + this.storyDeck.toString());
		
		System.out.println("Players Hands: \n\n");
			
		for(int i = 0; i < players.length; ++i){
			System.out.println("Player " + (i+1) + " Hand: \n" + players[i].getHand().toString());
		}
	}
	
//	public getState(){
//		
//	}
}
