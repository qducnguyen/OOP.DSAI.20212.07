package model.surface;

import javafx.beans.property.SimpleDoubleProperty;

public class Surface {

	private SimpleDoubleProperty staticCoefProperty = new SimpleDoubleProperty();
	private SimpleDoubleProperty kineticCoefProperty =  new SimpleDoubleProperty();
	
	public Surface() {}
	
	public Surface(double staticCoefValue) throws ArithmeticException {
		setStaticCoefValue(staticCoefValue);
		setKineticCoefValue(staticCoefValue / 2);
	}
	
	public Surface(double staticCoefValue, double kineticCoefValue) throws ArithmeticException {
		setStaticCoefValue(staticCoefValue);
		setKineticCoefValue(kineticCoefValue);
	}
	
	public double getStaticCoefValue() {
		return staticCoefProperty.getValue();
	}
	
	public double getKineticCoefValue() {
		return kineticCoefProperty.getValue();
	}
	
	public SimpleDoubleProperty getStaticCoefProperty() {
		return staticCoefProperty;
	}
	
	public SimpleDoubleProperty getKineticCoefProperty() {
		return kineticCoefProperty;
	}
	
	public void setStaticCoefValue(double staticCoefValue) throws ArithmeticException {
		if (staticCoefValue < 0) {
            staticCoefProperty.setValue(0);
			throw new ArithmeticException("Static friction coefficient must be >= 0 and <= 1");
		} else if (staticCoefValue > 1) {
            staticCoefProperty.setValue(1);
			throw new ArithmeticException("Static friction coefficient must be >= 0 and <= 1");
        } else if (staticCoefValue == 0) {
			kineticCoefProperty.setValue(0);
			staticCoefProperty.setValue(0);
			System.out.println("Kinetic friction coefficient is setted to " + String.format("%.3f", getKineticCoefValue()));
			System.out.println("Static friction coefficient is setted to " + String.format("%.3f", getStaticCoefValue()));
		} else if (staticCoefValue <= getKineticCoefValue()) {
            staticCoefProperty.setValue(Math.min(getKineticCoefValue() + 0.001, 1));
			throw new ArithmeticException("Static friction coefficient must be > kinetic friction coefficient: " + String.format("%.3f", getKineticCoefValue()));
		} else {
			staticCoefProperty.setValue(staticCoefValue);
			System.out.println("Static friction coefficient is setted to " + String.format("%.3f", getStaticCoefValue()));
		}
	}
	
	public void setKineticCoefValue(double kineticCoefValue) throws ArithmeticException {
		if (kineticCoefValue < 0) {
            kineticCoefProperty.setValue(0);
			throw new ArithmeticException("Kinetic friction coefficient must be >= 0 and <= 1");
		} else if (kineticCoefValue > 1) {
            kineticCoefProperty.setValue(1);
            throw new ArithmeticException("Static friction coefficient must be >= 0 and <= 1");
        } else if (kineticCoefValue == 0) {
			kineticCoefProperty.setValue(0);
			System.out.println("Kinetic friction coefficient is setted to " + String.format("%.3f", getKineticCoefValue()));
		} else if (getStaticCoefValue() <= kineticCoefValue) {
            kineticCoefProperty.setValue(getStaticCoefValue() - 0.001);
			throw new ArithmeticException("Kinetic friction coefficient must be < static friction coefficient: " + String.format("%.3f", getStaticCoefValue()));
		} else {
			kineticCoefProperty.setValue(kineticCoefValue);
			System.out.println("Kinetic friction coefficient is setted to " + String.format("%.3f", getKineticCoefValue()));
		}
	}
	
}
