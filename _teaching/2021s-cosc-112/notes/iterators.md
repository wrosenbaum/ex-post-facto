---
title: "Iterators"
description: "an introduction to the Iterator and Iterable interfaces"
layout: page
---



Previously, we implemented a `GenericList<T>` class that stores a collection of objects of type `T` as a linked list. The basic functionality offered by the `GenericList<T>` was as follows:

- `void add(T item)` add `item` to the end of the list.
- `void remove(T item)` remove the first instance of `item` from the list (if present).
- `boolean contains(T item)` determine if `item` is contained in the list.

While these methods provide basic functionality for storing items in a collection, the structure is quite limited. In particular, there is no way to accesses the items in the collection directly. In this note, we will describe a way of iterating over the list so that its contents may be examined. You can download demo code with the following link:

- [`IterableExample.zip`](/assets/java/IterableExample.zip)

## A Generic Linked List

A `GenericList` stores elements in a linked list of `Node`s, where each `Node` stores (1) a reference to the `item` stored in the `Node`, and (2) a reference to the `next` `Node` in the collection.

![](/assets/img/linked-list/objects.png){: width="75%"}

In code, we implemented the `GenericList` as follows:

```java
public class GenericList<T> {
    private Node head = null;
    private Node tail = null;

    public void add (T item) {
        ...	
    }
    
    public boolean contains (T item) {
        ...	
    }
    
    public void remove (T item) {
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

Note that `Node` is a ["nested" or "inner" class](https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html) (declared and defined within the `GenericList` class). This means that (1) a `Node`'s fields (i.e., `item` and `next`) can be directly accessed and modified from within the `GenericList` body, and (2) users of the `GenericList` class cannot create or access `Node`s directly. This second feature is a good design practice, as it forces us to separate the `GenericList`'s implementation from its interface. That is, another programmer should be able to use a `GenericList` without knowing anything about its implementation details.

## Iterating over a Linked List

Suppose we'd like to find out what items are stored in a linked list. How might we accomplish this given the implementation above? Specifically, we'd like to offer similar functionality to the following example with an array:

```java
Object[] arr;

...

for (int i = 0; i < arr.length; ++i) {
    Object item = arr[i];
    // do something with item
}
```

Conceptually, we can access the elements of the list as follows:

1. Store a node `curr`, initially the `head` of the list.
2. While `curr != null`:
    - access the item stored in `curr`
	- set `curr = curr.next`

It is not clear, though, how to implement such a procedure, especially if we want to hide implementation details (i.e., `Node`s) from the user. What we want is an **iterator**: an object that stores a current location (`Node`) that (1) returns the item stored at the node, and (2) updates the current location to the "next" location in the list. Java provides a standard interface for such functionality, called the [`Iterator<T>` interface](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Iterator.html) (click to see the documentation). Specifically, an `Iterator<T>` needs to implement two methods:

1. `boolean hasNext()` determines if there is a "next" item after the current item stored in the collection
2. `T next()` returns the next item stored in the collection.

For our `GenericList`, we can implement an `Iterator<T>` as follows. We make the class another inner class for `GenericList` since it refers to the inner workings of the class:

```java
import java.util.Iterator;

public class IterableList<T> {
    ...

    class Node {
       ...	
    }

    class ListIterator implements Iterator<T> {
	Node curr;
	
	public ListIterator() {
	    curr = head;
	}

	public boolean hasNext() {
	    return (curr != null);
	}

	public T next() {
	    if (curr == null) {
		return null;
	    }
	    
	    T item = curr.item;
	    curr = curr.next;
	    return item;
	}
    }
}
```

Now, given a `ListIterator`, we might want to iterate through all items in a `GenericList` as follows:

```java
GenericList<SomeClass> myList = new GenericList<SomeClass>();

...

Iterator<SomeClass> li = new ListIterator();
while (li.hasNext()) {
    SomeClass item = li.next();
    // do something with item
}
```

Unfortunately, this code is quite ugly. Moreover, we cannot execute `Iterator<SomeClass> li = new ListIterator()`, because `ListIterator` is an inner class for `GenericList` (as it should be!). To get around the latter problem, we can declare that `GenericList` supports functionality for iteration by having it implement the [`Iterable<T>` interface](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Iterable.html) (see link for documentation). That is, we should provide a method that creates and returns an iterator for a particular instance of a `GenericList`. The following modifications turn our `GenericList` into a list that supports iteration. Along with this new functionality, we upgrade the collection's name to `IterableList`:

```java
import java.util.Iterator;

public class IterableList<T> implements Iterable<T> {
    private Node head = null;
    private Node tail = null;

    ...

    public Iterator<T> iterator() {
	return new ListIterator();
    }

    class Node {
        ...	
    }

    class ListIterator implements Iterator<T> {
        ...	
    }
}
```

Now we can get a new iterator for our class by using

```java
IterableList<SomeClass> myList = new IterableList<SomeClass>();

...

Iterator<SomeClass> myList.iterator();
while (li.hasNext()) {
    SomeClass item = li.next();
	// do something with item
}
```

But generally, we'd like to avoid seeing iterators in our code at all! Thankfully Java supports a construction for iterating over `Iterable` collections: the [for-each loop](https://docs.oracle.com/javase/8/docs/technotes/guides/language/foreach.html). Using a for-each loop, the code above becomes:

```java
IterableList<SomeClass> myList = new IterableList<SomeClass>();

...

for (SomeClass item : myList) {
   // do something with item
}
```

The loop above is read "for each `item` in `myList`...". This is so much better! To see `IterableList` in action, download the following program. The `main` method is in `IterableExample.java`.

- [`IterableExample.zip`](/assets/java/IterableExample.zip)








