package dsai.group07.force.controller;

import javax.swing.JOptionPane;

import dsai.group07.force.model.Simulation;
import dsai.group07.force.model.object.MainObject;
import dsai.group07.force.model.vector.Force;
import dsai.group07.force.model.vector.NetForce;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

public class VectorController {
	private Simulation simul;
	
	@FXML
	ImageView fForceVector;
	
	@FXML
	ImageView aForceVector;
	
	@FXML
	ImageView totalForce;
	
	@FXML
	CheckBox aForceCheckBox;
	
	@FXML
	CheckBox fFroceCheckBox;
	
	@FXML
	CheckBox netForceCheckBox;
	
	@FXML
	TextField fFroceTextField;
	
	@FXML
	TextField aForceTextField;
	
	Rotate flipRotation = new Rotate(180, Rotate.Y_AXIS);
	
	// TODO: Fix rotate vector
//	private SimpleDoubleProperty frictionForceAngleRotate = new SimpleDoubleProperty(0);
//	private SimpleDoubleProperty appliedForceAngleRotate = new SimpleDoubleProperty(0);
//	private SimpleDoubleProperty totalForceAngleRotate = new SimpleDoubleProperty(0);
	
	private double preFrictionForce = 1;
	private double preAppliedForce = 1;
	private double preTotalForce = 1;
	
	private double  appliedForceX;
	private double frictionForceX;
	
	private Force fForceSimul;
	private Force aForceSimul;
	private Force totalForceSimul;
	
	
	public void setsim(Simulation simul) {
		
		this.simul = simul;
		
		
		this.appliedForceX = this.aForceVector.getX();
		this.frictionForceX = this.fForceVector.getX();
		
		this.fForceSimul = this.simul.getfForce();
		this.aForceSimul = this.simul.getaForce();
		this.totalForceSimul = this.simul.getNetForce();
		
		this.fForceVector.visibleProperty().bind(this.fFroceCheckBox.selectedProperty());
		this.fForceVector.fitWidthProperty().bind(this.fForceSimul.getLengthProperty());
//		this.fForceVector.rotateProperty().bind(this.frictionForceAngleRotate);
		this.fForceVector.setSmooth(true);
		
		this.aForceVector.visibleProperty().bind(this.aForceCheckBox.selectedProperty());
		this.aForceVector.fitWidthProperty().bind(this.aForceSimul.getLengthProperty());
//		this.aForceVector.rotateProperty().bind(this.appliedForceAngleRotate);
		this.aForceVector.setSmooth(true);
		
		
		this.totalForce.visibleProperty().bind(this.netForceCheckBox.selectedProperty());
		this.totalForce.fitWidthProperty().bind(this.totalForceSimul.getLengthProperty());
//		this.totalForce.rotateProperty().bind(this.totalForceAngleRotate);
	}
	@FXML
	public void fForceRotate() {
		double tmpFriction = this.getValueTextField(this.fFroceTextField);
		this.fForceSimul.setValue(tmpFriction);
		double tmpTotal = this.simul.getNetForce().getValue();
		
		if (tmpFriction*this.preFrictionForce < 0) {
			this.fForceVector.getTransforms().add(flipRotation);
		}
		
		if (tmpTotal*this.preTotalForce < 0) {
			this.totalForce.getTransforms().add(flipRotation);
		}
		

		this.preFrictionForce = tmpFriction;
		this.preTotalForce = tmpTotal;
	}
	
	@FXML
	public void aForceRotate() {
		double tmpAppliedForce = this.getValueTextField(this.aForceTextField);
		this.aForceSimul.setValue(this.getValueTextField(this.aForceTextField));
		double tmpTotal = this.simul.getNetForce().getValue();
		
		if (tmpTotal * this.preTotalForce < 0) {
			this.totalForce.getTransforms().add(flipRotation);
		}
		
		if (tmpAppliedForce *this.preAppliedForce < 0) {
			this.aForceVector.getTransforms().add(flipRotation);
		}
		this.preAppliedForce = tmpAppliedForce;
		this.preTotalForce = tmpTotal;
	}
	
	public double getValueTextField(TextField textField) {
		String value = textField.getText();
		return Double.parseDouble(value);
		
	}
}
