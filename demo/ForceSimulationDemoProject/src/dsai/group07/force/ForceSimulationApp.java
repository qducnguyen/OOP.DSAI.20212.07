package dsai.group07.force;

import java.io.IOException;

import dsai.group07.force.controller.AnimationController;
import dsai.group07.force.controller.ObjectContainerController;
import dsai.group07.force.controller.PauseResetPanelController;
import dsai.group07.force.model.Simulation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ForceSimulationApp extends Application  {
	
	private final String nameApp = "Force Simulation App";
	
	//Model
	
	private Simulation simul;
	
	
	
	private Stage primaryStage;
	private GridPane rootLayout;
	
	private StackPane topStackPane;
	private StackPane downStackPane;
	
	
	private GridPane controlPanel;
	private AnimationController con;
	private ObjectContainerController objCon;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(nameApp);
		this.primaryStage.getIcons().add(new Image("file:resources/images/app_icon.png"));
		
		this.primaryStage.setMinHeight(400);
		this.primaryStage.setMinWidth(650);
		
		initSimulation();
		
		initRootLayout();
		
		showAnimation();
		
		showControlPane();
		
		
		
	}
	
	
	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/dsai/group07/force/view/Rootlayout.fxml"));
			rootLayout = (GridPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
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
			loader.setLocation(getClass().getResource("/dsai/group07/force/view/Animation.fxml"));
			GridPane gridPaneOutSide = (GridPane) loader.load();
			
			topStackPane = (StackPane) gridPaneOutSide.getChildren().get(0);
			downStackPane = (StackPane) gridPaneOutSide.getChildren().get(1);
			
			this.rootLayout.getChildren().add(topStackPane);
			this.rootLayout.getChildren().add(downStackPane);
			

			con = loader.getController();
			
			con.setSim(simul);
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
		
	private void showControlPane() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/dsai/group07/force/view/ControlPanel.fxml"));
			controlPanel = (GridPane) loader.load();
						
			downStackPane.getChildren().add(controlPanel);
			
			showRecCir();
			
			showPauseResetPanel();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showRecCir() {
		try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/dsai/group07/force/view/ObjectContainer.fxml"));
		GridPane ObjectPanel = (GridPane) loader.load();
		controlPanel.add(ObjectPanel, 0, 0);
		
		objCon = loader.getController();
		
		objCon.setSimul(simul);
		objCon.setTopStackPane(topStackPane);
		objCon.setDownStackPane(downStackPane);
		
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showPauseResetPanel() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/dsai/group07/force/view/PauseResetPanel.fxml"));
			AnchorPane panel = (AnchorPane) loader.load();
			StackPane.setAlignment(panel, Pos.BOTTOM_RIGHT);
			
			topStackPane.getChildren().add(panel);
			
			PauseResetPanelController resController = loader.getController();
			
			
			resController.setSimul(simul);
			resController.setAnimationController(con);
			resController.setObjController(objCon);
			
			}
		catch(IOException e) {
				e.printStackTrace();
			}
	}
	
	private void initSimulation() {
		this.simul = new Simulation();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
