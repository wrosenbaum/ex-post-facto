---
title: "Lecture 03: Mutual Exclusion I"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 03: Mutual Exclusion I


## Outline

1. `Counter` Example
2. A Parable
3. Mutual Exclusion and Properties


## `Counter` Example

Recall the `Counter` object:

```java
public class Counter {
    long count = 0;
    
    public long getCount () { return count; }
    public void increment () { count++; }
    public void reset () { count = 0; }
}
```

## Intended Behavior

If `increment()` is called `n` times, `count` should be `n`

- even if incremented by different threads!


## Problem with Counter

When multiple threads increment in parallel, only one incrementation performed!


## Correct Behavior

If multiple threads try to increment at a time:

- exactly one thread gets to increment at a time
- other threads wait until increment completed


## Terminology

We want our `Counter` to satisfy **mutual exclusion**.


# A Parable


## A Shared Resource

- Professor (Scott) Alfeld and I are neighbors
- For purposes of today's lecture, say we share a backyard
- We have dogs: Finnnegan (my dog), Ruple (Scott's dog)
- Sadly, our dogs don't get along
   + they used to, but not anymore
   + we don't know why
   

## Finn and Ruple

![](/assets/img/mutex-1/finn.jpeg){: width="45%"} ![](/assets/img/mutex-1/ruple.jpeg){: width="45%"}


## A Question

How can Scott and Will ensure that we don't let Finn and Ruple out in the yard at the same time?

- don't like bothering each other with a text/phone call
- shouldn't have to "actively" communicate unless both dogs need to go out
- have a way of passively signaling intent
    + use flags!
	+ each has a flag that can be raised or lowered
	+ can see the state of each other's flags
	

## A Picture

<div style="margin-bottom: 12em"></div>


## Our Goals

**Safety Goal:**

- Both dogs are not simultaneously out in the yard
    + *mutual exclusion* property
	
**Liveness Goal:**

- If both dogs need to go outside, eventually one does 
    + *deadlock-freedom* property
	
Note: getting mutal exclusion and deadlock-freedom separately is easy!


## A First Protocol

Raised flag means my dog is in the yard!

1. Look to see if other flag is raised.
    + if so, wait until not raised

2. If not, raise flag then let dog out

3. When dog comes in, lower flag


## Does First Protocol Work?

<div style="margin-bottom: 12em"></div>


## A Bad Execution:

1. We both look at (approximately) the same time and see others' flag is down.

2. We both raise flags as (approximately) the same time.

3. We both let dogs out at the same time.


## A Second Protocol

Raised flag means I want to let my dog out!

1. Raise flag.

2. Check if other flag is up
    + if so, wait until not raised 

3. If other flag is down, let dog out!

4. When dog returns, lower flag.


## Does Second Protocol Work?

<div style="margin-bottom: 12em"></div>


## Another Bad Execution

1. Both raise flag at (approximately) same time.

2. Both see other's flag raised.

3. Both wait... neither dog ever goes outside!


## More Generally

Both protocols are **symmetric**

- Scott and Will behave the same way according to what we see

Can a symmetric protocol possibly work?


## For Any Symmetric Protocol

Suppose we act simultaneously:

1. start in same state

2. perform same action

3. see that other performed same action

4. respond in same manner

5. ...

This continues indefinitely

So either 

- we both let dogs out at same time, or
- neither dog goes out ever


## Apparently

We need an asymmetric protocol

- Under contention, give Finn priority
    + Scott agrees with this 
	
**Note.** *Symmetry breaking* is a common theme in parallel/distributed computing.
	
## Third Protocol

Separate protocols for Will and Scott

## Will's Protocol

When Finn needs to go out:

1. Raise flag

2. When Scott's flag is lowered, let Finn out

3. When Finn comes in, lower flag

## Scott's Protocol

When Ru needs to go out:

1. Raise flag

2. While Will's flag is raised:

    1. lower flag
	
	2. wait until Will's flag is lowered
	
	3. raise flag
	
3. When Scott's flag is up and Will's is down, release Ru

4. When Ru returns, lower flag

## Does Third Protocol Work?

- Do we get mutual exclusion?
- Do we get get deadlock-freedom?


## Crucial Insight

If both Scott and Will:

1. raise flag, then
2. look at other's flag

then at least one of us will see other flag raised

- always check other's flag *before* letting dog out
- both dogs not out at same time

## More Formally

If we want to *prove* mutual exclusion property

- argue by contradiction
- suppose that at some time, both dogs were out
- what could have led us there?


## Property of Both Protocols

Before letting a dog out, both Scott and Will do:

1. raise flag
2. see other's flag down
3. let dog out

## (Im)possible Timelines

<div style="margin-bottom: 12em"></div>

## Conclusion

If both Finn and Ru are in yard at same time, Will or Scott must not have followed the protocol!

- This establishes mutual exclusion property.

## What About Deadlock-Freedom?

If both Finn and Ruple want to go out

1. Both Will and Scott raise flags
2. Eventually, Scott sees Will's flag
   + lowers his flag (sorry Ru)
3. Eventually, Will sees Scott's flag down
4. Finn goes out!

## Nice!

This protocol gives mutual exclusion and deadlock-freedom...

<!-- ## ...But Could It Be Better?  -->

<!-- ## A Stronger Liveness Condition -->

<!-- **Starvation-freedom** If a dog wants to go out, eventually it will be able to go out. -->

<!-- Does Third Protocol give starvation freedom? -->

<!-- <div style="margin-bottom: 8em"></div> -->

<!-- ## Sorry, Ruple -->

<!-- Third protocol is not starvation-free! -->

<!-- - Finn could go out, come in, go out, come in... -->
<!-- - Scott only looks at Will's flag when it is up -->
<!-- - Ruple never goes out -->

<!-- Can we achieve starvation-freedom? -->


<!-- ## Mutual Exclusion Problem -->

<!-- - safety property (bad things don't happen) -->

<!--     + mutual exclusion -->
	
<!-- - liveness properties (good things eventually happen) -->

<!--     + deadlock-freedom -->
	
<!-- 	+ starvation-freedom -->


<!-- ## Bringing it Back to Computers -->

<!-- Multiple threads/processors attempt to: -->

<!-- - call a method, execute block of code, read/write to a field, ... -->

<!-- Want to ensure: -->

<!-- - only one thread/processor accesses resource at a time -->
<!-- - eventually one (or all) threads/processors should get access -->

<!-- ## In Java -->

<!-- Coming back to our `Counter`: -->

<!-- ```java -->
<!-- public class Counter { -->
<!--     long count = 0; -->
    
<!--     public long getCount () { return count; } -->
    
<!-- 	public void increment () {  -->
<!-- 	    count++;      // this line of code is *critical* -->
<!-- 	} -->
    
<!-- 	public void reset () { count = 0; } -->
<!-- } -->
<!-- ``` -->

<!-- ## Critical Sections -->

<!-- A **critical section** of code is a block of code that should be executed sequentially by one thread at a time: -->

<!-- - no concurrent executions -->
<!-- - no interleaving of statements with other threads -->

<!-- For example -->

<!-- ```java -->
<!-- public void increment () { -->
<!--     // start critical section -->
<!--     count++; -->
<!--     // end critical section -->
<!-- } -->
<!-- ``` -->

<!-- ## Protecting Critical Sections with Locks -->

<!-- The [`Lock` interface](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/locks/Lock.html) has two (for now) methods: -->

<!-- - `void lock()`: when method returns, thread **acquires lock** -->
<!--     + thread waits until lock is acquired -->
	
<!-- - `void unlock()`: when method returns, thread **releases lock** -->
<!--     + lock is available for another thread to acquire it -->

<!-- ## Locks and Mutual Exclusion -->

<!-- A `Lock` should satisfy safety and liveness: -->

<!-- - safety -->
<!--     + *mutual exclusion* at most one thread holds any lock at any given time -->
	
<!-- - liveness -->
<!--     + *deadlock-freedom* if multiple threads try to concurrently acquire a lock, one will eventually acquire it -->
<!-- 	+ *starvation-freedom* if a thread tries to acquire lock, it will eventually succeed -->


<!-- ## Using Locks 1 -->

<!-- Object instance has a `Lock` member variable: -->

<!-- ```java -->
<!-- public class SomeClass { -->
<!--     // an instance of an object implementing Lock -->
<!--     private Lock lock = new SomeLockImplementation(); -->
	
<!-- 	... -->
<!-- } -->
<!-- ``` -->

<!-- ## Using Locks 2 -->

<!-- Surround critical section with a try/catch/finally block -->

<!-- ```java -->
<!-- lock.lock();            // lock acquired after this -->
<!-- try { -->
<!--     // critical section -->
<!-- } finally { -->
<!--     lock.unlock();      // lock released after this -->
<!-- } -->
<!-- ``` -->

<!-- The `finally` block ensures that `lock.unlock()` is called even if there is an exception or return statement in the critical section! -->

<!-- ## A Locked Counter -->

<!-- ```java -->
<!-- public class Counter { -->
<!--     long count = 0; -->
<!--     Lock lock = new SomeLockImplementation(); -->
    
<!--     public long getCount () { return count; } -->
    
<!--     public void increment () {  -->
<!--         lock.lock(); -->
<!--         try { -->
		
<!--             count++; -->
			
<!--         } finally { -->
		
<!--             lock.unlock(); -->
			
<!--         } -->
<!--     } -->
    
<!--     // should probably lock this too... -->
<!--     public void reset () { count = 0; } -->
<!-- } -->
<!-- ``` -->

<!-- ## Things to Consider -->

<!-- - Might want more than one lock -->
<!--     + e.g., multiple distinct critical sections that *can* be executed concurrently -->
<!-- 	+ object with two counters, could have one lock for each counter -->
<!-- 	+ different counters incremented at same time is fine -->
	
<!-- - Interdependencies between locked objects can cause trouble -->
<!--     + individual locks well-behaved -->
<!-- 	+ example: *dining philosophers problem* (we'll talk about later) -->

<!-- ## Coming Up -->

<!-- How do we actually *implement* locks?!?! -->

<!-- - Many different solutions -->
<!-- - Offer different tradeoffs -->
<!--     + performance with little contention -->
<!-- 	+ performance with a lot of contention -->
<!-- - Which lock implementation is best depends on application -->
















