---
title: "Lecture 14: Objects and Lists"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 14: Objects and Lists

## Overview

1. `Object` Inheritance
2. Representing Lists of Objects

## Last Time

- Casting objects as other types
- Class hierarchies
- The `Object` class
    + every `class` is a subclass of `Object`

## Why is `Object` Inheritance Good?

`Object` has the following methods (among others): 

-  `boolean equals(Object obj)`
-  `String toString()`
-  ...

Therefore, *every* class in Java has these methods

## Testing Methods with `Animal`

```java
Animal a = new Elephant("Alice");
Animal b = new Elephant("Alice");
```

What is the behavior of...

- ... `a.equals(b)`?
- ... `a.toString()`?

Let's see!

<div style="margin-bottom: 6em"></div>

## Overriding Default Behavior

- default `a.equals(b)` is equivalent to `a == b`
- default `a.toString()` prints address (not meaningful)

How *should* we...

- ... check equality of `Animal`s?
- ... represent an `Animal` as a `String`?

<div style="margin-bottom: 8em"></div>

<!-- # Extending our Zoo -->

<!-- ## Previously -->

<!-- Stored `zoo` as an array of `Animal`s: -->

<!-- ```java -->
<!-- Animal[] zoo = new Animal[ZOO_SIZE]; -->

<!-- for (int i = 0; i < ZOO_SIZE; i++) { -->
<!--     Animal[i] = new ...; -->
<!-- } -->
<!-- ``` -->

<!-- Problem: -->

<!-- - We need to specify `ZOO_SIZE` in advance! -->
<!-- - But we may want to add/remove `Animal`s from our `zoo` -->

<!-- How can we make our zoo *dynamic*? -->

<!-- ## A `zoo` Array in Memory -->

<!-- How is `Animal[] zoo` actually stored in our computer's memory? -->

<!-- <div style="margin-bottom: 18em"></div> -->

<!-- ## Another Way to Store a Zoo -->

<!-- Think Locally! -->

<!-- - Consider an actual zoo -->
<!-- - Each animal is in some kind of enclosure -->
<!-- - Each enclosure has: -->
<!--     + a sign indicating what animal(s) are there -->
<!-- 	+ another sign pointing to next enclosure -->
<!-- - This is enough information to navigate the zoo and see all animals -->

<!-- <div style="margin-bottom: 8em"></div> -->

<!-- ## Representing a `Zoo` in Java -->

<!-- High level picture---a **Linked List**: -->

<!-- <div style="margin-bottom: 18em"></div> -->

<!-- ## Thinking `class`es -->

<!-- What do we need? -->

<!-- 1. `class Node` representing enclosure -->
<!--     + stores (reference to) an `Animal` -->
<!-- 	+ stores (reference to) next `Node` -->
<!-- 2. `class Zoo` -->
<!--     + stores (reference to) first `Node` (if any) -->
<!-- 	+ stores (reference to) last `Nodes` (if any) -->
<!-- 	+ implements desired operations: -->
<!-- 	    - feed `Animal`s -->
<!-- 		- add an `Animal` -->
<!-- 		- remove an `Animal` -->
<!-- 		- ... -->

<!-- ## Implementing `Node` -->

<!-- ```java -->
<!-- class Node { -->
<!--     private Animal animal; -->
<!--     private Node next; -->

<!--     public Node (Animal animal) { -->
<!-- 	this.animal = animal; -->
<!-- 	next = null; -->
<!--     } -->

<!--     public void setNext (Node nd) { -->
<!-- 	next = nd; -->
<!--     } -->

<!--     public Node getNext () { -->
<!-- 	return Next; -->
<!--     } -->

<!--     public Animal getAnimal () { -->
<!-- 	return animal; -->
<!--     } -->
<!-- } -->
<!-- ``` -->

<!-- ## Making the `Zoo` Class -->

<!-- ```java -->
<!-- public class Zoo { -->
<!--     private Node head = null; -->
<!--     private Node tail = null; -->

<!--     public void add (Animal a) {...} -->
	
<!--     public void feedAnimals () {...} -->
	
<!--     public void remove (Animal a) {...} -->

<!--     public boolean contains (Animal a) {...} -->
<!-- } -->
<!-- ``` -->

<!-- ## How to Implement `add`? -->

<!-- `add`ing in pictures: -->

<!-- <div style="margin-bottom: 18em"></div> -->

<!-- ## How to Implement `feedAnimals`? -->

<!-- <div style="margin-bottom: 18em"></div> -->

<!-- ## How to Implement  `remove`? -->

<!-- <div style="margin-bottom: 18em"></div> -->

<!-- ## How to Implement `contains`? -->

<!-- <div style="margin-bottom: 18em"></div> -->

<!-- ## Another Question -->

<!-- With the array `Animal[] zoo` we could access all of the `Animal`s easily: -->

<!-- ```java -->
<!-- Animal[] zoo; -->
<!-- ... -->
<!-- for (int i = 0; i < zoo.length; i++) { -->
<!--    // do something with zoo[i] -->
<!-- } -->
<!-- ``` -->

<!-- How could we do something similar with the `Zoo` class? -->

<!-- <div style="margin-bottom: 8em"></div> -->

<!-- ## Coming Up -->

<!-- - Generics -->
<!-- - Interfaces -->
<!-- - Iterators -->


