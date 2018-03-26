package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private Socket socket;                                                      // socket on server address and port
    private BufferedReader in;                                                  // in to server
    private PrintWriter out;                                                    // out from server
	Model model;
	View view;
	
	private static String testString;
	
	public Control(View view, String serverAddress, int serverPort) {
		logger.info("Control created");

        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
		this.view = view;
		
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

		gameInit(null);
		
		String returnedMessage;
		returnedMessage = getServerMessage();
		logger.info(returnedMessage);
		
		sendClientMessage("CLIENTMESSAGE--HELLO");
		
//		TEST
//		model.CardsTest();
//		END TEST
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
//        model = new Model(this, serverAddress, serverPort);  need to talk about constructor
//        view = new View(this);							   how will view connect
//        getServerMessage();
    }
	
//
//    /**
//     * Changes the client view based on which message was received from the server.
//     *
//     * @param serverMessage Message received from server
//     */
//
//    private void changeView (String serverMessage) {
//        String[] serverMessageComponents = serverMessage.split("--");   // array containing the components of the server message
//        switch (serverMessageComponents[1]) {
//            case "WELCOME":
//                view.showWelcomePanel();
//                getServerMessage();
//                break;
//            case "GETBET":
//                view.setWelcomeWaiting(false);
//                view.setContinuePlayingWaiting(false);
//                view.showBetPanel();
//                view.setBetMoneyLabel(serverMessageComponents[2]);
//                view.setMinimumBetLabel(serverMessageComponents[3]);
//                getServerMessage();
//                break;
//            case "BETRESPONSE":
//                switch (serverMessageComponents[2]) {
//                    case "INVALID":
//                        view.betError("Your bet must be a positive whole number.");
//                        getServerMessage();
//                        break;
//                    case "TOOMUCH":
//                        view.betError("You cannot bet more money than you have.");
//                        getServerMessage();
//                        break;
//                    case "MINIMUM":
//                        view.betError("You must bet at least the minimum amount.");
//                        getServerMessage();
//                        break;
//                    case "SUCCESS":
//                        view.betSuccess();
//                        view.setBetMoneyLabel(serverMessageComponents[3]);
//                        getServerMessage();
//                        break;
//                }
//                break;
//            case "NEWROUND":
//                view.setBetWaiting(false);
//                view.showTurnPanel();
//                view.setTurnMoneyLabel(serverMessageComponents[2]);
//                getServerMessage();
//                break;
//            case "BLACKJACK":
//                switch (serverMessageComponents[2]) {
//                    case "PLAYERANDDEALER":
//                        view.setBlackjackLabel("You and the dealer both have Blackjack!");
//                        getServerMessage();
//                        break;
//                    case "PLAYER":
//                        view.setBlackjackLabel("You have Blackjack!");
//                        getServerMessage();
//                        break;
//                    case "DEALER":
//                        view.setBlackjackLabel("The dealer has Blackjack!");
//                        getServerMessage();
//                        break;
//                    case "DEALERNOBLACKJACK":
//                        view.setBlackjackLabel("The dealer does not have Blackjack.");
//                        getServerMessage();
//                        break;
//                }
//                break;
//            case "NEWDEALERCARD":
//                view.addDealerCard(model.getCardImageLabel(serverMessageComponents[2]));
//                getServerMessage();
//                break;
//            case "GETINSURANCEBET":
//                view.enableInsuranceBet();
//                getServerMessage();
//                break;
//            case "INSURANCEBETRESPONSE":
//                switch (serverMessageComponents[2]) {
//                    case "ERROR":
//                        view.insuranceBetError();
//                        getServerMessage();
//                        break;
//                    case "PLACED":
//                        view.insuranceBetSuccess();
//                        view.setMessageLabel("Insurance Bet: $" + serverMessageComponents[3]);
//                        view.setTurnMoneyLabel(serverMessageComponents[4]);
//                        getServerMessage();
//                        break;
//                    case "NOTPLACED":
//                        view.insuranceBetSuccess();
//                        view.removeInsuranceBetInfo();
//                        getServerMessage();
//                        break;
//                }
//                break;
//            case "CANNOTINSURANCEBET":
//                view.setMessageLabel("You do not have enough money to place an insurance bet.");
//                getServerMessage();
//                break;
//            case "INSURANCEBETWON":
//                view.setMessageLabel("You won $" + serverMessageComponents[2] + " from your insurance bet.");
//                view.setTurnMoneyLabel(serverMessageComponents[3]);
//                getServerMessage();
//                break;
//            case "INSURANCEBETLOST":
//                view.setMessageLabel("You lost your insurance bet.");
//                getServerMessage();
//                break;
//            case "INSURANCEBETDONE":
//                view.setInsuranceBetWaiting(false);
//                getServerMessage();
//                break;
//            case "TAKETURN":
//                view.setTurnWaiting(false);
//                view.removeInsuranceBetInfo();
//                getServerMessage();
//                break;
//            case "NEWHAND":
//                model.addPlayerHandPanel(Integer.parseInt(serverMessageComponents[2]), new BlackjackHandPanel(this));
//                view.addPlayerHandPanel(model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[2])), Integer.parseInt(serverMessageComponents[2]));
//                getServerMessage();
//                break;
//            case "REMOVEHAND":
//                view.removePlayerHandPanel(model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[2])));
//                model.removePlayerHandPanel(Integer.parseInt(serverMessageComponents[2]));
//                getServerMessage();
//                break;
//            case "HANDBET":
//                model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[2])).setHandBet(serverMessageComponents[3]);
//                getServerMessage();
//                break;
//            case "HANDVALUE":
//                model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[2])).setHandValueLabel(serverMessageComponents[3]);
//                getServerMessage();
//                break;
//            case "TURNBLACKJACK":
//                switch (serverMessageComponents[2]) {
//                    case "PLAYERANDDEALER":
//                        view.setBlackjackLabel("You and the dealer both have Blackjack!");
//                        getServerMessage();
//                        break;
//                    case "PLAYER":
//                        view.setBlackjackLabel("You have Blackjack!");
//                        getServerMessage();
//                        break;
//                    case "DEALER":
//                        view.setBlackjackLabel("The dealer has Blackjack!");
//                        getServerMessage();
//                        break;
//                }
//                break;
//            case "NEWPLAYERCARD":
//                model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[2])).addCard(model.getCardImageLabel(serverMessageComponents[3]));
//                getServerMessage();
//                break;
//            case "TURNOPTION":
//                switch (serverMessageComponents[2]) {
//                    case "BOTH":
//                        model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[3])).enableSplitPairs();
//                        model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[3])).enableDoubleDown();
//                        model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[3])).enableHitStand();
//                        getServerMessage();
//                        break;
//                    case "SPLITPAIRS":
//                        model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[3])).enableSplitPairs();
//                        model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[3])).enableHitStand();
//                        getServerMessage();
//                        break;
//                    case "DOUBLEDOWN":
//                        model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[3])).enableDoubleDown();
//                        model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[3])).enableHitStand();
//                        getServerMessage();
//                        break;
//                    case "NEITHER":
//                        model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[3])).enableHitStand();
//                        getServerMessage();
//                        break;
//                }
//                break;
//            case "TURNOPTIONERROR":
//                model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[2])).turnError();
//                getServerMessage();
//                break;
//            case "BUST":
//                model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[2])).bust();
//                getServerMessage();
//                break;
//            case "SPLITPAIRSRESPONSE":
//                switch (serverMessageComponents[2]) {
//                    case "SUCCESS":
//                        view.setTurnMoneyLabel(serverMessageComponents[3]);
//                        getServerMessage();
//                        break;
//                }
//                break;
//            case "DOUBLEDOWNRESPONSE":
//                switch (serverMessageComponents[2]) {
//                    case "SUCCESS":
//                        model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[3])).doubleDownSuccess();
//                        view.setTurnMoneyLabel(serverMessageComponents[4]);
//                        getServerMessage();
//                        break;
//                }
//                break;
//            case "SENDRESULT":
//                view.setTurnWaiting(false);
//                getServerMessage();
//                break;
//            case "REMOVEDEALERFACEDOWNCARD":
//                view.removeDealerFaceDownCard();
//                getServerMessage();
//                break;
//            case "DEALERHANDVALUE":
//                view.setDealerHandValueLabel(serverMessageComponents[2]);
//                getServerMessage();
//                break;
//            case "REMOVEDOUBLEDOWNFACEDOWNCARD":
//                model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[2])).removeDoubleDownFaceDownCard();
//                getServerMessage();
//                break;
//            case "ROUNDRESULT":
//                switch (serverMessageComponents[2]) {
//                    case "BUST":
//                        switch (serverMessageComponents[3]) {
//                            case "TIE":
//                                model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[4])).setHandMessageLabel("You and the dealer both busted. It's a tie!");
//                                view.setTurnMoneyLabel(serverMessageComponents[5]);
//                                getServerMessage();
//                                break;
//                            case "DEALER":
//                                model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[4])).setHandMessageLabel("You busted. The dealer wins!");
//                                view.setTurnMoneyLabel(serverMessageComponents[5]);
//                                getServerMessage();
//                                break;
//                            case "PLAYER":
//                                model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[4])).setHandMessageLabel("The dealer busted. You win!");
//                                view.setTurnMoneyLabel(serverMessageComponents[5]);
//                                getServerMessage();
//                                break;
//                        }
//                        break;
//                    case "NORMAL":
//                        switch (serverMessageComponents[3]) {
//                            case "TIE":
//                                model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[4])).setHandMessageLabel("It's a tie!");
//                                view.setTurnMoneyLabel(serverMessageComponents[5]);
//                                getServerMessage();
//                                break;
//                            case "DEALER":
//                                model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[4])).setHandMessageLabel("The dealer wins!");
//                                view.setTurnMoneyLabel(serverMessageComponents[5]);
//                                getServerMessage();
//                                break;
//                            case "PLAYER":
//                                model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[4])).setHandMessageLabel("You win!");
//                                view.setTurnMoneyLabel(serverMessageComponents[5]);
//                                getServerMessage();
//                                break;
//                        }
//                        break;
//                    case "BLACKJACK":
//                        switch (serverMessageComponents[3]) {
//                            case "TIE":
//                                model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[4])).setHandMessageLabel("You and the dealer both have Blackjack. It's a tie!");
//                                view.setTurnMoneyLabel(serverMessageComponents[5]);
//                                getServerMessage();
//                                break;
//                            case "DEALER":
//                                model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[4])).setHandMessageLabel("The dealer has Blackjack. The dealer wins!");
//                                view.setTurnMoneyLabel(serverMessageComponents[5]);
//                                getServerMessage();
//                                break;
//                            case "PLAYER":
//                                model.getPlayerHandPanel(Integer.parseInt(serverMessageComponents[4])).setHandMessageLabel("You have Blackjack. You win!");
//                                view.setTurnMoneyLabel(serverMessageComponents[5]);
//                                getServerMessage();
//                                break;
//                        }
//                        break;
//                }
//                break;
//            case "GETCONTINUEPLAYING":
//                view.enableContinuePlaying();
//                getServerMessage();
//                break;
//            case "CONTINUEPLAYINGRESPONSE":
//                switch (serverMessageComponents[2]) {
//                    case "ERROR":
//                        view.continuePlayingError();
//                        getServerMessage();
//                        break;
//                    case "CONTINUE":
//                        view.reset();
//                        model.reset();
//                        view.showContinuePlayingPanel();
//                        getServerMessage();
//                        break;
//                }
//                break;
//            case "GAMEOVER":
//                view.showContinuePlayingPanel();
//                view.setContinuePlayingMoneyLabel(serverMessageComponents[2]);
//                view.gameOver();
//                getServerMessage();
//                break;
//            case "WAITING":
//                switch (serverMessageComponents[2]) {
//                    case "WELCOME":
//                        view.setWelcomeWaiting(true);
//                        view.setContinuePlayingWaiting(true);
//                        getServerMessage();
//                        break;
//                    case "BET":
//                        view.setBetWaiting(true);
//                        getServerMessage();
//                        break;
//                    case "INSURANCEBET":
//                        view.setInsuranceBetWaiting(true);
//                        getServerMessage();
//                        break;
//                    case "TURN":
//                        view.setTurnWaiting(true);
//                        getServerMessage();
//                        break;
//                }
//                break;
//            default:
//                System.err.println("Unknown message received from server: \"" + serverMessage + "\"");
//                break;
//        }
//    }
//

