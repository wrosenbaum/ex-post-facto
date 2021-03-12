---
title: "Project 1: Conway's Game of Life"
layout: reveal
---
{::options syntax_highlighter="nil" /}


# Project 01: Conway’s Game of Life

## Outline

1. Program Introduction
2. Simulation by Hand
3. Program Usage
4. Get Started!

## Conway's Game of Life

- 0 player game played on a grid of squares
- Each cell (square) can be alive or dead
- Next step/generation determined by current configuration
    - each cell has 8 neighbors
    - whether a cell is dead or alive in next generation depends on number of alive neighbors

## Your Task

- Complete implementation of Conway’s Game of Life
- Experiment and find “interesting” initial configurations

## The Challenges

1. Understand and work with a larger program
    - given a lot of code, much of it unfamiliar
	- understand structure enough to work with the code
2. Implement Life
3. Use your program to experiment!

## The Rules

"Game" played on square grid of **cells**

- each cell has 8 neighbors
- cells can be in one of two states: alive or dead
- system evolves in rounds

## Each Round

State of each cell in the *next* round is determined state in this round:

1. If cell is alive and 2 or 3 live neighbors, it stays alive
2. If cell is dead but has 3 live neighbors, it becomes alive
3. Otherwise, cell is dead in next round

## Test Your Understanding

## Simple Examples I (Red = Alive)

![](/assets/img/conways-life/eg01.png){: width="45%"}
![](/assets/img/conways-life/eg02.png){: width="45%"}

## Simple Examples II (Red = Alive)

![](/assets/img/conways-life/eg03.png){: width="45%"}
![](/assets/img/conways-life/eg04.png){: width="45%"}

## Using the Program

1. Compile and run from terminal
2. Terminal usage:

```text
java Life <init file> <no. of generations> <interface: Graphic or Text>
```

## Testing Examples

## Wild Behavior

## Why do People Like CGoL?

1. Simple rules, unexpectedly complex behavior
2. Behavior is *provably* unpredictable
    + given two configurations there is no algorithm that determines whether a game started from the first config eventually reaches the second
3. Can "program" certain behavior
    + *Life* can simulate an *arbitrary* computer program
	
## Initialization File Structure

```text
4 4     // dimensions of board (square)
1 1     // coordinates of alive cells 
1 2
2 1
2 2
```

![](/assets/img/conways-life/eg01.png){: width="25%"}

## Get Started!

