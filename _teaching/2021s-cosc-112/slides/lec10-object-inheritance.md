---
title: "Lecture 10: Object Inheritance"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 10: Object Inheritance

## Overview

1. Object Inheritance
2. Abstract Classes
3. Activity: Abstract Shapes

## Previously

- Used `class`es to represent types of objects
- *Instances* interacted, but no relationships between object *types* 

## Starting today

- Inheritance—relationships between object types

## A simple class

```java
    public class Person {
      private String name;           // an individual's name
	  
      public Person (String name) {
        this.name = name; 
      }
	  
      public String getName() {return name;}
	  
      public void sayGreeting () {System.out.println(name + ": Hello there!");}
    }
```

## A slightly less simple class

```java
    public class Student {
      private String name;           // an individual's name
      private String institution;

      public Student (String name, String institution) {
        this.name = name; 
        this.institution = institution;
      }
	  
      public String getName() {return name;}
	  
      public void sayGreeting () {System.out.println(name + ": What's Up?");}
    }
```

## Redundant code!

```java
    public class Student {
      private String name;                               // same as Person
      private String institution;
	  
      public Student (String name, String institution) {
        this.name = name;                                // same as Person
        this.institution = institution;
      }
	  
      public String getName() {return name;}             // same as Person
	  
      public void sayGreeting () {System.out.println(name + ": What's Up?");}
    }
```

## Question

Why should we make a whole new class just to make minor modifications to the `Person` class?

- Add `institution` field
- Change constructor arguments
- Change greeting


## We shouldn’t!

## Subclasses to the rescue

Given `Person`, we can make a **subclass** `Student` that

1. adds functionality (stores institution)
2. modifies functionality (changes greeting)

## Keyword `extends`

Making `Student` a subclass:

```java
public class Student extends Person {
    private String institution;
      
    public Student (String name, String institution) {
        super(name); // call the constructor for the superclass
        this.institution = institution;
    }
	  
    @Override
    public void sayGreeting () {System.out.println(name + ": What's up?");}
}
```

## But there’s an error!

```text
    Student.java:10: error: name has private access in Person
        public void sayGreeting () {System.out.println(name + ": What's up?");}
                                                       ^
    1 error
```

## The reason


- `private` variables and methods cannot be called from subclasses


## Two fixes


- use the getter method `getName()`
- change `name` to `protected` 
    - some consider risky: all classes in same directory can access `protected` methods/variables

## Using the subclass

```java
    Student alice = new Student("Alice", "Amherst College");
    Person bob = new Person("Bob");
    
    alice.sayGreeting();    // prints "Alice: What's up?"
    bob.sayGreeting();      // prints "Bob: Hello there!"
```

# Abstracting Away the Details

## Bouncing Disks

```java
public class BouncingDisk {
    public static final int DEFAULT_RADIUS = 25;
    
    private Pair curPosition;      // position of the center
    private Pair curVelocity;      // disk velocity (px / sec)
    private Pair nextPosition;     // position in next frame
    private Pair nextVelocity;     // velocity in next frame    
    private double radius;
    private Color color;
    private World world;           // the world to which the disk belongs
    
    public BouncingDisk(World world, double radius, Color color, Pair position, Pair velocity) {
	this.world = world;
	this.radius = radius;
	this.color = color;
	curPosition = new Pair(position);
	curVelocity = new Pair(velocity);
	nextPosition = new Pair(position);
	nextVelocity = new Pair(velocity);
    }

    public void update (double time) {
	Pair delta = curVelocity.times(time);
        nextPosition.set(curPosition.plus(delta));
        bounce();
    }

    public void advance () {
	curPosition.set(nextPosition);
	curVelocity.set(nextVelocity);
    }

    public void setPosition(Pair p){
        curPosition.set(p);
    }

    public void setVelocity(Pair v){
        curVelocity.set(v);
    }

    public void draw(Graphics g) {
	g.setColor(color);
	g.fillOval( (int) (curPosition.getX() - radius),
		    (int) (curPosition.getY() - radius),
		    (int) (2 * radius), (int) (2 * radius));

    }

    private void bounce() {
        // more code here
    }
}
```

## Questions

1. How much of this code is specific to drawing a **disk**?
2. What would need to change to draw another shape?

## Bouncing Shapes

Modifications:

1. Store `width` and `height` (instead of radius)
2. Make `position`, `velocity`, etc `protected` so subclasses can see these fields
3. Declare **but don’t implement** the `draw(Graphics g)`  method
    - need to make class and method `abstract`

**Note.** We cannot make instances of of abstract  classes, but we can define subclasses that are not `abstract`.

## Abstract Bouncing Shapes

```java
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public abstract class BouncingShape {
    public static final int DEFAULT_WIDTH = 25;
    public static final int DEFAULT_HEIGHT = 25;    
    
    protected Pair curPosition;      // position of the center
    protected Pair curVelocity;      // disk velocity (px / sec)
    protected Pair nextPosition;     // position in next frame
    protected Pair nextVelocity;     // velocity in next frame    
    protected double width;
    protected double height;
    protected Color color;
    protected World world;           // the world to which the disk belongs
    
    public BouncingShape(World world, double width, double height, Color color, Pair position, Pair velocity) {
	this.world = world;
	this.width = width;
	this.height = height;
	this.color = color;
	curPosition = new Pair(position);
	curVelocity = new Pair(velocity);
	nextPosition = new Pair(position);
	nextVelocity = new Pair(velocity);
    }

    public void update (double time) {
	Pair delta = curVelocity.times(time);
        nextPosition.set(curPosition.plus(delta));
        bounce();
    }

    public void advance () {
	curPosition.set(nextPosition);
	curVelocity.set(nextVelocity);
    }

    public void setPosition(Pair p){
        curPosition.set(p);
    }

    public void setVelocity(Pair v){
        curVelocity.set(v);
    }

    public abstract void draw(Graphics g);

    protected void bounce() {
	
        if (nextPosition.getX() - width / 2 <= 0){
            nextVelocity.flipX();
            nextPosition.setX(width / 2);
        } else if (nextPosition.getX() + width / 2 >= world.getWidth()){
            nextVelocity.flipX();
            nextPosition.setX(world.getWidth() - width / 2);
        }
	
        if (nextPosition.getY() - height / 2 <= 0){
            nextVelocity.flipY();
            nextPosition.setY(height / 2);
        } else if (nextPosition.getY() + height / 2 >=  world.getHeight()) {
            nextVelocity.flipY();
            nextPosition.setY(world.getHeight() - height / 2);
        }
    }
}
```
    
<!-- ## Activity -->

<!-- Write subclasses for [`BouncingShape`](/assets/java/abstract-bouncing-shapes/BouncingShape.java) to draw different shapes! -->

<!-- - disk -->
<!-- - square -->
<!-- - diamond -->
