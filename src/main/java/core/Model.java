package core;

public class Model {

	public Control control;
	
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

	Card currStoryCard;
	
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
			players[i] = new Player(i);
		}
		
		currentPlayer = 0;
	}
	
	public void initialShuffle(){

		this.adventureDeck.shuffle();
		
		this.storyDeck.shuffle();
		
	}
	
	public void deal(){
		
			            // 12 cards in hand
		for(int i = 0; i < 12; ++i){
			for(int j = 0; j < players.length; ++j){
				
				players[j].addToHand(this.adventureDeck.pop()); 
			}
			
			this.currStoryCard = storyDeck.pop();
		}
	}
	
	public void CardsTest(){
		
		System.out.println("Adventure Deck: \n" + this.adventureDeck.toString());

		System.out.println("Story Deck: \n" + this.storyDeck.toString());
		
		System.out.println("Players Hands: \n\n");
			
		for(int i = 0; i < players.length; ++i){
			System.out.println("Player " + (i+1) + " Hand: \n" + players[i].getHand().toString());
		}
	}
	

	public State getState(){
		
		State state = new State();
		
		state.players = this.players;
		
		state.currentPlayer = this.currentPlayer;
		
		state.currStoryCard = this.currStoryCard;
		
		return state;
	}

	public void play(String iD) {
		System.out.println("IN PARTY");
		CardCollection hand = this.players[this.currentPlayer].getHand();
		Card c = hand.getByID(iD);
		hand.remove(c);
		players[currentPlayer].addToParty(c);
	}

	public void discard(String iD) {
		
		System.out.println("IN DISCARD");
		CardCollection hand = this.players[this.currentPlayer].getHand();
		Card c = hand.getByID(iD);
		hand.remove(c);
		adventureDeckDiscard.add(c);
	}
	
	public void queue(String iD) {
		System.out.println("IN QUEUE");
		CardCollection hand = this.players[this.currentPlayer].getHand();
		Card c = hand.getByID(iD);
		hand.remove(c);
		players[currentPlayer].addToQueue(c);
	}
	public void dequeue(String iD) {
		System.out.println("IN HAND");
		CardCollection hand = this.players[this.currentPlayer].getQueue();
		Card c = hand.getByID(iD);
		hand.remove(c);
		players[currentPlayer].addToHand(c);
	}
	
	public void setScenario1() {
		
		initialShuffle();
		
		//deal the hands to each player
		for(int i = 0; i < 12; ++i){
			for(int j = 0; j < players.length; ++j){
				
				players[j].addToHand(this.adventureDeck.pop()); 
			}
			
			this.currStoryCard = storyDeck.popByID("126");
			System.out.println(this.currStoryCard.toString());
			
		}
		System.out.println(this.currStoryCard.toString());
		//set current StoryCard to BoarHunt
		
	}
	
	public void setScenario2() {
		
		initialShuffle();
		
		System.out.println("Adventure Deck: \n" + this.storyDeck.toString());
		//set current StoryCard to SearchForHolyGrail
		
		
		
	}
}
