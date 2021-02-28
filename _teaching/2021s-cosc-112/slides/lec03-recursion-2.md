---
title: "Lecture 03: Recursion II"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 03: Recursion II

## Outline

1. Recap of Last Time
2. (In)efficiency of Recursion
3. Magic of Recursion

## Last Time

1. Looked at recursive methods for factorial and Fibonacci numbers
2. Compared iterative and recursive solutions for Fibonacci

## Factorial Solutions

Iterative:

```java
    private static long iFactorial (long n) {
	    if (n <= 1) { return 1; }

        long val = 1;
        for (long i = 2; i <= n; i++) {
            val *= i;
        }
	    
        return val;
    }
```

Recursive:

```java
    private static long rFactorial (long n) {
	    if (n <= 1) { return 1; }
	    return n * rFactorial(n - 1);
    }
```

## Comparing the Solutions

Recursive is more succinct... but let's test their performance!

- [RecursiveFactorialTester.java](/assets/java/recursion-vs-iteration/RecursiveFactorialTester.java)

## Fibonacci Solutions

Iterative:

```java
    private static long iFib (long n) {
        long prev = 0; long cur = 1; long next;

        for (long count = 1; count < n; count++) {
            next = prev + cur;
            prev = cur;
            cur = next;
        }
		
        return cur;
    }
```

Recursive:

```java
    private static long rFib (long n) {
        if (n <= 2) return 1;
		
        return rFib(n-1) + rFib(n-2);
    }
```

## Comparing the Performance

- [RecursiveFibonacciTester.java](/assets/java/recursion-vs-iteration/RecursiveFibonacciTester.java)

## What happened?

Why is this:

```java
    private static long rFactorial (long n) {
	    if (n <= 1) { return 1; }
	    return n * rFactorial(n - 1);
    }
```

so much more efficient than this:

```java
    private static long rFib (long n) {
        if (n <= 2) return 1;
		
        return rFib(n-1) + rFib(n-2);
    }
```

## Factorial Recursive Calls

```java
    private static long rFactorial (long n) {
	    if (n <= 1) { return 1; }
	    return n * rFactorial(n - 1);
    }
```

<div style="margin-bottom: 12em"></div>

## Fibonacci Recursive Calls

```java
    private static long rFib (long n) {
        if (n <= 2) return 1;
		
        return rFib(n-1) + rFib(n-2);
    }
```

<div style="margin-bottom: 16em"></div>

## Some Observations

1. The iterative solutions were efficient.
2. Recursive factorials were efficient because no branching:
    - each method call made only one recursive call
3. Recursive Fibonacci was *in*efficient because of branching:
    - each method call made 2 recursive calls
	- even though few *distinct* calls, all method calls executed as prescribed by code
	
# Magic

## Previous Examples

- Extremely simple recursive methods
- Still simple iterative methods

## Tower of Hanoi Puzzle

Recall the Tower of Hanoi Puzzle:

1. 3 pegs: 1, 2, 3
2. stack of $$n $$ disks of decreasing size on first peg
3. can only move one peg at a time
4. cannot put larger disk on top of a smaller disk

**Goal.** Move all disks from peg 1 to peg 3

## Initial Configuration

All disks on first peg

![](/assets/img/hanoi/initial.png){: width="100%"}

## Rule 1

Move one disk at a time

![](/assets/img/hanoi/first-move.png){: width="100%"}

## Rule 2

Cannot place a larger disk atop a smaller disk

![](/assets/img/hanoi/illegal.png){: width="100%"}

## Goal

To move all disks from peg 1 to peg 3

![](/assets/img/hanoi/goal.png){: width="100%"}

## A Program

Want:

- A program that generates instructions for a solution
- Example solution for 2 disks

```text
Move disk from peg 1 to peg 2
Move disk from peg 1 to peg 3
Move disk from peg 2 to peg 3
```
	
<div style="margin-bottom: 8em"></div>

## A Problem

How do we design the program?

Hints:

- Think recursion!
- What has to happen before largest disk can be moved?

Discuss!

- [Worksheet link](https://drive.google.com/file/d/1BUaUk6oj3_5srUHKa0ujQ1nfezX5Tx5a/view?usp=sharing)

<!-- ## Solution for 3 Disks? -->

<!-- <div style="margin-bottom: 12em"></div> -->

<!-- ## Moving the Largest Disk -->

<!-- How to move largest disk from peg 1 to peg 3? -->

<!-- <div style="margin-bottom: 12em"></div> -->

<!-- ## Solving in 3 Steps (1) -->

<!-- Step 1: Move subtower to peg 2 -->

<!-- ![](/assets/img/hanoi/step-1.png){: width="100%"} -->

<!-- ## Solving in 3 Steps (2) -->

<!-- Step 2: Move bottom disk to peg 3 -->

<!-- ![](/assets/img/hanoi/step-2.png){: width="100%"} -->

<!-- ## Solving in 3 Steps (3) -->

<!-- Step 3: Move subtower to peg 3 -->

<!-- ![](/assets/img/hanoi/step-3.png){: width="100%"} -->


<!-- ## Moving a Sub-Tower -->

<!-- How do we move $$m $$ disks from peg $$i $$ to peg $$j $$? -->

<!-- <div style="margin-bottom: 12em"></div> -->


<!-- ## A General Procedure -->

<!-- To move $$m $$ disks from peg $$i $$ to $$j $$: -->

<!-- 1. move $$m - 1$$ disks from $$i $$ to $$k $$ (other peg) -->
<!-- 2. move one disk from $$i $$ to $$j $$ -->
<!-- 3. move $$m - 1$$ disks from $$k $$ to $$j $$ -->

<!-- What is missing? -->

<!-- ## Base Case! -->

<!-- If $$m = 1$$, just print -->

<!-- ```text -->
<!-- Move disk from peg i to peg j -->
<!-- ``` -->

<!-- ## Let's try it in code! -->

<!-- [TowerOfHanoi.java]() -->
