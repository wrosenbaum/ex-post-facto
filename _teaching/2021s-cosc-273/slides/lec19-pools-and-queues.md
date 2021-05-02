---
title: "Lecture 19: Pools and Queues"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 19: Pools and Queues

## Overview

1. Finishing Up Lists
2. Pools & Queues
3. A Blocking Queue
4. A Lock-free Queue

## Last Time: Lock-free linked list

Use `AtomicMarkableReference<Node>` for fields

- `mark` indicates logical removal

For `add`/`remove`:

1. Find location
2. Validate and modify
    + (first logically remove if `remove`)
	+ use `compareAndSet` to atomically
	    1. check that predecessor node has not been removed
		2. update `next` field of predecessor
		
For `contains`:

- Just traverse the list!

## Common Functionality

Traverse are remove:

1. Find `pred` and `curr` where `item` lives
2. Physically remove logically removed nodes along the way

Return: a `Window`

```java
    class Window {
	public Node pred;
	public Node curr;

	public Window (Node pred, Node curr) {
	    this.pred = pred;
	    this.curr = curr;
	}
    }
```

## Finding and Removing in Code

```java
    public Window find(Node head, long key) {
	Node pred = null;
	Node curr = null;
	Node succ = null;
	boolean[] marked = {false};
	boolean snip;

	retry: while(true) {
	    pred = head;
	    curr = pred.next.getReference();
	    while(true) {
		succ = curr.next.get(marked);
		while(marked[0]) {
		    snip = pred.next.compareAndSet(curr, succ, false, false);
		    if (!snip) continue retry;
		    curr = pred.next.getReference();
		    succ = curr.next.get(marked);
		}
		if (curr.key >= key) {
		    return new Window(pred, curr);
		}
		pred = curr;
		curr = succ;
	    }
	}
```

<div style="margin-bottom: 4em"></div>

## Removal in Code

```java
    public boolean remove(T item) {
	int key = item.hashCode();
	boolean snip;
	while (true) {
	    Window window = find(head, key);
	    Node pred = window.pred;
	    Node curr = window.curr;

	    if (curr.key != key) {
		return false;
	    }

	    Node succ = curr.next.getReference();
	    snip = curr.next.compareAndSet(succ, succ, false, true);
	    if (!snip) {
		continue;
	    }
	    pred.next.compareAndSet(curr, succ, false, false);
	    return true;
	}
    }
```

<div style="margin-bottom: 4em"></div>


## Test the Code


## Single Thread Performance

Runtimes: 1M Operations

| n elts | coarse | fine | optimistic | lazy | non-block |
|--------------|----------------|--------------|------------|------|------|
|10| 0.11 s | 0.14 s | 0.13 s| 0.11 s| 0.11 s |
|100| 0.14 s| 0.46 s | 0.19 s| 0.13 s| 0.20 s |
|1000| 1.1 s| 3.9 s | 2.2 s| 1.1 s| 2.8 s |
|10000| 28 s| 39 s | 59 s| 29 s | 66 s |

## Performance, 8 Threads

Runtimes: 1M Operations

| n elts | coarse | fine | optimistic | lazy | non-block |
|--------------|----------------|--------------|------------|------|-----|
|10| 0.21 s | 0.36 s | 0.33 s | 0.33 s | 0.21 s |
|100| 0.27 s | 1.80 s | 0.38 s | 0.12 s| 0.07 s |
|1000| 1.8 s | 4.7 s | 0.86 s | 0.19 s| 0.49 s |
|10,000| 32 s | 17 s | 9.2 s| 4.7 s | 9.2 s |

Note: fewer elements $\implies$ greater contention

## The Moral

Many ways of achieving correct behavior

Tradeoffs:

- Conceptual simplicity vs complexity/sophistication
- Best performance depends on usage case
    + very high contention: coarse locking is best
	    - simplest
	+ moderately high contention: non-blocking is best
	    - most sophisticated
		- hardest to reason about
		- easiest to mess up
	+ lower contention: lazy is best
	    - moderately sophisticated
	
