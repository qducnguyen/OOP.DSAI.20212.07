package dsai.group07.force.controller;

import javax.swing.JOptionPane;

import dsai.group07.force.model.Simulation;
import dsai.group07.force.model.object.MainObject;
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
	
	
	private SimpleDoubleProperty frictionForceAngleRotate = new SimpleDoubleProperty(0);
	private SimpleDoubleProperty appliedForceAngleRotate = new SimpleDoubleProperty(0);
	private SimpleDoubleProperty totalForceAngleRotate = new SimpleDoubleProperty(0);
	
	private Force fForceSimul;
	private Force aForceSimul;
	private Force totalForceSimul;
	
	private MainObject objectTest;
	
	
	public void setsim(Simulation simul) {
		this.simul = simul;
		this.objectTest = this.simul.getObj();
		
		this.fForceSimul = this.simul.getfForce();
		this.aForceSimul = this.simul.getaForce();
		this.totalForceSimul = this.simul.getNetForce();
		
		this.fForceVector.visibleProperty().bind(this.fFroceCheckBox.selectedProperty());
		this.fForceVector.fitWidthProperty().bind(this.fForceSimul.getLengthProperty());
		this.fForceVector.rotateProperty().bind(this.frictionForceAngleRotate);
		
		this.aForceVector.visibleProperty().bind(this.aForceCheckBox.selectedProperty());
		this.aForceVector.fitWidthProperty().bind(this.aForceSimul.getLengthProperty());
		this.aForceVector.rotateProperty().bind(this.appliedForceAngleRotate);
		
		this.totalForce.visibleProperty().bind(this.netForceCheckBox.selectedProperty());
		this.totalForce.fitWidthProperty().bind(this.totalForceSimul.getLengthProperty());
		this.totalForce.rotateProperty().bind(this.totalForceAngleRotate);
	}
	
	@FXML
	public void fForceRotate() {
		this.fForceSimul.setValue(Double.parseDouble(this.fFroceTextField.getText()));
		
		if (this.totalForceSimul.getValue() >= 0) {
			this.totalForceAngleRotate.set(0);
		}
		else {
			this.totalForceAngleRotate.set(180);
		}
		
		if (this.fForceSimul.getValue() >= 0) {
			this.frictionForceAngleRotate.set(0);
		}
		else {
			
			this.frictionForceAngleRotate.set(180);
		}
	}
	
	@FXML
	public void aForceRotate() {
		this.aForceSimul.setValue(Double.parseDouble(this.aForceTextField.getText()));
		
		if (this.totalForceSimul.getValue() >= 0) {
			this.totalForceAngleRotate.set(0);
		}
		else {
			this.totalForceAngleRotate.set(180);
		}
		
		if (this.aForceSimul.getValue() >= 0) {
			this.appliedForceAngleRotate.set(0);
		}
		else {
			this.appliedForceAngleRotate.set(180);
		}
	}
}
