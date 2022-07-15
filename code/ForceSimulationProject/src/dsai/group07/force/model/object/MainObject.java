package dsai.group07.force.model.object;

import dsai.group07.force.model.vector.Force;
import dsai.group07.force.model.vector.HorizontalVector;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;

/**
 * This class is an abstract class, which is parent class of Cube and Cylinder.
 * It contains attributes and methods for mass, transitional acceleration, transitional velocity and position.
 */

public abstract class MainObject {
	
	/**
	 * Holds default value of mass of class MainObject.
	 */
	public static final double DEFAULT_MASS = 50;
	
	/**
	 * A HorizontalVector holds the massProperty of MainObject.
	 */
	private DoubleProperty mass = new SimpleDoubleProperty(DEFAULT_MASS);
	
	/**
	 * Holds the transitional acceleration property of this MainObject.
	 * Initial value is 0.0.
	 */
	private HorizontalVector acc = new HorizontalVector(0.0);
	
	/**
	 * Holds the transition velocity property of this MainObject.
	 * Initial value is 0.0.
	 */
	private HorizontalVector vel = new HorizontalVector(0.0);
	
	/**
	 * Holds position property of this MainObject.
	 * Initial value is 0.0.
	 */
	private DoubleProperty pos = new SimpleDoubleProperty();

	/**
	 * Gets the mass property of this MainObject.
	 * @return This MainObject's mass property.
	 */
	public DoubleProperty massProperty() {
		return mass;
	}

	/**
	 * Gets the mass of this MainObject.
	 * @return This MainObject's mass.
	 */
	public double getMass() {
		return mass.get();
	}

	/**
	 * Changes the mass of this MainObject
	 * @param mass This MainObject's new mass
	 * @throws Exception Throws new Exception if new mass <= 0
	 */
	public void setMass(double mass) throws Exception {
		// mass must > 0
		if (mass <= 0) {
			throw new Exception("Object's mass must be > 0");
		} else {
			this.mass.setValue(mass);
		}
	}

	/**
	 * Gets the transitional acceleration property of this MainObject
	 * @return This MainObject's transitional acceleration property
	 */
	public HorizontalVector accProperty() {
		return acc;
	}

	/**
	 * Gets the transitional velocity of this MainObject
	 * @return This MainObject transitional velocity
	 */
	public HorizontalVector velProperty() {
		return vel;
	}

	/**
	 * Changes the transitional acceleration of this MainObject
	 * @param acc This MainObject's new transitional acceleration
	 */
	public void setAcc(double acc) {
		this.acc.setValue(acc);
	}

	/**
	 * Changes the transitional velocity of this MainObject
	 * @param vel This MainObject's new transitional velocity
	 */
	public void setVel(double vel) {
		this.vel.setValue(vel);
	}

	/**
	 * Updates the transitional acceleration of this MainObject 
	 * when apply a Force on it
	 * @param force The Force applied in this MainObject
	 */
	public void updateAcc(Force force) {
		setAcc(force.getValue() / getMass());
	}

	/**
	 * Updates the transitional velocity of this MainObject
	 * in time t
	 * @param t The time interval between two stages of this MainObject
	 */
	public void updateVel(double t) {
		// old transitional velocity
		double oldVel = velProperty().getValue();
		// new transitional velocity
		double newVel = oldVel + accProperty().getValue() * t;
		// when transitional velocity of MainObject changes sign
		// It must be set to 0 before continuing update to avoid effect of time interval t
		// In case applied force < normal force * static coefficient -> make Cube stop
		if (oldVel * newVel < 0) {
			setVel(0);
		} else {
			setVel(newVel);
		}
	}
	/**
	 * Applies force in time time interval to update 
	 * transitional acceleration, transitional velocity and position
	 * @param netforce The net force which applies in this MainObject
	 * @param fForce The friction force which applies in this MainObject
	 * @param t The time interval when applying forces
	 */
	public void applyForceInTime(Force netforce, Force fForce, double t) {
		// old transitional velocity
		double oldVel = velProperty().getValue();
		// update transitional acceleration
		updateAcc(netforce);
		// update transitional velocity
		updateVel(t);
		// update position
		updatePos(oldVel, t);
	}

	/** 
	 * Gets the position property of this MainObject
	 * @return This MainObject's position property
	 */
	public DoubleProperty posProperty() {
		return pos;
	}

	/**
	 * Gets the position of this MainObject
	 * @return This MainObject position
	 */
	public double getPos() {
		return pos.get();
	}

	/**
	 * Changes the position of this MainObject
	 * @param pos This MainObject's new position
	 */
	public void setPos(double pos) {
		this.pos.setValue(pos);
	}
	/**
	 * Updates the position of this MainObject
	 * @param oldVel This MainObject's old transitional velocity
	 * @param t The time interval between two stages of this MainObject
	 */
	public void updatePos(double oldVel, double t) {
		setPos(getPos() + oldVel * t + 0.5 * accProperty().getValue() * t * t);
	}

	/**
	 * Default class constructor.
	 */
	public MainObject() {
	}
	
	/**
	 * Class constructor specifying mass
	 * @param mass The mass of this MainObject
	 * @throws Exception Throw exception if mass <= 0
	 */
	public MainObject(double mass) throws Exception {
		setMass(mass);
	}

}
