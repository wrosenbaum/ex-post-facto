---
title: "Lecture 05: Defining Objects"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 05: Defining Objects

## Outline

1. A Final Thought on Recursion
2. Defining New Objects
3. A Counter Object

## A Final Thought on Recursion

Recursion is...

- subtle
- sometimes efficient, sometimes not
- powerful
- miraculous
- confusing

## How Confusing?

What does this program do?

```java
public static long collatz (long n) {
    if (n == 1) return 1;
    if (n % 2 == 0) return collatz (n / 2);
    else return collatz (3 * n + 1);
}
```

<div style="margin-bottom: 12em"></div>

## Insanity

```java
public static long collatz (long n) {
    if (n == 1) return 1;
    if (n % 2 == 0) return collatz (n / 2);
    else return collatz (3 * n + 1);
}
```

- **It is not known if this method enters an infinite loop for some value of `n`**
- This is not for lack of interest in the problem:
    - studied by some of the most eminent mathematicians of the last century

# Defining New Objects

## Coding So Far

1. Statements, blocks, control flow (conditionals and iteration)
2. Methods: **procedural programming**
    + encapsulate code in methods
	+ methods recieve input (arguments), perform operations, (maybe) produduce output (`return`)
	+ when a method returns, all its resources (local variables) go away
	

## Procedural Programming

Design Principle:

- Break a large task into smaller sub-tasks
    + write a method for each sub-task
	
- Makes code better:
    + shows *intent*
	+ easier to maintain (one error, one bug)
	+ makes code easier to read/understand
	
Methods allow for **encapsulation**: write a single piece of code that can be used by many parts of a program.

	
## Object Orientation

Idea: encapsulate code in **objects**

1. Create object **instances**
    + each instance is like its own program
2. Instances have internal **state**
    + *instance variables* 
3. Instances are **persistent**
    + once an instance is created, it remains indefinitely
4. Instances can change internal state
    + *instance methods* are specific to each instance
5. Instances can interact
    + call methods on an instance 
	
## Object Oriented Design

A different way to conceptualize a program:

- Think in terms of interacting objects

Advantages:

- More conceptual structure
- Better encapsulation
- Separate **interface** from **implementation**
    + don't need to understand how an object works (internally) to use the object

## Note

- Object orientation is a useful *abstraction*
- It does not provide greater power or more functionality
- It is a way to think about:
	- solving problems
	- writing code
	- designing software
	
## An Object

Forget about programming for a moment

<iframe width="560" height="315" src="https://www.youtube.com/embed/RyCnHyUKt5M" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

## Describe the Clicker Counter

## What Functionality Does it Offer?

<div style="margin-bottom: 12em"></div>

## How do You *Interact* With it?

<div style="margin-bottom: 12em"></div>

## What is its Internal State?

<div style="margin-bottom: 12em"></div>

## What *Limitations* Does it Have?

<div style="margin-bottom: 12em"></div>

## How Can We Represent it in Code?

We will define a `Counter` object!

- Internal state:
    + store count as an `int`
	
- Instance methods:
    + get count
	+ increment counter
	+ reset
	
- Constructor

Code it together!
	
## The Completed Object

```java
public class Counter {
    private int count;
    
	// constructor defines how to initialize instance
    public Counter () {
	    count = 0;
	}
	
	// getter method for count
	public int getCount () {
	    return count;
	}
	
	// increment the counter
	public void increment () {
	    count++;
	}
	
	// reset the counter
	public void reset () {
	    count = 0;
	}
}
```

## How can we use the Counter?

Create an **instance** of the `Counter` class:

```java
// create a Counter instance
Counter myCounter = new Counter();
		
// increment the counter: call instance method		
myCounter.increment();

// print the current count
System.out.println("Current count: " + myCounter.getCount());
```

## Why Make a `Counter` Class?

Couldn't we have just used an `int`?

<div style="margin-bottom: 12em"></div>

## Reasons `Counter` is Preferable to `int`

1. *Semantics.* a `Counter` signals object is being used to *count* something
    + an `int` could signify anything! 
2. *Safety.* a `Counter` restricts the operations 
    + cannot do something accidentally that would mess up count
3. *Separate implementation from interface.* 
    + can change implementation without affecting code that uses counter
	+ can use `Counter` objects without knowledge of internal workings
4. *Extensibility.* can add functionality without affecting the code using `Counter`

## Coming Up

Lots more about objects!

- Access control (`public`, `private`, and in between)
- Instance vs. class variables and methods
- Passing object instances to methods
- Defining relationships between objects


