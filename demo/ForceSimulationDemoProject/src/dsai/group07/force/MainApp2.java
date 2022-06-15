package dsai.group07.force;

import dsai.group07.force.view.MainAppController2;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp2 extends Application  {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/MainApp2.fxml") );
		Parent p = fxmlLoader.load();
		
		Scene scene = new Scene(p);
		//
		
		primaryStage.setMinHeight(400);
		primaryStage.setMinWidth(650);
		//
		primaryStage.setTitle( "Force Simulation App" );
		primaryStage.setScene( scene );
		primaryStage.show();
//		
//		
//		final double MAXIMUM_RATIO = 2.5 ;
//		final double MINIMUM_RATIO = 0.4;
//		
//		widthChangeListener = (observable, oldValue, newValue) -> {
//			primaryStage.heightProperty().removeListener(heightChangeListener);
//			double newValueWidthDouble = newValue.doubleValue();
//			double heightDouble = primaryStage.getHeight();
//			double newRatio = newValueWidthDouble / heightDouble;
//			if (newRatio > MAXIMUM_RATIO) {
//				primaryStage.setHeight(newValueWidthDouble / MAXIMUM_RATIO);
//			}
//			else if(newRatio < MINIMUM_RATIO) {
//				primaryStage.setHeight(newValueWidthDouble / MINIMUM_RATIO);
//			}
//			
//	        primaryStage.heightProperty().addListener(heightChangeListener);
//	    };
//	    
//	    heightChangeListener = (observable, oldValue, newValue) -> {
//	    	primaryStage.widthProperty().removeListener(widthChangeListener);
//	    	double newValueHeightDouble = newValue.doubleValue();
//			double widthDouble = primaryStage.getWidth();
//			double newRatio = widthDouble / newValueHeightDouble;
//			
//			if (newRatio > MAXIMUM_RATIO) {
//				primaryStage.setWidth(newValueHeightDouble * MAXIMUM_RATIO);
//			}
//			else if(newRatio < MINIMUM_RATIO) {
//				primaryStage.setWidth(newValueHeightDouble * MINIMUM_RATIO);
//			}
//			
//	    	primaryStage.widthProperty().addListener(widthChangeListener);
//	    };
//
//	    primaryStage.widthProperty().addListener(widthChangeListener);
//	    primaryStage.heightProperty().addListener(heightChangeListener);
//		
//	    //
		MainAppController2 con = fxmlLoader.getController();
		con.setSce(scene);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
