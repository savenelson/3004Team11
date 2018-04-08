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
import core.Player;
import core.State;

public class Client {

	private static final Logger logger = LogManager.getLogger(Client.class);
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
	ClientModel clientModel;
	View view;

	private static String testString;

	public Client(View view, String serverAddress, int serverPort) {
		logger.info("Control created");

		this.clientModel = new ClientModel(this);

		clientModel.instantiateStages();

		clientModel.instantiatePlayers(numPlayers);

		// model.initialShuffle(); //COMMENT OUT FOR SET SCENEARIOS

		// model.deal(); //COMMENT OUT FOR SET SCENEARIOS

		clientModel.setScenario1(); // UNCOMMENT FOR SCEN 1

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

		final String[] serverMessageComponents = serverMessage.split("--"); // array containing the components of the
																			// server
		// message
		switch (serverMessageComponents[1]) {
		case "MESSAGE":
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					view.info(serverMessageComponents[2]);
				}
			});

			getServerMessage();
			break;
		case "RESOLVESTAGE":
			clientModel.getQuesterManger().resolveStage();
			updateViewState();
			getServerMessage();
			break;
			

		case "DRAW":
			clientModel.draw(serverMessageComponents[3], Integer.parseInt(serverMessageComponents[2]));
			updateViewState();
			getServerMessage();
			break;
		case "UPDATE":
			if (serverMessageComponents[2].equals(Integer.toString(this.playerNumber))) {
				logger.info("Message was instigated by this client and not processed");
				getServerMessage();
			} else {
				/**
				 * convention of UPDATE case:
				 * "SERVERMESSAGE--UPDATE--CURRENTPLAYER--METOHDCALL--CARDID"
				 */
				logger.info("Message was instigated by another client, and will update this model");
				logger.info(serverMessageComponents[3]);
				switch (serverMessageComponents[3]) {
				case "QUEUE":
					clientModel.queue(serverMessageComponents[4], Integer.parseInt(serverMessageComponents[2]));
					updateViewState();
					getServerMessage();
					break;
				case "PARTY":
					clientModel.party(serverMessageComponents[4], Integer.parseInt(serverMessageComponents[2]));
					updateViewState();
					getServerMessage();
					break;
				case "DEQUEUE":
					clientModel.dequeue(serverMessageComponents[4], Integer.parseInt(serverMessageComponents[2]));
					updateViewState();
					getServerMessage();
					break;
				case "STAGE":
					clientModel.stage(serverMessageComponents[4], Integer.parseInt(serverMessageComponents[2]),
					Integer.parseInt(serverMessageComponents[5]));
					updateViewState();
					getServerMessage();
					break;
				case "UNSTAGE":
					clientModel.unstage(serverMessageComponents[4], Integer.parseInt(serverMessageComponents[2]),
					Integer.parseInt(serverMessageComponents[5]));
					updateViewState();
					getServerMessage();
					break;
				case "DISCARD":
					clientModel.discard(serverMessageComponents[4], Integer.parseInt(serverMessageComponents[2]));
					updateViewState();
					getServerMessage();
					break;
				case "ASSASSINATE":
					clientModel.assassinate(serverMessageComponents[4], Integer.parseInt(serverMessageComponents[2]));
					updateViewState();
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
			// clientModel.playGame();
			getServerMessage();
			break;
		case "TEST":
			logger.info("TEST GOOD!!");
			getServerMessage();
			break;
		case "SETTHREADPLAYER":
			clientModel.currentPlayer = Integer.parseInt(serverMessageComponents[2]);
			playerNumber = Integer.parseInt(serverMessageComponents[2]);
			logger.info("Player: " + clientModel.currentPlayer + " on ip: " + socket.getInetAddress() + " on port: "
					+ socket.getPort());
			updateViewState();
			getServerMessage();
			break;
		case "GETSPONSOR":
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					getSponsorDecision();
					updateViewState();
				}
			});
			getServerMessage();
			break;
		case "GETQUESTER":

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					getQuestingDecision();
				}
			});
			getServerMessage();
			break;
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
			clientModel.playGame();
			win = !win;
		}
	}

	public void updateViewState() {
		logger.debug("updateViewState() called");

		view.updateState();

	}

	public void stageIncrement() {
		logger.debug("stageIncrement() called");

		clientModel.getStage().nextStage();
		updateViewState();
	}

	public void stageOver() {
		logger.debug("stageOver() called");

		clientModel.stageOver();
	}

	public void getSponsorDecision() {
		logger.debug("getSponsorDecision() called");

		boolean isSponsor = view.popup("Player " + playerNumber + " - Would you like to sponsor this quest?");
		clientModel.getActivePlayer().isSponsor = isSponsor;
		sendClientMessage("CLIENTMESSAGE--ISSPONSOR--" + isSponsor + "--" + playerNumber);
	}

	public void getQuestingDecision() {
		logger.debug("getQuesting() called");

		// TODO GET THE RESPOND OF THE SPONSOR AND SEND IT BACK TO SEVER
		// LOOK ABOVE
		// return view.popup("Player " + (playerNumber + 1) + " - Would you like to
		// quest?");

		boolean isQuesting = view.popup("Player " + (playerNumber) + " - Would you like to quest?");
		clientModel.getActivePlayer().isQuesting = isQuesting;
		updateViewState();
		sendClientMessage("CLIENTMESSAGE--ISQUESTING--" + isQuesting + "--" + playerNumber);

	}

	public State getState() {
		logger.debug("getState() called");
		return clientModel.getState();
	}

	public void getStateString() {
		logger.debug("getState() called");
		sendClientMessage("CLIENTMESSAGE--GETSTATE");
		// return model.getState();
	}

	public void setNumPlayers(int i) {
		logger.debug("setNumPlayers() called");

		clientModel.numPlayers = i;
	}

	public Player getActivePlayer() {
		logger.debug("getActivePlayer() called");

		return clientModel.getActivePlayer();
	}

	public void printTestString() {
		System.out.println(testString);
	}

	// FIXME !! Here's where we're adding in the MESSAGES TO SERVER
	public void handClick(String clickType, String ID) {
		logger.debug("handClick() called");

		System.out.println(playerNumber);

		if (clickType.equals(View.PARTY)) {
			sendClientMessage("CLIENTMESSAGE--PARTY--" + ID + "--" + playerNumber);
			clientModel.party(ID, playerNumber);
		}

		else if (clickType.equals(View.STAGE)) {
			if (clientModel.stage(ID, playerNumber, playerNumber + clientModel.getStage().getCurrentStage())) {
				sendClientMessage("CLIENTMESSAGE--STAGE--" + ID + "--" + playerNumber + "--"
						+ clientModel.getStage().getCurrentStage());
			}

		} else if (clickType.equals(View.UNSTAGE)) {
			sendClientMessage("CLIENTMESSAGE--UNSTAGE--" + ID + "--" + playerNumber + "--"
					+ clientModel.getStage().getCurrentStage());
			clientModel.unstage(ID, playerNumber, clientModel.getStage().getCurrentStage());
		}

		else if (clickType.equals(View.QUEUE)) {
			sendClientMessage("CLIENTMESSAGE--QUEUE--" + ID + "--" + playerNumber);
			clientModel.queue(ID, playerNumber);
		}

		// FIXME MAYBE TODO MAYEB
		else if (clickType.equals(View.DEQUEUE)) {
			clientModel.dequeue(ID, playerNumber);
			sendClientMessage("CLIENTMESSAGE--DEQUEUE--" + ID + "--" + playerNumber);

		} else if (clickType.equals(View.DISCARD)) {
			clientModel.discard(ID, playerNumber);
			sendClientMessage("CLIENTMESSAGE--DISCARD--" + ID + "--" + playerNumber);

		} else if (clickType.equals(View.ASSASSINATE)) {
			sendClientMessage("CLIENTMESSAGE--ASSASSINATE--" + ID + "--" + playerNumber);
			clientModel.assassinate(ID, playerNumber);
		} else if (clickType.equals(View.ENDTURN)) {
			// if (clientModel.getCurrentState().canEndTurn()) {

			// }
		}
	}

	public void startStageCycle() {
		logger.debug("startStageCycle() called");

		clientModel.resetCurrentStage();
	}

	public void nextStory() {
		logger.debug("nextStory() called");

		clientModel.nextStory();
	}

	public void buttonClick(String clickType) {
		logger.debug("buttonClick() called");

		if (clickType.equals(View.STAGE1)) {
			clientModel.setCurrentStage(0);
		} else if (clickType.equals(View.STAGE2)) {
			clientModel.setCurrentStage(1);
		} else if (clickType.equals(View.STAGE3)) {
			clientModel.setCurrentStage(2);
		} else if (clickType.equals(View.STAGE4)) {
			clientModel.setCurrentStage(3);
		} else if (clickType.equals(View.STAGE5)) {
			clientModel.setCurrentStage(4);
		} else if (clickType.equals(View.ENDTURN)) {
			// if (clientModel.getCurrentState().canEndTurn()) {
			sendClientMessage("CLIENTMESSAGE--ENDTURN--" + "--" + playerNumber);
			// }
		}
	}

	public String getSubType(String ID, int currentPlayer) {
		logger.debug("getSubType() called");

		return clientModel.getSubType(ID, currentPlayer);
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

		clientModel.getCurrentState().nextPlayer();
		// view.nextPlayer();
	}

	public View getView() {
		logger.debug("getView() called");

		return view;
	}

	public void resolveStage() {

		view.stageResolved();

	}

	public void nextStage() {
		this.stageOver();
		logger.debug("Hello this is the model stage in the control " + clientModel.isDoneQuestingMode());
		if (clientModel.isDoneQuestingMode()) {
			view.resolveQuest();

		} else {
			// move to the next stage
			this.stageIncrement();
			nextPlayer();
		}
	}

}