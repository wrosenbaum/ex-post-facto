---
title: "Lab 03: Bouncing Disks"
description: "Animating objects in Java!"
layout: page
---

I this lab, you will continue your exploration of graphics and object orientation. Your program will produce an animation of disks of various colors bouncing around on the screen. For the basic assignment, the disks will be placed randomly on the screen with random initial velocities, and will bounce upon hitting the edge of the window. You are encouraged to explore some extensions of this basic program, for example by specifying different initial configurations, changing the size and or color of the disks, or figuring out how to make the disks interact with each other. A few specific suggestions will be given below.

To get started, download the source code for the project below, consisting of 4 `.java` files:

- `Bouncing.java` containing the main method
- `BouncingDisk.java` that describes the disks drawn on the screen and their interactions
- `Pair.java` that will provide basic functionality for manipulating pairs of numbers
- `World.java` that represents the world of `BouncingDisks`

[**Download `BouncingDisks.zip` here**](/assets/java/2021s-cosc-112/BouncingDisks.zip)

Once you’ve unzipped the code into an appropriate directory, you can try compiling all of the files from the terminal using the command:

```text
    javac *.java
```

Unfortunately, you’ll be greeted with a bunch of errors! This is because part of the code—namely `Pair.java` has not been implemented yet! The errors point to various other files you’ve downloaded. For example, the first error I see is:

```text
World.java:49: error: constructor Pair in class Pair cannot be applied to given types;
	    Pair position = new Pair((double) (xMin + rand.nextInt(xMax - xMin)), (double) (yMin + rand.nextInt(yMax - yMin)));
	                    ^
  required: no arguments
  found: double,double
  reason: actual and formal argument lists differ in length
```

While this error appears in `World.java`, the problem is not in this file---the problem is with `Pair.java`. In fact, opening `Pair.java` you’ll see that *nothing* has been implemented yet! Your first task is to implement the `Pair` class such that the remaining code will compile and run.

For the purposes of this assignment, you should view the (initial) errors when compiling the provided code not as mistakes, but as instructions for what you need to implement. For example, the first error listed above is telling you that `BouncingDisk` called a constructor for `Pair` with two arguments (both `double`s), but that no such constructors exists. You should interpret this error as the instruction to write a constructor for `Pair` that takes two arguments.

**Recommendation.** Do not attempt to fix all the errors at once! Address the errors one at a time, starting with the first error reported. Try to write code to fix the first error, then compile again---your “fix” for the error may introduce new errors that need to be addressed before moving on. 

Once you implement the complete functionality of `Pair.java` as required to compile the program, running `java Bouncing` without any modifications to the files other than `Pair.java` should output an animated scene similar to the following:

![](https://paper-attachments.dropbox.com/s_60C404F6703B36CB350D54C6CD278EB2B02E376270FADBB732054BEA7CF627CD_1600648278460_BouncingDisks.png){: width="100%"}

## Background

**Vectors and motion**
The `Pair` class you will implement is used to represent the quantities specifying the location and motion of the disks. In math and physics, such quantities are called (2 dimensional) **vectors**. Specifically, a 2 dimensional vector consists of two coordinates and is typically written $$(x, y)$$. Vectors support support two basic operations

- **(vector) addition** $$(x, y) + (x', y') = (x + x', y + y')$$
- **scalar multiplication** $$t (x, y) = (t x, t y)$$ (each coordinate gets multiplied by the number $$t$$).

The code you write for the `Pair` class will provide an implementation of the basic vector operations, as well as a few other operations needed by our program. Specifically, your program will use `Pair`s to represent the *position* the *velocity* of each disk. The code provided will handle the physics of how to compute and update the positions and velocities of the disks, so you do not need to worry about the physics for this lab. It is enough to implement the basic operations for vectors above in accordance with the errors (read: instructions) from the code provided.


## Instructions

To complete the assignment, you must complete the `Pair` class in accordance with its usage in the rest of the program. In particular, you should infer the **access control** (i.e., public or private), **return type**, **name**, and **parameters** for each method and field for `Pair` from the code provided (and the errors produced when compiling the provided code). While methods are given fairly descriptive names, you will need to understand *how* a method for `Pair` is being used in order to implement it. As a general hint, *no method you write for the `Pair` class requires more than a few lines of code to implement.* 


## Extensions

Once you have the basic program working, I encourage you to modify and generalize the code. Here are a few suggested directions you could take—these ideas can also be combined to make a more interesting program:

1. Modify the program so that disks accelerate under the influence of gravity. To implement this, you will need to change the *velocity* of each disk during each update (in addition to the position updates) in accordance with the acceleration due to gravity.
2. Modify the code to start with a specific (rather than random) initial configuration.
3. Modify the code so that the appearance of each disk changes with time or its position. For example, you might have disks that grow or shrink or change color over time or with their location.
4. Modify the physics so that the walls of the `World`  are less “bouncy.” Currently, the collisions are modeled as perfectly elastic: after a bounce, the ball’s speed is unchanged (though its direction changes). Update the program so that a disk bounces back with slightly less speed than before the bounce.
5. Add friction/drag to the simulation. Make it so that disks slow down over time.
6. Have disks interact with each other! For example, you could have disks
    - bounce off each other elastically ([relevant Wikipedia article](https://en.wikipedia.org/wiki/Elastic_collision#Two-dimensional)),
    - attract each other as in gravitational pull (see [Newton’s law of universal gravitation](https://en.wikipedia.org/wiki/Newton%27s_law_of_universal_gravitation)),
    - repel each other as (see [Coulomb’s law](https://en.wikipedia.org/wiki/Coulomb%27s_law)).
    
	Note that in order to implement this extension, you will need to check each pairs of disks for interactions, rather than just looking at disks individually. To this end, you might find it helpful that each disk stores its `Worl world`, and the `World` class has an instance method that returns its array of `BouncingDisk`s.


## What to submit

Submit all code needed to run your program (`Bouncing.java`, `BouncingDisk.java`, `Pair.java`, `World.java` ) to the Moodle site by **Friday, March 19th, 11:59 AoE.** Additionally, please fill out the survey linked to from the Moodle submission site.

## Grading

The lab will be graded on a 3 point scale as follows:

- **3** Everything compiles and runs as specified in this document; code is fairly readable and contains comments briefly describing the main functionality of methods defined/larger chunks of code.
- **2** The program produces more-or-less correct output, but is sloppy/hard to read; comments may be there, but are not helpful.
- **1** Program compiles, but is far from producing the expected output and/or does not run as specified; comments unhelpful or absent.
- **0** Program doesn't compile or outputs garbage; no comments explaining why.

Additionally, **extra credit** will be awarded for extensions, as well as early submissions (up to 4 days early) as specified in the course syllabus.


