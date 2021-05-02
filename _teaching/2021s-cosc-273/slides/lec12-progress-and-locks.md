---
title: "Lecture 12: Progress and Lock Implementations"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 12: Progress and Lock Implementations

## Overview

1. Non-blocking progress
    - Wait-freedom
	- Lock-freedom
2. Blocking vs Non-blocking Progress
3. Lock Implementations

## Last Time

Progress Conditions:

- **Wait-freedom**: pending method invocation always completes in a finite number of steps

- **Lock-freedom**: among all pending method calls, some method completes in a finite number of steps

Observed: wait-free $\implies$ lock-free

## A Wait-free Counter

```java
public class TwoCounter {
    int[] counts = new int[2];
	
    public void increment (int amt) {
        int i = ThreadID.get(); // thread IDs are 0 and 1
        int count = counts[i];
        counts[i] = count + amt;
    }
	
    public int read () {
        int count = counts[0];
        count = count + counts[1];
        return count;
    }
}
```

## An Atomic Primitive

`AtomicInteger` class:

```java
boolean compareAndSet(int expectedValue, int newValue); // ATOMIC
```

Specification for `AtomicInt ai`:

- if `ai`'s value is `expectedValue`, then
    + after call, `ai`'s value is `newValue`
	+ method returns `true`
- if `ai`'s value is not `expectedValue`, then
    + after call, `ai`'s value is unchanged
	+ method returns `false`

## A Silly Counter

```java
public class SillyCounter {

    AtomicInteger ai = new AtomicInteger(0);
	
    public void increment (int amt) {
        int val = ai.get();
        while (!ai.compareAndSet(val, val + amt)) {
            val = ai.get();
        }
    }
	
    public int read () {
        return ai.get();
    }
	
}
```

## Does `SillyCounter` Work?

```java
public class SillyCounter {

    AtomicInteger ai = new AtomicInteger(0);
	
    public void increment (int amt) {
        int val = ai.get();
        while (!ai.compareAndSet(val, val + amt)) {
            val = ai.get();
        }
    }
	
    public int read () {
        return ai.get();
    }
	
}
```

<div style="margin-bottom: 8em"></div>

## Is `SillyCounter` Lock-free?

```java
    public void increment (int amt) {
        int val = ai.get();
        while (!ai.compareAndSet(val, val + amt)) {
            val = ai.get();
        }
    }
```

<div style="margin-bottom: 12em"></div>

## Is `SillyCounter` Wait-free?

```java
    public void increment (int amt) {
        int val = ai.get();
        while (!ai.compareAndSet(val, val + amt)) {
            val = ai.get();
        }
    }
```

<div style="margin-bottom: 12em"></div>


## Properties of Nonblocking Progress

- Progress is guaranteed even if some thread stalls
    + e.g., scheduler stops scheduling a thread's method call
- Wait-freedom gives *maximal progress*
- Lock-freedom gives *minimal progress*
    + starvation can still occur
- Actual progress depends on *scheduler*
    + determines which threads make steps
	
<div style="margin-bottom: 4em"></div>


## Blocking Progress Conditions

- **starvation-free**: whenever *all* pending methods take steps, *every* method call completes in a finite number of steps
    + maximal (blocking) progress

<div style="margin-bottom: 4em"></div>

- **deadlock-free**: whenever *all* pending method calls take steps, *some* method call completes in a finite number of steps
    + minimal (blocking) progress

<div style="margin-bottom: 4em"></div>

## Blocking vs Nonblocking Progess

Nonblocking progress

- guarantees progress for *any* scheduler
- valid even if a process crashes

<div style="margin-bottom: 4em"></div>

Blocking progress

- progress only guaranteed for *fair* schedulers
- if a process crashes, progress not guaranteed

<div style="margin-bottom: 4em"></div>

## Which is Better?



```java
public class SillyCounter {
    private AtomicInteger ai = new AtomicInteger(0);
    public void increment (int amt) {
        int val = ai.get();
        while (!ai.compareAndSet(val, val + amt)) {
            val = ai.get();
        }
    }
}
```

