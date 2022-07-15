/*
 * The class functionalities: initialize control panel which includes: 4 main controller panels
 */

package dsai.group07.force.controller;

import java.io.IOException;

import dsai.group07.force.model.Simulation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ControlPanelController {

	private Simulation simul;

	private ObjectPanelController objController;
	private StatisticsPanelController staController;
	private ForcePanelController forceController;
	private SurfacePanelController surfaceController;

	private StackPane topStackPane;
	private StackPane downStackPane;
	private Circle cir;
	private Rectangle rec;

	@FXML
	private GridPane controlPanelGridPane;

	public void setTopStackPane(StackPane topStackPane) {
		this.topStackPane = topStackPane;
	}

	public void setDownStackPane(StackPane downStackPane) {
		this.downStackPane = downStackPane;
	}

	public void setSimul(Simulation simul) {
		this.simul = simul;
	}

	public ObjectPanelController getObjController() {
		return objController;
	}

	public GridPane getControlPanelGridPane() {
		return controlPanelGridPane;
	}

	public void init(Simulation simul, StackPane topStackPane, StackPane downStackPane) {
		setSimul(simul);
		setTopStackPane(topStackPane);
		setDownStackPane(downStackPane);

		showObjectPane();
		showForcePane();
		showSurfacePane();
		showStatisticsPane();
	}

	private void showObjectPane() {
		// Add and show objectPanel in ControlPane
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/dsai/group07/force/view/ObjectPanel.fxml"));

			// Add it to control Panel

			GridPane ObjectPanel = (GridPane) loader.load();
			GridPane.setMargin(ObjectPanel, new Insets(41, 40, 8, 20));
			controlPanelGridPane.add(ObjectPanel, 0, 0);

			// initialize the obj controller
			objController = loader.getController();
			objController.init(simul, topStackPane, downStackPane);

			// Get rec, cir from obj controller and pass it to statistic controller
			this.rec = objController.getRec();
			this.cir = objController.getCir();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showStatisticsPane() {
		// Add and show StatisticsPane
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/dsai/group07/force/view/StatisticsPanel.fxml"));

			StackPane panel = (StackPane) loader.load();
			StackPane.setAlignment(panel, Pos.TOP_CENTER);
			topStackPane.getChildren().add(panel);
			StackPane.setMargin(panel, new Insets(50, 50, 0, 0));

			// Reponsive app
			panel.translateXProperty().bind(topStackPane.widthProperty().divide(2.6));
			staController = loader.getController();
			staController.init(simul, this.rec, this.cir, this.topStackPane);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void showForcePane() {
		// Add and show ForcePane
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/dsai/group07/force/view/ForcePanel.fxml"));

			GridPane forcePanel = (GridPane) loader.load();
			controlPanelGridPane.add(forcePanel, 1, 0);
			forceController = loader.getController();
			forceController.init(simul);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showSurfacePane() {
		// Add and show SurfacePane
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/dsai/group07/force/view/SurfacePanel.fxml"));

			GridPane surfacePanel = (GridPane) loader.load();
			controlPanelGridPane.add(surfacePanel, 2, 0);
			surfacePanel.setAlignment(Pos.BOTTOM_CENTER);

			surfaceController = loader.getController();
			surfaceController.init(simul);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
