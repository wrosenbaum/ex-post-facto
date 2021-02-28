---
title: "Lecture 04: Mutual Exclusion II"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 04: Mutual Exclusion II

## Outline

1. Recap of Lecture 03
2. Critical Sections and Locks
3. Interlude: Events and Timing
4. The Peterson Lock

# Recap

## Last Time

- Considered shared backyard problem with Finn and Ru
    + both dogs cannot be in backyard at same time
	+ communication via raised/lowered flags
- We learned
    + achieving mutual exclusion and deadlock-freedom is subtle
	+ solution required an *asymmetric* protocol
- Our protocol gave preferential treatment to Finn
	
## Previous Protocol

Will's Protocol:

1. Raise flag
2. When Scott's flag is lowered, let Finn out
3. When Finn comes in, lower flag

Scott's Protocol:

1. Raise flag
2. While Will's flag is raised: (a) lower flag, (b) wait until Will's flag is lowered (c) raise flag
3. When Scott's flag is up and Will's is down, release Ru
4. When Ru returns, lower flag

## Crucial Insights

- Obtained mutual exclusion because of *flag principle:*
    + both Scott and Will raise flags
	+ both look at other's flag
	+ at least one sees other's flag up
	+ at least one doesn't release dog
	
- Obtained deadlock-freedom by *deferment:*
    + if both dogs want to go out, Scott defers to Will
	
## Protocol Gives Mutual Exclusion and Deadlock-freedom...

## ...But Could It Be Better?

## A Stronger Liveness Condition

**Starvation-freedom** If a dog wants to go out, eventually it will be able to go out.

Does Third Protocol give starvation freedom?

<div style="margin-bottom: 8em"></div>

## Sorry, Ruple

Third protocol is not starvation-free!

- Finn could go out, come in, go out, come in...
- Scott only looks at Will's flag when it is up
- Ruple never goes out

Can we achieve starvation-freedom?


## Mutual Exclusion Problem

