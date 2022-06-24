package dsai.group07.force.controller;

import dsai.group07.force.model.Simulation;
import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PauseResetPanelController {
	private Simulation simul;
	private AnimationController animationController;
	private ObjectContainerController objController;
	

	@FXML
	Button pauseButton;
    
    @FXML
    Button resetButton;
	
	@FXML
   	public void initialize()  {}
	
	// Constructor
	public void setObjController(ObjectContainerController objController) {
		this.objController = objController;
		
	}
	
	public void setSimul(Simulation simul) {
		this.simul = simul;
		// If the simulation process is already started then the resetButton is not disable
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
	}
		
	 
	 // Set symbol for pauseButton
	 public void setAnimationController(AnimationController animationController) {
			this.animationController = animationController;
			
			this.animationController.getParallelTransition().statusProperty().addListener((obs, oldValue, newValue) -> {
					if( newValue == Animation.Status.RUNNING ) {
						pauseButton.setText( "||" );
					} else {
						pauseButton.setText( ">" );
					}
				});
		}

	@FXML
	public void resetButtonPressed() {
		this.animationController.resetAnimation();
		this.simul.restart();
		this.objController.resetObjectPosition();
	}
	
	
	@FXML
	public void pauseButtonPressed() {
		// Case: When the simulation is running
		if(this.animationController.getParallelTransition().getStatus() == Animation.Status.RUNNING ) {
			this.animationController.pauseAnimation();
		} 
		// Case: When the simulation is stopping
		else {
			// If the simulation is not start now
			if (!simul.getIsStart()) {
				this.animationController.startAmination();
				simul.setIsStart(true);
			}
			else {
				this.animationController.continueAnimation();
			}
		}
	}
}
	
