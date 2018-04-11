package server;

import java.net.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.AdventureCard;
import javafx.application.Platform;

import java.io.*;


public class ServerThread extends Thread {
	private static final Logger logger = LogManager.getLogger(ServerThread.class);
	private Socket socket = null;
	private Server server;
	public PrintWriter out;
	public BufferedReader in;
	public int currentPlayer;

	public ServerThread(Socket socket, Server server, int currentPlayer) {
		super("Server");
		logger.info("ServerThread Created on " + socket);
		this.socket = socket;
		this.server = server;
		this.currentPlayer = currentPlayer;
	}
	
	public int getPlayerNumber() {return currentPlayer;}

	public void run() {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String clientMessage;
			out.println("SERVERMESSAGE--SETTHREADPLAYER--" + currentPlayer);
			out.println("SERVERMESSAGE--WELCOME");
			while ((clientMessage = in.readLine()) != null) {
				getClientMessage(clientMessage);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param clientMessage string that's sent from client in the format:
	 * 			in as "CLIENTMESSAGE--QUEUE--ID--playerNumber"
	 * 
	 * 			out as "SERVERMESSAGE--UPDATE--PLAYNUMBER--CALL--CARDID"
	 * 
	 * 			"SERVERMESSAGE--UPDATE--PLAYNUMBER--CALL--CARDID"
	 */
	private void getClientMessage(String clientMessage) {
		String[] clientMessageComponents = clientMessage.split("--"); // array containing the components of the server
		logger.info("msg from client: " + clientMessage);
		switch (clientMessageComponents[1]) {
		case "HELLO":
			logger.info("Client at " + clientMessageComponents[2] + " has connected");
			break;
		case "QUEUE":
			server.serverModel.queue(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]));
			server.sendServerMessage("SERVERMESSAGE--UPDATE--" + clientMessageComponents[3] + "--" + clientMessageComponents[1] + "--" + clientMessageComponents[2]);
			break;
		case "PARTY":
			server.serverModel.party(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]));
			server.sendServerMessage("SERVERMESSAGE--UPDATE--" + clientMessageComponents[3] + "--" + clientMessageComponents[1] + "--" + clientMessageComponents[2]);
			break;
		case "DEQUEUE":
			server.serverModel.dequeue(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]));
			server.sendServerMessage("SERVERMESSAGE--UPDATE--" + clientMessageComponents[3] + "--" + clientMessageComponents[1] + "--" + clientMessageComponents[2]);
			break;
			
		case "STAGE":
			server.serverModel.stage(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]),Integer.parseInt(clientMessageComponents[4]));
			server.sendServerMessage("SERVERMESSAGE--UPDATE--" + clientMessageComponents[3]+"--"+clientMessageComponents[1] + "--" +   clientMessageComponents[2]+"--"+clientMessageComponents[4]);
			break;
			
		case "UNSTAGE":
			server.serverModel.unstage(clientMessageComponents[4], Integer.parseInt(clientMessageComponents[2]),Integer.parseInt(clientMessageComponents[4]));
			server.sendServerMessage("SERVERMESSAGE--UPDATE--" + clientMessageComponents[3] + "--" + clientMessageComponents[1] + "--" + clientMessageComponents[2]+"--"+clientMessageComponents[4]);
			break;
		case "DISCARD":
			server.serverModel.discard(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]));
			server.sendServerMessage("SERVERMESSAGE--UPDATE--" + clientMessageComponents[3] + "--" + clientMessageComponents[1] + "--" + clientMessageComponents[2]);
			break;
		case "ASSASSINATE":
			server.serverModel.assassinate(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]));
			server.sendServerMessage("SERVERMESSAGE--UPDATE--" + clientMessageComponents[3] + "--" + clientMessageComponents[1] + "--" + clientMessageComponents[2]);
			break;
			
		case "ISSPONSOR":
			boolean ISSPONSOR = Boolean.parseBoolean(clientMessageComponents[2]);
			server.serverModel.getActivePlayer().isSponsor= ISSPONSOR;
			
			if(ISSPONSOR) {
				server.serverModel.questManager.setHasSponsor(true);
				server.sendServerMessage("SERVERMESSAGE--MESSAGE--"+server.serverModel.getActivePlayer()+" has decidied to sponsor. Get ready to quest");
			}else {
				server.sendServerMessage("SERVERMESSAGE--MESSAGE--"+server.serverModel.getActivePlayer()+" has not decidied to sponsor");
				server.serverModel.nextPlayer();
				server.serverModel.currentState.handle();
			}
			break;
			
		case "ISQUESTING":
			int playerNum = server.serverModel.getActivePlayer().getPlayerNumber();
			boolean ISQUESTER = Boolean.parseBoolean(clientMessageComponents[2]);
			server.serverModel.getActivePlayer().isQuesting= ISQUESTER;
			if(ISQUESTER) {
				server.sendServerMessage("SERVERMESSAGE--MESSAGE--"+playerNum+" has decidied to quest");
				server.sendServerMessage("SERVERMESSAGE--UPDATE--"+clientMessageComponents[2]+"--ISQUESTING"+"--"+server.serverModel.getActivePlayer().getPlayerNumber());
				AdventureCard c = server.serverModel.getAdventureDeck().peek();
				String ID = c.getID();
				server.serverModel.draw(ID,playerNum);
				server.sendServerMessage("SERVERMESSAGE--DRAW--" + playerNum + "--" + ID);

			} else {
				server.sendServerMessage("SERVERMESSAGE--MESSAGE--"+playerNum+" has NOT decidied to quest");
				server.serverModel.nextPlayer();
			}
			break;
			
		case "ISTOURNAMENTING":
			int playerNum1 = server.serverModel.getActivePlayer().getPlayerNumber();
			boolean isTournamenting = Boolean.parseBoolean(clientMessageComponents[2]);
			server.serverModel.getActivePlayer().isTournamenting= isTournamenting;
			if(isTournamenting) {
				server.sendServerMessage("SERVERMESSAGE--MESSAGE--"+playerNum1+" has decidied to tournament");
				server.sendServerMessage("SERVERMESSAGE--UPDATE--"+clientMessageComponents[2]+"--ISTOURNAMENTING"+"--"+server.serverModel.getActivePlayer().getPlayerNumber());
				AdventureCard c = server.serverModel.getAdventureDeck().peek();
				String ID = c.getID();
				server.serverModel.draw(ID,playerNum1);
				server.sendServerMessage("SERVERMESSAGE--DRAW--" + playerNum1 + "--" + ID);
			}else {
				server.sendServerMessage("SERVERMESSAGE--MESSAGE--"+playerNum1+" has NOT decidied to Tournament");
				server.serverModel.nextPlayer();
			}
			server.serverModel.endTurn();
		
			break;
			
		case "SETSTORYCARD":

		case "ENDTURN":
			//Platform.runLater(runnable);
			
			server.serverModel.endTurn();
			
			//server.serverModel.nextPlayer();
			
			break;
			
		case "GETSTATE":
			String stateString = server.serverModel.getState().toString();
			logger.info(stateString);
			out.println("SERVERMESSAGE--GETSTATE--" + stateString);
			break;
		default:
			System.err.println("Unknown message received from server: \"" + clientMessage + "\"");
			break;
		}
	}
}
