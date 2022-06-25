package dsai.group07.force;

import dsai.group07.force.controller.ForceSimulationAppController;
import dsai.group07.force.model.Simulation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ForceSimulationApp extends Application  {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		final String appName = "Force Simulation App";

		primaryStage.setTitle(appName);
		primaryStage.getIcons().add(new Image("file:resources/images/app_icon.png"));
		primaryStage.setMinHeight(400);
		primaryStage.setMinWidth(650);

		
		Simulation simul = new Simulation();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/dsai/group07/force/view/RootLayout.fxml"));
		Parent rootLayout = loader.load();
		
		ForceSimulationAppController appController = loader.getController();
		appController.init(simul);
		
		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
