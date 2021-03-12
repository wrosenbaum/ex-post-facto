---
title: "Lecture 07: Concurrent Objects 2"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 07: Concurrent Objects 2

## Announcements

1. Quiz 2 posted
2. Homework 2 posted in next couple days
3. Accountability group survey linked to today

## Outline

1. Linked Lists, Again
2. Blocking and consistency

## Last Time

Linked list with concurrent insertions:

- Lock individual nodes
- Can perform concurrent insertions when nodes don't overlap

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

## A Slightly Different Scenario

![](/assets/img/concurrent-objects/CircularList.png){: width="50%"}

## Question

Are multiple concurrent insertions guaranteed to eventually succeed?

![](/assets/img/concurrent-objects/CircularList.png){: width="25%"}

<div style="margin-bottom: 8em"></div>

# Blocking and Consistency

## Morals from Previous Examples

1. Locking whole object (linked list)
    - easy to reason about correctness
	- may give poor performance
2. Locking individual parts
    - may give better performance
	- more challenging to reason about correctness
	
## A Queue

```java
public class LockedQueue<T> {
    int head, tail;
    T[] contents;
    Lock lock;
	
    ...
	
    public void enq(T x) {
        lock.lock();
        try {
            items[tail} = x;
            tail++;
        } finally {
            lock.unlock();
        }
    }

    public T deq() {
        lock.lock();
        try {
            T x = items[head];
            head++;
            return x;
        } finally {
            lock.unlock();
        }
    }
}
```

## Question

What makes this queue implementation easy to reason about?

<div style="margin-bottom: 12em"></div>

## Executions are Essentially Sequential!

If multiple threads access the queue

- only one thread actually modifies queue at a time
- other threads must wait
- this property is called **blocking**:
    + some method calls cannot make progress while others perform a task
	
## Another Question

Queues are *first-in first-out* (FIFO) data structures

What does FIFO even *mean* if objects can be enqueued concurrently?

<div style="margin-bottom: 12em"></div>

## Blocking Execution

Consider:

- Two threads A, B concurrently call `enq(x)`, `enq(y)`, respectively
- Then thread A calls `deq()`

What is expected behavior? 

<div style="margin-bottom: 12em"></div>

## What Happens?

Depends on how concurrent operations are resolved!

![](/assets/img/consistency/enqueue.png){: width="100%"}

## Equivalent Sequential Execution

![](/assets/img/consistency/sequential.png){: width="100%"}

## In Sequential Execution

Method calls are linearly sorted:

- Method calls:
    + invocation
	+ response
- Each call's response preceeds next call's invocation

<div style="margin-bottom: 8em"></div>


## Reasoning About Sequential Executions

1. Assume object in some state
    - **precondition**
2. Method specifies
	- **postcondition**
	    + return value
		+ change of internal state (side effect)

Method calls performed sequentially $\implies$ state well defined *between* method calls.

- Specifying pre/post-conditions for each method define object's **sequential specification**

## Reasoning About Concurrent Executions

"Correct" behavior no longer well defined!

![](/assets/img/consistency/concurrent-exec.png){: width="100%"}

- call to `deq()` could return either `x` or `y`
    + both reasonable!
	
## A Reasonable Goal

A concurrent execution of a data structure is "correct" if it is consistent with *some* sequential execution of the data structure.

![](/assets/img/consistency/sequential.png){: width="100%"}

Response to each method call in concurrent execution is the same as the sequential execution.

- What other features of concurrent execution can/should the sequential execution maintain?

## Our Goal

Define sensible qualities for how executions should behave:

1. Sequential consistency
2. Linearizability

These are less rigid requirements than being essentially sequential

- May allow for less synchronization (locking) between threads
- Tradeoff: more lenient behavioral guarantees

# Sequential Consistency

## A Sensible Feature

Consider all method calls made by all threads

- Each method has precondition, postcondition

Behavior of execution should be consistent with *some* sequential execution of the method calls.

<div style="margin-bottom: 8em"></div>

## Is This Enough?

Behavior of execution should be consistent with *some* sequential execution of the method calls.

<div style="margin-bottom: 12em"></div>

## Probably Not!

Queue with multiple threads:

- thread 1 calls `enq(1)` then `enq(2)`
- other threads enqueue stuff, not `1` or `2`
- thread 1 calls `deq()` a bunch of times

Should have:

- thread 1 dequeues `1` before `2`

## Another Sensible Feature

Method calls should appear to take effect in **program order**

- if a single thread calls `methodOne()` before `methodTwo()`, then `methodOne()` should take effect before `methodTwo()` in sequential execution.

<div style="margin-bottom: 8em"></div>

## Sequential Consistency

An execution is **sequentially consistent** if all method calls can be ordered such that:

1. they are consistent with program order
2. they meet object's sequential specification

An implementation of an object is sequentially consistent if

1. it guarantees *every* execution is sequentially consistent

## Example

What are possible outcomes of `deq()` calls in a sequentially consistent execution?

![](/assets/img/consistency/mult-ops.png){: width="100%"}

<div style="margin-bottom: 12em"></div>

<!-- ## Sequential Consistency is Weak -->

<!-- - Method calls can appear interleaved in *any* order that maintains program order -->
<!-- - No guarantees for "real time" order -->
<!--     + (partially) order methods wrt real time -->

<!-- ![](/assets/img/consistency/mult-ops.png){: width="100%"} -->

