---
title: Welcome to COSC 112!
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Welcome to COSC 112!

## Outline

- Introductions
- Lab Section Plan
- Lab 01: Hello, Graphics!

(general course introduction in lecture)

# Introductions

## Your Professor

- [Will Rosenbaum](https://www.willrosenbaum.com)
- Originally from Seattle 
- Undregrad at Reed College in Portland, OR
- PhD in Mathematics from UCLA
- Postdocs at 
    + Tel Aviv University
	+ Max Planck Institute for Informatics (Saarbruecken, Germany)
- Started at Amherst last fall

## My Research

- Theoretical Computer Science
    + Interface between math and CS
- Theory of Distributed Systems
    + What can systems of interacting processors (broadly construed) compute?
- Research Questions
    + How efficiently can a computational task be performed in principle?
	+ What resources (time, memory, communication) are required to perform tasks?
	+ What tasks *cannot* be solved efficiently?
	
## Outside of Work

- Spend most time with family: Alivia, Ione (daughter), Finnegan (dog), Pip & Posy (cats)
- Hobbies: cooking, playing piano, hiking

## Meet your TAs

- Section 1:
    + Quentin Jeyaretnam & Hasham Warrich
- Section 2: 
    + Sarah Park & Naya Burshan
- Evening TA Session (MW, 7:00--9:00 pm)
    + Nico Ardila, Hasham Warrich, Faith Merritt
	
## Meet Each Other!

- Introduce Yourselves
    + Name
	+ Where from
	+ Year
	+ Major and/or interests
	
# Lab Sessions

## Purpose

- Informal discussion (small groups)
- Get questions answered
- Help getting started
- Troubleshooting

## Lab Structure

- Brief orientation
- Questions
- Small group discussion
- Brief recap

# Lab 01: Hello, Graphics!

## Graphical Programming

- A program that produces simple graphics
- Java tools
    + Abstract Window Toolkit (AWT): basic graphical constructs
	+ Tools for window-based applications 
- You'll mostly be looking at `Graphics` class
    + [`Graphics` documentation](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/Graphics.html)

## Example Output

![](/assets/img/2021s-cosc-112/lab01-hello-graphics/dog-grid.png){: width="75%"}


## `HelloGraphics.java`

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
```

## Where to Start

- [Download and Run `HelloGraphics.java`](/assets/java/2021s-cosc-112/lab01-hello-graphics/HelloGraphics.java)
- Demo with IntelliJ
    + Running program
	+ Program structure
	+ Adding a method (drawX)

## Questions?

## Discuss!

- What are you going to draw?
- How?
- What difficulties do you anticipate?
