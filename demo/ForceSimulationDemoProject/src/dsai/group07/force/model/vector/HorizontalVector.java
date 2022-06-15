package dsai.group07.force.model.vector;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class HorizontalVector {
	
	BooleanProperty direction = new SimpleBooleanProperty(true);
	DoubleProperty value = new SimpleDoubleProperty(0.0);
	
	public HorizontalVector() {
		
		value.addListener(
				(observable, oldValue, newValue) -> {
				if (newValue.doubleValue() > 0) {
					direction.set(true);
				}
				else {
					direction.set(false);
				}}
				);
		
		direction.addListener((observable, oldValue, newValue) -> {
			double newVal = Math.abs(value.doubleValue());
			if(newValue.booleanValue()) {
				value.set(newVal);
			}
			else {
				value.set(-newVal);
			}
		});
	}
	
	
	
	
	public HorizontalVector(double value) {
		this();
		this.value.set(value);
		
	}
	
//	public HorizontalVector(double length, boolean isRight) {
//		this();
//		this.value.set(value.get() * (isRight == true ? 1 : -1));
//		this.direction.set(isRight);
//	}
	
	public static final HorizontalVector sumTwoVector(HorizontalVector v1, HorizontalVector v2) {
		return new HorizontalVector(v1.getValue().doubleValue() + v2.getValue().doubleValue());
	}

	public BooleanProperty getDirection() {
		return direction;
	}

	public void setDirection(boolean isRight) {
		this.direction.set(isRight);;
	}

	public DoubleProperty getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value.set(value);
	}
	
	public double getLength() {
		return Math.abs(this.value.doubleValue());	}
	
	
}
