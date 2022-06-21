package dsai.group07.force.model.object;

import dsai.group07.force.model.vector.Force;
import dsai.group07.force.model.vector.HorizontalVector;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;

public abstract class MainObject {
	
	private DoubleProperty mass = new SimpleDoubleProperty();
	private HorizontalVector acc = new HorizontalVector(0.0);
	private HorizontalVector vel = new HorizontalVector(0.0);
	private DoubleProperty pos = new SimpleDoubleProperty();
	
	public DoubleProperty massProperty() {
		return mass;
	}
	
	public double getMass() {
		return mass.get();
	}
	
	public void setMass(double mass) throws ArithmeticException {
		if (mass <= 0) {
			throw new ArithmeticException("Object's mass must be > 0");
		} else {
			this.mass.setValue(mass);
		}
	}

	public HorizontalVector accProperty() {
		return acc;
	}

	public HorizontalVector velProperty() {
		return vel;
	}
	
	public void setAcc(double acc) {
		this.acc.setValue(acc);
	}
	
	public void setVel(double vel) {
		this.vel.setValue(vel);
	}
	
	public void updateAcc(Force force) {
		setAcc(force.getValue() / getMass());
	}
	
	public void updateVel(double a, double t) {
		setVel(velProperty().getValue() + a * t);
	}
	
	public void applyForceInTime(Force force, double t) {
		updateAcc(force);
		updateVel(this.acc.getValue(), t);
	}
	
	public DoubleProperty posProperty() {
		return pos;
	}
	
	public double getPos() {
		return pos.get();
	}
	
	public void setPos(double pos) {
		this.pos.setValue(pos);
	}
	
	public void updatePos(HorizontalVector vel, double t) {
		setPos(getPos() + vel.getValue() * t); 
	}
	
	public MainObject() {
		setMass(0.001);
	}
	
	public MainObject(double mass) throws ArithmeticException {
		setMass(mass);
	}

}
