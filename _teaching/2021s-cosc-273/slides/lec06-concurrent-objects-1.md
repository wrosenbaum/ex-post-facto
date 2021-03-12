---
title: "Lecture 06: Concurrent Objects 1"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 06: Concurrent Objects 1

## Outline

1. Finishing Up Mutex:
    + Bakery Algorithm
	+ Lower Bounds
2. Concurrent Objects


## Last Time

Lamport's Bakery Algorithm

```java
public void lock () {
    int i = ThreadID.get();
    flag[i] = true;
    label[i] = max(label[0], ..., label[n-1]) + 1;
    while (!hasPriority(i)) {} // wait	
}

public void unlock() {
    flag[ThreadID.get()] = false;
}
```

## We Showed:

1. Deadlock-freedom
    + unique thread with highest priority obtains lock
2. First-come-first-served (FCFS) Property
    + if `i` writes `label[i]` before `j` writes `label[j]`, then `i` obtains lock before `j`
	
Starvation-freedom follows from 1 and 2

## To Show: Mutual Exclusion

```java
public void lock () {
    int i = ThreadID.get();
    flag[i] = true;
    label[i] = max(label[0], ..., label[n-1]) + 1;
    while (!hasPriority(i)) {} // wait	
}
```

Suppose not:

- $A$ and $B$ concurrently in CS
- Assume: $(\mathrm{label}(A), A) < (\mathrm{label}(B), B)$


## Proof (Continued)

Since $B$ entered CS:

- Must have read
    + $(\mathrm{label}(B), B) < (\mathrm{label}(A), A)$, or
	+ $\mathrm{flag}[A] == \mathrm{false}$

- Former can not happen: labels strictly increasing

- So $B$ read $\mathrm{flag}[A] == \mathrm{false}$

## Compare Timelines!

<div style="margin-bottom: 18em"></div>

## Conclusion

Lamport's Bakery Algorithm:

1. Works for any number of threads
2. Satisfies MutEx and starvation-freedom

## Question

Is the bakery algorithm *practical?*

- Maybe for few threads...
- But for many threads?
    + `label` array contains $n$ indices
	+ must read all entries to set own `label`
	+ costly if many threads!
- Could we do better?

## Remarkably

We cannot do better:

- If $n$ threads want to achieve mutual exclusion + deadlock-freedom, must have $n$ read/write registers (variables)

- This is really bad if we have a lot of threads!
    + 1,000 threads means each call to `lock()` requires 1,000s of reads
	+ each call to `hasPriority` requires either 1,000 of reads or a more advanced data structure
	
- Things are messy!

## A Way Around the Lower Bound

- Argument relies crucially on fact that the *only* atomic operations are `read` and `write`
- Modern computers offer more powerful atomic operations
- In Java, `AtomicBoolean` class offers, e.g.,
	+ `compareAndSet(boolean expectedValue, boolean newValue)`
    + `getAndSet(boolean newValue)`
- These operations are useful, but still costly
- We will discuss more later

# Concurrent Objects

## So Far

Considered mutual exclusion:

- Restrict access to critical section of code to one thread at a time
- This makes sense for simple objects
    + e.g., the `increment()` method in `Counter`
	
What about larger data structures?

## Linked Lists

Recall: a (doubly) linked list

![](/assets/img/concurrent-objects/OriginalList.png){: width="100%"}

## Insertion 1

![](/assets/img/concurrent-objects/SingleInsertion1.png){: width="100%"}

## Insertion 2

![](/assets/img/concurrent-objects/SingleInsertion2.png){: width="100%"}


## Linked List in Code

```java
public class MyLinkedList {
    private Node head;
	...
	
    // insert a new node after nd
    public void insert (Node nd, value) {
        Node next = nd.getNext();
        Node cur = new Node(value);
        nd.next = cur;
        cur.prev = nd;
        cur.next = next;
        if (next != null) next.prev = cur;
    }
}

class Node {
    public Node next;
    public Node prev;
    public int value;
}
```

## Insertion with Multiple Threads

What could go wrong?

```java
    public void insert (Node nd, value) {
        Node next = nd.getNext();
        Node cur = new Node(value);
        nd.next = cur;
        cur.prev = nd;
        cur.next = next;
        if (next != null) next.prev = cur;
    }
```

<div style="margin-bottom: 8em"></div>

## How to Fix The Problem?

```java
    public void insert (Node nd, value) {
        Node next = nd.getNext();
        Node cur = new Node(value);
        nd.next = cur;
        cur.prev = nd;
        cur.next = next;
        if (next != null) next.prev = cur;
    }
```

<div style="margin-bottom: 12em"></div>

## A Fix: Locking the List

```java
public class MyLinkedList {
    private Node head;
    private Lock lock;
	...
	
    // insert a new node after nd
    public void insert (Node nd, value) {
        lock.lock();
        try { // all of this is critical section
            Node next = nd.getNext();
            Node cur = new Node(value);
            nd.next = cur;
            cur.prev = nd;
            cur.next = next;
            if (next != null) next.prev = cur;
        } finally {
            lock.unlock();
        }
    }
}
```

