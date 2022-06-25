package dsai.group07.force.controller;

import java.io.IOException;

import dsai.group07.force.model.Simulation;
import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
	private Button pauseButton;

    @FXML
    private Button resetButton;

	@FXML
   	public void initialize()  {
    	
    }
    
	
	public void init(Simulation simul) {
		setSimul(simul);
		showAnimation();
		showControlPane();
		setUpPauseResetPanel();
		
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
	
	private void setUpPauseResetPanel() {
		
		StackPane.setAlignment(pauseButton, Pos.BOTTOM_RIGHT);
		StackPane.setAlignment(resetButton, Pos.BOTTOM_RIGHT);
		
		StackPane.setMargin(pauseButton, new Insets(0, 100, 50, 0) );
		StackPane.setMargin(resetButton, new Insets(0, 25 ,50,0) );

		topStackPane.getChildren().add(pauseButton);
		topStackPane.getChildren().add(resetButton);
		
		resetButton.disableProperty().bind(this.simul.isStartProperty().not());
			
		this.simul.objProperty().addListener(
				(observable, oldValue, newValue) -> 
				{
					if(newValue == null) {
						pauseButton.setDisable(true);
					}
					else {
						pauseButton.setDisable(false);
					}
				});
		
		this.aniController.getParallelTransition().statusProperty().addListener((obs, oldValue, newValue) -> {
			if( newValue == Animation.Status.RUNNING ) {
				pauseButton.setText( "||" );
			} else {
				pauseButton.setText( ">" );
			}
		});
		
		
	}
	
	
	@FXML
	public void resetButtonPressed() {
		this.aniController.resetAnimation();
		this.simul.restart();
		this.objController.resetObjectPosition();
	}
	
	
	@FXML
	public void pauseButtonPressed() {
		if(this.aniController.getParallelTransition().getStatus() == Animation.Status.RUNNING ) {
			this.aniController.pauseAnimation();
		} 
		else {
			if (!simul.getIsStart()) {
				this.aniController.startAmination();
				simul.setIsStart(true);
			}
			else {
				this.aniController.continueAnimation();
			}
		}
	}
		
}
