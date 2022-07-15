package dsai.group07.force.model.vector;

import dsai.group07.force.model.object.Cube;
import dsai.group07.force.model.object.Cylinder;
import dsai.group07.force.model.object.MainObject;
import dsai.group07.force.model.surface.Surface;

/**
 * This class is used to represent the friction force between the Surface and
 * the MainObject. It is a kind of Force and contains further attributes and
 * methods for calculating value of friction force when changing Surface,
 * MainObject and AppiedForce.
 *
 */
public class FrictionForce extends Force {

	/**
	 * Holds the current Surface relating to this FrictionForce
	 */
	private Surface surface;
	/**
	 * Holds the current MainObject relating to this FrictionForce
	 */
	private MainObject mainObj;
	/**
	 * Holds the current Applied relating to this FrictionForce
	 */
	private AppliedForce aForce;
	/**
	 * Holds the value of gravitational acceleration
	 */
	public static final double g = 10;
	/**
	 * Holds the minimum transition velocity threshold. Below this threshold, only
	 * the static friction force takes effect on Cube while kinetic friction force
	 * disappear
	 */
	public static final double VEL_THRESHOLD = 0.001;

	/**
	 * Class constructor specifying value of this FrictionForce
	 * 
	 * @param value The value of this FrictionForce
	 */
	public FrictionForce(double value) {
		super(value);
	}

	/**
	 * Class constructor specifying value of this FrictionForce, also the current
	 * Surface, MainObject and AppliedForce relating to this FrictionForce
	 * 
	 * @param value   The pseudo value this FrictionForce (this value will be
	 *                updated according to current Surface, MainObject and
	 *                AppliedForce relating to this FrictionForce)
	 * @param surface The current Surface relating to this FrictionForce
	 * @param mainObj The current MainObject relating to this FrictionForce
	 * @param aForce  The current AppliedForce relating to this FrictionForce
	 */
	public FrictionForce(double value, Surface surface, MainObject mainObj, AppliedForce aForce) {
		// init pseudo value
		super(value);
		this.surface = surface;
		this.mainObj = mainObj;
		this.aForce = aForce;
		// recalculating the actual value of this FrictionForce
		updateFrictionForce();
	}

	/**
	 * Calculates the actual value of this FrictionForce according to current
	 * Surface, MainObject and AppliedForce relating to it
	 */
	public void updateFrictionForce() {
		if (mainObj != null) {
			double direction = 0;
			// Calculates normal force
			double normalForce = mainObj.getMass() * g;
			double aForceValue = Math.abs(aForce.getValue());

			// Calculate the direction of this FrcitionForce according to its transitional
			// velocity and applied force
			// Consider object's transitional velocity first then the applied force applying
			// on it
			if (mainObj.velProperty().getLength() != 0) {
				direction = (mainObj.velProperty().getDirection() == true) ? -1 : 1;
			} else {
				if (aForceValue == 0) {
					setValue(0);
					return;
				} else {
					direction = (aForce.getDirection() == true) ? -1 : 1;
				}
			}

			// Calculates the value of this FrictionForce
			if (mainObj instanceof Cube) {
				// Handles case when object velocity = 0
				if (aForceValue <= surface.getStaCoef() * normalForce
						&& mainObj.velProperty().getLength() < VEL_THRESHOLD) {
					setValue(-aForce.getValue());
				} else {
					setValue(direction * surface.getKiCoef() * normalForce);
				}
			} else if (mainObj instanceof Cylinder) {
				if (aForceValue <= 3 * surface.getStaCoef() * normalForce && aForceValue > 0) {
					setValue(direction * aForceValue / 3);
				} else {
					// When applied force = 0, consider kinetic friction coefficient
					setValue(direction * surface.getKiCoef() * normalForce);
				}
			}
		}
	}

	/**
	 * Changes the current MainObject relating to this FrictionForce
	 * @param obj The current MainObject relating to this FrictionForce
	 */
	public void setMainObj(MainObject obj) {
		this.mainObj = obj;
		// Updates friction force when changing the MainObject relating to its
		updateFrictionForce();
	}

}
