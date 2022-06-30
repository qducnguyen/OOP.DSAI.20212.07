package dsai.group07.force.controller;

import java.io.IOException;

import dsai.group07.force.model.Simulation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class ControlPanelController {
	
	private Simulation simul;
	private ObjectPanelController objController;
	private StatisticsPanelController staController;
	private ForcePanelController forceController;
	private SurfacePanelController surfaceController;
	private StackPane topStackPane;
	private StackPane downStackPane;
	private VectorController vectorController;
	
	@FXML
	private GridPane controlPanelGridPane;
	
	
	@FXML
   	public void initialize()  {
		
    }
	
	public void init(Simulation simul, StackPane topStackPane, StackPane downStackPane) {
		setSimul(simul);
		setTopStackPane(topStackPane);
		setDownStackPane(downStackPane);
		showObjectPanel();
		showForcePanel();
		showSurfacePanel();
		showStatisticsPanel();
		showVector();
	}
	
	
	
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

	private void showObjectPanel() {
		try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/dsai/group07/force/view/ObjectPanel.fxml"));
		GridPane ObjectPanel = (GridPane) loader.load();
		controlPanelGridPane.add(ObjectPanel, 0, 0);
		
		objController = loader.getController();
		
		objController.setSimul(simul);
		objController.setTopStackPane(topStackPane);
		objController.setDownStackPane(downStackPane);
		
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showStatisticsPanel() {
		try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/dsai/group07/force/view/StatisticsPanel.fxml"));
		StackPane panel = (StackPane) loader.load();
		
		topStackPane.getChildren().add(panel);
		
		staController = loader.getController();
		
		staController.setSimul(simul);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void showVector() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/dsai/group07/force/view/Vector.fxml"));
			GridPane panel = (GridPane) loader.load();
			
			this.topStackPane.getChildren().add(panel);
			
			this.vectorController = loader.getController();
			this.vectorController.setsim(simul);
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	#private void showForcePanel() {
	#	try {
	#		FXMLLoader loader = new FXMLLoader();
	#		loader.setLocation(getClass().getResource("/dsai/group07/force/view/ForcePanel.fxml"));
	#		StackPane forcePanel = (StackPane) loader.load();
	#		
	#		controlPanelGridPane.add(forcePanel, 1, 0);
	#	
	#		forceController = loader.getController();
	#		
	#		forceController.setSimul(simul);
	#	}
	#	
	#	catch(IOException e) {
	#		e.printStackTrace();
	#	}
	#}
	
	private void showSurfacePanel() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/dsai/group07/force/view/SurfacePanel.fxml"));
			GridPane surfacePanel = (GridPane) loader.load();
			
			controlPanelGridPane.add(surfacePanel, 2, 0);
			
			surfaceController = loader.getController();

			surfaceController.setSimul(simul);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
