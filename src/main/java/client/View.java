package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import server.ServerModel;
import core.AdventureCard;
import core.CardCollection;
import core.FoeCard;
import core.QuestCard;
import core.State;
import core.StoryCard;
import core.WeaponCard;

public class View extends Application {

	public static final String PLAY = "Play";
	public static final String PARTY = "Party";
	public static final String DISCARD = "Discard";
	public static final String STAGE = "Stage";
	public static final String QUEUE = "Queue";
	public static final String DEQUEUE = "Dequeue";
	public static final String ASSASSINATE = "Assassinate";
	public static final String UNSTAGE = "Unstage";

	public static final String STAGE1 = "Stage 1";
	public static final String STAGE2 = "Stage 2";
	public static final String STAGE3 = "Stage 3";
	public static final String STAGE4 = "Stage 4";
	public static final String STAGE5 = "Stage 5";
	public static final String ENDTURN = "End Turn";

	public Client control;
	private State state;

	private static final String DEFAULT_SERVER_ADDRESS = "localhost"; // default server address
	private static final int DEFAULT_SERVER_PORT = 44444; // default server port
	private String serverAddress = DEFAULT_SERVER_ADDRESS; // server address
	private int serverPort = DEFAULT_SERVER_PORT; // server port

	public static final String IMG_DIR = "src/main/resources/core/cards/";
	public static final String GIF = ".gif";

	// CONSTS FOR CANVAS LAYOUT
	public static final int rowPlayer1Rank = 80;
	public static final int colPlayer1Rank = 10;

	public static final int rowPlayer1Party = 230;
	// public static final int colPlayer1Party;

	public static final int rowHandTop6 = 390;
	public static final int colHandTop6 = 10;

	// public static final int colAdventureDeck;

	public static final int rowStoryCard = 80;
	public static final int rowHandBottom6 = 565;
	public static final int colHandBottom6 = 10;

	public static final int rowHandOverflow = 215;

	public static final int rowQueue = 271;
	public static final int colQueue = 230;

	public static final int rowAdventureDeck = 145;
	// public static final int colAdventureDeck;

	public static final int colStoryCard = 10;

	public static final int rowStage = 80;
	public static final int colStage = 340;

	public static final int rowPlayerARank = 271;
	public static final int colPlayerARank = 1200;
	public static final int rowPlayerBRank = 383;
	public static final int colPlayerBRank = 1200;
	public static final int rowPlayerCRank = 495;
	public static final int colPlayerCRank = 1200;
	public static final int rowPlayerDRank = 608;
	public static final int colPlayerDRank = 1200;

	public static final int rowPlayerAParty = 271; // 271
	public static final int colPlayerAParty = 760;
	public static final int rowPlayerBParty = 383; // 383
	public static final int colPlayerBParty = 760;
	public static final int rowPlayerCParty = 495; // 495
	public static final int colPlayerCParty = 760;
	public static final int rowPlayerDParty = 608; // 608
	public static final int colPlayerDParty = 760;

	public static final int cardSmallHeight = 112;
	public static final int cardSmallWidth = 80;
	public static final int cardMediumHeight = 160;
	public static final int cardMediumWidth = 115;
	public static final int cardLargeHeight = 200;
	public static final int cardLargeWidth = 150;
	public static final int cardXLargeHeight = 300;
	public static final int cardXLargeWidth = 225;

	private static final Logger logger = LogManager.getLogger(View.class);

	private ImageView imgView;

	private Stage stage;

	private Pane canvas;
	private TilePane tile;

	public View() {
		logger.info("View created");

	}

	public static void main(String[] args) {
		logger.info("main() running");
		launch(args);
	}

	private HBox Stage;

	public boolean popup(String message) {
		logger.debug("popup() called");
		boolean response;
		ButtonType yesButton = new ButtonType("Yes");
		ButtonType noButton = new ButtonType("No");
		Alert alert = new Alert(AlertType.CONFIRMATION, message, yesButton, noButton);
		Optional<ButtonType> yesOption = alert.showAndWait();
		if (yesOption.isPresent() && yesOption.get() == yesButton) {
			logger.info("Yes clicked");
			response = true;
		} else {
			// control.buttonClick(ENDTURN);
			logger.info("No clicked");
			response = false;
		}
		return response;
	}
	
