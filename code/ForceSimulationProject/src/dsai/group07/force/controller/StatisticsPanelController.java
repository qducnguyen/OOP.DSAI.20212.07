package dsai.group07.force.controller;

import dsai.group07.force.model.Simulation;
import dsai.group07.force.model.object.Cylinder;
import dsai.group07.force.model.object.Rotatable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableStringValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class StatisticsPanelController {
	
	private Simulation simul;
	
    @FXML
    private Label angLabel;

    @FXML
    private Label angAccLabel;

    @FXML
    private Label angVelLabel;
    
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
    private Label massLabel;
    
    @FXML
    private CheckBox angCheckBox;
    
    @FXML
    private CheckBox angAccCheckBox;
    
    @FXML
    private CheckBox angVelCheckBox;
    
    @FXML
    private CheckBox posCheckBox;
    
    @FXML
    private CheckBox accCheckBox;
    
    @FXML
    private CheckBox velCheckBox;
    
    @FXML
    private CheckBox aForceCheckBox;
    
    @FXML
    private CheckBox fForceCheckBox;
    
    @FXML
    private CheckBox sumForceCheckBox;
    
    @FXML CheckBox massCheckBox;
	
	@FXML
   	public void initialize()  {
		
		angLabel.setText("Current Angle Position  : 0.00 *");
		angAccLabel.setText("Current Angular Accelerate: 0.00 */s^2");
		angVelLabel.setText("Current Angular Velocity: 0.00 */s");
		 
		//Default 3 CheckBox above are invisible and disable
		setDisableThreeAngCheckBoxs(true);
		 
		accLabel.setText("Current Accelerate : 0.00 m/s^2");
		velLabel.setText("Current Velocity : 0.00 m/s");
		posLabel.setText("Current Position : 0.00 m");
		 
		aForceLabel.setText("Applied Force : 0 N");
		fForceLabel.setText("Friction Force : 0 N");
		sumForceLabel.setText("Net Force : 0 N");
		 
		angLabel.visibleProperty().bind(this.angCheckBox.selectedProperty());
		angAccLabel.visibleProperty().bind(this.angAccCheckBox.selectedProperty());
		angVelLabel.visibleProperty().bind(this.angVelCheckBox.selectedProperty());
		 
		accLabel.visibleProperty().bind(this.accCheckBox.selectedProperty());
		velLabel.visibleProperty().bind(this.velCheckBox.selectedProperty());
		posLabel.visibleProperty().bind(this.posCheckBox.selectedProperty());
		 
		aForceLabel.visibleProperty().bind(this.aForceCheckBox.selectedProperty());
		fForceLabel.visibleProperty().bind(this.fForceCheckBox.selectedProperty());
		sumForceLabel.visibleProperty().bind(this.sumForceCheckBox.selectedProperty());
		
		this.massLabel.visibleProperty().bind(this.massCheckBox.selectedProperty());
	}
	
	public void setSimul(Simulation simul) {
		this.simul = simul;
		
		angVelLabel.textProperty().bind(this.simul.getSysAngVel().asString("Current Angular Velocity : %.2f m/s"));

		this.simul.sysVelProperty().addListener(
				(observable, oldValue, newValue) -> 
				{
					velLabel.textProperty().bind(newValue.valueProperty().asString("Current Velocity : %.2f m/s"));
					System.out.println(observable);
				});
		
	
		this.simul.objProperty().addListener(
				(observable, oldValue, newValue) -> 
				{
					if(newValue instanceof Cylinder) {
						 setDisableThreeAngCheckBoxs(false);
					}
					else {
						setDisableThreeAngCheckBoxs(true);
					}
				}
				);
		
		this.simul.sysAccProperty().addListener(
				(observable, oldValue, newValue) -> {
					accLabel.textProperty().bind(newValue.valueProperty().asString("Current Accelerate : %.2f m/s^2"));
				});
		
		this.simul.objProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				this.simul.setObject(newValue);
				
				ObservableStringValue posString = Bindings.createStringBinding(() -> 
				"Current Position : " +  String.format("%.2f",this.simul.getObj().getPos()) + " m", this.simul.getObj().posProperty());
				posLabel.textProperty().bind(posString);
				
				if (newValue instanceof Cylinder) {
					
					ObservableStringValue angPosString = Bindings.createStringBinding(() -> 
					"Current Angle : " + String.format("%.2f",((Cylinder) this.simul.getObj()).getAngle()) + " *", ((Cylinder) this.simul.getObj()).angleProperty());
					angLabel.textProperty().bind(angPosString);
					
//					ObservableStringValue angVelString = Bindings.createStringBinding(() -> 
//					"Current Angular Velocity : " + ((Cylinder) this.simul.getObj()).getAngVel() + " */s", ((Cylinder) this.simul.getObj()).angVelProperty());
//					angVelLabel.textProperty().bind(angVelString);
					
										
					ObservableStringValue angAccString = Bindings.createStringBinding(() -> 
					"Current Angular Accelerate : " + String.format("%.2f",((Cylinder) this.simul.getObj()).getAngAcc()) + " */s^2", ((Cylinder) this.simul.getObj()).angAccProperty());
					angAccLabel.textProperty().bind(angAccString);
				}
			} 
		});

		ObservableStringValue aForceString = Bindings.createStringBinding(() -> 
			"Current Applied Force : " + String.format("%.2f",this.simul.getaForce().getValue()) + " N", this.simul.getaForce().valueProperty());
		aForceLabel.textProperty().bind(aForceString);

		ObservableStringValue fForceString = Bindings.createStringBinding(() -> 
			"Current Friction Force : " + String.format("%.2f",this.simul.getfForce().getValue()) + " N", this.simul.getfForce().valueProperty());
		fForceLabel.textProperty().bind(fForceString);

		ObservableStringValue netForceString = Bindings.createStringBinding(() -> 
			"Current Net Force : " + String.format("%.2f",this.simul.getNetForce().getValue()) + " N", this.simul.getNetForce().valueProperty());
		sumForceLabel.textProperty().bind(netForceString);
	};
	
	
	private void setDisableThreeAngCheckBoxs(boolean isAble) {
		angCheckBox.setDisable(isAble);
		angCheckBox.setVisible(!isAble);
		
		angAccCheckBox.setDisable(isAble);
		angAccCheckBox.setVisible(!isAble);
		
		angVelCheckBox.setDisable(isAble);
		angVelCheckBox.setVisible(!isAble);
	}
	
}
