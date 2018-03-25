package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
	private static final Logger logger = LogManager.getLogger(Server.class);
	
    private static final int DEFAULT_PORT = 44444;                      // default server port
    private static final int DEFAULT_PLAYERS_PER_TABLE = 2;             // default number of players per table
    private static final int DEFAULT_STARTING_MONEY = 2500;             // default amount of money players start with
    private static final int DEFAULT_MINIMUM_BET = 500;                 // default minimum player bet
    private static final int DEFAULT_NUMBER_OF_DECKS = 6;               // default number of decks in shoe
    private static final int DEFAULT_MINIMUM_CARDS_BEFORE_SHUFFLE = 78; // default minimum number of cards remaining before shuffling the shoe
    private int serverPort;                                             // server port
    private int playersPerTable;                                        // number of players per table

    /**
     * Constructor for QuestmodelServer object.
     *
     * @param serverPort Server port
     * @param playersPerTable Number of players per table
     * @param startingMoney Amount of money players start with
     * @param minimumBet Minimum player bet
     * @param numberOfDecks Number of decks in shoe
     * @param minimumCardsBeforeShuffle Minimum number of cards remaining before shuffling the shoe
     * @return 
     * @return 
     */

    public Server(int serverPort, int playersPerTable) {
    	this.serverPort = serverPort;
    	this.playersPerTable = playersPerTable;
    }

    /**
     * Starts the server and adds connected clients to new tables as new players.
     */

    public void start() {
        System.out.println("Starting Quests server\nServer port: " + serverPort + "\nPlayers per table: " + playersPerTable);
        ServerSocket serverSocket = null;
        try {
            System.out.println("Creating server socket");
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            System.err.println("Could not start Quests server on port " + serverPort);
            System.exit(1);
        }
        try {
            System.out.println("Listening on port " + serverPort);
            while (true) {
                Table newTable = new Table();
                Thread newTableThread = new Thread(newTable);
                for (int i = 0; i < playersPerTable; i++) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Received request from port " + socket.getPort());
                    ServerPlayer newPlayer = new ServerPlayer(socket, newTable);
                    newTable.addPlayer(newPlayer);
                    Thread newPlayerThread = new Thread(newPlayer);
                    newPlayerThread.start();
                }
                newTableThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method of the server that creates objects and executes other methods.
     *
     * @param args String array of arguments passed to the server
     */

    public static void main(String[] args) {
        int serverPort = DEFAULT_PORT;
        int playersPerTable = DEFAULT_PLAYERS_PER_TABLE;
        int startingMoney = DEFAULT_STARTING_MONEY;
        int minimumBet = DEFAULT_MINIMUM_BET;
        int numberOfDecks = DEFAULT_NUMBER_OF_DECKS;
        int minimumCardsBeforeShuffle = DEFAULT_MINIMUM_CARDS_BEFORE_SHUFFLE;
        for (int i = 0; i < args.length; i += 2) {
        }
        if (playersPerTable < 1) {
            System.err.println("Number of players per table must be at least 1");
            System.exit(1);
        } else if (startingMoney < minimumBet) {
            System.err.println("Amount of starting money cannot be less than minimum bet");
            System.exit(1);
        } else if (numberOfDecks < 1) {
            System.err.println("Number of decks must be at least 1");
            System.exit(1);
        } else if (minimumCardsBeforeShuffle < 0) {
            System.err.println("Minimum cards before shuffle cannot be less than 0");
            System.exit(1);
        }
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