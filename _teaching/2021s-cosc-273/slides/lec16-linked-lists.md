---
title: "Lecture 16: Concurrent Linked Lists"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 16: Concurrent Linked Lists

## Overview

1. Lab 04 Tips
2. `Set` as a Linked List
3. Synchronization Philosophies
4. Coarse-grained Linked List
5. Fine-grained Linked List
6. Optimistic Linked List

# Lab 04 Tips

## Prime Tasks

Suggested structure:

1. Compute small primes (up to `Math.sqrt(MAX)`)
    + use `Primes.getPrimesUpTo(int max)`
2. Use small primes and SoE to find rest of primes
    + consider a block `[n, n + k]` of numbers from `n` to `n + k`
	+ for each small prime `p`, remove multiples of `p` from `[n, n + k]`
	+ remaining numbers are prime
3. Write primes from step 2 in order to `primes` array

Have a dedicated thread for step 3; use several threads to perform step 2 in parallel

## Prime Tasks in Pictures

<div style="margin-bottom: 18em"></div>

## Performance Optimizations

1. Use reasonably small objects
    + several smaller tasks/arrays are better than few larger ones
	+ arrays should fit in low-level cache
2. Minimize sharing of objects
    + each object should be written by one thread, and read by at most one other thread
	+ think of "passing" completed tasks from producer to consumer
	+ limit concurrent access

## A `Set`

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

## Implementing `SimpleSet`

Store elements in a linked list of nodes

- Each `Node` stores:
    + reference to the stored object
	+ reference to the next `Node`
	+ a *key* associated with the object
	    + use hash code of object
		+ keys can be sorted

- The list stores
    + reference to `head` node
	+ a `tail` node
	+ `head` and `tail` have min and max key values

## Our Goals

1. Correctness, safety, liveness
    - linearizability
	- deadlock-freedom
	- starvation-freedom?
	- nonblocking???
2. Performance
    - parallelism?

# Synchronization Philosophies

## Synchronization Philosophies

1. Coarse-Grained
    - lock whole data structure for every operation
2. Fine-Grained
    - only lock what is needed to avoid disaster
3. Optimisitc
    - don't lock anything to read
	- lock to modify
4. Lazy
    - use "logical" removal
	- only use locks occasionally
5. Nonblocking
    - use atomics, not locks!

# Coarse-grained Linked List

## Coarse-grained Linked List

One lock for whole data structure

For any operation:

1. Lock entire list
2. Perform operation
3. Unlock list

## Coarse-grained List Figure

![](/assets/img/concurrent-lists/coarse-list.png){: width="100%"}

## Add `item` with Key `5`

![](/assets/img/concurrent-lists/coarse-list.png){: width="100%"}

## Step 1: Lock List

![](/assets/img/concurrent-lists/coarse-list.png){: width="100%"}

## Step 2: Find Correct Location

![](/assets/img/concurrent-lists/coarse-list.png){: width="100%"}

## Step 3: Do Insertion

![](/assets/img/concurrent-lists/coarse-list.png){: width="100%"}

## Step 4: Unlock List

![](/assets/img/concurrent-lists/coarse-list.png){: width="100%"}

## In Code

- Look at implementation
- Test performance
- This is our baseline!

# Fine-grained Linked List

## Fine-grained Linked List

One lock per node

For any operation: 

1. Store `curr`, `pred` (initialized to head)
    - lock `pred`
2. Hand-over-hand locking:
	- lock `curr = pred.next`
	- unlock `pred`
	- set `pred = curr`
    - repeat until correct location found
3. Perform operation
4. Release locks

## Fine-grained List Figure

![](/assets/img/concurrent-lists/fine-list.png){: width="100%"}

## Add `item` with Key `5`

![](/assets/img/concurrent-lists/fine-list.png){: width="100%"}

## Step 1: Set and Lock `pred`

![](/assets/img/concurrent-lists/fine-list.png){: width="100%"}

## Step 2: Hand over Hand Locking

![](/assets/img/concurrent-lists/fine-list.png){: width="100%"}

## Step 3: Perform Insert

![](/assets/img/concurrent-lists/fine-list.png){: width="100%"}

## Step 4: Release Locks

![](/assets/img/concurrent-lists/fine-list.png){: width="100%"}

## Test

- More efficient than coarse?

<div style="margin-bottom: 4em"></div>

- Why or why not?

<div style="margin-bottom: 4em"></div>

- What affects performance?

<div style="margin-bottom: 4em"></div>

# Optimistic Linked List

## Optimistic Linked List

One lock per node

For any operation:

1. Find location
2. Acquire locks
3. Validate location
    - go back to 1 if this fails!
4. Perform operation
5. Release locks

## Optimistic List Figure

![](/assets/img/concurrent-lists/optimistic-list.png){: width="100%"}

## Add `item` with Key `5`

![](/assets/img/concurrent-lists/optimistic-list.png){: width="100%"}

## Step 1: Find Location

![](/assets/img/concurrent-lists/optimistic-list.png){: width="100%"}

## Step 2: Acquire Locks

![](/assets/img/concurrent-lists/optimistic-list.png){: width="100%"}

## Step 3: Validate

![](/assets/img/concurrent-lists/optimistic-list.png){: width="100%"}

## Step 4: Perform Operation

![](/assets/img/concurrent-lists/optimistic-list.png){: width="100%"}

## Step 5: Release Locks

![](/assets/img/concurrent-lists/optimistic-list.png){: width="100%"}

## Why Could Validation fail?

![](/assets/img/concurrent-lists/optimistic-list.png){: width="100%"}

## Questions

- Liveness: Deadlock-free? Starvation-free?

<div style="margin-bottom: 6em"></div>

- Performance: When do you expect good performance? When not?

<div style="margin-bottom: 6em"></div>


## Next Time:

1. Lazy Synchronized List
2. Nonblocking Synchronized List
3. Other Data Structures
