package system;

import java.awt.Point;

public class MathOperations {

	public static Point middlePoint(Point p1, Point p2) {
		return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
	}

	public static double abstand(Point p1, Point p2) {
		return  Math.sqrt( (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y)
				* (p2.y - p1.y) );
	}

	public static boolean inCircle(Point p, Circle c) {
		return (((p.x - c.getCenterPoint().x) * (p.x - c.getCenterPoint().x))
				+ ((p.y - c.getCenterPoint().y) * (p.y - c.getCenterPoint().y))) < (c
				.getRadius() * c.getRadius());
	}
	
	public static float getAngle(Point a, Point b) {
	    return (float) Math.toDegrees(Math.atan2(a.x - b.x, a.y - b.y));
	}
}
