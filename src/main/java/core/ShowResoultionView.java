package core;


import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;


public class ShowResoultionView extends Pane{
	public int numberPlayers;
	private State state;
	private View view;
	private TilePane tile;
	private ImageView imgView;
	
	String IMG_DIR = "src/main/resources/core/cards/";
	String GIF = ".gif";
	
	int rowStage = 0;
	int colStage = 60;
	
	int rowPlayerARank = 160;
	int colPlayerARank = 60;
	int rowPlayerBRank = 320;
	int colPlayerBRank = 60;
	int rowPlayerCRank = 480;
	int colPlayerCRank = 60;
	int rowPlayerDRank = 640;
	int colPlayerDRank = 60;
	
	int rowPlayerAParty = 160;
	int colPlayerAParty = 180;
	int rowPlayerBParty = 320;
	int colPlayerBParty = 180;
	int rowPlayerCParty = 480;
	int colPlayerCParty = 180;
	int rowPlayerDParty = 640;
	int colPlayerDParty = 180;
	
	int rowPlayerAQueue = 160;
	int colPlayerAQueue = 800;
	int rowPlayerBQueue = 320;
	int colPlayerBQueue = 800;
	int rowPlayerCQueue = 480;
	int colPlayerCQueue = 800;
	int rowPlayerDQueue = 640;
	int colPlayerDQueue = 800;
	
	
	int cardSmallHeight = 112;
	int cardSmallWidth = 80;
	int cardMediumHeight = 160;
	int cardMediumWidth = 115;
	int cardLargeHeight = 200;
	int cardLargeWidth = 150;
	int cardXLargeHeight = 300;
	int cardXLargeWidth = 225;
	
	public ShowResoultionView(Pane toChange, State state, View view) {
		this.state = state;
		Label  label = new Label("Resolution");
		label.setPrefHeight(10);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setFont(new Font("Ariel", 30));
		
		Button goBack = new Button("Back");
		goBack.setPrefSize(80, 40);
		goBack.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	
		    	Pane newChange = new Pane(toChange);
		    	view.sceneChange(newChange);
		  	        
		    }
		});
		

		drawCards(this, state);
		
		this.getChildren().addAll(label, goBack);

