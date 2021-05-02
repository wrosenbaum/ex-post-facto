---
title: "Lecture 13: Casting and Objects"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 13: Casting and Objects

## Overview

1. Casting 
2. Hierarchy and the `Object` class
3. Extending our `Zoo`

## Last Time

Polymorphism!

- Instances can identify with with multiple classes:

```java
Animal alice = new Elephant("Alice");
alice instanceof Animal   // returns true
alice instanceof Elephant // also returns true
```

- Can use this behavior to create collections of different types of `Animal`:

```java
Animal zoo[] = new Animal[2];
zoo[0] = new Elephant("Alice");
zoo[1] = new Platypus("Bob");
```

## Today: Going Farther

We have an array of different `Animal`s...

```java
Animal zoo[] = new Animal[2];
zoo[0] = new Elephant("Alice");
zoo[1] = new Platypus("Bob");
```

- Different `Animals` do different things
- We saw:
    + calling `zoo[0].feed()` calls `Elephant` version of `feed`
	+ calling `zoo[1].feed()` calls `Platypus` version of `feed`
- What about methods particular to `Elephant` and `Platypus`?
    + an `Elephant` can `trumpet()`
	+ a `Platypus` can `sting()`

## What Happens...

... if we call

```
zoo[0].trumpet(); // zoo[0] is an Elephant
zoo[1].sting();   // zoo[1] is a Platypus
```

<div style="margin-bottom: 12em"></div>

## We Got an Error!

```text
Zoo.java:23: error: cannot find symbol
		zoo[0].trumpet();
		          ^
  symbol:   method sting()
  location: class Animal
1 error
```

The reason:

- `zoo` is declared to be an array of `Animal`s
    + `trumpet()` is not declared for `Animal`
	+ (`feed()` is declared in `Animal`)
- even though Java *knows* `zoo[0]` is an `Elephant`, the compiler complains because `zoo[0]` wasn't *declared* as an `Elephant`.
    + Java is *strongly typed*
	
## An Ethical Dilemma

What is the point of having a `Zoo` if your `Elephant`s can't `trumpet()`?

## Recall casting

- We can convert some primitive data types to others
- Some conversions are automatic:

```java
int n = 10;
double d = n; // java thinks this is okay
```

## But...

```java
    double d = 10.0;
    int n = d; 
```
gives 
```text
    error: incompatible types: possible lossy conversion from double to int
```

## Explicit Casting

We can force Java to do the conversion by casting:

```java
    double d = 10.0;
    int n = (int) d; // truncates d (i.e., removes everything after the decimal)
```

## Casting with `class`

- We can also cast instances of classes to subclasses
- The following gives an error since `sting` is not defined for `Animal`
```java
    Animal alice = new Elephant("Alice");
    alice.trumpet();
```
- The variable `alice` is treated as a reference to an `Animal`, not an `Elephant`
- We can make a new variable referring to the same instance, but treated as a `Elephant`:

```java
    Elephant aliceToo = (Elephant) alice; // cast alice a ref to Platypus
    aliceToo.trumpet(); // this works now!
```

## In Our `Zoo`

We can 

1. check if `Animal`s are `Elephant`s
2. if so, cast the `Animal` as an `Elephant`, then have it `trumpet()`

```java
	for (int i = 0; i < animals.length; i++) {
	    animals[i].feed();
	    if (animals[i] instanceof Elephant) {
		Elephant e = (Elephant) animals[i];
		e.trumpet();
	    }
```

## A Word of Caution

The Java compiler will let you cast as any sub-class of `Animal`:

```java
	for (int i = 0; i < animals.length; i++) {
	    animals[i].feed();
	    if (animals[i] instanceof Platypus) {
		Elephant e = (Elephant) animals[i];
		e.trumpet();
	    }
        }
```

- This code compiles
- Only get an error when we run the program

```text
Exception in thread "main" java.lang.ClassCastException: class Platypus cannot be cast to class Elephant (Platypus and Elephant are in unnamed module of loader 'app')
	at Zoo.main(Zoo.java:19)
```

- The error is *logical* not *syntactic*

# Hierarchy and the `Object` class

## Taking Things a Step Farther

- A class can have at most one **parent** class
    + cannot have 
	```java
	class MyClass extends SomeClass, AnotherClass {...}
	```
	+ *no multiple inheritance*
	+ (we'll return to this later)
- But the parent class can itself have many sub-classes

## Inheritance Diagrams

How to depict relationship between `Shape`, `Circle`, `Rectangle`, `Ellipse`, `Square`, `Quadrilateral`, `Triangle`?

<div style="margin-bottom: 18em"></div>

## `Object`: The Mother of all Classes

Every class in Java is automatically a subclass of `Object`

- every class inherits methods from `Object`:
    + `boolean equals(Object obj)`
	+ `String toString()`
	+ ...
	
Why is this good?

<div style="margin-bottom: 8em"></div>

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

<!-- ## Example with `Animal`s -->

<!-- - Implement `equals` method -->
<!--     + `public boolean equals (Object obj)` -->
<!--     + when are two `Animal`s equal? -->
<!-- - Implement `toString` method -->
<!--     + how to represent an `Animal` in text? -->
<!-- - Test `toString` with the `ObjectTester` -->

<!-- ## Up Next -->

<!-- Extending our `Zoo` -->

<!-- - `Animal[] zoo = new Animal[size]` requires us to know the size of our zoo in advance -->
<!-- - What if we don't know the size in advance? -->
<!-- - What if we want to add/remove animals? -->

<!-- How can we make our `Zoo` more *dynamic*? -->

<!-- <div style="margin-bottom: 8em"></div> -->