//
//    /**
//     * Calls the model quitGame method.
//     */
//
//    public void quitGame() {
//        model.quitGame();
//    }
//
//    /**
//     * Main method of the client that creates objects and executes other methods.
//     *
//     * @param args String array of arguments passed to the client
//     */
//
    
	public void gameInit(String args []){
		
		logger.info("gameInit() running");
		
		int numPlayers;

		//TODO this if/else can use some polish - always passes 4
		if(args == null || args.length == 0){
			numPlayers = 4;
		} else {
			numPlayers = Integer.parseInt(args[0]);
		}
		
		if(numPlayers >= 2 && numPlayers <= 4)
		{

			model = new Model(this);
			logger.info("passing numPlayers = " + numPlayers + " to model");
			
		} else {
			logger.fatal("number of players ERROR");
		}
		
		model.instantiatePlayers(numPlayers);
		
		model.instantiateStages(); //TODO set properly
		
		model.initialShuffle(); //COMMENT OUT FOR SET SCENEARIOS

		model.deal(); 			//COMMENT OUT FOR SET SCENEARIOS
		
//		model.setScenario1();	//UNCOMMENT FOR SCEN 1
		
//		model.setScenario2();	//UNCOMMENT FOR SCEN 2
		
//		model.setScenarioTest();
		
		//model.eventTesting();
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
			model.party(ID);
			//TODO send messsage to server with "CURRENTPLAYER--PARTY--ID"
		} 
		else if (clickType.equals(View.STAGE)) {
			model.stage(ID);
			//TODO send messsage to server with "CURRENTPLAYER--PARTY--ID"
		} 		
		else if (clickType.equals(View.UNSTAGE)) {
			model.unstage(ID);
		} 
		else if (clickType.equals(View.QUEUE)) {
			model.queue(ID);
			sendClientMessage("CLIENTMESSAGE--QUEUE--" + ID);
		} 
		else if (clickType.equals(View.DEQUEUE)) {
			model.dequeue(ID);
		}
		else if(clickType.equals(View.DISCARD)){
			//TODO send message to server with "CLIENTMESSAGE--DISCARD-CURRENTPLAYER-ID"
			model.discard(ID);
		}
		else if(clickType.equals(View.ASSASSINATE)){
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