	public void info(String message) {
		logger.debug("info() called");


		Alert alert = new Alert(AlertType.INFORMATION, message);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {

		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		logger.info("start() running");

		control = new Client(this, serverAddress, serverPort);
		stage = primaryStage;
		initUI(primaryStage);
	}

	private void update(Stage primaryStage) {
		logger.debug("update(Stage) called");

		state = control.getState();
		canvas = new Pane();
		canvas.setId("pane");
		canvas = drawCards(canvas);
		addStage(canvas);
		Scene scene = new Scene(canvas, 1280, 720);
		scene.getStylesheets().add("style.css");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);

		primaryStage.setTitle("Quests of the Round Table - Player " + (state.currentPlayer ));
		logger.info("Current View: Player " + state.currentPlayer);

		primaryStage.show();
	}

	public void update() {
		logger.debug("update() called");

		update(stage);
		control.mainLoop();
	}

	public void updateState() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				logger.debug("updateState() called");

				state = control.getState();
				update(stage);
			}
		});
	}

	public Pane drawCards(Pane canvas) {

		this.state = control.getState();
		addControlsToCanvas(canvas);
		addQueueToCanvas(canvas);
		addPlayerARankToCanvas(canvas);
		addPlayerBRankToCanvas(canvas);
		addShieldsAToCanvas(canvas);
		addPlayerAPartyToCanvas(canvas);
		addPlayerBPartyToCanvas(canvas);
		addShieldsBToCanvas(canvas);

		if (state.numPlayers == 3) {
			addPlayerCRankToCanvas(canvas);
			addPlayerCPartyToCanvas(canvas);
			addShieldsCToCanvas(canvas);
		}

		if (state.numPlayers == 4) {
			addPlayerCRankToCanvas(canvas);
			addPlayerCPartyToCanvas(canvas);
			addShieldsCToCanvas(canvas);
			addPlayerDRankToCanvas(canvas);
			addPlayerDPartyToCanvas(canvas);
			addShieldsDToCanvas(canvas);
		}

		addStoryCardToCanvas(canvas);
		addHandToCanvas(canvas);
		addStageToCanvas(canvas);
		addStage(canvas);
		return canvas;
	}

	private void initUI(Stage primaryStage) {
		logger.debug("initUI() called");

		state = control.getState();
		canvas = new Pane();
		canvas.setId("pane");
		canvas = drawCards(canvas);
		Scene scene = new Scene(canvas, 1280, 720);
		scene.getStylesheets().add("style.css");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Quests of the Round Table");
		primaryStage.show();
	}

	private void addHandToCanvas(Pane canvas) {
		logger.debug("addHandToCanvas() called");

		CardCollection<AdventureCard> hand = null;
		hand = state.players[state.currentPlayer].getHand();
		tile = new TilePane();
		tile.setPrefRows(2);
		tile.setPrefColumns(6);
		tile.setVgap(10);
		tile.setHgap(10);

		for (int i = 0; i < hand.size(); ++i) {
			try {
				Image img = new Image(new FileInputStream(IMG_DIR + hand.get(i).getImgName() + GIF));
				imgView = new ImageView();
				imgView.setId(hand.get(i).getID());
				imgView.setImage(img);
				imgView.relocate(colPlayerARank, rowPlayerARank);
				imgView.setFitWidth(cardMediumWidth);
				imgView.setFitHeight(cardMediumHeight);
				imgView.setPreserveRatio(true);
				setHandCardControl(imgView);
				tile.getChildren().add(imgView);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		tile.relocate(colHandTop6, rowHandTop6);
		canvas.getChildren().add(tile);
	}

	private void addStageToCanvas(Pane canvas) {
		logger.debug("addStageToCanvas() called");

		state = control.getState();
		CardCollection<AdventureCard> stage = state.stage;
		
		if (state.players[state.currentPlayer].isSponsor) {
			tile = new TilePane();
			tile.setPrefRows(1);
			tile.setPrefColumns(8);
			tile.setVgap(10);
			tile.setHgap(10);
			for (int i = 0; i < stage.size(); ++i) {
				try {
					Image img = new Image(new FileInputStream(IMG_DIR + stage.get(i).getImgName() + GIF));
					imgView = new ImageView();
					imgView.setId(stage.get(i).getID());
					imgView.setImage(img);
					imgView.relocate(colStage, rowStage);
					imgView.setFitWidth(cardMediumWidth);
					imgView.setFitHeight(cardMediumHeight);
					imgView.setPreserveRatio(true);
					setStageCardControl(imgView);
					tile.getChildren().add(imgView);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			tile.relocate(colStage, rowStage);

			canvas.getChildren().add(tile);
		} else if (state.isTournamenting && ((StoryCard) state.currentStoryCard).getSubType().equals(StoryCard.TOURNAMENT)) {
			

			state = control.getState();
			// stage = state.stages;
			Label queueCardsLabel;
			Label stageLabel;
		
			stageLabel = new Label("You Have entered the Tournament ");
	

			stageLabel.setFont(Font.font("Serif", FontWeight.BOLD, 60));
			stageLabel.relocate(colStage, rowStage + 20);

			queueCardsLabel = new Label("Queue your cards for The Tournament");
			queueCardsLabel.setFont(Font.font("Serif", FontWeight.BOLD, 32));
			queueCardsLabel.relocate(colStage + 95, rowStage + 100);
			canvas.getChildren().add(stageLabel);
			canvas.getChildren().add(queueCardsLabel);
		}else if (state.isQuesting && ((StoryCard) state.currentStoryCard).getSubType().equals(StoryCard.QUEST)) {
			state = control.getState();
			// stage = state.stages;
			Label queueCardsLabel;
			Label stageLabel;
			if (stage.size() > 1)
				stageLabel = new Label("Stage " + (state.currentStage ) + ": " + stage.size() + " cards");
			else
				stageLabel = new Label("Stage " + (state.currentStage ) + ": " + stage.size() + " card");

			stageLabel.setFont(Font.font("Serif", FontWeight.BOLD, 60));
			stageLabel.relocate(colStage + 100, rowStage + 20);

			queueCardsLabel = new Label("Queue your cards for Stage " + (state.currentStage ));
			queueCardsLabel.setFont(Font.font("Serif", FontWeight.BOLD, 32));
			queueCardsLabel.relocate(colStage + 95, rowStage + 100);
			canvas.getChildren().add(stageLabel);
			canvas.getChildren().add(queueCardsLabel);
		}
	}

	public void nextPlayer() {

		Label playerLabel = new Label("Player " + (control.getActivePlayer().getPlayerNumber() ) + " ready?");
		playerLabel.setFont(new Font("Ariel", 30));

		Button readyButton = new Button("Ready");
		readyButton.setFont(new Font("Ariel", 30));

		readyButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				logger.info("Ready clicked");

				control.view.update();
			}
		});
		StackPane layout = new StackPane();
		layout.getChildren().addAll(playerLabel, readyButton);
		layout.setPrefHeight(720);
		layout.setPrefWidth(1280);

		readyButton.setTranslateY(65);
		playerLabel.setTranslateY(-45);

		Scene scene = new Scene(layout);
		scene.getStylesheets().add("style.css");
		stage.setScene(scene);

	}

	public void resolveQuest() {
		logger.info("resolveQuest() called");

		StackPane layout = new StackPane();
		state = control.getState();
		int numShields = ((QuestCard) state.currentStoryCard).getNumStages();

		for (int i = 0; i < state.numPlayers; ++i) {
			if (!state.players[i].isSponsor) {
				Label passed = new Label("Player " + i);

				if (state.players[i].passedStage) {

					passed.setText(passed.getText() + " passed Quest and receives " + numShields + " shields!");
				} else {
					passed.setText(passed.getText() + " failed Quest and receives 0 shields.");
				}
				passed.setFont(new Font("Ariel", 30));
				layout.getChildren().add(passed);
				layout.setPrefHeight(720);
				layout.setPrefWidth(1280);
				passed.setTranslateY(-180 + (60 * i));
			}
		}
		Button nextStageButton = new Button("Next Stage");
		nextStageButton.setFont(new Font("Ariel", 30));
		layout.getChildren().add(nextStageButton);
		nextStageButton.setTranslateY(65);
		nextStageButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				logger.info("nextStageButton clicked");
			
				control.stageOver();
				control.clientModel.getCurrentState().reset();
				
				control.sendClientMessage("CLIENTMESSAGE--NEXTSTAGE--");
				//update(stage);
			}
		});
		Scene scene = new Scene(layout);
		scene.getStylesheets().add("style.css");
		stage.setScene(scene);
		// update(stage);
	}

	private void addQueueToCanvas(Pane canvas) {
		logger.debug("addQueueToCanvas() called");

		CardCollection<AdventureCard> queue = state.players[state.currentPlayer].getQueue();

		tile = new TilePane();
		tile.setPrefRows(1);
		tile.setPrefColumns(6);
		tile.setVgap(10);
		tile.setHgap(10);

		if (queue != null) {
			for (int i = 0; i < queue.size(); ++i) {
				try {
					Image img = new Image(new FileInputStream(IMG_DIR + queue.get(i).getImgName() + GIF));
					imgView = new ImageView();
					imgView.setId(queue.get(i).getID());
					imgView.setImage(img);
					imgView.relocate(colQueue, rowQueue);
					imgView.setFitWidth(cardSmallWidth);
					imgView.setFitHeight(cardSmallHeight);
					imgView.setPreserveRatio(true);
					setQueueCardControl(imgView);
					tile.getChildren().add(imgView);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			tile.relocate(colQueue, rowQueue);

			canvas.getChildren().add(tile);
		}
	}

	private void addPlayerARankToCanvas(Pane canvas) {
		logger.debug("addPlayerARankToCanvas() called");

		try {
			Image i = new Image(new FileInputStream(IMG_DIR + state.players[0].getRank().getImgName() + GIF));
			imgView = new ImageView();
			imgView.setImage(i);
			imgView.relocate(colPlayerARank, rowPlayerARank);
			imgView.setFitWidth(cardSmallWidth);
			imgView.setFitHeight(cardSmallHeight);
			imgView.setPreserveRatio(true);
			setRankControl(imgView, state.players[0].getHand().size());
			canvas.getChildren().addAll(imgView);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void addPlayerBRankToCanvas(Pane canvas) {
		logger.debug("addPlayerBRankToCanvas() called");

		try {
			Image i = new Image(new FileInputStream(IMG_DIR + state.players[1].getRank().getImgName() + GIF));
			imgView = new ImageView();
			imgView.setImage(i);
			imgView.relocate(colPlayerBRank, rowPlayerBRank);
			imgView.setFitWidth(cardSmallWidth);
			imgView.setFitHeight(cardSmallHeight);
			imgView.setPreserveRatio(true);
			setRankControl(imgView, state.players[1].getHand().size());
			canvas.getChildren().addAll(imgView);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void addPlayerCRankToCanvas(Pane canvas) {
		logger.debug("addPlayerCRankToCanvas() called");

		try {
			Image i = new Image(new FileInputStream(IMG_DIR + state.players[2].getRank().getImgName() + GIF));
			imgView = new ImageView();
			imgView.setImage(i);
			imgView.relocate(colPlayerCRank, rowPlayerCRank);
			imgView.setFitWidth(cardSmallWidth);
			imgView.setFitHeight(cardSmallHeight);
			imgView.setPreserveRatio(true);
			setRankControl(imgView, state.players[2].getHand().size());
			canvas.getChildren().addAll(imgView);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void addPlayerDRankToCanvas(Pane canvas) {
		logger.debug("addPlayerDRankToCanvas() called");

		try {
			Image i = new Image(new FileInputStream(IMG_DIR + state.players[3].getRank().getImgName() + GIF));
			imgView = new ImageView();
			imgView.setImage(i);
			imgView.relocate(colPlayerDRank, rowPlayerDRank);
			imgView.setFitWidth(cardSmallWidth);
			imgView.setFitHeight(cardSmallHeight);
			imgView.setPreserveRatio(true);
			setRankControl(imgView, state.players[3].getHand().size());
			canvas.getChildren().addAll(imgView);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void addPlayerAPartyToCanvas(Pane canvas) {
		logger.debug("addPlayerAPartyToCanvas() called");

		CardCollection<AdventureCard> party = state.players[0].getParty();

		tile = new TilePane();
		tile.setPrefRows(1);
		tile.setPrefColumns(5);
		tile.setVgap(10);
		tile.setHgap(10);

		if (party != null) {
			for (int i = 0; i < party.size(); ++i) {
				try {
					Image img = new Image(new FileInputStream(IMG_DIR + party.get(i).getImgName() + GIF));
					imgView = new ImageView();
					imgView.setId(party.get(i).getID());
					imgView.setImage(img);
					imgView.relocate(colPlayerAParty, rowPlayerAParty);
					imgView.setFitWidth(cardSmallWidth);
					imgView.setFitHeight(cardSmallHeight);
					imgView.setPreserveRatio(true);
					setPartyCardControl(imgView);
					tile.getChildren().add(imgView);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			tile.relocate(colPlayerAParty, rowPlayerAParty);

			canvas.getChildren().add(tile);
		}
	}

	private void addPlayerBPartyToCanvas(Pane canvas) {
		logger.debug("addPlayerBPartyToCanvas() called");

		CardCollection<AdventureCard> party = state.players[1].getParty();

		tile = new TilePane();
		tile.setPrefRows(1);
		tile.setPrefColumns(5);
		tile.setVgap(10);
		tile.setHgap(10);

		if (party != null) {
			for (int i = 0; i < party.size(); ++i) {
				try {
					Image img = new Image(new FileInputStream(IMG_DIR + party.get(i).getImgName() + GIF));
					imgView = new ImageView();
					imgView.setId(party.get(i).getID());
					imgView.setImage(img);
					imgView.relocate(colPlayerBParty, rowPlayerBParty);
					imgView.setFitWidth(cardSmallWidth);
					imgView.setFitHeight(cardSmallHeight);
					imgView.setPreserveRatio(true);
					setPartyCardControl(imgView);
					tile.getChildren().add(imgView);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			tile.relocate(colPlayerBParty, rowPlayerBParty);

			canvas.getChildren().add(tile);
		}
	}

	private void addPlayerCPartyToCanvas(Pane canvas) {
		logger.debug("addPlayerCPartyToCanvas() called");

		CardCollection<AdventureCard> party = state.players[2].getParty();

		tile = new TilePane();
		tile.setPrefRows(1);
		tile.setPrefColumns(5);
		tile.setVgap(10);
		tile.setHgap(10);

		if (party != null) {
			for (int i = 0; i < party.size(); ++i) {
				try {
					Image img = new Image(new FileInputStream(IMG_DIR + party.get(i).getImgName() + GIF));
					imgView = new ImageView();
					imgView.setId(party.get(i).getID());
					imgView.setImage(img);
					imgView.relocate(colPlayerCParty, rowPlayerCParty);
					imgView.setFitWidth(cardSmallWidth);
					imgView.setFitHeight(cardSmallHeight);
					imgView.setPreserveRatio(true);
					setPartyCardControl(imgView);
					tile.getChildren().add(imgView);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			tile.relocate(colPlayerCParty, rowPlayerCParty);

			canvas.getChildren().add(tile);
		}
	}

	private void addPlayerDPartyToCanvas(Pane canvas) {
		logger.debug("addPlayerDPartyToCanvas() called");

		CardCollection<AdventureCard> party = state.players[3].getParty();

		tile = new TilePane();
		tile.setPrefRows(1);
		tile.setPrefColumns(5);
		tile.setVgap(10);
		tile.setHgap(10);

		if (party != null) {
			for (int i = 0; i < party.size(); ++i) {
				try {
					Image img = new Image(new FileInputStream(IMG_DIR + party.get(i).getImgName() + GIF));
					imgView = new ImageView();
					imgView.setId(party.get(i).getID());
					imgView.setImage(img);
					imgView.relocate(colPlayerDParty, rowPlayerDParty);
					imgView.setFitWidth(cardSmallWidth);
					imgView.setFitHeight(cardSmallHeight);
					imgView.setPreserveRatio(true);
					setPartyCardControl(imgView);
					tile.getChildren().add(imgView);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			tile.relocate(colPlayerDParty, rowPlayerDParty);

			canvas.getChildren().add(tile);
		}
	}

	private void addStoryCardToCanvas(Pane canvas) {
		logger.debug("addStoryCardToCanvas() called");

		try {
			Image i = new Image(new FileInputStream(IMG_DIR + state.currentStoryCard.getImgName() + GIF));
			imgView = new ImageView();
			imgView.setImage(i);
			imgView.relocate(colStoryCard, rowStoryCard);
			imgView.setFitWidth(cardXLargeWidth);
			imgView.setFitHeight(cardXLargeHeight);
			imgView.setPreserveRatio(true);
			canvas.getChildren().addAll(imgView);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// canvas.getChildren().addAll(imgView);
	}

	private void addShieldsAToCanvas(Pane canvas) {
		logger.debug("addShieldsAToCanvas() called");

		String playerA = Integer.toString(state.players[0].getShieldCount());
		Label shieldsPlayerA = new Label(playerA);
		shieldsPlayerA.setFont(Font.font("Serif", FontWeight.BOLD, 30));
		shieldsPlayerA.relocate(colPlayerARank + 11, rowPlayerARank + 72);

		canvas.getChildren().addAll(shieldsPlayerA);
	}

	private void addShieldsBToCanvas(Pane canvas) {
		logger.debug("addShieldsBToCanvas() called");

		String playerA = Integer.toString(state.players[1].getShieldCount());
		Label shieldsPlayerA = new Label(playerA);
		shieldsPlayerA.setFont(Font.font("Serif", FontWeight.BOLD, 30));
		shieldsPlayerA.relocate(colPlayerBRank + 11, rowPlayerBRank + 72);

		canvas.getChildren().addAll(shieldsPlayerA);
	}

	private void addShieldsCToCanvas(Pane canvas) {
		logger.debug("addShieldsCToCanvas() called");

		String playerA = Integer.toString(state.players[2].getShieldCount());
		Label shieldsPlayerA = new Label(playerA);
		shieldsPlayerA.setFont(Font.font("Serif", FontWeight.BOLD, 30));
		shieldsPlayerA.relocate(colPlayerCRank + 11, rowPlayerCRank + 72);

		canvas.getChildren().addAll(shieldsPlayerA);
	}

	private void addShieldsDToCanvas(Pane canvas) {
		logger.debug("addShieldsDToCanvas() called");

		String playerA = Integer.toString(state.players[3].getShieldCount());
		Label shieldsPlayerA = new Label(playerA);
		shieldsPlayerA.setFont(Font.font("Serif", FontWeight.BOLD, 30));
		shieldsPlayerA.relocate(colPlayerDRank + 11, rowPlayerDRank + 72);

		canvas.getChildren().addAll(shieldsPlayerA);
	}

	private void setStageCardControl(final ImageView anAdventure) {
		logger.debug("setStageCardControl() called");

		final ContextMenu fileMenu = new ContextMenu();

		EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				control.handClick(((MenuItem) event.getSource()).getText(), anAdventure.getId());
				state = control.getState();

				update(stage);

				// addHandToCanvas(canvas);
			}
		};

		MenuItem stageItem = new MenuItem(UNSTAGE);
		stageItem.setOnAction(eh);
		fileMenu.getItems().add(stageItem);

		anAdventure.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			public void handle(MouseEvent t) {
				if (t.getButton() == MouseButton.SECONDARY) {
					fileMenu.show(anAdventure, t.getScreenX(), t.getScreenY());
				}
			}
		});
	}

	public void alert(String message) {
		logger.debug("alert() called ");
		Platform.runLater( new Runnable() {
			public void run() {
				Alert alert = new Alert(AlertType.ERROR, message);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {

				}
				
			}
		});

		
	}

	private void setHandCardControl(final ImageView anAdventure) {
		logger.debug("setHandCardControl() called");

		final ContextMenu fileMenu = new ContextMenu();

		EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				state = control.getState();
				control.handClick(((MenuItem) event.getSource()).getText(), anAdventure.getId());
				update(stage);
			}
		};

		state = control.getState();

		String subType = control.getSubType(anAdventure.getId(), state.currentPlayer);

		if (subType.equals(AdventureCard.ALLY) || subType.equals(AdventureCard.AMOUR)) {
			MenuItem playItem = new MenuItem(PARTY);
			playItem.setOnAction(eh);
			fileMenu.getItems().add(playItem);
		}

		else if ((subType.equals(AdventureCard.FOE) || subType.equals(AdventureCard.TEST))
				&& state.players[state.currentPlayer].isSponsor) {
			MenuItem playItem = new MenuItem(STAGE);
			playItem.setOnAction(eh);
			fileMenu.getItems().add(playItem);
		}

		else if (subType.equals(AdventureCard.WEAPON)) {

			if (state.players[state.currentPlayer].isSponsor) {
				MenuItem stageItem = new MenuItem(STAGE);
				stageItem.setOnAction(eh);
				fileMenu.getItems().add(stageItem);
			} else {
				MenuItem queueItem = new MenuItem(QUEUE);
				queueItem.setOnAction(eh);
				fileMenu.getItems().add(queueItem);
			}
		}

		MenuItem discardItem = new MenuItem(DISCARD);
		discardItem.setOnAction(eh);
		fileMenu.getItems().add(discardItem);

		anAdventure.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			public void handle(MouseEvent t) {
				if (t.getButton() == MouseButton.SECONDARY) {
					fileMenu.show(anAdventure, t.getScreenX(), t.getScreenY());
					state = control.getState();
				}
			}
		});
	}

	private void setQueueCardControl(final ImageView anAdventure) {
		logger.debug("setQueueCardControl() called");

		final ContextMenu fileMenu = new ContextMenu();

		EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				control.handClick(((MenuItem) event.getSource()).getText(), anAdventure.getId());
				state = control.getState();
				update(stage);

				// addHandToCanvas(canvas);
			}
		};

		MenuItem queueItem = new MenuItem(DEQUEUE);
		queueItem.setOnAction(eh);
		fileMenu.getItems().add(queueItem);

		anAdventure.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			public void handle(MouseEvent t) {
				if (t.getButton() == MouseButton.SECONDARY) {
					fileMenu.show(anAdventure, t.getScreenX(), t.getScreenY());
				}
			}
		});
	}

	private void setPartyCardControl(final ImageView anAlly) {
		logger.debug("setPartyCardControl() called");

		final ContextMenu fileMenu = new ContextMenu();

		EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				control.handClick(((MenuItem) event.getSource()).getText(), anAlly.getId());

				state = control.getState();
				update(stage);
			}
		};

		MenuItem assassinateAlly = new MenuItem(ASSASSINATE);
		assassinateAlly.setOnAction(eh);
		fileMenu.getItems().add(assassinateAlly);

		anAlly.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			public void handle(MouseEvent t) {
				if (t.getButton() == MouseButton.SECONDARY) {
					fileMenu.show(anAlly, t.getScreenX(), t.getScreenY());
				}
			}
		});
	}

	private void addStage(Pane canavas) {
		logger.debug("addStage() called");

		Stage = new HBox();

		Stage.relocate(colStage, rowStage);

		canavas.getChildren().addAll(Stage);

	}

	private void addCardToStage(HBox stage, ImageView newCard) {
		logger.debug("addCardToStage() called");

		ImageView cardAdded = new ImageView();
		cardAdded.setImage(newCard.getImage());
		cardAdded.setFitHeight(12);
		stage.getChildren().add(cardAdded);
	}

	private void setRankControl(final ImageView aRankCard, final int numberOfcards) {
		logger.debug("setRankControl() called");

		aRankCard.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				Rectangle rect = new Rectangle(0, 0, 100, 100);
				Tooltip t = new Tooltip("This player has " + numberOfcards + " in their hands");
				Tooltip.install(rect, t);
				Tooltip.install(aRankCard, t);
			}
		});
	}

	private void addControlsToCanvas(Pane canvas) {
		logger.debug("addControlsToCanvas() called");

		// our coordinates
		Button[] stageButtons = new Button[5];
		Button stage1 = new Button(STAGE1);
		stageButtons[0] = stage1;
		stage1.relocate(240, 80);
		stage1.setMinWidth(80);
		stage1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				logger.info("Stage 1 clicked");
				control.buttonClick(STAGE1);
				state = control.getState();

				update(stage);
			}
		});
		Button stage2 = new Button(STAGE2);
		stageButtons[1] = stage2;
		stage2.relocate(240, 110);
		stage2.setMinWidth(80);
		stage2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				logger.info("Stage 2 clicked");
				control.buttonClick(STAGE2);
				state = control.getState();
				update(stage);
			}
		});
		Button stage3 = new Button(STAGE3);
		stageButtons[2] = stage3;
		stage3.relocate(240, 140);
		stage3.setMinWidth(80);
		stage3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				logger.info("Stage 3 clicked");
				control.buttonClick(STAGE3);
				state = control.getState();
				update(stage);
			}
		});
		Button stage4 = new Button(STAGE4);
		stageButtons[3] = stage4;
		stage4.relocate(240, 170);
		stage4.setMinWidth(80);
		stage4.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				logger.info("Stage 4 clicked");
				control.buttonClick(STAGE4);
				state = control.getState();
				update(stage);
			}
		});
		Button stage5 = new Button(STAGE5);
		stageButtons[4] = stage5;
		stage5.relocate(240, 200);
		stage5.setMinWidth(80);
		stage5.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				logger.info("Stage 5 clicked");
				control.buttonClick(STAGE5);
				state = control.getState();
				update(stage);
			}
		});
		Button endTurn = new Button(ENDTURN);
		endTurn.relocate(240, 230);
		endTurn.setMinWidth(80);
		endTurn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				logger.info("End Turn clicked");
				state = control.getState();
				control.buttonClick(ENDTURN);
			}
		});

		if ((state.players[state.currentPlayer].isSponsor
				&& ((StoryCard) state.currentStoryCard).getSubType().equals(StoryCard.QUEST))) {
			int numStages = ((QuestCard) state.currentStoryCard).getNumStages();
			for (int i = 4; i != numStages - 1; i--) {
				stageButtons[i].setDisable(true);
			}
			canvas.getChildren().addAll(stage1, stage2, stage3, stage4, stage5, endTurn);
		} else {
			canvas.getChildren().add(endTurn);
		}
	}

	public void stageResolved() {
		logger.debug("stageResolved() called");
		logger.info("Stage Over");

		final StackPane layout = new StackPane();
		state = control.getState();

		for (int i = 0; i < state.numPlayers; ++i) {
			logger.info("players booleans: " + state.players[i].passedStage);
		}
			
		for (int i = 0; i < state.numPlayers; ++i) {

			if (!state.players[i].isSponsor) {
				Label passed = new Label("Player " + i);
				if (state.players[i].passedStage) {
					passed.setText(passed.getText() + " passed stage " + state.currentStage);

				} else {
					passed.setText(passed.getText() + " failed stage " + state.currentStage);

				}
				passed.setFont(new Font("Ariel", 30));
				layout.getChildren().add(passed);
				layout.setPrefHeight(720);
				layout.setPrefWidth(1280);
				passed.setTranslateY(-180 + (60 * i));
				if (state.players[i].passedStage) {
					logger.info("Player " + i + " passed stage  " + state.currentStage);
				} else {
					logger.info("Player " + i + " failed stage " + state.currentStage );
				}
			}
		}
		Button nextStageButton = new Button("Next Stage");
		nextStageButton.setFont(new Font("Ariel", 30));
		layout.getChildren().add(nextStageButton);
		nextStageButton.setTranslateY(65);
		nextStageButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				logger.info("nextStageButton clicked");

				control.nextStage();

			}
		});

		Button showCardsButton = new Button("Show Cards");
		showCardsButton.setFont(new Font("Ariel", 30));
		layout.getChildren().add(showCardsButton);
		showCardsButton.setTranslateY(140);
		showCardsButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				logger.info("showCardsButton clicked");

				state = control.getState();

				ShowResoultionView resolution = new ShowResoultionView(layout, state, control.getView());
				sceneChange(resolution);
			}
		});
		Scene scene = new Scene(layout);
		scene.getStylesheets().add("style.css");

		stage.setScene(scene);
		// update(stage);
	}
	
	
	public void tournamentResolved() {
			logger.info("Tournament resolving ");

			final StackPane layout = new StackPane();
			state = control.getState();
			
			for (int i = 0; i < state.numPlayers; ++i) {

					Label passed = new Label("Player " + i);
					if (state.players[i].isTournamentWinner) {
						passed.setText(passed.getText() + "  is the winner of the Tournament ");
					} else {
						passed.setText(passed.getText() + " has lost the tournament");
					}
					passed.setFont(new Font("Ariel", 30));
					layout.getChildren().add(passed);
					layout.setPrefHeight(720);
					layout.setPrefWidth(1280);
					passed.setTranslateY(-180 + (60 * i));
					if (state.players[i].isTournamentWinner) {
						logger.info("Player " + i + " won the Tournament!");
					} else {
						logger.info("Player " + i + " failed the Tournament");
					}
			}
			Button nextStageButton = new Button("Next");
			nextStageButton.setFont(new Font("Ariel", 30));
			layout.getChildren().add(nextStageButton);
			nextStageButton.setTranslateY(65);
			nextStageButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					logger.info("nextStageButton clicked");

					control.stageOver();
					control.clientModel.getCurrentState().reset();
					
					control.sendClientMessage("CLIENTMESSAGE--NEXTSTAGE--");
				}
			});

			Button showCardsButton = new Button("Show Cards");
			showCardsButton.setFont(new Font("Ariel", 30));
			layout.getChildren().add(showCardsButton);
			showCardsButton.setTranslateY(140);
			showCardsButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					logger.info("showCardsButton clicked");

					state = control.getState();

					ShowResoultionView resolution = new ShowResoultionView(layout, state, control.getView());
					sceneChange(resolution);
				}
			});
			Scene scene = new Scene(layout);
			scene.getStylesheets().add("style.css");

			stage.setScene(scene);
			// update(stage);
		
	}

	public void sceneChange(Pane newScreen) {
		logger.debug("sceneChange() called");

		newScreen.setId("pane");

		Scene scene = new Scene(newScreen, 1280, 720);

		scene.getStylesheets().add("style.css");
		stage.setScene(scene);
	}

}