---
title: "Lecture 23: Josephus and Mazes"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 23: Josephus and Mazes

## Overview

1. Josephus Problem
2. Solving Mazes

## Last Time

1. Queue ADT
    - enqueue/dequeue operations
	- FIFO (first-in, first-out)
2. Josephus Problem

## Josephus’ Manicure Problem

- $n$ people determine to give each other manicures
- they only have 1 set of tools/supplies
    + only one person can give another a manicure at a time
- once a person receives a manicure, they leave

Setup:

- people seated at a round table
- labeled sequentially clock-wise from $1$ to $n$

## A System

- the current manicurist gives a manicure to the person seated to their left
- after the manicure, person to their left leaves
- the manicurist hands the tools to the next person to their left who becomes the next manicurist
- repeat until all but one person receives a manicure

Initially, person 1 is manicurist.

## Example $n = 5$

![](/assets/img/josephus/josephus-5.png){: width="100%"}

## An Activity

- Devise a procedure for determining where Josephus should sit to avoid a manicure

- Use a queue!
    + assume your queue has a `getSize()` method
	
## How Can We Find Josephus' Spot?

<div style="margin-bottom: 18em"></div>

## Proposed Queue Solution

1. Add people 1 through n to a queue in order 
    - head of queue is manicurist
	- person behind them receives manicure
2. Repeat until queue has size 1:
    1. dequeue manicurist
    2. enqueue manicurist
    3. dequeue receiver of manicure
	
<div style="margin-bottom: 8em"></div>

## Code Up a Solution!

# Mazes

What is a Maze?

- a grid of cells
- each cell has up to 4 neighboring cells
    - some adjacent cells blocked off by walls
- specified starting cell and goal cell

**Objective** find a path from starting cell to goal cell

## Maze Example

![](/assets/img/mazes/maze01.png){: width="50%"}

## An Activity

How can we solve mazes in general?

- Come up with a procedure that will always find a solution to a maze
- No code

## Maze Example

![](/assets/img/mazes/maze01.png){: width="50%"}

## High Level Strategy

- explore until we reach the goal!
- keep track of where we’ve been so we don’t repeat cells

## In More Detail

- fix an order on possible neighbors of cells
    - e.g., north, east, south west
- when visiting a cell:
    - mark it as visited
    - check if it is the goal
    - if not, visit “next” unvisited neighbor
    - if no unvisited neighbors, return to “parent” cell
	

## Representing a Maze in Code

- `Cell` class stores (among other things)
    - list of references to neighboring `Cell`s (those not separated by walls)
- `Maze` class stores
    - 2d array of `Cell`s
    - `start` and `goal` cells
	
## Implementing Our Solution Strategy

- use an `ArrayList` to keep track of `visited` cells
- use a stack to keep track of `active`  cells
    - `active` cells form a path from `start` to current cell
    - when we visit a new cell, push it to the stack
    - when we’ve explored all neighbors (and haven’t found the goal):
        - pop current cell off stack
        - return to previous cell on stack—cell from which we initially visited current cell

## Illustration

![](/assets/img/mazes/maze02.png){: width="30%"}

<div style="margin-bottom: 6em"></div>

## Recursion: Our Old Friend

Use a stack an find goal recursively

- write method `boolean solve (Stack<Cell> active, ArrayList<Cell> visited) {...}`

How?

## Stack-based solution

- idea: try to find `goal` from `current` cell (top of `active`)
    - if `current` is `goal`, return `true`
    - otherwise, find `goal` from unvisited neighbors:
        - push neighbor onto `active` stack and add neighbor to `visited`
        - call `solve` to continue exploration from new neighbor
    - if recursive call to `solve` returns `true`, we should return `true` too
    - if exploration fails to find anything pop `current` off stack and return `false`

When `goal` is found, `active` will contain the path from `start` to `goal` 	

## Solution in Code


## Question

Some of our solutions were really inefficient!

- goal could be adjacent to start, but a much longer path from start to goal is found

How could we ensure that we find the shortest path from start to goal?

