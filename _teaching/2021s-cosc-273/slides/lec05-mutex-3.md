---
title: "Lecture 05: Mutual Exclusion 3"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 05: Mutual Exclusion 3

## Outline

1. Peterson Lock Correctness
2. Lamport's Bakery Algorithm
3. Memory Lower Bounds

## Last Time

Introduced the Peterson Lock:

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

## Features

Claims:

1. `Peterson` satisfies mutual exclusion
2. `Peterson` satisfies *starvation-freedom*
    - stronger than deadlock-freedom
	- previous lock only achieved deadlock-freedom
	
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

## But We Want More Threads!

**Questions:**

1. What part of Peterson implementation generalizes easily to more threads?
2. What part doesn't generalize as easily?

<div style="margin-bottom: 12em"></div>

## Peterson Lock Code, Again

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


## Semantics of Peterson Lock

- `flag` variable signals *intent* to enter CS
    + easily generalizes to more threads
- `victim` variable signals *priority* to enter CS
    + `victim = me` means `you` have priority
- For more threads
    + more victims?
	    + how decide priority among victims?
	+ how can this system be fair?


## Lamport's Idea for Priority I

![](/assets/img/lamport-bakery/ticket-machine.jpg){: width="50%"}

## Lamport's Idea for Priority II

After signalling intent to enter CS

- Take a ticket
    + value of ticket is 1 more than max of others' tickets
	+ thread `i` sets `label[i]` to ticket value
	
- What is the problem with this?

<div style="margin-bottom: 12em"></div>

## Breaking Priority Ties

Two processes may see the same set of tickets and take same label:

- have `label[i] == label[j]` for `i != j`

**Solution:**

Break ties by ID:

- if `label[i] == label[j]` and `i < j`, then `i` has priority

Use **lexicographic order** on pairs `(label[i], i)`

## Question About Tie-breaking

Is this process fair?

- Seems we are *always* giving priority to thread `0`...

<div style="margin-bottom: 12em"></div>

## Lamport's Bakery Algorithm

Fields:

- `boolean[] flag`
    + `flag[i] == true` indicates `i` would like enter CS
	
- `int[] label`
    + `label[i]` indicates "ticket" number held by `i`
	
Initialization:

- set all `flag[i] = false`, `label[i] = 0`

## Locking

Locking Method:

```java
public void lock () {
    int i = ThreadID.get();
    flag[i] = true;
    label[i] = max(label[0], ..., label[n-1]) + 1;
    while (!hasPriority(i)) {} // wait	
}
```

The method `hasPriority(i)` returns `true` if and only if there is no `k` such that 

- `flag[k] == true` and
- either `label[k] < label[i]` or `label[k] == label[i]` and `k < i`

## Unlocking

Just lower your flag:

```java
public void unlock() {
    flag[ThreadID.get()] = false;
}
```

# Bakery Algorithm Guarantees

## Bakery Algorithm is Deadlock-Free

```java
public void lock () {
    int i = ThreadID.get();
    flag[i] = true;
    label[i] = max(label[0], ..., label[n-1]) + 1;
    while (!hasPriority(i)) {} // wait	
}
```

Why?

<div style="margin-bottom: 12em"></div>


## First-come-first-served (FCFS) Property

- If:  $A$ writes to `label` before $B$ calls `lock()`,
- Then: $A$ enter CS before $B$.

```java
public void lock () {
    int i = ThreadID.get();
    flag[i] = true;
    label[i] = max(label[0], ..., label[n-1]) + 1;
    while (!hasPriority(i)) {} // wait	
}
```

Why?

<div style="margin-bottom: 8em"></div>


## Bakery Algorithm is Starvation-Free

Thread `i` calls `lock()`:

- `i` writes `label[i]`
- By FCFS, subsequent calls to `lock()` by `j != i` have lower priority
- By deadlock-freedom every `k` ahead of `i` eventaully releases lock

So:

- `i` eventually served

## Bakery Algorithm Satisfies MutEx

```java
public void lock () {
    int i = ThreadID.get();
    flag[i] = true;
    label[i] = max(label[0], ..., label[n-1]) + 1;
    while (!hasPriority(i)) {} // wait	
}
```

Suppose not:

- $A$ and $B$ concurrently in CS
- Assume: $(\mathrm{label}(A), A) < (\mathrm{label}(B), B)$


## Proof (Continued)

Since $B$ entered CS:

- Must have read
    + $(\mathrm{label}(B), B) < (\mathrm{label}(A), A)$, or
	+ $\mathrm{flag}[A] == \mathrm{false}$

- Former can not happen: labels strictly increasing

- So $B$ read $\mathrm{flag}[A] == \mathrm{false}$

## Compare Timelines!

<div style="margin-bottom: 18em"></div>

## Conclusion

Lamport's Bakery Algorithm:

1. Works for any number of threads
2. Satisfies MutEx and starvation-freedom

## Question

Is the bakery algorithm *practical?*

- Maybe for few threads...
- But for many threads?
    + `label` array contains $n$ indices
	+ must read all entries to set own `label`
	+ costly if many threads!
- Could we do better?

## Remarkably

We cannot do better:

- If $n$ threads want to achieve mutual exclusion + deadlock-freedom, must have $n$ read/write registers (variables)

- This is really bad if we have a lot of threads!
    + 1,000 threads means each call to `lock()` requires 1,000s of reads
	+ each call to `hasPriority` requires either 1,000s of reads or a more advanced data structure
	
- Things are messy!

## A Way Around the Bound

- Argument relies crucially on fact that the *only* atomic operations are `read` and `write`

- Modern computers offer more powerful atomic operations

- In Java, `AtomicBoolean` class
    + `getAndSet()`
	+ `compareAndSet()`
	
- We will discuss more later


## Up Next

Concurrent Objects!



















