---
title: "Lecture 12: Polymorphism"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 12: Polymorphism

## Announcements

1. Please compile and run the code for Project 02 today!
    - [Project Description](https://willrosenbaum.com/teaching/2021s-cosc-112/projects/02-substitution-cipher/)
	- [Response](https://forms.gle/meXLqN5XM2SFeA1t5)
2. Quiz Posted Today
    - Covers object inheritance
    - Complete by Sunday

## Outline

1. Recap of Inheritance
2. Polymorphism

## Advantage of Inheritance

Encapsulation!

- Write a (super) class with common features of many classes
- Write sub classes that inherit from super class
    + only implement the *differences* with the super-class
- Do not re-implement/duplicate common code
	
## When to use Inheritance

The "is a" rule:

- Two object types: `Object1`, `Object2`
- If `Object2` is a `Object1`, then it might make sense to define `Object2` as a subclass of `Object1`
    + e.g. `Apple` is a `Fruit`, so maybe define `class Apple extends Fruit`

	
# Polymorphism

## An Example, with Animals

```java
public abstract class Animal {
    private String name;

    public Animal (String name) { this.name = name; }

    public String getName () { return name; }

    public abstract String getSpecies ();

    public void feed () {
	System.out.println("You just fed " + name + " the " + getSpecies()
			   + " some " + getFavoriteFood() + "!");
    }

    public abstract String getFavoriteFood ();

    public abstract void printAnimalFact ();
}
```

## Your Task

Write a `class` that `extends Animal`

1. Implement all `abstract` methods
2. Implement one method that is specific to your animal
    + e.g., `Dog` might have a `bark()` method that prints `Woof!` to `System.out`.
	
## Making a Zoo

A zoo with one type of animal:

```java
public class Zoo {
    public static final int ZOO_SIZE = 10;
        
    public static void main(String[] args) {
	
	Platypus[] animals = new Platypus[ZOO_SIZE];
	
	for (int i = 0; i < animals.length; i++) {
	    animals[i] = new Platypus(Names.getRandomName());
	}

	for (int i = 0; i < animals.length; i++) {
	    animals[i].feed();
	}
    }
}
```

## But We Want More!

How can we store different *types* of animal in our zoo?

- Declare `animals` to be an array of `Animal`:

```java
Animal[] animals = new Animal[ZOO_SIZE];
```

- Initialize each entry in `animals` to be the desired type of `Animal`:

```java	
animals[0] = new Platypus(Names.getRandomName());
animals[1] = new Dog(Names.getRandomName());
...
```

## Why is this Okay?

```java
Animal[] animals = new Animal[ZOO_SIZE];
animals[0] = new Platypus(Names.getRandomName());
animals[1] = new Dog(Names.getRandomName());
...
```

- `animals[i]` stores a *reference* to an `Animal`
- a `Platypus` is type of `Animal`
    + `Platypus extends Animal`
- can call any method defined in `Animal` class...

## Polymorphism

The entries in `animals` are (references to) `Animal`s

- But they are *also* types of `Animal`
    + some are `Platypus`es, some are `Dog`s, ...
- A single instance satisfies the specification of multiple classes at once

This is **polymorphism**

## Checking Types

We can see polymorphism using the `instanceof` keyword

- `alice instanceof Animal` returns `true` if `alice` is an `Animal`
- `alice instanceof Platypus` returns `true` if `alice` is a `Platypus`

For example

```java
	    if (alice instanceof Animal) {
		System.out.println("Alice is an Animal!");
	    }
	    if (alice instanceof Platypus) {
		System.out.println("Alice is a Platypus");
	    }
```

## Question

What happens if call `alice.sting()`?

- `alice` refers to a `Platypus`
- `Platypus` has a method `sting()`
- `alice` refers to an `Animal` that is a `Platypus`
    + so she can `sting()` right?

<div style="margin-bottom: 8em"></div>

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
    Animal alice = new Platypus("Alice");
    alice.sting();
```
- The variable `alice` is treated as a reference to an `Animal`, not a `Platypus`
- we can make a new variable referring to the same instance, but treated as a `Platypus`:

```java
    Platypus aliceToo = (Platypus) alice; // cast alice a ref to Platypus
    aliceToo.sting(); // this works now!
```

## A problem with arrays

- What if we don’t know the type of an entry of an array?
    - if `animals[0]` is a `Platypus`, it should `sting()`
    - otherwise don’t do anything
- use the operator `instanceof`

```java
    if (animals[i] instanceof Platypus) {
      Platypus p = (Platypus) animals[i];
      p.sting();
    }
```









