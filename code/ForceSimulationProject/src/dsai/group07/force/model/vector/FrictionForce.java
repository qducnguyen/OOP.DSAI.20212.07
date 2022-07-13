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
		updateFrictionForce();
	}
	
	public void updateFrictionForce() {
		if (mainObj != null) {
			double direction = 0;
			double normalForce = mainObj.getMass() * g;
			double aForceValue = Math.abs(aForce.getValue());
			
			if (mainObj.velProperty().getLength() != 0) {
				direction = (mainObj.velProperty().getDirection() == true) ? -1: 1;
			} else {
				if (aForceValue == 0) {
					setValue(0);
					return;
				} else {
					direction = (aForce.getDirection() ==  true) ? -1: 1;
				}
			}
			
			if (mainObj instanceof Cube) {
				if (aForceValue <= surface.getStaCoef() * normalForce && mainObj.velProperty().getLength() < VEL_THRESHOLD) {
					setValue(-aForce.getValue());
				} else {
					setValue(direction * surface.getKiCoef() * normalForce);
				}
			}
			
			else if (mainObj instanceof Cylinder) {
				if (aForceValue <= 3 * surface.getStaCoef() * normalForce && aForceValue > 0) {
					setValue(direction * aForceValue / 3);
				} 
				else {
					setValue(direction * surface.getKiCoef() * normalForce); 
				}
			}
		}
} 

	public void setMainObj(MainObject obj) {
		this.mainObj = obj;
	}

}



