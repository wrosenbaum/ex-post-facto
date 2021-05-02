/*
 * Class Blob represents a compound shape consisting of one or more
 * AbstractShapes. Provides a method to estimate the area of the Blob
 * using Monte Carlo estimation.
 */

import java.util.ArrayList;
import java.util.Random;

public class Blob {
    // an ArrayList of the constituent shapes in the blob
    private ArrayList<AbstractShape> shapes;

    // create an empty Blob (without any shapes)
    public Blob () {
	shapes = new ArrayList<AbstractShape>();
    }

    // create a Blob from an ArrayList of shapes
    public Blob (ArrayList<AbstractShape> shapes) {
	this.shapes = shapes;
    }

    // add a new AbstractShape s to the blob
    public void add (AbstractShape s) {
	shapes.add(s);
    }

    // Find the minimum x value of any shape in this Blob.
    public double getXMin () {
	double xMin = Double.MAX_VALUE;

	// COMPLETE ME
	
	return xMin;
    }

    // Find the maximum x value of any shape in this Blob.
    public double getXMax () {
	double xMax = Double.MIN_VALUE;

	// COMPLETE ME
	
	return xMax;
    }

    // Find the minimum y value of any shape in this Blob.
    public double getYMin () {
	double yMin = Double.MAX_VALUE;

	// COMPLETE ME

	return yMin;
    }

    // Find the maximum y value of any shape in this Blob.
    public double getYMax () {
	double yMax = Double.MIN_VALUE;

	// COMPLETE ME

	return yMax;
    }

    /*
     * Method: conatins. Determine whether or not a given point (x, y)
     * is contained in this Blob. Note that a point is contained in
     * the Blob precisely when it is contained in one of the Blob's
     * constituent shapes.
     */
    public boolean contains (double x, double y) {

	// COMPLETE ME
	
	return false;
    }
    
    /*
     * Method: estimateArea. Estimate the area of
     * this Blob using a Monte Carlo estimation. The parameter nPoints
     * is the number of random trial points that should be used to
     * compute the estimate.
     */
    public double estimateArea (int nPoints) {

	// COMPLETE ME

	return 0.0;
    }
}
