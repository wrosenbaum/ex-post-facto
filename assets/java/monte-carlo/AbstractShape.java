/*
 * Class AbstractShape represents a geometric shape. The class stores
 * a location (x, y) corresponding to the upper-left corning of the
 * shape's bounding box, as well as the width and height of the
 * bounding box (all stored as doubles). Provides abstract methods to
 * draw a shape and determine whether a given point is contained in
 * the shape.
 */


import java.awt.Color;
import java.awt.Graphics;

public abstract class AbstractShape {
    // the x and y coordinates of the upper-left corner of the
    // bounding box of the AbstractShape
    protected double x;     
    protected double y;

    // width and height of the bounding box
    protected double width;
    protected double height;

    public AbstractShape (double x, double y, double width, double height) {
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
    }

    // Getters and setters
    public double getX () { return x; }
    public double getY () { return y; }
    public double getWidth () { return width; }
    public double getHeight () { return height; }

    // return the maximum x value of the bounding box
    public double getXMax () { return x + width; }

    // return the maximum y value of the bounding box
    public double getYMax () { return y + height; }

    // draw the shape to a Graphics object g
    public abstract void draw (Graphics g);

    // determine whether a given point (x, y) is contained in the
    // AbstractShape
    public abstract boolean contains (double x, double y);
    
}
