package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.text.html.HTMLDocument.HTMLReader.CharacterAction;

import system.Circle;
import system.Line;
import system.MathOperations;
import system.MyPoint;

public class MyPanel extends JPanel {

	private final int X = 800;
	private final int Y = 500;
	
	public static int threadNumber = 3;

	private ArrayList<MyPoint> points;
	private ArrayList<MyPoint> edgePoints;
	private ArrayList<Circle> circles;
	private ArrayList<Line> edgeLines;
	private boolean ifDrawCircles = true;
	private boolean ifFillWithColors = true;
	private boolean ifColorLines = true;
	private boolean ifPhoto = true;
	/** Kolekcja wszystkich kolorów reprezentuj¹cych punkty. */
	public static ArrayList<Color> colors;
	/** Liczba wszytkich kolorów reprezentuj¹cych punkty. */
	public static int colorCounter = 0;

	public MyPanel() {
		setBorder(BorderFactory.createLineBorder(Color.PINK));
		edgePoints = new ArrayList<MyPoint>();
		points = new ArrayList<MyPoint>();
		circles = new ArrayList<Circle>();
		edgeLines = new ArrayList<Line>();
		colors = new ArrayList<Color>();

		// dodanie punktu przez klikniecie:
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {

				addPoint(new MyPoint(e.getX(), e.getY()));
				calcCircles();
				findEdgePoints();
				repaint();
			}
		});
	}

	protected void findEdgePoints() {
		edgePoints.clear();
		edgeLines.clear();
		for (MyPoint point : points) {
			if (point.getCircles().size() <= 3)
				edgePoints.add(point);

		}
	}

	protected void calcCircles() {

		// wyczyszczenie kolekcji okrêgów z punktów:
		for (MyPoint point : points) {
			point.clearCircles();
		}

		circles.clear();
		
		//podzia³ na w¹tki:
		int delta = points.size()/threadNumber;
		Thread[] threads = new Thread[threadNumber];
		
		for (int ID = 0; ID < threadNumber; ID++) {
			threads[ID] = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		for (int i = 0; i < points.size(); i++) {
			for (int j = i + 1; j < points.size(); j++)
				for (int k = j + 1; k < points.size(); k++) {

					Circle circle = new Circle(points.get(i), points.get(j),
							points.get(k));

					addCircle(circle);

					for (int ii = 0; ii < points.size(); ii++) {
						if (ii == i || ii == j || ii == k)
							continue;
						if (MathOperations.inCircle(points.get(ii), circle)) {
							for (MyPoint po : circle.points) {
								po.getCircles().remove(circle);
							}
							circles.remove(circle);
						}
					}

				}
		}

		System.out.println(circles.size() + " okregow");
	}

	public Dimension getPreferredSize() {
		return new Dimension(250, 200);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, X, Y);

		//rysowanie zdjêcia:
		if (ifPhoto) {
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File(
						"zad1.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.drawImage(img, 0, 0, X, Y, Color.BLACK, null);
		}
		
		if (ifFillWithColors)
			drawField(g);
		

		// wyczyszczenie z NAN:
		for (MyPoint point : points) {
			for (Circle circle : point.getCircles()) {
				if (Double.isNaN(circle.getRadius())) {
					point.getCircles().remove(circle);
					System.out.println("NaN znaleziony");
				}
			}
		}

		// rysowanie okr¹gów:
		if (ifDrawCircles)
			for (Circle circle : circles) {
				drawCircle(circle, g);
			}

		// rysowanie punktów nie krawêdziowych:
		for (MyPoint point : points) {
			drawPoint(point, g);
		}

		// rysowanie linii dla niegranicznych:
		g.setColor(Color.WHITE);
		for (int p = 0; p < points.size() - 1; p++) {
			// // je¿eli jest punktem krawêdziowym to pomiñ:
			// if (edgePoints.contains(points.get(p)))
			// continue;
			MyPoint point = points.get(p);
			if (ifColorLines)
				g.setColor(colors.get(p));
			point.sortCircles();
			for (int i = 0; i < point.getCircles().size() - 1; i++) {
				g.drawLine(point.getCircles().get(i).getCenterPoint().x, point
						.getCircles().get(i).getCenterPoint().y, point
						.getCircles().get(i + 1).getCenterPoint().x, point
						.getCircles().get(i + 1).getCenterPoint().y);
			}

			if (point.getCircles().size() >= 2)
				g.drawLine(point.getCircles().get(0).getCenterPoint().x, point
						.getCircles().get(0).getCenterPoint().y, point
						.getCircles().get(point.getCircles().size() - 1)
						.getCenterPoint().x,
						point.getCircles().get(point.getCircles().size() - 1)
								.getCenterPoint().y);

		}

		// rysowanie linii dla granicznych:
		for (MyPoint point1 : edgePoints) {
			for (MyPoint point2 : edgePoints) {
				if (point1 == point2)
					continue;
				for (Circle circle1 : point1.getCircles()) {
					for (Circle circle2 : point2.getCircles()) {
						if (circle1 == circle2)
							;
						// edgeLines.add(new Line(circle1.getCenterPoint(),
						// MathOperations.middlePoint(point1, point2)));
					}
				}
			}

		}

		for (Line line : edgeLines) {
			g.setColor(Color.WHITE);
			g.drawLine(line.A.x, line.A.y, line.B.x, line.B.y);
			System.out.println("rysowanie linii granicznej");
		}

	}

	private void drawField(Graphics g) {
		if (points.size() == 0)
			return;
		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {
				int winner = 0;
				double winnerDistance = 999999;
				MyPoint badanypunkt = new MyPoint(x, y);

				for (int p = 0; p < points.size(); p++) {

					if (MathOperations.abstand(points.get(p), badanypunkt) <= winnerDistance) {
						winner = p;
						winnerDistance = MathOperations.abstand(points.get(p),
								badanypunkt);
					}

				}
				System.out.print(winner);
				g.setColor(colors.get(winner));
				g.fillRect(x, y, 1, 1);
			}

		}
	}

	void calc() {

	}

	private void drawPoint(MyPoint point, Graphics g) {
		int width = 10;
		g.setColor(Color.BLACK);
		g.fillOval(point.x - width / 2, point.y - width / 2, width, width);
		width = 8;
		g.setColor(Color.WHITE);
		g.fillOval(point.x - width / 2, point.y - width / 2, width, width);
		g.drawString(Integer.toString(point.getCircles().size()), point.x + 4,
				point.y);

	}

	private void drawCircle(Circle circle, Graphics g) {
		if (circles.size() == 0)
			return;
		g.setColor(Color.DARK_GRAY);
		g.drawOval(circle.getCenterPoint().x - (int) circle.getRadius(),
				circle.getCenterPoint().y - (int) circle.getRadius(),
				(int) circle.getRadius() * 2, (int) circle.getRadius() * 2);

		g.setColor(Color.GREEN);
		g.fillOval(circle.getCenterPoint().x - 2,
				circle.getCenterPoint().y - 2, 4, 4);
	}

	public void addPoint(MyPoint p) {
		points.add(p);
		final Random rand = new Random();
		colorCounter++;
		colors.add(new Color(rand.nextInt(255), rand.nextInt(255), rand
				.nextInt(255)));
		repaint();
	}

	public void addCircle(Circle c) {
		circles.add(c);
		repaint();
	}

	public void setIfDrawCircles(boolean ifDrawCircles) {
		this.ifDrawCircles = ifDrawCircles;
		repaint();
	}

	public void setIfFillWithColors(boolean ifFillWithColors) {
		this.ifFillWithColors = ifFillWithColors;
		repaint();
	}

	public void setIfColorLines(boolean ifColorLines) {
		this.ifColorLines = ifColorLines;
		repaint();
	}
	
	public void setIfPhoto(boolean ifPhoto) {
		this.ifPhoto = ifPhoto;
		repaint();
	}
	
	

	public void clear() {
		edgeLines.clear();
		edgePoints.clear();
		points.clear();
		circles.clear();
		colors.clear();

		addPoint(new MyPoint(-800, -800));
		addPoint(new MyPoint(-700, 1000));
		addPoint(new MyPoint(1800, -800));
		addPoint(new MyPoint(1700, 1000));

		repaint();
	}

}
