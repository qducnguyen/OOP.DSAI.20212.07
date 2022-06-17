package view;

import java.text.NumberFormat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import static view.MainApp.model;

public class Controller {
    @FXML
    private TextField staticCoefTextField;

    @FXML
    private TextField kineticCoefTextField;

    @FXML 
    private Slider staticCoefSlider;

    @FXML
    private Slider kineticCoefSlider;

    @FXML
    private Button playButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button resetButton;

    @FXML
    public void playButtonPress(ActionEvent e) {
        model.continueGame();
        playButton.setDisable(true);
        pauseButton.setDisable(false);
    }

    @FXML
    public void pauseButtonPress(ActionEvent e) {
        model.pauseGame();
        playButton.setDisable(false);
        pauseButton.setDisable(true);
    }

    @FXML
    public void resetButtonPress(ActionEvent e) {
        model.resetGame();
        kineticCoefSlider.setValue(0);
        staticCoefSlider.setValue(0);
        playButton.setDisable(true);
        pauseButton.setDisable(false);
    }

    public void initialize() {
        staticCoefTextField.textProperty().bindBidirectional(
            staticCoefSlider.valueProperty(), NumberFormat.getNumberInstance());

        kineticCoefTextField.textProperty().bindBidirectional(
            kineticCoefSlider.valueProperty(), NumberFormat.getNumberInstance());
        
        //kineticCoefSlider.maxProperty().bind(staticCoefSlider.valueProperty());

        staticCoefTextField.textProperty().bindBidirectional(
            model.getSurface().getStaticCoefProperty(), NumberFormat.getNumberInstance());

        kineticCoefTextField.textProperty().bindBidirectional(
            model.getSurface().getKineticCoefProperty(), NumberFormat.getNumberInstance());

        staticCoefSlider.valueProperty().bindBidirectional(model.getSurface().getStaticCoefProperty());
        kineticCoefSlider.valueProperty().bindBidirectional(model.getSurface().getKineticCoefProperty());
        
        staticCoefTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                model.getSurface().setStaticCoefValue(Double.parseDouble(newValue));
            } catch (ArithmeticException e) {
                pauseButtonPress(new ActionEvent());
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(e.getMessage());
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
            }
        });

        kineticCoefTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                model.getSurface().setKineticCoefValue(Double.parseDouble(newValue));
            } catch (ArithmeticException e) {
                pauseButtonPress(new ActionEvent());
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(e.getMessage());
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
            }
        }); 
    }
}
