package dsai.group07.force.model.object;

import dsai.group07.force.model.vector.Force;
import dsai.group07.force.model.vector.FrictionForce;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
//import javafx.geometry.HorizontalDirection;

public class Cylinder extends MainObject implements Rotatable{

	private DoubleProperty angle = new SimpleDoubleProperty();
	private DoubleProperty angAcc = new SimpleDoubleProperty();
	private DoubleProperty angVel = new SimpleDoubleProperty();
	private DoubleProperty radius = new SimpleDoubleProperty(MAX_RADIUS);
	public static final double MAX_RADIUS = 1.0;
	public static final double MIN_RADIUS = 0.1;
	
	public Cylinder() throws Exception {
		super();
	}
	
	public Cylinder(double mass) throws Exception {
		super(mass);
	}
	
	public Cylinder(double mass, double radius ) throws Exception {
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
	public void updateAngAcc(Force force) throws Exception {
		if (force instanceof FrictionForce) {
			setAngAcc(-force.getValue() / (0.5 * getMass() * getRadius() * getRadius()));
		} else {
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
	public void updateAngVel(double t) {
		if (velProperty().getValue() == 0) {
			setAngVel(0);
		} else {
			setAngVel(getAngVel() + getAngAcc() * t);
		}
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
		if (radius < MIN_RADIUS) {
			this.radius.setValue(MIN_RADIUS);
			throw new Exception("The radius of object must be > " + MIN_RADIUS +" and <= " + MAX_RADIUS);
		} else if (radius > MAX_RADIUS) {
			this.radius.setValue(MAX_RADIUS);
			throw new Exception("The radius of object must be > " + MIN_RADIUS +" and <= " + MAX_RADIUS);
		} else {
			this.radius.setValue(radius);
		}
	}

	@Override
	public void applyForceInTimeRotate(Force force, double t) throws Exception {
		double oldAngVel = getAngVel();
		updateAngAcc(force);
		updateAngVel(t);
		updateAngle(oldAngVel, t);
	}
}

