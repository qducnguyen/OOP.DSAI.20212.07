package dsai.group07.force;

import java.io.IOException;

import dsai.group07.force.controller.AnimationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ForceSimulationApp extends Application  {
	
	private final String nameApp = "Force Simulation App";
	
	private Stage primaryStage;
	private GridPane rootLayout;
	private Scene scene;
	
	private StackPane upStackPane;
	private StackPane downStackPane;
	
	GridPane controlPanel;
	AnimationController con;
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(nameApp);
		this.primaryStage.getIcons().add(new Image("file:resources/images/app_icon.png"));
		
		this.primaryStage.setMinHeight(400);
		this.primaryStage.setMinWidth(650);
		
		initRootLayout();
		
		showAnimation();
		
		showControlPane();
		
	}
	
	
	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/Rootlayout.fxml"));
			rootLayout = (GridPane) loader.load();
			
			scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showAnimation() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/Animation.fxml"));
			GridPane gridPaneOutSide = (GridPane) loader.load();
			
			upStackPane = (StackPane) gridPaneOutSide.getChildren().get(0);
			downStackPane = (StackPane) gridPaneOutSide.getChildren().get(1);
			
			this.rootLayout.getChildren().add(upStackPane);
			this.rootLayout.getChildren().add(downStackPane);
			

			con = loader.getController();
			con.setSce(scene);
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
		
	private void showControlPane() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/ControlPanel.fxml"));
			controlPanel = (GridPane) loader.load();
						
			downStackPane.getChildren().add(controlPanel);
			
			showRecCir();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showRecCir() {
		try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("view/ObjectContainer.fxml"));
		GridPane ObjectPanel = (GridPane) loader.load();
		controlPanel.add(ObjectPanel, 0, 0);
		
		
		con.setObjController(loader.getController());
		
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
