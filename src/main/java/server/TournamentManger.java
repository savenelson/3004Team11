package server;

import core.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TournamentManger implements StoryCardState {
	
	private static final Logger logger = LogManager.getLogger(TournamentManger.class);
	private static final String ENDTURN = "End turn";
	ServerModel serverModel;
	Player[] players;
	
	private boolean tournamentersReady = false;

	private int numberOfCardsToReturn = 0;
	
	private int numberOfEndTurnsCalled = 0;		//the number of end turns

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
		
			if (!this.serverModel.getActivePlayer().declinedToQuest) {
				serverModel.server.getTournamentDecision();
				this.serverModel.getActivePlayer().declinedToQuest = true;
			}
		
			if (numberOfEndTurnsCalled == numOfPotential*2) {
					
				serverModel.server.resolveTournament();
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
	public void resolveTournament() {

		Player winner =  serverModel.getPlayers()[0];
		int playerBP = serverModel.getPlayers()[0].getBattlePoint();
		playerBP += serverModel.getPlayers()[0].getPartyBattlesPoint();
		for(int i = 1; i < serverModel.getPlayers().length; ++i){
			logger.info("Player " + i + "'s BP = " + serverModel.getPlayers()[i].getBattlePoint());
			
			int playerBP2 = serverModel.getPlayers()[i].getBattlePoint();
			playerBP2 += serverModel.getPlayers()[i].getPartyBattlesPoint();

				if (playerBP<=playerBP2) {
					if(i>0) {
						serverModel.getPlayers()[i-1].isTournamentWinner = false;
					}
					winner = serverModel.getPlayers()[i];
						serverModel.getPlayers()[i].isTournamentWinner = true;
					}
				}
		
		logger.info("The winner Of the Tournament is" +winner+ "and is getting this many shields :" + ((TournamentCard) serverModel.getCurrentStoryCard()).getNumShields());
		winner.addShields(((TournamentCard) serverModel.getCurrentStoryCard()).getNumShields());
	}

	public void increaseResponse() {
		numberOfEndTurnsCalled++;
	}

	@Override
	public void resolveStage() {
		// TODO Auto-generated method stub
		
	}
}
