package dsai.group07.force.model;

import dsai.group07.force.model.object.Cube;
import dsai.group07.force.model.object.MainObject;
import dsai.group07.force.model.surface.Surface;
import dsai.group07.force.model.vector.HorizontalVector;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Simulation {
	
	
	private BooleanProperty isStart = new SimpleBooleanProperty(false); 
	private MainObject obj;
	private Surface sur;
	private HorizontalVector aForce;
	private HorizontalVector fForce;
	
	public Simulation() {
		this.obj = new Cube();
		this.sur = new Surface();
		this.aForce = new HorizontalVector(0);
		this.fForce = new HorizontalVector(0);
	}
	
	
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
	
	public BooleanProperty isStartProperty() {
		return this.isStart;
	}
	
	public void setIsStart(boolean isStart) {
		this.isStart.set(isStart);
	}
	
	public boolean getIsStart() {
		return this.isStart.get();
	}
	// sumForce
	// Surfaces
	
	public void pause() {
		///
	}
	
	public void restart() {
		isStart.set(false);
	}
	
	
}
