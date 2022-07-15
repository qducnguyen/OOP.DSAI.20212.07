package dsai.group07.force.test.vector;

import dsai.group07.force.model.vector.HorizontalVector;

public class HorizontalVectorTest {
	public static void main(String[] args) {
		HorizontalVector vec = new HorizontalVector(-1.5);
		System.out.println(vec.getValue());
		System.out.println(vec.getDirection());
		System.out.println(vec.getLength());
		vec.setDirection(true);
		System.out.println(vec.getValue());
		vec.setValue(-1.5);
		System.out.println(vec.getDirection());
	}
}
