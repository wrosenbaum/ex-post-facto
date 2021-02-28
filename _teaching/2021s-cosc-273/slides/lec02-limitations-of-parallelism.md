---
title: Limitations of Parallelism
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 02: Limitations of Distributed Control and Parallelism

## Outline

1. Problems with Distributed Control & Braess's Paradox
2. Limits of Parallelism & Amdahl's Law

# Distributed Control

## A Distributed Problem

- All processors/agents may not be under centralized control
- Agents may have individual *incentives*
- Each agent acts autonomously to maximize their *utility*
   + e.g., tuning internet routers to maximize internet speed
   + choosing driving routes to minimize commute time
   
## Traffic Routing

Two types of roads

- Country road: fixed maximum speed
- Freeway: congestion decreases speed
    + more cars $$\implies $$ longer commutes
	+ travel time proportional to number of cars on road
	

## Example

- 100 cars want to travel from city A to city D
- 2 possible routes:
    + northern route through city B
	+ southern route through city C
- Freeways from A to B and from C to D
- Country roads from A to C and B to D


## A Simple Map

<svg width="800" height="500" version="1.1" xmlns="http://www.w3.org/2000/svg">
  <line x1="100" y1="250" x2="400" y2="50" stroke="blue" stroke-width="10"/>
  <line x1="100" y1="250" x2="400" y2="450" stroke="red" stroke-width="10"/>
  <line x1="400" y1="50" x2="700" y2="250" stroke="red" stroke-width="10"/>
  <line x1="400" y1="450" x2="700" y2="250" stroke="blue" stroke-width="10"/>
  
  <circle cx="100" cy="250" r="25" stroke="black" stroke-width="5" fill="white"/>
  <circle cx="400" cy="50" r="25" stroke="black" stroke-width="5" fill="white"/>
  <circle cx="400" cy="450" r="25" stroke="black" stroke-width="5" fill="white"/>
  <circle cx="700" cy="250" r="25" stroke="black" stroke-width="5" fill="white"/>
  
  <text text-anchor="middle" alignment-baseline="middle" x="100" y="250">A</text>
  <text text-anchor="middle" alignment-baseline="middle" x="400" y="50">B</text>
  <text text-anchor="middle" alignment-baseline="middle" x="400" y="450">C</text>
  <text text-anchor="middle" alignment-baseline="middle" x="700" y="250">D</text>
  
  <text text-anchor="middle" alignment-baseline="middle" x="250" y="100">x_B</text>
  <text text-anchor="middle" alignment-baseline="middle" x="250" y="400">1.01</text>
  <text text-anchor="middle" alignment-baseline="middle" x="550" y="100">1.01</text>
  <text text-anchor="middle" alignment-baseline="middle" x="550" y="400">x_C</text>
</svg>

Blue = freeway, Red = country road


## Commute Times

- Commute time on (blue) freeways = proportion of cars on freeway
    + e.g., if 60 cars take freeway from A to B, commute time from A to B is 0.6 hr
- Commute time on (red) country roads constant: 1.01 hr


## Question 1

<svg width="800" height="500" version="1.1" xmlns="http://www.w3.org/2000/svg">
  <line x1="100" y1="250" x2="400" y2="50" stroke="blue" stroke-width="10"/>
  <line x1="100" y1="250" x2="400" y2="450" stroke="red" stroke-width="10"/>
  <line x1="400" y1="50" x2="700" y2="250" stroke="red" stroke-width="10"/>
  <line x1="400" y1="450" x2="700" y2="250" stroke="blue" stroke-width="10"/>
  
  <circle cx="100" cy="250" r="25" stroke="black" stroke-width="5" fill="white"/>
  <circle cx="400" cy="50" r="25" stroke="black" stroke-width="5" fill="white"/>
  <circle cx="400" cy="450" r="25" stroke="black" stroke-width="5" fill="white"/>
  <circle cx="700" cy="250" r="25" stroke="black" stroke-width="5" fill="white"/>
  
  <text text-anchor="middle" alignment-baseline="middle" x="100" y="250">A</text>
  <text text-anchor="middle" alignment-baseline="middle" x="400" y="50">B</text>
  <text text-anchor="middle" alignment-baseline="middle" x="400" y="450">C</text>
  <text text-anchor="middle" alignment-baseline="middle" x="700" y="250">D</text>
  
  <text text-anchor="middle" alignment-baseline="middle" x="250" y="100">x_B</text>
  <text text-anchor="middle" alignment-baseline="middle" x="250" y="400">1.01</text>
  <text text-anchor="middle" alignment-baseline="middle" x="550" y="100">1.01</text>
  <text text-anchor="middle" alignment-baseline="middle" x="550" y="400">x_C</text>
