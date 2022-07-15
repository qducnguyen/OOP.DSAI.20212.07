package dsai.group07.force.model.object;

import dsai.group07.force.model.vector.Force;
import javafx.beans.property.DoubleProperty;

public interface Rotatable {

	/**
	 * Gets the angular acceleration property of this Rotatable
	 * 
	 * @return This Rotatable's angular acceleration
	 */
	DoubleProperty angAccProperty();

	/**
	 * Gets the angular acceleration of this Rotatable
	 * 
	 * @return This Rotatable's angular acceleration
	 */
	double getAngAcc();

	/**
	 * Changes the angular acceleration of this Rotatable
	 * 
	 * @param angAcc This Rotatable's angular acceleration
	 */
	void setAngAcc(double angAcc);

	/**
	 * Updates the angular acceleration of this Rotatable when a Force applies on it
	 * and causes rotation
	 * 
	 * @param force The force applies on this Rotatable and causes rotation
	 */
	void updateAngAcc(Force force);

	/**
	 * Gets the angular velocity property of this Ratatable
	 * 
	 * @return This Rotatable's angular velocity property
	 */
	DoubleProperty angVelProperty();

	/*
	 * Gets the angular velocity of this Rotatable
	 */
	double getAngVel();

	/**
	 * Changes the angular velocity of this Rotatable
	 * 
	 * @param angVel This Rotatable's new angular velocity
	 */
	void setAngVel(double angVel);

	/**
	 * Updates the angular velocity of this Rotatable in after time interval t
	 * 
	 * @param t The time interval between two stages of this Rotatable
	 */
	void updateAngVel(double t);

	/**
	 * Gets the angular property of this Rotatable
	 * 
	 * @return This Rotatable's angular property
	 */
	DoubleProperty angleProperty();

	/**
	 * Gets the angle of this Rotatable
	 * 
	 * @return This Rotatable's angle
	 */
	double getAngle();

	/**
	 * Changes the angle of this Rotatable
	 * 
	 * @param angle This Roatatable's new angle
	 */
	void setAngle(double angle);

	/**
	 * Updates the angle of this Rotatable after time interval t
	 * 
	 * @param oldAngVel This Rotatable's old angle
	 * @param t         The time interval between two stages of this Rotatable
	 */
	void updateAngle(double oldAngVel, double t);

	/**
	 * Gets the radius property of this Rotatable
	 * 
	 * @return The radius property of this Rotatable
	 */
	DoubleProperty radiusProperty();

	/**
	 * Gets the radius of this Rotatable
	 * 
	 * @return The radius of this Rotatable
	 */
	double getRadius();

	/**
	 * Changes the radius of this Rotatable
	 * 
	 * @param radius This Rotatable's new radius
	 * @throws Exception Throw exception if invalid radius
	 */
	void setRadius(double radius) throws Exception;

	/**
	 * Applies a force in a time time interval to update angular acceleration,
	 * angular velocity and angular of this Rotatable
	 * 
	 * @param force The force applies on this Rotatable that causes rotation
	 * @param t     The time interval between two stages of this Rotatable
	 */
	void applyForceInTimeRotate(Force force, double t);

}
