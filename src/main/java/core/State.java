package core;

import java.util.ArrayList;



public class State {
	
	public Player [] players;	
	
	public Card currentStoryCard;
	
	public int currentPlayer;

	public CardCollection<AdventureCard> stage;
	
	public int currentViewer;
	
	public int currentStage;
	
	public boolean inNextQ;
	
	public int currentSponsor;
	
	public int numPlayers;
	
	public int numStages;
	
	public boolean currentPlayerNotSponsoring;
	
	public ArrayList<CardCollection<AdventureCard>> stages;
	
	public boolean stageResolved;
	
	public boolean toggleForStages;
	
	public int stagePlaceHolder;
	
	public int stageOverCount;
	
	public boolean isQuesting;
	
	@Override
	public String toString() {
		String result;
		String playerStrings = "";
		String tokenSplit = "--";
		
		for(Player player : players) {
			playerStrings += player.toString();
			playerStrings += tokenSplit;
			playerStrings += player.getRank();
			playerStrings += tokenSplit;
			playerStrings += player.getShieldCount();
			playerStrings += tokenSplit;
			playerStrings += player.getParty();
			playerStrings += tokenSplit;
			playerStrings += player.getQueue();
			playerStrings += tokenSplit;
			playerStrings += player.getHand();
			playerStrings += tokenSplit;
		}

		result = ("" + playerStrings + currentStoryCard + "--" + currentPlayer + "--" + currentStage + "--" + currentSponsor + "--" + numPlayers + "--" + numStages + "--" + stageResolved + "--" + isQuesting);
		return result;
	}
	
}
