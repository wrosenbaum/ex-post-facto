/**
 * Lab 01: HelloGraphics
 * 
 * Description: Describe what your program does. 
 * 
 * Extensions: Describe the extensions you implemented, if any.
 *
 * @author Your Name Here
 *
 ***********************************************************
 * (to be completed by the grader)
 * 
 * Grade (out of 3):
 * 
 * Extension grade: 
 * 
 * Grader Comments: 
 * 
 */
 

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class HelloGraphics extends JPanel{
    public static final int BOX_WIDTH = 1024;
    public static final int BOX_HEIGHT = 768;
    public static final Color MAMMOTH_PURPLE = new Color(63, 31, 105);
    public static final Color SPRING_LEAF = new Color(91, 161, 81);

    public HelloGraphics(){
        this.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
    }
    
    //Your drawing method(s) here
        
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Your code here: feel free to remove what is below
	
        g.setColor(MAMMOTH_PURPLE);
        g.fillRect(0, 0, BOX_WIDTH, BOX_HEIGHT);

	g.setColor(Color.WHITE);
	g.setFont(new Font("Times New Roman", Font.BOLD, 48));
	g.drawString("Protect the herd!", 50, 50);

	g.setColor(SPRING_LEAF);
	g.drawLine(50, 55, 500, 55);

        g.setColor(Color.GREEN);
        g.fillOval(50, 100, 30, 20);

        g.setColor(Color.RED);
        g.fillRect(100, 100, 20, 30);

        g.setColor(Color.BLUE);
        g.drawRoundRect(300, 200, 100, 200, 50, 100);

        g.setColor(Color.ORANGE);
        g.drawArc(200, 100, 200, 300, 90, 140);
    }
    
    public static void main(String args[]){
        JFrame frame = new JFrame("Hello, Graphics!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new HelloGraphics());
        frame.pack();
        frame.setVisible(true);
    }
}
