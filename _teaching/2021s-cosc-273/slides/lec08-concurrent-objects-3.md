---
title: "Lecture 08: Concurrent Objects 3"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 08: Concurrent Objects 3

## Outline

1. Sequential Consistency
2. Linearizability
3. Progress Conditions

## Last Time

What is "correct" behavior for a concurrent execution of a data structure?

# Sequential Consistency

## Two Natual Desiderata

Method calls/responses consistent with *some* sequential execution

Method calls can be ordered such that:

1. they are consistent with **program order**

    - if a *single thread* calls `methodOne()` before `methodTwo()`, then `methodOne()` $\to$ `methodTwo()` in sequential execution
	
2. pre/post conditions for each method call satisfy data structure's sequential specification

Such executions are **sequentially consistent**

## Definition

An execution is **sequentially consistent** if all method calls can be ordered such that:

1. they are consistent with program order
2. they meet object's sequential specification

An *implementation* of an object is sequentially consistent if it guarantees *every* execution is sequentially consistent

## Example

What are possible outcomes of `deq()` calls in a sequentially consistent execution?

![](/assets/img/consistency/mult-ops.png){: width="100%"}

<div style="margin-bottom: 12em"></div>

## Sequential Consistency is Weak

- Method calls can appear interleaved in *any* order that maintains program order
- No guarantees for "real time" order
    + (partially) order methods w.r.t. real time

![](/assets/img/consistency/mult-ops.png){: width="100%"}

## Sequential Consistency is Nonblocking

Consider concurrent execution:

- Pending method call (invoked, but no response yet)
- Then: exists a response that can be made immediately that does not violate SC

It is possible to achieve SC without needing to wait for response from other pending methods

- May still be difficult to figure out *which* response will give SC execution

## Nonblocking Example

![](/assets/img/consistency/concurrent-exec.png){: width="100%"}

<div style="margin-bottom: 8em"></div>

## A Subtler Issue

Suppose we have a program two queues, `p` and `q`

- Implementation of queue guarantees sequentially consistency

Must an execution of a program with `p` and `q` be sequentially consistent?

<div style="margin-bottom: 8em"></div>

## An Execution

![](/assets/img/consistency/compositionality.png){: width="100%"}

<div style="margin-bottom: 12em"></div>

## Sequentially Consistent for `p`

![](/assets/img/consistency/compositionality-p.png){: width="100%"}

<div style="margin-bottom: 12em"></div>

## Sequentially Consistent for `q`

![](/assets/img/consistency/compositionality-q.png){: width="100%"}

<div style="margin-bottom: 12em"></div>

## But is Execution SC?

![](/assets/img/consistency/compositionality.png){: width="100%"}

<div style="margin-bottom: 12em"></div>


## Sequential Consistency is not Compositional

A property $\mathcal{P}$ is **compositional** if

- Whenever all objects in system satisfy $\mathcal{P}$, then system satisfies $\mathcal{P}$

Previous example shows:

- Sequential consistency is *not* compositional

## Sequential Consistency Is...

- ...Maybe the weakest reasonble guarantee we could expect of a concurrent implementation of an object
- ...Potentially allows for a lot of parallelism
    + SC is a nonblocking property
- ...Not compositional
    + a system made of SC components need not be SC!
	
<!-- # Linearizability -->

<!-- ## Weird Thing about SC -->

<!-- SC makes no reference to invocation/response intervals for method calls -->

<!-- - Why bother with time intervals if we ignore them (execept relative order for single threads)? -->

<!-- ## Another idea -->

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

<!-- ## Are Linearizable Objects Compositional? -->

<!-- <div style="margin-bottom: 18em"></div> -->


<!-- ## Are Linearizable Executions Nonblocking? -->

<!-- <div style="margin-bottom: 18em"></div> -->

<!-- # Progress Conditions -->

<!-- ## Is Nonblocking Property Practical? -->

<!-- Nonblocking property $\implies$ *existence* of consistent response to pending method calls -->

<!-- - Does not ensure that such a response can be *found* easily -->

<!-- <div style="margin-bottom: 8em"></div> -->

<!-- ## Nonblocking Progress Conditions -->

<!-- Conditions for implementations: -->

<!-- - **Wait-freedom**: pending method invocation always completes in a finite number of steps -->

<!-- - **Lock-freedom**: among all pending method calls, some method completes in a finite number of steps -->

<!-- <div style="margin-bottom: 8em"></div> -->




<!-- ## Properties of Nonblocking Progress -->

<!-- - Progress is guaranteed even if some thread stalls -->
<!-- - Wait-freedom gives *maximal progress* -->
<!-- - Lock-freedom gives *minimal progress* -->
<!--     + starvation can still occur -->
<!-- - Actual progress depends on *scheduler*  -->
<!--     + determines which threads make steps -->
	
<!-- <div style="margin-bottom: 4em"></div> -->


<!-- ## Blocking Progress Conditions -->

<!-- - **deadlock-free**: whenever *all* pending method calls take steps, each method call completes in a finite number of steps -->

<!-- - **starvation-free**: whenever *all* pending methods take steps, *some* method call completes in a finite number of steps -->

<!-- ## Blocking vs Nonblocking Progess -->

<!-- Nonblocking progress -->

<!-- - guarantees progress for any scheduler -->
<!-- - valid even if a process crashes -->

<!-- Blocking progress -->

<!-- - progress only guaranteed for *fair* schedulers -->
<!-- - if a process crashes, progress not guaranteed -->

<!-- ## Up Next -->

<!-- 1. Better multithreading through executors -->
<!-- 2. Implementations! -->
<!--     - locks -->
<!-- 	- data structures -->





