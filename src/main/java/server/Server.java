package server;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
	private static final Logger logger = LogManager.getLogger(Server.class);
	
    private static final int DEFAULT_PORT = 44444;                      // default server port
    private static final int DEFAULT_PLAYERS_PER_TABLE = 4;             // default number of players per table
    private static final int maxPlayers = 4;							// max players for table
    private int serverPort;                                             // server port
    private int playersPerGame = 4;                                        // number of players per table
	ArrayList<ServerThread> clientThreads = new ArrayList<ServerThread>();
    public ServerModel serverModel;

    /**
     * Constructor for Server object.
     *
     * @param serverPort Server port
     * @param playersPerGame Number of players per table
     */

    public Server(int serverPort, int playersPerGame) {
    	logger.debug("Server(int serverPort, int playersPerGame) called");
    	this.serverPort = serverPort;
    	this.playersPerGame = playersPerGame;
    }

    /**
     * Starts the server and adds connected clients to new tables as new players.
     * 
     * FOR ONE CLIENT ONLY - TESTING MODE
     * @return 
     */
    
    public void setModel(ServerModel serverModel) {
    	logger.debug("setModel(Model model)  called");

    	this.serverModel = serverModel;
    }

    public void start() {
    	logger.debug("start()  called");

        System.out.println("Starting Quests server\nServer port: " + serverPort + "\nPlayers per table: " + playersPerGame);
        
        System.out.println("Listening on port " + serverPort);

    		serverModel = new ServerModel(this);
		serverModel.instantiatePlayers(playersPerGame);

		serverModel.instantiateStages(); //TODO set properly
		
//		serverModel.initialShuffle(); //COMMENT OUT FOR SET SCENEARIOS

//		serverModel.deal(); 			//COMMENT OUT FOR SET SCENEARIOS

//		serverModel.setScenario1();	//UNCOMMENT FOR SCEN 1

//		serverModel.setScenario2();	//UNCOMMENT FOR SCEN 2

		serverModel.setScenarioTest(); //UNCOMMENT FOR end game testing
		
		boolean listening = true;
    		
        try (ServerSocket serverSocket = new ServerSocket(serverPort)){
	    	while(listening) {
	    		for(int g = 0; g<2; g++) {
	        		clientThreads.add(new ServerThread(serverSocket.accept(), this, g));
	        		clientThreads.get(g).start();
	        		
	        		if(clientThreads.size()==2) {
	        			System.out.println("Beginning to Quest lets go model");
	        			serverModel.playGame();
	        		}
	    		}
	    	}
        } catch (IOException e) {
            System.err.println("Could not listen on port " + 44444);
            System.exit(-1);
        }
    }
    
    public void sendServerMessage(String serverMessage) {
    	logger.info(serverMessage);
    	for(ServerThread thread : clientThreads) {
    		logger.info("sending message to player " + thread.currentPlayer);
    		thread.out.println(serverMessage);
    	}
    }
    
    /**
     * Used to send a message to a specific Client 
     * 
     * Like to ask one if a clients to sponsor 
     * 
     * 
     * 
     */
    public void sendServerMessageToOne(String serverMessage, int playerNum) {
    	logger.info(serverMessage + " to " + playerNum);


    		for(ServerThread thread1 : clientThreads) {
    			logger.info("Current threads playernumber: " + thread1.currentPlayer);
    			
    			if(thread1.getPlayerNumber()== playerNum) {
    				thread1.out.println(serverMessage);
    			}
    		}
    	}
    
    /**
     * Used to send a message to a all but one Client 
     */
    public void sendServerMessageToAllButOne(String serverMessage, int playerNum) {
    	logger.info(serverMessage + " to " + playerNum);


    		for(ServerThread thread1 : clientThreads) {
    			logger.info("Current threads playernumber: " + thread1.currentPlayer);
    			
    			if(thread1.getPlayerNumber() != playerNum) {
    				thread1.out.println(serverMessage);
    				
    			}
        		
    		}
    	}
    /**
     * ASk for the current player if they would like to sponsor 
     * 
     * 
     */
    
    public void getSponsorDecision(){
		logger.info("getSponsorDecision() called");

		sendServerMessageToOne("SERVERMESSAGE--GETSPONSOR",serverModel.getActivePlayer().getPlayerNumber());
    
    }
    
    /**
     * ASk for the all the players except the current player if they would like to sponsnor 
     * 
     * 
     */
    public void getQuesterDecison() {
    	
    	sendServerMessageToOne("SERVERMESSAGE--GETQUESTER", serverModel.getActivePlayer().getPlayerNumber());
  
    }
    
    public static void main(String[] args) {
        int serverPort = DEFAULT_PORT;
        int playersPerGame = DEFAULT_PLAYERS_PER_TABLE;

         Server questsServer = new Server(serverPort, playersPerGame);
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
		sendServerMessage("SERVERMESSAGE--RESOLVEQUEST");

//		
	}
	
	public void resolveStage(){
		logger.info("resolveQuest() called");
		sendServerMessage("SERVERMESSAGE--RESOLVESTAGE");

//		
	}

	public void update() {
		// TODO Auto-generated method stub
		//BROKEN FIXME . This is being called from ServerModel and needs to maybe talk to Clients?
	}
}