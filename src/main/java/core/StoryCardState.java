package core;

public interface StoryCardState {
	void handle();
	void nextPlayer();
	void setPlayer();
	boolean canEndTurn();
}