</svg>

Which route, if I *know* how many cars are taking each route?


## Question 2

<svg width="800" height="500" version="1.1" xmlns="http://www.w3.org/2000/svg">
  <line x1="100" y1="250" x2="400" y2="50" stroke="blue" stroke-width="10"/>
  <line x1="100" y1="250" x2="400" y2="450" stroke="red" stroke-width="10"/>
  <line x1="400" y1="50" x2="700" y2="250" stroke="red" stroke-width="10"/>
  <line x1="400" y1="450" x2="700" y2="250" stroke="blue" stroke-width="10"/>
  
  <circle cx="100" cy="250" r="25" stroke="black" stroke-width="5" fill="white"/>
  <circle cx="400" cy="50" r="25" stroke="black" stroke-width="5" fill="white"/>
  <circle cx="400" cy="450" r="25" stroke="black" stroke-width="5" fill="white"/>
  <circle cx="700" cy="250" r="25" stroke="black" stroke-width="5" fill="white"/>
  
  <text text-anchor="middle" alignment-baseline="middle" x="100" y="250">A</text>
  <text text-anchor="middle" alignment-baseline="middle" x="400" y="50">B</text>
  <text text-anchor="middle" alignment-baseline="middle" x="400" y="450">C</text>
  <text text-anchor="middle" alignment-baseline="middle" x="700" y="250">D</text>
  
  <text text-anchor="middle" alignment-baseline="middle" x="250" y="100">x_B</text>
  <text text-anchor="middle" alignment-baseline="middle" x="250" y="400">1.01</text>
  <text text-anchor="middle" alignment-baseline="middle" x="550" y="100">1.01</text>
  <text text-anchor="middle" alignment-baseline="middle" x="550" y="400">x_C</text>
</svg>

How to choose a route if I don't know how many cars take each route?


## Observations

1. If I choose route randomly, my average commute time is 1.51 hr, regardless of what everyone else does.

2. Centralized control can guarantee that everyone's commute is exactly 1.51 hr:
    + route 50 cars through B and 50 cars through C
	
## The Free Market

Elon Musk thinks this map is ridiculous!

<svg width="400" height="250" version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 800 500">
  <line x1="100" y1="250" x2="400" y2="50" stroke="blue" stroke-width="10"/>
  <line x1="100" y1="250" x2="400" y2="450" stroke="red" stroke-width="10"/>
  <line x1="400" y1="50" x2="700" y2="250" stroke="red" stroke-width="10"/>
  <line x1="400" y1="450" x2="700" y2="250" stroke="blue" stroke-width="10"/>
  
  <circle cx="100" cy="250" r="25" stroke="black" stroke-width="5" fill="white"/>
  <circle cx="400" cy="50" r="25" stroke="black" stroke-width="5" fill="white"/>
  <circle cx="400" cy="450" r="25" stroke="black" stroke-width="5" fill="white"/>
  <circle cx="700" cy="250" r="25" stroke="black" stroke-width="5" fill="white"/>
  
  <text text-anchor="middle" alignment-baseline="middle" x="100" y="250">A</text>
  <text text-anchor="middle" alignment-baseline="middle" x="400" y="50">B</text>
  <text text-anchor="middle" alignment-baseline="middle" x="400" y="450">C</text>
  <text text-anchor="middle" alignment-baseline="middle" x="700" y="250">D</text>
  
  <text text-anchor="middle" alignment-baseline="middle" x="250" y="100">x_B</text>
  <text text-anchor="middle" alignment-baseline="middle" x="250" y="400">1.01</text>
  <text text-anchor="middle" alignment-baseline="middle" x="550" y="100">1.01</text>
  <text text-anchor="middle" alignment-baseline="middle" x="550" y="400">x_C</text>
</svg>

> "We will build a hyperloop connecting cities B and C!!! The commute will be free and (almost) instantaneous!!!"


## The New Picture

