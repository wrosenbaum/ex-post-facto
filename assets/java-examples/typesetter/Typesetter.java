import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;

/*
 *  The Typesetter class prints a nicely-formatted message to the
 *  screen. I don't really have much more to say about it, but I
 *  wanted to include a long comment here.
 */


public class Typesetter extends JPanel {
    
    public static final int BOX_WIDTH = 1024;
    public static final int BOX_HEIGHT = 768;
    public static final int FONT_SIZE = 48;
    public static final Color MAMMOTH_PURPLE = new Color(63, 31, 105);

    private String message;
    
    public Typesetter(){
        this.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));

	// modify this line to change the message
	this.message = "Protect the Herd!";
    }

    // modify this method to change how the message is printed
    private void printMessage(Graphics g) {
	g.setColor(Color.WHITE);
	g.setFont(new Font("Times New Roman", Font.BOLD, FONT_SIZE));

	for (int i = 0; i < 20; i++) {
	    g.drawString(message, 50, 50 + i * 60);
	}
    }
        
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

	// make the background
        g.setColor(MAMMOTH_PURPLE);
        g.fillRect(0, 0, BOX_WIDTH, BOX_HEIGHT);

	printMessage(g);
    }
    
    public static void main(String args[]){
        JFrame frame = new JFrame("Typesetter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new Typesetter());
        frame.pack();
        frame.setVisible(true);
    }
}
