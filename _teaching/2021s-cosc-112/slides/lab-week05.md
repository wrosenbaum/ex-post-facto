---
title: "Lab 03: Bouncing Disks"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lab 03: Bouncing Disks

## Announcements

1. Accountability Group Assignments
    + arrange to meet this week
2. Quiz 02 is actually happening this week
    + on objects and memory
3. Rest of week:
    + finishing objects and memory
	+ starting object inheritance

<section>
<img src="/assets/img/2021-03/WiCS-slide.png">
</section>

## Lab 03: Bouncing Disks

Two new features:

1. Object instances represented in graphical form
    + `BouncingDisk` 
2. Animation
    + draw/update loop
	
## Quick Demo

## Bouncing Disk

A `BouncingDisk` stores

- position
- velocity
- radius
- color

Primary instance methods

- draw itself on a `Graphics` object
- update its new state

## Animation in Theory

- Sequence of frames displayed in rapid succession
    + 24 fps $\implies \sim 42$ ms per frame for movies
	+ $\sim 60$ fps $\implies \sim 17$ ms per frame modern broadcast
- Each frame differs incrementally from previous
- Gives illusion of motion

## Animation in Code

Draw/update loop:

```java
        while(true){
            world.update(1.0 / (double) FPS);
            this.repaint();
            try{
                Thread.sleep(1000/FPS);
            }
            catch(InterruptedException e){
		System.err.println("Thread interrupted.");
		return;
	    }
        }
```

## Animating Motion

A moving object:

- position
- velocity (speed + direction)
- units: pixels / second
- how to update for next frame?

<div style="margin-bottom: 8em"></div>

## How to Represent Bouncing?

<div style="margin-bottom: 12em"></div>

## Your Task

1. Complete `Pair` class 
    + reprsents *vector* $(x, y)$-coordinates
    + no explicit instructions
	+ error messages tell you what is missing
	+ remark: multiple constructors possible!
2. Extensions
    + disks accelerate due to gravity?
    + disks change color over time?
	+ disks interact with each other?


## Assignment Demo

[Download the Code](/teaching/2021s-cosc-112/labs/03-bouncing-disks/)




	
