package dsai.group07.force.model.vector;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class HorizontalVector {
	
	private BooleanProperty direction = new SimpleBooleanProperty(true);
	private DoubleProperty value = new SimpleDoubleProperty(0.0);
	
	public HorizontalVector(double value) {
		this.setValue(value);
	}
	
	public static final HorizontalVector sumTwoVector(HorizontalVector v1, HorizontalVector v2) {
		HorizontalVector result = new HorizontalVector(v1.getValue() + v2.getValue());
		result.updateValueDirection();
		return result;
		
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
	
	private void updateValueDirection() {
		if(this.getValue() >= 0) {
			this.direction.set(true);;
		}
		else {
			this.direction.set(false);
		}
	}
	
	private void updateDirectionValue() {
		double absValue =	Math.abs(this.getValue());
		if (this.getDirection()) {
			this.value.set(absValue);
		}
		else {
			this.value.set(-absValue);
		}
				}
}
