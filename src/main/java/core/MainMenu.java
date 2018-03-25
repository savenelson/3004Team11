package core;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MainMenu extends Pane{
	private static final Logger logger = LogManager.getLogger(Player.class);

	public static final String IMG_DIR = "src/main/resources/core/cards/";
	public static final String GIF = ".gif";
	public static final String SQUIRE = "Squire";
	public static final String KNIGHT = "Knight";
	public static final String CHAMPION_KNIGHT = "ChampionKnight";
	public int numberPlayers;
	public MainMenu(final View aView, Pane toChange) {

		Label  label = new Label("Pick one of the following the options below to begin your quest");
		label.setPrefHeight(10);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setFont(new Font("Ariel", 30));
		label.setTranslateX(200);
		
		VBox col = new VBox();
		VBox row = new VBox(75);
		row.setAlignment(Pos.CENTER_RIGHT);
		
		Image i;
		ImageView imgView = new ImageView();
		try {
			i = new Image(new FileInputStream(IMG_DIR + "Rank"+SQUIRE + GIF));
			 
			imgView.setImage(i);
			imgView.setFitWidth(100);
			imgView.setFitHeight(100);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Button human2 = new Button("2 Humans");
		
		human2.setGraphic(imgView);
		human2.setStyle("-fx-font: 22 arial; -fx-base: rgb(153,73,0); -fx-text-fill:rgb(255,255,255);");
		human2.setPrefSize(500, 150);
		human2.setOnAction(new EventHandler<ActionEvent>() {
			
		    public void handle(ActionEvent e) {
		    	logger.info("Game for 2 players begining");
		        numberPlayers = 2;
		    	aView.update();
		    }
		});
		
		ImageView imgView2 = new ImageView();
		try {
			i = new Image(new FileInputStream(IMG_DIR + "Rank"+ KNIGHT + GIF));
			 
			imgView2.setImage(i);
			imgView2.setFitWidth(100);
			imgView2.setFitHeight(100);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Button human3 = new Button("3 Humans");
		human3.setGraphic(imgView2);
		human3.setPrefSize(500, 150);
		human3.setStyle("-fx-font: 22 arial; -fx-base: rgb(0,0,255); -fx-text-fill:rgb(255,255,255);");
		human3.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	logger.info("Game for 3 players begining");

		    	numberPlayers = 3;
		    	aView.update();
		    }
		});
		ImageView imgView3 = new ImageView();
		try {
			i = new Image(new FileInputStream(IMG_DIR + "Rank"+ CHAMPION_KNIGHT + GIF));
			 
			imgView3.setImage(i);
			imgView3.setFitWidth(100);
			imgView3.setFitHeight(100);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HBox b = new HBox();
		b.setPrefSize(10, 10);
		b.getChildren().addAll(imgView3,imgView);
		Button human4 = new Button("4 Humans");
		human4.setPrefSize(500, 150);
		human4.setStyle("-fx-font: 22 arial; -fx-base: rgb(170,0,0); -fx-text-fill:rgb(255,255,255);");
		human4.setGraphic(b);
		human4.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	logger.info("Game for 4 players begining");
		    	numberPlayers = 4;
		    	aView.update();	        
		    }
		});
		
		row.getChildren().addAll(human2,human3,human4);
		col.getChildren().addAll(label,row);
		this.getChildren().add(col);

		
	}
	
	public int numberSelected() {
		return numberPlayers;
	}
	
}
	
	
	
	
	
	


