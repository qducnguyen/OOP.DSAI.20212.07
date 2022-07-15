package dsai.group07.force.model.object;

import dsai.group07.force.model.vector.Force;
import dsai.group07.force.model.vector.FrictionForce;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * This class is used to represent the cylinder object. It extends MainObject
 * class and implements Rotatable interface. It contains further attributes and
 * methods for cylinder radius, angle, angular acceleration and angular
 * velocity.
 *
 */
public class Cylinder extends MainObject implements Rotatable {
	/**
	 * Holds the angle property of this Cylinder
	 */
	private DoubleProperty angle = new SimpleDoubleProperty();
	/**
	 * Holds the angular acceleration property of this Cylinder
	 */
	private DoubleProperty angAcc = new SimpleDoubleProperty();
	/**
	 * Holds the angular velocity property of this Cylinder
	 */
	private DoubleProperty angVel = new SimpleDoubleProperty();
	/**
	 * Holds the radius property of this Cylinder. Default radius = 0.3 *
	 * {@link Cylinder#MAX_RADIUS}
	 */
	private DoubleProperty radius = new SimpleDoubleProperty(MAX_RADIUS * 0.3);
	/**
	 * Holds the max radius of class Cylinder
	 */
	public static final double MAX_RADIUS = 1.0;
	/**
	 * Holds the min radius of class Cylinder
	 */
	public static final double MIN_RADIUS = 0.1;

	/**
	 * Default class constructor
	 */
	public Cylinder() throws Exception {
		super();
	}

	/**
	 * Class constructor specifying mass
	 * 
	 * @param mass This Cylinder's mass
	 * @throws Exception Throw exception if mass <= 0
	 */
	public Cylinder(double mass) throws Exception {
		super(mass);
	}

	/**
	 * Class constructor specifying mass and size
	 * 
	 * @param mass   This Cylinder's mass
	 * @param radius This Cylinder's radius
	 * @throws Exception Exception Throw exception if mass <= 0
	 */
	public Cylinder(double mass, double radius) throws Exception {
		this(mass);
		setRadius(radius);
	}

	@Override
	public DoubleProperty angAccProperty() {
		return angAcc;
	}

	@Override
	public double getAngAcc() {
		return angAcc.get();
	}

	@Override
	public void setAngAcc(double angAcc) {
		this.angAcc.setValue(angAcc);
	}

	@Override
	public void updateAngAcc(Force force) {
		// In case there is no friction force, cylinder just translates
		if (force instanceof FrictionForce) {
			setAngAcc(-force.getValue() / (0.5 * getMass() * getRadius() * getRadius()));
		}
	}

	@Override
	public DoubleProperty angVelProperty() {
		return angVel;
	}

	@Override
	public double getAngVel() {
		return angVel.get();
	}

	@Override
	public void setAngVel(double angVel) {
		this.angVel.setValue(angVel);
	}

	@Override
	public void updateAngVel(double t) {
		setAngVel(getAngVel() + getAngAcc() * t);
	}

	@Override
	public DoubleProperty angleProperty() {
		return angle;
	}

	@Override
	public double getAngle() {
		return angle.get();
	}

	@Override
	public void setAngle(double angle) {
		this.angle.setValue(angle);
	}

	@Override
	public void updateAngle(double oldAngVel, double t) {
		setAngle(getAngle() + oldAngVel * t + 0.5 * getAngAcc() * t * t);
	}

	@Override
	public DoubleProperty radiusProperty() {
		return radius;
	}

	@Override
	public double getRadius() {
		return radius.get();
	}

	@Override
	public void setRadius(double radius) throws Exception {
		if (radius < MIN_RADIUS || radius > MAX_RADIUS) {
			this.radius.setValue(MAX_RADIUS * 0.3);
			throw new Exception("The radius of object must be >= " + MIN_RADIUS + " and <= " + MAX_RADIUS);
		} else {
			this.radius.setValue(radius);
		}
	}

	@Override
	public void applyForceInTimeRotate(Force force, double t) {
		// Gets this Cylinder old angular velocity and update
		// its angular acceleration, angular velocity and angle
		double oldAngVel = getAngVel();
		updateAngAcc(force);
		updateAngVel(t);
		updateAngle(oldAngVel, t);
	}
	/**
	 * netForce causes translation and fForce causes rotation
	 */
	@Override
	public void applyForceInTime(Force netforce, Force fForce, double t) {
		// Applies netForce (sum of forces) for translation
		super.applyForceInTime(netforce, fForce, t);
		// Applies fForce (friction force) for rotation
		this.applyForceInTimeRotate(fForce, t);
	}
}
