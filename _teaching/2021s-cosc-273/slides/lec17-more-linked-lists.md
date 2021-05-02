--
title: "Lecture 17: More Linked Lists"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 17: More Linked Lists

## Overview

1. Review: Coarse- and Fine-grained Lists
2. Optimistic List
3. Lazy List

## Announcements

1. Comments on proposals posted
    - see shared Google drive
	- please consider/respond to comments
2. **forthcoming lab/hw assignments are optional**
    - can be submitted by end of finals week
	- lowest grades dropped
3. Quizzes weeks of 4/26, 5/3, 5/10, 5/17
    - no comprehensive exam
4. Final project steps
    - Proof of concept due 5/7
	- Short video due 5/19
	- Final submission due 5/28

## Last Time

A `Set` of elements:

- store a collection of *distinct* elements
- `add` an element
    + no effect if element already there
- `remove` an element 
    + no effect if not present
- check if set `contains` an element

## An Interface

```java
public interface SimpleSet<T> {
    /*
     * Add an element to the SimpleSet. Returns true if the element
     * was not already in the set.
     */
    boolean add(T x);

    /*
     * Remove an element from the SimpleSet. Returns true if the
     * element was previously in the set.
     */
    boolean remove(T x);

    /*
     * Test if a given element is contained in the set.
     */
    boolean contains(T x);
}
```

## Coarse-grained Locking

One lock for whole data structure

For any operation:

1. Lock entire list
2. Perform operation
3. Unlock list

## Coarse-grained Insertion

![](/assets/img/concurrent-lists/coarse-list.png){: width="100%"}

## Step 1: Acquire Lock

![](/assets/img/concurrent-lists/coarse-list-lock.png){: width="100%"}

## Step 2: Iterate to Find Location

![](/assets/img/concurrent-lists/coarse-list-insert-1.png){: width="100%"}

## Step 2: Iterate to Find Location

![](/assets/img/concurrent-lists/coarse-list-insert-2.png){: width="100%"}

## Step 2: Iterate to Find Location

![](/assets/img/concurrent-lists/coarse-list-insert-3.png){: width="100%"}

## Step 3: Insert Item

![](/assets/img/concurrent-lists/coarse-list-insert-4.png){: width="100%"}

## Step 4: Unlock List

![](/assets/img/concurrent-lists/coarse-list-insert-5.png){: width="100%"}

## Coarse-grained Appraisal

Advantages:

- Easy to reason about
- Easy to implement

Disadvantages:

- No parallelism
- All operations are blocking

## Fine-grained Locking

One lock per node

For any operation: 

1. Lock head and its next
2. Hand-over-hand locking while searching
    - always hold at least one lock
3. Perform operation
4. Release locks

## A Fine-grained Insertion

![](/assets/img/concurrent-lists/fine-list.png){: width="100%"}

## Step 1: Lock Initial Nodes

![](/assets/img/concurrent-lists/fine-list-insert-1.png){: width="100%"}

## Step 2: Hand-over-hand Locking

![](/assets/img/concurrent-lists/fine-list-insert-1.png){: width="100%"}

## Step 2: Hand-over-hand Locking

![](/assets/img/concurrent-lists/fine-list-insert-2.png){: width="100%"}

## Step 2: Hand-over-hand Locking

![](/assets/img/concurrent-lists/fine-list-insert-3.png){: width="100%"}

## Step 3: Perform Insertion

![](/assets/img/concurrent-lists/fine-list-insert-4.png){: width="100%"}

## Step 4: Unlock Nodes

![](/assets/img/concurrent-lists/fine-list-insert-5.png){: width="100%"}

## An Advantage: Parallel Access

![](/assets/img/concurrent-lists/fine-list-concurrent-1.png){: width="100%"}

## An Advantage: Parallel Access

![](/assets/img/concurrent-lists/fine-list-concurrent-2.png){: width="100%"}

## An Advantage: Parallel Access

![](/assets/img/concurrent-lists/fine-list-concurrent-3.png){: width="100%"}

## An Advantage: Parallel Access

![](/assets/img/concurrent-lists/fine-list-concurrent-4.png){: width="100%"}

## An Advantage: Parallel Access

![](/assets/img/concurrent-lists/fine-list-concurrent-5.png){: width="100%"}

## An Advantage: Parallel Access

![](/assets/img/concurrent-lists/fine-list-concurrent-6.png){: width="100%"}

## An Advantage: Parallel Access

![](/assets/img/concurrent-lists/fine-list-concurrent-7.png){: width="100%"}

## An Advantage: Parallel Access

![](/assets/img/concurrent-lists/fine-list-concurrent-8.png){: width="100%"}

## An Advantage: Parallel Access

