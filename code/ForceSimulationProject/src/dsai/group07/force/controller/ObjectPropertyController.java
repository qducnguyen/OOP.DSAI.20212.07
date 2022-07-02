package dsai.group07.force.controller;

import javax.swing.JOptionPane;

import dsai.group07.force.model.Simulation;
import dsai.group07.force.model.object.Cube;
import dsai.group07.force.model.object.Cylinder;
import dsai.group07.force.model.object.MainObject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ObjectPropertyController {
	
	
	private Simulation simul;
	
	
	private double max_Value = 10;
	
	private BooleanProperty closeWindow = new SimpleBooleanProperty(false);
	@FXML
	Button confirmButton;
	
	@FXML
	Label massLabel;
	
	@FXML
	Label lengthLabel;
	
	@FXML
	TextField massTextField;
	
	@FXML
	TextField lengthTextField;
	
	public BooleanProperty getCloseWindown() {
		return this.closeWindow;
	}
	
	public double getMaxValue() {
		return this.max_Value;
	}
	
	@FXML 
	public void confirmInput() throws Exception {
		String massString = this.massTextField.getText();
		String lengthString = this.lengthTextField.getText();
		
		if (isNumber(lengthString) && isNumber(massString)) {
			
			double temp = Double.parseDouble(lengthString);
			if (temp <= 0 || temp > this.max_Value) {
				JOptionPane.showMessageDialog(null, 
						"The value of length must be larger than 0 and smaller than or equal to " + this.max_Value, 
						"Invalue value", 
						JOptionPane.ERROR_MESSAGE);
				this.closeWindow.set(false);
			}
			
			else {
				System.out.println("Object Property Controller: " + this.simul.getObj());
				System.out.println("Check boolean: "+  (this.simul.getObj() instanceof Cylinder));
				if (this.simul.getObj() instanceof Cylinder) {
					
					System.out.println("Object Property Controller: " + (Cylinder)this.simul.getObj());
					((Cylinder) this.simul.getObj()).setRadius(temp);
					this.simul.getObj().setMass(Double.parseDouble(massString));
					System.out.println("Object Property Controller: " + ((Cylinder)this.simul.getObj()).getRadius());
				}
				
				else if (this.simul.getObj() instanceof Cube) {
					((Cube) this.simul.getObj()).setSize(temp);
					this.simul.getObj().setMass(Double.parseDouble(massString));
				}
				
				else {
					System.out.println("Sth wrong");
				}
				this.closeWindow.set(true);
			}
		}
		else {
			JOptionPane.showMessageDialog(null, 
					"The input value must be in type of number ", 
					"Invalue value", 
					JOptionPane.ERROR_MESSAGE);
			this.closeWindow.set(false);
		}
	}
	
	public void setsim(Simulation simul) {
		this.simul = simul;
		if (this.simul.getObj() instanceof Cylinder) {
			
			this.lengthLabel.setText("Radius");
			this.max_Value = Cube.MAX_SIZE;
		}
		else if (this.simul.getObj() instanceof Cube) {
			
			this.lengthLabel.setText("Side length");
			this.max_Value = Cylinder.MAX_RADIUS;
		}
	}
	
	public boolean isNumber(String s) {
		try {
			Double.parseDouble(s);
			return true;
		}
		catch (Exception e){
			return false;
		}
	}
	
	
}
