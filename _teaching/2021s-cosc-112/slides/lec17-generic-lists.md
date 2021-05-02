---
title: "Lecture 17: Generic Linked Lists"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 17: Generic Linked Lists

## Announcements

1. Lab 06 (forthcoming) due next Friday (4/30)
    - last lab assignment
2. Project 3:
    - assigned 5/3
	- due 5/28 (suggested 5/19)
3. Weekly Quizzes
    - 4/26, 5/3, 5/10, 5/17
	- in lieu of exam
4. Extension portfolio (optional)
    - due 5/28

## Overview

1. `Zoo` Implementation
2. Iterating over the List
3. Making the List Generic

## Last Time: `Zoo` as Linked List

![](/assets/img/linked-list/inserted.png){: width="100%"}

## We Implemented

1. `add(Animal a)` to append a new `Animal`
2. `contains(Animal a)` to check if an `Animal` is in the `Zoo`
3. `feedAnimals()` to feed the `Animals`

We discussed `remove(Animal a)` to remove an `Animal`

## `remove()` in Pictures

![](/assets/img/linked-list/inserted.png){: width="100%"}

<div style="margin-bottom: 4em"></div>

## `remove()` in Code

```java
    public void remove (Animal a) {
	Node curr = head;
	
        if (curr == null) {
            return;
        }

	if (curr.getAnimal().equals(a)) {
	    head = curr.getNext();
	    return;
	}
	
	Node prev = curr;
	curr = curr.getNext();

	while (curr != null) {
	    if (curr.getAnimal().equals(a)) {
		prev.setNext(curr.getNext());
		return;
	    }
	    
	    prev = curr;
	    curr = curr.getNext();
	}
    }
```

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

## Idea:

- Store a current node in the `Zoo`
- Write a method `next()` that:
    1. gets the animal stored in the current node (if not `null`)
	    + if current node is `null`, return `null`
	2. sets the current node to its next node
	3. returns the animal
- Write a method `hasNext()` to check if there is a next animal
	
(We will see a more elegant way using iterators later...)
	
## Testing our Code

# Generics

## More than just a Zoo

The logic and structure of our `Zoo` is great!

But what if we want to store things other than `Animal`s?

## Zoo
	
![](/assets/img/linked-list/zoo-final.png){: width="100%"}

## School
	
![](/assets/img/linked-list/school.png){: width="100%"}

## Set of Numbers
	
![](/assets/img/linked-list/numbers.png){: width="100%"}

## Same Logic, Same Implementation?

How could we represent a list of *anything?*

<div style="margin-bottom: 12em"></div>

## One Idea: Use `Object`

![](/assets/img/linked-list/objects.png){: width="100%"}

## An Implementation

```java
public class ListOfObjects {
    private Node head = null;
    private Node tail = null;

    public void add (Object obj) {
        ...	
    }

    public void remove (Object obj) {
        ...	
    }
    
    public boolean contains (Object obj) {
        ...	
    }
	
    public Object next () {
        ...	
    }
}

class Node {
    private Object obj;
    private Node next;

    public Node (Object obj) {
	this.obj = obj;
	next = null;
    }

    public void setNext (Node nd) {
	next = nd;
    }

    public Node getNext () {
	return next;
    }

    public Object getObj () {
	return obj;
    }
}
```

## Why Isn't This Great?

```java
ListOfObjects list = new ListOfObjects();
list.add(new Elephant("Alice"));
...
```

<div style="margin-bottom: 12em"></div>


## Why this Isn't Great I

```java
ListOfObjects list = new ListOfObjects();
list.add(new Elephant("Alice"));
...
```

Casting! Every time we access something in the list, it must be cast as the correct type:

```java
Animal a = (Animal) list.next()
```

This is annoying...

## Why this Isn't Great II

```java
ListOfObjects list = new ListOfObjects();
list.add(new Elephant("Alice"));
...
```

There is nothing requiring us to use the same datatype

```java
list.add(new Platypus("Bob"));
list.add("Adding a String now");
list.add(Integer.getInteger("3745"));
```

These operations are perfectly valid!

- No error checking for consistent types!

## Generics: A Better Way

Idea: Write a `GenericList` class that

1. can be used to store *any* specified type of object
2. each instance only stores one type
3. Java checks that correct types are used

## Generic Syntax

Declare `class` with a *type*, `T`:

```java
public class GenericList<T> {
    ...
}
```

Inside the class, `T` can be used like a variable representing the type:

```java
// add an item of type T
public void add (T item) {
    ...
}

// get next item of type T
public T next () {
    ...
}
```

## Two Options for `Node`

1. Make `Node` generic:
    ```java
	class Node<T> {
	    private T item;
        private Node<T> next;
        ...
	}
	```
2. Make `Node` an *inner class*:
    ```java
	public class GenericList<T> {
	
	    ...
    
        class Node {
            private T item;
            private Node<T> next;
            ...
        }		
	}
	```

## Using Our Generic List

```java
// make lists storing different types
GenericList<Animal> zoo = new GenericList<Animal>();
GenericList<Person> amherst = new GenericList<Person>();

// populate the lists
zoo.add(new Platypus("Alice"));
amherst.add(new Person("Bob"));

...

// these give errors!
amherst.add(new Elephant("Eve"));
zoo.contains("Bob");
```
 


