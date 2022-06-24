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
		updateNetForce();
		forcesListener();
	}
	
	public void updateNetForce() {
		Force netForce = sumTwoForce(aForce, fForce);
		// Set value of this netForce, class netForce inherit value from its base class
		setValue(netForce.getValue());
		
	}
	
	// To do: Convert Listener to controller
	public void forcesListener() {
		try {
			aForce.valueProperty().addListener(observable -> {
				try {
					updateNetForce();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			fForce.valueProperty().addListener(observable -> {
				try {
					updateNetForce();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
