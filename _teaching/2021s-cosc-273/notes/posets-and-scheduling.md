---
title: "Partially Ordered Sets and Scheduling"
description: "We introduce the notion of a partially ordered set and a general scheduling problem."
layout: page
---

## Introduction

In discussing Amdahl's law ([Lecture 02](/teaching/2021s-cosc-273/slides/lec02-limitations-of-parallelism/#/computational-limits-of-parallelism)), we assumed that a computational task consisted of many elementary operations, some of which had to be computed sequentially, while others could be computed in parallel. Amdahl's law then gives a quantitative measure of the **speedup**, $$S $$---i.e., how much faster the task can be performed by using $$n$$ parallel processors:

$$
S = \frac{1}{1 - p + \frac{p}{n}}
$$

Here, $$p$$ is the proportion of operations that can be performed in parallel. 

While Amdahl's law gives a valuable heuristic for how much we might expect parallelism to speed up a computation, it is not typically the case that we can classify operations simply as "parallel" or "sequential." Instead, a task might consist of several sub-tasks, some of which need to be completed before others are started.

------------

**Example** (getting dressed). Consider the task of getting dressed. Depending on the particulars of what I intend to wear, this task can be decomposed into several sub-tasks, such as:

- put on underwear
- put on shirt
- put on left sock
- put on right sock
- put on pants
- put on left shoe
- put on right shoe
- put on sweater

Some of these tasks need to be performed in a particular order---e.g., I must put on my left sock before putting on my left shoe. For others, however, the order is not important. For example, I can put on my left sock before or after putting on my shirt. The following diagram indicates the relationships between the sub-tasks: there is an arrow from one task to another whenever the former task must be completed before the latter.

![](/assets/img/posets-and-scheduling/clothes-poset.svg){: width="75%"}

