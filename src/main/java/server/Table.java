package server;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Table implements Runnable{

    private ArrayList<ServerPlayer> table = new ArrayList<ServerPlayer>();                    // holds the players at the table
    private CountDownLatch placedBetsLatch;                                 // latch to wait for all players to place their bets
    private CountDownLatch placedInsuranceBetsLatch;                        // latch to wait for all players to place their insurance bets
    private CountDownLatch turnLatch;                                       // latch to wait for all players to be ready for their turns
    private CountDownLatch continuePlayingLatch;                            // latch to wait for all players to determine if they will keep playing
    private Model model;
    
    public Table() {
		
	}
	
	/**
	 * Thread run method
	 */
	public void run() {
//		model = new Model();
		model.instantiateStages();
		model.initialShuffle();
        do {
//            playQuests();
        } while (true); //FIXME need a better stop boolean
	}

    /**
     * Adds a player to the table.
     *
     * @param player Player to add to table
     */

	

    /**
     * Plays Quests.
     * FIXME has all logic from blackjack
     */

//    private void playQuests() {
//        setup();
//        for (Player player : table) {
//            player.startLatchCountDown();
//        }
//        try {
//            placedBetsLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        for (Player player : table) {
//            player.betLatchCountDown();
//        }
//        dealInitialCards();
//        for (Player player : table) {
//            player.dealLatchCountDown();
//        }
//        try {
//            placedInsuranceBetsLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        for (Player player : table) {
//            player.insuranceBetLatchCountDown();
//        }
//        try {
//            turnLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        for (Player player : table) {
//            player.takeTurn(player.originalPlayerHand());
//        }
//        dealerTurn();
//        for (Player player : table) {
//            player.dealerTurnLatchCountDown();
//        }
//        try {
//            continuePlayingLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
    
//    private void setup() {
//        if (shoe.remainingCards() <= minimumCardsBeforeShuffle) {
//            shoe = new Shoe(numberOfDecks);
//            shoe.shuffle();
//        }
//        dealerHand.clear();
//        dealerHasBlackjack = false;
//        placedBetsLatch = new CountDownLatch(numPlayers());
//        placedInsuranceBetsLatch = new CountDownLatch(numPlayers());
//        turnLatch = new CountDownLatch(numPlayers());
//        continuePlayingLatch = new CountDownLatch(numPlayers());
//    }
	
    /**
     * Deals the first two cards to each player and the dealer.
     */

    private void dealInitialCards() {
    	//FIXME add here game logic for initial deal
    }
    
    
    public void addPlayer(ServerPlayer player) {
        table.add(player);
    }
    
    public void removePlayer(Player player) {
        table.remove(player);
    }

    /**
     * Returns the number of players at the table.
     *
     * @return the number of players at the table
     */

    public int numPlayers() {
        return table.size();
    }

    /**
     * Returns the minimum bet of the table.
     *
     * @return the minimum bet of the table
     */

    
    //TODO NEED MORE GAME LOGIC METHODS
}