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
	
	public static Scene display(String display, String message, Pane canvas) {
		
		Label label = new Label(message);
		
		Button readyButton = new Button("Ready");
		
		readyButton.setOnAction(e->{
			
		});
		VBox layout = new VBox(5);
		
		layout.getChildren().addAll(label,readyButton);
		Scene scene = new Scene(layout);
		
		layout.getChildren().add(canvas);
		
		return scene;
		
		//window.setScene(scene);
		//window.showAndWait();

	}
	
	

}
