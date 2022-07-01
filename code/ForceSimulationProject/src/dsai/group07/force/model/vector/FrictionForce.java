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
	/* Only used for testing model
	public void surfaceListener() {
		try {
			surface.staCoefProperty().addListener(observable -> {
				try {
					updateFrictionForce();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			surface.kiCoefProperty().addListener(observable -> {
				try {
					updateFrictionForce();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void objectListener() {
		try {
			mainObj.massProperty().addListener(observable -> {
				try {
					updateFrictionForce();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			mainObj.velProperty().valueProperty().addListener(observable -> {
				try {
					updateFrictionForce();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void aForceListener() {
		try {
			aForce.valueProperty().addListener(observable -> {
				try {
					updateFrictionForce();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	public void updateFrictionForce() {
		if (mainObj != null) {
			double direction = 0;
			if (mainObj.velProperty().getValue() != 0) {
				direction = (mainObj.velProperty().getDirection() == true) ? -1: 1;
			} else {
				if (aForce.getValue() != 0) {
					direction = (aForce.getDirection() ==  true) ? -1: 1;
				}
			}
			
			double normalForce = mainObj.getMass() * g;
			double aForceValue = Math.abs(aForce.getValue());
			
			if (mainObj instanceof Cube) {
				if (aForceValue <= surface.getStaCoef() * normalForce && aForceValue > 0) {
					setValue(direction * aForceValue);
				} else {
					setValue(direction * surface.getKiCoef() * normalForce);
				}
			} else if (mainObj instanceof Cylinder) {
				if (aForceValue <= 3 * surface.getStaCoef() * normalForce && aForceValue > 0) {
					setValue(direction * aForceValue / 3);
				} else {
					setValue(direction * surface.getKiCoef() * normalForce); 
				}
			}
		}
	} 

	public void setMainObj(MainObject obj) {
		this.mainObj = obj;
	}

}



