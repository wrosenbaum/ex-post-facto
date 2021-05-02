---
title: "Lab Week 09"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lab 05: Monte Carlo

## Overview

1. Monte Carlo Simulation
2. Lab Demo
3. Getting to Work

## A Question

What is the area of this shape?

![](/assets/img/blobs/blob-2.png){: width="50%"}

## Monte Carlo: A Way of Estimation

Throw darts, and see how many hit!

![](/assets/img/blobs/blob-darts.png){: width="50%"}

## To Implement

1. representation of blob
    - tell if a point is or is not contained in blob
	- find bounding box of blob
2. throw darts
    - generate a random point in the bounding box
	- test if in blob
	- repeat
3. combine results of experiment
    - compute area of bounding box
	- find proportion of hits
	- estimate area
	
## Lab 05: Implement This Procedure

Given:

- `AbstractShape`
- `Circle`
- `Blob`
    + consists of one or more constituent `AbstractShapes`


Your Task:

1. find bounding box
2. determining containment
3. Monte Carlo simulation to estimate area
		
## A Demo

## Questions?

