package dsai.group07.force.model.object;

import dsai.group07.force.model.vector.Force;
import dsai.group07.force.model.vector.FrictionForce;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;


public class Cylinder extends MainObject implements Rotatable{

	private DoubleProperty angle = new SimpleDoubleProperty();
	private DoubleProperty angAcc = new SimpleDoubleProperty();
	private DoubleProperty angVel = new SimpleDoubleProperty();
	private DoubleProperty radius = new SimpleDoubleProperty(MAX_RADIUS);
	public static final double MAX_RADIUS = 1.0;
	
	public Cylinder() throws Exception {
		super();
	}
	
	public Cylinder(double mass) throws Exception {
		super(mass);
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
	public void updateAngAcc(Force force) throws Exception {
		if (force instanceof FrictionForce) {
			setAngAcc(force.getValue() / (0.5 * getMass() * getRadius() * getRadius()));
		} 
		else {
			throw new Exception("No friction force, object just translates");
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
	public void updateAngVel(double angAcc, double t) {
		setAngVel(getAngVel() + angAcc * t);
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
	public void updateAngle(double angVel, double t) {
		setAngle(getAngle() + getAngVel() * t); 
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
		if (radius <= 0) {
			this.radius.setValue(0.001);
			throw new Exception("The radius of object must be > 0 and <= " + MAX_RADIUS);
		} 
		else if (radius > MAX_RADIUS) {
			this.radius.setValue(MAX_RADIUS);
			throw new Exception("The radius of object must be > 0 and <= " + MAX_RADIUS);
		} 
		else {
			this.radius.setValue(radius);
		}
	}

	@Override
	public void applyForceInTimeRotate(Force force, double t) throws Exception {
		updateAngAcc(force);
		updateAngVel(angAcc.get(), t);
		updateAngle(angVel.get(), t);
	}
}