<svg width="800" height="500" version="1.1" xmlns="http://www.w3.org/2000/svg">
  <line x1="100" y1="250" x2="400" y2="50" stroke="blue" stroke-width="10"/>
  <line x1="100" y1="250" x2="400" y2="450" stroke="red" stroke-width="10"/>
  <line x1="400" y1="50" x2="700" y2="250" stroke="red" stroke-width="10"/>
  <line x1="400" y1="450" x2="700" y2="250" stroke="blue" stroke-width="10"/>
  <line x1="400" y1="50" x2="400" y2="450" stroke="gray" stroke-width="10"/>
  
  <circle cx="100" cy="250" r="25" stroke="black" stroke-width="5" fill="white"/>
  <circle cx="400" cy="50" r="25" stroke="black" stroke-width="5" fill="white"/>
  <circle cx="400" cy="450" r="25" stroke="black" stroke-width="5" fill="white"/>
  <circle cx="700" cy="250" r="25" stroke="black" stroke-width="5" fill="white"/>
  
  <text text-anchor="middle" alignment-baseline="middle" x="100" y="250">A</text>
  <text text-anchor="middle" alignment-baseline="middle" x="400" y="50">B</text>
  <text text-anchor="middle" alignment-baseline="middle" x="400" y="450">C</text>
  <text text-anchor="middle" alignment-baseline="middle" x="700" y="250">D</text>
  
  <text text-anchor="middle" alignment-baseline="middle" x="250" y="100">x_B</text>
  <text text-anchor="middle" alignment-baseline="middle" x="250" y="400">1.01</text>
  <text text-anchor="middle" alignment-baseline="middle" x="550" y="100">1.01</text>
  <text text-anchor="middle" alignment-baseline="middle" x="550" y="400">x_C</text>
  <text text-anchor="middle" alignment-baseline="middle" x="450" y="250">0</text>
</svg>


## What is the Best Strategy?

Previously:

- Choosing B/C route randomly gives expected commute time of 1.51 hr
- Centralized control guaranteed commute times of 1.51 hr

These options are still viable, but now get more choices:

- A to B to C to D: commute time $$X_B + X_C $$
- A to C to B to D: commute time 2.02

Which route should you choose? What is the aggregate behavior?


## Best Strategy?

<svg width="800" height="500" version="1.1" xmlns="http://www.w3.org/2000/svg">
  <line x1="100" y1="250" x2="400" y2="50" stroke="blue" stroke-width="10"/>
  <line x1="100" y1="250" x2="400" y2="450" stroke="red" stroke-width="10"/>
  <line x1="400" y1="50" x2="700" y2="250" stroke="red" stroke-width="10"/>
  <line x1="400" y1="450" x2="700" y2="250" stroke="blue" stroke-width="10"/>
  <line x1="400" y1="50" x2="400" y2="450" stroke="gray" stroke-width="10"/>
  
  <circle cx="100" cy="250" r="25" stroke="black" stroke-width="5" fill="white"/>
  <circle cx="400" cy="50" r="25" stroke="black" stroke-width="5" fill="white"/>
  <circle cx="400" cy="450" r="25" stroke="black" stroke-width="5" fill="white"/>
  <circle cx="700" cy="250" r="25" stroke="black" stroke-width="5" fill="white"/>
  
  <text text-anchor="middle" alignment-baseline="middle" x="100" y="250">A</text>
  <text text-anchor="middle" alignment-baseline="middle" x="400" y="50">B</text>
  <text text-anchor="middle" alignment-baseline="middle" x="400" y="450">C</text>
  <text text-anchor="middle" alignment-baseline="middle" x="700" y="250">D</text>
  
  <text text-anchor="middle" alignment-baseline="middle" x="250" y="100">x_B</text>
  <text text-anchor="middle" alignment-baseline="middle" x="250" y="400">1.01</text>
  <text text-anchor="middle" alignment-baseline="middle" x="550" y="100">1.01</text>
  <text text-anchor="middle" alignment-baseline="middle" x="550" y="400">x_C</text>
  <text text-anchor="middle" alignment-baseline="middle" x="450" y="250">0</text>
</svg>

Which route should you choose? What is the aggregate behavior?


## Dominant Strategy

Observation:

- $$X_B, X_C \leq 1$$ no matter what

Independent of everyone else's choices, your fastest route is A to B to C to D:

- Total cost: $$X_B + X_C$$

This is the *dominant strategy*


## Collective Behavior

If everyone chooses according to self interest:

- all cars commute A to B to C to D
- $$X_B = X_C = 1$$
- everyone's commute takes 2 hours!

Without the hyperloop, expected commute time was 1.51 hours...

- adding the hyperloop **increased** commute times

