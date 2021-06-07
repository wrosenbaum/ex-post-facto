/*
 * Class Blob represents a compound shape consisting of one or more
 * AbstractShapes. Provides a method to estimate the area of the Blob
 * using Monte Carlo estimation.
 *
 * Grade: 1/3
 * Comments: I would suggest making sure to iterate each shape in the shapes array list for each method.
 * For instance, for finding the minimum value, you will have to compare each value of each shape in
 * the array list to complete the min method. 
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
        // getting the xMin coordinate value
        double xMin = shapes.get(0).getX();
	
	return xMin;
    }

    // Find the maximum x value of any shape in this Blob.
    public double getXMax () {
        // getting the xMax coordinate value
        double xMax = shapes.get(0).getXMax();



	
	return xMax;
    }

    // Find the minimum y value of any shape in this Blob.
    public double getYMin () {
        // getting the yMin coordinate value
        double yMin = shapes.get(0).getY();





	return yMin;
    }

    // Find the maximum y value of any shape in this Blob.
    public double getYMax () {
        // getting the yMax coordinate value
        double yMax = shapes.get(0).getYMax();

	return yMax;
    }

    /*
     * Method: conatins. Determine whether or not a given point (x, y)
     * is contained in this Blob. Note that a point is contained in
     * the Blob precisely when it is contained in one of the Blob's
     * constituent shapes.
     */
    public boolean contains (double x, double y) {

        // Checking whether the given random point in the first index
        // from the array list is hitting the blob or not
        return shapes.get(0).contains(x,y); //returns false if outside, true if inside blob

    }
    
    /*
     * Method: estimateArea. Estimate the area of
     * this Blob using a Monte Carlo estimation. The parameter nPoints
     * is the number of random trial points that should be used to
     * compute the estimate.
     */
    public double estimateArea (int nPoints) {

        //declaring variable to hold the number of points which hit inside the blob
        int inside = 0;
        //declaring a class to simulate the random points which may or may not hit the blob
        Random rand = new Random();

        //loops through the given number of points
        for (int i = 0; i < nPoints; i++) {
            //getting the random x and y point values
            double x = rand.nextDouble();
            double y = rand.nextDouble();
            //if these points are inside the blob, increment the 'inside' variable
            if (contains(x,y)) {
                inside ++;
            }
        }

        //As per the Monte Carlo Formula
        double fraction = inside / (double)nPoints;
        double boxArea = shapes.get(0).getWidth() * shapes.get(0).getHeight();
        double blobArea = fraction * boxArea;


	return blobArea;
    }
}
