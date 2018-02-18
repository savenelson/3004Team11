package core;

public class Model {

	private Player [] players;
	
	private AdventureDeck adventureDeck;
	public AdventureDeck getAdventureDeck(){return this.adventureDeck;}
	
	//private AdventureDeck adventureDeckDiscard = new AdventureDeck();
	
	private StoryDeck storyDeck;
	public StoryDeck getStoryDeck(){return storyDeck;}

	
	//private StoryDeck storyDeckDiscard = new StoryDeck();
	
	Model(int numPlayers){
	
		this.adventureDeck = new AdventureDeck();
		this.storyDeck = new StoryDeck();
		
		instantiatePlayers(numPlayers);
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
	
	public void CardsTest(){
		
		System.out.println("Adventure Deck: \n" + this.adventureDeck.toString());

		System.out.println("Story Deck: \n" + this.storyDeck.toString());
			
//		for(int i = 0; i < 12; ++i){
//			for(int j = 0; j < players.length; ++j){
//				
//				players[i].getHand().toString(); 
//			}
//		}
	}
	
}
