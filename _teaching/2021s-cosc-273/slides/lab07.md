---
title: "Lab Week 7: Generating Prime Numbers"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lab Week 7: Generating Prime Numbers

## Schedule

1. Quiz 03: Finish by Wednesday (March 31)
2. Homework 02: Due Friday (April 2)
3. Final project preliminary proposal: Due next Friday (April 9)
4. Lab 04: Due following Friday (April 16)

Also: Only 5 labs/homework assignments

## Overview

1. Prime numbers
2. Generating Primes
3. Your Task

# Prime Numbers

## Recall

The natural numbers $0, 1, 2, 3, \ldots$

Given natural numbers $n$, $d$:
- $d$ is a **divisor** of $n$ if $n = d \cdot q$ for some natural number $q$
    + $q$ is the **quotient** of $n$ and $d$
	+ $n$ is a **multiple** of $d$
- $d$ is a **proper divisor** of $n$ if it is a divisor and $d \neq 1, n$

- In Java
    + `(n % d == 0)` returns `true` if and only if `d` divides `n`
	
## Prime Definitions

A natural number $p > 1$ is **prime** if it has no proper divisors.

- A natural number $n > 1$ that is not prime is **composite**

Examples:

- $2, 3, 5, 7, 11, 17, 19$ are prime

- $4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20$ are composite

## Who Cares?

- Mathematicians
    + primes are atomic building blocks of natural numbers
	+ understanding how prime numbers are distributed is a central goal of number theory
- Cryptographers
    + large prime numbers are essential for RSA encryption 
- Everyone
    + RSA encryption is most widely used public key encryption
	+ used for secure communication everywhere

## Testing if a Number is Prime

Method 1: Trial Division

- Check all numbers less than `n` to see if `n` is divisble by them:

```java
public boolean isPrime(int n) {
    if (n <= 1) return false;
	
    for (int d = 2; d < n; ++d) {
        if (n % d == 0)
            return false;
    }
	
    return true;
}
```

## Example

Is 91 prime?

<div style="margin-bottom: 12em"></div>

## Is Trial Division Efficient?

Can we improve trial division?

- Do we have to check all possible divisors up to `n-1`?

<div style="margin-bottom: 12em"></div>

## Making Things More Efficient

**Claim 1.** If `n` is composite, then it has a divisor at most `Math.sqrt(n)`.

**Why?**

<div style="margin-bottom: 8em"></div>

**Conclusion.** Only need to check divisors up to `Math.sqrt(n)`

## A Faster Procedure

```java
public boolean isPrime(int n) {
    if (n <= 1) return false;
	
    for (int d = 2; d * d <= n; ++d) {
        if (n % d == 0)
            return false;
    }
	
    return true;
}
```

## Can Procedure Be Improved More?

**Claim 2.** If `n` is composite, then it has a *prime* divisor at most `Math.sqrt(n)`.

So we only need to check *primes* up to `Math.sqrt(n)`

Example:

- To determine if a number less than...
    + ...100 is prime, need only check divisibility by $2, 3, 5, 7$
    + ...1,000 is prime, need only check divisibility by $2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31$
	+ ...1,000,000 is prime, need only check divisibility by primes up to 1,000
	
## Generating Primes

Suppose we want to generate *all* primes up to $N$...

How should we do this?

<div style="margin-bottom: 12em"></div>

## Sieve of Eratosthenes

1. Write numbers `2` through `N`
2. Read numbers in order:
    - if a number is not crossed out, circle it
	    + it is prime!
	- then cross out all multiples

Example

```text

02 03 04 05 06 07 08 09 10

11 12 13 14 15 16 17 18 19

20 21 22 23 24 25 26 27 28

29 30 31 32 33 34 35 36 37

```

## Observation/Optimization

In SoE, once we find primes up to `Math.sqrt(N)`, we can stop!

Why?

<div style="margin-bottom: 12em"></div>

## Eratosthenes in Code

1. Make boolean array `isPrime` of size `N`
    - interpretation: `isPrime[i] == true` if `i` is prime
2. Initialize `isPrime[i]` to `true` for all `i >= 2`
3. Iterate over indices `i` up to `Math.sqrt(N)`:
    - if `isPrime[i]`:
	    + set `isPrime[j] = false` for all `j` that are multiples of `i`
	- otherwise, do nothing

When done: `isPrime[i]` is true precisely for primes

## Eratosthenes in Java

```java
boolean[] isPrime = new boolean[N];

for (int i = 2; i < N; ++i) {
    isPrime[i] = true;
}

for (int i = 2; i < N; ++i) {
    if (isPrime[i]) {
        for (int j = 2 * i; j < N; j += i) {
            isPrime[j] = false;		
        }
    }
}
```

## How Fast is This?

Test it for yourself!

- [Download `Primes.java`](/assets/java/primes/Primes.java)

Note: some small optimizations in `Primes.java`

# Lab 04: Primes

## Your Task

Write a program that computes an array `int[] primes` of consecutive primes as quickly as possible.

To test your program, I will

- run the program for 1 second
- read `primes[0], primes[1], ...`
- stop reading at the first index that is not prime, or the primes are out of order
    + e.g., if `primes[0] = 3`, I stop reading at index `0`
- your program performance is determined by the highest index read

## Conceptual Challenges

1. Generating primes faster
    - use parallelism!
2. Dealing with unknown termination time
    - task will be interrupted
	- must start writing primes before all primes found
3. Writing primes *in order*
    - requires coordination between threads
	
## Technical Challenges

1. Handling interruptions gracefully
2. Using shared data structure to coordinate between threads
3. Having concurrent threads with different roles
    - e.g., producers and consumers
	
## A Parallel Activity

As noted earlier:

1. A number up to 100 is prime if and only if it has no prime divisor under 10
2. Primes under 10 are 2, 3, 5, 7

Let's compute primes up to 100 in parallel!

- Each breakout room will test 10 numbers for primality
- Room 01: 10--19
- Room 02: 20--29
- ...

You'll have 2 minutes!

## Primes up to 100:

```text
00-09:

10-19:

20-29:

30-39:

40-49:

50-59:

60-69:

70-79:

80-89:

90-99:
```

## Suggestion

Your program can use similar ideas to this activity:

- Small tasks to find primes
- Other task(s) to write primes in order when found




