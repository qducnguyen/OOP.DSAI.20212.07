package dsai.group07.force.model;

import dsai.group07.force.model.object.MainObject;
import dsai.group07.force.model.surface.Surface;
import dsai.group07.force.model.vector.HorizontalVector;

public class Simulation {
	
	private boolean isPause;
	private MainObject obj;
	private Surface sur;
	private HorizontalVector aForce;
	private HorizontalVector fForce;
	
	
	public MainObject getObj() {
		return obj;
	}
	
	public Surface getSur() {
		return sur;
	}
	
	
	public HorizontalVector getaForce() {
		return aForce;
	}

	public void setaForce(double aForce) {
		this.aForce.setValue(aForce);
	}

	public HorizontalVector getfForce() {
		return fForce;
	}

	public void setfForce(double fForce) {
		this.fForce.setValue(fForce);
	}
	
	// sumForce
	// Surfaces
	

	public void pause() {
		///
	}
	
	public void restart() {
		//
	}
	
}
