---
title: "Monte Carlo Simulation"
layout: page
---

In this note, we briefly describe the method of Mote Carlo simulation for computing mathematical values. Monte Carlo simulation is a powerful and flexible tool for estimating values that might be difficult to compute otherwise. The basic idea is to set up a randomized experiment whose success probability or outcome is related to the value we wish to compute. By running the experiment many times, we can then estimate the success probability, from which we can compute an estimate of the value or outcome we're looking for. 

### Example: Computing $$\pi$$

The number $$\pi = 3.14159\cdots$$ is one of the most important (and well-studied) mathematical constants. Its value has been computed to incredible accuracy. You can get representation of $$\pi$$ as a `double` in Java using `Math.PI`. But what if we didn't know that? How could we compute---or estimate---the value of $$\pi$$?

One way of estimating $$\pi$$ is to use the formula for the area of disk (i.e., the region bounded by a circle): $$A = \pi r^2$$, where $$A$$ is the disk's area, and $$r$$ its radius. Imagine a circular dartboard inside a square frame, where the diameter of the dartboard is equal to side length of the frame:


{:refdef: style="text-align: center;"}
![dartboard](/assets/img/monte-carlo-method/dartboard.svg){: width="50%"}
{: refdef}

If the dartboard has a radius $$r $$, then its area is $$A_{d} = \pi r^2$$. Meanwhile, the inner side length of the frame is $$2 r$$, so the framed region (i.e., white and blue regions above) have an area of $$A_f = (2 r)^2 = 4 r^2$$. Suppose I throw a dart at the frame. Now, I'm not very good at throwing darts, so the dart is equally likely to land anywhere inside the frame (and if it misses the frame entirely, I'll keep throwing until it hits inside the frame). Given that (1) the dart lands within the frame, and (2) the dart is equally likely to land anywhere within the frame, the probability, $$p $$, that the dart hits the dartboard is the ratio of areas of the dartboard and the framed region:

$$
p = \frac{A_d}{A_f} = \frac{\pi r^2}{4 r^2} = \frac{\pi}{4}.
$$

We can solve this expression for $$\pi $$ to get $$\pi = 4 p$$.

The crucial observation is that we can estimate $$p$$ by throwing (many) darts at the frame and counting the number of times they hit the dartboard:

$$
p \approx \frac{\#\text{times dartboard hit}}{\#\text{darts thrown}}.
$$

So in order to estimate $$\pi = 4 p$$, it suffices to throw enough darts at our dartboard, and count the number of hits.

**IYI.** The fact that $$ p $$ is approximated by the expression above is mathematically guaranteed by the [law of large numbers](https://en.wikipedia.org/wiki/Law_of_large_numbers). In fact, a more careful analysis can tell us how many experiments (i.e. darts) we need to run in order to approximate $$ \pi $$ to a desired accuracy. Such an analysis shows that the standard deviation of our estimate---i.e., the expected amount by which the estimate differs from $$ \pi$$---is proportional to $$1 / \sqrt{n}$$, where $$n$$ is the number of experiments performed. Therefore if we want to double the accuracy of our estimate, we need to run 4 times as many experiments; for each extra digit of precision (i.e., tenfold increase in accuracy) we need to do 100 times as many experiments!

Thankfully, we don't need a real dartboard (and endless time) to perform the experiment above: we can write a program to do the procedure for us! All we have to do is choose a suitable square and inscribed disk. Then we can generate random points in the square (i.e., pairs of random numbers with appropriate bounds) and check whether or not they lie in the disk.

Here is a table of outputs for a program that uses the Monte Carlo simulation procedure described above to estimate $$\pi$$.

--------------------

| number of points &nbsp;&nbsp;| $$ \pi$$ estimate |
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

--------------------

The the correct digits in the table are indicated in boldface. Notice that as the number of samples increases, so does the accuracy of our approximation of $$ \pi$$, albeit, slowly: we have to increase the number of samples by a factor of about 100 in order to gain a single digit of accuracy. 

There are much more efficient ways of computing $$\pi $$, but the Monte Carlo method has two main advantages. First, the method is conceptually simple (relying only on a fact from high-school level geometry) and straightforward to program (my solution is only 8 lines of code). The second advantage is that the method is **[embarrassingly parallel](https://en.wikipedia.org/wiki/Embarrassingly_parallel)**. That is, if we have multiple processors/computers/people generating samples independently, the outcomes of those experiments can be combined to provide a more accurate estimate. Thus, even though this method is inefficient for a single processor, the computation can easily be sped up if multiple processors are used.

**Exercise.** Code up your own program that runs a Monte Carlo simulation to estimate $$ \pi$$. How long does it take to produce 1,000,000 samples? 1,000,000,000 samples?

### Monte Carlo Simulation

The method of calculating $$\pi $$ described above is but one simple example of Monte Carlo simulation. In the case of calculating digits of $$\pi $$, much more efficient methods exist. However, for more complex problems---especially those arising in the sciences, engineering, and finance---Monte Carlo simulation is a common way of predicting the behavior of a system. 

For example, consider the problem of predicting the trajectory of a hurricane. Meteorologists use mathematical models to predict the future weather patterns from current observations. However, the predictions can vary dramatically given small deviations in the current observations. Therefore, meteorologists run many simulations from the same current observations with slight random perturbations of the input. A more accurate prediction is then formed by looking at the average behavior of the many simulations. Similar techniques are applied in engineering to predict the structural integrity of buildings and bridges during a storm, in mathematical finance to predict the behavior of stocks, and in countless other data-intensive applications.

Even many seemingly simple problems turn out to bee far complicated to solve analytically. Consider the card game [Solitaire](https://en.wikipedia.org/wiki/Klondike) (of which there are [many variations](https://en.wikipedia.org/wiki/Solitaire)). Despite the game's relative simplicity, it is not known what the probability that a game is winnable starting from a randomly shuffled deck! Nonetheless, we can estimate the probability of winning a game by Monte Carlo simulation.

**Exercise.** Write a program that plays (some version of) Solitaire, and use a Monte Carlo simulation to estimate the probability of winning. 

As we saw above, Monte Carlo simulation can be computationally demanding: computing $$\pi $$ to just 4 decimal places required 1 billion samples! Yet the upshot is that Monte Carlo simulations are easily parallelizable. Many simulations can be run independently in parallel on different processors or computers, and the results from the simulations easily combined. Thus, Monte Carlo simulation is one of the few computational techniques where simply throwing more computational power at a problem easily gives better results. A more careful treatment of distributed and parallel computing shows that this is not typically the case.
