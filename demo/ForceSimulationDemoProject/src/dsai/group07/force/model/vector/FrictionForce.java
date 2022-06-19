package dsai.group07.force.model.vector;

import dsai.group07.force.model.Simulation;

public class FrictionForce extends Force {
	
	private Simulation sim;

	public FrictionForce(double value) {
		super(value);
	}
	
	public FrictionForce(double value, Simulation sim) {
		this(value);
		this.sim = sim;
		//Add listener to changes of Simulation
		updateValue();
	}
	
	public void updateValue() {
		//TODO: Use Surface and MainObject to calculate FrictionForce 
	}

}
