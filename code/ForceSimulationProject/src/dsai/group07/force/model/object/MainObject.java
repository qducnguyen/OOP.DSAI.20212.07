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
	
	public void setMass(double mass) throws Exception {
		if (mass <= 0) {
			throw new Exception("Object's mass must be > 0");
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
	
	public void updateVel(double t) {
		setVel(velProperty().getValue() + this.accProperty().getValue() * t);
	}
	
	public void applyForceInTime(Force force, double t) {
		updateAcc(force);
		updateVel(t);
		updatePos(vel.getValue(), t);
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
	
	public void updatePos(double v, double t) {
		setPos(getPos() + v * t); 
	}
	
	public MainObject() throws Exception {
		setMass(0.001);
	}
	
	public MainObject(double mass) throws Exception {
		setMass(mass);
	}

}
