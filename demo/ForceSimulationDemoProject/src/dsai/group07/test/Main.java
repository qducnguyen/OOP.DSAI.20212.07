package dsai.group07.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import dsai.group07.vector.Vector;

public class Main extends Application{
	public void start(Stage primaryStage) throws Exception  {
		Vector location = new Vector(1,2);
		Vector velocity = new Vector(2,3);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainStageTest.fxml") );
		
		Parent p = fxmlLoader.load();
		
		Scene sc = new Scene(p);
		
		primaryStage.setScene(sc);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
