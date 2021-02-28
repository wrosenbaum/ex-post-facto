---
title: Welcome to COSC 273!
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Welcome to COSC 273!

## Outline

- Introductions
- Lab Section Plan
- Lab 01: Estimating Pi
- Other Examples

(general course introduction in lecture)

# Introductions

## Your Professor

- [Will Rosenbaum](https://www.willrosenbaum.com)
- Originally from Seattle 
- Undregrad at Reed College in Portland, OR
- PhD in Mathematics from UCLA
- Postdocs at 
    + Tel Aviv University
	+ Max Planck Institute for Informatics (Saarbruecken, Germany)
- Started at Amherst last fall

## My Research

- Theoretical Computer Science
    + Interface between math and CS
- Theory of Distributed Systems
    + What can systems of interacting processors (broadly construed) compute?
- Research Questions
    + How efficiently can a computational task be performed in principle?
	+ What resources (time, memory, communication) are required to perform tasks?
	+ What tasks *cannot* be solved efficiently?
	
## Outside of Work

- Spend most time with family: Alivia, Ione (daughter), Finnegan (dog), Pip & Posy (cats)
- Hobbies: cooking, playing piano, hiking

# Lab Sessions

## Purpose

- Informal discussion (small groups)
- Get questions answered
- Lectures focus on *principles*
- Labs focus on *practice* (i.e., coding)
    - Troubleshooting code, etc


## Lab Structure

- Orientation/Lab introduction
- Questions
- Small group discussion
- Brief recap

## Lab Enrollment

Current enrollment:

- Lab 01: 36
- Lab 02: 5

Need to balance these enrollments!

- I'll send out questionnaire to balance sections class as equitably as equitably as possible

## Course Enrollment

- Currently full
- Many others want to enroll
- If you plan to drop the class, please do so early so that others can enroll during add/drop period

# Lab 01: Estimating Pi

## A Formula from High School

{:refdef: style="text-align: center;"}
![dartboard](/assets/img/monte-carlo-method/dartboard.svg){: width="40%"}
{: refdef}

Area of a disk: $$A = \pi r^2$$

## An Idea from Probability

Pick a random point inside the framed region.

{:refdef: style="text-align: center;"}
![dartboard](/assets/img/monte-carlo-method/dartboard.svg){: width="40%"}
{: refdef}

The *probability* the point lies in the disk is proportional to the disk's area.

## In More Detail

- area of disk is $$\pi r^2$$
- area of surrounding square is $$(2 r)^2 = 4 r^2$$
- the probability that a (uniformly) random point in the square lies in the disk is:
$$
\frac{\text{area of circle}}{\text{area of square}} = \frac{\pi r^2}{4 r^2} = \frac 1 4 \pi.
$$

so...

## Estimation by Sampling

...to estimate $$\pi $$, suffices to estimate the probability that a random point point in the square lies inside the disk:

- pick a bunch of random points
- see how many lie in disk
- $$p = $$ proportion of points that do
- $$\pi \approx 4 p$$

Example of **Monte Carlo method**

## An Example by Hand

{:refdef: style="text-align: center;"}
![dartboard](/assets/img/monte-carlo-method/dartboard.svg){: width="40%"}
{: refdef}

Number of Samples:

Number of Hits: 

$$\pi $$ Estimate: 


## Does This Work?

- Mathematically guaranteed to work most of the time, for sufficiently many random points
- How many?
- How efficient is this solution?

## Basic Pi Demo

## Speeding Things Up

A nice feature of the code:
- The more samples we run, the better the approximation
- Samples can be run *independently* and results aggregated

## Speeding Things Up

A nice feature of modern computers:
- They can do multiple independent operations *in parallel*
- We can generate indpendent samples concurrently
- Just need to figure out how in code!

## Parallel Pi Demo

## Threads in Java

Threads...

- are single sequences of operations
- can be executed concurrently/in parallel by modern computers
- can be created/run by making instances of the `Thread` class
- **are incredibly subtle to reason about**

## Lab 01

Use multithreading to estimate $$\pi $$ as quickly as possible (using Monte Carlo method above)

- [Notes on Multithreading Here](/teaching/2021s-cosc-273/notes/multithreading)

## Multithreading is Great!

Fractal Example