- safety property (bad things don't happen)
    + mutual exclusion
- liveness properties (good things eventually happen)
    + deadlock-freedom
	+ starvation-freedom

Note: starvation-freedom $$\implies$$ deadlock-freedom

## Bringing it Back to Computers

Multiple threads/processors attempt to:

- call a method, execute block of code, read/write to a field, ...

Want to ensure:

- only one thread/processor accesses resource at a time
- eventually one (or all) threads/processors should get access

## In Java

Coming back to our `Counter`:

```java
public class Counter {
    long count = 0;
    
    public long getCount () { return count; }
    
    public void increment () {
        count++;      // this line of code is *critical*
    }
    
    public void reset () { count = 0; }
}
```

## Critical Sections

A **critical section** of code is a block of code that should be executed sequentially by one thread at a time:

- no concurrent executions
- no interleaving of statements with other threads

For example

```java
public void increment () {
    // start critical section
    count++;
    // end critical section
}
```

## Protecting Critical Sections with Locks

The [`Lock` interface](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/locks/Lock.html) has two (for now) methods:

- `void lock()`: when method returns, thread **acquires lock**
    + thread waits until lock is acquired
	
- `void unlock()`: when method returns, thread **releases lock**
    + lock is available for another thread to acquire it

## Locks and Mutual Exclusion

A `Lock` should satisfy safety and liveness:

- safety
    + *mutual exclusion* at most one thread holds any lock at any given time
	
- liveness
    + *deadlock-freedom* if multiple threads try to concurrently acquire a lock, one will eventually acquire it
	+ *starvation-freedom* if a thread tries to acquire lock, it will eventually succeed


## Using Locks 1

Object instance has a `Lock` member variable:

```java
public class SomeClass {
    // an instance of an object implementing Lock
    private Lock lock = new SomeLockImplementation();
	
	...
}
```

## Using Locks 2

Surround critical section with a try/catch/finally block

```java
lock.lock();            // lock acquired after this
try {
    // critical section
} finally {
    lock.unlock();      // lock released after this
}
```

The `finally` block ensures that `lock.unlock()` is called even if there is an exception or return statement in the critical section!

## A Locked Counter

```java
public class Counter {
    long count = 0;
    Lock lock = new SomeLockImplementation();
    
    public long getCount () { return count; }
    
    public void increment () {
        lock.lock();
        try {
		
            count++;
			
        } finally {
		
            lock.unlock();
			
        }
    }
    
    // should probably lock this too...
    public void reset () { count = 0; }
}
```

## Alright

- Now we know how to *use* locks
- But how can we *implement* one?

# Interlude: Events and Timing

## Convenient Assumptions I

- Executions of programs/protocols/algorithms consist of discrete **events**
    + e.g., perform elementary arithmetic, logic, comparison
	+ read or write values; send or receive messages
	+ call or return from a method/function

## Convenient Assumptions II

- Events for a *single* thread/process occur **sequentially**
    + given any two distinct events, one precedes the other
	
<div style="margin-bottom: 8em"></div>

	
## Convenient Assumptions III

- Events for different threads/processes can occur **concurrently**
    + caveat: what if multiple threads concurrently write to same location?
	+ only one value written
	+ treat written value as latest event
- Next week: formally define "linearizability"
	
<div style="margin-bottom: 8em"></div>


## Timing of Events

Wall clock time:

- Every event $$a $$ has an associated time $$t_a $$ at which the event occurs
    + if $$a $$ precedes $$b $$, then $$t_a < t_b$$.
	
- Often $$t_e $$ is time an operation completes
    + if process $$P_1 $$ writes to a register at time $$t_1 $$, then any process $$P_2 $$ reading the register at time $$t_2 \geq t_1$$ will read what $$P_1 $$ wrote.
	
<div style="margin-bottom: 8em"></div>



	
## Ordering of Events

- If event $a$ precedes $b$ (i.e., $t_a < t_b$) write $a \to b$
- If $a_1 \to a_2$, can associate an **interval** $$I_A = (a_1, a_2)$$
- Say $$I_A = (a_1, a_2)$$ **precedes** $$I_B = (b_1, b_2)$$ if $$a_2 \to b_1$$
    + similarly, $$I_A \to b \iff a_2 \to b$$
	+ $$a \to I_B \iff a \to b_1$$
	
<div style="margin-bottom: 8em"></div>

## Are These Assumptions Justified?

**NO!**

## The Real World

Things are not so nice!

- Even elementary operations do not happen instantaneously!
    + writing to a register takes some time
	+ [**metastability**](https://en.wikipedia.org/wiki/Metastability_(electronics)) can occur

- Compilers, operating systems, and hardware make decisions that are out of our control!
    + these choices often privilege performace over correctness

## Try to Rely on Robust Assumptions

- Our protocols and analysis allow for some wiggle-room
    + rely on principles like "flag principle"
	
	> if two processes (1) raise flags then (2) check the other's flag, then at least one will see the flag raised
		
	remains true, regardless of precise ordering of events
	
	+ Still need to assume events can be ordered for this to be valid


## Still Though

*We have to work very carefully to ensure that our assumptions are as close to reality as possible!*

1. Make reasonable, informed assumptions
2. Write code such that the assumptions are most likely to be correct

When the assumptions *are* correct, our protocols are guaranteed to work

## Going Forward

We will continue to make unjustified assumptions about how computers behave

- Assumptions will 
    1. help us reason about protocol correctness
	2. help us appreciate how subtle these problems are, even in an idealized setting
	3. allow us to *prove* correctness of protocols in idealized models of computation
	
- We will see later
    1. how to make our computers come as close as possible to satisfying our assumptions
	2. turn theory into practice
	
## Another Warning

Treat code for the next few lectures as "Java-like pseudo-code"

- follow Java syntax conventions
- may not be compilable Java


# The Peterson Lock

## Yet Another Protocol

Previous protocol satisfied

- mutual exclusion
- deadlock-freedom

but not

- starvation freedom

We want more!

## Fresh Idea

- Have a shared field that can be written by either process

- If concurrently written by two threads, then exactly one write operation succeeds
    + *which* thread's write succeeds is arbitrary
	
- How can we use shared field to break symmetry?

<div style="margin-bottom: 8em"></div>
	
## Symmetry Breaking Idea

Consider:

- Process $$A $$ and $$B $$ both write names in same field
- Processes repeatedly read from field
- Eventually, they will agree on name written in field
- That process is **victim**
    + victim defers to other process
	
<div style="margin-bottom: 8em"></div>


## Peterson Lock (2 Threads)

Fields:

1. flag (boolean) for each thread
    + indicates intent to acquire lock
    
2. value (int) shared between threads
    + indicates identity of victim
	
<div style="margin-bottom: 8em"></div>
	
## Acquiring Peterson Lock

1. Set my flag to be `true`
2. Set myself to be `victim`
3. While other's flag is `true` and I am victim:
    + wait
	
## Releasing Peterson Lock

1. Set my flag to `false`

## A Bit More Formally

In Java-ish Pseudocode

```java
class Peterson implements Lock {
    private boolean[] flag = new boolean[2];
	private int victim;
	
	public void lock () {
	   int i = ThreadID.get(); // get my ID, 0 or 1
	   int j = 1 - i;          // other thread's ID
	   
	   flag[i] = true;         // set my flag
	   victim = i;             // set myself to be victim
	   while (flag[j] && victim == i) {
	       // wait
	   }
	}
	
	public void unlock () {
	    int i = ThreadID.get();
		flag[i] = false;
	}
}
```

## Proof of Mutual Exclusion I

Suppose not... 

- $$A $$ and $$B $$ both in critical section (CS) at same time

## Proof of Mutual Exclusion II

- Actions of $$A $$:
    + $$(A.1)$$ writes `flag[A] = true`
	+ $$(A.2)$$ writes `victim = A`
	+ $$(A.3)$$ reads `flag[B]`
	+ $$(A.4)$$ reads `victim`

- Actions of $$B $$:
    + $$(B.1)$$ writes `flag[B] = true`
    + $$(B.2)$$ writes `victim = B`
	+ $$(B.3)$$ reads `flag[A]`
	+ $$(B.4)$$ reads `victim`
	
## Proof of Mutual Exclusion III

Suppose $$(B.2) \to (A.2)$$:

- i.e., $$A $$ wrote to `victim` last

- if not, continue argument with roles of $$A $$ and $$B $$ reversed

## Timelines

<div style="margin-bottom: 18em"></div>


## Conclusion

The Peterson lock satisfies mutual exclusion

## Proof of Starvation-Freedom I

Suppose not:

- Some thread runs forever in `lock()` method
- Assume it is thread $$A $$

```java
	public void lock () {
	   int i = ThreadID.get(); // get my ID, 0 or 1
	   int j = 1 - i;          // other thread's ID
	   
	   flag[i] = true;         // set my flag
	   victim = i;             // set myself to be victim
	   while (flag[j] && victim == i) {
	       // wait
	   }
	}
```

- $$A $$ is stuck in `while` loop

## Proof of Starvation-Freedom II

$$A $$ stuck in:

```java
	   while (flag[B] && victim == A) {
	       // wait
	   }
```

- If $$B $$ leaves CS, $$B $$ sets `flag[B] = false`
- If $$B $$ calls `lock()` again, sets `victim = B` 
- In in either case, $$A $$ eventually breaks out of loop

So

- Must be that $$B $$ also stuck in `lock()` call

## Proof of Starvation-Freedom III

- Must be that $$B $$ also stuck in `lock()` call
- But then:
    + `flag[B] && victim == A` is true ($$A $$ in `while` loop)
	+ `flag[A] && victim == B` is true ($$B $$ in `while` loop)
- This is a contradiction!
    + `victim` cannot be both `A` and `B`
	
## Conclusion

- Peterson lock satisfies
    + mutual exclusion
	+ starvation-freedom
	
- Therefore
    + Peterson lock also satisfies deadlock-freedom
	
Nice!

## For Your Consideration I

How can we deal with more than two threads?

- Can the Peterson lock be generalized to multiple threads?
- Is there a better way?

## Some Things to Consider II

- Might want more than one lock
    + e.g., multiple distinct critical sections that *can* be executed concurrently
	+ object with two counters, could have one lock for each counter
	+ different counters incremented at same time is fine
	
## Some Things to Consider III
Interdependencies between locked objects can cause trouble

- individual locks well-behaved
- contention between *different* locks still cause deadlock
- e.g., task requires two locks to be acquired:
     + one thread acquires one lock
	 + other thread acquires other lock
	 + both waiting on each other to finish



