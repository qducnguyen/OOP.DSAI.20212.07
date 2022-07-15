package dsai.group07.force.controller;

import dsai.group07.force.model.Simulation;
import dsai.group07.force.model.vector.AppliedForce;
import dsai.group07.force.model.vector.FrictionForce;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class ForcePanelController {

	private Simulation simul;

	@FXML
	private StackPane forcePanel;

	@FXML
	private TextField forceTextField;

	@FXML
	private Slider forceSlider;

	public void init(Simulation simul) {
		// set Simulation
		this.simul = simul;
		
		// text validation using pseudo class "error": matches positive / negative integer and double number
		// changes background color of text field to red when invalid input
		forceTextField.setDisable(true);
		forceSlider.setDisable(true);

		// text validation: 
		forceTextField.textProperty().addListener(event -> {
			forceTextField.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"),
					!forceTextField.getText().isEmpty()
							&& !forceTextField.getText().matches("^([+-]?)(0|([1-9][0-9]*))(\\.[0-9]+)?$"));
		});

		// slider changes -> text field changes
		forceSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			forceTextField.setText(String.format("%.2f", newValue));
		});

		// slider changes <-> applied force of this Simulation changes
		forceSlider.valueProperty().bindBidirectional(this.simul.getaForce().valueProperty());
		
		// updates net force
		netForceListener();

		// applied changes -> updates friction force
		this.simul.getaForce().valueProperty().addListener(observable -> {
			try {
				((FrictionForce) this.simul.getfForce()).updateFrictionForce();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		// applied changes -> starting and continuing property changes
		this.simul.getaForce().valueProperty().addListener((observable, oldValue, newValue) -> {

			if (!this.simul.getIsStart() && newValue.doubleValue() != 0.0) // newValue.doubleValue() != 0: Prevent auto
																			// start when force == 0
			{
				this.simul.start();
			} else if (this.simul.getIsPause() && this.simul.getIsStart()) {
				this.simul.conti();
			}

			forceTextField.getParent().requestFocus();

		});

		// main object changes -> 
		this.simul.objProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null) {
				// setDisable true of slider and text field when null object
				forceTextField.setDisable(true);
				forceSlider.setDisable(true);
				// also set text filed and applied force to 0
				forceTextField.setText("0");
				this.simul.setaForce(0);
			} else {
				// setDisable true of slider and text field when object != null
				forceTextField.setDisable(false);
				forceSlider.setDisable(false);
				
				// sets object relating to friction force
				((FrictionForce) this.simul.getfForce()).setMainObj(newValue);
				// update friction force when object changes
				this.simul.getObj().velProperty().valueProperty().addListener((observable1, oldValue1, newValue1) -> {
					((FrictionForce) this.simul.getfForce()).updateFrictionForce();
				});

			}
		});

	}

	// text filed entered -> changes applied force
	@FXML
	void forceTextFieldOnAction(ActionEvent event) {
		try {
			this.simul.getaForce().setValue((double) Double.parseDouble(forceTextField.getText()));
			if (Math.abs((double) Double.parseDouble(forceTextField.getText())) > AppliedForce.ABS_MAX_AFORCE) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("\nPlease input a number >= -" + AppliedForce.ABS_MAX_AFORCE + " and <= " + AppliedForce.ABS_MAX_AFORCE);
				alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert.showAndWait();
			}
			System.out.println(
					"Current Applied Force Value " + this.simul.getaForce().getValue() + " from On Action force Text");
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setContentText(e.getMessage() + "\nPlease input a number >= -" + AppliedForce.ABS_MAX_AFORCE + " and <=" + AppliedForce.ABS_MAX_AFORCE);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.showAndWait();
		}
	}

	// updates net force when applied / friction force changes
	public void netForceListener() {
		this.simul.getaForce().valueProperty().addListener(observable -> {
			try {
				this.simul.updateNetForce();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		this.simul.getfForce().valueProperty().addListener(observable -> {
			try {
				this.simul.updateNetForce();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}
