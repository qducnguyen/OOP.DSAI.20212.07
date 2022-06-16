package dsai.group07.force.model.object;

import dsai.group07.force.model.vector.HorizontalVector;

public class Cube extends MainObject{
	@Override
	public void updateAcc(HorizontalVector sumForce) {
		// TODO Auto-generated method stub
		setAccValue(sumForce.getValue() / getMass());
	}
}