## More Data Structures

Recently:

- Thread pools to simplify thread/task management

Questions:

- What is a pool?
- How are they implemented?

## A `Pool<T>` Interface

Want to model producer/consumer problem

- producers create things (e.g., tasks)
- consumers consume things (e.g., complete tasks)

A **pool** stores items between production and consumption:

```java
public interface Pool<T> {
    void put(T item); // add item to the pool
    T get();          // remove item from pool
}
```

## Pool Properties

1. Bounded vs unbounded
2. Fairness
    - FIFO queue
	- LIFO stack
	- Something else?
3. Method specifications
	- partial: threads wait for conditions
        + `get` from empty pool waits for a `put`
		+ `put` to full pool waits for a `get`
	- total: no calls wait for others
	- synchronous: e.g. every `put` call waits for `get` 

## Implementing a Pool

Can use a queue to implement a pool:

- `enq` implements `put`
- `deq` implements `get`
- LIFO priority

Two queue implementations today

1. Unbounded total queue (blocking)
2. Lock-free unbounded total queue

## Unbounded Total Queue (Blocking)

- Use linked list implementation of queue
- Store:
    + `Node head` sentinal
	    + `deq` returns `head.next` value (if any), updates `head`
	+ `Node tail`
	    + `enq` updates `tail.next`, updates `tail`
- Locks:
    + `enqLock` locks `enq` operation
	+ `deqLock` locks `deq` operation
	+ individual `Node`s are *not* locked

## Unbounded Queue in Pictures

![](/assets/img/concurrent-queues/unbounded-queue.png){: width="100%"}

## Dequeue 1: Aquire `deqLock`

![](/assets/img/concurrent-queues/unbounded-deq-1.png){: width="100%"}

## Dequeue 2: Get Element (or Exception)

![](/assets/img/concurrent-queues/unbounded-deq-2.png){: width="100%"}

## Dequeue 3: Update `head`

![](/assets/img/concurrent-queues/unbounded-deq-3.png){: width="100%"}

## Dequeue 4: Release Lock

![](/assets/img/concurrent-queues/unbounded-deq-4.png){: width="100%"}

## Enqueue 1: Make Node

![](/assets/img/concurrent-queues/unbounded-enq-1.png){: width="100%"}

## Enqueue 2: Acquire `enqLock`

![](/assets/img/concurrent-queues/unbounded-enq-2.png){: width="100%"}

## Enqueue 3: Update `tail.next`

![](/assets/img/concurrent-queues/unbounded-enq-3.png){: width="100%"}

## Enqueue 4: Update `tail`

![](/assets/img/concurrent-queues/unbounded-enq-4.png){: width="100%"}

## Enqueue 5: Release Lock

![](/assets/img/concurrent-queues/unbounded-enq-5.png){: width="100%"}

## `UnboundedQueue` in Code

```java
public class UnboundedQueue<T> implements SimpleQueue<T> {
    final ReentrantLock enqLock;
    final ReentrantLock deqLock;
    volatile Node head;
    volatile Node tail;

    public UnboundedQueue() {
	head = new Node(null);
	tail = head;
	enqLock = new ReentrantLock();
	deqLock = new ReentrantLock();
    }

    public T deq() throws EmptyException {
	T value;
	deqLock.lock();
	try {
	    if (head.next == null) {
		throw new EmptyException();
	    }
	    value = head.next.value;
	    head = head.next;
	} finally {
	    deqLock.unlock();
	}

	return value;
    }

    public void enq (T value) {
	enqLock.lock();
	try {
	    Node nd = new Node(value);
	    tail.next = nd;
	    tail = nd;
	} finally {
	    enqLock.unlock();
	}
    }
    
    class Node {
	final T value;
	volatile Node next;

	public Node (T value) {
	    this.value = value;
	}
    }
}
```

## Unbounded Logic

Easy to reason about:

- concurrent calls to `enq`
   + one acquires `enqLock`
   + others wait
