package dsai.group07.force.controller;

import java.io.IOException;

import dsai.group07.force.model.Simulation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class ForceSimulationAppController {

	private Simulation simul;

	private StackPane topStackPane;
	private StackPane downStackPane;
	
	
	private GridPane controlPanel;
	private AnimationController aniController;
	private ObjectPanelController objController;
	private ControlPanelController controlController;
	
	@FXML
    private GridPane rootLayout;
	
	@FXML
   	public void initialize()  {
    	
    }
    
	
	public void init(Simulation simul) {
		setSimul(simul);
		showAnimation();
		showControlPane();
		showPauseResetPanel();
	}
	
	
    public void setSimul(Simulation simul) {
		this.simul = simul;
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
			
			aniController = loader.getController();
			
			aniController.setSim(simul);
			
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
			
			controlController = loader.getController();
			
			controlController.init(simul, topStackPane, downStackPane);
			
			this.objController = controlController.getObjController();
			
			
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
			resController.setAnimationController(aniController);
			resController.setObjController(objController);
			
			}
		catch(IOException e) {
				e.printStackTrace();
			}
	}
}
