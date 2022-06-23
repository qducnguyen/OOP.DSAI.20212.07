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
		setValue(netForce.getValue());
	}
	
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