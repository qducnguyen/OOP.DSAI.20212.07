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
		
		this.simul.sysVelProperty().addListener((observable, oldValue, newValue) -> 
				{
					velLabel.textProperty().bind(newValue.valueProperty().asString("Current Velocity : %.2f m/s"));
					System.out.println(observable);
				});
		this.simul.sysAccProperty().addListener((observable, oldValue, newValue) ->{
					accLabel.textProperty().bind(newValue.valueProperty().asString("Current Acceleration: %.2f m/s"));
					System.out.println(observable);
				});
		
	}
	
	
}
