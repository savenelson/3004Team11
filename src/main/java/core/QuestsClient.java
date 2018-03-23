package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QuestsClient {
	private static final Logger logger = LogManager.getLogger(QuestsClient.class);
    private static final String DEFAULT_SERVER_ADDRESS = "localhost";   // default server address
    private static final int DEFAULT_SERVER_PORT = 44444;               // default server port
    private String serverAddress;                                       // server address
    private int serverPort;                                             // server port
	private QuestsClientView view;
	private QuestsClientModel model;
	
	private static String testString;
	
	public QuestsClient(String serverAddress, int serverPort) {
		logger.info("Control created");

        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
		gameInit(null);

//		TEST
//		model.CardsTest();
//		END TEST
	}
	
    public void start() {
        System.out.println("Starting Quests client\nServer address: " + serverAddress + "\nServer port: " + serverPort);
        model = new QuestsClientModel(this, serverAddress, serverPort);
        view = new QuestsClientView(this);
//        getServerMessage();
    }
    
    /**
     * TODO: From BlackJack - and needs to be integrated
     * Gets a message from the server and calls the changeView method with the message.
     */

//    private void getServerMessage() {
//        SwingWorker swingWorker = new SwingWorker<String, String>() {
//            @Override
//            public String doInBackground() throws Exception {
//                return model.getServerMessage();
//            }
//
//            @Override
//            public void done() {
//                try {
//                    changeView(get());
//                } catch (InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        swingWorker.execute();
//    }
//
//    
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
//    /**
//     * Calls the model sendClientMessage method with the given clientMessage.
//     *
//     * @param clientMessage Message to send to server
//     */
//
//    public void sendClientMessage(String clientMessage) {
//        model.sendClientMessage(clientMessage);
//    }
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
//    public static void main(String[] args) {
//        String serverAddress = DEFAULT_SERVER_ADDRESS;
//        int serverPort = DEFAULT_SERVER_PORT;
//        for (int i = 0; i < args.length; i += 2) {
//            String input = args[i];
//            String argument = null;
//            try {
//                argument = args[i + 1];
//            } catch (ArrayIndexOutOfBoundsException e) {
//                System.err.println("Options: [-a serverAddress] [-p serverPort]");
//                System.exit(1);
//            }
//            switch (input) {
//                case "-a":
//                    serverAddress = argument;
//                    break;
//                case "-p":
//                    try {
//                        serverPort = Integer.parseInt(argument);
//                    } catch (NumberFormatException e) {
//                        System.err.println("Server port must be an integer");
//                        System.exit(1);
//                    }
//                    break;
//                default:
//                    System.err.println("Options: [-a serverAddress] [-p serverPort]");
//                    System.exit(1);
//                    break;
//            }
//        }
//        Control controller = new Control(serverAddress, serverPort);
//        controller.start();
//    }
    
  public static void main(String[] args) {
  String serverAddress = DEFAULT_SERVER_ADDRESS;
  int serverPort = DEFAULT_SERVER_PORT;
  QuestsClient controller = new QuestsClient(serverAddress, serverPort);
  controller.start();
}
    
//	public static void main(String [] args){
//		logger.info("main() running");
//
//		launch(args);
//	}
    
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

			model = new QuestsClientModel(this);
			logger.info("passing numPlayers = " + numPlayers + " to model");
			
		} else {
			logger.fatal("number of players ERROR");
		}
		
		model.instantiatePlayers(numPlayers);
		
		model.instantiateStages(); //TODO set properly
		
//		model.initialShuffle(); //COMMENT OUT FOR SET SCENEARIOS

//		model.deal(); 			//COMMENT OUT FOR SET SCENEARIOS
		
		model.setScenario1();	//UNCOMMENT FOR SCEN 1
		
//		model.setScenario2();	//UNCOMMENT FOR SCEN 2
		
//		model.setScenarioTest();
		
		//model.eventTesting();
	}

	public void mainLoop(){
		logger.info("mainLoop() running");

		boolean win = false;
		while(!win){
			model.playGame();
			win = !win;
		}
	}
	
	public void updateViewState(){
		logger.debug("updateViewState() called");

		view.updateState();
		
	}
	
	public void viewUpdate(){
		logger.debug("update() called");
		view.update();
	}
	
	public void stageIncrement(){
		logger.debug("stageIncrement() called");

		model.state.currentStage = model.state.stagePlaceHolder;
		model.state.toggleForStages = false;
		model.resolveStage();
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
	
	public void viewerChanged(){
		logger.debug("viewerChanged() called");

		model.viewerChanged();
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

	public void handClick(String clickType, String ID) {
		logger.debug("handClick() called");

		if(clickType.equals(QuestsClientView.PARTY)){
			model.party(ID);
		} 
		else if (clickType.equals(QuestsClientView.STAGE)) {
			model.stage(ID);
		} 		
		else if (clickType.equals(QuestsClientView.UNSTAGE)) {
			model.unstage(ID);
		} 
		else if (clickType.equals(QuestsClientView.QUEUE)) {
			model.queue(ID);
		} 
		else if (clickType.equals(QuestsClientView.DEQUEUE)) {
			model.dequeue(ID);
		}
		else if(clickType.equals(QuestsClientView.DISCARD)){
			model.discard(ID);
		}
		else if(clickType.equals(QuestsClientView.ASSASSINATE)){
			model.assassinate(ID);
		}
	}
	
	public void startStageCycle(){
		logger.debug("startStageCycle() called");

		model.resetCurrentStage();
	}

	public void nextStory(){
		logger.debug("nextStory() called");

		model.nextStory();
	}
	
	public void resolveStage(){
		logger.debug("resolveStage() called");

		model.resolveStage();
	}
	
	public void buttonClick(String clickType) {
		logger.debug("buttonClick() called");

		if(clickType.equals(QuestsClientView.STAGE1)){
			model.setCurrentStage(0);
		} else if (clickType.equals(QuestsClientView.STAGE2)) {
			model.setCurrentStage(1);
		} else if (clickType.equals(QuestsClientView.STAGE3)) {
			model.setCurrentStage(2);
		} else if (clickType.equals(QuestsClientView.STAGE4)) {
			model.setCurrentStage(3);
		} else if (clickType.equals(QuestsClientView.STAGE5)) {
			model.setCurrentStage(4);
		} else if(clickType.equals(QuestsClientView.ENDTURN)){
		
			model.endTurn();
		}
	}
	
	public void endTurn(){
		model.endTurn();
	};
	
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

		model.stagesSet();
	}
	
	public void alert(String message){
		logger.info("alert() called: " + message);

		view.alert(message);
	}



	public QuestsClientView getView() {
		logger.debug("getView() called");

		return view;
	}
	
}
