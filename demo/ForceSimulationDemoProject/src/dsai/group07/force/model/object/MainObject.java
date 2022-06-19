package dsai.group07.force.model.object;

import dsai.group07.force.model.vector.Force;
import dsai.group07.force.model.vector.HorizontalVector;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.DoubleProperty;

public abstract class MainObject {
	private IntegerProperty mass = new SimpleIntegerProperty(0);
	private HorizontalVector acc = new HorizontalVector(0.0);
	private HorizontalVector vel = new HorizontalVector(0.0);
	private DoubleProperty pos = new SimpleDoubleProperty(0.0);
	
	public IntegerProperty massProperty() {
		return this.mass;
	}
	
	public int getMass() {
		return this.mass.get();
	}
	
	public void setMass(int mass) {
		this.mass.setValue(mass);
	}

	public HorizontalVector getAcc() {
		return acc;
	}

	public HorizontalVector getVel() {
		return vel;
	}
	
	public void setAccValue(double acc) {
		this.acc.setValue(acc);
	}
	
	public void updateVel(double t) {
		double a = getAcc().getValue();
		this.vel.setValue(getVel().getValue() + a * t);
	};
	
	public void updateAcc(Force force) {
		this.acc.setValue(force.getValue() / getMass());
	};
	
	public void applyForceInTime(Force force, double t) {
		updateAcc(force);
		updateVel(t);
	}

}
