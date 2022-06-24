package dsai.group07.force.model.vector;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.shape.SVGPath;


public class HorizontalVector {
	
	protected BooleanProperty direction = new SimpleBooleanProperty(true);
	protected DoubleProperty value = new SimpleDoubleProperty(0.0);
//	protected ObjectProperty<SVGPath> svgPath = new SimpleObjectProperty<>(); // Declare to test
	
	public HorizontalVector(double value) {
		this.setValue(value);
	}

	public BooleanProperty directionProperty() {
		return this.direction;
	}
	
	public boolean getDirection() {
		return this.direction.get();
	}

	public void setDirection(boolean isRight) {
		this.direction.set(isRight);
		updateDirectionValue();
	}

	public DoubleProperty valueProperty() {
		return this.value;
	}

	public double getValue() {
		return this.value.get();
	}
	
	public void setValue(double value) {
		this.value.set(value);
		updateValueDirection();
	}
	
	public double getLength() {
		return Math.abs(this.value.doubleValue());	
	}
	
	// set direction for vector
	protected void updateValueDirection() {
		if(this.getValue() >= 0) {
			this.direction.set(true);;
		}
		else {
			this.direction.set(false);
		}
	}
	
	
	// set the absolute value for the vector
	protected void updateDirectionValue() {
		double absValue = Math.abs(this.getValue());
		if (this.getDirection()) {
			this.value.set(absValue);
		}
		else {
			this.value.set(-absValue);
		}
	}
}


