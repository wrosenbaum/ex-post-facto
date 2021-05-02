---
title: "Lecture 11: More Inheritance"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 11: More Inheritance

## Overview

1. Recap from Wednesday
2. Activity: Abstract Shapes
3. Class Hierarchies
4. `Object` class

## Recap of Last Time

- Introduced **object inheritance**
    + define one class as a **subclass** of another
	+ only implement the *differences* between the classes
	+ use keyword `extends`

<div style="margin-bottom: 8em"></div>

## Also Last Time

- Introduced **abstract classes**
    + like a normal class, but some methods are `abstract`
	+ `abstract` methods are not implemented, only declared
	+ subclasses must implement `abstract` methods though
- *Instances* of abstract classes cannot be created
	
## Abstract Shapes

```java
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


## Activity

Write subclasses for [`BouncingShape`](/assets/java/abstract-bouncing-shapes/BouncingShape.java) to draw different shapes!

- disk
- square
- diamond

## Test Your `BouncingShapes`

<!-- ## Advantage of Inheritance -->

<!-- Encapsulation! -->

<!-- - Write a (super) class with common features of many classes -->
<!-- - Write sub classes that inherit from super class -->
<!--     + only implement the *differences* with the super-class -->
<!-- - Do not re-implement/duplicate common code -->
	
<!-- ## When to use Inheritance -->

<!-- The "is a" rule: -->

<!-- - Two object types: `Object1`, `Object2` -->
<!-- - If `Object2` is a `Object1`, then it might make sense to define `Object2` as a subclass of `Object1` -->
<!--     + e.g. `Apple` is a `Fruit`, so maybe define `class Apple extends Fruit` -->
	
<!-- ## Going Farther -->

<!-- - A class can have at most one **parent** class -->
<!--     + cannot have `class MyClass extends SomeClass, AnotherClass {...}` -->
<!-- 	+ *no multiple inheritance* -->
<!-- 	+ (we'll return to this later) -->
<!-- - But the parent class can itself  -->

<!-- ## Inheritance Diagrams -->

<!-- How to depict relationship between `Shape`, `Circle`, `Rectangle`, `Ellipse`, `Square`, `Quadrilateral`, `Triangle`? -->

<!-- <div style="margin-bottom: 18em"></div> -->

<!-- ## `Object`: The Mother of all Classes -->

<!-- Every class in Java is automatically a subclass of `Object` -->

<!-- - every class inherits methods from `Object`: -->
<!--     + `boolean equals(Object obj)` -->
<!-- 	+ `String toString()` -->
<!-- 	+ ... -->
	
<!-- Why is this good? -->

<!-- <div style="margin-bottom: 8em"></div> -->

<!-- ## Why is `Object` Inheritance Good? -->

<!-- *Every* class in Java has methods -->

<!-- -  `boolean equals(Object obj)` -->
<!-- -  `String toString()` -->
<!-- -  ... -->

<!-- You can always *assume* these methods are there -->

<!-- ## Example of Usefulness -->

<!-- This following code is *guaranteed* to compile -->

<!-- ```java -->
<!-- MyClass a = new MyClass(); -->
<!-- MyClass b = new MyClass(); -->

<!-- if (a.equals(b)) { -->
<!--     // do something -->
<!-- } -->

<!-- System.out.println("a = " + a); // prints a.toString() -->
<!-- ``` -->

<!-- But you might want to override default behavior here! -->

<!-- - default `a.equals(b)` is equivalent to `a == b` -->
<!-- - default `a.toString()` prints address (not meaningful) -->

<!-- ## Example with Shapes -->

<!-- Printing shape messages -->

<!-- ## Next Up -->

<!-- Polymorphism: -->

<!-- - How to deal with collections of related datatypes -->
<!-- - How to exploit the full power of `abstract`ion  -->








