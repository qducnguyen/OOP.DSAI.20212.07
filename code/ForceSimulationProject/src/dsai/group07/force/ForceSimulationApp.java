/*
 *  main Class for the project. 
 */

package dsai.group07.force;

import dsai.group07.force.controller.ForceSimulationAppController;
import dsai.group07.force.model.Simulation;
import dsai.group07.force.model.surface.Surface;
import dsai.group07.force.model.vector.AppliedForce;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ForceSimulationApp extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {

		// Name for the application
		final String appName = "Force Simulation App";
		primaryStage.setTitle(appName);

		// Set icon for the application
		primaryStage.getIcons().add(new Image("file:resources/images/app_icon.png"));

		// Set minimum windows size to avoid abnormal look
		primaryStage.setMinHeight(600);
		primaryStage.setMinWidth(850);

		// Load view for the stage
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/dsai/group07/force/view/RootLayout.fxml"));
		Parent rootLayout = loader.load();
		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);

		// Load model for view through its controller
		ForceSimulationAppController appController = loader.getController();
		Simulation simul = new Simulation(null, new Surface(), new AppliedForce(0));
		appController.init(simul);

		// Display the stage
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
