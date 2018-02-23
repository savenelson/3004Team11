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

public class MainMenu extends Pane{
	private VBox buttonRow;
	private View view;
	private Pane mainPane;
	public int numberPlayers;
	public MainMenu(View aView,Pane toChange) {
		
		view = aView;
		mainPane = toChange;
		
		
		
		VBox col = new VBox();
		
		Label  label = new Label("Pick one of the following the options below to begin your quest");
		label.setPrefHeight(100);
		
		label.setFont(new Font("Arial", 30));
		
		
		
		
		VBox row = new VBox(75);
		row.setAlignment(Pos.CENTER_RIGHT);
		this.buttonRow = row;
		
		
	
		
		Button human2 = new Button("2 Humans");
		human2.setPrefSize(500, 150);
		human2.setOnAction(new EventHandler<ActionEvent>() {
			
		    public void handle(ActionEvent e) {
		        aView.sceneChange(toChange);
		        numberPlayers = 2;
		        
		    }
		});
		Button human3 = new Button("3 Humans");
		human3.setPrefSize(500, 150);
		human3.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	aView.sceneChange(toChange);
		    	numberPlayers = 3;
		        
		    }
		});
		Button human4 = new Button("4 Humans");
		human4.setPrefSize(500, 150);
		human4.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	aView.sceneChange(toChange);
		    	numberPlayers = 4;
		        
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
	
	
	
	
	
	


