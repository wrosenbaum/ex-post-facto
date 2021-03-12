---
title: "Lecture 06: More on Objects"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 06: More on Objects

## Overview

1. Finishing and Testing our Counter Object
2. Strange Arithmetic

## Last Time

We will defined a `Counter` object!

- Internal state:
    + store count as an `int`
	
- Instance methods:
    + get count
	+ increment counter
	+ reset
	
- Constructor

## Today

Finish and test the implementation!

## Finished Implementation

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

# Strange Arithmetic

## Middle School Arithmetic

Given any positive integer $a$, we have

- $$1 = \frac{a}{a} = a \cdot \frac 1 a.$$

Writing the multiplication as repeated addition, we get

- $$a \cdot \frac 1 a = \underset{a \text{ terms}}{\underbrace{\frac 1 a + \frac 1 a + \cdots + \frac 1 a}} = 1$$

What does Java think of this?


## Testing the Identity

Consider the following piece of code

```java
    int a = 10;
    double recip = 1.0 / a;
    double product = 0;
    
    // add 1/a to itself a times
    for (int i = 0; i < a; i++) {
      product += recip;
    }
```


## Questions

1. What do you expect the value of `product` to be?
2. What is the value of `product`?
3. What should it be?

## Test Code

What happened?

<div style="margin-bottom: 4em"></div>

Why?

<div style="margin-bottom: 8em"></div>

Does it matter?

<div style="margin-bottom: 8em"></div>

## Worse arithmetic

Now consider an execution of the following code:

```java
    double one = product;
	int iter = 50;
	for (int i = 0; i < iter; i++) {
	    one = one * one;
	}
```

## Same Questions

1. What do you expect the value of `power` to be?
2. What is the value of `power`?
3. What should it be?

## What is Going on Here?

- Our calculations that should all give a value of 1 

- But can give values arbitrarily far from 1
    - Why? 
	
	<div style="margin-bottom: 4em"></div>
	
    - How can we fix this?
	
	<div style="margin-bottom: 8em"></div>

## Representation of floats/doubles

- Java represents floating point numbers (`float` and `double`) in binary
- For many fractional values, this behavior leads to round-off errors 
    - the number 1 / 10 does not have a finite binary expression, so it is rounded off
- when we perform more arithmetic operations, round-off error can add up to be (unacceptably) large!

## How can we fix this problem?

How can we ensure that we have, for example, 

- $$\frac 1 6 + \frac 1 6 + \frac 1 6 + \frac 1 6 + \frac 1 6 + \frac 1 6 = 1$$?

Increasing the precision of `float` and `double` won’t fix the problem!

<div style="margin-bottom: 8em"></div>




## (Maybe) better design?

Design a `Fraction` object to represent fractional values!

- Follow the link from class to work on a group activity

## State of a Fraction Object

- What instance variables to store?

<div style="margin-bottom: 12em"></div>

## How to Implement Arithmetic (`+` and `*`)?

<div style="margin-bottom: 18em"></div>


## What Subtleties/problems/inconveniences of Using `Fraction`?

<div style="margin-bottom: 18em"></div>


## Next Time

We'll write and test our `Fraction` class!


