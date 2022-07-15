package dsai.group07.force.model.vector;

/**
 * This class is used to represent a force which is a kind of HorizontalVector.
 * The difference of Force and HorizontalVector is that in this problem, we only
 * need to plus two Force not two general HorizontalVector
 *
 */
public class Force extends HorizontalVector {

	/**
	 * Class constructor specifying value
	 * @param value The value of this Force
	 */
	public Force(double value) {
		super(value);
	}

	/**
	 * Gets the sum of two forces
	 * @param f1 The first Force
	 * @param f2 The second Force
	 * @return The sum of two forces f1 and f2
	 */
	public static Force sumTwoForce(Force f1, Force f2) {
		// Create new Force which value = algebraic sum of values of f1 and f2
		Force netForce = new Force(f1.getValue() + f2.getValue());
		// Update direction 
		netForce.updateValueDirection();
		return netForce;
	}

}