- concurrent calls to `deq`
   + one acquires `deqLock`
   + others wait

What about concurrent calls to `enq`/`deq`?

- Ever an issue?

<div style="margin-bottom: 6em"></div>

<!-- ## Concurrent `enq`/`deq` -->

<!-- - Okay if queue is not empty -->
<!--     + `head` and `tail` refer to different nodes -->
<!-- 	+ no concurrent modification -->
<!-- - What if queue is empty? -->

<!-- <div style="margin-bottom: 8em"></div> -->

<!-- ## Concurrent Subtlety -->

<!-- ![](/assets/img/concurrent-queues/unbounded-conc.png){: width="100%"} -->

<!-- ## Concurrent Subtlety -->

<!-- ![](/assets/img/concurrent-queues/unbounded-conc.png){: width="50%"} -->

<!-- - `enq` checks for `head.next` -->
<!-- - `deq` updates `tail.next` (= `head.next`) -->

<!-- Who wins? -->

<!-- ## Linearizing `enq()` -->

<!-- ```java -->
<!--     public void enq (T value) { -->
<!-- 	enqLock.lock(); -->
<!-- 	try { -->
<!-- 	    Node nd = new Node(value); -->
<!-- 	    tail.next = nd; -->
<!-- 	    tail = nd; -->
<!-- 	} finally { -->
<!-- 	    enqLock.unlock(); -->
<!-- 	} -->
<!--     } -->
<!-- ``` -->

<!-- <div style="margin-bottom: 6em"></div> -->

<!-- ## Linearizing `deq()` -->

<!-- ```java -->
<!--     public T deq() throws EmptyException { -->
<!-- 	T value; -->
<!-- 	deqLock.lock(); -->
<!-- 	try { -->
<!-- 	    if (head.next == null) { -->
<!-- 		throw new EmptyException(); -->
<!-- 	    } -->
<!-- 	    value = head.next.value; -->
<!-- 	    head = head.next; -->
<!-- 	} finally { -->
<!-- 	    deqLock.unlock(); -->
<!-- 	} -->

<!-- 	return value; -->
<!--     } -->
<!-- ``` -->

<!-- <div style="margin-bottom: 6em"></div> -->

<!-- ## Something to Consider -->

<!-- `enq` linearizes to -->
<!-- ```java -->
<!-- tail.next = nd -->
<!-- ``` -->

<!-- - this happens before `tail` is updated -->

<!-- `deq` linearizes to -->
<!-- ```java -->
<!-- value = head.next.value; -->
<!-- ``` -->

<!-- - this happens before `head` is updated -->

<!-- So: -->

<!-- - `head` and `tail` don't always point to first (predecessor) and last items in the queue -->
  
<!-- Why is this not a problem? -->

<!-- ## Why is this not a problem? -->

<!-- `head` and `tail` don't always point to first (predecessor) and last items in the queue? -->

<!-- Not a problem because -->

<!-- - Nodes lock `enq`/`deq` operations -->
<!-- - `tail`/`head` updated before lock released -->
<!-- - only `enq` cares about `tail` value -->
<!--     + other enqueuers don't modify until after `tail` updated -->
<!-- - only `deq` cares about `head` value -->
<!--     + other dequeuers don't modify until after `head` udpated -->
<!-- - crisis averted -->

<!-- ## What is the Next Question? -->

<!-- ...we've got a queue with locks... -->

<!-- ## Can We Make a Lock-free Queue? -->

<!-- Use `AtomicReferences` for `head`/`tail`/`next` -->

<!-- - can atomically verify/update fields with `compareAndSet` -->
<!-- - e.g. for `enq` -->
<!--     ``` -->
<!-- Node nd = new Node(item); -->
<!-- Node last = tail.get(); -->
<!-- Node next = last.next.get(); -->
<!-- if (next == null) { -->
<!--     last.next.compareAndSet(next, node); -->
<!-- } -->
<!-- 	``` -->

<!-- The subtelty -->

