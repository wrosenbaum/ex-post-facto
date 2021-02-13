---
layout: page
title: "Lab 01: Hello Graphics!"
description: an introduction to graphical programs in Java
---

----------

**DUE:** Friday, February 19, 23:59 [AOE](https://time.is/Anywhere_on_Earth)

----------
### Prerequisites

-  Read [Getting Started with Java](/teaching/2021s-cosc-112/notes/getting-started-java/)
- Read [Anatomy of a Java Program](/teaching/2021s-cosc-112/notes/anatomy-of-java-program/)	
    

### Look at the graphics!

To get started download the [file `HelloGraphics.java`](/assets/java/2021s-cosc-112/lab01-hello-graphics/HelloGraphics.java), or copy and paste the code below into a new file.

<details markdown=1>
<summary>click to view code</summary>

```java

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
    
        //Your code here, if you want to define additional methods.
    
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
```
</details>
<br/>

You should get output that looks something like this:

![](/assets/teaching/2021s-cosc-112/lab01-hello-graphics/hello-graphics.png){: width="100%"} 

### Check out the documentation

Now that we've seen what the program does, it is time to figure out *how*. Look at the code, and reference it against the documentation for the the various objects we've used. In particular, you'll want to look at

- [Color](https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html)
- [Graphics](https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics.html)

since these classes will be especially helpful in your assignment. You can glance at the documentation for the other classes used in `HelloGraphics.java`

- [Dimension](https://docs.oracle.com/javase/8/docs/api/java/awt/Dimension.html)
- [Font](https://docs.oracle.com/javase/8/docs/api/java/awt/Font.html)
- [JPanel](https://docs.oracle.com/javase/8/docs/api/javax/swing/JPanel.html)
- [JFrame](https://docs.oracle.com/javase/8/docs/api/javax/swing/JFrame.html)

but you don't need to worry too much about these for now.

**IYI:** the colors `MAMMOTH_PURPLE` and `SPRING_LEAF` come from Amherst College's [Visual Identity Toolkit](https://www.amherst.edu/news/communications/visual-identity-toolkit). There you can find resources to make things look Amherst-y.

### Your assignment

To complete this lab you should:


1. Draw an object of your choosing. This can be a stick figure, a building, a mammoth, or whatever you want, but it needs to contain at least 5 base shapes (ovals, rectangles, etc).

2. Draw 25 copies of your object in a 5 x 5 grid.

**Note.** Making 25 copies in a grid naively is a painful process of busywork. I suggest defining a method that draws one copy of your object, and using (nested) loops to draw the grid of objects.

**Extension.** To challenge yourself (and get some extra credit), make it so that the images in the grid are not identical, for example by distorting the image or changing its color based on its position in the grid. Below is an example that I put together while procrastinating from doing more productive work. Note that there is a grid of 25 dogs of varying shapes and sizes! 

![](/assets/teaching/2021s-cosc-112/lab01-hello-graphics/dog-grid.png){: width="100%"} 

### What to submit

Please turn in your version of `HelloGraphics.java` to the Moodle submission site. Additionally, complete [this survey/self-assessment](https://forms.gle/RNa2DSy6iYKpPgJ56) for the assignment.

### Grading

