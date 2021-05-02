---
title: "Lecture 14: More Locking Strategies"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 14: More Locking Strategies

## Overview

1. Review of TAS and TTAS
2. Backing Off
3. Queue Based Locks

## Last Time

The Test-and-set (TAS) Lock:

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

## Performance of TAS

Decreases with number of processors

- From Remus and Romulus (1M lock accesses):

```text
     n  elapsed time (ms)
     1           109
     2           200
     3           492
     4           494
     5           576
     6           804
     7           796
     8          1001
     9          1130
    10          1128
    11          1123
    12          1260
    13          1320
    14          1476
    15          1610
    16          1646
    17          1594
    18          1198
    19          1755
    20          1139
    21          1940
    22          2033
    23          2133
    24          2258
    25          2125
    26          2578
    27          2211
    28          2432
    29          2188
    30          2663
    31          2579
```

Doing same number of ops takes 25 times as long on 31 threads as on 1 thread!

## One Problem with TAS

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

Each thread calls `getAndSet` constantly!

- more complex atomic primitive operations are more costly

## Another Approach

Only try to `getAndSet` if previously saw lock was unlocked

- the Test-and-test-and-set (TTAS) lock:

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

- Less frequent `getAndSet` $\implies$ better performance?

## TTAS Lock Performance

Same test as before on Remus and Romulus (1M accesses):

```text
     n  elapsed time (ms)
     1           108
     2           202
     3           206
     4           212
     5           253
     6           237
     7           218
     8           242
     9           275
    10           250
    11           214
    12           239
    13           216
    14           265
    15           219
    16           283
    17           266
    18           287
    19           279
    20           286
    21           231
    22           294
    23           300
    24           243
    25           293
    26           303
    27           300
    28           294
    29           302
    30           303
    31           305
    32           309
```

Now: performance with 32 threads is less than 3 times slower than without contention!

- That is about 8 times faster than TAS lock!

## The Moral

- Read more, write less!
    + reading atomic variables is more efficient than writing
	+ this is especially true when there is a lot of contention

## Yet Another Approach

Backing off under contention

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

- If we hit `if (...)` but fail to acquire lock, there are other threads attempting to acquire lock
    + **contention detected** at this point
- So: we could wait

## Exponential Backoff

Store:

1. `MIN_DELAY`, `MAX_DELAY` (constants)
2. `limit` initialized to `MIN_DELAY`

When contention detected:

1. Pick random `delay` between `0` and `limit`
2. Update `limit = Math.min(2 * limit, MAX_DELAY)`
3. Wait `delay` time before attempting to acquire lock again

## Illustration

![](/assets/img/exponential-backoff/contention.png){: width="100%"}

## Contention Detected

![](/assets/img/exponential-backoff/contention-detected.png){: width="100%"}

## First Backoff

![](/assets/img/exponential-backoff/backoff-1.png){: width="100%"}

## More Contention

![](/assets/img/exponential-backoff/contention-2.png){: width="100%"}

## Second Backoff

![](/assets/img/exponential-backoff/backoff-2.png){: width="100%"}

## Final Backoff

![](/assets/img/exponential-backoff/final.png){: width="100%"}


## Result of Exponential Backoff

1. Spread out attempts to acquire lock
    - more contention $\implies$ larger `delay`
	- $\implies$ each attempt more likely to succeed
2. Waiting threads don't use (as many) computer resources
3. Locked resource may be under-utilized

## Implementing Exponential Backoff

Is it practical?

```text
     n  elapsed time (ms)
     1           148
     2           234
     3           250
     4           226
     5           216
     6           230
     7           255
     8           267
     9           262
    10           269
    11           271
    12           276
    13           265
    14           298
    15           289
    16           267
    17           296
    18           292
    19           308
    20           280
    21           290
    22           302
    23           261
    24           304
    25           313
    26           317
    27           317
    28           331
    29           322
    30           313
    31           315
    32           314
```

Not better than TTAS for tested parameters.

- [Download `SimpleLocks.zip`]()

## Technical Problem

Textbook `Backoff` uses `Thread.sleep(...)`

- `sleep` tells OS scheduler not to schedule thread for a while
    + doesn't use resources like `while (true) {};`
- Minimum `sleep` delay is 1 ms
- This is MILLIONS of CPU cycles!
    + way too long to help with high contention/small tasks!
- My wasteful implementation uses "busy waiting":

```java
    private void spin (long delay) {
	long start = System.nanoTime();
	long cur = System.nanoTime();

	while (cur - start < delay) {
	    cur = System.nanoTime();
	};
    }
```

## The Moral

Exponential backoff is:

- A useful strategy in a variety of contexts
    + frequently used in network protocols
- Probably not the best strategy for most of our locking needs

## A Goal

So far:

- `TASLock`
- `TTASLock`
- `BackoffLock`

are deadlock-free, but not starvation free!

**Question.** How to achieve starvation freedom?

<div style="margin-bottom: 8em"></div>

## Queueing Lock Strategy

Represent threads waiting for lock as a queue

- linked list representation of queue 
	+ each thread has associated node
	+ node stores boolean value:
	    + `true` I want/have lock
		+ `false` I don't want/have lock
	+ thread also stores predecessor node
- list initialized with a single node
    + `tail` node storing `false`

## Initial Configuration

<div style="margin-bottom: 18em"></div>


## Thread `A` Acquires Lock

<div style="margin-bottom: 18em"></div>

## Thread `B` Calls `lock()`

<div style="margin-bottom: 18em"></div>

## Thread `A` Releases Lock

<div style="margin-bottom: 18em"></div>

## Thread `B` Acquires Lock

<div style="margin-bottom: 18em"></div>

## Technical Challenge

We need a `Node` for each thread!

- Don't know number of threads in advance!

<!-- ## Interlude: Thread-local Objects -->

<!-- Java (generic) class: `ThreadLocal<T>` -->

<!-- - Make a `T` for each thread -->
<!-- - Some `ThreadLocal` methods: -->
<!--     + `T get()` returns current thread's copy of variable -->
<!-- 	+ `protected T initialValue()` returns current thread's initial value -->
<!-- 	    + you'll want to `@Override` this to initialize  -->
<!-- 	+ `void set(T value)` set value of current thread's copy -->
	
<!-- ## Ugly Syntax -->

<!-- Make a `MyClass` instance for each thread: -->

<!-- ```java -->
<!-- public class TLObjects { -->
<!--     private ThreadLocal<MyClass> myInstance =  -->
<!--         new ThreadLocal<MyClass>() { -->
<!--             @Override -->
<!--             protected MyClass initialValue() { -->
<!--                 return new MyClass(...args...); -->
<!--             } -->
<!--         }; -->
    
<!-- } -->
<!-- ``` -->

<!-- <div style="margin-bottom: 6em"></div> -->

<!-- ## An Example: `ThreadId` -->

<!-- Make a class that assigns sequential IDs to threads, starting at 0 -->

<!-- Fields: -->

<!-- - `static AtomicInteger` that stores next ID to be assigned -->
<!-- - `static ThreadLocal<Integer>`that stores each thread's ID -->

<!-- Methods: -->

<!-- - `static int get()` returns value of thread's ID -->

<!-- Why is everything `static`? -->

<!-- <div style="margin-bottom: 4em"></div> -->

<!-- ## `ThreadID` in Code -->

<!-- ## Next Time -->

<!-- - Using `ThreadLocal` variables to implement queue-based lock -->

<!--     + CLH lock (Craig, Hagersten, Landin)  -->
	
<!-- - Linked Lists -->
