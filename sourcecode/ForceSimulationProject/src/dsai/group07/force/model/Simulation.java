package dsai.group07.force.model;

import dsai.group07.force.model.object.MainObject;
import dsai.group07.force.model.surface.Surface;
import dsai.group07.force.model.vector.AppliedForce;
import dsai.group07.force.model.vector.Force;
import dsai.group07.force.model.vector.FrictionForce;
import dsai.group07.force.model.vector.HorizontalVector;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This class is used to contain all information about model. It aggregates
 * MainObject, Surface, AppliedForce and AppliedForce and controls all actions
 * relating to these class such as changing MainObject, adjusting static/kinetic
 * coefficient and applying forces on MainObject. It also performs starting,
 * pausing and continuing simulation.
 *
 */
public class Simulation {

	/**
	 * Holds the start property of this Simulation
	 */
	private BooleanProperty isStart = new SimpleBooleanProperty(false);
	/**
	 * Holds the pause property of this Simulation (true means the simulation is
	 * pausing while false means the simulation is continuing)
	 */
	private BooleanProperty isPause = new SimpleBooleanProperty(true);
	// isStart = false --> isPause always true but the opposite is not true.
	/**
	 * Holds the main objects of this simulation (a node in application contains
	 * many MainObject, we always consider the newest one)
	 */
	private ObjectProperty<MainObject> obj = new SimpleObjectProperty<>();
	/**
	 * Holds the system transitional velocity of this Simulation. It equals the
	 * newest MainObject's transitional velocity
	 */
	private ObjectProperty<HorizontalVector> sysVel = new SimpleObjectProperty<>();
	/**
	 * Holds the system transitional acceleration of this Simulation. It equals the
	 * newest MainObject's transitional acceleration
	 */
	private ObjectProperty<HorizontalVector> sysAcc = new SimpleObjectProperty<>();
	/**
	 * Holds the system angular acceleration of this Simulation. It equals the
	 * newest Cylinder's angular acceleration	
	 */
	private DoubleProperty sysAngAcc = new SimpleDoubleProperty(0);
	/**
	 * Holds the system angular velocity of this Simulation. It equals the newest
	 * Cylinder's angular velocity
	 */
	private DoubleProperty sysAngVel = new SimpleDoubleProperty(0);
	/**
	 * Holds the net force of this Simulation
	 */
	private Force netForce = new Force(0);

	/**
	 * Holds the current Surface relating to this Simulation
	 */
	private Surface surface;
	/**
	 * Holds the current AppliedForce relating to this Simulation
	 */
	private Force aForce;
	/**
	 * Holds the current FrictionForce relating to this Simulation
	 */
	private Force fForce;

	/**
	 * Default class constructor
	 */
	public Simulation() {
		this.surface = new Surface();
		this.aForce = new AppliedForce(0);
		this.fForce = new FrictionForce(0);
	}

	/**
	 * Class constructor specifying MainObject, Surface and AppliedForce
	 * 
	 * @param mainObj The current MainObject relating to this Simulation
	 * @param surface The current Surface relating to this Simulation
	 * @param aForce  The current AppliedForce relating to this Simulation
	 */
	public Simulation(MainObject mainObj, Surface surface, AppliedForce aForce) {
		this.obj.set(mainObj);
		this.surface = surface;
		this.aForce = aForce;
		this.fForce = new FrictionForce(0, surface, mainObj, aForce);
		// Updates net force according to current MainOject, Surface and AppliedForce
		updateNetForce();
	}

	/**
	 * Changes the system transitional velocity of this Simulation
	 * 
	 * @param horizontalVector This Simulation's new transitional velocity
	 */
	public void setSysVel(HorizontalVector horizontalVector) {
		this.sysVel.set(horizontalVector);
	}

	/**
	 * Changes the system transitional acceleration of this Simulation
	 * 
	 * @param horizontalVector This Simulation's new transitional acceleration
	 */
	public void setSysAcc(HorizontalVector horizontalVector) {
		this.sysAcc.set(horizontalVector);
	}

	/**
	 * Gets the system transitional velocity property of this Simulation
	 * 
	 * @return This Simualtion's system transitional velocity property
	 */
	public ObjectProperty<HorizontalVector> sysVelProperty() {
		return sysVel;
	}

	/**
	 * Gets the system transitional acceleration property of this Simulation
	 * 
	 * @return This Simulation's system transitional acceleration property
	 */
	public ObjectProperty<HorizontalVector> sysAccProperty() {
		return sysAcc;
	}

	public DoubleProperty getSysAngAcc() {
		return sysAngAcc;
	}

	/**
	 * Changes the system angular acceleration of this Simulation
	 * 
	 * @param sysAngAcc This Simulation's new angular acceleration
	 */
	public void setSysAngAcc(double sysAngAcc) {
		this.sysAngAcc.set(sysAngAcc);
	}

	/**
	 * Gets the system angular velocity of this Simulation
	 * 
	 * @return This Simulation's angular velocity
	 */
	public DoubleProperty getSysAngVel() {
		return sysAngVel;
	}

