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
   	public void initialize()  {
		 
	}
	 
	 public void setSimul(Simulation simul) {
			this.simul = simul;
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
	 
	 
	public void setObjController(ObjectContainerController objController) {
		this.objController = objController;
		
	}

	@FXML
	public void resetButtonPressed() {
		this.animationController.resetAnimation();
		this.simul.restart();
		this.objController.resetObjectPosition();
	}
	
	
	@FXML
	public void pauseButtonPressed() {
		if(this.animationController.getParallelTransition().getStatus() == Animation.Status.RUNNING ) {
			this.animationController.pauseAnimation();
		} 
		else {
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
	
