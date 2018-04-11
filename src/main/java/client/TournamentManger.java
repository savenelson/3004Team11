package client;

import core.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.Player;
import core.QuesterQueque;
import core.StoryCardState;

public class TournamentManger implements StoryCardState {
	
	private static final Logger logger = LogManager.getLogger(TournamentManger.class);
	private static final String ENDTURN = "End turn";
	ClientModel clientModel;
	Player[] players;

	private boolean questersReady = false;

	private int numOfansewers;

	private int numberOfCardsToReturn = 0;

	private int numberOfrequests = 0;

	private QuesterQueque questers;

	private int numOfQuesterPotential;

	private int numOfQuester = 0;

	private int numOfRepsonders = 0;

	private int nextPersonToDraw = 0;

	/**
	 * 
	 * Constructor Takes a model gets its player and copies it make a new
	 * Questerqueue
	 */
	public TournamentManger(ClientModel clientModel) {
		this.clientModel = clientModel;
		this.players = clientModel.getPlayers();

		this.questers = new QuesterQueque();

	}


	/**
	 * 
	 * This will handle the TournamentsEvents
	 * 
	 * 
	 */

	public void handle() {

	}

	@Override
	public void nextPlayer() {
		// TODO Auto-generated method stub
		clientModel.nextPlayer();
		
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
		if (clientModel.getActivePlayer().getHand().size() > 12) {
			// clientModel.control.alert("Hand Size is too large, please discard");
			logger.info("Player " + clientModel.getActivePlayer().getPlayerNumber() + " hand too large");
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

		Player winner =  clientModel.getPlayers()[0];
		int playerBP = clientModel.getPlayers()[0].getBattlePoint();
		playerBP += clientModel.getPlayers()[0].getPartyBattlesPoint();
		for(int i = 1; i < clientModel.getPlayers().length; ++i){
			logger.info("Player " + i + "'s BP = " + clientModel.getPlayers()[i].getBattlePoint());
			
			int playerBP2 = clientModel.getPlayers()[i].getBattlePoint();
			playerBP2 += clientModel.getPlayers()[i].getPartyBattlesPoint();

			if (playerBP<=playerBP2) {
				if(i>0) {
					clientModel.getPlayers()[i-1].isTournamentWinner = false;
				}
				winner = clientModel.getPlayers()[i];
				clientModel.getPlayers()[i].isTournamentWinner = true;
			}
		}
		
		logger.info("The winner Of the Tournament is" +winner+ "and is getting this many shields :" + ((TournamentCard) clientModel.getCurrentStoryCard()).getNumShields());
		winner.addShields(((TournamentCard) clientModel.getCurrentStoryCard()).getNumShields());
	}
	
	public void increaseResponse() {
		numberOfEndTurnsCalled++;
	}


	@Override
	public void resolveStage() {
		// TODO Auto-generated method stub
		
	}

}
