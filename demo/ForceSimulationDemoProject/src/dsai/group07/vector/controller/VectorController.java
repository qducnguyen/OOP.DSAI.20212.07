package dsai.group07.vector.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class VectorController implements Initializable{
	@FXML
	ImageView myVector;
	
	@FXML 
	CheckBox myVisible;
	
	@FXML
	Slider mySlider;
	
	double scale = 0;
	double y = 0;
	
	public void increaseXvalue(double scale) {
		this.myVector.setScaleX(scale);
	}
	
//	public void decreaseXvalue(double dx) {
//		this.x -= dx;
//		this.myVector.setScaleX(x);
//	}
	
	public void increaseYvalue(int dy) {
		this.y += dy;
	}
	
	public void checkBoxValue() {
		if (this.myVisible.isSelected()) {
			this.myVector.setVisible(true);
		}
		else {
			this.myVector.setVisible(false);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mySlider.valueProperty().addListener(new MyMouseListener(this));		
	}
	public Slider getSlider() {
		return mySlider;
	}
	
	
}
