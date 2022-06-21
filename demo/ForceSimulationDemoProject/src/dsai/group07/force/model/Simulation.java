package dsai.group07.force.model;

import dsai.group07.force.model.object.MainObject;
import dsai.group07.force.model.surface.Surface;
import dsai.group07.force.model.vector.AppliedForce;
import dsai.group07.force.model.vector.Force;
import dsai.group07.force.model.vector.FrictionForce;
import dsai.group07.force.model.vector.HorizontalVector;
import dsai.group07.force.model.vector.NetForce;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Simulation {
	
	
	private BooleanProperty isStart = new SimpleBooleanProperty(false); 
	private BooleanProperty isPause = new SimpleBooleanProperty(true);
	private ObjectProperty<MainObject> obj = new SimpleObjectProperty<>();
	private ObjectProperty<HorizontalVector> sysVel = new SimpleObjectProperty<>();
	private ObjectProperty<HorizontalVector> sysAcc = new SimpleObjectProperty<>();
	private Surface sur;
	private Force aForce;
	private Force fForce;
	
	
	
	
	public void setSysVel(HorizontalVector horizontalVector) {
		this.sysVel.set(horizontalVector);
	}

	public void setSysAcc(HorizontalVector horizontalVector) {
		this.sysAcc.set(horizontalVector);
	}
	
	public ObjectProperty<HorizontalVector> sysVelProperty() {
		return sysVel;
	}

	public ObjectProperty<HorizontalVector> sysAccProperty() {
		return sysAcc;
	}

	public Simulation() {
		this.sur = new Surface();
		this.aForce = new AppliedForce(0);
		this.fForce = new FrictionForce(0);
	}
	
	public Simulation(MainObject mainObj, Surface sur, AppliedForce aForce) {
		this.obj.set(mainObj);
		this.sur = sur;
		this.aForce = aForce;
		this.fForce = new FrictionForce(0, this);
	}
	
	public ObjectProperty<MainObject> objProperty(){
		return this.obj;
	}
	
	public MainObject getObj() {
		return this.obj.get();
	}
	
	public void setObject(MainObject obj) {
		this.obj.set(obj);
		if (obj == null) {
			this.sysAcc.set(new HorizontalVector(0));
			this.sysVel.set(new HorizontalVector(0));
		}
		else {
		this.sysAcc.set(obj.getAcc());
		this.sysVel.set(obj.getVel());
		}
	}
	
	public Surface getSur() {
		return sur;
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

	public void setfForce(double fForce) {
		this.fForce.setValue(fForce);
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
		return getObj().getVel();
	}
	
	public void applyForceInTime(Force force, double t) {
		getObj().applyForceInTime(force, t);
	}
	
}
