package dsai.group07.force.controller;

import dsai.group07.force.model.Simulation;
import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PauseResetPanelController {
	private Simulation simul;
	private AnimationController animationController;
	

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
	 
	 
	@FXML
	public void resetButtonPressed() {
		this.simul.restart();
		this.animationController.resetAnimation();
	}
	
	
	@FXML
	public void pauseButtonPressed() {
		if(this.animationController.getParallelTransition().getStatus() == Animation.Status.RUNNING ) {
			this.animationController.pauseAnimation();
			
			//Testing
			if (this.simul.getObj() != null) {
			System.out.println(this.simul.getObj().getClass());}
			else {
				System.out.println("Null Object");
			}
		} else {
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
	
