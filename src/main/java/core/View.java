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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	
	
	
	//CONSTS FOR CANVAS LAYOUT
	int rowPlayer1Rank = 20;
	int colPlayer1Rank = 10;
	
	int rowStage = 40;
	int colStage = 195;
	
	int rowPlayer1Party = 230;
	int colPlayer1Party;
	
	int rowHandTop6 = 350;
	int colHandTop6 = 10;
	
	int rowHandBottom6 = 525;
	int colHandBottom6 = 10;
	
	int rowAdventureDeck = 145;
	int colAdventureDeck;
	
	int rowStoryCard = 30;
	int colStoryCard = 880;
	
	int rowPlayerARank = 383-35;
	int colPlayerARank = 1200;
	int rowPlayerBRank = 495-35;
	int colPlayerBRank = 1200;
	int rowPlayerCRank = 608-35;
	int colPlayerCRank = 1200;
	
	int rowPlayerAParty = 383-35;
	int colPlayerAParty = 760;
	int rowPlayerBParty = 495-35;
	int colPlayerBParty = 760;
	int rowPlayerCParty = 608-35;
	int colPlayerCParty = 760;

	int cardSmallHeight = 112;
	int cardSmallWidth = 80;
	int cardMediumHeight = 160;
	int cardMediumWidth = 115;
	int cardLargeHeight = 200;
	int cardLargeWidth = 150;
	int cardXLargeHeight = 260;
	int cardXLargeWidth = 195;

	
//	private static final Logger logger = LogManager.getLogger(View.class);
	
	private Button addShieldsButton;
	private TextField shieldCount;
	//private TextField rightOperandTxtBox;
	private TextField answerTxtBox;
	private ComboBox<String> numberShieldsToAdd;
	private Image[] ranksImg, handImg, partyImg;
	private ImageView imgViewRank, imgViewStory;
	
