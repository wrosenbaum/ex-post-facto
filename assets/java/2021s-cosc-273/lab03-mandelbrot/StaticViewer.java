import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class StaticViewer extends JPanel{
    public static final int BOX_WIDTH = 512;
    public static final int BOX_HEIGHT = 512;
    
    private double[] bounds = {-2.0, -1.5, 1.0, 1.5};
    private MandelbrotFrame mFrame;
    private Color[][] bitmap = new Color[BOX_WIDTH][BOX_HEIGHT];

    public StaticViewer(){
        this.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
	mFrame = new MandelbrotFrame(bounds, BOX_WIDTH, BOX_HEIGHT);
	bitmap = mFrame.getBitmap();
    }

    // modify this method if you want to animate things!
    public void run () {
	mFrame.setBounds(bounds);
	bitmap = mFrame.getBitmap();
	this.repaint();	    
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Your code here: feel free to remove what is below
	for (int i = 0; i < bitmap.length; ++i) {
	    for (int j = 0; j < bitmap[i].length; ++j) {
		g.setColor(bitmap[i][j]);
		g.fillRect(j, i, 1, 1);
	    }
	}
	
    }
    
    public static void main(String args[]){

        JFrame frame = new JFrame("Mandelbrot Viewer!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	StaticViewer curInstance = new StaticViewer();
        frame.setContentPane(curInstance);
        frame.pack();
        frame.setVisible(true);
	curInstance.run();
    }
}
