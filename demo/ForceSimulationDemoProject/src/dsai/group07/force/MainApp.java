package dsai.group07.force;

import dsai.group07.force.view.MainAppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application  {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/MainApp.fxml") );
		Parent p = fxmlLoader.load();
		
		Scene scene = new Scene(p);

		primaryStage.setTitle( "Force Simulation App" );
		primaryStage.setScene( scene );
		primaryStage.show();
		
		MainAppController con = fxmlLoader.getController();
		con.setSce(scene);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
