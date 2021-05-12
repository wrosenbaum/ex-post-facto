---
title: "Lecture 21: A Bounded Queue"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 21: A Bounded Queue

## Reminder

Proof of Concept for final project **due this Friday.**

## Overview

1. Testing Previous Queues
2. A Bounded Queue
3. Stacks

## Previously

1. Unbounded Queue with Locks
    - lock `enq` and `deq` methods
2. Lock-free Unbounded Queue
    - use `AtomicReference` for `head`, `tail`, `next`
	- use `compareAndSet` to validate & update atomically
	- update `head`/`tail` proactively
	    + guard against incomplete `deq`/`enq`

## Lock-free Dequeue Method

```java
    public T deq() throws EmptyException {
	
	while (true) {
	    
	    Node first = head.get();
	    Node last = tail.get();
	    Node next = first.next.get();
	    
	    if (first == head.get()) {
		if (first == last) {
		    if (next == null) {
			throw new EmptyException();
		    }
		    
		    tail.compareAndSet(last, next);
		} else {
		    T value = next.value;
		    if (head.compareAndSet(first, next))
			return value;
		}
	    }
	}
    }
```

## Testing our Queues

## Bounded Queues

- Both previous queues were unbounded
- Sometimes we want bounded queues:
    + have limited space
	+ want to force tighter synchronization between producers & consumers
	
How can we implement a bounded queue (with locks)?

<div style="margin-bottom: 8em"></div>

## One Option

Keep track of size!

- Start with our `UnboundedQueue`
    + lock `enq` and `deq` methods
- Add a `final int capacity` field
- Add an `AtomicInteger size` field
    + increment upon `enq`
	+ decrement upon `deq`
- Make sure `size <= capacity`

Why should `size` be atomic?

<div style="margin-bottom: 8em"></div>

## Enqueue Method

1. acquires `enqLock`
2. if `size` is less than capacity
    - enqueue item
	- increment size
	- release lock
3. otherwise
	- throw exception? (total method)
	- wait until `size < capacity`? (partial method)
		
## Dequeue Method

1. acquires `deqLock`
2. if `size` is greater than `0`
    - dequeue item
	- decrement size
	- release lock
3. otherwise
    - throw exception? (total method)
	- wait until `size > 0`? (partial method)
			
## An Unexceptional Queue

Suppose we don't want to throw exceptions

- Full/empty queue operations are expected, not exceptional
- Queue should handle these cases by having threads wait

Question: How might we implement this behavior?

<div style="margin-bottom: 8em"></div>

## Enqueue with Waiting

```java
    public void enq (T value) {
	enqLock.lock();
	try {
	    Node nd = new Node(value);
            while (size.get() == capacity) { }; // wait until not full
	    tail.next = nd;
	    tail = nd;
	} finally {
	    enqLock.unlock();
	}
    }
```

## A Problem?

This is wasteful!

```java
while (size.get() == capacity) { }; // wait until not full
```

The thread:

1. Acquires lock
2. Fails to enqueue while holding lock
3. Uses resources to repeatedly check condition `size.get() == capacity`

What if it takes a while until the queue is not full?

## A More Prudent Way

The following would be better:

1. enqueuer sees that `size.get() == capacity`
2. enqueuer temporarily gives up lock
3. enqueuer passively waits for the *condition* that `size.get() < capacity`
    + not constantly checking
4. dequeuer sees that enqueuers are waiting
5. after dequeue, dequeuer notifies waiting enqueuers
6. enqueuer acquires lock, enqueues

## A Waiting Room Analogy

![](/assets/img/concurrent-queues/bounded-queue.png){: width="100%"}

## Queue Full

![](/assets/img/concurrent-queues/bounded-queue.png){: width="100%"}

## Enqueuer Arrives, Acquires Lock

![](/assets/img/concurrent-queues/bounded-enq-1.png){: width="100%"}

## Enqueuer Sees Full, Waits

![](/assets/img/concurrent-queues/bounded-enq-2.png){: width="100%"}

## Enqueuer Arrives, Acquires Lock

![](/assets/img/concurrent-queues/bounded-enq-3.png){: width="100%"}

