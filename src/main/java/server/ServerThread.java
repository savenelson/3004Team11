package server;

import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
	private Socket socket = null;
	private Server server;
	public PrintWriter out;
	public BufferedReader in;
	public int currentPlayer;

	public ServerThread(Socket socket, Server server, int currentPlayer) {
		super("Server");
		System.out.println("ServerThread Created with socket:" + socket);
		this.socket = socket;
		this.server = server;
		this.currentPlayer = currentPlayer;
	}

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
			System.out.println("Client has responded!!!");
			break;
		case "QUEUE":
			server.model.queue(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]));
			server.sendServerMessage("SERVERMESSAGE--UPDATE--" + clientMessageComponents[3] + "--" + clientMessageComponents[1] + "--" + clientMessageComponents[2]);
			break;
		case "PARTY":
			server.model.party(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]));
			break;
		case "DEQUEUE":
			server.model.dequeue(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]));
			break;
		case "UNSTAGE":
			server.model.unstage(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]));
			break;
		case "DISCARD":
			server.model.discard(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]));
			break;
		case "ASSASSINATE":
			server.model.assassinate(clientMessageComponents[2], Integer.parseInt(clientMessageComponents[3]));
			break;
		case "GETSTATE":
			String stateString = server.model.getState().toString();
			System.out.println(stateString);
			out.println("SERVERMESSAGE--GETSTATE--" + stateString);
			break;
		default:
			System.err.println("Unknown message received from server: \"" + clientMessage + "\"");
			break;
		}
	}
}
