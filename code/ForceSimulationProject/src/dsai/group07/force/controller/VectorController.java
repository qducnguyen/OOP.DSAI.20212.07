package dsai.group07.force.controller;

import dsai.group07.force.model.Simulation;
import dsai.group07.force.model.vector.Force;
import dsai.group07.force.model.vector.NetForce;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class VectorController {
	private Simulation simul;
	
	@FXML
	ImageView netForceVector;
	
	@FXML
	CheckBox netForceCheck;
	
	@FXML
	TextField netForceTextField;
	private SimpleDoubleProperty angleRotate = new SimpleDoubleProperty(0);
	private Force netForceSimul;
	
	public void setsim(Simulation simul) {
		this.simul = simul;
		this.netForceSimul = this.simul.getfForce();
		this.netForceVector.visibleProperty().bind(this.netForceCheck.selectedProperty());
		this.netForceVector.fitWidthProperty().bind(this.netForceSimul.valueProperty());
		this.netForceVector.rotateProperty().bind(this.angleRotate);
		
		
	}
	
	@FXML
	public void vectorRotate() {
		this.netForceSimul.setValue(Double.parseDouble(this.netForceTextField.getText()));
		System.out.println(this.simul.getfForce().valueProperty());
		if (this.netForceSimul.getValue() >= 0) {
			this.angleRotate.set(0);
		}
		else {
			
			this.angleRotate.set(180);
		}
	}
	
}