Or

```java
public class LockedCounter {
    private Lock lock = new StarvationFreeLock();
    int count = 0;
    public void increment (int amt) {
        lock.lock()
        try {
            count += amt;
        } finally {
            lock.unlock();
        }
    }
}
```

## Scheduling w/ 2 Threads

```java
public class SillyCounter {
    private AtomicInteger ai = new AtomicInteger(0);
    public void increment (int amt) {
        int val = ai.get();
        while (!ai.compareAndSet(val, val + amt)) {
            val = ai.get();
        }
    }
}
```

What happens to thread 1 if scheduler stops scheduling steps of thread 2?

<div style="margin-bottom: 6em"></div>


## Scheduling w/ 2 Threads

```java
public class LockedCounter {
    private Lock lock = new StarvationFreeLock();
    int count = 0;
    public void increment (int amt) {
        lock.lock()
        try {
            count += amt;
        } finally {
            lock.unlock();
        }
    }
}
```

What happens to thread 1 if scheduler stops scheduling steps of thread 2?



<div style="margin-bottom: 6em"></div>


## Scheduling w/ 2 Threads

```java
public class SillyCounter {
    private AtomicInteger ai = new AtomicInteger(0);
    public void increment (int amt) {
        int val = ai.get();
        while (!ai.compareAndSet(val, val + amt)) {
            val = ai.get();
        }
    }
}
```

Is thread 1 guaranteed to make progress under *fair* scheduler?

<div style="margin-bottom: 6em"></div>


## Scheduling w/ 2 Threads

```java
public class LockedCounter {
    private Lock lock = new StarvationFreeLock();
    int count = 0;
    public void increment (int amt) {
        lock.lock()
        try {
            count += amt;
        } finally {
            lock.unlock();
        }
    }
}
```

Is thread 1 guaranteed to make progress under *fair* scheduler?

<div style="margin-bottom: 6em"></div>

## So

- With an unfair scheduler (or when threads can be interrupted), lock-freedom (nonblocking) might be better
    + guarantees some progress
- With a fair scheduler (threads will not be interrupted), starvation-freedom (blocking) might be better
    + with fairness assumption, *every* thread is guaranteed progress
	
Nonblocking is not strictly superior to blocking!



## Progress vs Correctness

- Can have a data structure that is...
    + ...sequentially consistent and wait-free
	+ ...linearizable and lock-free
	+ ...sequentially consistent and deadlock-free
	+ ...
	
- Different implementations have different trade-offs

- Which implementation is best depends on application:
    + how much synchronization is required?
	    + how frequently is contention expected?
	+ what correctness guarantee is required?
	

# Finally: Implementations

## Back to Where We Started

1. A `Counter` object, now with a lock
2. A lock

## Let's Implement a Lock!

Recall the Peterson lock:

```java
class Peterson implements Lock {
    private boolean[] flag = new boolean[2];
    private int victim;
	
    public void lock () {
       int i = ThreadID.get(); // get my ID, 0 or 1
       int j = 1 - i;          // other thread's ID
	   
       flag[i] = true;         // set my flag
       victim = i;             // set myself to be victim
       while (flag[j] && victim == i) {};
    }
	
    public void unlock () {
        int i = ThreadID.get();
        flag[i] = false;
    }
}
```

## A Challenge

Peterson lock assumes 2 threads, with IDs `0` and `1`

- How do we accomplish this?

<div style="margin-bottom: 8em"></div>


## Make a Thread Subclass

Manually set an ID for threads

```java
public class PetersonThread extends Thread {
    private int id;
    private LockedCounter ctr;
    private int numIncrements;

    public PetersonThread (int id, LockedCounter ctr, int numIncrements) {
	super();
	this.id = id;
	this.ctr = ctr;
	this.numIncrements = numIncrements;
    }

    public int getPetersonId() {
	return id;
    }

    @Override
    public void run () {
	for (int i = 0; i < numIncrements; ++i) {
	    ctr.increment();
	}
    }
}
```

