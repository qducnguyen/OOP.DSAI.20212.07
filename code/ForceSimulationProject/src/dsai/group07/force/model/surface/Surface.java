package dsai.group07.force.model.surface;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Surface {

	DoubleProperty staCoef = new SimpleDoubleProperty(MAX_STA_COEF / 2);
	DoubleProperty kiCoef = new SimpleDoubleProperty(MAX_STA_COEF / 4);
	public static final double MAX_STA_COEF = 1.0;
	public static final double STEP_COEF = 0.001;

	public Surface() {
	}

	public Surface(double staCoef) throws Exception {
		setStaCoef(staCoef);
		setKiCoef(staCoef / 2);
	}

	public Surface(double staCoef, double kiCoef) throws Exception {
		setStaCoef(staCoef);
		setKiCoef(kiCoef);
	}

	public DoubleProperty staCoefProperty() {
		return staCoef;
	}

	public double getStaCoef() {
		return staCoef.get();
	}

	public DoubleProperty kiCoefProperty() {
		return kiCoef;
	}

	public double getKiCoef() {
		return kiCoef.get();
	}

	public void setStaCoef(double staCoef) throws Exception {
		if (staCoef < 0) {
			this.staCoef.setValue(0);
			System.out.println("1 Static friction coefficient is setted to " + String.format("%.3f", getStaCoef()));
			throw new Exception("1 Static friction coefficient must be >= 0 and <= " + MAX_STA_COEF);
		} else if (staCoef > MAX_STA_COEF) {
			this.staCoef.setValue(MAX_STA_COEF);
			System.out.println("2 Static friction coefficient is setted to " + String.format("%.3f", getStaCoef()));
			throw new Exception("2 Static friction coefficient must be >= 0 and <= " + MAX_STA_COEF);
		} else if (staCoef == 0) {
			kiCoef.setValue(0);
			this.staCoef.setValue(0);
			System.out.println("3 Kinetic friction coefficient is setted to " + String.format("%.3f", getKiCoef()));
			System.out.println("3 Static friction coefficient is setted to " + String.format("%.3f", getStaCoef()));
		} else if (staCoef <= getKiCoef()) {
			this.staCoef.setValue(getKiCoef() + STEP_COEF);
			System.out.println("4 Static friction coefficient is setted to " + String.format("%.3f", getStaCoef()));
			throw new Exception("4 Static friction coefficient must be > kinetic friction coefficient: "
					+ String.format("%.3f", getKiCoef()));
		} else {
			this.staCoef.setValue(staCoef);
			System.out.println("5 Static friction coefficient is setted to " + String.format("%.3f", getStaCoef()));
		}
	}

	public void setKiCoef(double kiCoef) throws Exception {
		if (kiCoef < 0) {
			this.kiCoef.setValue(0);
			System.out.println("1 Kinetic friction coefficient is setted to " + String.format("%.3f", getKiCoef()));
			throw new Exception("1 Kinetic friction coefficient must be >= 0 and < " + MAX_STA_COEF);
		} else if (kiCoef >= MAX_STA_COEF) {
			this.kiCoef.setValue(getStaCoef() - STEP_COEF);
			System.out.println("2 Kinetic friction coefficient is setted to " + String.format("%.3f", getKiCoef()));
			throw new Exception("2 Kinetic friction coefficient must be >= 0 and < " + MAX_STA_COEF);
		} else if (getStaCoef() <= kiCoef) {
			this.kiCoef.setValue(Math.max(0, getStaCoef() - STEP_COEF));
			System.out.println("3 Kinetic friction coefficient is setted to " + String.format("%.3f", getKiCoef()));
			throw new Exception("3 Kinetic friction coefficient must be < static friction coefficient: "
					+ String.format("%.3f", getStaCoef()));
		} else {
			this.kiCoef.setValue(kiCoef);
			System.out.println("4 Kinetic friction coefficient is setted to " + String.format("%.3f", getKiCoef()));
		}
	}

}
