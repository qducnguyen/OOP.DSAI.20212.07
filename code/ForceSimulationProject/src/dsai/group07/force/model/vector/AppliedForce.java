package dsai.group07.force.model.vector;

public class AppliedForce extends Force {

	public static final double ABS_MAX_AFORCE = 500;

	public AppliedForce(double value) {
		super(value);
	}

	@Override
	public void setValue(double value) {
		if (value > ABS_MAX_AFORCE) {
			setValue(ABS_MAX_AFORCE);
		} else if (value < -ABS_MAX_AFORCE) {
			setValue(-ABS_MAX_AFORCE);
		} else {
			super.setValue(value);
		}
	}

}