//		view.update();
	}
	
	public Pane drawCards(Pane canvas, State state){
		addStageToCanvas(canvas);
		addPlayerARankToCanvas(canvas);
		addPlayerBRankToCanvas(canvas);
		addPlayerAPartyToCanvas(canvas);
		addPlayerBPartyToCanvas(canvas);
		addPlayerAQueueToCanvas(canvas);
		addPlayerBQueueToCanvas(canvas);

		if(state.numPlayers == 3){
			addPlayerCRankToCanvas(canvas);
			addPlayerCPartyToCanvas(canvas);
			addPlayerCQueueToCanvas(canvas);
		}
		if(state.numPlayers == 4){
			addPlayerCRankToCanvas(canvas);
			addPlayerCPartyToCanvas(canvas);
			addPlayerCQueueToCanvas(canvas);
			addPlayerDRankToCanvas(canvas);
			addPlayerDPartyToCanvas(canvas);
			addPlayerDQueueToCanvas(canvas);
		}
		
		return canvas;
	}
	
	private void addStageToCanvas(Pane canvas) {
		CardCollection stage = state.stages[state.currentStage];
		
		tile = new TilePane();
		tile.setPrefRows(1);
		tile.setPrefColumns(5);
		tile.setVgap(10);
		tile.setHgap(10);
		
		if (stage != null) {
			for (int i = 0; i < stage.size(); ++i){
				try {
					Image img = new Image(new FileInputStream(IMG_DIR + stage.get(i).getImgName() + GIF));
					imgView = new ImageView();
					imgView.setId(stage.get(i).getID());
					imgView.setImage(img);
					imgView.relocate(colStage, rowStage);
					imgView.setFitWidth(cardSmallWidth);
					imgView.setFitHeight(cardSmallHeight);
					imgView.setPreserveRatio(true);
					tile.getChildren().add(imgView);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
	
			tile.relocate(colStage, rowStage);
			
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
					tile.getChildren().add(imgView);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
	
			tile.relocate(colPlayerDParty, rowPlayerDParty);
			
			canvas.getChildren().add(tile);
		}
	}
	
	private void addPlayerAQueueToCanvas(Pane canvas) {
		
		CardCollection queue = state.players[0].getQueue();
		
		tile = new TilePane();
		tile.setPrefRows(1);
		tile.setPrefColumns(5);
		tile.setVgap(10);
		tile.setHgap(10);
		
		if (queue != null) {
			for (int i = 0; i < queue.size(); ++i){
				try {
					Image img = new Image(new FileInputStream(IMG_DIR + queue.get(i).getImgName() + GIF));
					imgView = new ImageView();
					imgView.setId(queue.get(i).getID());
					imgView.setImage(img);
					imgView.relocate(colPlayerBQueue, rowPlayerBQueue);
					imgView.setFitWidth(cardSmallWidth);
					imgView.setFitHeight(cardSmallHeight);
					imgView.setPreserveRatio(true);
					tile.getChildren().add(imgView);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
	
			tile.relocate(colPlayerAQueue, rowPlayerAQueue);
			
			canvas.getChildren().add(tile);
		}
	}
	
	private void addPlayerBQueueToCanvas(Pane canvas) {
		
		CardCollection queue = state.players[1].getQueue();
		
		tile = new TilePane();
		tile.setPrefRows(1);
		tile.setPrefColumns(5);
		tile.setVgap(10);
		tile.setHgap(10);
		
		if (queue != null) {
			for (int i = 0; i < queue.size(); ++i){
				try {
					Image img = new Image(new FileInputStream(IMG_DIR + queue.get(i).getImgName() + GIF));
					imgView = new ImageView();
					imgView.setId(queue.get(i).getID());
					imgView.setImage(img);
					imgView.relocate(colPlayerBQueue, rowPlayerBQueue);
					imgView.setFitWidth(cardSmallWidth);
					imgView.setFitHeight(cardSmallHeight);
					imgView.setPreserveRatio(true);
					tile.getChildren().add(imgView);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
	
			tile.relocate(colPlayerBQueue, rowPlayerBQueue);
			
			canvas.getChildren().add(tile);
		}
	}
	
	private void addPlayerCQueueToCanvas(Pane canvas) {
		
		CardCollection queue = state.players[2].getQueue();
		
		tile = new TilePane();
		tile.setPrefRows(1);
		tile.setPrefColumns(5);
		tile.setVgap(10);
		tile.setHgap(10);
		
		if (queue != null) {
			for (int i = 0; i < queue.size(); ++i){
				try {
					Image img = new Image(new FileInputStream(IMG_DIR + queue.get(i).getImgName() + GIF));
					imgView = new ImageView();
					imgView.setId(queue.get(i).getID());
					imgView.setImage(img);
					imgView.relocate(colPlayerCQueue, rowPlayerCQueue);
					imgView.setFitWidth(cardSmallWidth);
					imgView.setFitHeight(cardSmallHeight);
					imgView.setPreserveRatio(true);
					tile.getChildren().add(imgView);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
	
			tile.relocate(colPlayerCQueue, rowPlayerCQueue);
			
			canvas.getChildren().add(tile);
		}
	}
	
	private void addPlayerDQueueToCanvas(Pane canvas) {
		
		CardCollection queue = state.players[3].getQueue();
		
		tile = new TilePane();
		tile.setPrefRows(1);
		tile.setPrefColumns(5);
		tile.setVgap(10);
		tile.setHgap(10);
		
		if (queue != null) {
			for (int i = 0; i < queue.size(); ++i){
				try {
					Image img = new Image(new FileInputStream(IMG_DIR + queue.get(i).getImgName() + GIF));
					imgView = new ImageView();
					imgView.setId(queue.get(i).getID());
					imgView.setImage(img);
					imgView.relocate(colPlayerDQueue, rowPlayerDQueue);
					imgView.setFitWidth(cardSmallWidth);
					imgView.setFitHeight(cardSmallHeight);
					imgView.setPreserveRatio(true);
					tile.getChildren().add(imgView);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
	
			tile.relocate(colPlayerDQueue, rowPlayerDQueue);
			
			canvas.getChildren().add(tile);
		}
	}
	
	public int numberSelected() {
		return numberPlayers;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
	
	
	
	
	
	


