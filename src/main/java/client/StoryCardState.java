package client;

public interface StoryCardState {
	void handle();
	void nextPlayer();
	void setPlayer();
	boolean canEndTurn();
}
