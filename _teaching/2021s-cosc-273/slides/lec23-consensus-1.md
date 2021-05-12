---
title: "Lecture 23: Consensus 1"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 23: Consensus 1

## Mission Critical Components

Suppose you're designing an airplane

1. Need computers to control *everything* 
    + sensors for speed, thrust, flap positions, pitch, roll, yaw
	+ must adjust constantly to fly
2. But computers occassionally (regularly) crash/need restart

How to design around this issue?

<div style="margin-bottom: 8em"></div>


## Fault-Tolerance through Duplication?

Have multiple duplicate, independent systems

- systems run in parallel
- highly unlikely both crash simultaneously
    + restarts are infrequent
	+ restarting one system won't affect other system
	
The end of our worries?

<div style="margin-bottom: 8em"></div>

## Trouble Ahead

Suppose all systems working normally, but

- system 1 says increase thrust
- system 2 says decrease thrust
- system 3 not responding (restart?)

What do we do?

<div style="margin-bottom: 8em"></div>

## The Problem of Consensus

Have multiple systems with different inputs

- For us, binary inputs
    + `0` = decrease thrust
	+ `1` = increase thrust
	
Goal:

- agree on same output

## More Formally

Setup:

- $n$ processes/threads, $P_1, P_2, \ldots, P_n$
- processes have unique IDs, $1, 2, \ldots, n$ (like `ThreadId.get()`)
- each holds private input: $x_1, x_2, \ldots, x_n$
    + for simplicity, each $x_i$ is $0$ or $1$

Output:

- each process $i$ outputs $b_i$, 0 or 1

Failure:

- Some process(es) may crash
    + failing process may perform some steps before failing

## Conditions for Consensus

- **Agreement**: all processes output the same value
- **Validity**: if all systems have the same intput, they all output that value
- **Termination**: all (non-faulty) processes decide on an output and terminate after a finite number of steps

## Exercise

Devise an algorithm for consensus assuming:

1. Fair scheduler
    - every process is scheduled eventually
2. No faulty processes

<div style="margin-bottom: 12em"></div>

## Consensus with Faults

Suppose some process(es) may crash at any time during an execution...

- Other processes can't tell that a process crashes
    + e.g., cannot distinguish slow process from crashed

## Not Consensus 1

How can we achieve...

- ~~**Agreement**: all processes output the same value~~
- **Validity**: if all systems have the same intput, they all output that value
- **Termination**: all (non-faulty) processes decide on an output and terminate after a finite number of steps

<div style="margin-bottom: 8em"></div>

## Not Consensus 2

How can we achieve...

- **Agreement**: all processes output the same value
- ~~**Validity**: if all systems have the same intput, they all output that value~~
- **Termination**: all (non-faulty) processes decide on an output and terminate after a finite number of steps

<div style="margin-bottom: 8em"></div>

## Not Consensus 3

How can we achieve...

- **Agreement**: all processes output the same value
- **Validity**: if all systems have the same intput, they all output that value
- ~~**Termination**: all (non-faulty) processes decide on an output and terminate after a finite number of steps~~

<div style="margin-bottom: 8em"></div>

## So

Without too much trouble, we can achieve...

- ...consensus without faults
- ..."consensus" without agreement
- ..."consensus" without validity
- ..."consensus" without termination

What about consensus with faults?

<div style="margin-bottom: 8em"></div>

## A Remarkable Fact

**Theorem** (FLP, 1985). There is no algorithm that achieves consensus in the presence of even a single faulty process.

- Proven by Fischer, Lynch, and Paterson in 1985
    + their version of result for "message passing" model
	+ ours is for shared memory
- Surprise to the parallel/distributed computing community
- Among most influential results in CS

## Our Plan

Prove FLP result!

Before going further

- Specify computational model
- Make preliminary observations
- Outline argument

## Computational Model

- Processes have shared memory (registers)
- Atomic read/write access
    + behavior like `volatile` variables in Java
- Scheduler decides which process makes a step
    + assume each step is read/write
- Some processes may **crash**
    + such a process is never scheduled again
- Scheduler is otherwise fair: non-crashed processes are scheduled eventually

## Algorithms

An **algorithm** $A$ specifies next operation:

- read value from shared register
- write value to shared register
- terminate

as a function of 

- input ($x_i$)
- outcomes of previous (read) operations

*Next step is uniquely determined by local input and values read in previous steps*

## Example: Default to `0`

Idea: output `0` unless all processes have input `1`

```java
int in = getLocalInput();
int i = ThreadId.get();

write(i, in); // write my value to register i

if (in == 0) return 0;

for (int j = 0; j < nProcesses; j++) {
    // wait until register j has been written	
    while (read(j) != 0 && read(j) != 1) { };
	
    if (read(j) == 0) return 0;
}

// all processors have in == 1
return 1;
```

## Example in Pictures

## Executions

An **execution** $E$ of algorithm $A$ specifies

- Inputs of all processes
- Sequence of steps taken by processes
    + read
	+ write
	+ terminate
	+ crash

Executions may be incomplete

- Not all nodes have terminated/crashed yet
    + encodes current state/history of execution

Executions may be **extended** by scheduling more steps

## Example of Execution

## Extending Executions

## Indistinguishable Executions

- $E$ and $E'$ are executions
- they are **indistinguishable** at process $i$ if in $E$ and $E'$:
    1. $i$ has same input
	2. sequence of read/write operations performed by $i$ are same 
	3. the sequence of values read and written by $i$ are the same 

## Indistinguishable Example

## 


