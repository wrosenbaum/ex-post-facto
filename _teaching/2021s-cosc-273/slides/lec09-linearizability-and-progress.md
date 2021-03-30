---
title: "Lecture 09: Linearizability and Progress"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 09: Linearizability and Progress

## Last Time

- Sequential consistency:
    + consistent with *program order*
	+ consistent with *sequential specification*
- Properties of sequential consistency
    + not compositional
	    - sequentially consistent components do not imply sequential consistency
	+ nonblocking
	    - methods can give SC response without waiting for other pending method calls
		
## Today

Linearizable objects and executions

## Weird Thing about SC

SC makes no reference to invocation/response intervals for method calls

- Why bother with time intervals if we ignore them (execept relative order for single threads)?

## Another idea

- Make sure execution is consistent with timing of method calls
- Consider sequential executions consistent with each method call taking effect at some *instant* during the method call

## Same Example, Fewer Options

![](/assets/img/consistency/mult-ops.png){: width="100%"}

Can only change relative order of method calls if they overlap

<div style="margin-bottom: 8em"></div>

## Linearization Points

A **linearization point** is a point in a method call where method "takes effect"

- all events after linearization point see effect of method call
- linearization points must be distinct (correspond to some atomic operation)
	
## Example of Linearization Points

![](/assets/img/consistency/linearization-pts.png){: width="100%"}

<div style="margin-bottom: 8em"></div>

## Equivalent Sequential Execution

![](/assets/img/consistency/equivalent-seq-exec.png){: width="100%"}

<div style="margin-bottom: 8em"></div>

## An Alternative Sequential Execution

![](/assets/img/consistency/alt-seq-exec.png){: width="100%"}

<div style="margin-bottom: 8em"></div>

## Linearizability

A concurrent execution is **linearizable** if:

- exists a linearization point in each method call such that execution is consistent with sequential execution where method calls occur in order of corresponding linearization points

An implementation of an object is linearizable if:

- it guarantees every execution is linearizable

## Comparing Linearizability and SC

1. Does linearizability imply sequential consistency?
2. Does sequential consistency imply linearizability?

<div style="margin-bottom: 12em"></div>

## Linearizability $\implies$ SC

Must show:

1. Maintains program order
2. Satisfies sequential specification

<div style="margin-bottom: 12em"></div>

## SC $\nRightarrow$ Linearizability

![](/assets/img/consistency/compositionality-p.png){: width="100%"}

<div style="margin-bottom: 12em"></div>

## Compositionality

Recall this execution with 2 queues (not sequentially consistent)

![](/assets/img/consistency/compositionality.png){: width="100%"}

<div style="margin-bottom: 12em"></div>

## But...

`p` and `q` are not linearizable in this execution!

![](/assets/img/consistency/compositionality.png){: width="100%"}

<div style="margin-bottom: 12em"></div>

## Two More Questions

1. Are linearizable objects compositional?
2. Is linearizability a nonblocking condition?

## Are Linearizable Objects Compositional?

<div style="margin-bottom: 18em"></div>


## Is linearizability a nonblocking condition?

<div style="margin-bottom: 18em"></div>

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

<!-- ## Properties of Nonblocking Progress -->

<!-- - Progress is guaranteed even if some thread stalls -->
<!-- - Wait-freedom gives *maximal progress* -->
<!-- - Lock-freedom gives *minimal progress* -->
<!--     + starvation can still occur -->
<!-- - Actual progress depends on *scheduler* -->
<!--     + determines which threads make steps -->
	
<!-- <div style="margin-bottom: 4em"></div> -->


<!-- ## Blocking Progress Conditions -->

<!-- - **deadlock-free**: whenever *all* pending method calls take steps, each method call completes in a finite number of steps -->

<!-- <div style="margin-bottom: 6em"></div> -->

<!-- - **starvation-free**: whenever *all* pending methods take steps, *some* method call completes in a finite number of steps -->

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

## Coming Up

Implementations!

- locks
- data structures

# Homework Questions

## Question 1

Suppose $$n $$ threads call the `visit()` method of a `Bouncer` object. Argue that the following hold:

1. At most one thread gets the value `STOP`.
2. At most $$n - 1$$ threads get the value `DOWN`.
3. At most $$n - 1$$ threads get the value `RIGHT`.

## Question 2

Consider a 2D triangular array of `Bouncer` objects arranged as follows:

![](/assets/img/2021s-cosc-273/hw02/bouncer.png){: width="50%"}

## Question 2, Continued

Suppose: 

- All threads start by calling `visit()` on `Bouncer` 0. 
- Whenever a thread visits a `Bouncer`, if the `Bouncer` returns `STOP`, the thread adopts the number of the `Bouncer` as its ID. 
- If `DOWN` is returned, the thread then visits the `Bouncer` below; if `RIGHT` is returned, the thread visits the `Bouncer` to the right.

1. Show that for a sufficiently large array of `Bouncer`s, every thread will eventually `STOP` at some `Bouncer`, thereby adopting its ID.
2. If the number $$n $$ of threads is known in advance, how many `Bouncer`s are required to ensure that all threads adopt an ID?

## Illustrations

<div style="margin-bottom: 18em"></div>




