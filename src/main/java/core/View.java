package core;

import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;


public class View extends Application {
	
	public static final String PLAY = "Play";
	public static final String DISCARD = "Discard";
	public static final String STAGE = "Stage";
	public static final String QUEUE = "Queue";
	public static final String DEQUEUE = "Dequeue";
	
	public static final String STAGE1 = "Stage 1";
	public static final String STAGE2 = "Stage 2";
	public static final String STAGE3 = "Stage 3";
	public static final String STAGE4 = "Stage 4";
	public static final String STAGE5 = "Stage 5";
	public static final String ENDTURN = "End Turn";
	
	private Control control;
	private State state;
		
	public static final String IMG_DIR = "src/main/resources/core/cards/";
	public static final String GIF = ".gif";

	//CONSTS FOR CANVAS LAYOUT
	public static final int rowPlayer1Rank = 80;
	public static final int colPlayer1Rank = 10;
	
	public static final int rowPlayer1Party = 230;
//	public static final int colPlayer1Party;
	
	public static final int rowHandTop6 = 390;
	public static final int colHandTop6 = 10;
	
	public static final int rowHandBottom6 = 565;
	public static final int colHandBottom6 = 10;
	
	public static final int rowHandOverflow = 215;
	
	public static final int rowQueue = 271;
	public static final int colQueue = 230;
	
	public static final int rowAdventureDeck = 145;
//	public static final int colAdventureDeck;
	
	public static final int rowStoryCard = 80;
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
	
	public static final int rowPlayerAParty = 271; //271
	public static final int colPlayerAParty = 760;
	public static final int rowPlayerBParty = 383; //383
	public static final int colPlayerBParty = 760;
	public static final int rowPlayerCParty = 495; //495
	public static final int colPlayerCParty = 760;
	public static final int rowPlayerDParty = 608; //608
	public static final int colPlayerDParty = 760;

	public static final int cardSmallHeight = 112;
	public static final int cardSmallWidth = 80;
	public static final int cardMediumHeight = 160;
	public static final int cardMediumWidth = 115;
	public static final int cardLargeHeight = 200;
	public static final	int cardLargeWidth = 150;
	public static final	int cardXLargeHeight = 300;
	public static final	int cardXLargeWidth = 225;

	
	//	private static final Logger logger = LogManager.getLogger(View.class);
	
	
	private TextField shieldCount;
	
	private Image[] ranksImg, handImg, partyImg;
	private ImageView imgView;
	
	//Hands
	private HBox CardHandTop, CardHandBottom, CardHandOverflow;
	
	private Stage stage;
	private Pane canvas;
	private TilePane tile;
	
	MainMenu menu = new MainMenu(this,null);

	public View () {}

	public static void main(String [] args){
		launch(args);
	}

	private HBox Stage; 
	

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		control = new Control(this);
		stage = primaryStage;
		initUI2(primaryStage);
	}

	private void initUI(Stage primaryStage) {
		
		state = control.getState();
		
		canvas = new Pane();
		canvas.setId("pane");
		
		addControlsToCanvas(canvas);
		addQueueToCanvas(canvas);
		addPlayerARankToCanvas(canvas);
		addPlayerBRankToCanvas(canvas);
		addPlayerCRankToCanvas(canvas);
		addPlayerDRankToCanvas(canvas);
		addPlayerAPartyToCanvas(canvas);
		addPlayerBPartyToCanvas(canvas);
		addPlayerCPartyToCanvas(canvas);
		addPlayerDPartyToCanvas(canvas);
		
		
	
		addStoryCardToCanvas(canvas);
		
		
		addHandToCanvas(canvas);
		addStageToCanvas(canvas);

		addStage(canvas);
		
		
		Scene scene = new Scene(canvas, 1280, 720);
		scene.getStylesheets().add("style.css");	
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Quests of the Round Table");
		primaryStage.show();
	}
