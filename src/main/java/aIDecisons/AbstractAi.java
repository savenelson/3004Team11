package aIDecisons;

import core.Player;
import core.State;

public abstract class AbstractAi {
	Player player;
	
	State state;
	
	DiscardAfterWinningTest discardBehaviour;
	

	//Sponsors behaviors
	DoISponsorAQuest toSponsorDecision;
	
	SponsorBehaviour sponsorBehaviour;
	
	
	//Tournament behaviors
	DoIParticipateInTournament toTournamentDecision;
	
	TournamentBehaviour tournamentBehaviour;
	
	
	//Quest behaviors
	doIParticipateInQuest questBehaviour;
	
	ToQuest toQuestDecision;
	
	
	//Bid behaviors
	NextBid bidBehaviour;
	
	nextBid toBidDecision;
	
	
	
	
	
	
	
	
	public void performQuestDecison() {
		toQuestDecision.decide();
	}
	
	
	public void performSponsorDecison() {
		toSponsorDecision.decide();
	}
	
	public void performBidDecison() {
		toBidDecision.decide();
	} 
	public boolean performIsEnteringTournament() {
		toTournamentDecision.decide();
	} 
	
	public void performQuestMove() {
		questBehaviour.move();
	}
	
	public void performSponsorMove() {
		sponsorBehaviour.move();
	}
	
	public void performBidMove() {
		bidBehaviour.move();
		
	}
	
	public void performTournamentMove() {
		tournamentBehaviour.move();
	}
	
	public void performdiscardMove() {
		discardBehaviour.move();
	}
	
	
	
	
	public AbstractAi() {
		System.out.println("This is the AI");
	}
	
	public void makeDecision() {
		decisionBehaviour.decide();
	}

}
