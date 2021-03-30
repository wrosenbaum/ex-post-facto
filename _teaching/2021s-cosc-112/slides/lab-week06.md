---
title: "Lab Week 06: Exceptions"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lab Week 06: Exceptions

## Plan

1. Exceptions
2. Input Stream
3. Starting Lab 04: Exceptions

# Exceptions

## A Problem You Brought Up

```java
    public class Fraction {
        private int num;
        private int den;
        
        Fraction (int num, int den) {
            this.num = num;
            this.den = den;
            reduce();
        }
    }
```
What stops us from calling `new Fraction(1, 0)`?

## Nothing Stops Us!

Unique problem:

- Nothing *formally* wrong with `new Fraction(1, 0)`
    + valid Java syntax, no error
- Problem is with semantics
    + $1 / 0$ is not a well defined fraction
    + not a programming error, but a logical error

## Enter Exceptions

Exceptions give a way of:

1. Signaling that a method could introduce undesireable behavior 
2. Detecting that undesireable behavior occurred
3. Handling the undesireable behavior
    - make the method caller deal with it!

## Declaring Methods that Throw Exceptions

```java
        Fraction (int num, int den) throws ArithmeticException {
            if (den == 0) {
                throw new ArithmeticException("division by zero");
            }
            this.num = num;
            this.den = den;
            reduce();
        }
```

When `throw ...` is executed, method call halts

- like `return` statement, but nothing returned; no `Fraction` created

## What happens

- Now `new Fraction(1, 0)` throws the exception
- This is good because we see that there was an error

## Catching Exceptions

When an exception is *thrown* it can be *caught*

- Catching exceptions allows us to handle bad behavior without crashing the program

```java
Fraction f;
try {
    // some code that could throw an exception
    f = newFraction(getNum(), getDen());
} catch (ArithmeticException e) {
    // code to be executed if exception is thrown
    System.out.println(e);
    System.out.println("Using default value of 0/1");
    f = newFraction(0, 1);
}
```

- This is a **`try-catch` block**

# Input Streams

## How Does Your Computer Read (Keyboard) Input?

- 1 character at a time
- With each keystroke, it decides what to do next
    + print a letter on the screen?
	+ wait for another keystroke (e.g., shift key)?

## Accessing Individual Characters

- Use `InputStream in`
    + in particular `System.in`
- `in.read()` returns one character at a time from terminal input
    + returns an `int`
	+ `0` through `255` represents `char`
	+ `-1` represents end of stream
	+ throws `IOException` if something goes wrong
	
# Lab 04: Exceptions

## Terminal Input

- Just a sequence (stream) of *characters*
- Sometimes might represent an integer:
    + `123`, `-23` represent numbers
	+ `Mammoth`, `12a3` don't represent numbers

<div style="margin-bottom: 6em"></div>

## Your Task

Write a program that
1. Parses integers from input stream
    - stream may contain many values separated by whitespace
	- e.g., `1 yes 2 no -321`
2. Returns values of valid integers as `int`
3. Throws an exception if value is not an integer

## Program Demo

## How?

- Use `in.read()` to get individual characters
- Each time a character is read, decide what to do
    + update `int` value?
	+ return value?
	+ throw exception?
	+ ???
	
<div style="margin-bottom: 8em"></div>

## Suggestion

Implement solution with no exception handling first

- Assume all input consists of valid numbers
- Once this solution works, modify it to handle exceptions
