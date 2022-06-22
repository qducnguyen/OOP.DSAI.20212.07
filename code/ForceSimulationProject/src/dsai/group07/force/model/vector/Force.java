package dsai.group07.force.model.vector;

public class Force extends HorizontalVector {

	public Force(double value) {
		super(value);
	}
	
	public Force sumTwoForce(Force f1, Force f2) {
		Force netForce = new Force(f1.getValue() + f2.getValue());
		netForce.updateValueDirection();
		return netForce;
	}

}
