package dsai.group07.force.model.surface;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Surface {
	DoubleProperty staCo = new SimpleDoubleProperty(0);
	DoubleProperty kiCo = new SimpleDoubleProperty(0);
	
	public DoubleProperty staCoProperty() {
		return this.staCo;
	}
	public void setStaCo(double staCo) {
		this.staCo.setValue(staCo);	
		}
	public double getStaCo() {
		return this.staCo.get();
	}
	public DoubleProperty kiCoProperty() {
		return this.kiCo;
	}
	public void setKiCo(double kiCo) {
		this.kiCo.setValue(kiCo);
	}
	public double getKiCo() {
		return this.kiCo.get();
	}
	
	
}
