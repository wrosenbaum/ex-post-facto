---
title: "Lecture 13: Lock Implementations"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 13: Lock Implementations

## Overview

- Peterson Lock
- Test-and-set Lock
- Test-and-test-and-set Lock

# Peterson Lock


## The Peterson Lock

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

Next week: A better way

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
- only primitive datatypes are visible
    + if `volatile SomeClass...`, only the *reference* is treated as volatile


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
	    + (more on this next time)
	
- Limitations of `volatile` variables
    + can ony perform atomic read/write operations
	+ only for primitive data-types
	+ need at least `n` registers (variables) for lock with `n` threads
	
## Simplicity through Atomicity

Better locks through atomics:

- `AtomicBoolean` supports atomic operations
- For example:
    + `ab.getAndSet(boolean newValue)`
	+ sets `ab`'s value to `newValue` and returns previous value
	
How could you use a single `AtomicBoolean` to implement a lock?

<div style="margin-bottom: 8em"></div>

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

## How Does `TASLock` Perform?

- test on laptop
- test on Remus/Romulus

## A Tweak: `TTASLock`

In `TASLock`, we do a lot of `getAndSet()` operations under contention

```java
    public void lock () {
        while (isLocked.getAndSet(true)) {}
    }
```

Since `getAndSet` is expensive, we could try:

- only attempt `getAndSet` if we've seen `isLocked.get() == false`

## Test-and-test-and-set Lock

```java
    public void lock () {
	while (true) {
	    while (isLocked.get()) {};

	    if (!isLocked.getAndSet(true)) {
		return;
	    }
	}
    }
```

How does this affect performance?

## Next Time

- Knowing when to back off
- Using queues to lock
    + thread-local objects
	

