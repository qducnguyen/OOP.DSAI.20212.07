package dsai.group07.force.model.object;

import dsai.group07.force.model.vector.HorizontalVector;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class MainObject {
	private DoubleProperty mass = new SimpleDoubleProperty(0.0);
	private HorizontalVector acc = new HorizontalVector(0.0);
	private HorizontalVector vel = new HorizontalVector(0.0);
	
	public DoubleProperty getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass.setValue(mass);
	}

	public HorizontalVector getAcc() {
		return acc;
	}

	public HorizontalVector getVel() {
		return vel;
	}
	
	public abstract void updateAcc(HorizontalVector sumVec);
	public abstract void updateVel();
}
