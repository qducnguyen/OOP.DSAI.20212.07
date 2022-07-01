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
	public static final double VEL_THRESHOLD = 10e-5;

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
			double normalForce = g * mainObj.getMass();
			if (mainObj instanceof Cube) {
				// Object is motionless, friction force should oppose applied force and = applied force
				System.out.println("AAAAA");
				if (aForce.getLength() <= normalForce * surface.getStaCoef()) {
					System.out.println("BBBB");
					System.out.println(mainObj.velProperty().getLength());
					if (mainObj.velProperty().getLength() <= VEL_THRESHOLD) {
						System.out.println("CCC");
						mainObj.setVel(0);
						setValue( -aForce.getValue());
					} else {

						// Object is moving, friction force should oppose object's velocity and = normalForce * kiCoef
						double sign = -Math.signum(mainObj.velProperty().getValue());
						setValue(sign * surface.getKiCoef() * normalForce); 
					}
				} else {
					if (mainObj.velProperty().getLength() <= VEL_THRESHOLD) {
						mainObj.setVel(0);
						double sign = -Math.signum(this.aForce.getValue());
						setValue(sign * surface.getKiCoef() * normalForce);
					} else {
						double sign = -Math.signum(mainObj.velProperty().getValue());
						setValue(sign * surface.getKiCoef() * normalForce);
					}
				}
			} else if (mainObj instanceof Cylinder) {
//				System.out.println("AAAAA");
//				System.out.println(3 * normalForce * surface.getStaCoef());
				if (aForce.getLength() <= 3 * normalForce * surface.getStaCoef()) {
					System.out.println("BBBB");
					System.out.println(mainObj.velProperty().getLength());
					
//					if (mainObj.velProperty().getLength() <= VEL_THRESHOLD) {
//						System.out.println("CCC");
//						mainObj.setVel(0);
//						double result = -aForce.getValue()  / 3;
//						setValue(result); 
					
					double sign = -Math.signum(mainObj.velProperty().getValue());
					if (sign == 0) {
						sign = -Math.signum(aForce.getValue());
					}
					setValue(sign * aForce.getLength()  / 3);
					}
				else {
//					if (mainObj.velProperty().getLength() <= VEL_THRESHOLD) {
//						mainObj.setVel(0);
//						double sign = -Math.signum(this.aForce.getValue());
//						setValue(sign * surface.getKiCoef() * normalForce);
//					} else {
						double sign = -Math.signum(mainObj.velProperty().getValue());
						setValue(sign * surface.getKiCoef() * normalForce);
						
//					}
				}
				} 
			}
		} 
	

	public void setMainObj(MainObject obj) {
		this.mainObj = obj;
	}

}