(Icons created by by [Laurin Kraan](https://thenounproject.com/laurinkraan/collection/clothes-and-accessories/))

------------

In what follows, we will describe a mathematical object---a *partially ordered set*, a.k.a. *poset*---that gives a way of formalizing relationships between elements as in the previous example. Using this formalism we will describe the general problem of scheduling tasks for optimal latency.


## Partially Ordered Sets

A **partially ordered set** (or **poset**) consists of a set $$X $$ of elements together with a binary relation $$\prec $$ satisfying the following properties:

- *transitivity:* if $$x \prec y$$ and $$y \prec z$$, then $$x \prec z$$
- *anti-symmetry:* if $$x \prec y$$, then $$y \nprec x$$ (i.e., it is not the case that $$x \prec y$$ and $$y \prec x$$)

In a poset, it may be the case that two elements $$x $$ and $$y $$ satisfy $$x \nprec y$$ and $$y \nprec x$$. We call such elements **incomparable**. Otherwise, if either $$x \prec y$$ or $$y \prec x$$, we say $$x $$ and $$y $$ are **comparable**.

**Examples.** Here are a few examples of posets:

1. The natural numbers $$\mathbf{N} = \{0, 1, 2, \ldots\}$$ together with the standard $$< $$ relation are a poset. Note since every distinct pair of numbers $$x $$ and $$y $$ satisfy either $$x < y$$ or $$y < x$$, the relation $$< $$ is said to be a **total order** on the natural numbers.

2. Clothing items form a poset, where the $$\prec $$ relation indicates if one item must be put on before another. Using our previous clothing items:

    $$
    X = \{\text{underwear}, \text{shirt}, \text{L sock}, \text{R sock}, \text{pants}, \ldots\}.
    $$

    For the precedence relationship, we have, for example

    $$
	\text{underwear} \prec \text{pants} \prec \text{L shoe}.
	$$
	
	We also have $$\text{L sock} \prec \text{L shoe}$$. The elements $$\text{L sock}$$ and $$\text{pants} $$ are incomparable: either one can be put on before the other.	

3. Intervals of time. Suppose $$t_0$$ and $$t_1$$ are distinct times with $$t_0 < t_1$$---i.e., $$t_0 $$ occurs before $$t_1 $$. Then we denote the interval $$T = [t_0, t_1]$$ which consists of all times between $$t_0 $$ and $$t_1 $$. We can define a precedence relation $$\prec $$ on the set of *intervals* of time as follows. If $$T = [t_0, t_1]$$ and $$S = [s_0, s_1]$$, then 

    $$T \prec S \iff t_1 \leq s_0$$.
	
	That is, $$T $$ precedes $$S $$ if and only if the interval $$T$$ ends no later than $$S $$ begins. In this poset, intervals of time are comparable if and only if they do not overlap (except possibly at one endpoint). If two intervals do overlap, then they are said to be **concurrent**. That is, two intervals of time are concurrent if and only if they are incomparable under the relation $$\prec $$ defined above. 
	
	We will frequently use the notation $$\to $$ to denote precedence of time intervals with respect to the $$\prec $$ defined above. That is, we will write $$T \to S$$ to indicate that $$T $$ ends before $$S $$ begins. 
	
	
Given two elements $$x $$ and $$y $$ in $$X $$, we say that $$y $$ **covers** $$x $$  if $$x \prec y$$ and there is no element $$z $$ satisfying $$x \prec z \prec y$$. That is, there is no element "between" $$x$$ and $$y$$ in the poset. In our clothing example, $$\text{sweater }$$ covers $$\text{shirt }$$ because there is no item of clothing that must be put on between a shirt and a sweater. However, $$\text{L shoe}$$ does not cover $$\text{underwear} $$, because $$\text{pants} $$ should be put on after $$\text{underwear} $$ and before $$\text{L shoe}$$. 

Often, we can represent posets in a visually appealing way by drawing all of its elements as well as arrows from each $$x $$ to $$y $$ whenever $$x $$ covers $$y $$. Such a diagram is called a **Hasse diagram**. At the beginning of this note, we have drawn the Hasse diagram for the "getting dressed" poset. 

If $$x \prec y$$, then there may not be an arrow directly from $$x $$ to $$y $$ in the Hasse diagram. However, there will be a directed path---i.e., a sequence of arrows---going from $$x $$ to $$y $$. On the other hand, if $$x $$ and $$y $$ are incomparable, then there is neither a directed path from $$x $$ to $$y $$ nor a directed path from $$y $$ to $$x $$. 

Note that because of the anti-symmetry property of posets, a Hasse diagram does not contain cycles (sequences of arrows $$x_0 \to x_1 \to \cdots \to x_k \to x_0$$ that return to their starting points). Indeed, if we had such a cycle containing an element $$x $$, say, then we'd have $$x \prec x$$, which is not allowed by the anti-symmetry property of posets.

## Scheduling for Optimal Latency

Using the formalism of posets, we can now define the problem of computing an optimal schedule for parallel computation. Assume that some task $$T $$ can be decomposed into sub-tasks $$T = \{T_1, T_2, \ldots, T_n\}$$. In general, we can encode the relationships between the various $$T_i$$ as a poset, where $$T_i \prec T_j$$ if (sub)task $$T_i$$ needs to be performed before $$T_j$$. Finally, each task $$T_i $$ has an associated **weight** $$w_i$$, which indicates the amount of time it takes a single processor to perform $$T_i $$.

To complete the task $$T $$, we must complete all of the sub-tasks $$T_1, T_2, \ldots, T_n$$. As computer scientists, our goal is to complete all of the tasks as quickly as possible. Moreover, we may have multiple processors that can perform some tasks in parallel. The only limitation is that if $$T_i \prec T_j$$, then $$T_i $$ must be completed (by some processor) before task $$T_j $$ can be begun (possibly by another processor). However, if $$T_i $$ and $$T_j $$ are incomparable, then they can be performed in parallel (by two different machines).

Given $$k $$ processors $$P_1, P_2, \ldots, P_k$$ a **schedule** for $$T $$ assigns each sub-task $$T_i $$ a processor $$P_j $$ as well as a time $$t_i $$ at which $$P_j $$ should start task $$T_i $$. Observe that if a processor starts $$T_i$$ at times $$t_i$$, then the task will complete at time $$t_i + w_i$$, where $$w_i $$ is the weight of $$T_i $$. A schedule is **feasible** if:

1. no single processor is performing multiple tasks at the same time (i.e., if a processor is assigned $$T_i $$ at time $$t_i $$ and $$T_j $$ at time $$t_j > t_i$$, then $$t_j \geq t_i + w_i$$), and

2. for every pair of tasks $$T_i $$ and $$T_j $$ with $$T_i \prec T_j$$, $$T_j$$ is scheduled to start after (or at the same time) $$T_i$$ completes (i.e., $$t_j \geq t_i + w_i$$). 

The **latency** of a schedule is the total time between when the first task is scheduled to when the last task completes. Our goal is to find a schedule with the smallest latency.

-----------

**Example.** Suppose a task $$T $$ is comprised of 5 sub-tasks, $$T_1, T_2, T_3, T_4, T_5$$ with the precedence relation below:

![](/assets/img/posets-and-scheduling/generic-poset.svg){: width="50%"}

That is, $$T_1 $$ must be performed before $$T_2, T_3$$, and $$T_4$$ (which can be performed in any order), and all other tasks must be performed before $$T_5 $$. Suppose the weights of the tasks are $$w_1 = w_2 = w_5 = 1$$, $$w_3 = 2$$ and $$w_4 = 3$$. That is, $$w_1$$ takes one second (say) to perform, etc.

If we have a single processor, the best we can do is schedule all tasks sequentially, making sure that all of a task's preceding tasks are performed before scheduling a task. For example, we could schedule:

|time &nbsp;|$$P_1 $$ (task latency)|
|-----------|------------------------|
| 0 | $$T_1 $$ (1)|
| 1 | $$T_2 $$ (1)|
| 2 | $$T_3 $$ (2)|
| 4 | $$T_4 $$ (3)|
| 7 | $$T_5 $$ (1)|
| 8 | complete|
|---|---------|

Clearly, we can't do better than latency 8 with a single processor.  If we have two processors, the problem becomes more interesting, because we have choices to make. No matter what, $$T_1 $$ has to complete before any processor starts $$T_2, T_3$$ or $$T_4 $$. But then we have to choose which to schedule next. For example, we can schedule:

| time &nbsp; | $$P_1$$ &nbsp; | $$P_2$$ |
|-------------|----------------|---------|
| 0 | $$T_1$$ (1) | (idle) |
| 1 | $$T_2$$ (1) | $$T_3$$ (2) |
| 2 | $$T_4$$ (3) | $$\vdots$$ |
| 3 | $$\vdots$$  | (idle) |
| 5 | $$T_5$$ (1) | (idle) |
| 6 | complete &nbsp;| complete|

It turns out we can do better! Notice that in the previous schedule $$T_2 $$ and $$T_3 $$ to both start at time 1, while $$T_4 $$ (which has the largest weight/latency) doesn't start until after $$P_1 $$ completes $$T_2 $$. However, by starting $$T_4 $$ immediately after $$T_1 $$ completes, we get:

| time &nbsp; | $$P_1$$ &nbsp; | $$P_2$$ |
|-------------|----------------|---------|
| 0 | $$T_1$$ (1) | (idle) |
| 1 | $$T_4$$ (3) | $$T_2$$ (1) |
| 2 | $$\vdots$$ | $$T_3$$ (2) |
| 4 | $$T_5 $$  | (idle) |
| 5 | complete &nbsp;| complete|

This is the best possible schedule---even adding more processors cannot improve the latency. (To see this, observe that we have $$T_1 \prec T_4 \prec T_5$$, so these tasks must be performed sequentially. Since $$w_1 = w_5 = 1$$ and $$w_4 = 3$$, performing these tasks sequentially has latency $$w_1 + w_4 + w_5 = 5$$, which is what the schedule above achieves.)

-----------

In general, there are many ways to schedule a given set of tasks on parallel machines, and different schedules may have vastly different latencies. Unfortunately, there is no known efficient algorithm for finding the best (i.e., lowest latency) schedule. In fact, this problem is known to be [NP-hard](https://en.wikipedia.org/wiki/NP-hardness). Thus, under a [widely-believed conjecture](https://en.wikipedia.org/wiki/P_versus_NP_problem), there is no efficient procedure for finding an optimal-latency schedule. Even if we restrict to relatively simple scenarios---e.g., all tasks have the same latency, or there are only two processors and all tasks have latency either 1 or 2---the problem of finding an optimal schedule remains intractable.

Another difficulty in scheduling processes is that we might not know the latency of an individual sub-task in advance. That is, we might not know how long a task takes to complete until we've completed it. In this situation, the best we can do is construct a schedule **greedily**. That is, whenever a process is idle, it checks if there is a task that can be performed (all of its preceding tasks have been completed) but is not being performed by another processor; if so, it begins one such task. More formally, a schedule is a **greedy schedule** if for all times $$t $$, each processor is either performing a task at time $$t $$, or there are no available tasks (every incomplete task is either in the process of being performed, or one of its preceding tasks has not been completed).

------------

**Example.** Both of the 2-processor schedules for the previous example are greedy schedules. However, the second schedule has smaller latency. Thus, different greedy schedules may have different latencies. 

------------

**Exercise.** Consider again the first example with putting on clothes. Suppose the tasks have the following latencies:

| **task** &nbsp; | **latency** (s) |
|-----------------|-----------------|
| underwear | 10 |
| pants | 30 |
| each sock | 15 |
| each shoe | 30 |
| shirt | 10 |
| sweater | 10 |

What is the minimum latency achievable for getting dressed with 1 processor? With 2 processors? With 3 processors?

Suppose we perform the getting dressed tasks greedily with 2 processors. What is the worst-case latency among all *greedy* schedules? 

------------



