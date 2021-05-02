---
title: "Lecture 20: Lock-free and Bounded Queues"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 20: Lock-free and Bounded Queues 


## Last Time

1. Introduced `Pool` interface:
    - `void put(T item)`
    - `T get()`
2. Gave lock-based queue implementation
    - lock `enq` and `deq` *methods* (not nodes)

## Today

1. Finish up lock-based queue
2. Implement lock-free queue
3. Discuss a bounded queue

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

## Concurrent `enq`/`deq`

- Okay if queue is not empty
    + `head` and `tail` refer to different nodes
	+ no concurrent modification
- What if queue is empty?

<div style="margin-bottom: 8em"></div>

## Concurrent Subtlety

![](/assets/img/concurrent-queues/unbounded-conc.png){: width="100%"}

## Concurrent Subtlety

![](/assets/img/concurrent-queues/unbounded-conc.png){: width="50%"}

- `deq` checks for `head.next`
- `enq` updates `tail.next` (= `head.next`)

Who wins?

## Linearizing `enq()`

```java
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
```

<div style="margin-bottom: 6em"></div>

## Linearizing `deq()`

```java
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
```

<div style="margin-bottom: 6em"></div>

## Something to Consider

`enq` linearizes to
```java
tail.next = nd
```

- this happens before `tail` is updated

`deq` linearizes to
```java
head.next == null
```

- this happens before `head` is updated

So:

- `head` and `tail` don't always point to first (predecessor) and last items in the queue
  
Why is this not a problem?

## Why is this not a problem?

`head` and `tail` don't always point to first (predecessor) and last items in the queue?

Not a problem because

- Nodes lock `enq`/`deq` operations
- `tail`/`head` updated before lock released
- only `enq` cares about `tail` value
    + other enqueuers don't modify until after `tail` updated
- only `deq` cares about `head` value
    + other dequeuers don't modify until after `head` udpated
- crisis averted

## What is the Next Question?

...we've got a queue with locks...

## Can We Make a Lock-free Queue?

Use `AtomicReferences` for `head`/`tail`/`next`

- can atomically verify/update fields with `compareAndSet`
- e.g. for `enq`
    ```java
Node nd = new Node(item);
Node last = tail.get();
Node next = last.next.get();
if (next == null) {
    if (last.next.compareAndSet(next, node)) {
        tail.compareAndSet(last, node);	 
    }
}
	```

The subtelty

- Cannot modify both `tail` and `tail.get().next` atomically
- At some point, `tail` will not refer to last node in list
- Without locks, other threads will be able to see this!

## The Challenge

`enq` and `deq` must function properly even if:

1. `head` and `tail` don't point where they should
2. other `enq` and `deq` operations are in progress
    - partially complete method calls
	
<div style="margin-bottom: 8em"></div>
	
## An Idea

Clean up each others' messes!

- Call to `enq` can detect if `tail` isn't correct
	+ `tail.get().next != null`
- If this occurs, update tail:
    ```java
Node last = tail.get();
Node next = last.next.get();
if (next != null) {
    tail.compareAndSet(last, next);
}

    ```

Threads helping each other!

## `LockFreeQueue`

```java
public class LockFreeQueue<T> implements SimpleQueue<T> {
    private AtomicReference<Node> head;
    private AtomicReference<Node> tail;
    
    public LockFreeQueue() {
	Node sentinel = new Node(null);
	this.head = new AtomicReference<Node>(sentinel);
	this.tail = new AtomicReference<Node>(sentinel);
    }

    public void enq(T item) {...}

    public T deq() throws EmptyException {...}
    
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

## Lock-free Enqueue Method

```java
    public void enq(T item) {
	if (item == null) throw new NullPointerException();
	
	Node node = new Node(item);

	while (true) {
	    Node last = tail.get();
	    Node next = last.next.get();

	    if (last == tail.get()) {

		if (next == null) {

		    if (last.next.compareAndSet(next, node)) {
			tail.compareAndSet(last, node);
			return;
		    }
		    
		} else {
		    
		    tail.compareAndSet(last, next);
		    
		}
	    }
	}
    }
```

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

## Cool.

Let's test the implementations!

## Bounded Queues

- Both previous queues were unbounded
- Sometimes we want bounded queues:
    + have limited space
	+ want to force tighter synchronization between producers & consumers
	
How can we implement a bounded queue (with locks)?

<div style="margin-bottom: 8em"></div>

## One Option

Keep track of size!

- Start with our `BoundedQueue`
- Add a `final int capacity` field
- Add an `AtomicInteger size` field

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