	/**
	 * Changes the system angular velocity of this Simulation
	 * 
	 * @param sysAngVel This Simulation's new angular velocity
	 */
	public void setSysAngVel(double sysAngVel) {
		this.sysAngVel.set(sysAngVel);
	}

	public ObjectProperty<MainObject> objProperty() {
		return this.obj;
	}

	/**
	 * Changes the MainObject of this Simulation also updates system transitional
	 * acceleration and velocity of according to new object
	 * 
	 * @param obj This Simulation's new MainObject
	 */
	public void setObject(MainObject obj) {
		this.obj.set(obj);
		if (obj == null) {
			this.sysAcc.set(new HorizontalVector(0));
			this.sysVel.set(new HorizontalVector(0));
		} else {
			this.sysAcc.set(obj.accProperty());
			this.sysVel.set(obj.velProperty());
		}

	}

	/**
	 * Gets the newest MainObject of this Simulation
	 * 
	 * @return This Simulation's newest MainObject
	 */
	public MainObject getObj() {
		return this.obj.get();
	}

	/**
	 * Gets the pausing property of this Simulation
	 * 
	 * @return This Simulation's property
	 */
	public BooleanProperty isPauseProperty() {
		return isPause;
	}

	/**
	 * Gets the pausing state of this Simulation
	 * 
	 * @return The pausing state of this Simulation
	 */
	public boolean getIsPause() {
		return isPause.get();
	}

	/**
	 * Changes the pausing state of this Simulation
	 * 
	 * @param isPause This Simulation's new pausing state
	 */
	public void setIsPause(boolean isPause) {
		this.isPause.set(isPause);
	}

	/**
	 * Gets the starting property of this Simulation
	 * 
	 * @return This Simulation's starting property
	 */
	public BooleanProperty isStartProperty() {
		return this.isStart;
	}

	/**
	 * Gets the starting state of this Simulation
	 * 
	 * @return This Simulation's starting state
	 */
	public boolean getIsStart() {
		return this.isStart.get();
	}

	/**
	 * Changes the starting state of this Simulation
	 * 
	 * @param isStart This Simulation's new starting state
	 */
	public void setIsStart(boolean isStart) {
		this.isStart.set(isStart);
	}

	/**
	 * Gets the current Surface relating to this Simulation
	 * 
	 * @return The current Surface relating to this Simulation
	 */
	public Surface getSur() {
		return surface;
	}

	/**
	 * Gets the current AppliedForce relating to this Simulation
	 * 
	 * @return The current AppliedForce relating to this Simulation
	 */
	public Force getaForce() {
		return aForce;
	}

	/**
	 * Sets the current AppliedForce relating to this Simulation
	 * 
	 * @param aForce This Simulation's new AppliedForce
	 */
	public void setaForce(double aForce) {
		this.aForce.setValue(aForce);
	}

	/**
	 * Gets the current FrictionForce relating to this Simulation
	 * 
	 * @return The current FrictionForce relating to this Simulation
	 */
	public Force getfForce() {
		return fForce;
	}

	public Force getNetForce() {
		return netForce;
	}

	/**
	 * Starts application
	 */
	public void start() {
		setIsStart(true);
		setIsPause(false);
	}

	/**
	 * Pauses this Simulation
	 */
	public void pause() {
		setIsPause(true);
	}

	/**
	 * Continues this Simulation
	 */
	public void conti() {
		setIsPause(false);
	}

	/**
	 * Restarts this Simulation
	 */
	public void restart() {
		setIsStart(false);
		setIsPause(true);
		setObject(null);
		aForce.setValue(0);
		fForce.setValue(0);

		// Handles exception when set default value of static and kinetic friction
		// coefficient
		try {
			surface.setStaCoef(Surface.MAX_STA_COEF / 2);
			surface.setKiCoef(Surface.MAX_STA_COEF / 4);
		} catch (Exception e) {
			try {
				surface.setKiCoef(Surface.MAX_STA_COEF / 4);
				surface.setStaCoef(Surface.MAX_STA_COEF / 2);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	/**
	 * Updates MainObject's transitional acceleration according to this Simulation's
	 * net force
	 */
	public void updateObjAcc() {
		getObj().updateAcc(getNetForce());
	}

	/**
	 * Gets the transitional velocity property of this Simualtion's newest object
	 * 
	 * @return The transitional velocity property of this Simualtion's newest object
	 */
	public HorizontalVector getObjVel() {
		return getObj().velProperty();
	}

	/**
	 * Updates this Simulation's netForce according to its AppliedForce and
	 * FrictionForce
	 */
	public void updateNetForce() {
		Force newNerForce = Force.sumTwoForce(aForce, fForce);
		netForce.setValue(newNerForce.getValue());
	}

	/**
	 * Updates all attributes of the MainObject when applying forces on it after a
	 * time interval t
	 * 
	 * @param t The time interval between two stages of this Simulation
	 */
	public void applyForceInTime(double t) {
		obj.get().applyForceInTime(getNetForce(), getfForce(), t);
	}

}
