---
title: "Monte Carlo Simulation"
layout: page
---

In this note, we briefly describe the method of Mote Carlo simulation for computing mathematical values. Monte Carlo simulation is a powerful and flexible tool for estimating values that might be difficult to compute otherwise. The basic idea is to set up a randomized experiment whose success probability is related to the value we wish to compute. By running the experiment many times, we can then estimate the success probability, from which we can compute an estimate of the value we're looking for. 

### Example: Computing $\pi$

The number $$\pi = 3.14159\cdots$$ is one of the most imporant (and well-studied) mathematicals constants. Its value has been computed to incredible accuracy. You can get representation of $$\pi$$ as a `double` in Java using `Math.PI`. But what if we didn't know that? How could we compute---or estimate---the value of $$\pi$$?

One way of estimating $$\pi$$ is to use the formula for the area of disk (i.e., the region bounded by a circle): $A = \pi r^2$, where $A$ is the disk's area, and $r$ its radius. Imagine a circular dartboard inside a square frame, where the diameter of the dartboard is equal to side length of the frame:

[picture]

If the dartboard has a radius $r$, then its area is $A_{d} = \pi r^2$. Meanwhile, the side length of the frame is $2 r$, so its area is $A_f = (2 r)^2 = 4 r^2$. Suppose I throw a dart at the frame. Now, I'm not very good at throwing darts, so the dart is equally likely to land anywhere inside the frame (and if it misses the frame entirely, I'll keep throwing until it hits inside the frame). Given that (1) the dart lands within the frame, and (2) the dart is equally likely to land anywhere within the frame, the probability, $p$, that the dart hits the dartboard is the ratio of areas of the dartboard and the frame 
$$
p = \frac{A_d}{A_f} = \frac{\pi r^2}{4 r^2} = \frac{\pi}{4}.
$$
We can solve this expression for $\pi$ to get $\pi = 4 p$.

The crucial observation is that we can estimate $p$ by throwing (many) darts at the frame and counting the number of times they hit the dartboard:
$$
p \approx \frac{\#\text{times dartboard hit}}{\#\text{darts thrown}}.
$$
So in order to estimate $\pi = 4 p$, it suffices to throw enough darts at our darboard, and count the number of hits.

**IYI.** The fact that $p$ is approximated by the expression above is mathematically guaranteed by the [law of large numbers](https://en.wikipedia.org/wiki/Law_of_large_numbers). In fact, a more careful analysis can tell us how many experiments (i.e. darts) we need to run in order to approximate $\pi$ to a desired accuracy. Such an analysis shows that the standard devation of our estimate---i.e., the expected amount by which the estimate differs from $\pi$---is proportional to $1 / \sqrt{n}$, where $n$ is the number of experiments performed. Therefore if we want to double the accuracy of our estimate, we need to run 4 times as many experiments; for each extra digit of precision (i.e., tenfold increase in accuracy) we need to do 100 times as many experiments!

Thankfully, we don't need a real dartboard (and endless time) to perform the experiment above: we can write a program to do the precedure for us! All we have to do is choose a suitable square and inscribed disk. Then we can generate random points in the square (i.e., pairs of random numbers with appropriate bounds) and check whether or not they lie in the disk.

Here is a table of outputs for a program that uses the Monte Carlo simulation procedure described above to estimate $\pi$.

| number of points | $\pi$ estimate |
|------------------|----------------|
|10 | **3**.2|
|20 | **3**.4|
|50 | **3**.2|
|100| **3**.32|
|500| **3.1**28|
|1000| **3.1**32|
|5000| **3.1**024|
|10000| **3.14**44|
|100000| **3.14**432|
|1000000| **3.1**39868|
|10000000| **3.141**4836|
|100000000| **3.141**45256|
|1000000000| **3.1415**67368|
