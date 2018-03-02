package core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class QuestManager extends Pane{
	public static final String ENDTURN = "End Turn";
	private Control control;
	public State state;
	private TilePane tile;
	private ImageView imgView;
	String IMG_DIR = "src/main/resources/core/cards/";
	String GIF = ".gif";
	public boolean hasAnswer = false;
	
	
	
	public QuestManager(State state, Control control) {
		this.state = state;
		this.control = control;
		Label  label = new Label("Sponsor Quest");
		Label label2 = new Label(state.players[state.currentPlayer].toString());
		label2.relocate(550, 10);
		
		label.setPrefHeight(10);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setFont(new Font("Ariel", 40));
		label.relocate(550, 10);
		try {
			Image i = new Image(new FileInputStream(IMG_DIR + state.currentStoryCard.getImgName() + GIF));
			imgView = new ImageView();
			imgView.setImage(i);
			imgView.relocate(100, 100);
			imgView.setFitWidth(100);
			imgView.setFitHeight(100);
			imgView.setPreserveRatio(true);
			this.getChildren().addAll(imgView);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.getChildren().addAll(label, label2);
		
		
	}
	
	
	
	public void setState() {
		state = control.getState();
	}

}
