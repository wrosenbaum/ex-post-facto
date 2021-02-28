---
title: "Lecture 02: Recursion I"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 02: Recursion I

## Outline

1. Intro to Recursion
2. Activity: Fibonacci numbers
3. Comparing Recursion and Iteration

## Code Used in This Lecture

- [`Factorial.java`](/assets/java/2021s-cosc-112/lec01-recursion-1/Factorial.java)
- [`Fibonacci.java`](/assets/java/2021s-cosc-112/lec01-recursion-1/Fibonacci.java)
- [`FactorialTest.java`](/assets/java/2021s-cosc-112/lec01-recursion-1/FactorialTest.java)

# Recursion

## Recursion

- Lab 01, you use *iteration* (looping) to solve a problem (printing something on the screen many times)
```java
    for int(i = 0; i < max; i++) {
      // do something
    }
```

- Some problems are naturally *recursive*: solution of the whole problem can be reduced to solving *the same* problem (possibly several times) on a smaller instance.
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

- $$f(n) = n \cdot (n-1) \cdot (n - 2) \cdots 2 \cdot 1$$

Compute some factorials!

<div style="margin-bottom: 12em"></div>


## Factorial: Recursive Formula

For any $n \geq 2$, we have 

- $$f(n) = \begin{cases} 1 &\text{ if } n = 1\\ n \cdot f(n-1) &\text{ if } n > 1.\end{cases}$$

This is a *recursive formula*!

<div style="margin-bottom: 8em"></div>

## Factorial in Code

```java
    private static int factorial(int n) {
        if (n <= 1) {
          return 1;
        }
    
        return n * factorial(n - 1);
    }
```

[Download `Factorial.java`](/assets/java/2021s-cosc-112/lec01-recursion-1/Factorial.java)

## How does this work?

```java
    private static int factorial(int n) {
        if (n == 1) {
          return 1;
        }
    
        return n * factorial(n - 1);
    }
```


Compute `factorial(4)` by hand!

<div style="margin-bottom: 12em"></div>
<!-- This method call returns `4 * factorial(3)` -->
<!-- `factorial(3)` returns `3 * factorial(2)`, so we get a nested sequence of function calls: -->

<!-- - `factorial(4)` returns `4 * factorial(3)` -->
<!--     - `factorial(3)` returns `3 * factorial(2)` -->
<!--         - `factorial(2)` returns `2 *factorial(1)` -->
<!--             - `factorial(1)` returns `1` -->
<!--         - `factorial(2)` returns `2 * 1 = 2` -->
<!--     - `factorial(3)` returns `3 * 2 = 6` -->
<!-- - `factorial(4)` returns `4 * 6 = 24`  -->

## Fibonacci Numbers

The Fibonacci numbers are the sequence of numbers

$1, 1, 2, 3, 5, 8, 13, 21, \ldots$

where each number is the sum of the previous two.

**Activity.** Write a method that computes the $n$-th Fibonacci number for a positive integer (`long`) $n$.

## Two Approaches

- Iterative (computation using a loop)
- Recursive

**Question.** Which is better?

<div style="margin-bottom: 12em"></div>

## Let's Test Their Performance!

[Download `Fibonacci.java`](/assets/java/2021s-cosc-112/lec01-recursion-1/Fibonacci.java)

## Testing the Performance of Factorials

[Download `FactorialTest.java`](/assets/java/2021s-cosc-112/lec01-recursion-1/FactorialTest.java)

## A Question

What makes recursive Fibonacci slow, while recursive factorial is fairly fast?

<div style="margin-bottom: 12em"></div>

## Recursive Fibonacci

```java
private static long fib (long n) {
    if (n <= 2)
        return 1;
	
    return fib(n-1) + fib(n-2);
}
```

<div style="margin-bottom: 12em"></div>


## Recursive Factorial

```java
    private static int factorial(int n) {
        if (n <= 1) {
          return 1;
        }
    
        return n * factorial(n - 1);
    }
```

<div style="margin-bottom: 12em"></div>


## Upcoming

Three morals, three questions

1. Recursion may give simple code, but may be less efficient
    - How can we determine which is better?

2. Fibonacci numbers grow too fast
    - How can we design a better solution?

3. We should do better at error checking
    - Can we validate input while running?



