package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ForceSimulationModel;
import model.surface.Surface;

public class MainApp extends Application {

    public static ForceSimulationModel model;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
 
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Friction Slider Controller");
        stage.show();
    }

    public static void main(String[] args) {
        model = new ForceSimulationModel(new Surface(0.5, 0.25));
        Application.launch(args);
    }
    
}