<!-- ## Sequential Consistency is Nonblocking -->

<!-- Consider concurrent execution: -->

<!-- - Pending method call (invoked, but no response yet) -->
<!-- - Then: exists a response that can be made immediately that does not violate SC -->

<!-- It is possible to achieve SC without needing to wait for response from other pending methods -->

<!-- - May still be difficult to figure out *which* response will give SC execution -->

<!-- ## A Subtler Issue -->

<!-- Suppose we have a program two queues, `p` and `q` -->

<!-- - Implementation of queue guarantees sequentially consistency -->

<!-- Must an execution of a program with `p` and `q` be sequentially consistent? -->

<!-- <div style="margin-bottom: 8em"></div> -->

<!-- ## An Execution -->

<!-- ![](/assets/img/consistency/compositionality.png){: width="100%"} -->

<!-- <div style="margin-bottom: 12em"></div> -->

<!-- ## Sequentially Consistent for `p` -->

<!-- ![](/assets/img/consistency/compositionality-p.png){: width="100%"} -->

<!-- <div style="margin-bottom: 12em"></div> -->

<!-- ## Sequentially Consistent for `q` -->

<!-- ![](/assets/img/consistency/compositionality-q.png){: width="100%"} -->

<!-- <div style="margin-bottom: 12em"></div> -->

<!-- ## But is Execution SC? -->

<!-- ![](/assets/img/consistency/compositionality.png){: width="100%"} -->

<!-- <div style="margin-bottom: 12em"></div> -->


<!-- ## Sequential Consistency is not Compositional -->

<!-- A property $\mathcal{P}$ is **compositional** if  -->

<!-- - Whenever all objects in system satisfy $\mathcal{P}$, then system satisfies $\mathcal{P}$ -->

<!-- Previous example shows: -->

<!-- - Sequential consistency is *not* compositional -->

<!-- ## Sequential Consistency Is... -->

<!-- - ...Maybe the weakest reasonble guarantee we could expect of a concurrent implementation of an object -->
<!-- - ...Potentially allows for a lot of parallelism -->
<!--     + SC is a nonblocking property -->
<!-- - ...Not compositional -->
<!--     + a system made of SC components need not be SC! -->
	
<!-- # Linearizability -->

<!-- ## Weird Thing about SC -->

<!-- SC makes no reference to invocation/response intervals for method calls -->

<!-- - Why bother with time intervals if we ignore them (execept relative order for single threads)? -->

<!-- Another idea: -->

<!-- - Make sure execution is consistent with timing of method calls -->
<!-- - Consider sequential executions consistent with each method call taking effect at some *instant* during the method call -->

<!-- ## Same Example, Fewer Options -->

<!-- ![](/assets/img/consistency/mult-ops.png){: width="100%"} -->

<!-- Can only change relative order of method calls if they overlap -->

<!-- <div style="margin-bottom: 8em"></div> -->

<!-- ## Linearization Points -->

<!-- A **linearization point** is a point in a method call where method "takes effect" -->

<!-- - all events after linearization point see effect of method call -->
<!-- - linearization points must be distinct (correspond to some atomic operation) -->
	
<!-- ## Example of Linearization Points -->

<!-- ![](/assets/img/consistency/linearization-pts.png){: width="100%"} -->

<!-- <div style="margin-bottom: 8em"></div> -->

<!-- ## Equivalent Sequential Execution -->

<!-- ![](/assets/img/consistency/equivalent-seq-exec.png){: width="100%"} -->

<!-- <div style="margin-bottom: 8em"></div> -->

<!-- ## An Alternative Sequential Execution -->

<!-- ![](/assets/img/consistency/alt-seq-exec.png){: width="100%"} -->

<!-- <div style="margin-bottom: 8em"></div> -->

<!-- ## Linearizability -->

<!-- A concurrent execution is **linearizable** if: -->

<!-- - exists a linearization point in each method call such that execution is consistent with sequential execution where method calls occur in order of corresponding linearization points -->

<!-- An implementation of an object is linearizable if: -->

<!-- - it guarantees every execution is linearizable -->

<!-- ## Comparing Linearizability and SC -->

<!-- 1. Does linearizability imply sequential consistency? -->
<!-- 2. Does sequential consistency imply linearizability? -->

<!-- <div style="margin-bottom: 12em"></div> -->

<!-- ## Linearizability $\implies$ SC -->

<!-- Must show: -->

<!-- 1. Maintains program order -->
<!-- 2. Satisfies sequential specification -->

<!-- <div style="margin-bottom: 12em"></div> -->

<!-- ## SC $\nRightarrow$ Linearizability -->

<!-- ![](/assets/img/consistency/compositionality-p.png){: width="100%"} -->

<!-- <div style="margin-bottom: 12em"></div> -->

<!-- ## Compositionality -->

<!-- Recall this execution with 2 queues (not sequentially consistent) -->

<!-- ![](/assets/img/consistency/compositionality.png){: width="100%"} -->

<!-- <div style="margin-bottom: 12em"></div> -->

<!-- ## But... -->

<!-- `p` and `q` are not linearizable in this execution! -->

<!-- ![](/assets/img/consistency/compositionality.png){: width="100%"} -->

<!-- <div style="margin-bottom: 12em"></div> -->

<!-- ## Two More Questions -->

<!-- 1. Are linearizable objects compositional? -->
<!-- 2. Are linearizable executions nonblocking? -->













