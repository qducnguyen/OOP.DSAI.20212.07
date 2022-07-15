package dsai.group07.force.controller;

import dsai.group07.force.model.Simulation;
import dsai.group07.force.model.surface.Surface;
import dsai.group07.force.model.vector.FrictionForce;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class SurfacePanelController {

	private Simulation simul;

	@FXML
	private TextField staticCoefTextField;

	@FXML
	private TextField kineticCoefTextField;

	@FXML
	private Slider staticCoefSlider;

	@FXML
	private Slider kineticCoefSlider;

	public void init(Simulation simul) {
		// set simulation
		this.simul = simul;
		
		// text validation: matches positive integer and double number
		// changes background color of text field to red when invalid input
		staticCoefTextField.textProperty().addListener(event -> {
			staticCoefTextField.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"),
					!staticCoefTextField.getText().isEmpty()
							&& !staticCoefTextField.getText().matches("^([+]?)(0|([1-9][0-9]*))(\\.[0-9]+)?$"));
		});

		kineticCoefTextField.textProperty().addListener(event -> {
			kineticCoefTextField.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"),
					!kineticCoefTextField.getText().isEmpty()
							&& !kineticCoefTextField.getText().matches("^([+]?)(0|([1-9][0-9]*))(\\.[0-9]+)?$"));
		});

		// slider changes <-> static / kinetic coefficient changes
		staticCoefSlider.valueProperty().bindBidirectional(this.simul.getSur().staCoefProperty());

		// static / kinetic coefficient changes -> text field changes
		this.simul.getSur().staCoefProperty().addListener((observable, oldValue, newValue) -> {
			staticCoefTextField.setText(String.format("%.3f", newValue));
		});

		// unfocus text filed -> set text of text field
		staticCoefTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				staticCoefTextField.setText(String.format("%.3f", this.simul.getSur().getStaCoef()));
			}
		});

		// slider changes -> static / kinetic coefficient changes
		staticCoefSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			try {
				this.simul.getSur().setStaCoef(newValue.doubleValue());
			} catch (Exception e) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText(e.getMessage() + "\nPlease input a number >= 0 and <= " + Surface.MAX_STA_COEF);
				alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert.showAndWait();
			}
		});

		kineticCoefSlider.valueProperty().bindBidirectional(this.simul.getSur().kiCoefProperty());

		this.simul.getSur().kiCoefProperty().addListener((observable, oldValue, newValue) -> {
			kineticCoefTextField.setText(String.format("%.3f", newValue));
		});

		kineticCoefTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				kineticCoefTextField.setText(String.format("%.3f", this.simul.getSur().getKiCoef()));
			}
		});

		kineticCoefSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			try {
				this.simul.getSur().setKiCoef(newValue.doubleValue());
			} catch (Exception e) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText(e.getMessage() + "\nPlease input a number >= 0 and <= " + Surface.MAX_STA_COEF);
				alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert.showAndWait();
			}
		});

		// when static / kinetic friction coefficient changes -> friction force changes
		this.simul.objProperty().addListener((observable, oldValue, newValue) -> {
			((FrictionForce) this.simul.getfForce()).setMainObj(newValue);
			((FrictionForce) this.simul.getfForce()).setValue(0);
		});

		// updates friction force when static / kinetic coefficient changes
		surfaceListener();
	}

	// when text filed entered -> static / kinetic friction coefficient changes
	@FXML
	void staticTextFieldOnAction(ActionEvent event) {
		try {
			double newValue = Double.parseDouble(staticCoefTextField.getText());
			this.simul.getSur().setStaCoef(newValue);
			staticCoefTextField.getParent().requestFocus();
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setContentText(e.getMessage() + "\nPlease input a number >= 0 and <= " + Surface.MAX_STA_COEF);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.showAndWait();
		}
	}

	@FXML
	void kineticTextFieldOnAction(ActionEvent event) {
		try {
			double newValue = Double.parseDouble(kineticCoefTextField.getText());
			this.simul.getSur().setKiCoef(newValue);
			kineticCoefTextField.getParent().requestFocus();
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setContentText(e.getMessage() + "\nPlease input a number >= 0 and < " + Surface.MAX_STA_COEF);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.showAndWait();
		}
	}

	// updates friction force when static / kinetic coefficient changes
	public void surfaceListener() {
		try {
			this.simul.getSur().staCoefProperty().addListener((observable, oldValue, newValue) -> {
				try {
					((FrictionForce) this.simul.getfForce()).updateFrictionForce();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

			this.simul.getSur().kiCoefProperty().addListener((observable, oldValue, newValue) -> {
				try {
					((FrictionForce) this.simul.getfForce()).updateFrictionForce();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