## Making a `PetersonLock`

```java
class PetersonLock {
    private boolean[] flag = new boolean[2];
    private int victim;

    public void lock () {
	int i = ((PetersonThread)Thread.currentThread()).getPetersonId();
	int j = 1 - i;
	flag[i] = true;
	victim = i;
	while (flag[j] && victim == i) {};
    }

    public void unlock () {
	int i = ((PetersonThread)Thread.currentThread()).getPetersonId();	
	flag[i] = false;
    }
}
```

## And Now: A Locked Counter

```java
public class LockedCounter {
    private int count = 0;
    PetersonLock lock = new PetersonLock();

    public void increment () {
	lock.lock();
	try {
	    ++count;
	} finally {
	    lock.unlock();
	}
    }

    public int read () {
	return count;
    }
}
```

## Finally, We're Ready to Test It!

## D'oh!

What happened?

<div style="margin-bottom: 8em"></div>

## Memory Consistency!

![](/assets/img/cache-hierarchy/cache-cpu.png){: width="75%"}

## `volatile` Variables

Java can make variables visible between threads:

- use `volatile` keyword
- individual read/write operations to `volatile` are atomic

Drawbacks:

- `volatile` variables are less efficient
- *only* single read/write operations are atomic
    + e.g. `count++` not atomic

## What Variables Should be `volatile`?

- In `PetersonLock`?
- In `LockedCounter`?

## `PetersonLock` Again

```java
class PetersonLock {
    private boolean[] flag = new boolean[2];
    private int victim;

    public void lock () {
	int i = ((PetersonThread)Thread.currentThread()).getPetersonId();
	int j = 1 - i;
	flag[i] = true;
	victim = i;
	while (flag[j] && victim == i) {};
    }

    public void unlock () {
	int i = ((PetersonThread)Thread.currentThread()).getPetersonId();	
	flag[i] = false;
    }
}
```

## A Problem

Only primitive datatypes can be `volatile`
- `volatile boolean[] flag` makes the *reference* `volatile`, not the data itself

How to fix this?

<div style="margin-bottom: 8em"></div>

## A Fix

Just make 2 `boolean` variables, `flag0` and `flag1`

- Yes, I know this is ugly


## `LockedCounter` Again

```java
public class LockedCounter {
    private int count = 0;
    PetersonLock lock = new PetersonLock();

    public void increment () {
	lock.lock();
	try {
	    ++count;
	} finally {
	    lock.unlock();
	}
    }

    public int read () {
	return count;
    }
}
```

## Testing Our Counter Again

## Finally!!!

What have we done?

1. *Proven* correctness of a lock
    + idealized model of computation
	+ atomic read/write operations
2. Implemented lock
    + used Java to resemble idealized model
3. Used lock
    + saw expected behavior
	
Theory and practice converge!

## Limitations

- Limitations of `PetersonLock`
    + only two threads
	+ weird Java gymnastics to deal with thread IDs
	
- Limitations of `volatile` variables
    + can ony perform atomic read/write operations
	+ only for primitive data-types
	+ need at least `n` registers (variables) for lock with `n` threads
	
## Simplicity through Atomicity

Better locks through atomics:

- `AtomicBoolean` supports atomic operations in addition to 
- For example:
    + `ab.getAndSet(boolean newValue)`
	+ sets `ab`'s value to `newValue` and returns previous value
	
How could you use a single `AtomicBoolean`

## The Test-and-set Lock

```java
public class TASLock {
    AtomicBoolean isLocked = new AtomicBoolean(false);
	
    public void lock () {
        while (isLocked.getAndSet(true)) {}
    }
	
	public void unlock () {
        isLocked.set(false);
	}
}
```

## Advantages/Disadvantages?

```java
public class TASLock {
    AtomicBoolean isLocked = new AtomicBoolean(false);
	
    public void lock () {
        while (isLocked.getAndSet(true)) {}
    }
	
	public void unlock () {
        isLocked.set(false);
	}
}
```
<div style="margin-bottom: 8em"></div>

## Next Time

- More Locks!


