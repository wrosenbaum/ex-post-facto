import java.awt.Color;
import java.awt.Graphics;

public class Circle extends AbstractShape {
    // create a circle with a given center (cx, cy) and radius
    public Circle (double cx, double cy, double radius) {
	super(cx - radius, cy - radius, 2 * radius, 2 * radius);
    }

    public void draw (Graphics g) {
	g.setColor(Color.BLACK);
	g.fillOval((int) x, (int) y, (int) width, (int) height);
    }

    public boolean contains (double x, double y) {
	double radius = width / 2;
	double cx = this.x + radius;
	double cy = this.y + radius;

	return ((x - cx) * (x - cx) + (y - cy) * (y - cy) <= radius * radius);
    }
}
