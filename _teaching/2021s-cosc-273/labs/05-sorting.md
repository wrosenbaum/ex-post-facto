---
title: "Lab 05: Sorting"
description: "sorting faster than ever before"
layout: page
---

--------------------

**This Assignment is Optional.** If you would like to receive credit for the assignment, please submit it by Wednesday, May 19th, 23:59 AoE.

--------------------

## Background

Sorting is a fundamental task in computer science. As such, most general-purpose programming languages provide a built-in sorting function. For example, Java provides the [`Arrays.sort(...)` method](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Arrays.html#sort(double%5B%5D,int,int)). The `sort` method will takes an array numbers (`int`, `double`, `float`, etc.) and sorts the array in-place in ascending order. For example, after calling

```java
double[] data = {4.0, 2.0, 3.0, 1.0};
Arrays.sort(data)
```

`data` will store the values `{1.0, 2.0. 3.0, 4.0}`. The `Arrays.sort` method is quite efficient: on my laptop, it sorts a random array of 10 million `double`s in about 1 second. Nonetheless, there is always room for improvement!

## Your Task

In this lab, you will use multithreading and parallelism in order to implement a faster sorting procedure than `Arrays.sort` (at least for large arrays). Specifically, you should implement a method `Sorting.parallelSort(double[] data)` that sorts an array of `double`s in place. To get started, download the following archive, which includes a program `SortingTester.java` to test your implementation.

- [`Sorting.zip`](/assets/java/2021s-cosc-273/lab04-sorting/Sorting.zip)

## Grading

Your program will be graded on a 6 point scale according to the following criteria:

- **Correctness** (2 pts). Your program employs multithreading and sorts all tested inputs correctly.
    + **Note.** Be sure to test your program on "easy" inputs such as those repeated values.

- **Performance** (2 pts). Your program sorts large randomly generated inputs significantly faster than the provided baseline implementation. Your program should run at least twice as fast as `Arrays.sort()` on arrays of aroud 10 million entries.

- **Style & Documentation** (2 pts). Code is well-organized and commented. Submission includes a `README` file that describes your program at a high level, and briefly discusses optimizations and tests performed.

## Suggestions and Resources

Many of the fastest practical sorting algorithms use some variant of [quicksort](https://en.wikipedia.org/wiki/Quicksort). We briefly describe a high-level description of quicksort. For simplicity we assume that elements in the array are pair-wise distinct (i.e., not value is repeated), but your program should handle the case where repeated values are allowed.

1. Choose a `pivot` element in the array---typically either some fixed element or the a random element in the array.
2. Reorder the array so that
    - `pivot` is at index `p` and
    - all elements at indices `i < p` are smaller than `pivot`
	- all elements at indices `i > p` are greater than `pivot`
3. Recursively sort
    - the sub-array from indices `0` to `p-1`
	- the sub-array from indices `p+1` to `n` (where `n` is the original size of the array).

The recursive sorting in step 3 can again be performed using quicksort. To this end, it is useful to define the general method to take three parameters: the array `data` to be sorted, as well as indices `i` and `j`. A call to `quicksort(data, i, j)` will return an array such that the elements of `data` with indices `i, i+1,..., j-1` are sorted, while the remaining entries are unchanged.
   	
For our purposes, the quicksort algorithm is appealing for two reasons. First, it sorts elements in-place. That is, all of the operations can be performed on the original array without duplicating data. (In particular, this is true of step 2). Second, the sub-steps of step 3 of the procedure can be performed in parallel.

For a detailed account of implementing and parallelizing quicksort (and other sorting algorithms), see

 - *Sorting and Selection* in *Sequential and Parallel Data Structures and Algorithms* (book chapter [available here](https://people.mpi-inf.mpg.de/~mehlhorn/ToolboxNew.html)).

Since quicksort follows a divide-and-conquer approach, you may find the following documentation/tutorial helpful:

- [Class `ForkJoinPool`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/ForkJoinPool.html)
- [Class `RecursiveAction`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/RecursiveAction.html)
- [A brief tutorial on `ForkJoinPool`s](http://tutorials.jenkov.com/java-util-concurrent/java-fork-and-join-forkjoinpool.html)

Note that there are many different variants of quicksort. Exactly which version you use and how it is implemented can have a significant impact on your program's performance.

##### Tips

1. For reasonably small arrays, `Arrays.sort()` is likely to be faster than a more complex multi-threaded procedure. You may freely use `Arrays.sort()` as a sub-routine/base case for your sorting procedure.

2. If you choose to implement quicksort, the most costly part of the execution is the "reordering" step (step 2). Even though the procedure can be conceptually simple, there are a lot of opportunities for inefficiency. In particular, for a large array you will want to consider the cache performance and memory access pattern to the input.
