---
title: "Project 1: Conway's Game of Life"
description: "implementing a classic mathematical game"
layout: page
---

## Conway’s Game of Life

The Mathematician John Conway invited a “game” called Life as a simplified simulation of cellular life. Conway’s Game of Life was popularized in 1970 when it appeared in Martin Gardner’s monthly column for Scientific American (see the Moodle site for a link to the article). Since then, the game has maintained a cult status as a favorite programming challenge for professional coders and hobbyists alike. You can read more about the game, its history, and terminology here:

[Game of Life](http://www.scholarpedia.org/article/Game_of_Life)

In this project, you will be filling in the final methods for an implementation of Conway’s Game of Life.

#### Rules

The Game of Life takes place on a grid of squares (cells). Each cell is, at any given time in one of two states: `alive` or `dead`. An initial configuration (specifying which cells are alive and which are dead) is provided. Then the grid updates itself in a series of steps (or “generations”). At each step, every cell changes its value based on the values of its neighbors (the neighbors of a cell are the 8 cells around it (above, below, left, right, and the four diagonal cells)). Cells change in each generation based on the following rules:

1. Death by Underpopulation: An `alive` cell with fewer than two living neighbors becomes `dead`.
2. Living on: An `alive` cell with two or three `alive` neighbors stays `alive`.
3. Death by Overpopulation: An `alive` cell with four or more `alive` neighbors becomes `dead`.
4. Reproduction: A dead cell with exactly three `alive` neighbors becomes `alive`.

From these simple rules, very complicated behavior can arise. For example (from wikipedia):

![A LightWeight Space Ship (LWSS)](https://upload.wikimedia.org/wikipedia/commons/3/37/Game_of_life_animated_LWSS.gif)
![A Pulsar](https://upload.wikimedia.org/wikipedia/commons/0/07/Game_of_life_pulsar.gif)
![A Glider](https://upload.wikimedia.org/wikipedia/commons/f/f2/Game_of_life_animated_glider.gif)

#### Code:

In this project you’ll be given more code than you normal get in labs. In addition, this code is documented, and uses a different style than what you’re used to from labs. Professor Kaplan was kind enough to donate the code for this project. I recommend you start early, as there is a lot of code to read through. This project is just as much about understanding and working with a larger code base as it is about writing code to simulate the Game of Life.

This is not a team project. You must work and submit as an individual. See the class policy regarding high level collaboration amongst your fellow students. 

To begin, download and unzip the following file:

- [`Life.zip`](/assets/java/2021s-cosc-112/Life.zip)

First, read through the java files to understand how the program is structured.
Then, to compile, type:

```text
    javac *.java
```

(The `*` is a special character (called a wildcard) which, in this context, lets us compile all the `.java` files at once.)

I recommend compiling and running the program in a terminal (e.g., the terminal in IntelliJ, Terminal.app on MacOS, or Command Prompt on Windows). To run the program, issue the commands as follows:

```text
    java Life [some init file] [number of generations to run] [Graphic or Text]
```

For example:

```text
    java Life simple.init 10 Graphic
``` 

will run the problem using the initial configuration specified in `simple.init` for `10` generations.

#### Getting started

Extract the files into a new folder, and examine the various Java source code files. There’s a good bit there, and you should expect to spend significant time simply grasping the relationship between the files. Here is a description of what’s there: 

-  `Life.java`: This simple class contains the `main()` method that gets the program started. It creates a Game object and then calls `play()` on that object to get the program moving. You should not change this class. 

-  `Game.java`: A Game is the high-level director of this cellular simulation. It reads some not-so-simple work of reading an initial grid file (see below) and creating the Grid of Cell objects described therein. It then is responsible for evolving the cells for the number of generations request by the user, displaying the grid at each through a UserInterface object. There are two methods in this class that you must write: 
    1. `evolve()`
    2. `getPopulation()` 
	

- `Grid.java`: A Grid is a two-dimensional container of Cell objects. You should not change this class. 

- `Cell.java`: A `Cell` is either dead or alive. It additionally determines, based on the cells around it—its neighborhood—whether it should live or die in the next generation. There are three methods in this class that you must write: 
    1. `countLiveNeighbors()` 
    2. `evolve()`
    3. `advance()` 
	
- `UserInterface.java`: An interface (which we will discuss in more detail in an upcoming week) that defines how to show the state of the grid to a user at each generation. 

- `TextInterface.java`: An implementation of a UserInterface that shows the evolving grid by printing each generation to the console. 

- `Support.java`: A handy utility method or two. You should not change this class. 

- `simple.init`: A simple initial grid file. It contains pairs of integers such that the first line provides the size of the grid, while all subsequent lines provide the coordinates of initially live cells. Taken together, these form starting state of the game in generation 0. 

- `X-pattern.init`: Another initial grid file. It specifies a modestly larger grid with a more interesting pattern of initially live cells.
What you need to implement

For this assignment, you will be writing 5 methods:


1. In `Game.java`: `evolve()`
2. In `Game.java`: `getPopulation()`
3. In `Cell.java`: `countLiveNeighbors()`
4. In `Cell.java`: `evolve()`
5. In `Cell.java`: `advance()`

Look at the comments within the code, as well as how each method is called, to see what each method should do.

You should not change any other `.java` file. 

Tips:

1. Start early. You should read through the code to understand what is going on at a high level before you dive in and start writing code. It can take time to grok what’s happening, so start early.

2. `simple.init` and `X-pattern.init` are fairly simple initialization configurations. Just because your solution works on those doesn’t mean it works in general. You should perform your own, additional testing by creating other initialization files. The file line of an `.init` file contains the width and height of the grid. The rest of the lines specify the locations of all `alive` cells.

3. Be methodical when debugging. Make `.init` files which are simple, and for which you know what should happen. When you make a change to your code, check that you still pass tests you used to pass.

4. Here is a link to a web-based implementation of Conway’s Game of Life. This may be helpful in experimenting with initial configurations with interesting behavior.

#### To Submit Your Work

Please submit 4 files to Moodle by Friday, March 12th, 11:59 AoE (7:59 am Saturday Sept. 19th in Amherst):

1. `Game.java` with the modifications specified above.
2. `Cell.java` with the modifications specified above.
3. `myconfig.init` which specifies an initial configuration which leads to interesting behavior when run for 100 generations. Note: There will be some subjective grading for this, but for the most part, anything non-trivial will result in full credit, and I reserve the right to grant extra credit for particularly interesting submissions.
4. `README.txt` wich describes the behavior of `myconfig.init`, and what you find interesting or cool about it.

Additionally, you will be asked to respond to a survey when you submit the assignment.

#### Grading

The grading will be on a 5 point scale. 4 points are for the program correctness (i.e., `Game.java` and `Cell.java`), and 1 point is for your initial configuration `myconfig.init` and its description in `README.txt`.

- **4 points.** Submission produces the desired output in the manner specified by the assignment. Code is clearly organized with comments describing what different components of the code do.

- **3 points.** Submission produces correct output on some inputs, but may diverge from the program description in the assignment. Code is not clearly organized and/or lacking helpful comments.

- **2 point.** Submission compiles and runs, but does not produce the desired output for many inputs. Code is confusingly written without useful comments.

- **1 point.** Submission compiles and runs, and produces *some* output.

- **0 points.** Submission does not compile. 

The grading for `myconfig.init` will be more subjective, but any submission that clearly demonstrates that you put some thought and effort into it will receive full marks.