![](/assets/img/concurrent-lists/fine-list-concurrent-9.png){: width="100%"}

## An Advantage: Parallel Access

![](/assets/img/concurrent-lists/fine-list-concurrent-10.png){: width="100%"}

## An Advantage: Parallel Access

![](/assets/img/concurrent-lists/fine-list-concurrent-11.png){: width="100%"}

## An Advantage: Parallel Access

![](/assets/img/concurrent-lists/fine-list-concurrent-12.png){: width="100%"}

## Fine-grained Appraisal

Advantages:

- Parallel access
- Reasonably simple implementation

Disadvantages:

- More locking overhead
    + can be much slower than coarse-grained 
- All operations are blocking 

## Optimistic Synchronization

Fine-grained wastes resources locking

- Nodes are locked when traversed
- Locked even if not modified!

A better procedure?

1. Traverse without locking
2. Lock relevant nodes
3. Perform operation
4. Unlock nodes

## A Better Way?

![](/assets/img/concurrent-lists/optimistic-list.png){: width="100%"}

## A Better Way?

![](/assets/img/concurrent-lists/optimistic-insert-1.png){: width="100%"}

## A Better Way?

![](/assets/img/concurrent-lists/optimistic-insert-2.png){: width="100%"}

## A Better Way?

![](/assets/img/concurrent-lists/optimistic-insert-3.png){: width="100%"}

## A Better Way?

![](/assets/img/concurrent-lists/optimistic-insert-4.png){: width="100%"}

## A Better Way?

![](/assets/img/concurrent-lists/optimistic-insert-8.png){: width="100%"}

## What Could Go Wrong?

<div style="margin-bottom: 18em"></div>

## An Issue!

Between traversing and locking

- Another thread modifies the list
- Now locked nodes aren't the right nodes!

## An Issue, Illustrated

![](/assets/img/concurrent-lists/optimistic-insert-3.png){: width="100%"}

## An Issue, Illustrated

![](/assets/img/concurrent-lists/optimistic-issue-4.png){: width="100%"}

## An Issue, Illustrated

![](/assets/img/concurrent-lists/optimistic-issue-5.png){: width="100%"}

## An Issue, Illustrated

![](/assets/img/concurrent-lists/optimistic-issue-6.png){: width="100%"}

## An Issue, Illustrated

![](/assets/img/concurrent-lists/optimistic-issue-8.png){: width="100%"}

## An Issue, Illustrated

![](/assets/img/concurrent-lists/optimistic-issue-9.png){: width="100%"}

## How can we Address this Issue?

<div style="margin-bottom: 18em"></div>

## Optimistic Synchronization, Validated

1. Traverse without locking
2. Lock relevant nodes
3. **Validate list**
    - if validation fails, go back to Step 1
4. Perform operation
5. Unlock nodes

## How do we Validate?

*After* locking, ensure that:

1. `pred` is reachable from `head`
2. `curr` is `pred`'s successor

If these conditions aren't met:

- Start over!

## Optimistic Insertion

![](/assets/img/concurrent-lists/optimistic-list.png){: width="100%"}

## Step 1: Traverse the List

![](/assets/img/concurrent-lists/optimistic-insert-1.png){: width="100%"}

## Step 1: Traverse the List

![](/assets/img/concurrent-lists/optimistic-insert-2.png){: width="100%"}

## Step 1: Traverse the List

![](/assets/img/concurrent-lists/optimistic-insert-3.png){: width="100%"}

## Step 2: Acquire Locks

![](/assets/img/concurrent-lists/optimistic-insert-4.png){: width="100%"}

## Step 3: Validate List - Traverse

![](/assets/img/concurrent-lists/optimistic-insert-5.png){: width="100%"}

## Step 3: Validate List - `pred` Reachable?

![](/assets/img/concurrent-lists/optimistic-insert-6.png){: width="100%"}

## Step 3: Validate List - Is `curr` next?

![](/assets/img/concurrent-lists/optimistic-insert-7.png){: width="100%"}

## Step 4: Perform Insertion

![](/assets/img/concurrent-lists/optimistic-insert-8.png){: width="100%"}

## Step 5: Release Locks

![](/assets/img/concurrent-lists/optimistic-insert-9.png){: width="100%"}

## Implementing Validation

```java
    private boolean validate (Node pred, Node curr) {
	Node node = head;
	while (node.key <= pred.key) {
	    if (node == pred) {
		return pred.next == curr;
	    }

	    node = node.next;
	}

	return false;
    }
```

## Question

Under what conditions might optimistic synchronization be fast/slow?

<div style="margin-bottom: 18em"></div>

## Testing Optimistic Synchronization

## Optimistic Appraisal

Advantages:

