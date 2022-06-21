package dsai.group07.force.model.object;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Cube extends MainObject {
	
	private DoubleProperty size = new SimpleDoubleProperty(MAX_SIZE);
	public static final double MAX_SIZE = 1.0;
	
	public Cube() {
		super();
	}
	
	public Cube(double mass) throws ArithmeticException {
		super(mass);
	}
	
	public Cube(double mass, double size) throws ArithmeticException {
		this(mass);
		setSize(size);
	}
	
	public DoubleProperty sizeProperty() {
		return size;
	}
	
	public double getSize() {
		return size.get();
	}
	
	public void setSize(double size) throws ArithmeticException {
		if (size <=  0) {
			setSize(0.001);
			throw new ArithmeticException("Cube's size must > 0 and <= " + MAX_SIZE);
		} else if (size > MAX_SIZE) {
			setSize(MAX_SIZE);
			throw new ArithmeticException("Cube's size must > 0 and <= " + MAX_SIZE);
		} else {
			this.size.setValue(size);
		}
	}
	
}
