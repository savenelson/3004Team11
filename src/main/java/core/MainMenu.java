package core;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class MainMenu extends Pane{
	public int numberPlayers;
	public MainMenu(View aView, Pane toChange) {

		Label  label = new Label("Pick one of the following the options below to begin your quest");
		label.setPrefHeight(10);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setFont(new Font("Ariel", 30));
		
		VBox col = new VBox();
		VBox row = new VBox(75);
		row.setAlignment(Pos.CENTER_RIGHT);
		
		Button human2 = new Button("2 Humans");
		human2.setPrefSize(500, 150);
		human2.setOnAction(new EventHandler<ActionEvent>() {
			
		    public void handle(ActionEvent e) {
		        numberPlayers = 2;
		    	aView.update();
		    }
		});
		
		Button human3 = new Button("3 Humans");
		human3.setPrefSize(500, 150);
		human3.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	numberPlayers = 3;
		    	aView.update();
		    }
		});
		
		Button human4 = new Button("4 Humans");
		human4.setPrefSize(500, 150);
		human4.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
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
	
	
	
	
	
	


