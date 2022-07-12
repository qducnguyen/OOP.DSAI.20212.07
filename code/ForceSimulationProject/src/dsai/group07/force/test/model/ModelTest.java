package dsai.group07.force.test.model;

import dsai.group07.force.model.Simulation;
import dsai.group07.force.model.object.Cylinder;
import dsai.group07.force.model.surface.Surface;
import dsai.group07.force.model.vector.AppliedForce;

public class ModelTest {

	public static void main(String[] args) throws Exception {
		/*
		 * Simulation simul = new Simulation(new Cube(12), new Surface(0.75, 0.5), new
		 * AppliedForce(90)); simul.applyForceInTime(simul.getNetForce(), 10);
		 * System.out.println("aForce " + simul.getaForce().getValue());
		 * System.out.println("fForce " + simul.getfForce().getValue());
		 * System.out.println("netForce " + simul.getNetForce().getValue());
		 * System.out.println("Pos " + simul.getObj().getPos());
		 * System.out.println("vel " + simul.getObj().velProperty().getValue());
		 * System.out.println("acc " + simul.getObj().accProperty().getValue());
		 * 
		 * 
		 * simul.setaForce(-30); simul.getSur().setKiCoef(0.4);
		 * simul.getObj().setMass(10);
		 * System.out.println(simul.getNetForce().getValue());
		 * simul.applyForceInTime(simul.getNetForce(), 10.0);
		 * System.out.println(simul.getaForce().getValue());
		 * System.out.println(simul.getfForce().getValue());
		 * System.out.println(simul.getNetForce().getValue());
		 * System.out.println(simul.getObj().getPos());
		 * System.out.println(simul.getObj().velProperty().getValue());
		 * System.out.println(simul.getObj().accProperty().getValue());
		 */

		Simulation simul = new Simulation(new Cylinder(12), new Surface(0.75, 0.5), new AppliedForce(10));
		Cylinder mainObj = (Cylinder) simul.getObj();

		simul.applyForceInTime(10);

		System.out.println("Kg " + mainObj.getMass());
		System.out.println("aForce " + simul.getaForce().getValue());
		System.out.println("fForce " + simul.getfForce().getValue());
		System.out.println("netForce " + simul.getNetForce().getValue());

		System.out.println("acc " + simul.getObj().accProperty().getValue());
		System.out.println("vel " + simul.getObj().velProperty().getValue());
		System.out.println("Pos " + simul.getObj().getPos());

		System.out.println("AngAcc " + mainObj.getAngAcc());
		System.out.println("AngVel " + mainObj.getAngVel());
		System.out.println("Angle " + mainObj.getAngle());

		System.out.println(Math.signum(-0));
	}

}
