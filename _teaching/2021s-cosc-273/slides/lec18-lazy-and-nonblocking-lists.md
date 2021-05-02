---
title: "Lecture 18: Lazy and Nonblocking Lists"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 18: Lazy and Nonblocking Lists

## Overview

1. Logical Removal and Lazy Lists
2. Nonblocking Lists
3. List Appraisal

## Last Time(s)

1. Coarse-grained synchronization
    - lock the whole data structure for each operation
2. Fine-grained synchronization
    - lock the parts of the data structure currently being accessed
3. Optimistic synchronization
    - don't lock until ready to modify
	- validate between locking and modification
	- validation failure $\implies$ restart

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

## Performance, Single Threaded

Runtimes: 1M Operations

| num elements | coarse-grained | fine-grained | optimistic |
|--------------|----------------|--------------|------------|
|10| 0.11 s | 0.14 s | 0.13 s|
|100| 0.14 s| 0.46 s | 0.19 s|
|1000| 1.1 s| 3.9 s | 2.2 s|
|10000| 28 s| 39 s | 59 s|

- fine-grained slow-down: more locks
- optimistic slow-down: validataion 

## Performance, 8 Threads

Runtimes: 1M Operations

| num elements | coarse-grained | fine-grained | optimistic |
|--------------|----------------|--------------|------------|
|10| 0.21 s | 0.36 s | 0.33 s|
|100| 0.27 s | 1.80 s | 0.38 s|
|1000| 1.8 s | 4.7 s | 0.86 s |
|10,000| 32 s | 17 s | 9.2 s|

Note: fewer elements $\implies$ greater contention


## Optimism and Validation

Under best circumstances:

- validation succeeds
    + likely if little contention
- still traverse the list twice

Under contention:

- all operations are *blocking*
    - not wait-free
- contention can lead to validation failures
    - not starvation-free

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

## Single Thread Performance

Runtimes: 1M Operations

| n elts | coarse | fine | optimistic | lazy |
|--------------|----------------|--------------|------------|------|
|10| 0.11 s | 0.14 s | 0.13 s| 0.11 s|
|100| 0.14 s| 0.46 s | 0.19 s| 0.13 s|
|1000| 1.1 s| 3.9 s | 2.2 s| 1.1 s|
|10000| 28 s| 39 s | 59 s| 29 s |

## Performance, 8 Threads

Runtimes: 1M Operations

| n elts | coarse | fine | optimistic | lazy |
|--------------|----------------|--------------|------------|------|
|10| 0.21 s | 0.36 s | 0.33 s | 0.33 s |
|100| 0.27 s | 1.80 s | 0.38 s | 0.12 s|
|1000| 1.8 s | 4.7 s | 0.86 s | 0.19 s|
|10,000| 32 s | 17 s | 9.2 s| 4.7 s |

Note: fewer elements $\implies$ greater contention


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

## What's Next?

Can we make *all* of the operations wait-free?

- A concurrent list without locks?

## Why Does LazyList Need Locks?

1. Traverse without locking
2. Lock relevant nodes
3. Validate list
4. Perform operation
5. Unlock nodes

<div style="margin-bottom: 6em"></div>

## Why Does LazyList Need Locks?

The issue:

- Validation and modification are separate steps
- Must enforce that nodes are unchanged between validation and mod

Cause for hope:

- Validataion is simple, local:
    ```java
        private boolean validate (Node pred, Node curr) {
    	return !pred.marked && !curr.marked && pred.next == curr;
        }
    ```
- Modification (e.g., `add`) is simple, local:
   ```java
   Node node = new Node(item);
   node.next = curr;
   pred.next = node; // this is the only step that modifies list!
   ```
   
## An Idea

If we can 

1. combine validation and modification steps
2. perform this operation atomically

then maybe we can avoid locking?

## A Tool

Better living with atomics!

- `AtomicMarkableReference<T>`
- Stores 
    1. a reference to a `T`
	2. a boolean `marked`
- Atomic operations
    1. `boolean compareAndSet(T expectedRef, T newRef, boolean expectedMark, boolean newMark)`
	2. `T get(boolean[] marked)`
	3. `T getReference()`
	4. `boolean isMarked()`


