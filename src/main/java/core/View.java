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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;


public class View extends Application {
	
	private Control control;
	private State state;
		
	public static final String IMG_DIR = "src/main/resources/core/cards/";
	public static final String GIF = ".gif";

	//CONSTS FOR CANVAS LAYOUT
	int rowPlayer1Rank = 80;
	int colPlayer1Rank = 10;
	
	int rowStage = 40;
	int colStage = 195;
	
	int rowPlayer1Party = 230;
	int colPlayer1Party;
	
	int rowHandTop6 = 390;
	int colHandTop6 = 10;
	
	int rowHandBottom6 = 565;
	int colHandBottom6 = 10;
	
	int rowHandOverflow = 215;
	
	int rowQueue = 271;
	int colQueue = 230;
	
	int rowAdventureDeck = 145;
	int colAdventureDeck;
	
	int rowStoryCard = 80;
	int colStoryCard = 10;
	
	int rowPlayerARank = 271;
	int colPlayerARank = 1200;
	int rowPlayerBRank = 383;
	int colPlayerBRank = 1200;
	int rowPlayerCRank = 495;
	int colPlayerCRank = 1200;
	int rowPlayerDRank = 608;
	int colPlayerDRank = 1200;
	
	int rowPlayerAParty = 271; //271
	int colPlayerAParty = 760;
	int rowPlayerBParty = 383; //383
	int colPlayerBParty = 760;
	int rowPlayerCParty = 495; //495
	int colPlayerCParty = 760;
	int rowPlayerDParty = 608; //608
	int colPlayerDParty = 760;

	int cardSmallHeight = 112;
	int cardSmallWidth = 80;
	int cardMediumHeight = 160;
	int cardMediumWidth = 115;
	int cardLargeHeight = 200;
	int cardLargeWidth = 150;
	int cardXLargeHeight = 300;
	int cardXLargeWidth = 225;

	
	//	private static final Logger logger = LogManager.getLogger(View.class);
	
	
	private TextField shieldCount;
	
	private Image[] ranksImg, handImg, partyImg;
	private ImageView imgViewRank, imgViewStory;
	
	//Hands
	private HBox CardHandTop, CardHandBottom, CardHandOverflow;
	
	public View () {
		
	}
	
//	View(){
//		System.out.println("WORKING");
//	}
	
	
	public static void main(String [] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		control = new Control(this);
		initUI(primaryStage);
	}

	private void initUI(Stage primaryStage) {
		
		state = control.getState();
		
		Pane canvas = new Pane();
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
		

		Scene scene = new Scene(canvas, 1280, 720);
		scene.getStylesheets().add("style.css");	
		primaryStage.setScene(scene);
		primaryStage.setTitle("Quests of the Round Table");
		primaryStage.show();
	}
	
