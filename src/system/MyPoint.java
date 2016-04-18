package system;

import java.awt.Point;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyPoint extends Point {

	List<Circle> circles;

	public MyPoint(int x, int y) {
		super(x, y);
		circles = new CopyOnWriteArrayList<Circle>();
	}

	public void addCircle(Circle c) {
		if (!circles.contains(c))
			circles.add(c);
		else
			System.out.println("okrag juz przypisany");
	}

	public void sortCircles() {

		
		for (int i = 0; i < circles.size(); i++) {
			for (int j = 1; j < circles.size(); j++) {
				if (MathOperations.getAngle(this, circles.get(j)
						.getCenterPoint()) < MathOperations.getAngle(this,
						circles.get(j - 1).getCenterPoint()))
					Collections.swap(circles, j, j - 1);
			}
		}

		 for (Circle circle : circles) {
		 System.out.println(MathOperations.getAngle(this,
		 circle.getCenterPoint()) + " "+circle.getCenterPoint().x+
		 " "+circle.getCenterPoint().y);
		 }
		 System.out.println("-------------------------");

	}

	public List<Circle> getCircles() {
		return circles;
	}

	public void clearCircles() {
		circles.clear();
		// System.out.print(circles.size()+" ");
	}
}
