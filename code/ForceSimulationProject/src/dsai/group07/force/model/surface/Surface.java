package dsai.group07.force.model.surface;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * This class is used to represent surface which contains attributes and methods
 * for adjusting static friction coefficient and kinetic friction coefficient
 *
 */
public class Surface {
	/**
	 * Holds the static friction property of this Surface
	 * Default value = {@link #MAX_STA_COEF} / 2
	 */
	DoubleProperty staCoef = new SimpleDoubleProperty(MAX_STA_COEF / 2);
	/**
	 * Holds the kinetic friction coefficient of this Surface
	 * Default value = {@link #MAX_STA_COEF} / 4
	 */
	DoubleProperty kiCoef = new SimpleDoubleProperty(MAX_STA_COEF / 4);
	/**
	 * Holds the max static coefficient of class Surface
	 */
	public static final double MAX_STA_COEF = 1.0;
	/**
	 * Holds the step of static/kinetic coefficient of class Surface
	 */
	public static final double STEP_COEF = 0.001;

	/**
	 * Default class constructor
	 */
	public Surface() {
	}

	/**
	 * Class constructor specifying static friction coefficient and set kinetic
	 * friction coefficient equals static friction coefficient / 2
	 * 
	 * @param staCoef The static friction coefficient of this Surface
	 * @throws Exception Throw exception if invalid static friction coefficient
	 * @see #setStaCoef(double)
	 */
	public Surface(double staCoef) throws Exception {
		setStaCoef(staCoef);
		setKiCoef(staCoef / 2);
	}

	/**
	 * Class constructor specifying static friction coefficient and kinetic friction
	 * coefficient
	 * 
	 * @param staCoef The static friction coefficient of this Surface
	 * @param kiCoef  The kinetic friction coefficient of this Surface
	 * @throws Exception Throw exception if invalid static / kinetic friction
	 *                   coefficient
	 * @see #setStaCoef(double) and {@link #setStaCoef(double)}
	 */
	public Surface(double staCoef, double kiCoef) throws Exception {
		setStaCoef(staCoef);
		setKiCoef(kiCoef);
	}

	/**
	 * Gets the static friction coefficient property of this Surface
	 * @return The static friction coefficient property of this Surface
	 */
	public DoubleProperty staCoefProperty() {
		return staCoef;
	}

	/**
	 * Gets the static friction coefficient of this Surface
	 * @return The static friction coefficient of this Surface
	 */
	public double getStaCoef() {
		return staCoef.get();
	}

	/**
	 * Gets the kinetic friction coefficient property of this Surface
	 * @return The kinetic friction coefficient property of this Surface
	 */
	public DoubleProperty kiCoefProperty() {
		return kiCoef;
	}

	/**
	 * Gets the kinetic friction coefficient of this Surface
	 * @return the kinetic friction coefficient of this Surface
	 */
	public double getKiCoef() {
		return kiCoef.get();
	}

	/**
	 * Changes the static friction coefficient of this Surface
	 * @param staCoef This Surface's new static friction coefficient
	 * @throws Exception Throw exception if invalid static friction coefficient
	 */
	public void setStaCoef(double staCoef) throws Exception {
		if (staCoef < 0 || staCoef > MAX_STA_COEF) {
			// Set to its default value
			this.staCoef.setValue(MAX_STA_COEF / 2);
			throw new Exception("Static friction coefficient must be >= 0 and <= " + MAX_STA_COEF);
		} else if (staCoef == 0) {
			// Sets both staCoef and kiCoef to 0 if staCoef = 0
			kiCoef.setValue(0);
			this.staCoef.setValue(0);
		} else if (staCoef <= getKiCoef()) {
			this.staCoef.setValue(getKiCoef() + STEP_COEF);
			throw new Exception("Static friction coefficient must be > kinetic friction coefficient: "
					+ String.format("%.3f", getKiCoef()));
		} else {
			this.staCoef.setValue(staCoef);
		}
	}

	/**
	 * Changes the kinetic friction coefficient of this Surface
	 * @param staCoef This Surface's new kinetic friction coefficient
	 * @throws Exception Throw exception if invalid kinetic friction coefficient
	 */
	public void setKiCoef(double kiCoef) throws Exception {
		if (kiCoef < 0 || kiCoef >= MAX_STA_COEF) {
			// Sets to its default value
			this.kiCoef.setValue(MAX_STA_COEF / 4);
			throw new Exception("Kinetic friction coefficient must be >= 0 and < " + MAX_STA_COEF);
		} else if (getStaCoef() <= kiCoef && getStaCoef() != 0) {
			// Handles case when staCoef has already = 0
			this.kiCoef.setValue(Math.max(0, getStaCoef() - STEP_COEF));
			throw new Exception("Kinetic friction coefficient must be < static friction coefficient: "
					+ String.format("%.3f", getStaCoef()));
		} else {
			this.kiCoef.setValue(kiCoef);
		}
	}

}
