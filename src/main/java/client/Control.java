package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Platform;

public class Control {

	private static final Logger logger = LogManager.getLogger(Control.class);
	private static final int MESSAGE_WAIT_TIME = 500; // time to wait between server messages
	private static final String DEFAULT_SERVER_ADDRESS = "localhost"; // default server address
	private static final int DEFAULT_SERVER_PORT = 44444; // default server port
	private String serverAddress = DEFAULT_SERVER_ADDRESS; // server address
	private int serverPort; // server port
	private Socket socket; // socket on server address and port
	private BufferedReader in; // in to server
	private PrintWriter out; // out from server
	private int playerNumber; // everyclienthas a unique playernum
	private int numPlayers = 4;
	String serverMessage = null;
	Model model;
	View view;

	private static String testString;

	public Control(View view, String serverAddress, int serverPort) {
		logger.info("Control created");

		

		this.model = new Model(this);

		model.instantiateStages();

		model.instantiatePlayers(numPlayers);

		// model.initialShuffle(); //COMMENT OUT FOR SET SCENEARIOS

		// model.deal(); //COMMENT OUT FOR SET SCENEARIOS

		model.setScenario1(); // UNCOMMENT FOR SCEN 1

		// model.setScenario2(); //UNCOMMENT FOR SCEN 2

		// model.setScenarioTest();

		// model.eventTesting();
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.view = view;

		try {
			socket = new Socket(serverAddress, serverPort);
			logger.info(socket);

		} catch (IOException e) {
			logger.info("No Quests server running on port " + serverPort + " at address " + serverAddress);
			System.exit(1);
		}
		try {
			InputStreamReader isr = new InputStreamReader(socket.getInputStream()); // input stream reader from socket
			in = new BufferedReader(isr);
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		sendClientMessage("CLIENTMESSAGE--HELLO");

		getServerMessage();
	}

	/**
	 * Gets a message sent by the server.
	 *
	 * @return message sent by the server
	 * @throws IOException
	 */
	public void getServerMessage() {
		logger.debug("getServerMessage() called");
		serverMessage = null;
		try {
			Thread.sleep(MESSAGE_WAIT_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		final Thread thread = new Thread(new Runnable() {
			public void run() {
				while (serverMessage == null) {
					try {
						serverMessage = in.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
					processServerMessage(serverMessage);
				}
			}
		});
		thread.start();
	}

	/**
	 * Sends a message to the server.
	 *
	 * @param clientMessage
	 *            Message to send to server
	 */

	public void sendClientMessage(String clientMessage) {
		out.println(clientMessage);
	}

	public void start() {
		logger.info("Starting Quests client\nServer address: " + serverAddress + "\nServer port: " + serverPort);

	}

	/**
	 * Changes the client view based on which message was received from the server.
	 *
	 * @param serverMessage
	 *            Message received from server
	 * 
	 *            Message Convention: SERVERMESSAGE--CALL--CALLARGS
	 */
	private void processServerMessage(String serverMessage) {

		logger.info("MSG fm server: " + serverMessage);

		String[] serverMessageComponents = serverMessage.split("--"); // array containing the components of the server
																		// message
		switch (serverMessageComponents[1]) {
		case "UPDATE":
			if (serverMessageComponents[2].equals(Integer.toString(this.playerNumber))) {
				logger.info("Message was instigated by this client and not processed");
				getServerMessage();
			} else {
				/**
				 * convention of UPDATE case: "SERVERMESSAGE--UPDATE--CURRENTPLAYER--METOHDCALL--CARDID"
				 */
				logger.info("Message was instigated by another client, and will update this model");
				switch (serverMessageComponents[3]) {
				case "QUEUE":
					model.queue(serverMessageComponents[4], Integer.parseInt(serverMessageComponents[2]));
					getServerMessage();
					break;
				case "PARTY":
					model.party(serverMessageComponents[4], Integer.parseInt(serverMessageComponents[2]));
					getServerMessage();
					break;
				case "DEQUEUE":
					model.dequeue(serverMessageComponents[4], Integer.parseInt(serverMessageComponents[2]));
					getServerMessage();
					break;
				case "UNSTAGE":
					model.unstage(serverMessageComponents[4], Integer.parseInt(serverMessageComponents[2]));
					getServerMessage();
					break;
				case "DISCARD":
					model.discard(serverMessageComponents[4], Integer.parseInt(serverMessageComponents[2]));
					getServerMessage();
					break;
				case "ASSASSINATE":
					model.assassinate(serverMessageComponents[4], Integer.parseInt(serverMessageComponents[2]));
					getServerMessage();
					break;
				default:
					logger.info("Couldnt parse message from SERVERMESSAGE--UPDATE-- ?!?!?!");
					getServerMessage();
					break;
				}
			}
			break;
		case "WELCOME":
			logger.info("server has established connection with this client");
			getServerMessage();
			break;
		case "TEST":
			logger.info("TEST GOOD!!");
			getServerMessage();
			break;
		case "SETTHREADPLAYER":
			model.currentPlayer = Integer.parseInt(serverMessageComponents[2]);
			playerNumber = Integer.parseInt(serverMessageComponents[2]);
			logger.info(
					"Player: " + model.currentPlayer + " on port: " + socket.getPort());
			getServerMessage();
			break;
			
		case "GAMEHANDLE":
			
				/**
				 * convention of UPDATE case: "SERVERMESSAGE--GAMEHANDLE--PlAYERID--GETSPONSOR"
				 */
				logger.info("Message was instigated by ME lient, and will update this model");
				switch (serverMessageComponents[3]) {
				case "GETSPONSOR":
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
						    model.control.getSponsorDecision();}
					});
					
					break;
				case "PARTY":
					model.party(serverMessageComponents[4], Integer.parseInt(serverMessageComponents[2]));
					break;
				case "DEQUEUE":
					model.dequeue(serverMessageComponents[4], Integer.parseInt(serverMessageComponents[2]));
					break;
				case "UNSTAGE":
					model.unstage(serverMessageComponents[4], Integer.parseInt(serverMessageComponents[2]));
					break;
				case "DISCARD":
					model.discard(serverMessageComponents[4], Integer.parseInt(serverMessageComponents[2]));
					break;
				case "ASSASSINATE":
					model.assassinate(serverMessageComponents[4], Integer.parseInt(serverMessageComponents[2]));
					break;
				default:
					logger.info("Couldnt parse message from SERVERMESSAGE--UPDATE-- ?!?!?!");
					break;
				}
			
	
			
		case "GETSTATE":

			// model.state = Integer.parseInt(serverMessageComponents[2]);
			// logger.info("currentPlayer for client on port:" + socket.getPort() + ",
			// currentPlayer: "+ model.currentPlayer);
			// getServerMessage();
			// break;
		default:
			logger.info("Unknown message received from server: \"" + serverMessage + "\"");
			break;
		}
	}


	/**
	 * Calls the model quitGame method.
	 */

	public void quitGame() {
		// model.quitGame(); //still need to write this in the model
	}

	public void mainLoop() {
		logger.debug("mainLoop() running");

		boolean win = false;
		while (!win) {
			// TODO send messsage to server with "CLIENTMESSAGE--playGame"
			model.playGame();
			win = !win;
		}
	}

	public void updateViewState() {
		logger.debug("updateViewState() called");

		view.updateState();

	}

	public void stageIncrement() {
		logger.debug("stageIncrement() called");

		model.stage.nextStage();
		updateViewState();
		// model.state.toggleForStages = false;

	}

	public void stageOver() {
		logger.debug("stageOver() called");

		model.stageOver();
	}

	public boolean getSponsorDecision() {
		logger.debug("getSponsorDecision() called");

		return view.popup(
				"Player " + (playerNumber + 1) + " - Would you like to sponsor this quest?");
	}

	public boolean getQuestingDecision() {
		logger.debug("getQuesting() called");

		return view.popup("Player " + (playerNumber + 1) + " - Would you like to quest quest?");
	}

	public State getState() {
		logger.debug("getState() called");
		return model.getState();
	}

	public void getStateString() {
		logger.debug("getState() called");
		sendClientMessage("CLIENTMESSAGE--GETSTATE");
		// return model.getState();
	}

	public void setNumPlayers(int i) {
		logger.debug("setNumPlayers() called");

		model.numPlayers = i;
	}

	public Player getActivePlayer() {
		logger.debug("getActivePlayer() called");

		return model.getActivePlayer();
	}

	public void printTestString() {
		System.out.println(testString);
	}

	// FIXME !! Here's where we're adding in the MESSAGES TO SERVER
	public void handClick(String clickType, String ID) {
		logger.debug("handClick() called");

		if (clickType.equals(View.PARTY)) {
			sendClientMessage("CLIENTMESSAGE--PARTY--" + ID + "--" + playerNumber);
			model.party(ID, playerNumber);
		} else if (clickType.equals(View.STAGE)) {
			sendClientMessage("CLIENTMESSAGE--STAGE--" + ID + "--" + playerNumber);
			model.stage(ID, playerNumber);
		} else if (clickType.equals(View.UNSTAGE)) {
			sendClientMessage("CLIENTMESSAGE--UNSTAGE--" + ID + "--" + playerNumber);
			model.unstage(ID, playerNumber);
		} else if (clickType.equals(View.QUEUE)) {
			model.queue(ID, playerNumber);
			sendClientMessage("CLIENTMESSAGE--QUEUE--" + ID + "--" + playerNumber);
		} else if (clickType.equals(View.DEQUEUE)) {
			sendClientMessage("CLIENTMESSAGE--DEQUEUE--" + ID + "--" + playerNumber);
			model.dequeue(ID, playerNumber);
		} else if (clickType.equals(View.DISCARD)) {
			sendClientMessage("CLIENTMESSAGE--DISCARD--" + ID + "--" + playerNumber);
			model.discard(ID, playerNumber);
		} else if (clickType.equals(View.ASSASSINATE)) {
			sendClientMessage("CLIENTMESSAGE--ASSASSINATE--" + ID + "--" + playerNumber);
			model.assassinate(ID, playerNumber);
		}
	}

	public void startStageCycle() {
		logger.debug("startStageCycle() called");

		model.resetCurrentStage();
	}

	public void nextStory() {
		logger.debug("nextStory() called");

		model.nextStory();
	}

	public void buttonClick(String clickType) {
		logger.debug("buttonClick() called");

		if (clickType.equals(View.STAGE1)) {
			model.setCurrentStage(0);
		} else if (clickType.equals(View.STAGE2)) {
			model.setCurrentStage(1);
		} else if (clickType.equals(View.STAGE3)) {
			model.setCurrentStage(2);
		} else if (clickType.equals(View.STAGE4)) {
			model.setCurrentStage(3);
		} else if (clickType.equals(View.STAGE5)) {
			model.setCurrentStage(4);
		} else if (clickType.equals(View.ENDTURN)) {

			model.endTurn();
		}
	}

	public String getSubType(String ID, int currentPlayer) {
		logger.debug("getSubType() called");

		return model.getSubType(ID, currentPlayer);
	}

	public void resolveQuest() {
		logger.debug("resolveQuest() called");

		view.resolveQuest();
	}

	public void stagesSet() {
		logger.debug("stagesSet() called");

		startStageCycle();
	}

	public void alert(String message) {
		logger.info("alert() called: " + message);

		view.alert(message);
	}

	public void nextPlayer() {
		logger.debug("next player");

		model.currentState.nextPlayer();
		// view.nextPlayer();
	}

	public View getView() {
		logger.debug("getView() called");

		return view;
	}

	public void nextStage() {
		this.stageOver();
		logger.debug("Hello this is the model stage in the control " + model.isDoneQuestingMode);
		if (model.isDoneQuestingMode) {
			view.resolveQuest();

		} else {
			// move to the next stage
			this.stageIncrement();
			nextPlayer();
		}
	}
}