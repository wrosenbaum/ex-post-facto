---
title: "Lab 09: Fork-Join Pools"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lab 09: Fork-Join Pools

## Announcements

Feedback on final project proposals in next few days!

## Overview

1. Divide and Conquer & Recursion
2. Fork-Join Pools
3. Activity: Analyzing Behavior

## Divide and Conquer

Many computation problems can be solved efficiently by:

1. Breaking an instance into two or more smaller instances
2. Solving the smaller instances (maybe recursively)
3. Combining the smaller solutions to solve the original instance

## Example 1: Searching Unsorted Array

- Given `int[] arr` of size `N`
- Does `arr` contain `1`?
- Idea:
    1. divide `arr` in half
	2. search left half for `1`
	3. serach right half for `1`
	4. return `true` if step 1 or 2 succeeds
	
<div style="margin-bottom: 4em"></div>

	
## Example 2: MergeSort

- Given `int[] arr` of size `N`
- Sort `arr` in increasing order
- Idea:
    1. divide `arr` in half
	2. sort left half 
	3. sort right half
	4. merge sorted halves
	
<div style="margin-bottom: 6em"></div>

## Other Examples

- Other sorting algorithms
    + QuickSort
- Multiplying large numbers
- Multiplying matrices
    + Strassen's algorithm
- Computing Fourier transforms

## Observation

Divide-and-conquer often lends itself well to parallelism:

1. Divide instance into smaller instances
2. Solve smaller instances *in parallel*
3. Combine solutions

<div style="margin-bottom: 8em"></div>

## Fork-Join Pools

Idea:

- A thread pool with efficient support for *forking*:
    + divide a task into two or more sub-tasks
	+ complete sub-tasks
	+ combine solutions (if necessary)
- Naturally lends itself to recursion

<div style="margin-bottom: 8em"></div>

## Fork/Merge Diagram: Merge Sort

<div style="margin-bottom: 18em"></div>

## Creating a Fork-Join Pool

Creating a Fork-Join Pool is easy!

- tasks are **invoked** in FJP

```java
import java.util.concurrent.ForkJoinPool;
...
ForkJoinPool pool = new ForkJoinPool(POOL_SIZE);
...
pool.invoke(new SomeTask(...));
```

## Recursive Actions

Tasks without return values = **recursive action**

- extend `RecursiveAction` class
- override `compute()` method

## MergeSort as `RecursiveAction`

```java
import java.util.concurrent.RecursiveAction;

class MSTask extends RecursiveAction {
    ...
    public MSTask (double[] data, int min, int max) {
        ...
    }
	
    @Override
    protected void compute () {
        if (max - min <= 1) {
            ...
            return;
        }
		
        int mid = min + (max - min) / 2
		
        MTask left = new MTask(data, min, mid);
        MTask right = new MTask(data, mid, max);
		
        left.fork();
        right.fork(); // or can use right.compute()
		
        left.join();
        right.join(); // leave out if right.compute()
        
        merge(data, min, mid, max);
    }
	
    void merge (double[] data, int min, int mid, int max) {
        ...	
    }
}
```

Invoke with `pool.invoke(new MTask(data, 0, data.length))`

## Efficiency

Often Fork-Join pools are not as efficient you'd like them to be

To deal with this:

- Use large "base case"
    + don't waste multithreading breaking up small tasks
- Only use on large instances

Still FJPs can lead to elegant solutions, readable code

## Recursive Task

What if we want tasks to return a value?

- Use `RecursiveTask<T>`!
    + task returns a value of type `T`
	+ similar to `RecursiveAction` except `compute()` returns a `T`
- `pool.invoke(someRecursiveTask<T>)` now also returns a `T`
- `join()` method also returns a `T`

## A Simple Example

Finding the maximum value in an unsorted array

- What is a task?
- How to combine results?

<div style="margin-bottom: 12em"></div>

## MaxTask 

```java
class MaxTask extends RecursiveTask<Double> {
    public static int PARALLEL_LIMIT = MaxFinder.DATA_SIZE / 1000;
    
    double[] data;
    int min;
    int max;
    
    public MaxTask (double[] data, int min, int max) {
	this.data = data;
	this.min = min;
	this.max = max;
    }

    @Override
    protected Double compute () {
	if (max - min <= PARALLEL_LIMIT) {
	    return findMax();
	}

	MaxTask left = new MaxTask(data, min, min + (max - min) / 2);
	MaxTask right = new MaxTask(data, min + (max - min) / 2, max);

	right.fork();

	double l = left.compute();
	
	double r = right.join();

	return Math.max(l, r); 
    }

    private Double findMax() {
	double maxValue = Double.MIN_VALUE;

	for (int i = min; i < max; ++i) {
	    if (maxValue < data[i]) {
		maxValue = data[i];
	    }
	}

	return maxValue;
    }
}
```

## The Competition

```java
    public static double findMax(double[] data) {
	double max = Double.MIN_VALUE;

	for (int i = 0; i < DATA_SIZE; ++i) {
	    if (max < data[i]) {
		max = data[i];
	    }
	}

	return max;
    }
```

## An Activity

Compare the run-times of the two methods!

1. Is there a value of `DATA_SIZE` for which `MaxTask` is faster?
2. What values of `PARALLEL_LIMIT` give better performance?

Disclaimer:

- everything about Java is optimized to execute code like `findMax` efficiently
- fork-join pools are better suited for more complex tasks...
    + ...like sorting 
	+ you'll experience this in the next lab assignment!

## Discuss

What did you find?


## Lab 05: Our Last Lab

Simple problem:

- Given a large array of `double`s, sort it as quickly as possible
- Use `Arrays.sort(...)` as a baseline

Details to follow...







