---
title: "Lecture 10: More Linearizability and Progress"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 10: More Linearizability and Progress

## Overview

1. Counter, Revisited
2. Progress Condiditions

## Note on Pixels and Points

<div style="margin-bottom: 18em"></div>

# Counter, Revisited

## Recall

A concurrent execution is **linearizable** if:

- exists a linearization point in each method call such that execution is consistent with sequential execution where method calls occur in order of corresponding linearization points

An *implementation* of an object is linearizable if:

- it guarantees every execution is linearizable

## A Bad Counter

```java
public class Counter {
    int count = 0;
    
    public void increment () {
        ++count;
    }
    
    public int read () {
        return count;
    }
}
```

Why is this bad with multiple threads, even if read/write operations are atomic?

<div style="margin-bottom: 8em"></div>

## A Better Counter?

For two threads:

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

Is this better?

## Download the Worksheet

- [**More `Counter` Examples**](https://docs.google.com/document/d/19rbzXGILiFejnj2L-7qXl6xFqTh0Ne3HEWfOpOJQZT0/edit?usp=sharing)

## Is `TwoCounter` Linearizable?

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

<div style="margin-bottom: 6em"></div>

## More Threads!

```java
public class ThreeCounter {
    int[] counts = new int[3];
	
    public void increment (int amt) {
        int i = ThreadID.get(); // thread IDs are 0, 1, and 2
        int count = counts[i];
        counts[i] = count + amt;		
    }
	
    public int read () {
        int count = counts[0];
        count = count + counts[1];
        count = count + counts[2];		
        return count;
    }	
}
```

Is `ThreeCounter` Linearizable?

<div style="margin-bottom: 4em"></div>

## Is `ThreeCounter` Linearizable?

<div style="margin-bottom: 18em"></div>

## Conclusion

- Linearizability is...
    + ...a reasonable correctness condition
	+ ...nonblocking
	+ ...compositional
	+ ...subtle to reason about
	
You will work through more problems on linearizability on Homework 3 and Quiz 3.

<!-- # Progress Conditions -->

<!-- ## Is Nonblocking Property Practical? -->

<!-- Nonblocking property $\implies$ *existence* of consistent response to pending method calls -->

<!-- - Does not ensure that such a response can be *found* easily -->

<!-- <div style="margin-bottom: 8em"></div> -->

<!-- ## Scheduler -->

<!-- Given a set of (concurrent) method calls a **scheduler** chooses sequence of methods that make steps -->

<!-- - in computers, OS is typically scheduler -->
<!-- - scheduler may be fair, may not be -->
<!-- - want progress guarantees independent of scheduler -->

<!-- <div style="margin-bottom: 6em"></div> -->


<!-- ## Nonblocking Progress Conditions -->

<!-- Conditions for implementations: -->

<!-- - **Wait-freedom**: pending method invocation always completes in a finite number of steps -->

<!-- <div style="margin-bottom: 6em"></div> -->

<!-- - **Lock-freedom**: among all pending method calls, some method completes in a finite number of steps -->

<!-- <div style="margin-bottom: 6em"></div> -->

<!-- ## Question -->

<!-- Is `TwoCounter` lock-fee? Wait-free? -->

<!-- ```java -->
<!-- public class TwoCounter { -->
<!--     int[] counts = new int[2]; -->
	
<!--     public void increment (int amt) { -->
<!--         int i = ThreadID.get(); // thread IDs are 0 and 1 -->
<!--         int count = counts[i]; -->
<!--         counts[i] = count + amt;		 -->
<!--     } -->
	
<!--     public int read () { -->
<!--         int count = counts[0]; -->
<!--         count = count + counts[1]; -->
<!--         return count; -->
<!--     }	 -->
<!-- } -->
<!-- ``` -->

<!-- <div style="margin-bottom: 6em"></div> -->



<!-- ## An Atomic Primitive -->

<!-- `AtomicInteger` class: -->

<!-- ```java -->
<!-- boolean compareAndSet(int expectedValue, int newValue); -->
<!-- ``` -->

<!-- Specification: -->

<!-- - if `ai`'s value is `expectedValue`, then -->
<!--     + after call, `ai`'s value is `newValue` -->
<!-- 	+ method returns `true` -->
<!-- - if `ai`'s value is not `expectedValue`, then -->
<!--     + after call, `ai`'s value is unchanged -->
<!-- 	+ method returns `false` -->

<!-- ## A Silly Counter -->

<!-- ```java -->
<!-- public class SillyCounter { -->

<!--     AtomicInteger ai = new AtomicInteger(0); -->
	
<!--     public void increment (int amt) { -->
<!--         int val = ai.get(); -->
<!--         while (!ai.compareAndSet(val, val + amt)) { -->
<!--             val = ai.get(); -->
<!--         } -->
<!--     } -->
	
<!--     public int read () { -->
<!--         return ai.get(); -->
<!--     } -->
	
<!-- } -->
<!-- ``` -->

<!-- ## Does `SillyCounter` Work? -->

<!-- ```java -->
<!-- public class SillyCounter { -->

<!--     AtomicInteger ai = new AtomicInteger(0); -->
	
<!--     public void increment (int amt) { -->
<!--         int val = ai.get(); -->
<!--         while (!ai.compareAndSet(val, val + amt)) { -->
<!--             val = ai.get(); -->
<!--         } -->
<!--     } -->
	
<!--     public int read () { -->
<!--         return ai.get(); -->
<!--     } -->
	
<!-- } -->
<!-- ``` -->

<!-- <div style="margin-bottom: 8em"></div> -->

<!-- ## Is `SillyCounter` Lock-free? -->

<!-- ```java -->
<!--     public void increment (int amt) { -->
<!--         int val = ai.get(); -->
<!--         while (!ai.compareAndSet(val, val + amt)) { -->
<!--             val = ai.get(); -->
<!--         } -->
<!--     } -->
<!-- ``` -->

<!-- <div style="margin-bottom: 12em"></div> -->

<!-- ## Is `SillyCounter` Wait-free? -->

<!-- ```java -->
<!--     public void increment (int amt) { -->
<!--         int val = ai.get(); -->
<!--         while (!ai.compareAndSet(val, val + amt)) { -->
<!--             val = ai.get(); -->
<!--         } -->
<!--     } -->
<!-- ``` -->

<!-- <div style="margin-bottom: 12em"></div> -->




<!-- ## Properties of Nonblocking Progress -->

<!-- - Progress is guaranteed even if some thread stalls -->
<!--     + e.g., scheduler stops scheduling a thread's method call -->
<!-- - Wait-freedom gives *maximal progress* -->
<!-- - Lock-freedom gives *minimal progress* -->
<!--     + starvation can still occur -->
<!-- - Actual progress depends on *scheduler* -->
<!--     + determines which threads make steps -->
	
<!-- <div style="margin-bottom: 4em"></div> -->


<!-- ## Blocking Progress Conditions -->

<!-- - **starvation-free**: whenever *all* pending methods take steps, *every* method call completes in a finite number of steps -->
<!--     + maximal (blocking) progress -->

<!-- <div style="margin-bottom: 6em"></div> -->

<!-- - **deadlock-free**: whenever *all* pending method calls take steps, *some* method call completes in a finite number of steps -->
<!--     + minimal (blocking) progress -->

<!-- <div style="margin-bottom: 6em"></div> -->

<!-- ## Blocking vs Nonblocking Progess -->

<!-- Nonblocking progress -->

<!-- - guarantees progress for any scheduler -->
<!-- - valid even if a process crashes -->

<!-- <div style="margin-bottom: 4em"></div> -->

<!-- Blocking progress -->

<!-- - progress only guaranteed for *fair* schedulers -->
<!-- - if a process crashes, progress not guaranteed -->

<!-- <div style="margin-bottom: 4em"></div> -->

<!-- ## Progress and Correctness Conditions are Indpendent -->

<!-- - Can have a data structure that is... -->
<!--     + ...sequentially consistent and wait-free -->
<!-- 	+ ...linearizable and lock-free -->
<!-- 	+ ...sequentially consistent and deadlock-free -->
<!-- 	+ ... -->
	
<!-- - Different implementations have different trade-offs -->

<!-- - Which implementation is best depends on application: -->
<!--     + how much synchronization is required? -->
<!-- 	    + how frequently is contention expected? -->
<!-- 	+ what correctness guarantee is required? -->
	

<!-- ## Coming Up -->

<!-- Implementations! -->

<!-- - locks -->
<!-- - data structures -->



