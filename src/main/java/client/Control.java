package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Control{
	
	private static final Logger logger = LogManager.getLogger(Control.class);
    private static final int MESSAGE_WAIT_TIME = 500;                   // time to wait between server messages
    private static final String DEFAULT_SERVER_ADDRESS = "localhost";   // default server address
    private static final int DEFAULT_SERVER_PORT = 44444;               // default server port
    private String serverAddress;                                       // server address
    private int serverPort;                                             // server port
    private Socket socket;                                              // socket on server address and port
    private BufferedReader in;                                          // in to server
    private PrintWriter out;											// out from server
    private int playerNumber;											//everyclienthas a unique playernum
	private int numPlayers = 4; 
	Model model;
	View view;
	
	private static String testString;
	
	public Control(View view, String serverAddress, int serverPort) {
		logger.info("Control created");

        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
		this.view = view;
		this.model = new Model(this);
		
        gameInit(null);
		
        try {
            socket = new Socket(serverAddress, serverPort);
    		logger.info("Socket created" + socket);

        } catch (IOException e) {
            System.err.println("No Quests server running on port " + serverPort + " at address " + serverAddress);
            System.exit(1);
        }
        try {
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());    // input stream reader from socket
            in = new BufferedReader(isr);
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		sendClientMessage("CLIENTMESSAGE--HELLO");
		
//		FIXME: WHEN YOU UNCOMMENT THIS, YOU WONT GET THE VIEW TO SHOW, but you'll get and send messages.
//		getServerMessage();  //this will start the readline, and wait for server messages to come in
	}
	
    /**
     * Gets a message sent by the server.
     *
     * @return message sent by the server
     */

    public String getServerMessage() {
    	logger.info("getServerMessage() called");
        String serverMessage = null;
        try {
            Thread.sleep(MESSAGE_WAIT_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (serverMessage == null) {
            try {
                serverMessage = in.readLine();
                processServerMessage(serverMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return serverMessage;
    }

    /**
     * Sends a message to the server.
     *
     * @param clientMessage Message to send to server
     */

    public void sendClientMessage(String clientMessage) {
        out.println(clientMessage);
    }
	
    public void start() {
        System.out.println("Starting Quests client\nServer address: " + serverAddress + "\nServer port: " + serverPort);
    }
	

    /**
     * Changes the client view based on which message was received from the server.
     *
     * @param serverMessage Message received from server
     */

    private void processServerMessage (String serverMessage) {
        String[] serverMessageComponents = serverMessage.split("--");   // array containing the components of the server message
        switch (serverMessageComponents[1]) {
            case "WELCOME":
            	logger.info("welcome recieved!");
                getServerMessage();
                break;
            case "CURRENTPLAYER":
            	model.currentPlayer = Integer.parseInt(serverMessageComponents[2]);
            	logger.info("currentPlayer for client on port:" + socket.getPort() + ", currentPlayer: "+ model.currentPlayer);
                getServerMessage();
                break;
            case "GETSTATE":
//            	model.state = Integer.parseInt(serverMessageComponents[2]);
//            	logger.info("currentPlayer for client on port:" + socket.getPort() + ", currentPlayer: "+ model.currentPlayer);
//                getServerMessage();
//                break;
    		default:
    			System.err.println("Unknown message received from server: \"" + serverMessage + "\"");
    			break;
    		}
    }

    /**
     * Calls the model quitGame method.
     */

    public void quitGame() {
//        model.quitGame();   //still need to write this in the model
    }

	public void gameInit(String args []){
		
		logger.info("gameInit() running");
		
		model.instantiatePlayers(numPlayers);
		
		model.instantiateStages(); 
		
//		model.initialShuffle(); //COMMENT OUT FOR SET SCENEARIOS

//		model.deal(); 			//COMMENT OUT FOR SET SCENEARIOS
		
		model.setScenario1();	//UNCOMMENT FOR SCEN 1
		
//		model.setScenario2();	//UNCOMMENT FOR SCEN 2
		
//		model.setScenarioTest();
		
//		model.eventTesting();
		
		start();
	}

	public void mainLoop(){
		logger.info("mainLoop() running");

		boolean win = false;
		while(!win){
			//TODO send messsage to server with "CLIENTMESSAGE--playGame"
			model.playGame();
			win = !win;
		}
	}
	
	public void updateViewState(){
		logger.debug("updateViewState() called");

		view.updateState();
		
	}
	
	public void stageIncrement(){
		logger.info("stageIncrement() called");

		
		model.stage.nextStage();
		updateViewState();
	//	model.state.toggleForStages = false;
		
	}
	
	public void stageOver(){
		logger.debug("stageOver() called");

		model.stageOver();
	}

	public boolean getSponsorDecision(){
		logger.info("getSponsorDecision() called");

		return view.popup("Player " + (getActivePlayer().getPlayerNumber()+1) + " - Would you like to sponsor this quest?");
	}
	public boolean getQuestingDecision(){
		logger.info("getQuesting() called");

		return view.popup("Player " + (getActivePlayer().getPlayerNumber()+1) + " - Would you like to quest quest?");
	}
	
	public State getState(){
		logger.debug("getState() called");
		return model.getState();
	}
	
	public void getStateString(){
		logger.debug("getState() called");
		sendClientMessage("CLIENTMESSAGE--GETSTATE");
		//return model.getState();
	}

	public void setNumPlayers(int i){
		logger.debug("setNumPlayers() called");

		model.numPlayers = i;
	}
	
	public Player getActivePlayer(){
		logger.debug("getActivePlayer() called");
		
		return model.getActivePlayer();
	}
	
	public void printTestString(){System.out.println(testString);}

	
	
	//FIXME !! Here's where we're adding in the MESSAGES TO SERVER
	public void handClick(String clickType, String ID) {
		logger.debug("handClick() called");

		if(clickType.equals(View.PARTY)){
			sendClientMessage("CLIENTMESSAGE--PARTY--" + ID);
			model.party(ID);
		} 
		else if (clickType.equals(View.STAGE)) {
			sendClientMessage("CLIENTMESSAGE--STAGE--" + ID);
			model.stage(ID);
		} 		
		else if (clickType.equals(View.UNSTAGE)) {
			sendClientMessage("CLIENTMESSAGE--UNSTAGE--" + ID);
			model.unstage(ID);
		} 
		else if (clickType.equals(View.QUEUE)) {
			model.queue(ID);
			sendClientMessage("CLIENTMESSAGE--QUEUE--" + ID);
		} 
		else if (clickType.equals(View.DEQUEUE)) {
			sendClientMessage("CLIENTMESSAGE--DEQUEUE--" + ID);
			model.dequeue(ID);
		}
		else if(clickType.equals(View.DISCARD)){
			sendClientMessage("CLIENTMESSAGE--DISCARD--" + ID);
			model.discard(ID);
		}
		else if(clickType.equals(View.ASSASSINATE)){
			sendClientMessage("CLIENTMESSAGE--ASSASSINATE--" + ID);
			model.assassinate(ID);
		}
	}
	
	public void startStageCycle(){
		logger.debug("startStageCycle() called");

		model.resetCurrentStage();
	}

	public void nextStory(){
		logger.info("nextStory() called");

		model.nextStory();
	}
	
	public void buttonClick(String clickType) {
		logger.debug("buttonClick() called");

		if(clickType.equals(View.STAGE1)){
			model.setCurrentStage(0);
		} else if (clickType.equals(View.STAGE2)) {
			model.setCurrentStage(1);
		} else if (clickType.equals(View.STAGE3)) {
			model.setCurrentStage(2);
		} else if (clickType.equals(View.STAGE4)) {
			model.setCurrentStage(3);
		} else if (clickType.equals(View.STAGE5)) {
			model.setCurrentStage(4);
		} else if(clickType.equals(View.ENDTURN)){
		
			model.endTurn();
		}
	}
	
	public String getSubType(String ID, int currentPlayer){
		logger.debug("getSubType() called");

		return model.getSubType(ID, currentPlayer);
	}

	public void resolveQuest(){
		logger.debug("resolveQuest() called");

		view.resolveQuest();
	}
	
	public void stagesSet(){
		logger.debug("stagesSet() called");

		startStageCycle();

		
	}
	
	public void alert(String message){
		logger.info("alert() called: " + message);

		view.alert(message);
	}

	public void nextPlayer() {
		
		logger.info("next playr");
		
		model.currentState.nextPlayer();
		//view.nextPlayer();
	}


	public View getView() {
		logger.debug("getView() called");

		return view;
	}
	
	public void nextStage() {
		this.stageOver();
		logger.info("Hello this is the model stage in the control "+ model.isDoneQuestingMode);
		if (model.isDoneQuestingMode) {
			view.resolveQuest(); 
			
		}else {
			//move to the next stage
			this.stageIncrement();
			nextPlayer();
		}
		
		
	}
	
}