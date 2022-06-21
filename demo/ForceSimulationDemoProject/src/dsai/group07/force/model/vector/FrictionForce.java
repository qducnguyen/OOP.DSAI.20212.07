package dsai.group07.force.model.vector;

import dsai.group07.force.model.object.Cube;
import dsai.group07.force.model.object.Cylinder;
import dsai.group07.force.model.object.MainObject;
import dsai.group07.force.model.surface.Surface;

public class FrictionForce extends Force {
	
	private Surface surface;
	private MainObject mainObj;
	private AppliedForce aForce;
	public static final double g = 10;
	public static final double VEL_THRESHOLD = 0.001;

	public FrictionForce(double value) {
		super(value);
	}
	
	public FrictionForce(double value, Surface surface, MainObject mainObj, AppliedForce aForce) {
		super(value);
		this.surface = surface;
		this.mainObj = mainObj;
		this.aForce = aForce;
		//Add listener to changes of Simulation
		updateValue();
	}
	
	public void updateValue() {
		double normalForce = g * mainObj.getMass();
		
		if (mainObj instanceof Cube) {
			// Object is motionless, friction force should oppose applied force and = applied force
			if (aForce.getLength() <= normalForce * surface.getStaCoef()) {
				if (mainObj.velProperty().getLength() <= VEL_THRESHOLD) {
					mainObj.setVel(0);
					setValue(-aForce.getValue());
				} else {
					// Object is moving, friction force should oppose object's velocity and = normalForce * kiCoef
					double sign = -Math.signum(mainObj.velProperty().getValue());
					setValue(sign * surface.getKiCoef() * normalForce); 
				}
			} else {
				double sign = -Math.signum(mainObj.velProperty().getValue());
				setValue(sign * surface.getKiCoef() * normalForce); 
			}
		} else if (mainObj instanceof Cylinder) {
			
		}
	}

}