<div style="margin-bottom: 8em"></div>

## Illustration of Locked Execution

![](/assets/img/concurrent-objects/InsertBoth.png){: width="100%"}

## Red Acquires Lock

![](/assets/img/concurrent-objects/RedBlocks.png){: width="100%"}

## Red Inserts Element

![](/assets/img/concurrent-objects/RedInserts.png){: width="100%"}

## Red Releases Lock

![](/assets/img/concurrent-objects/RedReleases.png){: width="100%"}

## Blue Acquires Lock

![](/assets/img/concurrent-objects/BlueLocks.png){: width="100%"}

## Blue Inserts Element

![](/assets/img/concurrent-objects/BlueInserts.png){: width="100%"}

## Blue Releases Lock

![](/assets/img/concurrent-objects/BlueReleases.png){: width="100%"}

# Nice...

# ...but...

## ...Could we Have Done Better?

![](/assets/img/concurrent-objects/InsertBoth.png){: width="100%"}

How?

<div style="margin-bottom: 12em"></div>

## When Can We Insert Concurrently?

<div style="margin-bottom: 8em"></div>


## What Should we Lock?

Not the whole list!

<div style="margin-bottom: 12em"></div>

## Idea: Locking Individual Nodes

![](/assets/img/concurrent-objects/InsertBoth.png){: width="100%"}

Which nodes need to be locked?

<div style="margin-bottom: 8em"></div>

## Locking Nodes in Code

```java
class Node {
    private Lock lock;
    public Node next;
    public Node prev;
    public int value;
	
    public void lock() { lock.lock(); }
    public void unlock() { lock.unlock(); }
}
```

## Insertion with Locked Nodes

```java
    public void insert (Node nd, value) {
        Node cur = new Node(value);
		
        nd.lock();
        try {
            Node next = nd.getNext();
            if (next != null) next.lock();
            nd.next = cur;
            cur.prev = nd;
            cur.next = next;
            if (next != null) next.prev = cur;
        } finally {
            if (next != null) next.unlock();
            nd.unlock();
        }
    }
```

## Concurrent Insertions

![](/assets/img/concurrent-objects/InsertBoth.png){: width="100%"}

## Acquiring Locks

![](/assets/img/concurrent-objects/BothBlock.png){: width="100%"}

## Both Insert

![](/assets/img/concurrent-objects/BothInsert.png){: width="100%"}

## Both Release

![](/assets/img/concurrent-objects/BothRelease.png){: width="100%"}

## What Happens with Contention?

![](/assets/img/concurrent-objects/Contention.png){: width="100%"}

## Red Acquires Locks (Blue Waits)

![](/assets/img/concurrent-objects/ContentionRedLock.png){: width="100%"}

## Red Inserts & Releases Locks

![](/assets/img/concurrent-objects/ContentionRedInsert.png){: width="100%"}

## Blue Finally Acquires Locks

![](/assets/img/concurrent-objects/ContentionBlueLock.png){: width="100%"}

## Blue Inserts & Releases Locks

![](/assets/img/concurrent-objects/ContentionBlueInsert.png){: width="100%"}

## This Seems Pretty Good!

<!-- ## For Your Consideration -->

<!-- What happens if we allow node removal as well? What could go wrong? -->

<!-- <div style="margin-bottom: 12em"></div> -->

<!-- ## A Slightly Different Scenario -->

<!-- ![](/assets/img/concurrent-objects/CircularList.png){: width="50%"} -->

<!-- ## Question -->

<!-- Are multiple concurrent insertions guaranteed to eventually succeed? -->

<!-- ![](/assets/img/concurrent-objects/CircularList.png){: width="25%"} -->

<!-- <div style="margin-bottom: 8em"></div> -->

<!-- ## Another Question -->

<!-- Can this problem occur if list is not circular? -->

<!-- ```java -->
<!--     public void insert (Node nd, value) { -->
<!--         Node cur = new Node(value); -->
		
<!--         nd.lock(); -->
<!--         try { -->
<!--             Node next = nd.getNext(); -->
<!--             if (next != null) next.lock(); -->
<!--             nd.next = cur; -->
<!--             cur.prev = nd; -->
<!--             cur.next = next; -->
<!--             if (next != null) next.prev = cur; -->
<!--         } finally { -->
<!--             if (next != null) next.unlock(); -->
<!--             nd.unlock(); -->
<!--         } -->
<!--     } -->
<!-- ``` -->

<!-- <div style="margin-bottom: 4em"></div> -->


<!-- ## Morals -->

<!-- 1. Fine-grained locking is desireable -->
<!--     - some operations can be performed in parallel -->
<!-- 	- get more efficiency -->
<!-- 2. Fine-grained locking is subtle -->
<!-- 	- reasoning about multiple locks is delicate -->
<!--     - can achieve deadlock, even when no individual lock does! -->

<!-- Need to formalize what we *want* to achieve with concurrent access -->

<!-- ## Next Week -->

<!-- Formalizing what we want to achieve with concurrent -->

<!-- - Understanding what is (im)possible with concurrent objects -->
<!-- - Want best tradeoff between performance and correctness -->

