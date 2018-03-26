package server;

import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
	private Socket socket = null;
	private Server server;
	private int currentPlayer;

	public ServerThread(Socket socket, Server server, int currentPlayer) {
		super("ServerModel");
		System.out.println("ServerThread Created with socket:" + socket);
		this.socket = socket;
		this.server = server;
		this.currentPlayer = currentPlayer;
	}

	public void run() {
		try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
			String clientMessage, outputLine;
			out.println("SERVERMESSAGE--WELCOME");
			out.println("SERVERMESSAGE--CURRENTPLAYER--" + currentPlayer);
			System.out.println("SERVERMESSAGE--WELCOME/n");

			while ((clientMessage = in.readLine()) != null) {
				getClientMessage(clientMessage);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getClientMessage(String clientMessage) {
		String[] clientMessageComponents = clientMessage.split("--"); // array containing the components of the server
																		// message
		switch (clientMessageComponents[1]) {
		case "HELLO":
			System.out.println("Client has responded!!!");
			break;
		case "QUEUE":
			server.model.queue(clientMessageComponents[2]);
			break;
		default:
			System.err.println("Unknown message received from server: \"" + clientMessage + "\"");
			break;
		}
	}
}
