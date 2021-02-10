---
title: "Introduction & Recursion"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Introduction & Recursion

## Agenda
1. Course Introduction and Structure
2. Activity: Zoom Etiquette
3. Recursion
4. Activity: Fibonacci numbers


## Course Introduction and Structure


## What do you know?

- basic data-types and variables `int`, `char`, `String`, `boolean`
- if-then-else statements
- iteration (`for` and `while` loops)
- arrays and strings
- defining functions/methods


## What will this course cover
- objected oriented design: defining and creating `class`es, class hierarchy
- generic programming: interfaces, abstract classes and methods
- simple data structures: linked lists, stacks, queues
- recursion, randomization, and other topics

## What will you do?
- weekly lab assignments (~20%)
- monthly projects (3 total) (~30%)
- quizzes and exams (2 total) (~30%)
- **actively participate in class** (~20%)
    - discussion on Moodle
    - discussion in lecture


## Weekly Rhythm
- Monday: start working on lab/project, attend lab session
- Tuesday: prepare for lecture: reading, videos, etc.; write some code
- Wednesday: lecture and debrief
- Thursday: prepare for lecture; write some code
- Friday: lecture and debrief; turn in assignment

## How will you do it?
- work collaboratively, but don't share code
- participate in discussion
    - Moodle forums
    - Slack channel
- interact with posted materials (readings, videos, etc)
    - assume everything is required, unless marked with **IYI**

[moodle site tour](https://moodle.amherst.edu/course/view.php?id=19909)


## Lecture format
- not really a lecture
    - typically short introduction
- work in small groups on problems
- rejoin larger group to discuss solutions
- **you must prepare by engaging with course materials before lecture**


## You might not like the format (compared to lectures)

[Studies indicate](https://www.pnas.org/content/116/39/19251?cid=nwsltrtn&source=ams&sourceId=5078450) you might feel:

- sessions are disjointed and lacking flow
- there are frequent interruptions to work
- concerned errors you make won't be corrected
- general feeling of frustration and confusion


## But this format is effective
![](https://paper-attachments.dropbox.com/s_897BD7E021B0C0CEEFB21A6C945E56712D55279F1653A924E4AEB866FA319F9E_1598409313843_active-learning.png)


## The moral

Learning probably doesn't feel the way you think it should:

> [W]hen students experienced confusion and increased cognitive effort with active learning, they perceived this disfluency as a signal of poor learning, while in fact the opposite is true.

-- [Deslauriers et al. 2019](https://www.pnas.org/content/116/39/19251?cid=nwsltrtn&source=ams&sourceId=5078450)


## Getting Help
- lab sessions
- Moodle forums and Slack channel
- evening TA sessions (MW 7--9pm)
- office hours
    - drop-in
    - by appointment
- email (if other channels are not appropriate)

# Zoom Etiquette

## Zoom for group discussion
- you will spend most of the lecture time working in breakout groups on Zoom
- you will be given a prompt (Google Doc) to respond to
- you might be asked to code too
- you might be asked to share main points of discussion with class

## Order of business
- we should agree on etiquette/protocol for discussions in Zoom
- and by "we" I mean "you"
    - video/microphone etiquette?
    - asking questions? interruptions?
    - specific roles for small group discussions (scribe, coder, reporter, participant, etc?)

## Go forth and discuss
- join breakout groups
- each group has a Google doc to work on
- we will be back in a few minutes

# Recursion

## Recursion

- in Lab 01, you are using *iteration* (looping) to solve a problem (printing something on the screen many times)
```java
    for int(i = 0; i < max; i++) {
      // do something
    }
```

- in contrast some problems are naturally *recursive*: the solution of the whole problem can be reduced to solving *the same* problem (possibly several times) on a smaller instance.
```java
    public int doSomething(int n) {
      // some code
      doSomething(n - 1); // recursive function call
      // some more code
      return something;
    }
```

## Example: Factorial

For a positive integer $n$, the factorial function, $f(n) = n!$ is defined to be:

$f(n) = n \cdot (n-1) \cdot (n - 2) \cdots 2 \cdot 1$

Thus, $f(1) = 1$, $f(2) = 2 \cdot 1 = 2$, $f(3) = 3 \cdot 2 \cdot 1 = 6$, and so on.

Observe that for any $n \geq 2$, we have $f(n) = n \cdot f(n-1)$, and $f(1) = 1$.

This is a *recursive formula*!


## Factorial in Code
```java
    private static int factorial(int n) {
        if (n == 1) {
          return 1;
        }
    
        return n * factorial(n - 1);
    }
```

## How does this work?

Consider `factorial(4)`. This method call returns `4 * factorial(3)`
`factorial(3)` returns `3 * factorial(2)`, so we get a nested sequence of function calls:

- `factorial(4)` returns `4 * factorial(3)`
    - `factorial(3)` returns `3 * factorial(2)`
        - `factorial(2)` returns `2 *factorial(1)`
            - `factorial(1)` returns `1`
        - `factorial(2)` returns `2 * 1 = 2`
    - `factorial(3)` returns `3 * 2 = 6`
- `factorial(4)` returns `4 * 6 = 24` 

## Fibonacci Numbers

The Fibonacci numbers are the sequence of numbers

$1, 1, 2, 3, 5, 8, 13, 21, \ldots$

where each number is the sum of the previous two.

In this activity, you will be asked to write a function to compute the $n$-th Fibonacci number for a positive integer $n$.


## Upcoming

## Three morals, three questions

1. Recursion may give simple code, but may be less efficient
    - How can we determine which is better?
2. Fibonacci numbers grow too fast
    - How can we design a better solution?
3. We should do better at error checking
    - Can we validate input while running?


