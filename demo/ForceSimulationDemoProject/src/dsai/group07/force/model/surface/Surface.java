package dsai.group07.force.model.surface;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Surface {
	DoubleProperty staCo = new SimpleDoubleProperty(0);
	DoubleProperty kiCo = new SimpleDoubleProperty(0);
	
	public DoubleProperty getStaCo() {
		return staCo;
	}
	public void setStaCo(double staCo) {
		this.staCo.setValue(staCo);	
		}
	public DoubleProperty getKiCo() {
		return kiCo;
	}
	public void setKiCo(double kiCo) {
		this.kiCo.setValue(kiCo);
	}
	
}
