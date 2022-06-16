package dsai.group07.vector.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class MyMouseListener implements ChangeListener<Number>{
	VectorController controller;
	double scale;
	public MyMouseListener(VectorController controller) {
		this.controller = controller;
	}

	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		scale = this.controller.getSlider().getValue();
		this.controller.increaseXvalue(scale);
	}

}