## Enqueuer Sees Full, Waits

![](/assets/img/concurrent-queues/bounded-enq-4.png){: width="100%"}

## Dequeuer Arrives, Sees Full, Dequeues

![](/assets/img/concurrent-queues/bounded-enq-5.png){: width="100%"}

## Dequeuer Announces No Longer Full

![](/assets/img/concurrent-queues/bounded-enq-6.png){: width="100%"}

## Enqueuers Leaving Waiting Room

![](/assets/img/concurrent-queues/bounded-enq-7.png){: width="100%"}

## Dequeuer Releases Lock

![](/assets/img/concurrent-queues/bounded-enq-8.png){: width="100%"}

## Enqueuer Locks; Enqueue Ensues

![](/assets/img/concurrent-queues/bounded-enq-9.png){: width="100%"}



## The `Lock` and `Condition` Interfaces

The `Lock` interface defines a curious method:

- `Condition newCondition()` returns a new Condition instance that is bound to this Lock instance

The `Condition` interface

- `void await()` causes the current thread to wait until it is signalled or interrupted
- `void	signal()` wakes up one waiting thread
- `void	signalAll()` wakes up all waiting threads

## Improving our Queue

Define condition for `enqLock`

- `notFullCondition` 

When enqueuing to a full queue

- thread signals that it is waiting
    + need a flag (`volatile boolean`) for this
- thread calls `notFullCondition.await()`
    + waits until `notFullCondition` is satisfied

## When Dequeueing

When dequeueing 

- thread checks if thread is waiting
    + checks flag for this
- if so, after dequeue, dequeuer calls `notFullCondition.signalAll()`
    + signals that queue is no longer full

(Similar: `notEmptyCondition` for `deq` method)

## Making a `BoundedQueue`

```java
public class BoundedQueue<T> implements SimpleQueue<T> {
    ReentrantLock enqLock, deqLock;
    Condition notEmptyCondition, notFullCondition;
    AtomicInteger size;
    volatile Node head, tail;
    final int capacity;

    public BoundedQueue(int capacity) {
	this.capacity = capacity;
	this.head = new Node(null);
	this.tail = this.head;
	this.size = new AtomicInteger(0);
	this.enqLock = new ReentrantLock();
	this.notFullCondition = this.enqLock.newCondition();
	this.deqLock = new ReentrantLock();
	this.notEmptyCondition = this.deqLock.newCondition();
    }

    public void enq(T item) { ... }

    public T deq() { ... }

    class Node { ... }
}
```

## Enqueueing

```java
    public void enq(T item) {
	boolean mustWakeDequeuers = false;
	Node nd = new Node(item);
	enqLock.lock();
	try {
	    while (size.get() == capacity) {
		try {
		    // System.out.println("Queue full!");
		    notFullCondition.await();
		} catch (InterruptedException e) {
		    // do nothing
		}
	    }
	    
	    tail.next = nd;
	    tail = nd;
	    
	    if (size.getAndIncrement() == 0) {
		mustWakeDequeuers = true;
	    }
	    
	} finally {
	    
	    enqLock.unlock();
	    
	}

	if (mustWakeDequeuers) {
	    
	    deqLock.lock();
	    
	    try {
		notEmptyCondition.signalAll();
	    } finally {
		deqLock.unlock();
	    }
	}
    }
```

## Dequeueing

```java
    public T deq() {
	T item;
	boolean mustWakeEnqueuers = false;
	deqLock.lock();
	try {

	    while (head.next == null) {
		try {
		    // System.out.println("Queue empty!");
		    notEmptyCondition.await();
		} catch(InterruptedException e) {
		    //do nothing
		}
	    }
	    
	    item = head.next.item;
	    head = head.next;

	    if (size.getAndDecrement() == capacity) {
		mustWakeEnqueuers = true;
	    }
	} finally {
	    deqLock.unlock();
	}

	if (mustWakeEnqueuers) {
	    enqLock.lock();
	    try {
		notFullCondition.signalAll();
	    } finally {
		enqLock.unlock();
	    }
	}

	return item;
    }	
```

