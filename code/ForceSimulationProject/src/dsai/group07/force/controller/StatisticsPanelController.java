package dsai.group07.force.controller;

import dsai.group07.force.model.Simulation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StatisticsPanelController {
	
	private Simulation simul;
	

    @FXML
    private Label posLabel;

    @FXML
    private Label accLabel;

    @FXML
    private Label velLabel;

    @FXML
    private Label aForceLabel;

    @FXML
    private Label fForceLabel;

    @FXML
    private Label sumForceLabel;
    
    
	
	@FXML
   	public void initialize()  {
		 accLabel.setText("Current Accelerate: 0.00 m/s^2");
		 velLabel.setText("Current Velocity : 0.00 m/s");
		 posLabel.setText("Current Position : 0.00 m");
		 aForceLabel.setText("Applied Force: 0 N");
		 fForceLabel.setText("Friction Force: 0 N");
		 sumForceLabel.setText("Sum Force: 0 N");
	}
	
	public void setSimul(Simulation simul) {
		this.simul = simul;
		
		this.simul.sysVelProperty().addListener(
				(observable, oldValue, newValue) -> 
				{
					velLabel.textProperty().bind(newValue.valueProperty().asString("Current Velocity : %.2f m/s"));
					System.out.println(observable);
				});
		this.fForceLabel.textProperty().bind(this.simul.getfForce().valueProperty().asString("Current friction force: %.2f s/s"));
		this.aForceLabel.textProperty().bind(this.simul.getaForce().valueProperty().asString("Current applied force: %.2f m/s"));
		this.sumForceLabel.textProperty().bind(this.simul.getNetForce().valueProperty().asString("Current total force: %.2f m/s"));
		
	}
}
