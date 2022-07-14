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

	@FXML
	public void initialize() {
		forceTextField.setDisable(true);
		forceSlider.setDisable(true);

		forceTextField.textProperty().addListener(event -> {
			// If the value passed into Text Field isn't decimal then raise an error.
			forceTextField.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"),
					!forceTextField.getText().isEmpty() && !forceTextField.getText().matches("\\\\d+\\\\.\\\\d+"));
		});

		forceSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			forceTextField.setText(String.format("%.3f", newValue));
		});
	}
	
	
	public void init(Simulation simul) {
		this.simul = simul;

		forceSlider.valueProperty().bindBidirectional(this.simul.getaForce().valueProperty());
		netForceListener();

		this.simul.getaForce().valueProperty().addListener(observable -> {
			try {
				((FrictionForce) this.simul.getfForce()).updateFrictionForce();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

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

		this.simul.objProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null) {
				forceTextField.setDisable(true);
				forceSlider.setDisable(true);
				forceTextField.setText("0");
				this.simul.setaForce(0);
			} else {
				forceTextField.setDisable(false);
				forceSlider.setDisable(false);
				((FrictionForce) this.simul.getfForce()).setMainObj(newValue);

				this.simul.getObj().velProperty().valueProperty().addListener((observable1, oldValue1, newValue1) -> {
					((FrictionForce) this.simul.getfForce()).updateFrictionForce();
				});

			}
		});

	}

	@FXML
	void forceTextFieldOnAction(ActionEvent event) {
		try {
			if (Math.abs((double) Double.parseDouble(forceTextField.getText())) > AppliedForce.ABS_MAX_AFORCE) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("\nPlease input a number >= -500 and <= 500");
				alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert.showAndWait();
			}
			this.simul.getaForce().setValue((double) Double.parseDouble(forceTextField.getText()));
			System.out.println(
					"Current Applied Force Value " + this.simul.getaForce().getValue() + " from On Action force Text");
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setContentText(e.getMessage() + "\nPlease input a number >= -500 and <= 500");
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.showAndWait();
		}
	}

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