private void initUI2(Stage primaryStage) {
		
		state = control.getState();
		
		canvas = new Pane();
		canvas.setId("pane");
		
		addControlsToCanvas(canvas);
		addQueueToCanvas(canvas);
		addPlayerARankToCanvas(canvas);
		addPlayerBRankToCanvas(canvas);
		addPlayerCRankToCanvas(canvas);
		addPlayerDRankToCanvas(canvas);
		addPlayerAPartyToCanvas(canvas);
		addPlayerBPartyToCanvas(canvas);
		addPlayerCPartyToCanvas(canvas);
		addPlayerDPartyToCanvas(canvas);
		addStoryCardToCanvas(canvas);
		addShieldsAToCanvas(canvas);
		addShieldsBToCanvas(canvas);
		addShieldsCToCanvas(canvas);
		addShieldsDToCanvas(canvas);

		addHandToCanvas(canvas);
		addStageToCanvas(canvas);

		addStage(canvas);
		
		MainMenu menu = new MainMenu(this,canvas);
		
		this.menu = menu;

		menu.setId("pane");
		
		Scene scene = new Scene(menu, 1280, 720);
		scene.getStylesheets().add("style.css");	
		
		System.out.println("Should be nice back");
		
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Quests of the Round Table");
		primaryStage.show();
	}
	
	 
	private void addHandToCanvas(Pane canvas) {
		CardCollection hand = state.players[state.currentPlayer].getHand();
		
		tile = new TilePane();
		tile.setPrefRows(2);
		tile.setPrefColumns(6);
		tile.setVgap(10);
		tile.setHgap(10);

		for (int i = 0; i < hand.size(); ++i){
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
		CardCollection stage = state.stage;
		
		tile = new TilePane();
		tile.setPrefRows(1);
		tile.setPrefColumns(8);
		tile.setVgap(10);
		tile.setHgap(10);

		for (int i = 0; i < stage.size(); ++i){
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
	}
	
	
	private void addQueueToCanvas(Pane canvas) {
		CardCollection queue = state.players[state.currentPlayer].getQueue();
		
		tile = new TilePane();
		tile.setPrefRows(1);
		tile.setPrefColumns(6);
		tile.setVgap(10);
		tile.setHgap(10);
		
		if (state.players[state.currentPlayer].getQueue() != null) {
			for (int i = 0; i < queue.size(); ++i){
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
		try {
			Image i = new Image(new FileInputStream(IMG_DIR + state.players[0].getRank().getImgName() + GIF));
			imgView = new ImageView();
			imgView.setImage(i);
			imgView.relocate(colPlayerARank, rowPlayerARank);
			imgView.setFitWidth(cardSmallWidth);
			imgView.setFitHeight(cardSmallHeight);
			imgView.setPreserveRatio(true);
			canvas.getChildren().addAll(imgView);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addPlayerBRankToCanvas(Pane canvas) {
		try {
			Image i = new Image(new FileInputStream(IMG_DIR + state.players[1].getRank().getImgName() + GIF));
			imgView = new ImageView();
			imgView.setImage(i);
			imgView.relocate(colPlayerBRank, rowPlayerBRank);
			imgView.setFitWidth(cardSmallWidth);
			imgView.setFitHeight(cardSmallHeight);
			imgView.setPreserveRatio(true);
			canvas.getChildren().addAll(imgView);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addPlayerCRankToCanvas(Pane canvas) {
		try {
			Image i = new Image(new FileInputStream(IMG_DIR + state.players[2].getRank().getImgName() + GIF));
			imgView = new ImageView();
			imgView.setImage(i);
			imgView.relocate(colPlayerCRank, rowPlayerCRank);
			imgView.setFitWidth(cardSmallWidth);
			imgView.setFitHeight(cardSmallHeight);
			imgView.setPreserveRatio(true);
			canvas.getChildren().addAll(imgView);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 	
	
	private void addPlayerDRankToCanvas(Pane canvas) {
		try {
			Image i = new Image(new FileInputStream(IMG_DIR + state.players[3].getRank().getImgName() + GIF));
			imgView = new ImageView();
			imgView.setImage(i);
			imgView.relocate(colPlayerDRank, rowPlayerDRank);
			imgView.setFitWidth(cardSmallWidth);
			imgView.setFitHeight(cardSmallHeight);
			imgView.setPreserveRatio(true);
			canvas.getChildren().addAll(imgView);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addPlayerAPartyToCanvas(Pane canvas) {
		
		CardCollection party = state.players[0].getParty();
		
		tile = new TilePane();
		tile.setPrefRows(1);
		tile.setPrefColumns(5);
		tile.setVgap(10);
		tile.setHgap(10);
		
		if (party != null) {
			for (int i = 0; i < party.size(); ++i){
				try {
					Image img = new Image(new FileInputStream(IMG_DIR + party.get(i).getImgName() + GIF));
					imgView = new ImageView();
					imgView.setId(party.get(i).getID());
					imgView.setImage(img);
					imgView.relocate(colPlayerAParty, rowPlayerAParty);
					imgView.setFitWidth(cardSmallWidth);
					imgView.setFitHeight(cardSmallHeight);
					imgView.setPreserveRatio(true);
					setMorgaineCardControl(imgView);
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
		
		CardCollection party = state.players[1].getParty();
		
		tile = new TilePane();
		tile.setPrefRows(1);
		tile.setPrefColumns(5);
		tile.setVgap(10);
		tile.setHgap(10);
		
		if (party != null) {
			for (int i = 0; i < party.size(); ++i){
				try {
					Image img = new Image(new FileInputStream(IMG_DIR + party.get(i).getImgName() + GIF));
					imgView = new ImageView();
					imgView.setId(party.get(i).getID());
					imgView.setImage(img);
					imgView.relocate(colPlayerBParty, rowPlayerBParty);
					imgView.setFitWidth(cardSmallWidth);
					imgView.setFitHeight(cardSmallHeight);
					imgView.setPreserveRatio(true);
					setMorgaineCardControl(imgView);
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
		
		CardCollection party = state.players[2].getParty();
		
		tile = new TilePane();
		tile.setPrefRows(1);
		tile.setPrefColumns(5);
		tile.setVgap(10);
		tile.setHgap(10);
		
		if (party != null) {
			for (int i = 0; i < party.size(); ++i){
				try {
					Image img = new Image(new FileInputStream(IMG_DIR + party.get(i).getImgName() + GIF));
					imgView = new ImageView();
					imgView.setId(party.get(i).getID());
					imgView.setImage(img);
					imgView.relocate(colPlayerCParty, rowPlayerCParty);
					imgView.setFitWidth(cardSmallWidth);
					imgView.setFitHeight(cardSmallHeight);
					imgView.setPreserveRatio(true);
					setMorgaineCardControl(imgView);
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
		
		CardCollection party = state.players[3].getParty();
		
		tile = new TilePane();
		tile.setPrefRows(1);
		tile.setPrefColumns(5);
		tile.setVgap(10);
		tile.setHgap(10);
		
		if (party != null) {
			for (int i = 0; i < party.size(); ++i){
				try {
					Image img = new Image(new FileInputStream(IMG_DIR + party.get(i).getImgName() + GIF));
					imgView = new ImageView();
					imgView.setId(party.get(i).getID());
					imgView.setImage(img);
					imgView.relocate(colPlayerDParty, rowPlayerDParty);
					imgView.setFitWidth(cardSmallWidth);
					imgView.setFitHeight(cardSmallHeight);
					imgView.setPreserveRatio(true);
					setMorgaineCardControl(imgView);
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
		
		System.out.println(state.currentStoryCard.getImgName());
		try {
			Image i = new Image(new FileInputStream(IMG_DIR + state.currentStoryCard.getImgName() + GIF));
			imgView = new ImageView();
			imgView.setImage(i);
			imgView.relocate(colStoryCard, rowStoryCard);
			imgView.setFitWidth(cardXLargeWidth);
			imgView.setFitHeight(cardXLargeHeight);
			imgView.setPreserveRatio(true);
			setCardClickHandler();
			canvas.getChildren().addAll(imgView);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		//canvas.getChildren().addAll(imgView);
	}
	
	private void addShieldsAToCanvas(Pane canvas) {
		String playerA = Integer.toString(state.players[0].getShieldCount());
		//System.out.println(playerA);
		Label shieldsPlayerA = new Label(playerA);
		shieldsPlayerA.setFont(Font.font("Serif", FontWeight.BOLD, 30));
		shieldsPlayerA.relocate(colPlayerARank+11,rowPlayerARank+72	);
		
		
		
		canvas.getChildren().addAll(shieldsPlayerA);
	}
	
	private void addShieldsBToCanvas(Pane canvas) {
		String playerA = Integer.toString(state.players[1].getShieldCount());
		//System.out.println(playerA);
		Label shieldsPlayerA = new Label(playerA);
		shieldsPlayerA.setFont(Font.font("Serif", FontWeight.BOLD, 30));
		shieldsPlayerA.relocate(colPlayerBRank+11,rowPlayerBRank+72	);
		
		
		
		canvas.getChildren().addAll(shieldsPlayerA);
	}
	private void addShieldsCToCanvas(Pane canvas) {
		String playerA = Integer.toString(state.players[2].getShieldCount());
		//System.out.println(playerA);
		Label shieldsPlayerA = new Label(playerA);
		shieldsPlayerA.setFont(Font.font("Serif", FontWeight.BOLD, 30));
		shieldsPlayerA.relocate(colPlayerCRank+11,rowPlayerCRank+72	);
		
		
		
		canvas.getChildren().addAll(shieldsPlayerA);
	}
	private void addShieldsDToCanvas(Pane canvas) {
		String playerA = Integer.toString(state.players[3].getShieldCount());
		//System.out.println(playerA);
		Label shieldsPlayerA = new Label(playerA);
		shieldsPlayerA.setFont(Font.font("Serif", FontWeight.BOLD, 30));
		shieldsPlayerA.relocate(colPlayerDRank+11,rowPlayerDRank+72	);
		
		
		
		canvas.getChildren().addAll(shieldsPlayerA);
	}
	
	
	
	
	private void setCardClickHandler() {
		final Random rand = new Random();
		imgView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//			logger.info("Card click detected");
			Image randomImage = ranksImg[rand.nextInt(ranksImg.length)];
			imgView.setImage(randomImage);
		});
	}
	
	private void setStageCardControl(ImageView anAdventure) {
		ContextMenu fileMenu = new ContextMenu();
		
		EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				System.out.println(((MenuItem) event.getSource()).getText());					
				System.out.println(anAdventure.getId());
				control.handClick(((MenuItem) event.getSource()).getText(), anAdventure.getId());
				state = control.getState();
				
				initUI(stage);


				//addHandToCanvas(canvas);
			}
		};

		MenuItem playItem = new MenuItem(PLAY);
		playItem.setOnAction(eh);
		fileMenu.getItems().add(playItem);
		
		MenuItem discardItem = new MenuItem(DISCARD);
		discardItem.setOnAction(eh);
		fileMenu.getItems().add(discardItem);
		
		MenuItem stageItem = new MenuItem(STAGE);
		stageItem.setOnAction(eh);
		fileMenu.getItems().add(stageItem);
		
		MenuItem queueItem = new MenuItem(QUEUE);
		queueItem.setOnAction(eh);
		fileMenu.getItems().add(queueItem);

		fileMenu.getItems().add(new MenuItem("Discard"));
		fileMenu.getItems().add(new MenuItem("Play"));
		
		MenuItem campaigne =  new MenuItem("Campaigne");
		
		campaigne.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        addCardToStage(Stage, imgView);
		        System.out.println(e.getSource());
		    }
		});
		
		fileMenu.getItems().add(campaigne);


		anAdventure.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

		@Override
		public void handle(MouseEvent t) {
			if (t.getButton() == MouseButton.SECONDARY) {
				fileMenu.show(anAdventure,t.getScreenX(),t.getScreenY());
			}
		}
		});
	}
	
	private void setHandCardControl(ImageView anAdventure) {
		ContextMenu fileMenu = new ContextMenu();
		
		EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				System.out.println(((MenuItem) event.getSource()).getText());					
				System.out.println(anAdventure.getId());
				control.handClick(((MenuItem) event.getSource()).getText(), anAdventure.getId());
				state = control.getState();
				
				initUI(stage);

				//addHandToCanvas(canvas);
			}
		};

		MenuItem playItem = new MenuItem(PLAY);
		playItem.setOnAction(eh);
		fileMenu.getItems().add(playItem);
		
		MenuItem discardItem = new MenuItem(DISCARD);
		discardItem.setOnAction(eh);
		fileMenu.getItems().add(discardItem);
		
		MenuItem stageItem = new MenuItem(STAGE);
		stageItem.setOnAction(eh);
		fileMenu.getItems().add(stageItem);
		
		MenuItem queueItem = new MenuItem(QUEUE);
		queueItem.setOnAction(eh);
		fileMenu.getItems().add(queueItem);

		anAdventure.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

		@Override
		public void handle(MouseEvent t) {
			if (t.getButton() == MouseButton.SECONDARY) {
				//System.out.println(t.getSource());
				//CardHandTop.getChildren().remove(t.getSource());
				fileMenu.show(anAdventure,t.getScreenX(),t.getScreenY());
			}
		}
		});
	}
	
	private void setQueueCardControl(ImageView anAdventure) {
		ContextMenu fileMenu = new ContextMenu();
		
		EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				System.out.println(((MenuItem) event.getSource()).getText());					
				System.out.println(anAdventure.getId());
				control.handClick(((MenuItem) event.getSource()).getText(), anAdventure.getId());
				state = control.getState();
				
				initUI(stage);

				//addHandToCanvas(canvas);
			}
		};

		MenuItem discardItem = new MenuItem(DISCARD);
		discardItem.setOnAction(eh);
		fileMenu.getItems().add(discardItem);
		
		MenuItem queueItem = new MenuItem(DEQUEUE);
		queueItem.setOnAction(eh);
		fileMenu.getItems().add(queueItem);

		anAdventure.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

		@Override
		public void handle(MouseEvent t) {
			if (t.getButton() == MouseButton.SECONDARY) {
				fileMenu.show(anAdventure,t.getScreenX(),t.getScreenY());
			}
		}
		});
	}

	private void setMorgaineCardControl(ImageView anAlly) {
		ContextMenu fileMenu = new ContextMenu();

		fileMenu.getItems().add(new MenuItem("Assassinate"));

		anAlly.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

		@Override
		public void handle(MouseEvent t) {
			if (t.getButton() == MouseButton.SECONDARY) {
				fileMenu.show(anAlly,t.getScreenX(),t.getScreenY());
			}
		}
			//boolean result= ConfirmCampaigneBox.display("Drawn new card", "Would you like to draw this card?");
		});
	}
	
	private void addStage(Pane canavas) {
		
		Stage = new HBox();
		
		Stage.relocate(colStage, rowStage);
		
		
		canavas.getChildren().addAll(Stage);
		
	}
	
	private void addCardToStage(HBox stage, ImageView newCard) {
		
		ImageView cardAdded = new ImageView();
		cardAdded.setImage(newCard.getImage());
		cardAdded.setFitHeight(12);  
		stage.getChildren().add(cardAdded);
	}
	
	private void addControlsToCanvas(Pane canvas) {
		// our coordinates 

//		Label labelHand = new Label("Hand");
//		labelHand.setFont(Font.font("Serif", FontWeight.NORMAL, 20));
//		labelHand.relocate(0, 0);
		
		Button stage1 = new Button (STAGE1);
		stage1.relocate(240,80);
		stage1.setMinWidth(80);
		stage1.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        control.buttonClick(STAGE1);
				state = control.getState();
				initUI(stage);
		    }
		});
		Button stage2 = new Button (STAGE2);
		stage2.relocate(240,110);
		stage2.setMinWidth(80);
		stage2.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        control.buttonClick(STAGE2);
				state = control.getState();
				initUI(stage);
		    }
		});
		Button stage3 = new Button (STAGE3);
		stage3.relocate(240,140);
		stage3.setMinWidth(80);
		stage3.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        control.buttonClick(STAGE3);
				state = control.getState();
				initUI(stage);
		    }
		});
		Button stage4 = new Button (STAGE4);
		stage4.relocate(240,170);
		stage4.setMinWidth(80);
		stage4.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        control.buttonClick(STAGE4);
				state = control.getState();
				initUI(stage);
		    }
		});
		Button stage5 = new Button (STAGE5);
		stage5.relocate(240,200);
		stage5.setMinWidth(80);
		stage5.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        control.buttonClick(STAGE5);
				state = control.getState();
				initUI(stage);
		    }
		});
		Button endTurn = new Button (ENDTURN);
		endTurn.relocate(240,230);
		endTurn.setMinWidth(80);
		endTurn.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        control.buttonClick(ENDTURN);
				state = control.getState();
				initUI(stage);
				System.out.println("was pressed");
			//	ConfirmNextPlayer.display("On to the next person", "Click on the ready button when ready?");
				stage.setScene(ConfirmNextPlayer.display("On to the next person", "Click on the ready button when ready?", canvas));
		    }
		});

		canvas.getChildren().addAll(stage1,stage2,stage3,stage4,stage5,endTurn);
	}
	

	public void sceneChange(Pane newScreen) {
		Scene scene = new Scene(newScreen);
		scene.getStylesheets().add("style.css");	
		stage.setScene(scene);
	}
}