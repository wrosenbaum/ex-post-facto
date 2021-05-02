---
title: "Lecture 15: Linked Lists"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 15: Linked Lists

## Overview

1. A `zoo` as an Array
2. Linked Lists, Conceptually
3. Implementing a Linked List

## Previously

Stored `zoo` as an array of `Animal`s:

```java
Animal[] zoo = new Animal[ZOO_SIZE];

for (int i = 0; i < ZOO_SIZE; i++) {
    Animal[i] = new ...;
}
```

Problem:

- We need to specify `ZOO_SIZE` in advance!
- But we may want to add/remove `Animal`s from our `zoo`

How can we make our zoo *dynamic*?

## A `zoo` Array in Memory

How is `Animal[] zoo` actually stored in our computer's memory?

<div style="margin-bottom: 18em"></div>

## Another Way to Store a Zoo

Think Locally!

- Consider an actual zoo
- Each animal is in some kind of enclosure
- Each enclosure has:
    + a sign indicating what animal(s) are there
	+ another sign pointing to next enclosure
- This is enough information to navigate the zoo and see all animals

<div style="margin-bottom: 8em"></div>

## Representing a `Zoo` in Java

High level picture---a **Linked List**:

<div style="margin-bottom: 18em"></div>

## Thinking `class`es

What do we need?

1. `class Node` representing enclosure
    + stores (reference to) an `Animal`
	+ stores (reference to) next `Node`
2. `class Zoo`
    + stores (reference to) first `Node` (if any)
	+ stores (reference to) last `Nodes` (if any)
	+ implements desired operations:
	    - feed `Animal`s
		- add an `Animal`
		- remove an `Animal`
		- ...

## Implementing `Node`

```java
class Node {
    private Animal animal;
    private Node next;

    public Node (Animal animal) {
	this.animal = animal;
	next = null;
    }

    public void setNext (Node nd) {
	next = nd;
    }

    public Node getNext () {
	return Next;
    }

    public Animal getAnimal () {
	return animal;
    }
}
```

## Making the `Zoo` Class

```java
public class Zoo {
    private Node head = null;
    private Node tail = null;

    public void add (Animal a) {...}
	
    public void feedAnimals () {...}
	
    public void remove (Animal a) {...}

    public boolean contains (Animal a) {...}
}
```

## How to Implement `add`?

![](/assets/img/linked-list/initial.png){: width="100%"}

## `add` a `Platypus` named Doug

![](/assets/img/linked-list/add-doug.png){: width="100%"}

## Step 1: Create a `Node` for Doug

![](/assets/img/linked-list/create-node.png){: width="100%"}

## Step 2: Update `tail.next`

![](/assets/img/linked-list/update-tail-next.png){: width="100%"}

## Step 3: Update `tail`

![](/assets/img/linked-list/update-tail.png){: width="100%"}

## Now `Doug` is in the List

![](/assets/img/linked-list/inserted.png){: width="100%"}

## What if List Was Empty?

![](/assets/img/linked-list/empty.png){: width="100%"}

## Make New `Node` `head` and `tail`

![](/assets/img/linked-list/empty-inserted.png){: width="100%"}

## `add` in Code

1. Create a `Node` for `doug`:
    ```
	Node nd = new Node(doug);
	```
2. If `tail != null`
    - Update `tail.next`:
        ```
		tail.setNext(nd);
		```
	- Update `tail`:
	    ```
		tail = nd;
		```
3. Else
    - Set `head`, `tail`:
	    ```
	    head = nd;
	    tail = nd;
        ```
		
## Activity

Think about how to implement other `Zoo` operations!

- Feed all the animals
- Remove an animal
- Determine if the zoo contains a given animal

## How to Implement `feedAnimals`?

![](/assets/img/linked-list/inserted.png){: width="50%"}

<div style="margin-bottom: 8em"></div>


## How to Implement  `remove`?

![](/assets/img/linked-list/inserted.png){: width="50%"}

<div style="margin-bottom: 8em"></div>

## How to Implement `contains`?

![](/assets/img/linked-list/inserted.png){: width="50%"}

<div style="margin-bottom: 8em"></div>

## Testing Our Code

## Another Question

With the array `Animal[] zoo` we could access all of the `Animal`s easily:

```java
Animal[] zoo;
...
for (int i = 0; i < zoo.length; i++) {
   // do something with zoo[i]
}
```

How could we do something similar with the `Zoo` class?

<div style="margin-bottom: 8em"></div>

## Coming Up

- Generics
- Interfaces
- Iterators
