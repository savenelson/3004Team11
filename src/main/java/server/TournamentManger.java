package server;

import core.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.Player;
import core.QuesterQueque;
import core.StoryCardState;

public class TournamentManger implements StoryCardState {
	
	
	private static final Logger logger = LogManager.getLogger(TournamentManger.class);
	private static final String ENDTURN = "End turn";
	ServerModel serverModel;
	Player[] players;
	
	private boolean tournamentersReady = false;

	private int numOfansewers;

	private int numberOfCardsToReturn = 0;

	private int numberOfrequests = 0;

	private QuesterQueque tournamenters;

	private int numOfQuesterPotential;

	private int numOfQuester = 0;

	private int numOfRepsonders = 0;

	private int nextPersonToDraw = 0;

	/**
	 * 
	 * Constructor Takes a model gets its player and copies it make a new
	 * Questerqueue
	 */
	public TournamentManger(ServerModel serverModel) {
		this.serverModel = serverModel;
		this.players = serverModel.getPlayers();

		this.tournamenters = new QuesterQueque();

	}


	/**
	 * 
	 * This will handle the TournamentsEvents
	 * 
	 * 
	 */

	public void handle() {

		int numOfPotential = serverModel.getPlayers().length ;
		
			if (numOfRepsonders == numOfPotential) {
					
				serverModel.server.resolveTournament();
			}else {
				serverModel.server.getTournamentDecision();
			}
	}

	@Override
	public void nextPlayer() {
		// TODO Auto-generated method stub
		serverModel.nextPlayer();
		
	}

	@Override
	public void setPlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canEndTurn() {
		//	public boolean checkHandSize() {
		logger.debug("checkHandSize() called");

		// if the hand is bigger then 12 then reurn false
		if (serverModel.getActivePlayer().getHand().size() > 12) {
			// serverModel.control.alert("Hand Size is too large, please discard");
			logger.info("Player " + serverModel.getActivePlayer().getPlayerNumber() + " hand too large");
			return false;

		}
		
		return true;
	}

	@Override
	public void setHasSponsor(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resolveStage() {
		/**
		 * To resolve a stage, we need to count the following data structures:
		 *    - players Queue
		 *    - players Party
		 *    - players Rank
		 *    - get a card if they pass
		 */
		
		//TODO : need to get tournament gue
		Player winner =  serverModel.getPlayers()[0];
		
		int playerBP = serverModel.getPlayers()[0].getBattlePoint();
		playerBP += serverModel.getPlayers()[0].getPartyBattlesPoint();
		for(int i = 1; i < serverModel.getPlayers().length- 1; ++i){
			logger.info("This player battle points " + serverModel.getPlayers()[i].getBattlePoint()+"This is the battle points "+serverModel.getPlayers()[i].getBattlePoint());
			
			int playerBP2 = serverModel.getPlayers()[i].getBattlePoint();
			playerBP2 += serverModel.getPlayers()[i].getPartyBattlesPoint();
				
		
				
				
				if (playerBP<=playerBP2) {
					winner = serverModel.getPlayers()[i];
					
					
		
					}
				
					
				}
		
		logger.info("The winner Of the Tournament is" +winner+ "and is getting this many shields :" + ((TournamentCard) serverModel.getCurrentStoryCard()).getNumShields());
		winner.addShields(((TournamentCard) serverModel.getCurrentStoryCard()).getNumShields());

		
	}

	public void increaseResponse() {
		
	
			
		
	}

}