//	public static void main(String[] args) {
////		logger.info("Home Screen booting up ...");
//		
//		launch(args);
//	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		initUI(primaryStage);
	}

	private void initUI(Stage primaryStage) {
		Pane canvas = new Pane();
		canvas.setId("pane");
		
		addControlsToCanvas(canvas);
		addRankCardsToCanvas(canvas);
		addHandCard1ToCanvas(canvas);
		addPlayerARankToCanvas(canvas);
		addPlayerBRankToCanvas(canvas);
		addPlayerCRankToCanvas(canvas);
		addPlayerAPartyToCanvas(canvas);
		addPlayerBPartyToCanvas(canvas);
		addPlayerCPartyToCanvas(canvas);
		addStoryCardToCanvas(canvas);
		

		Scene scene = new Scene(canvas, 1280, 720);
		scene.getStylesheets().add("style.css");	
		primaryStage.setScene(scene);
		primaryStage.setTitle("Quests of the Round Table");
		primaryStage.show();
	}
	
	

	private void addRankCardsToCanvas(Pane canvas) {
		File cardsDir = new File("src/main/resources/core/cards");
		FilenameFilter imgFilter = new FilenameFilter() {
			
			@Override
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
		imgViewRank.setImage(ranksImg[2]);
		imgViewRank.relocate(colPlayer1Rank, rowPlayer1Rank);
		imgViewRank.setFitWidth(cardLargeWidth);
		imgViewRank.setFitHeight(cardLargeHeight);
		imgViewRank.setPreserveRatio(true);
		
		Timeline timeline = new Timeline();
		timeline.setAutoReverse(true);
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		KeyValue keyValue = new KeyValue(imgViewRank.xProperty(), 200, Interpolator.EASE_BOTH);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(800), keyValue);
		
		// this made the card bounce.
		//timeline.getKeyFrames().add(keyFrame);
		timeline.play();
		
		setCardClickHandler();
				
		canvas.getChildren().addAll(imgViewRank);
	}
	
	private void addHandCard1ToCanvas(Pane canvas) {
		File cardsDir = new File("src/main/resources/core/cards");
		FilenameFilter imgFilter = new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.toLowerCase().startsWith("weapon");
			}
		};
		
		File[] handCardsFile = cardsDir.listFiles(imgFilter);
		ranksImg = new Image[handCardsFile.length];
		int idx = 0;
		for (File cardFile : handCardsFile) {
			try {
				ranksImg[idx] = new Image(new FileInputStream(cardFile.getPath()));
				idx++;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//Displays Hand, Row 1, first 6 cards
		HBox CardHandTop = new HBox(6); //space between nodes
		CardHandTop.relocate(colPlayer1Rank, rowHandTop6);
		//CardHandTop.setPadding(new Insets(5));// Padding betwenn Hboc border
		for(int i =0; i<6; i++) {
			imgViewRank = new ImageView();
			imgViewRank.setImage(ranksImg[i]);
			imgViewRank.relocate(colPlayer1Rank, rowHandTop6);
			imgViewRank.setFitWidth(cardMediumWidth);
			imgViewRank.setFitHeight(cardMediumHeight);
			if (i > 5) {
			}
			imgViewRank.setPreserveRatio(true);
			CardHandTop.getChildren().addAll(imgViewRank);
		}
		
		HBox CardHandBottom = new HBox(6); //space between nodes
		CardHandBottom.relocate(colPlayer1Rank, rowHandBottom6);
		//CardHandBottom.setPadding(new Insets(5));// Padding betwenn Hboc border
		//TODO set i<HandCardFile - 6
		for(int i =0; i<6; i++) {
			imgViewRank = new ImageView();
			imgViewRank.setImage(ranksImg[i]);
			imgViewRank.relocate(colPlayer1Rank, rowHandBottom6);
			imgViewRank.setFitWidth(cardMediumWidth);
			imgViewRank.setFitHeight(cardMediumHeight);
			imgViewRank.setPreserveRatio(true);
			CardHandBottom.getChildren().addAll(imgViewRank);
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
				
		canvas.getChildren().addAll(CardHandTop, CardHandBottom);
	}
	
	private void addPlayerARankToCanvas(Pane canvas) {
		File cardsDir = new File("src/main/resources/core/cards");
		FilenameFilter imgFilter = new FilenameFilter() {
			
			@Override
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
		imgViewRank.relocate(colPlayerARank, rowPlayerARank);
		imgViewRank.setFitWidth(cardSmallWidth);
		imgViewRank.setFitHeight(cardSmallHeight);
		imgViewRank.setPreserveRatio(true);
		
		Timeline timeline = new Timeline();
		timeline.setAutoReverse(true);
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		KeyValue keyValue = new KeyValue(imgViewRank.xProperty(), 200, Interpolator.EASE_BOTH);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(800), keyValue);
		
		// this made the card bounce.
		//timeline.getKeyFrames().add(keyFrame);
		timeline.play();
		
		setCardClickHandler();
				
		canvas.getChildren().addAll(imgViewRank);
	}
	
	private void addPlayerBRankToCanvas(Pane canvas) {
		File cardsDir = new File("src/main/resources/core/cards");
		FilenameFilter imgFilter = new FilenameFilter() {
			
			@Override
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
		
		Timeline timeline = new Timeline();
		timeline.setAutoReverse(true);
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		KeyValue keyValue = new KeyValue(imgViewRank.xProperty(), 200, Interpolator.EASE_BOTH);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(800), keyValue);
		
		// this made the card bounce.
		//timeline.getKeyFrames().add(keyFrame);
		timeline.play();
		
		setCardClickHandler();
				
		canvas.getChildren().addAll(imgViewRank);
	}
	
	private void addPlayerCRankToCanvas(Pane canvas) {
		File cardsDir = new File("src/main/resources/core/cards");
		FilenameFilter imgFilter = new FilenameFilter() {
			
			@Override
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
		imgViewRank.setImage(ranksImg[0]);
		imgViewRank.relocate(colPlayerCRank, rowPlayerCRank);
		imgViewRank.setFitWidth(cardSmallWidth);
		imgViewRank.setFitHeight(cardSmallHeight);
		imgViewRank.setPreserveRatio(true);
		
		Timeline timeline = new Timeline();
		timeline.setAutoReverse(true);
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		KeyValue keyValue = new KeyValue(imgViewRank.xProperty(), 200, Interpolator.EASE_BOTH);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(800), keyValue);
		
		// this made the card bounce.
		//timeline.getKeyFrames().add(keyFrame);
		timeline.play();
		
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
	
	private void addStoryCardToCanvas(Pane canvas) {
		File cardsDir = new File("src/main/resources/core/cards");
		FilenameFilter imgFilter = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.toLowerCase().startsWith("quest");
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
		imgViewRank.setImage(CardPath + state.currentStory.getImgName());
		imgViewRank.relocate(colStoryCard, rowStoryCard);
		imgViewRank.setFitWidth(cardXLargeWidth);
		imgViewRank.setFitHeight(cardXLargeHeight);
		imgViewRank.setPreserveRatio(true);
		
		Timeline timeline = new Timeline();
		timeline.setAutoReverse(true);
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		KeyValue keyValue = new KeyValue(imgViewRank.xProperty(), 200, Interpolator.EASE_BOTH);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(800), keyValue);
		
		// this made the card bounce.
		//timeline.getKeyFrames().add(keyFrame);
		timeline.play();
		
		setCardClickHandler();
				
		canvas.getChildren().addAll(imgViewRank);
	}
	
	
	

	private void setCardClickHandler() {
		final Random rand = new Random();
		imgViewRank.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//			logger.info("Card click detected");
			Image randomImage = ranksImg[rand.nextInt(ranksImg.length)];
			imgViewRank.setImage(randomImage);
		});
	}

	private void addControlsToCanvas(Pane canvas) {
		// our coordinates 
		
		int rowPlayer1RankButtons = 10;
		int columnRankButtons = 220;
		
		int row1 = 10;
		int row2 = 260;
		int txtBoxWidth = 45;
		
		//Char table
		Label labelChar = new Label("Character");
		labelChar.setFont(Font.font("Serif", FontWeight.NORMAL, 20));
		labelChar.relocate(30, 0);
		
		Label labelHand = new Label("Hand");
		labelHand.setFont(Font.font("Serif", FontWeight.NORMAL, 20));
		labelHand.relocate(30, 330);
		
//		leftOperandTxtBox = new TextField();
//		leftOperandTxtBox.setMaxWidth(txtBoxWidth);
//		leftOperandTxtBox.relocate(20, row2);
		
//		operatorDropdown = new ComboBox<String>();
//		operatorDropdown.getItems().addAll("+", "-", "x", "/", "%");
//		operatorDropdown.setValue("+");
//		operatorDropdown.relocate(80, row2);
//		
//		rightOperandTxtBox = new TextField();
//		rightOperandTxtBox.setMaxWidth(txtBoxWidth);
//		rightOperandTxtBox.relocate(150, row2);
		
		addShieldsButton = new Button("Add # of shields");
		addShieldsButton.relocate(rowPlayer1RankButtons,columnRankButtons);
		/*
		answerTxtBox = new TextField();
		answerTxtBox.setMaxWidth(txtBoxWidth);
		answerTxtBox.setEditable(false);
		answerTxtBox.relocate(110, 220);
		*/
		
		//shield counter field 
		shieldCount = new TextField();
		shieldCount.setMaxWidth(txtBoxWidth);
		shieldCount.setEditable(false);
		shieldCount.relocate(rowPlayer1RankButtons, columnRankButtons+30);
		shieldCount.setText("0");
		
		// the number of Shields to add 
		numberShieldsToAdd = new ComboBox<String>();
		numberShieldsToAdd.getItems().addAll("0","1","2","3","4","5","6","7","8","9","10");
		numberShieldsToAdd.setValue("0");
		numberShieldsToAdd.relocate(rowPlayer1RankButtons+120, columnRankButtons);
		setAddingShieldHandler();
		canvas.getChildren().addAll(labelHand, labelChar, addShieldsButton,numberShieldsToAdd, shieldCount);
	}

	private void setAddingShieldHandler() {
		// TODO Auto-generated method stub
		addShieldsButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
					String shieldToAdd = numberShieldsToAdd.getValue();
					shieldCount.setText(shieldToAdd);
				}
			});
	}
	

//	private void setSolnBtnClickHandler() {
//		solnBtn.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				logger.info("Solving Button clicked");
//				
//				//Double leftOperand = Double.valueOf(leftOperandTxtBox.getText());
//				//Double rightOperand = Double.valueOf(rightOperandTxtBox.getText());
//				//String operator = operatorDropdown.getValue();
//				
//				ModelController solver = new ModelController();
//				//String answer = String.valueOf(solver.solve(operator, leftOperand, rightOperand));
//				
//				answerTxtBox.setText(answer);
//			}
//		});
//	}
}