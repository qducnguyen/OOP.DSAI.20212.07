package dsai.group07.vector;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.control.TextField;

public class VectorController {
	@FXML
	ImageView arrow = new ImageView();
	
	@FXML
	Slider slider = new Slider();
	public void initialize() {
		slider.valueProperty().addListener(new ChangeListener<Slider>() {

			@Override
			public void changed(ObservableValue<? extends Slider> observable, Slider oldValue, Slider newValue) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
//	slider.valueProperty().addListenner
}
