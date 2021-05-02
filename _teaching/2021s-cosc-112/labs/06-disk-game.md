---
title: "Lab 06: Disk Game"
description: "a simple game"
layout: page
---

In this lab, you will explore how to use **event handling** in order to implement a simple game. Previously our graphical programs were passive: once the the program was launched, the user could not interact with the program in real time. Event handling allows our programs to get input from the user—for example, reading individual keystrokes, cursor location and clicks—in real time. Using these events, we can write programs that are truly interactive.

The game is simple. The player manipulates a **ship** (for now, simply a white disk) using the arrow keys to control the ship’s acceleration. The ship moves around the screen and bounces off of the walls. There are two other types of objects on the screen: **hazards** (red disks) and **goals** (small green disks). The goal of the game is to reach all of the goals while avoiding the hazards. Here is a screenshot of the game:

![](/assets/img/2021s-cosc-112/lab06-disk-game/screenshot-1.png){: width="100%"}

Initially the ship is stationary at the center of the screen. When the player presses an arrow key, the ship accelerates in corresponding direction. When the arrow is released, the ship ceases to accelerate, but continues moving under the influence of inertia.

When the disk reaches a goal (small green disk), the goal is removed from the screen, as shown below.

![](/assets/img/2021s-cosc-112/lab06-disk-game/screenshot-2.png){: width="100%"}

If the ship hits a hazard, the game ends, and a message is printed to the console. 


## Instructions

Download the code below to get started:

- [`DiskGame.zip`](/assets/java/2021s-cosc-112/lab06-disk-game/DiskGame.zip)


The contents of all the files should be familiar from the Bouncing Disks lab. For this lab, you should finish the implementations of the following methods:

- `keyPressed(KeyEvent e)` and `keyReleased(KeyEvent e)` in `DiskGame.java`. These methods are required to implement [the `KeyListener` interface](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/event/KeyListener.html), which in turn allows our program to get keyboard input from the user. The first method gets called whenever a key is pressed, while the second method gets called whenever a key is released. The `KeyEvent e` passed to method gives all the information you need to figure out which key has been pressed/released—see the [`KeyEvent` documentation](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/event/KeyEvent.html) for details. Your program should, for example, set the acceleration of the ship to be in the upward direction while the up arrow key is pressed until the up arrow key is released.
- `reset()` in `Game.java`. This method removes all hazards and goals from the game (if any), and adds new hazards and goals in random locations. (Use the appropriate constructor for `BouncingDisk` to set these locations.)
- `drawShapes(Graphics g)` in `Game.java` should call the `draw` method on all of the shapes in the game: the ship, hazards, and goals.
- `setShipVertAccel (int amount)` and `setShipHorAccel (int amount)` in `Game.java`. These methods set, respectively, the vertical (y) and horizontal (x) components of the ship’s acceleration. You’ll want the `amount` to be `0`, `1`, or `-1`. The amount should be multiplied by the `ACCEL_FACTOR` (currently set to `50`), so that after `setShipVertAccel(1)` is called, the ship’s vertical acceleration would be `50`, while `setShipVertAccel(-1)` and `setShipVertAccel(0)` would set the ships vertical acceleration to `-50` and `0`, respectively.
- `checkGoalCollision()` checks if the ship is currently overlapping with any goal disk. If so, the disk should be removed from the game. If all goal disks have been removed, the `reset()` method should be called to populate the game with new goals and hazards.
- `checkHazardCollision()`checks if the ship is currently overlapping with any hazard disk. If so, the game should print a message saying the player lost, and exit. 


## Extensions

- Modify the `Game` class so that it keeps track of the player’s score—i.e., the number of goals they have reached. When the game ends, print the player’s score to the console.
- Update the `Game` class to have levels and lives. That is, when the player hits a hazard, they lose a life. The game only ends when they have lost all of their lives. Additionally, the player hits all of the goals, you could have the player reach another level. Increasing levels could have increased difficulty, e.g., by introducing more hazards, or faster motion.
- Make the game look better with graphics and animations! Add a background image, and/or make the ship/hazards/goals images. You might find the [Oracle image tutorial](https://docs.oracle.com/javase/tutorial/2d/images/index.html) helpful. When a goal is reached or a hazard encountered make some sort of animation. (You can reuse some code from the fountain project to make explosions.)
- Make the game more dynamic by having goals and or hazards move around.
- Add additional interior walls that the ship (and moving hazards/goals) bounce off of.
- Program an entirely different game like [Asteroids](http://www.dougmcinnes.com/html-5-asteroids/) or [Breakout](https://codeincomplete.com/games/breakout/).

## What to submit

Submit all code needed to run your program (`DiskGame.java`, `Game.java`, `Pair.java`, `BouncingDisk.java`) to the Moodle site by **Friday, April 30, 11:59 AoE.** Additionally, please fill out the survey linked to from the Moodle submission site.

## Grading

The lab will be graded on a 3 point scale as follows:

- **3** Everything compiles and runs as specified in this document; code is fairly readable and contains comments briefly describing the main functionality of methods defined/larger chunks of code.
- **2** The program produces more-or-less correct output, but is sloppy/hard to read; comments may be there, but are not helpful.
- **1** Program compiles, but is far from producing the expected output and/or does not run as specified; comments unhelpful or absent.
- **0** Program doesn't compile or outputs garbage; no comments explaining why.

Additionally, **extra credit** will be awarded for extensions, as well as early submissions (up to 4 days early) as specified in the course syllabus. If you submit an extension, please include a `README` file that describes the functionality of the extension.