	private void addHandToCanvas(Pane canvas) {
		File cardsDir = new File("src/main/resources/core/cards");
		FilenameFilter imgFilter = new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.toLowerCase().startsWith("weapon");
			}
		};
		

		String[] cardHandNames = new String[20];

		String[] handCardNames = new String[state.players[state.currentPlayer].getHand().size()];
		
		for(int i=0;i<state.players[state.currentPlayer].getHand().size();i++) {
			handCardNames[i] = state.players[state.currentPlayer].getHand().get(i).getImgName();
		}
		
		File[] handCardsFile = cardsDir.listFiles(imgFilter);
		ranksImg = new Image[state.players[state.currentPlayer].getHand().size()];
		System.out.println(state.players[state.currentPlayer].getHand().size());
		int idx = 0;
		for (String cardFile : handCardNames) {
			try {
				ranksImg[idx] = new Image(new FileInputStream(IMG_DIR + state.players[state.currentPlayer].getHand().get(idx).getImgName() + GIF));
				idx++;
				System.out.println(idx);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Displays Hand, Row 1, first 6 cards
		CardHandTop = new HBox(6); //space between nodes
		CardHandTop.relocate(colPlayer1Rank, rowHandTop6);
		//CardHandTop.setPadding(new Insets(5));// Padding betwenn Hboc border
		for(int i =0; i<6; i++) {
			imgViewRank = new ImageView();
			imgViewRank.setImage(ranksImg[i]);
			imgViewRank.relocate(colPlayer1Rank, rowHandTop6);
			imgViewRank.setFitWidth(cardMediumWidth);
			imgViewRank.setFitHeight(cardMediumHeight);
			setHandCardControl(imgViewRank);
			if (i > 5) {
			}
			imgViewRank.setPreserveRatio(true);
			CardHandTop.getChildren().addAll(imgViewRank);
		}
		
		CardHandBottom = new HBox(6); //space between nodes
		CardHandBottom.relocate(colPlayer1Rank, rowHandBottom6);
		//CardHandBottom.setPadding(new Insets(5));// Padding betwenn Hboc border
		//TODO set i<HandCardFile - 6
		for(int i =0; i<6; i++) {
			imgViewRank = new ImageView();
			imgViewRank.setImage(ranksImg[i+6]);
			imgViewRank.relocate(colPlayer1Rank, rowHandBottom6);
			imgViewRank.setFitWidth(cardMediumWidth);
			imgViewRank.setFitHeight(cardMediumHeight);
			imgViewRank.setPreserveRatio(true);
			setHandCardControl(imgViewRank);
			CardHandBottom.getChildren().addAll(imgViewRank);
		}

		CardHandOverflow = new HBox(6); //space between nodes
		
		//TODO This needs to show, when our hand has exceded 12.
//		CardHandOverflow.relocate(colPlayer1Rank, rowHandOverflow);
//		for(int i =0; i<6; i++) {
//			imgViewRank = new ImageView();
//			imgViewRank.setImage(ranksImg[i+6]);
//			imgViewRank.relocate(colPlayer1Rank, rowHandOverflow);
//			imgViewRank.setFitWidth(cardMediumWidth);
//			imgViewRank.setFitHeight(cardMediumHeight);
//			imgViewRank.setPreserveRatio(true);
//			setHandCardControl(imgViewRank);
//			CardHandOverflow.getChildren().addAll(imgViewRank);
//		}

		
		setCardClickHandler();
				
		canvas.getChildren().addAll(CardHandTop, CardHandBottom, CardHandOverflow);
	}
	
	private void addQueueToCanvas(Pane canvas) {
		File cardsDir = new File("src/main/resources/core/cards");
		FilenameFilter imgFilter = new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.toLowerCase().startsWith("amour");
			}
		};
		
		File[] partyCardsFile = cardsDir.listFiles(imgFilter);
		ranksImg = new Image[partyCardsFile.length];
		int idx = 0;
		for (File cardFile : partyCardsFile) {
			try {
				ranksImg[idx] = new Image(new FileInputStream(cardFile.getPath()));
				idx++;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//Displays Hand, Row 1, first 6 cards
		HBox PlayerAParty = new HBox(6); //space between nodes
		PlayerAParty.relocate(colQueue, rowQueue);
		//PlayerAParty.setPadding(new Insets(5));// Padding betwenn Hboc border
		for(int i =0; i<6; i++) {
			imgViewRank = new ImageView();
			imgViewRank.setImage(ranksImg[i-i]);
			imgViewRank.relocate(colQueue, rowQueue);
			imgViewRank.setFitWidth(cardSmallWidth);
			imgViewRank.setFitHeight(cardSmallHeight);

			imgViewRank.setPreserveRatio(true);
			PlayerAParty.getChildren().addAll(imgViewRank);
		}

		
		
		setCardClickHandler();
				
		canvas.getChildren().addAll(PlayerAParty);
	}

	private void addPlayerARankToCanvas(Pane canvas) {
		try {
			Image i = new Image(new FileInputStream(IMG_DIR + state.players[state.currentPlayer].getRank().getImgName() + GIF));
			imgViewRank = new ImageView();
			imgViewRank.setImage(i);
			imgViewRank.relocate(colPlayerARank, rowPlayerARank);
			imgViewRank.setFitWidth(cardSmallWidth);
			imgViewRank.setFitHeight(cardSmallHeight);
			imgViewRank.setPreserveRatio(true);
			setCardClickHandler();
			canvas.getChildren().addAll(imgViewRank);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addPlayerBRankToCanvas(Pane canvas) {
		File cardsDir = new File("src/main/resources/core/cards");
		FilenameFilter imgFilter = new FilenameFilter() {
			
			
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.toLowerCase().startsWith("rank");
			}
		};
		
		File[] rankCardsFile = cardsDir.listFiles(imgFilter);
		ranksImg = new Image[rankCardsFile.length];
		int idx = 0;
		for (File cardFile : rankCardsFile) {
			try {
				ranksImg[idx] = new Image(new FileInputStream(cardFile.getPath()));
				idx++;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		imgViewRank = new ImageView();
		imgViewRank.setImage(ranksImg[1]);
		imgViewRank.relocate(colPlayerBRank, rowPlayerBRank);
		imgViewRank.setFitWidth(cardSmallWidth);
		imgViewRank.setFitHeight(cardSmallHeight);
		imgViewRank.setPreserveRatio(true);
		
		setCardClickHandler();
				
		canvas.getChildren().addAll(imgViewRank);
	}
	
	private void addPlayerCRankToCanvas(Pane canvas) {
		File cardsDir = new File("src/main/resources/core/cards");
		FilenameFilter imgFilter = new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.toLowerCase().startsWith("rank");
			}
		};
		
		File[] rankCardsFile = cardsDir.listFiles(imgFilter);
		ranksImg = new Image[rankCardsFile.length];
		int idx = 0;
		for (File cardFile : rankCardsFile) {
			try {
				ranksImg[idx] = new Image(new FileInputStream(cardFile.getPath()));
				idx++;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		imgViewRank = new ImageView();
		imgViewRank.setImage(ranksImg[1]);
		imgViewRank.relocate(colPlayerCRank, rowPlayerCRank);
		imgViewRank.setFitWidth(cardSmallWidth);
		imgViewRank.setFitHeight(cardSmallHeight);
		imgViewRank.setPreserveRatio(true);

		
		setCardClickHandler();
				
		canvas.getChildren().addAll(imgViewRank);
	}
	
	private void addPlayerDRankToCanvas(Pane canvas) {
		File cardsDir = new File("src/main/resources/core/cards");
		FilenameFilter imgFilter = new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.toLowerCase().startsWith("rank");
			}
		};
		
		File[] rankCardsFile = cardsDir.listFiles(imgFilter);
		ranksImg = new Image[rankCardsFile.length];
		int idx = 0;
		for (File cardFile : rankCardsFile) {
			try {
				ranksImg[idx] = new Image(new FileInputStream(cardFile.getPath()));
				idx++;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		imgViewRank = new ImageView();
		imgViewRank.setImage(ranksImg[1]);
		imgViewRank.relocate(colPlayerDRank, rowPlayerDRank);
		imgViewRank.setFitWidth(cardSmallWidth);
		imgViewRank.setFitHeight(cardSmallHeight);
		imgViewRank.setPreserveRatio(true);
	
		
		setCardClickHandler();
				
		canvas.getChildren().addAll(imgViewRank);
	}
	
	private void addPlayerAPartyToCanvas(Pane canvas) {
		File cardsDir = new File("src/main/resources/core/cards");
		FilenameFilter imgFilter = new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.toLowerCase().startsWith("ally");
			}
		};
		
		File[] partyCardsFile = cardsDir.listFiles(imgFilter);
		ranksImg = new Image[partyCardsFile.length];
		int idx = 0;
		for (File cardFile : partyCardsFile) {
			try {
				ranksImg[idx] = new Image(new FileInputStream(cardFile.getPath()));
				idx++;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//Displays Hand, Row 1, first 6 cards
		HBox PlayerAParty = new HBox(5); //space between nodes
		PlayerAParty.relocate(colPlayerAParty, rowPlayerAParty);
		//PlayerAParty.setPadding(new Insets(5));// Padding betwenn Hboc border
		for(int i =0; i<5; i++) {
			imgViewRank = new ImageView();
			imgViewRank.setImage(ranksImg[i+1]);
			imgViewRank.relocate(colPlayerAParty, rowPlayerAParty);
			imgViewRank.setFitWidth(cardSmallWidth);
			imgViewRank.setFitHeight(cardSmallHeight);
			setMorgaineCardControl(imgViewRank);
			imgViewRank.setPreserveRatio(true);
			PlayerAParty.getChildren().addAll(imgViewRank);
		}

		
		
		setCardClickHandler();
				
		canvas.getChildren().addAll(PlayerAParty);
	}

	private void addPlayerBPartyToCanvas(Pane canvas) {
		File cardsDir = new File("src/main/resources/core/cards");
		FilenameFilter imgFilter = new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.toLowerCase().startsWith("ally");
			}
		};
		
		File[] partyCardsFile = cardsDir.listFiles(imgFilter);
		ranksImg = new Image[partyCardsFile.length];
		int idx = 0;
		for (File cardFile : partyCardsFile) {
			try {
				ranksImg[idx] = new Image(new FileInputStream(cardFile.getPath()));
				idx++;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//Displays Hand, Row 1, first 6 cards
		HBox PlayerAParty = new HBox(5); //space between nodes
		PlayerAParty.relocate(colPlayerBParty, rowPlayerBParty);
		//PlayerAParty.setPadding(new Insets(5));// Padding betwenn Hboc border
		for(int i =0; i<5; i++) {
			imgViewRank = new ImageView();
			imgViewRank.setImage(ranksImg[i+4]);
			imgViewRank.relocate(colPlayerBParty, rowPlayerBParty);
			imgViewRank.setFitWidth(cardSmallWidth);
			imgViewRank.setFitHeight(cardSmallHeight);
			setMorgaineCardControl(imgViewRank);
			imgViewRank.setPreserveRatio(true);
			PlayerAParty.getChildren().addAll(imgViewRank);
		}

		Timeline timeline = new Timeline();
		timeline.setAutoReverse(true);
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		KeyValue keyValue = new KeyValue(imgViewRank.xProperty(), 200, Interpolator.EASE_BOTH);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(800), keyValue);
		
		// this made the card bounce.
		//timeline.getKeyFrames().add(keyFrame);
		timeline.play();
		
		setCardClickHandler();
				
		canvas.getChildren().addAll(PlayerAParty);
	}

	private void addPlayerCPartyToCanvas(Pane canvas) {
		File cardsDir = new File("src/main/resources/core/cards");
		FilenameFilter imgFilter = new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.toLowerCase().startsWith("ally");
			}
		};
		
		File[] partyCardsFile = cardsDir.listFiles(imgFilter);
		ranksImg = new Image[partyCardsFile.length];
		int idx = 0;
		for (File cardFile : partyCardsFile) {
			try {
				ranksImg[idx] = new Image(new FileInputStream(cardFile.getPath()));
				idx++;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//Displays Hand, Row 1, first 6 cards
		HBox PlayerAParty = new HBox(5); //space between nodes
		PlayerAParty.relocate(colPlayerCParty, rowPlayerCParty);
		//PlayerAParty.setPadding(new Insets(5));// Padding betwenn Hboc border
		for(int i =0; i<5; i++) {
			imgViewRank = new ImageView();
			imgViewRank.setImage(ranksImg[i]);
			imgViewRank.relocate(colPlayerCParty, rowPlayerCParty);
			imgViewRank.setFitWidth(cardSmallWidth);
			imgViewRank.setFitHeight(cardSmallHeight);
			setMorgaineCardControl(imgViewRank);
			imgViewRank.setPreserveRatio(true);
			PlayerAParty.getChildren().addAll(imgViewRank);
		}

	
		setCardClickHandler();
				
		canvas.getChildren().addAll(PlayerAParty);
	}
	
	private void addPlayerDPartyToCanvas(Pane canvas) {
		File cardsDir = new File("src/main/resources/core/cards");
		FilenameFilter imgFilter = new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.toLowerCase().startsWith("ally");
			}
		};
		
		File[] partyCardsFile = cardsDir.listFiles(imgFilter);
		ranksImg = new Image[partyCardsFile.length];
		int idx = 0;
		for (File cardFile : partyCardsFile) {
			try {
				ranksImg[idx] = new Image(new FileInputStream(cardFile.getPath()));
				idx++;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//Displays Hand, Row 1, first 6 cards
		HBox PlayerDParty = new HBox(5); //space between nodes
		PlayerDParty.relocate(colPlayerDParty, rowPlayerDParty);
		//PlayerAParty.setPadding(new Insets(5));// Padding betwenn Hboc border
		for(int i =0; i<5; i++) {
			imgViewRank = new ImageView();
			imgViewRank.setImage(ranksImg[i]);
			imgViewRank.relocate(colPlayerDParty, rowPlayerDParty);
			imgViewRank.setFitWidth(cardSmallWidth);
			imgViewRank.setFitHeight(cardSmallHeight);
			setMorgaineCardControl(imgViewRank);
			imgViewRank.setPreserveRatio(true);
			PlayerDParty.getChildren().addAll(imgViewRank);
		}

		
		
		setCardClickHandler();
				
		canvas.getChildren().addAll(PlayerDParty);
	}
	
	
	private void addStoryCardToCanvas(Pane canvas) {
		
		System.out.println(state.currStoryCard.getImgName());
		try {
			Image i = new Image(new FileInputStream(IMG_DIR + state.currStoryCard.getImgName() + GIF));
			imgViewRank = new ImageView();
			imgViewRank.setImage(i);
			imgViewRank.relocate(colStoryCard, rowStoryCard);
			imgViewRank.setFitWidth(cardXLargeWidth);
			imgViewRank.setFitHeight(cardXLargeHeight);
			imgViewRank.setPreserveRatio(true);
			setCardClickHandler();
			canvas.getChildren().addAll(imgViewRank);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		//canvas.getChildren().addAll(imgViewRank);
	}
	
	
	

	private void setCardClickHandler() {
		final Random rand = new Random();
		imgViewRank.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//			logger.info("Card click detected");
			Image randomImage = ranksImg[rand.nextInt(ranksImg.length)];
			imgViewRank.setImage(randomImage);
		});
	}
	
	private void setHandCardControl(ImageView anAdventure) {
		ContextMenu fileMenu = new ContextMenu();

		fileMenu.getItems().add(new MenuItem("Play"));
		fileMenu.getItems().add(new MenuItem("Discard"));
		fileMenu.getItems().add(new MenuItem("Stage"));
		fileMenu.getItems().add(new MenuItem("Queue"));

		anAdventure.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

		@Override
		public void handle(MouseEvent t) {
			if (t.getButton() == MouseButton.SECONDARY) {
				fileMenu.show(anAdventure,t.getScreenX(),t.getScreenY());
			}
		}
			//boolean result= ConfirmCampaigneBox.display("Drawn new card", "Would you like to draw this card?");
		});
	}
  
  static Label labelHand;
	

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
	
	private void addControlsToCanvas(Pane canvas) {
		// our coordinates 
		
		int rowPlayer1RankButtons = 10;
		int columnRankButtons = 220;
		
		int row1 = 10;
		int row2 = 260;
		int txtBoxWidth = 45;

		Label labelHand = new Label("Hand");

		labelHand.setFont(Font.font("Serif", FontWeight.NORMAL, 20));
		labelHand.relocate(0, 0);

		canvas.getChildren().addAll(labelHand);
	}
}