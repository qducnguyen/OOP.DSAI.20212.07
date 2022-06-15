package dsai.group07.force.model.object;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.HorizontalDirection;

public interface Rotatable {
	DoubleProperty getAngAcc();
	DoubleProperty getAngVel();
	void upAngAcc(HorizontalDirection fric);
	void upAngVel(HorizontalDirection fric);
}