<!-- - Cannot modify both `tail` and `tail.get().next` atomically -->
<!-- - At some point, `tail` will not refer to last node in list -->
<!-- - Without locks, other threads will be able to see this! -->

<!-- ## The Challenge -->

<!-- `enq` and `deq` must function properly even if: -->

<!-- 1. `head` and `tail` don't point where they should -->
<!-- 2. other `enq` and `deq` operations are in progress -->
<!--     - partially complete method calls -->
	
<!-- <div style="margin-bottom: 8em"></div> -->
	
<!-- ## An Idea -->

<!-- Clean up each others' messes! -->

<!-- - Call to `enq` can detect if `tail` isn't correct -->
<!-- 	+ `tail.get().next != null` -->
<!-- - If this occurs, update tail: -->
<!--     ```java -->
<!-- Node last = tail.get(); -->
<!-- Node next = last.next.get(); -->
<!-- if (next != null) { -->
<!--     tail.compareAndSet(last, next); -->
<!-- } -->

<!--     ``` -->

<!-- Threads helping each other! -->

<!-- ## `LockFreeQueue` -->

<!-- ```java -->
<!-- public class LockFreeQueue<T> implements SimpleQueue<T> { -->
<!--     private AtomicReference<Node> head; -->
<!--     private AtomicReference<Node> tail; -->
    
<!--     public LockFreeQueue() { -->
<!-- 	Node sentinel = new Node(null); -->
<!-- 	this.head = new AtomicReference<Node>(sentinel); -->
<!-- 	this.tail = new AtomicReference<Node>(sentinel); -->
<!--     } -->

<!--     public void enq(T item) {...} -->

<!--     public T deq() throws EmptyException {...} -->
    
<!--     class Node { -->
<!-- 	public T value; -->
<!-- 	public AtomicReference<Node> next; -->
    
<!-- 	public Node(T value) { -->
<!-- 	    this.value = value; -->
<!-- 	    this.next  = new AtomicReference<Node>(null); -->
<!-- 	} -->
<!--     }     -->
<!-- } -->
<!-- ``` -->

<!-- ## Lock-free Enqueue Method -->

<!-- ```java -->
<!--     public void enq(T item) { -->
<!-- 	if (item == null) throw new NullPointerException(); -->
	
<!-- 	Node node = new Node(item);  -->

<!-- 	while (true) {		  -->
<!-- 	    Node last = tail.get();     -->
<!-- 	    Node next = last.next.get();  -->

<!-- 	    if (last == tail.get()) { -->

<!-- 		if (next == null) { -->

<!-- 		    if (last.next.compareAndSet(next, node)) { -->
<!-- 			tail.compareAndSet(last, node); -->
<!-- 			return; -->
<!-- 		    } -->
		    
<!-- 		} else { -->
		    
<!-- 		    tail.compareAndSet(last, next); -->
		    
<!-- 		} -->
<!-- 	    } -->
<!-- 	} -->
<!--     } -->
<!-- ``` -->

<!-- ## Lock-free Dequeue Method -->

<!-- ```java -->
<!--     public T deq() throws EmptyException { -->
	
<!-- 	while (true) { -->
	    
<!-- 	    Node first = head.get(); -->
<!-- 	    Node last = tail.get(); -->
<!-- 	    Node next = first.next.get(); -->
	    
<!-- 	    if (first == head.get()) { -->
<!-- 		if (first == last) {   -->
<!-- 		    if (next == null) {	 -->
<!-- 			throw new EmptyException(); -->
<!-- 		    } -->
		    
<!-- 		    tail.compareAndSet(last, next); -->
<!-- 		} else { -->
<!-- 		    T value = next.value;  -->
<!-- 		    if (head.compareAndSet(first, next)) -->
<!-- 			return value; -->
<!-- 		} -->
<!-- 	    } -->
<!-- 	} -->
<!--     } -->
<!-- ``` -->

<!-- ## Cool. -->

<!-- Let's test the implementations! -->
	
	
