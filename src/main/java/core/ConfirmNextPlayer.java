package core;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmNextPlayer {
	
	static Boolean ansewer;
	
	public static void display(String display, String message) {
		Stage window = new Stage();
		window.initModality(Modality.WINDOW_MODAL);
		window.setHeight(720);
		window.setWidth(1280);
		
		Label label = new Label(message);
		
		
		
		Button readyButton = new Button("Ready");
		
		readyButton.setOnAction(e->{
			window.close();
		});
		VBox layout = new VBox(5);
		
		layout.getChildren().addAll(label,readyButton);
		Scene scene = new Scene(layout);
		
		window.setScene(scene);
		window.showAndWait();
		
	}
	
	

}
