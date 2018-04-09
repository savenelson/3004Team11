package core;

public interface StoryCardState {
	void handle();
	void nextPlayer();
	void setPlayer();
	boolean canEndTurn();
	void setHasSponsor(boolean b);
	void resolveStage();
	//void resolveQuest();
	void increaseResponse();
}
