---
layout: page
title: The Anatomy of a Java Program
description: a guide to your first graphical program
---



These notes will walk you through the basics of our first graphical Java program, and review the structure of all Java programs. To get started, download `Typesetter.java`. Open the file and follow along!

https://www.dropbox.com/s/zxxgogi51zostq3/Typesetter.java?dl=0

----------

`Typesetter.java` begins with the following lines:

```java
    import java.awt.Color;
    import java.awt.Font;
    import java.awt.Graphics;
    import java.awt.Dimension;
    import javax.swing.JPanel;
    import javax.swing.JFrame;
```

Each of these statements tells the Java compiler the name of a resource our program will use (and where to find it).

- awt stands for "Abstract Window Toolkit." This package contains a bunch of tools for all things graphical in Java. See [the documentation](https://docs.oracle.com/javase/8/docs/api/java/awt/package-summary.html#package.description) for a full description.
- swing is the name of a Java package for GUIs (graphical user interfaces). Read more in [the swing documentation](https://docs.oracle.com/javase/8/docs/api/javax/swing/package-summary.html).

----------

The `import` statements are necessary for Java to compile the program...
...but they also help you, the programmer.


- they tell you where to look to find documentation on the resources used
- `import java.awt.Color` tells you to familiarize yourself with the `Color` class defined in the `awt` package
- see the [Color documentation here](https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html)
- now... find the documentation for the other classes this program uses!

----------

The next lines define a multi-line comment, explaining what the program/Class does:

```java
    /*
     *  The Typesetter class prints a nicely-formatted message to the
     *  screen. I don't really have much more to say about it, but I
     *  wanted to include a long comment here.
     */
```

----------

The next lines define the name of the class implemented in this program:


    public class Typesetter extends JPanel {
    
        ...
    
    }
- `public class Typesetter` tells the compiler that we are defining a new class (object) called a `Typesetter`
- don't worry about `extends JPanel` for now... we will discuss what `extends` means later
- note that `public class <ClassName>` must *always* be in a file called `<ClassName>.java`

----------

Inside the `Typesetter` class, we define some constant values that will determine the behavior of our object:

```java
        public static final int BOX_WIDTH = 1024;
        public static final int BOX_HEIGHT = 768;
        public static final int FONT_SIZE = 48;
        public static final Color MAMMOTH_PURPLE = new Color(63, 31, 105);
    
        private String message;
```

Don't worry about the keywords `public static final` and `private` for now. But notice the pattern:

```
    <modifier(s)> <type> <name> (= <value>);
```

----------

Next up, we see

```java
        public Typesetter(){
            this.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
    
            // modify this line to change the message
            this.message = "Protect the Herd!";
        }
```

This defines a *constructor* for the `Typesetter` class.

- the constructor describes how to create a new `Typesetter`
- this constructor sets the size of the window, and sets the message to be printed
- don't worry about keyword `this` for now
- we'll talk about constructors more later

----------

Next, our code defines a *method* (or *function*) called `printMessage`:

```java
        // modify this method to change how the message is printed
        private void printMessage(Graphics g) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Times New Roman", Font.BOLD, FONT_SIZE));
    
            g.drawString(message, 50, 50);
        }
```

Recall the pattern for defining a method:

```
    <modifier(s)> <return type> <methodName> (<input type> <variable name> ...) {
        ...
}
```

----------

Next we see:

```java
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
    
            // make the background
            g.setColor(MAMMOTH_PURPLE);
            g.fillRect(0, 0, BOX_WIDTH, BOX_HEIGHT);
    
            printMessage(g);
        }
```

The `@Override` directive tells us that we are re-defining a method already defined (in this case, in the class `JPanel`). More on that later...

- the `paintComponent` method in a `JPanel` is what will actually draw stuff on the screen
- we are drawing a `MAMMOTH_PURPLE` box
- then calling `printMessage(g)`, which prints our message

----------

Finally, we see:

```java
        public static void main(String args[]){
            JFrame frame = new JFrame("Typesetter");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new Typesetter());
            frame.pack();
            frame.setVisible(true);
        }
```

Every `public class ClassName` must contain

```java
    public static void main(String args[]) {...}
```

This is the code that actually runs when we execute `java ClassName`.

----------

See the [JFrame documentation](https://docs.oracle.com/javase/8/docs/api/javax/swing/JFrame.html) if you want to understand what all of these statements mean. (No need to understand it all now!)

----------

Vocabulary to remember:

- `import`
- `class`
- method
- argument (to a method)
- return type
- `main`
----------

Note on `Graphics` coordinates:

- units are pixels (type `int`)
- origin `(0, 0)` is the *top left corner*
- x-coordinates increase *to the right*
- y-coordinates increase *downward*

----------

Modify the code to print the message multiple times!
