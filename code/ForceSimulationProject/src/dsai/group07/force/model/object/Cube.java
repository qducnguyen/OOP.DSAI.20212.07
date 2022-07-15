package dsai.group07.force.model.object;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * This class is used to represent the cube object. 
 * It is child class of MainObject class.
 * It contains further information about cube size.
 *
 */
public class Cube extends MainObject {
	/**
	 * Holds the size property of this Cube Default size = 0.3 * {@link Cube#MAX_SIZE}
	 */
	private DoubleProperty size = new SimpleDoubleProperty(MAX_SIZE * 0.3);
	// max and min size of class Cube
	public static final double MAX_SIZE = 1.0;
	public static final double MIN_SIZE = 0.1;

	/**
	 * Default class constructor
	 * 
	 * @see MainObject#MainObject()
	 */
	public Cube() {
		super();
	}

	/**
	 * Class constructor specifying mass
	 * 
	 * @param mass This Cube's mass
	 * @throws Exception Throw exception if mass <= 0
	 * @see MainObject#MainObject(double)
	 */
	public Cube(double mass) throws Exception {
		super(mass);
	}

	/**
	 * Class constructor specifying mass and size
	 * 
	 * @param mass This Cube's mass
	 * @param size This Cube's size
	 * @throws Exception Exception Throw exception if mass <= 0
	 */
	public Cube(double mass, double size) throws Exception {
		this(mass);
		setSize(size);
	}

	/**
	 * Gets size property of this Cube
	 * 
	 * @return This Cube's size property
	 */
	public DoubleProperty sizeProperty() {
		return size;
	}

	/**
	 * Gets the size of this Cube
	 * 
	 * @return This Cube's size
	 */
	public double getSize() {
		return size.get();
	}

	/**
	 * Changes the size of this Cube
	 * 
	 * @param size This Cube's new size
	 * @throws Exception Throw exception if size < {@link Cube#MIN_SIZE} or > {@link Cube#MAX_SIZE}
	 */
	public void setSize(double size) throws Exception {
		if (size < MIN_SIZE || size > MAX_SIZE) {
			setSize(MAX_SIZE * 0.3);
			throw new Exception("Cube's size must >= " + MIN_SIZE + " and <= " + MAX_SIZE);
		} else {
			this.size.setValue(size);
		}
	}

}
