package dsai.group07.vector;

public class Vector {
	protected double x;
	protected double y;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector add(Vector v) {
		this.x = v.x + this.x;
		this.y = v.y + this.y;
		return this;
	}
	
	public String toString() {
		String out = "Vector: " + this.x + " " + this.y; 
		return out;
	}
}
