package dsai.group07.force.model.vector;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * This class is used to represent horizontal vector such as transition
 * acceleration, transition velocity and force. It contain attributes and
 * methods for direction and value of this vector.
 *
 */
public class HorizontalVector {
	/**
	 * Holds the direction property of this HorizontalVector. Default value is true
	 * means right direction (while false means left direction).
	 */
	protected BooleanProperty direction = new SimpleBooleanProperty(true);
	/**
	 * Holds the value property of this HorizontalVector (double number). Default
	 * value if 0.0
	 */
	protected DoubleProperty value = new SimpleDoubleProperty(0.0);

	/**
	 * Class constructor specifying value
	 * 
	 * @param value The value of this HorizontalVector
	 */
	public HorizontalVector(double value) {
		this.setValue(value);
	}

	/**
	 * Gets the direction property of this HorizontalVector
	 * 
	 * @return The direction property of this HorizontalVector
	 */
	public BooleanProperty directionProperty() {
		return this.direction;
	}

	/**
	 * Gets the direction of this HorizontalVector
	 * 
	 * @return The direction of this HorizontalVector
	 */
	public boolean getDirection() {
		return this.direction.get();
	}

	/**
	 * Changes the direction of this HorizontalVector
	 * 
	 * @param isRight This HorizontalVector's new direction
	 */
	public void setDirection(boolean isRight) {
		this.direction.set(isRight);
		// Updates value according to direction
		updateDirectionValue();
	}

	/**
	 * Gets the value property of this HorizontalVector
	 * 
	 * @return The value property of this HorizontalVector
	 */
	public DoubleProperty valueProperty() {
		return this.value;
	}

	/**
	 * Gets the value of this HorizontalVector
	 * 
	 * @return The value of this HorizontalVector
	 */
	public double getValue() {
		return this.value.get();
	}

	/**
	 * Changes the value of this HorizontalVector
	 * 
	 * @param value This HorizontalVector's new value
	 */
	public void setValue(double value) {
		this.value.set(value);
		updateValueDirection();
	}

	/**
	 * Gets the absolute value of this HorizontalVector
	 * 
	 * @return The absolute value of this HorizontalVector
	 */
	public double getLength() {
		return Math.abs(this.value.doubleValue());
	}

	/**
	 * Updates direction of this HorizontalVector according its value
	 */
	protected void updateValueDirection() {
		if (this.getValue() >= 0) {
			this.direction.set(true);
			;
		} else {
			this.direction.set(false);
		}
	}

	/**
	 * Updates the (sign of) value of this HorizontalVector according to its direction
	 */
	protected void updateDirectionValue() {
		double absValue = Math.abs(this.getValue());
		if (this.getDirection()) {
			this.value.set(absValue);
		} else {
			this.value.set(-absValue);
		}
	}
}
