/*
 * main Controller for the application
 */

package dsai.group07.force.controller;

import java.io.IOException;

import dsai.group07.force.model.Simulation;
import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class ForceSimulationAppController {

	private Simulation simul;

	private StackPane topStackPane;
	private StackPane downStackPane;

	private GridPane controlPanel;
	private AnimationController aniController;
	private ObjectPanelController objController;
	private ControlPanelController controlController;

	@FXML
	private GridPane rootLayout;

	@FXML
	private Button pauseButton;

	@FXML
	private Button resetButton;

	public void setSimul(Simulation simul) {
		this.simul = simul;
	}

	public void init(Simulation simul) {
		// Initialize the controller
		setSimul(simul);
		showAnimation();
		showControlPane();
		setUpPauseResetOperation();
	}

	private void showAnimation() {
		// show animation including image views of 'cloud' background and 'surface'
		// background
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/dsai/group07/force/view/Animation.fxml"));
			GridPane gridPaneOutSide = (GridPane) loader.load();

			// get and add background to the root
			topStackPane = (StackPane) gridPaneOutSide.getChildren().get(0);
			downStackPane = (StackPane) gridPaneOutSide.getChildren().get(1);
			this.rootLayout.getChildren().add(topStackPane);
			this.rootLayout.getChildren().add(downStackPane);

			// initialize the animation controller
			aniController = loader.getController();
			aniController.setSim(simul);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showControlPane() {
		// show control pane including: object, surface panel, statistic panel, force
		// panel
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/dsai/group07/force/view/ControlPanel.fxml"));

			// add control pane into downStackPane
			controlPanel = (GridPane) loader.load();
			downStackPane.getChildren().add(controlPanel);

			// initialize the control controller
			controlController = loader.getController();
			controlController.init(simul, topStackPane, downStackPane);

			// get object controller in order to pause/start correctly
			this.objController = controlController.getObjController();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void resetButtonPressed() {
		// TODO: Reset ControlPanel
		// Reset scene manually through other controllers and the model as well.
		this.aniController.resetAnimation();
		this.objController.resetObjectPosition();
		this.objController.resetCirAnimation();

		// Reset the model
		this.simul.restart();

	}

	@FXML
	public void pauseButtonPressed() {
		// Start if the app is not start yet, continue if the app is pause, pause if the
		// app is running just through model
		if (this.aniController.getParallelTransitionUp().getStatus() == Animation.Status.RUNNING) {
			simul.pause();
		} else {
			if (!simul.getIsStart()) {
				simul.start();
			} else {
				simul.conti();
			}
		}
	}

	private void setUpPauseResetOperation() {
		// the method controls start/pause of the program
		// Add pause and reset button to the topStackPane
		StackPane.setAlignment(pauseButton, Pos.BOTTOM_RIGHT);
		StackPane.setAlignment(resetButton, Pos.BOTTOM_RIGHT);
		StackPane.setMargin(pauseButton, new Insets(0, 150, 10, 0));
		StackPane.setMargin(resetButton, new Insets(0, 75, 10, 0));
		topStackPane.getChildren().add(pauseButton);
		topStackPane.getChildren().add(resetButton);

		// Bind resetButton vs isStartProperty, if not start -> no reset
		resetButton.disableProperty().bind(this.simul.isStartProperty().not());

		// Null object --> Disable pause Button
		this.simul.objProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null) {
				pauseButton.setDisable(true);
			} else {
				pauseButton.setDisable(false);
			}
		});

		// When start with not null object -> start animation and rotation based on
		// startProperty of the model
		this.simul.isStartProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				this.aniController.startAmination();
				this.objController.startCirAmination();
			}
		});

		// Continue or pause based on the pauseProperty of the model
		this.simul.isPauseProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				pauseButton.setText("||");
				this.aniController.continueAnimation();
				this.objController.continueCirAnimation();
			} else {
				pauseButton.setText(">");
				this.aniController.pauseAnimation();
				this.objController.pauseCirAnimation();
			}
		});

	}

}
