---
title: "Lecture 19: Stacks"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 19: Stacks

## Overview

1. Interfaces
2. Stacks
3. Stack Interface
4. Stack Implementation

## Last Time

- Introduced `interface`s
- Saw the `Comparable<T>` interface:
    ```java	
public interface Comparable<T> {
    int compareTo (T t);
}
	```
- Updated `Animal` to implement `Comparable<Animal>`:
    ```java
public abstract class Animal implements Comparable<Animal> {
    ...
    public int compareTo (Animal a) {
	    if (this.getSpecies().equals(a.getSpecies())) {
	        return this.name.compareTo(a.name);
	    }

	    return this.getSpecies().compareTo(a.getSpecies());
    }
}
	```
- `Animal`s can now be sorted!

## Sorted Zoo Example
	
## Why Interfaces?

An `interface` is a *contract:*

1. In order to implement the `interface`, you must provide certain functionality
2. If you provide that functionality, I can write code that uses the functionality to perform some task
    + I don't need to know *how* you provide functionality
	
Sorting example:

- We wrote code to store a sorted list of items
- Works for *any* class implementing `Comparable`
- The `class` determines what is *meant* by comparison

## Good Design
	
*Separate interface from implementation*

- Better encapsulation
- Explicit functionality of the class
- Explicit requirements to use existing functionality
- Can provide different implementations of same interface
    + sort by species/name? sort by age? by height?
	+ different implementations might be appropriate for different contexts
	
## Previously

We've seen some data structures:

- array
    + access elements by index
- linked list
    + access elements by reference

Data structures specify:

1. how data is stored
2. how data is accessed
3. how data is modified

## Abstract Data Types

Used linked list data structure to *implement*

1. `GenericList`
    + store a collection of elements
	+ `add` and `remove`
	+ iterate over elements
2. `SortedList`
    + store a *sorted* collection of elements
	+ `insert` and `remove` 
	+ iterate over elements
	
Abstract data type specifies interaction (i.e., interface), not implementation

## Up Next

Two fundamental abstract data types

1. Stack
2. Queue

We'll consider

- implementations
- applications

## A Problem

Learning a new subject:

1. Find a book, start reading
2. Realize I am missing some background for the book
3. Look up references
    + repeat step 1 with new book
	
Eventually, my desk is covered with books!

- How do I keep track of everything?

## My Problem, Illustrated

![](/assets/img/stacks/book-1.png){: width="100%"}

## Get Stuck, Get Another Book

![](/assets/img/stacks/book-2.png){: width="100%"}

## Get Stuck, Get Yet Another Book

![](/assets/img/stacks/book-3.png){: width="100%"}

## And Another

![](/assets/img/stacks/book-4.png){: width="100%"}

## Lesson Learned: See Previous Book

![](/assets/img/stacks/book-5.png){: width="100%"}

## Another Lesson Learned

![](/assets/img/stacks/book-6.png){: width="100%"}

## Stuck Again; Get Another Book

![](/assets/img/stacks/book-7.png){: width="100%"}

## Lesson Learned

![](/assets/img/stacks/book-8.png){: width="100%"}

## Lesson Learned: Back to Start

![](/assets/img/stacks/book-9.png){: width="100%"}

## Keeping Organized

Store books in a stack on my desk:

- Most recently accessed on top
- Book below is book I was reading when I got stuck
- ...

Successively removing books allows backtracking

- Only ever add/remove top book on a stack

## A New Abstract Data Type

Introducing the **stack**!

- Store a collection of items
- Supported operations:
    + *push* a new item on top of the stack
	+ *peek* at the item currently on top
	+ *pop* remove and return the item currently on top

## Stack Operations

![](/assets/img/stacks/book-1.png){: width="100%"}

Push *Sequential...Algorithms* onto stack

## Stack Operations

![](/assets/img/stacks/book-2.png){: width="100%"}

Push *Art of Multiprocessor Programming* onto stack

## Stack Operations

![](/assets/img/stacks/book-3.png){: width="100%"}

Push *Java Concurrency in Practice* onto stack

## Stack Operations

![](/assets/img/stacks/book-3.png){: width="100%"}

Peek returns *Java Concurrency in Practice*

## Stack Operations

![](/assets/img/stacks/book-2.png){: width="100%"}

Pop returns & removes *Java Concurrency in Practice*

## Stack Operations

![](/assets/img/stacks/book-1.png){: width="100%"}

Pop returns & removes *Art of Multiprocessor Programming*

## Stacks in Programming

Stacks are useful in computing!

- Executing method calls
- Search with backtracking
- Evaluating arithmetic expressions
- ...

## A Stack Interface in Java

```java
public interface SimpleStack<T> {
    // push a new item on the top of the stack
    public void push(T item);

    // return the item currently at the top of the stack
    public T peek();

    // remove and return the item currently at the top of the stack
    public T pop();

    // check if the stack is empty
    public boolean isEmpty();
}
```

## Activity

How might we implement a stack?

## Representing Stack as Linked List

How to store items?

<div style="margin-bottom: 18em"></div>

## Representing Stack as Linked List

How to push?

<div style="margin-bottom: 18em"></div>

## Representing Stack as Linked List

How to pop?

<div style="margin-bottom: 18em"></div>


## Representing Stack as Linked List

How to code it?
```java
public class StackList<T> implements SimpleStack<T>, Iterable<T> {
    // fields?
    ...
    
    public void push(T item) {
        ...	
    }
    
    public T peek() {
        ...	
    }
    
    public T pop() {
        ...
    }


    class Node {
	T item;
	Node next;

	public Node (T item) {
	    this.item = item;
	    next = null;
	}
    }

}
```





