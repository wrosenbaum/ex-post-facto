---
title: "Lecture 07: A `Fraction` Object"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 07: A `Fraction` Object

## Announcements

1. Quiz 02 Posted by tomorrow
    - complete by end of day Sunday
2. Accountability Groups
    - last chance to [**submit survey**](https://forms.gle/WSVyazMuv87644L78)


## Outline

1. Review of bad arithmetic
2. Fixing bad arithmetic with good design
3. Coding and testing a `Fraction` object

## Last Time

Executed this code:

```java
    int a = 10;
    double recip = 1.0 / a;
    double product = 0;
    
    // add 1/a to itself a times
    for (int i = 0; i < a; i++) {
      product += recip;
    }
	
    double one = product;
    int iter = 50;
    for (int i = 0; i < iter; i++) {
         one = one * one;
    }
```

## Expectations vs Reality

With idealized arithmetic

- should have `product = 1.0`, `one = 1.0`

In reality

- `product = 0.99...9` or `product = 1.00...01`
- `one` could be *literally any non-negative value*

## Today

Fixing this issue with design!

## The Issue

- Floating point numbers are represented as (binary) decimal expansions.

- Example: instead of $1 / 3$, computer stores (binary equivalent of) `0.33...3`
    + `float` and `double` are fixed sizes (# of bits/digit) 
	+ expression is *truncated*
	+ so value is not precisely $1 / 3$

- This is issue is inherent to storing fractional values as decimals
    + increasing *precision* will not fix the problem
	
## An Activity

Think about how to represent *precise* fractional values in a computer

- design a `Fraction` object

Hint: no rounding errors for integer values

## State of a Fraction Object

- What instance variables to store?

<div style="margin-bottom: 12em"></div>

## How to Implement Arithmetic (`+` and `*`)?

<div style="margin-bottom: 18em"></div>

## What Subtleties/problems/inconveniences of Using `Fraction`?

<div style="margin-bottom: 18em"></div>

## Let's Code it Together!


