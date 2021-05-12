---
title: "Lab Week 12: Stable Matchings"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Project 3: Stable Matchings

## Overview

1. Stable Marriage Problem
2. Project Description
3. Demo

## Stable Marriage Problem

Setup:

- 2 sets of **agents**
    + residents 
	+ hospitals
- want to match residents to hospitals
    + each resident assigned to one hospital
	+ each hospital assigned one resident
- agents have **preferences**
    + residents rank hospitals
	+ hospitals rank residents
	
How should we match hospitals and residents?

## Background

Problem formalized by Gale & Shapley, 1962

- Defined a criterion for a "good" matching: **stability**
- Described an algorithm to find a stable matching

Central problem in economics

- 1,000's of academic articles
- 4+ books devoted solely to this problem
- 2012 Nobel Prize in Economics (Shapley & Roth)

And recently: Marriage Pact

## Input

- A set $H$ of hospitals
- A set $R$ of residents
- For each hospital $h$:
    + a ranked list of all residents
- For each resident $r$
    + a ranked list of all hospitals
	
Example:

```text
    hospitals
    A B
    residents
    a b
    preferences
    a: A B
    b: A B
    A: a b
    B: a b
```

## Output

```text
    hospitals
    A B
    residents
    a b
    preferences
    a: A B
    b: A B
    A: a b
    B: a b
```

Goal: find a matching where no hospital-resident pair has an incentive to deviate

## Blocking Pairs

```text
    hospitals
    A B
    residents
    a b
    preferences
    a: A B
    b: A B
    A: a b
    B: a b
```

Consider matching `(a, B), (b, A)`

<div style="margin-bottom: 8em"></div>

The pair `(a, A)` is a **blocking pair**.

## Stability

A matching without blocking pairs is **stable**.

```text
    hospitals
    A B
    residents
    a b
    preferences
    a: A B
    b: A B
    A: a b
    B: a b
```

Consider matching `(a, A), (b, B)`

<div style="margin-bottom: 8em"></div>

## An Algorithm (McVitie & Wilson)

1. Residents arrive one at a time
2. When a resident arrives, proposes to first choice hospital
3. When a hospital receives a proposal:
    + if no other proposals yet, provisially accept
	+ otherwise compare proposals
	    - provisionally accept preferred proposal
	    - *refuse* less preferred proposal
4. When a resident receives refusal
    + propose to next choice hospital
	
Continue until all residents arrive

## Algorithm Example

## Project 3

Implement McVitie & Wilson algorithm to find stable matchings!

- Provided with examples/tests

## Program Demo








