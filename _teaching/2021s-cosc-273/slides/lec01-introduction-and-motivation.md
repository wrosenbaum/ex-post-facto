---
title: "Lecture 01: Intro and Motivation"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 01: Intro and Motivation

## Outline

1. [Course Introduction and Structure](#course-introduction-and-structure)
2. [Motivating Questions](#motivating-questions)
3. [Why Parallel and Distributed Computing?](#why-parallel-and-distributed-computing)
4. [Motivating Examples](#motivating-examples)

# Course Introduction and Structure

## Expected Background (Programming)

- Object Oriented design in Java
    - classes and inheritance
	- interfaces
	- exception handling
	- generics

## Expected Background (Conceptual)

- Basic data structures:
    - linked lists
	- stacks 
	- queues
	- balanced trees
	- (hash tables)
- Supported operations, and their complexities

## Main Topics Covered

- multithreaded programming in Java
- mutual exclusion,
- concurrent objects,
- locks and contention resolution,
- blocking synchronization,
- concurrent data structures,
- scheduling, work distribution, and barriers,
- data parallelism (MapReduce and streams).

(Others as time allows.)

## Course Materials

- *The Art of Multiprocessor Programming* (Moodle -> Course Reserves)
- Notes (posted to Moodle)
- Recorded lectures


## Course Focus

- *Principles* of parallel computing:
    + conceptual & technical issues that are fundamental to parallel programming
	+ indpendent of computing technology
	+ want provable guarantees for behavior
	
- We care about *performance* but...
    + newest technologies will *not* be emphasized
	+ prefer methods that enhance our understanding of a problem
	
- Compare to
    + Data Mining (COSC 254)
	+ Performance Evaluation (COSC 365)



## Course Structure

- 2 Lectures / Week
    + guided discussion
    + small group discussion
	+ emphasize conceptual material
   
- 1 Lab / Week
    + emphasise technical/programming/performance
	+ open ended
	
- Accountability groups
    + meet once a week (you schedule)
	+ can be brief meeting
	

## Evaluation

- Coding/Lab Assignments (bi-weekly, individual): 20%
- Written/Theoretical Assignments (bi-weekly, small groups) 20%
- Quizzes (weekly, individual) 20%
- Participation (everyone!) 10%
- Final project (small groups) 30%


# Motivating Questions

## Main Goals

1. Write programs that are **correct**
    + *always* produce desired output (assuming no hardware errors...)
	
2. Write programs that **perform well**
    + many measures of performance
	+ our primary focus: speed and/or throughput
	+ other relevant measures:
		- space/memory 
		- communication (minimize) 
	    - power consumption (minimize)

## Program Correctness

Goal: Guarantee that a program actually solves the problem you intend.

- Is your *algorithm* correct?

    + Can you mathematically prove that your procedure produces the correct output in an idealized model of computation?

- Is your *implementation* correct?

    + Does your code faithfully implement the intended (correct) algorithm under normal operating conditions?

## Program Performance

- Theoretical performance:
    + How many elementary operations does your algorithm require? (Assume elementary operations have some nominal cost.)
	+ How does the number of operations *scale* with the size of the input?
	
- Practical performance:
    + How efficient is your program in practice?
	+ May vary from machine to machine, or even between executions on the same machine.
	+ Difficult to predict from first principles; use heuristics instead.

# Why Parallel Computing?

## History of Computing Power: Moore's Law

Transister density chip doubles every 2 years

![Moore's Law](/assets/img/moores-law.jpg){: width="75%"}

Transister density for Intel chips ([img source](https://doi.org/10.1145/3282307)).

## But Processor *Speed* Is Not Increasing!

| Year | Transistors | Clock speed | CPU model |
|------|-------------|-------------|-----------|
|1979|	30 k	|5 MHz	|8088
|1985|	300 k	|20 MHz	|386
|1989|	1 M	|20 MHz	|486
|1995|	6 M	|200 MHz |	Pentium Pro
|2000|	40 M	|2 000 MHz |	Pentium 4
|2005|	100 M	|3 000 MHz |	2-core Pentium D
|2008|	700 M	|3 000 MHz |	8-core Nehalem
|2014|	6 B	|2 000 MHz |	18-core Haswell
|2017|	20 B	|3 000 MHz  |	32-core AMD Epyc
|2019|	40 B	|3 000 MHz |	64-core AMD Rome

## Question

In what sense are computers "faster" now than in 2000?

- **Latency** has not improved (i.e., time to perform a single operation).
- Yet my current laptop is immeasurably faster than a desktop with a Y2K era Pentium 4 processor.
    + Why? How?
	
**Answer:** Parallelism!

## What is Parallelism?

The ability to perform multiple operations *simultaneously*.

Examples:

- bit-level parallelism (e.g., adding two 32-bit numbers)
- instruction-level parallelism (multiple elementary instructions at a time)
- multi-core: independent processors operating at same time on same computer
- distributed networks: clusters, server farms, internet
- chefs in a kitchen, ants in a colony, people on earth

## Promise of Parallelism

*"Many hands make light work."*

- More processors $$\implies $$ more operations per second!
- Greater throughput!
- Perform multiple operations at once!

## Perils of Parallelism

More processors, more problems

- Some computations need to be done sequentially in order
    + next step relies on result of current step
- Processors must *share* resources
    + communication and sychronization are costly
- Nondeterminism
    + different executions give different behavior
	+ algorithms must account for all possible executions!	

## Concurrent vs Parallel vs Distributed

- **Concurrent**: multiple processes under way at same time
- **Parallel**: multiple operations performed simultaneously
- **Distributed**: independent processes have indpendent inputs, communicate with each other

## Unavoidability of Parallel & Distributed Computing

Modern computing is inherently distributed!

- Different parts of the computer interact
    + cores within processors
	+ processor registers, cache, main memory, IO, etc.

- Different computers interact
    + local computer networks
	+ clusters and server farms
	+ internet
	
## The Power of Parallelism I

![Power of Parallelism](/assets/img/speedup.jpg){: width="100%"}

## The Power of Parallelism II

![Power of Parallelism](/assets/img/speedup-annotated.jpg){: width="100%"}


## Historical Notes

- Explosion of computing power due to parallelism is recent (last 20 years)
- Theoretical foundations of parallel and distributed systems are older:
    + [PODC conference](https://www.podc.org/) since 1982
	+ [SPAA conference](https://spaa.acm.org/) since 1989
- My biased view:
    + theoretical innovation facilitates practical innovation
	+ theory and principles of computing maintain their value independent of technological innovation

# Motivating Examples

## Embarrassingly Parallel Problems

We saw in lab: computing $$\pi $$ with multiple threads:

```text
n threads | pi estimate | time (ms)
-----------------------------------
        1 |     3.14156 |   8674
        2 |     3.14166 |   4540
        4 |     3.14166 |   2229
        8 |     3.14164 |   1675
-----------------------------------
```

# Things Get Weird

## Shared Objects

Consider a simple counter:

```java
public class Counter {
    long count = 0;
    
    public long getCount () { return count; }
    public void increment () { count++; }
    public void reset () { count = 0; }
}
```

## A Task

Increment the counter 1 million times.

## An Idea

Use multithreading to increment the counter!

- We can finish quickly, and then take life easy.

## A Thread 

Define a `Runnable` object to increment the counter:

```java
public class ThreadCount implements Runnable {
    private Counter counter;
    private long times;      // number of times to increment counter

    public ThreadCount (Counter counter, long times) {
        this.counter = counter;
        this.times = times;
    }

    public void run () {
        for (long i = 0; i < times; i++) {
            counter.increment();
        }
    }
}
```


## Run Several Threads

```java
public class BadCounter {
    public static int NUM_THREADS = 4;
	public static int TIMES = 1_000_000;
    public static long TIMES_PER_THREAD = TIMES / NUM_THREADS;

    public static void main (String[] args) {
	Counter counter = new Counter();
	Thread[] threads = new Thread[NUM_THREADS];
	for (int i = 0; i < NUM_THREADS; i++) {
	    threads[i] = new Thread(new ThreadCount(counter, TIMES_PER_THREAD));
	}

	for (Thread t : threads)
	    t.start();

	for (Thread t : threads) {
	    try {
		t.join();
	    }
	    catch (InterruptedException e) {
	    }
	}

	System.out.println("Expected final count: " + NUM_THREADS * TIMES_PER_THREAD);
	System.out.println("Actual final count: " + counter.getCount());
    }
}
```
## Run the code yourself!

- [Counter.java](/assets/java/2021s-cosc-273/lec01/Counter.java)
- [ThreadCount.java](/assets/java/2021s-cosc-273/lec01/ThreadCount.java)
- [BadCounter.java](/assets/java/2021s-cosc-273/lec01/BadCounter.java)

## What happened?

- What is the final count?
- Is the behavior what you expected?
- How can we diagnose the behavior?
- Does the behavior persist if the number of increment operations is small?

## Next Time

Theoretical Limitations of Parallelism
