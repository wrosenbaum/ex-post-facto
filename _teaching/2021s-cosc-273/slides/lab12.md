---
title: "Lab Week 12: Sorting Networks"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lab Week 12: Sorting Networks

## Outline

1. Simple Sorting Algorithms
2. Sorting Networks
3. Sorting Activity

## Last Time

Sorting by Divide and Conquer

- Merge sort 
    1. sort left half
	2. sort left half
	3. merge sorted halves
- Quicksort
    1. pick pivot
	2. split according to pivot
	    + left half less than pivot
		+ right half at least pivot
	3. sort left half
	4. sort right half

## Sorting Networks

Another view of sorting in parallel

## Insertion Sort, Revisited

```java
for (int i = 1; i < data.length; ++i) {
    for (int j = i; j > 0; --j) {
        if (data[j-1] > data[j]) {
            swap(data, j-1, j)		
        }
    }
}
```

<div style="margin-bottom: 12em"></div>

## Appealing Features of Insertion Sort

```java
for (int i = 1; i < data.length; ++i) {
    for (int j = i; j > 0; --j) {
        if (data[j-1] > data[j]) {
            swap(data, j-1, j)		
        }
    }
}
```

1. Only modifications are (adjacent) swaps
    + sorting is *in place*
2. Access pattern is independent of input
    + inputs always read/compared in same order
	+ only difference between execution is outcomes of swaps

## Comparators: Visualizing Swaps

```java
        if (data[i] > data[j]) {
            swap(data, i, j)
        }

```

![](/assets/img/sorting-networks/comparator.png){: width="75%"}

## Comparator Swap

![](/assets/img/sorting-networks/comparator-swap.png){: width="100%"}

## Comparator No Swap

![](/assets/img/sorting-networks/comparator-no-swap.png){: width="100%"}

## Sorting Array of Two Elements

![](/assets/img/sorting-networks/comparator-2.png){: width="100%"}

## Insertion Sort

```java
for (int i = 1; i < data.length; ++i) {
    for (int j = i; j > 0; --j) {
        if (data[j-1] > data[j]) {
            swap(data, j-1, j)		
        }
    }
}
```
![](/assets/img/sorting-networks/insertion-sort-0.png){: width="75%"}

## Insertion Sort: `i = 1`

![](/assets/img/sorting-networks/insertion-sort-1.png){: width="100%"}

## Insertion Sort: `i = 2`

![](/assets/img/sorting-networks/insertion-sort-2.png){: width="100%"}

## Insertion Sort: `i = 3`

![](/assets/img/sorting-networks/insertion-sort-3.png){: width="100%"}

## Which Operations can be Parallelized?

![](/assets/img/sorting-networks/insertion-sort-3.png){: width="100%"}

## Parallelism

![](/assets/img/sorting-networks/insertion-sort-parallel-1.png){: width="100%"}

## Cleaner Parallel Representation

![](/assets/img/sorting-networks/insertion-sort-parallel-2.png){: width="100%"}

## Depth

![](/assets/img/sorting-networks/insertion-sort-parallel-3.png){: width="100%"}

## Insertion Sort: Larger Instance

![](/assets/img/sorting-networks/insertion-sort-large-1.png){: width="100%"}

## Done In Parallel?

![](/assets/img/sorting-networks/insertion-sort-large-1.png){: width="100%"}

## Done In Parallel!

![](/assets/img/sorting-networks/insertion-sort-large-2.png){: width="100%"}

## Cleaned Up

![](/assets/img/sorting-networks/insertion-sort-large-3.png){: width="100%"}

## Parallel Depth?

![](/assets/img/sorting-networks/insertion-sort-large-4.png){: width="100%"}

## Bubble Sort

Consider:

```java
for (int m = data.length - 1; m > 0; --m) {
    for (int i = 0; i < m; ++i) {
        if (data[i] > data[i+1]) {
            swap(data, i, i+1)		
        }
    }
}
```

Can we make a sorting network corresponding to bubble sort?

<div style="margin-bottom: 8em"></div>

## Bubble Sort: `m = 6`

![](/assets/img/sorting-networks/bubble-sort-1.png){: width="100%"}

## Bubble Sort: `m = 5`

![](/assets/img/sorting-networks/bubble-sort-2.png){: width="100%"}

## Bubble Sort: `m = 4`

![](/assets/img/sorting-networks/bubble-sort-3.png){: width="100%"}

## Bubble Sort: `m = 3`

![](/assets/img/sorting-networks/bubble-sort-4.png){: width="100%"}

## Bubble Sort: `m = 2`

![](/assets/img/sorting-networks/bubble-sort-5.png){: width="100%"}

## Bubble Sort Network

![](/assets/img/sorting-networks/bubble-sort-6.png){: width="100%"}

## Bubble Sort Parallelized?

![](/assets/img/sorting-networks/bubble-sort-6.png){: width="100%"}

## Does it Look Familiar?

![](/assets/img/sorting-networks/bubble-sort-7.png){: width="100%"}

## Huh

- Insertion sort and bubble sort perform precisely same operations
    + only differ in the order in which comparisons are made
- When fuly parallelized, both are same sorting network

- Parallel versions are reasonably efficient
    + depth $2 (n - 1) - 1 = 2 n - 3$
	

## Applications

- Sorting networks can be efficiently implemented in hardware
    + can sort fixed number of items much faster than software sorting
- Depth corresponds to latency
    + smaller depth = faster computations

## Activity

Find a minimum depth sorting networks for small $n$!

## Minimum Depth for $n = 4$?

<div style="margin-bottom: 18em"></div>

## Smaller Depth for $n = 6$?

<div style="margin-bottom: 18em"></div>

## Current State

What is known:

- Optimal depth sorting networks for $n \leq 17$

What is not known:

- Optimal depth sorting networks for $n \geq 18$


