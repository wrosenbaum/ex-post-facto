import java.awt.Color;
import java.util.concurrent.*;

public class MandelbrotFrame {
    // bounds of the frame
    private double xMin;
    private double yMin;
    private double xMax;
    private double yMax;

    // width and height of the frame in pixels
    private int width;
    private int height;

    // height x width array of escape values
    private float[][] esc;

    public MandelbrotFrame (double[] bounds, int width, int height) {
	xMin = bounds[0];
	yMin = bounds[1];
	xMax = bounds[2];
	yMax = bounds[3];

	this.width = width;
	this.height = height;

	esc = new float[height][width];
    }

    // set the bounds of the frame
    public void setBounds (double[] bounds) {
	xMin = bounds[0];
	yMin = bounds[1];
	xMax = bounds[2];
	yMax = bounds[3];	
    }

    public void updateEscapeTimes() {
	// implement this
    }

    private Color colorMap (float val) {
	// replace this with a more intersting color map
	if (val == 0.0)
	    return Color.BLACK;

	return Color.WHITE;	
    }


    /*
     * return a 2d array of Colors depicting the Mandelbrot set
     */
    public Color[][] getBitmap () {
	updateEscapeTimes();
	Color[][] bitmap = new Color[height][width];

	for (int row = 0; row < height; ++row) {
	    for (int col = 0; col < width; ++col) {
		bitmap[row][col] = colorMap(esc[row][col]);
	    }
	}

	return bitmap;
    }
}