In real life: closing Times Square to car traffic in NYC *decreased* avg. commute time in Manhattan. ([source](https://blogs.cornell.edu/info2040/2018/09/16/braess-paradox-and-times-squares-pedestrian-plaza/))


## The Price of Anarchy

With hyperloop and everyone choosing their fastest route:

- commute time is 2 hr for everyone

Optimal global strategy

- send half of cars A, B, D and half A, C, D
- commute time is 1.51 hr for everyone

**Rational, self-interested distributed control leads to 33% worse efficiency for everyone!** This is the [Price of Anarchy](https://en.wikipedia.org/wiki/Price_of_anarchy).


## The Morals

1. Having more options can lead to worse outcomes, even when everyone acts rationally in their self interest.

2. Individual rational behavior can lead to worse outcomes for everyone than centralized control.
    + ["Tragedy of the Commons"](https://en.wikipedia.org/wiki/Tragedy_of_the_commons) scenario
	+ see also ["Prisoner's Dilemma"](https://en.wikipedia.org/wiki/Prisoner%27s_dilemma)
	

## COVID as Tragedy of the Commons

- Hard to assess global ramifications of individual behavior

- Situations where everyone

    1. has perfect information
    2. has good intentions
    3. assesses their individual risks rationally
	
can lead to disasterous outcomes

- This issue is inherent to distributed systems, and not merely a result of human or political flaws



# Computational Limits of Parallelism

## Limits of Parallelism

How much can parallelism speed up computation?

- Sometimes a lot
    + Monte Carlo simulation to estimate $$\pi $$
	+ other "embarrassinlgy parallel" tasks
	
- Sometimes not?
    + given a program and a number $$T $$, determine if the program terminates after at most $$T $$ steps
	+ this problem is **conjectured** not to be parallelizable
	
## Quantifying Speedup

Consider a program consisting of
   + $$N $$ elementary operations, 
   + takes time $$T $$ to run sequentially
   
Suppose:
   + a fraction $$p $$ of operations can be performed in parallel
   + (remaining $$1 - p$$ fraction must be performed sequentially)
   
Question: how long to complete computation with $$n $$ parallel machines?
   
## Idea

With $$n $$ parallel machines:

- perform $$p $$-fraction of parallelizable ops in parallel on all $$n $$ machines
    + total time $$T \cdot \frac{p}{n}$$
	
- perform remaining ops sequentially on a single machine
    + total time $$T \cdot (1 - p)$$
	
Total time: $$T \cdot (1 - p) + T \cdot \frac{p}{n} = T \cdot \left(1 - p + \frac p n\right)$$


## How Much Improvement?

The **speedup** is the ratio of the original time $$T $$ to the parallel time $$T \cdot \left(1 - p + \frac p n\right)$$:

- $$S = \frac{1}{1 - p + \frac p n}$$

This is the best performance improvement possible **in principle**

- may not be achievable in practice!


## Example

1 person can chop 1 onion per minute

Recipe calls for:

- chop 6 onions
- saute onions for 4 minutes

Note:

- chopping onions can be done in parallel
- sauteing 
    + takes 4 minutes no matter what
	+ must be accomplished after chopping

## Example (continued)

How much can the cooking process be sped up by $$n $$ cooks?

<div style="margin-bottom: 12em"></div>

## Example (continued)

- For one chef, $$T = 6 + 4 = 10$$
- Only chopping onions is parallelizable, so $$p = 6 / 10 = 0.6$$
- Amdahl's Law:
    + $$S = \frac{1}{1 - p - \frac{p}{n}} = \frac{1}{0.4 + \frac 1 n 0.6}$$
- So:
    + $$n = 2 \implies S = 1.43$$
	+ $$n = 3 \implies S = 1.67$$
	+ $$n = 6 \implies S = 2$$
- Always have $$S < 1 / (1 - p) = 2.5$$

## Speedup Improvement by Adding More Processors

- Second processor: 43%
- Third processor: 17%
- Fourth processor: 9%
- Fifth processor: 6%
- Sixth processor 4%


## Latency vs Number of Processors

How does latency $$T $$ scale with $$n $$?

- Adding more processors has *declining marginal utility*:
    + each additional processor has a smaller effect on total performance
    + at some point, adding more processors to a computation is wasteful
- Another consideration:
    + after parallel ops have been performed, extra processors are idle (potentially wasteful!)

## A Remark

The proportion of parallelizable operations $p$ is not always obvious from problem statement

- Amdahl's law a valuable heuristic for general phenomena:
    1. an $$n $$-fold increase in parallel processing power does not typically give an $$n $$-fold speedup in computations
	2. adding new parallel processors becomes less helpful the more parallel processors you already have
- Often helpful to think about scheduling subtasks (not individual operations)
- May have relationships between tasks (e.g., one must be performed before another)

## Homework Assignment 1

- You'll be asked to take a more nuanced approach to computing an optimal *schedule* for parallel processing

    













