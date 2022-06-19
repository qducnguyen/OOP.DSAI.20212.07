package dsai.group07.force.model.vector;

public class NetForce extends Force {
	
	private Force aForce;
	private Force fForce;

	public NetForce(double value) {
		super(value);
	}
	
	public NetForce(double value, Force aForce, Force fForce) {
		super(value);
		this.aForce = aForce;
		this.fForce = fForce;
		updateValue();
	}
	
	public void updateValue() {
		//TODO: Use aForce and fForce to calculate netForce
	}

}
