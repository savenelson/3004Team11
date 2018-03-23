package core;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Table implements Runnable{

    private ArrayList<ServerPlayer> table = new ArrayList<ServerPlayer>();                    // holds the players at the table
    private CountDownLatch placedBetsLatch;                                 // latch to wait for all players to place their bets
    private CountDownLatch placedInsuranceBetsLatch;                        // latch to wait for all players to place their insurance bets
    private CountDownLatch turnLatch;                                       // latch to wait for all players to be ready for their turns
    private CountDownLatch continuePlayingLatch;                            // latch to wait for all players to determine if they will keep playing
	private QuestsServerModel model;
	
	public Table() {
		
	}
	
	/**
	 * Thread run method
	 */
	public void run() {
		model = new QuestsServerModel();
	}

    /**
     * Adds a player to the table.
     *
     * @param player Player to add to table
     */

    public void addPlayer(ServerPlayer player) {
        table.add(player);
    }

}
