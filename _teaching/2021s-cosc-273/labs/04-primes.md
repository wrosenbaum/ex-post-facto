---
title: "Lab 04: Primes!"
description: "generating primes quickly"
layout: page
---

--------------------

**DUE:** Friday, April 16, 23:59 [AoE](https://time.is/Anywhere_on_Earth)

--------------------


## Background

Recall that a **prime number** is a an integer $$p > 1$$ that is not divisible by any number $$d $$ strictly between $$1 $$ and $$ p$$. For example, the first few prime numbers are $$2, 3, 5, 7, 11, 13, 17, 19, 23, 27, 29,\ldots$$. A number $$n > 1$$ that is not prime is said to be **composite**. Prime numbers have many applications in computer science, notably in the [RSA encryption scheme](https://en.wikipedia.org/wiki/RSA_(cryptosystem))---currently the most widely used public key encryption scheme.

##### The Sieve of Eratosthenes

The problem of computing prime numbers has fascinated mathematicians for millenia. One of the earliest recorded methods for producing prime numbers (and indeed, one of the earliest recorded algorithms for any problem) is the [Sieve of Eratosthenes](https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes) (SoE). For any value of $$N $$, the SoE produces all of the primes between $$1 $$ and $$N $$. The basic idea is as follows:

1. Write all numbers from $$2 $$ to $$N $$ consecutively, and start reading the numbers in order.
2. Whenever a new number is read:
    - if the number is marked as composite, continue
	- otherwise, if the number is not marked, mark it as prime; then mark all multiples of the number as composite
	
For example, we can generate the prime numbers up to 20:

```text
02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20
```

In the first iteration of step 2 above, we'll mark 2 as prime, and mark all of the multiples of 2 to be composite:

```text
02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20
PP    cc    cc    cc    cc    cc    cc    cc    cc    cc
```

In the next iteration, we see the 03 is the next unmarked number, so we mark it as prime, and remove all of its multiples:

```text
02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20
PP PP cc    cc    cc cc cc    cc    cc cc cc    cc    cc
```

Now 5 is the next unmarked number, so we mark it as prime, and mark its multiples as composite:

```text
02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20
PP PP cc PP cc    cc cc cc    cc    cc cc cc    cc    cc
```

Continuing in this way, we'll reach the state

```text
02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20
PP PP cc PP cc PP cc cc cc PP cc PP cc cc cc PP cc PP cc
```

from which we can infer that the primes up to $$20 $$ are

```text
02 03 05 07 11 13 17 19
```

##### An Optimization

This process can be optimized somewhat. If a number $$n \leq N$$ is composite, then it has a divisor that is at most $$\sqrt{N}$$. (To see this, suppose $$n = a \cdot b$$ with $$a, b > \sqrt{N}$$. Then $$a \cdot b > \sqrt{N} \cdot \sqrt{N} = N$$. Thus if $$n $$ is composite---i.e., $$n = a \cdot b$$ for some $$a, b$$ satisfying $$1 < a, b < n$$ and $$n \leq N$$, then we must have either $$a \leq \sqrt{N}$$ or $$b \leq \sqrt{N}$$.) Therefore, if we have marked numbers with divisors at most $$\sqrt{N}$$ as composite and some number $$n < N$$ has not been marked as composite, then we can infer that $$n $$ is prime.

##### Implementing the Sieve of Eratosthenes

To implement the SoE in Java, we can store which numbers are prime/composite as an array of Boolean values. Specifically, we can create an array `boolean[] isPrime`, with the interpretation that `isPrime[i]` should be `true` if `i` is prime, and `false` if `i` is composite. Using the optimization described above, we can implement the sieve as follows:

```java
        boolean[] isPrime = new boolean[MAX];
	
	// initialize isPrime[i] to true for i >= 2
	for (int i = 2; i < MAX; ++i) {
	    isPrime[i] = true;
	}

        // apply the Sieve of Eratosthenes
	for (int i = 0; i < MAX; ++i) {
	    if (isPrime[i]) {

		if (i < ROOT_MAX) {
		    int j = 2 * i;

		    while (true) {
			isPrime[j] = false;
			if (j >= MAX - i) {
			    break;
			}
			j += i;
		    }
		}
	    }
	}
	
```

When the method above completes, `isPrime` will have the property that `isPrime[i]` is `true` precisely when `i` is prime. It is then straightforward to use the array `isPrime` to list all of the primes up to `MAX - 1`: read through the array and print each index `i` with `isPrime[i] == true`.

## Your Task

For this lab you will write a program `ParallelPrimes.java` that computes as many primes as possible in one second. Specifically, your program must generate an array `int[] primes` that contains as many consecutive primes as possible:

```java
primes[0] = 2;
primes[1] = 3;
primes[2] = 5;
...
```

To this end, you should complete an implementation of `ParallelPrimes.java` (linked below). The program will give you access to a thread pool for exactly 1 second before terminating all active and pending tasks in the pool. It then compares the generated `int[] primes` to an array of known primes and prints the first index at which the two arrays differ.

To get started, download the following files:

- [`ParallelPrimes.java`](/assets/java/primes/ParallelPrimes.java)
- [`Primes.java`](/assets/java/primes/Primes.java)

You should modify `ParallelPrimes.java` to perform your multithreaded implementation. You may use `Primes.java` as well in your implementation.

## Grading

Your program will be graded on a 6 point scale according to the following criteria:

- **Correctness** (2 pts). The program correctly produces primes using multithreading. The individual tasks handle interrupts gracefully and the threads in the thread pool terminate within 50ms of termination (i.e., the entire runtime is at most 1050ms). 

- **Performance** (2 pts). The program produces a number of primes comparable to the benchmark program. (The results of sample benchmark runs will be provided soon.)

- **Style & Documentation** (2 pts). Code is well-organized and commented. Submission includes a `README` file that describes your program at a high level, and briefly discusses optimizations and tests performed.

## Suggestions

##### Optimization of SoE and Parallelization

The optimization of the SoE described above suggests a way to parallelize (some of) our computation. Suppose we've already computed all primes up to, say 100, and stored these primes in an array `int[] smallPrimes`. Since every composite integer up to 10,000 (= 100 x 100) has a prime factor that is at most 100, the SoE will find all primes up to 10,000 after we remove multiples of the primes in `smallPrimes`. Moreover, the tasks of finding primes between 1,000 and 1,999 (say) and finding primes between 2,000 and 2,999 can be done indpendently (given shared access to `smallPrimes`). This observation suggests a way of breaking down the problem into tasks: first find "sufficiently many" small primes using the sequential SoE, then use these small primes to find larger primes in parallel.

##### Coordination

Using the SoE to generate an array of primes (as implemented in `Primes.java`) consists of two logically distinct steps:

1. Compute the array `boolean[] isPrime`.
2. Use `isPrimes` to compute the actual array `int[] primes` of prime numbers.

The first step more computationally intensive, but it can be parallelized as suggested above (at least once some small primes are found). The second step is relatively faster, but it should be done sequentially to ensure that the primes appear in sorted order. This suggests using two distinct types of tasks: one type of task that performs step 1 (which can be parallelized), and another type of task that performs step 2. You might invoke many type-1 tasks, while a single type-2 runs concurrently to write primes. 

You may find it useful that `Executor`s in Java assign tasks to threads in the order the tasks are added (using the `execute` method). This does not guarantee that the first task submitted will be the first task *completed.* Thus you will need to ensure that the type-2 task waits until it has the "next" block of primes to write. To this end, you may find it helpful to use a concurrent data structure in which completed type-1 tasks can place their output while the type-2 task waits for the appropriate task to be completed. You can find a [complete list of built-in concurrent data structures in the Java API documentation here](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/package-summary.html).