## An Algorithm?

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

<!-- ## Common Functionality -->

<!-- Traverse are remove: -->

<!-- 1. Find `pred` and `curr` where `item` lives -->
<!-- 2. Physically remove logically removed nodes along the way -->

<!-- Return: a `Window` -->

<!-- ```java -->
<!--     class Window { -->
<!-- 	public Node pred; -->
<!-- 	public Node curr; -->

<!-- 	public Window (Node pred, Node curr) { -->
<!-- 	    this.pred = pred; -->
<!-- 	    this.curr = curr; -->
<!-- 	} -->
<!--     } -->
<!-- ``` -->

<!-- ## Finding and Removing in Code -->

<!--     public Window find(Node head, long key) { -->
<!-- 	Node pred = null; -->
<!-- 	Node curr = null; -->
<!-- 	Node succ = null; -->
<!-- 	boolean[] marked = {false}; -->
<!-- 	boolean snip; -->

<!-- 	retry: while(true) { -->
<!-- 	    pred = head; -->
<!-- 	    curr = pred.next.getReference(); -->
<!-- 	    while(true) { -->
<!-- 		succ = curr.next.get(marked); -->
<!-- 		while(marked[0]) { -->
<!-- 		    snip = pred.next.compareAndSet(curr, succ, false, false); -->
<!-- 		    if (!snip) continue retry; -->
<!-- 		    curr = pred.next.getReference(); -->
<!-- 		    succ = curr.next.get(marked); -->
<!-- 		} -->
<!-- 		if (curr.key >= key) { -->
<!-- 		    return new Window(pred, curr); -->
<!-- 		} -->
<!-- 		pred = curr; -->
<!-- 		curr = succ; -->
<!-- 	    } -->
<!-- 	} -->


<!-- ## Removal in Code -->

<!-- ```java -->
<!--     public boolean remove(T item) { -->
<!-- 	int key = item.hashCode(); -->
<!-- 	boolean snip; -->
<!-- 	while (true) { -->
<!-- 	    Window window = find(head, key); -->
<!-- 	    Node pred = window.pred; -->
<!-- 	    Node curr = window.curr; -->

<!-- 	    if (curr.key != key) { -->
<!-- 		return false; -->
<!-- 	    } -->

<!-- 	    Node succ = curr.next.getReference(); -->
<!-- 	    snip = curr.next.attemptMark(succ, true); -->
<!-- 	    if (!snip) { -->
<!-- 		continue; -->
<!-- 	    } -->
<!-- 	    pred.next.compareAndSet(curr, succ, false, false); -->
<!-- 	    return true; -->
<!-- 	} -->
<!--     } -->
<!-- ``` -->

<!-- ## Test the Code -->


<!-- ## Single Thread Performance -->

<!-- Runtimes: 1M Operations -->

<!-- | n elts | coarse | fine | optimistic | lazy | non-block | -->
<!-- |--------------|----------------|--------------|------------|------|------| -->
<!-- |10| 0.11 s | 0.14 s | 0.13 s| 0.11 s| 0.11 s | -->
<!-- |100| 0.14 s| 0.46 s | 0.19 s| 0.13 s| 0.20 s | -->
<!-- |1000| 1.1 s| 3.9 s | 2.2 s| 1.1 s| 2.8 s | -->
<!-- |10000| 28 s| 39 s | 59 s| 29 s | 66 s | -->

<!-- ## Performance, 8 Threads -->

<!-- Runtimes: 1M Operations -->

<!-- | n elts | coarse | fine | optimistic | lazy | non-block | -->
<!-- |--------------|----------------|--------------|------------|------|-----| -->
<!-- |10| 0.21 s | 0.36 s | 0.33 s | 0.33 s | 0.21 s | -->
<!-- |100| 0.27 s | 1.80 s | 0.38 s | 0.12 s| 0.07 s | -->
<!-- |1000| 1.8 s | 4.7 s | 0.86 s | 0.19 s| 0.49 s | -->
<!-- |10,000| 32 s | 17 s | 9.2 s| 4.7 s | 9.2 s | -->

<!-- Note: fewer elements $\implies$ greater contention -->

## Next Week

Other linear data structures!

- Queues
- Stacks
