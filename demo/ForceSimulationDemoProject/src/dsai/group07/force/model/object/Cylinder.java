package dsai.group07.force.model.object;

import dsai.group07.force.model.vector.HorizontalVector;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.HorizontalDirection;

public class Cylinder extends MainObject implements Rotatable{
@Override
public void updateAcc(HorizontalVector sumForce) {
	setAccValue(sumForce.getValue() / getMass());
}

@Override
	public DoubleProperty getAngAcc() {
		// TODO Auto-generated method stub
		return null;
	}
@Override
	public DoubleProperty getAngVel() {
		// TODO Auto-generated method stub
		return null;
	}
@Override
		public void upAngAcc(HorizontalDirection fric) {
			// TODO Auto-generated method stub
			
		}
@Override
			public void upAngVel(HorizontalDirection fric) {
				// TODO Auto-generated method stub
				
			}
}

