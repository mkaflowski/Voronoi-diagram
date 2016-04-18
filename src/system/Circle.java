package system;
import java.awt.Point;
import java.util.ArrayList;

public class Circle {

    private double radius;
    private MyPoint center_point;
    public ArrayList<MyPoint> points; 

    public Circle() {
    	points = new ArrayList<MyPoint>();
        center_point = new MyPoint(0, 0);
        radius = 1;
    }

    public Circle(int x, int y, int r) {
    	points = new ArrayList<MyPoint>();
        center_point = new MyPoint(x, y);
        radius = r;
    }
    
    public Circle(MyPoint A, MyPoint B, MyPoint C) {
    	points = new ArrayList<MyPoint>();
        center_point = circleCenter(A, B, C);
        radius = MathOperations.abstand(center_point, A);
        points.add(A);
        points.add(B);
        points.add(C);
        
        A.addCircle(this);
        B.addCircle(this);
        C.addCircle(this);
    }

    public double getRadius() {
        return radius;
    }
    
    public Point getCenterPoint() {
		return center_point;
	}

    public double getArea() {
        return Math.PI * radius * radius;
    }

    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
    
    private MyPoint circleCenter(MyPoint A, MyPoint B, MyPoint C) {

        float yDelta_a = B.y - A.y;
        float xDelta_a = B.x - A.x;
        float yDelta_b = C.y - B.y;
        float xDelta_b = C.x - B.x;
        MyPoint center = new MyPoint(0,0);

        float aSlope = yDelta_a/xDelta_a;
        float bSlope = yDelta_b/xDelta_b;  
        center.x = (int) ((aSlope*bSlope*(A.y - C.y) + bSlope*(A.x + B.x)
            - aSlope*(B.x+C.x) )/(2* (bSlope-aSlope) ));
        center.y = (int) (-1*(center.x - (A.x+B.x)/2)/aSlope +  (A.y+B.y)/2);

        return center;
      }


	public boolean hasPoint(Point point) {
		for (Point p : points) {
			if(p==point)
				return true;
		}
		return false;
	}
}