package server;

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
	public TournamentManger(ServerModel serverModel) {
		this.serverModel = serverModel;
		this.players = serverModel.getPlayers();

		this.questers = new QuesterQueque();

	}


	/**
	 * 
	 * This will handle the TournamentsEvents
	 * 
	 * 
	 */

	public void handle() {

		int numOfPotential = serverModel.getNumPlayers() - 1;

		// if I do not have a sponsor ask the person if they want to sponsor
	

			// if I haven't ask to sponsor yet then ask ORIGINAL
			
			
		
			// if I haven't ask to quester yet then ask
			if (!this.serverModel.getActivePlayer().declinedToQuest) {
			
				serverModel.server.getQuesterDecison();
				numOfRepsonders++;
			
			// numOfQuesterPotential
			if (numOfRepsonders == 2) {
				questersReady = true;
			}

			if (questersReady) {

				resolveStage();
				for (int i = 0; i < serverModel.getNumPlayers(); ++i) {
					logger.info("players booleans: " + players[i].passedStage);
				}

				serverModel.server.resolveStage();
			}
		}

	}

	@Override
	public void nextPlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canEndTurn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setHasSponsor(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resolveStage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void increaseResponse() {
		// TODO Auto-generated method stub
		
	}

}