## Testing the Queue

## For Your Consideration

What if we want to make a lock-free bounded queue?

- Can we just add `size` and `capacity` fields to our `LockFreeQueue`?

# Stacks

## Recall the Stack

Basic operations

- `void push(T item)` add a new item to the top of the stack
- `T pop()` remove top item from the stack and return it
    + throw `EmptyException` if stack was empty

## Linked List Implementation

![](/assets/img/stacks/list-stack.png){: width="100%"}

## `push()` Step 1: Create Node

![](/assets/img/stacks/list-stack-push-1.png){: width="100%"}

## `push()` Step 2: Set `next`

![](/assets/img/stacks/list-stack-push-2.png){: width="100%"}

## `push()` Step 3: Set `head`

![](/assets/img/stacks/list-stack-push-3.png){: width="100%"}

## `push()` Complete

![](/assets/img/stacks/list-stack-push-4.png){: width="100%"}

## `pop()`?

![](/assets/img/stacks/list-stack-push-4.png){: width="100%"}

## `pop()` Step 1: Store `value`

![](/assets/img/stacks/list-stack-pop-1.png){: width="100%"}

## `pop()` Step 2: Update `head`

![](/assets/img/stacks/list-stack-pop-2.png){: width="100%"}

## `pop()` Step 3: Return `value`

![](/assets/img/stacks/list-stack-pop-3.png){: width="100%"}

## Concurrent Stack

With locks:

- Since all operations modify `head`, coarse locking is natural choice

Without locks?

<div style="margin-bottom: 12em"></div>

## A Lock-free Stack

Use linked-list implementation

- Logic is simpler than queues' because all operations affect same node
- Idea:
    + store `top` as an `AtomicReference<Node>`
	+ use `compareAndSet` to modify `top`
	    - success, or retry
- Unlike queue:
    + item on top of stack precisely when `top` points to item's `Node`

## Implementing the Lock-Free Stack

```java
public class LockFreeStack<T> implements SimpleStack<T> {
    AtomicReference<Node> top = new AtomicReference<Node>(null);

    public void push(T item) {...}

    public T pop() throws EmptyException {...}
    
    class Node {
	public T value;
	public AtomicReference<Node> next;
    
	public Node(T value) {
	    this.value = value;
	    this.next  = new AtomicReference<Node>(null);
	}
    }    

}
```

## Implementing `push`

```java
    public void push(T item) {
	
	Node nd = new Node(item);
	Node oldTop = top.get();
	nd.next.set(oldTop);
	
	while (!top.compareAndSet(oldTop, nd)) {
	    oldTop = top.get();
	    nd.next.set(oldTop);	    
	}
    }
```

## Implementing `pop`

```java
    public T pop() throws EmptyException {
	while (true) {
	    Node oldTop = top.get();
	    
	    if (oldTop == null) {
		throw new EmptyException();
	    }

	    Node newTop = oldTop.next.get();

	    if (top.compareAndSet(oldTop, newTop)) {
		return oldTop.value;
	    }
	}
    }
```

## Sequential Bottleneck 

Modifying `top`

- No matter how many threads, `push`/`pop` rate limited by `top.compareAndSet(...)`
- This seems inherent to any stack...

<div style="margin-bottom: 8em"></div>

... or is it?


## Elimination

Consider several concurrent accesses to a stack:

- T1 calls `stk.push(item1)`
- T2 calls `stk.push(item2)`
- T3 calls `stk.pop()`
- T4 calls `stk.push(item4)`
- T5 calls `stk.pop()`
- T6 calls `stk.pop()`

**Trick Question.** What is the state of `stk` after these calls?

<div style="margin-bottom: 8em"></div>

## What do we need the stack for?

Match and exchange!

<div style="margin-bottom: 16em"></div>

Cut out the middleperson!

## A Different Strategy

1. Attempt to `push`/`pop` to stack
   + if success, good job
2. If attempt fails, try to find a partner
   + if `push`, try to find a `pop` and give them your value
   + if `pop`, try to find a `push` and take their value
   
Next time: implement an exchange object to facilitate this





