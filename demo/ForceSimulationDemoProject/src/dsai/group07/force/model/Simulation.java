package dsai.group07.force.model;

import java.awt.Robot;

import dsai.group07.force.model.object.MainObject;
import dsai.group07.force.model.object.Rotatable;
import dsai.group07.force.model.surface.Surface;
import dsai.group07.force.model.vector.AppliedForce;
import dsai.group07.force.model.vector.Force;
import dsai.group07.force.model.vector.FrictionForce;
import dsai.group07.force.model.vector.HorizontalVector;
import dsai.group07.force.model.vector.NetForce;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Simulation {
	
	
	private BooleanProperty isStart = new SimpleBooleanProperty(false); 
	private BooleanProperty isPause ;
	private MainObject obj;
	private Surface surface;
	private Force aForce;
	private Force fForce;
	
	public Simulation() {
		this.obj = null;
		this.surface = new Surface();
		this.aForce = new AppliedForce(0);
		this.fForce = new FrictionForce(0);
	}
	
	public Simulation(MainObject mainObj, Surface surface, AppliedForce aForce) {
		this.obj = mainObj;
		this.surface = surface;
		this.aForce = aForce;
		this.fForce = new FrictionForce(0, surface, mainObj, aForce);
	}
	
	public MainObject getObj() {
		return obj;
	}
	
	public void setObject(MainObject obj) {
		this.obj = obj;
	}
	
	public Surface getSur() {
		return surface;
	}
	
	public Force getaForce() {
		return aForce;
	}

	public void setaForce(double aForce) {
		this.aForce.setValue(aForce);
	}

	public Force getfForce() {
		return fForce;
	}
	
	public Force getNetForce() {
		return new NetForce(0, aForce, fForce);
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
	
	public void pause() {
		setIsStart(false);
	}
	
	public void cont() {
		setIsStart(true);
	}
	
	public void restart() {
		setIsStart(false);
		aForce.setValue(0);
		fForce.setValue(0);
		setObject(null);
	}
	
	public BooleanProperty isPauseProperty() {
		return isPause;
	}
	
	public boolean getIsPause() {
		return isPause.get();
	}

	public void setIsPause(boolean isPause) {
		this.isPause.set(isPause);;
	}

	public void getObjAcc() {
		getObj().updateAcc(getNetForce());
	}
	
	public HorizontalVector getObjVel() {
		return getObj().velProperty();
	}
	
	public void applyForceInTime(Force force, double t) {
		obj.applyForceInTime(force, t);
		if (obj instanceof Rotatable) {
			try {
				((Rotatable) obj).applyForceInTimeRotate(force, t);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
