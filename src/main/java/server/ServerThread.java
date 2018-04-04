package server;

import java.net.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	 * 			out as "SERVERMESSAGE--UPDATE--PLAYNUMBER--CALL--CARDID"
	 */
	private void getClientMessage(String clientMessage) {
		String[] clientMessageComponents = clientMessage.split("--"); // array containing the components of the server
																		// message
		switch (clientMessageComponents[1]) {
		case "HELLO":
			logger.info("Client has responded!!!");
			break;
		case "QUEUE":
			server.model.queue(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]));
			server.sendServerMessage("SERVERMESSAGE--UPDATE--" + clientMessageComponents[3] + "--" + clientMessageComponents[1] + "--" + clientMessageComponents[2]);
			break;
		case "PARTY":
			server.model.party(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]));
			server.sendServerMessage("SERVERMESSAGE--UPDATE--" + clientMessageComponents[3] + "--" + clientMessageComponents[1] + "--" + clientMessageComponents[2]);
			break;
		case "DEQUEUE":
			server.model.dequeue(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]));
			server.sendServerMessage("SERVERMESSAGE--UPDATE--" + clientMessageComponents[3] + "--" + clientMessageComponents[1] + "--" + clientMessageComponents[2]);
			break;
		case "UNSTAGE":
			server.model.unstage(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]));
			server.sendServerMessage("SERVERMESSAGE--UPDATE--" + clientMessageComponents[3] + "--" + clientMessageComponents[1] + "--" + clientMessageComponents[2]);
			break;
		case "DISCARD":
			server.model.discard(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]));
			server.sendServerMessage("SERVERMESSAGE--UPDATE--" + clientMessageComponents[3] + "--" + clientMessageComponents[1] + "--" + clientMessageComponents[2]);
			break;
		case "ASSASSINATE":
			server.model.assassinate(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]));
			server.sendServerMessage("SERVERMESSAGE--UPDATE--" + clientMessageComponents[3] + "--" + clientMessageComponents[1] + "--" + clientMessageComponents[2]);
			break;
			
		case "STAGE":
			server.model.stage(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]));
			server.sendServerMessage("SERVERMESSAGE--UPDATE--" + clientMessageComponents[3] + "--" + clientMessageComponents[1] + "--" + clientMessageComponents[2]);
			break;
			
			
			
		case "ISSPONSOR":
			boolean ISSPONSOR = Boolean.parseBoolean(clientMessageComponents[2]);
			server.model.getActivePlayer().isSponsor= ISSPONSOR;
			if(ISSPONSOR) {
				server.sendServerMessage("SERVERMESSAGE--MESSAGE--"+server.model.getActivePlayer()+" has decidied to sponsor. Get ready to quest");
			}else {
				server.sendServerMessage("SERVERMESSAGE--MESSAGE--"+server.model.getActivePlayer()+" has not decidied to sponsor");
				server.model.nextPlayer();
			
			}
			
			break;
			
		case "ISQUESTER":
			boolean ISQUESTER = Boolean.parseBoolean(clientMessageComponents[2]);
			server.model.getActivePlayer().isQuesting= ISQUESTER;
			if(ISQUESTER) {
				server.sendServerMessage("SERVERMESSAGE--MESSAGE--"+server.model.getActivePlayer()+" has decidied to quest");
			}else {
				server.sendServerMessage("SERVERMESSAGE--MESSAGE--"+server.model.getActivePlayer()+" has NOT decidied to quest");
				server.model.nextPlayer();
			
			}
			break;
		case "ENDTURN":
			server.model.endTurn();
			
			break;
			
		case "GETSTATE":
			String stateString = server.model.getState().toString();
			logger.info(stateString);
			out.println("SERVERMESSAGE--GETSTATE--" + stateString);
			break;
		default:
			System.err.println("Unknown message received from server: \"" + clientMessage + "\"");
			break;
		}
	}
}
