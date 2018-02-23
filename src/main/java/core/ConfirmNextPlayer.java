package core;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmNextPlayer {
	
	static Boolean ansewer;
	
	public static Scene display(String display, int message, Pane canvas) {
		
<<<<<<< HEAD
		
		Label label = new Label("Is Player "+ message + "ready ?" );
=======
		Label label = new Label(message);
>>>>>>> 5cd02a41dcf4d222a4f94d0ce848debe94bcc7bb
		
		Button readyButton = new Button("Ready");
		
		readyButton.setOnAction(e->{
			
			
		});
		VBox layout = new VBox(5);
		
		layout.getChildren().addAll(label,readyButton);
		
		Scene scene = new Scene(layout);
		
		layout.getChildren().add(canvas);
		
		return scene;
		

	}
	
	

}
