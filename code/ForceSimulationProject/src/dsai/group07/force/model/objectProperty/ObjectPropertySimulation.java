package dsai.group07.force.model.objectProperty;

import dsai.group07.force.controller.ObjectPropertyController;
import dsai.group07.force.model.Simulation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ObjectPropertySimulation extends Application{

	private Simulation simul;
	
	
	public ObjectPropertySimulation(Simulation simul) {
		this.simul = simul;
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsai/group07/force/view/ObjectProperty.fxml"));
		AnchorPane root = loader.load();
		
		ObjectPropertyController opc = loader.getController();
		opc.setsim(simul);
		
		primaryStage.setScene(new Scene(root));
		
		primaryStage.show();
		
		
		opc.getCloseWindown().addListener(
				(observable, oldValue, newValue) -> {
					if (newValue == true) {
						primaryStage.close();
					}
					
				}
		);
	}
	


}