- Less locking than fine-grained
- More opportunities for parallelism than coarse-grained

Disadvantages:

- Validation could fail
- Not starvation-free
    + even if locks are starvation-free

## So Far

All operations have been *blocking*

- Each method call locks a portion of the data structre
- Method calls lock out other calls
- True even for `contains()` calls 
    + doesn't modify the data structure
	
## Observation

Operations are complicated because they consist of several steps

- hard to reason about *when* the operation appears to take place
- coarse/fine-grained synchronization stop other threads from seeing operations "in progress"
- optimistic synchronization may encounter "in progress" operations before locking
    + validation required
	
## Lazy Synchronization

- **Mark** a node before physical removal
   + marked nodes are *logically removed*, still physically present
- Only marked nodes are ever removed

Validation simplified:

- Just check if nodes are marked
- No need to traverse whole list!

## Lazy Operation

1. Traverse without locking
2. Lock relevant nodes
3. Validate list
    - check nodes are
	    + not marked
		+ correct relationship
    - if validation fails, go back to Step 1
4. Perform operation
    - for removal, mark node first
5. Unlock nodes


## Lazy Removal Illustrated

![](/assets/img/concurrent-lists/lazy-list.png){: width="100%"}

## Step 1: Traverse List

![](/assets/img/concurrent-lists/lazy-list-remove-1.png){: width="100%"}

## Step 1: Traverse List

![](/assets/img/concurrent-lists/lazy-list-remove-2.png){: width="100%"}

## Step 2: Lock Nodes

![](/assets/img/concurrent-lists/lazy-list-remove-3.png){: width="100%"}

## Step 3: Validate `pred.next == curr`?

![](/assets/img/concurrent-lists/lazy-list-remove-4.png){: width="100%"}

## Step 3: Validate not marked?

![](/assets/img/concurrent-lists/lazy-list-remove-4.png){: width="100%"}

## Step 4a: Perform Logical Removal

![](/assets/img/concurrent-lists/lazy-list-remove-5.png){: width="100%"}

## Step 4b: Perform Physical Removal

![](/assets/img/concurrent-lists/lazy-list-remove-6.png){: width="100%"}

## Step 5: Release Locks and Done!

![](/assets/img/concurrent-lists/lazy-list-remove-7.png){: width="100%"}

## A `Node` in Code

```java
    private class Node {
	T item;
	int key;
	Node next;
	Lock lock;
	volatile boolean marked;

	public Node (int key) {
	    this.item = null;
	    this.key = key;
	    this.next = null;
	    this.lock = new ReentrantLock();
	    this.marked = false;
	}
	
	public Node (T item) {
	    this.item = item;
	    this.key = item.hashCode();
	    this.next = null;
	    this.lock = new ReentrantLock();
	}

	public void lock () {
	    lock.lock();
	}

	public void unlock () {
	    lock.unlock();
	}
	
    }
```

## Validation, Simplified

```java
    private boolean validate (Node pred, Node curr) {
	return !pred.marked && !curr.marked && pred.next == curr;
    }
```

## Improvements?

1. Limited locking as in optimistic synchronization
2. Simpler validation
    - faster---no list traversal
    - more likely to succeed?
3. Logical removal easier to reason about
    - linearization point at logical removal line
4. `contains()` no longer acquires locks
    - often most frequent operation
	- now it is wait-free!
	
## Wait-free Containment

```java
    public boolean contains (T item) {
	int key = item.hashCode();
	Node curr = head;
	while (curr.key < key) {
	    curr = curr.next;
	}

	return curr.key == key && !curr.marked;
    }
```

## Testing Performance!

## Lazy Appraisal

Advantages:

- Less locking than fine-grained
- More opportunities for parallelism than coarse-grained
- Simpler validataion than optimistic
- Wait-free `contains` method

Disadvantages:

- Validation could still fail (though maybe less likely)
- Not starvation-free
    + even if locks are starvation-free
- `add` and `remove` still blocking

## What's next

Can we make *all* of the operations wait-free?

- A concurrent list without locks?

## As Before

To get correctness without locks we need atomics!

- `AtomicMarkableReference<T>`
- Stores 
    1. a reference to a `T`
	2. a boolean `marked`
- Atomic operations
    1. `boolean compareAndSet(T expectedRef, T newRef, boolean expectedMark, boolean newMark)`
	2. `T get(boolean[] marked)`
	3. `T getReference()`
	4. `boolean isMarked()`

## Nonblocking List Idea

Similar to LazyList

- use `AtomicMarkableReference` to mark and modify references simultaneously
- modifications done by `LazyList` can be done atomically!
- `add` and `remove` are lock-free
- `contains` is wait-free (hence also lock-free)

## Details Next Time
