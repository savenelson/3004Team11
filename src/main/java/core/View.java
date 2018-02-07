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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class View extends Application {
	
	//CONSTS FOR CANVAS LAYOUT
	int rowRank = 20;
	int rowStage = 40;
	int rowParty = 230;
	int rowHand1 = 350;
	int rowHand2 = 525;
	int rowDeck = 145;
	int rowPlayerX = 375;
	int rowPlayerY = 480;
	int rowPlayerZ = 590;
	int colRank = 10;
	int colStage = 195;
	int cardRankX;
	int cardRankY;
	int cardHandX = 120;
	int cardHandY = 160;
	int cardPartyX;
	int cardPartyY;
	int cardStageX;
	int cardStageY;
	int cardStoryX;
	int cardStoryY;
	int cardPlayerX;
	int cardPlayerY;
	
//	private static final Logger logger = LogManager.getLogger(View.class);
	
	private Button addShieldsButton;
	private TextField shieldCount;
	//private TextField rightOperandTxtBox;
	private TextField answerTxtBox;
	private ComboBox<String> numberShieldsToAdd;
	private Image[] ranksImg, handImg;
	private ImageView imgViewRank;
	
	public static void main(String[] args) {
//		logger.info("Home Screen booting up ...");
		
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		initUI(primaryStage);
	}

	private void initUI(Stage primaryStage) {
		Pane canvas = new Pane();
		//canvas.setStyle("-fx-background-color: black");
		
		addControlsToCanvas(canvas);
		addRankCardsToCanvas(canvas);
		addHandCard1ToCanvas(canvas);
		
		

		
		
		Scene scene = new Scene(canvas, 1280, 720);
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
		imgViewRank.relocate(colRank, rowRank);
		imgViewRank.setFitWidth(150);
		imgViewRank.setFitHeight(200);
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
				return name.toLowerCase().startsWith("ally");
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

		HBox CardHand = new HBox(10); //space between nodes
		CardHand.relocate(colRank, rowHand1);
		//CardHand.setPadding(new Insets(5));// Padding betwenn Hboc border
		
		for(int i =0; i<rankCardsFile.length; i++) {
			imgViewRank = new ImageView();
			imgViewRank.setImage(ranksImg[i]);
			imgViewRank.relocate(colRank, rowHand1);
			imgViewRank.setFitWidth(cardHandX);
			imgViewRank.setFitHeight(cardHandY);
			imgViewRank.setPreserveRatio(true);
			CardHand.getChildren().addAll(imgViewRank);
			
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
				
		canvas.getChildren().addAll(CardHand);
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
		
		int rowRankButtons = 10;
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
		addShieldsButton.relocate(rowRankButtons,columnRankButtons);
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
		shieldCount.relocate(rowRankButtons, columnRankButtons+30);
		shieldCount.setText("0");
		
		
		// the number of Shields to add 
		numberShieldsToAdd = new ComboBox<String>();
		numberShieldsToAdd.getItems().addAll("0","1","2","3","4","5","6","7","8","9","10");
		numberShieldsToAdd.setValue("0");
		numberShieldsToAdd.relocate(rowRankButtons+120, columnRankButtons);
		setAddingShieldHandler();
		//setSolnBtnClickHandler();
		
		//with buttons
//		canvas.getChildren().addAll(label, leftOperandTxtBox, rightOperandTxtBox, 
//				operatorDropdown, solnBtn, answerTxtBox);
		
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
