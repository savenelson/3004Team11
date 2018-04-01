package core;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class ShowResoultionView extends Pane{
	public int numberPlayers;
	private State state;
	private TilePane tile;
	private ImageView imgView;
	
	
	String IMG_DIR = "src/main/resources/core/cards/";
	String GIF = ".gif";
	
	int rowStage = 60;
	int colStage = 360;
	
	int rowPlayerARank = 214;
	int colPlayerARank = 268;
	int rowPlayerBRank = 326;
	int colPlayerBRank = 268;
	int rowPlayerCRank = 438;
	int colPlayerCRank = 268;
	int rowPlayerDRank = 550;
	int colPlayerDRank = 268;
	
	int rowPlayerAParty = 214;
	int colPlayerAParty = 450;
	int rowPlayerBParty = 326;
	int colPlayerBParty = 450;
	int rowPlayerCParty = 438;
	int colPlayerCParty = 450;
	int rowPlayerDParty = 550;
	int colPlayerDParty = 450;
	
	int rowPlayerAQueue = 214;
	int colPlayerAQueue = 800;
	int rowPlayerBQueue = 326;
	int colPlayerBQueue = 800;
	int rowPlayerCQueue = 438;
	int colPlayerCQueue = 800;
	int rowPlayerDQueue = 550;
	int colPlayerDQueue = 800;
	
	
	int cardSmallHeight = 112;
	int cardSmallWidth = 80;
	int cardMediumHeight = 160;
	int cardMediumWidth = 115;
	int cardLargeHeight = 200;
	int cardLargeWidth = 150;
	int cardXLargeHeight = 300;
	int cardXLargeWidth = 225;
	
	public ShowResoultionView(final Pane toChange, State state, final View view) {
		this.state = state;
		Label  label = new Label("Resolution");
		label.setPrefHeight(10);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setFont(new Font("Ariel", 40));
		label.relocate(550, 10);
		
		Label  rank = new Label("Rank");
		rank.setPrefHeight(10);
		rank.setTextAlignment(TextAlignment.CENTER);
		rank.setFont(new Font("Ariel", 30));
		rank.relocate(270, 170);
		
		Label  party = new Label("Party");
		party.setPrefHeight(10);
		party.setTextAlignment(TextAlignment.CENTER);
		party.setFont(new Font("Ariel", 30));
		party.relocate(455, 170);
		
		Label  queue = new Label("Queue");
		queue.setPrefHeight(10);
		queue.setTextAlignment(TextAlignment.CENTER);
		queue.setFont(new Font("Ariel", 30));
		queue.relocate(805, 170);
		
		Label  p1 = new Label("Player 1");
		p1.setPrefHeight(10);
		p1.setTextAlignment(TextAlignment.CENTER);
		p1.setFont(new Font("Ariel", 30));
		p1.relocate(90, 250);
		
		Label  p2 = new Label("Player 2");
		p2.setPrefHeight(10);
		p2.setTextAlignment(TextAlignment.CENTER);
		p2.setFont(new Font("Ariel", 30));
		p2.relocate(90, 360);
		
		Label  p3 = new Label("Player 3");
		p3.setPrefHeight(10);
		p3.setTextAlignment(TextAlignment.CENTER);
		p3.setFont(new Font("Ariel", 30));
		p3.relocate(90, 470);
		
		Label  p4 = new Label("Player 4");
		p4.setPrefHeight(10);
		p4.setTextAlignment(TextAlignment.CENTER);
		p4.setFont(new Font("Ariel", 30));
		p4.relocate(90, 580);
		
		Label  stage = new Label("Stage");
		stage.setPrefHeight(10);
		stage.setTextAlignment(TextAlignment.CENTER);
		stage.setFont(new Font("Ariel", 30));
		stage.relocate(240, 100);
		
		Button goBack = new Button("Back");
		goBack.setPrefSize(80, 40);
		goBack.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	
		    	Pane newChange = new Pane(toChange);
		    	view.sceneChange(newChange);
		  	        
		    }
		});
		goBack.relocate(580, 670);
		

		drawCards(this, state);
		
		this.getChildren().addAll(label, goBack, p1, p2, p3, p4, rank, party, queue, stage);

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
		CardCollection<AdventureCard> stage = state.stages.get(state.currentStage);
		
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
	
	
	
	
	
	


