package server;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
	private static final Logger logger = LogManager.getLogger(Server.class);
	
    private static final int DEFAULT_PORT = 44444;                      // default server port
    private static final int DEFAULT_PLAYERS_PER_TABLE = 4;             // default number of players per table
    private static final int maxPlayers = 4;							// max players for table
    private int serverPort;                                             // server port
    private int playersPerTable;                                        // number of players per table
    public Model model;
    /**
     * Constructor for Server object.
     *
     * @param serverPort Server port
     * @param playersPerTable Number of players per table

     */

    public Server(int serverPort, int playersPerTable) {
    	this.serverPort = serverPort;
    	this.playersPerTable = playersPerTable;
    }

    /**
     * Starts the server and adds connected clients to new tables as new players.
     * 
     * FOR ONE CLIENT ONLY - TESTING MODE
     * @return 
     */
    
    public void setModel(Model model) {
    	this.model = model;
    }

    public void start() {
        System.out.println("Starting Quests server\nServer port: " + serverPort + "\nPlayers per table: " + playersPerTable);
        
        System.out.println("Listening on port " + serverPort);
        	
    	model = new Model(this);
		model.instantiatePlayers(playersPerTable);
		model.instantiateStages(); //TODO set properly
		model.setScenarioTest();
    		
		boolean listening = true;
    		
        try (ServerSocket serverSocket = new ServerSocket(serverPort)){
        	
        	while(listening) {
        		for(int g = 0; g<maxPlayers; g++) {
        			System.out.println("g count:" + g);
            		new ServerThread(serverSocket.accept(), this, g).start();
        		}
        	}
        } catch (IOException e) {
            System.err.println("Could not listen on port " + 4444);
            System.exit(-1);
        }
    }
    
    
//ORIGINAL LOOP FOR MORE THAN ONE PLAYER
//    public void start() {
//        System.out.println("Starting Quests server\nServer port: " + serverPort + "\nPlayers per table: " + playersPerTable);
//        ServerSocket serverSocket = null;
//        try {
//            System.out.println("Creating server socket");
//            serverSocket = new ServerSocket(serverPort);
//        } catch (IOException e) {
//            System.err.println("Could not start Quests server on port " + serverPort);
//            System.exit(1);
//        }
//        try {
//            System.out.println("Listening on port " + serverPort);
//            while (true) {
//                Table newTable = new Table();
//                Thread newTableThread = new Thread(newTable);
//                for (int i = 0; i < playersPerTable; i++) {
//                    Socket socket = serverSocket.accept();
//                    System.out.println("Received request from port " + socket.getPort());
//                    ServerPlayer newPlayer = new ServerPlayer(socket, newTable);
//                    newTable.addPlayer(newPlayer);
//                    Thread newPlayerThread = new Thread(newPlayer);
//                    newPlayerThread.start();
//                }
//                newTableThread.start();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Main method of the server that creates objects and executes other methods.
     *
     * @param args String array of arguments passed to the server
     */

    public static void main(String[] args) {
        int serverPort = DEFAULT_PORT;
        int playersPerTable = DEFAULT_PLAYERS_PER_TABLE;

         Server questsServer = new Server(serverPort, playersPerTable);
        questsServer.start();
    }
    
	public void alert(String message){
		logger.info("alert() called: " + message);

//		BROKEN: the below call FIXME to talk to client
//		view.alert(message);
	}

	public void updateViewState(){
		logger.debug("updateViewState() called");
		
//		BROKEN: the below call FIXME to talk to client
//		view.updateState();
		
	}
	
	public void resolveQuest(){
		logger.debug("resolveQuest() called");

//		BROKEN: the below call FIXME to talk to client
//		view.resolveQuest();
	}

	public void update() {
		// TODO Auto-generated method stub
		//BROKEN FIXME . This is being called from ServerModel and needs to maybe talk to Clients?
	}
}