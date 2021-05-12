---
title: "Lecture 21: Stacks and Balanced Expressions"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 21: Stacks and Balanced Expressions

## Overview

1. Project 3: Stable Matchings
2. Balanced Expressions
3. Queues

# Project 3: Stable Matchings

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
    + including several by me
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

![](/assets/img/stable-matchings/sm-instance.png){: width="100%"}

## M & W Step 1: `a` proposal

![](/assets/img/stable-matchings/sm-1.png){: width="100%"}

## M & W Step 2: `b` proposal

![](/assets/img/stable-matchings/sm-2.png){: width="100%"}

## M & W Step 3: `B` refusal

![](/assets/img/stable-matchings/sm-3.png){: width="100%"}

## M & W Step 4: `a` proposal

![](/assets/img/stable-matchings/sm-4.png){: width="100%"}

## M & W Step 5: `c` proposal

![](/assets/img/stable-matchings/sm-5.png){: width="100%"}

## M & W Step 6: `A` refusal

![](/assets/img/stable-matchings/sm-6.png){: width="100%"}

## M & W Step 7: `c` proposal

![](/assets/img/stable-matchings/sm-7.png){: width="100%"}

## M & W Step 8: `B` refusal

![](/assets/img/stable-matchings/sm-8.png){: width="100%"}

## M & W Step 9: `b` proposal

![](/assets/img/stable-matchings/sm-9.png){: width="100%"}

## M & W Step 10: `A` refusal

![](/assets/img/stable-matchings/sm-10.png){: width="100%"}

## M & W Step 11: `a` proposal

![](/assets/img/stable-matchings/sm-11.png){: width="100%"}

## And Done!

![](/assets/img/stable-matchings/sm-11.png){: width="100%"}


## Project 3

Implement McVitie & Wilson algorithm to find stable matchings!

- Provided with examples/tests

<!-- ## Program Demo -->

# Balanced Expressions

## Brackets

Java uses a lot of brackets:

- curly braces `{ ... }`
- parentheses `( ... )`
- square brackets `[ ... ]`

If expression are properly bracketed, Java compiler complains!

## An Abstraction

This expression

```java
public class HelloWorld {
    public static void main(String[] args)} {
        System.out.println("Hello, World!");
    }
}
```

but with just the brackets:

```java
{ ( [ ] ) } { ( ) } }
```

<div style="margin-bottom: 8em"></div>


## Question

When is an expression properly bracketed?

- What makes `{ ( [ ] ) } { ( ) } }` okay?
- What makes `{ ( [ ] ) } } { ( ) } }` not?

<div style="margin-bottom: 12em"></div>

## Balanced Expressions

A bracketed expression is *balanced* if

1. every opening bracket has a corresponding closing bracket 
    - closing bracket appears after opening bracket
    - one-to-one correspondence between opening and closing
2. opening-closing pairs are *nested*
    - if opening bracket appears between opening-closing pair, then corresponding closing bracket does too

<div style="margin-bottom: 2em"></div>

```text
{    {    {    }    }    {    }    {    }    }
```

<div style="margin-bottom: 2em"></div>

```text
{    {    }    }    }    {    }
```

## How To Check For Balance?

<div style="margin-bottom: 4em"></div>

```text
{    {    {    }    }    {    }    {    }    }
```

<div style="margin-bottom: 8em"></div>

## Balance Checking with a Stack

1. Scan expression from left to right
2. Whenever an open bracket is encountered, push it to the stack
3. When a closing bracket is encountered:
    - check if it matches top of stack
	- if so, pop the stack
	- if not, or stack is empty, expression is not balanced!
4. If stack is not empty at end of expression, expression is not balanced!

## Checking for Balance

```text
{    {    {    }    }    {    }    {    }    }
```

<div style="margin-bottom: 18em"></div>


## Implementation

<!-- ## Stack Ordering -->

<!-- Stacks are **L**ast **I**n, **F**irst **O**ut (LIFO): -->

<!-- - the last item pushed is next item popped -->

<!-- Often items should be processed in the order they arrive: -->

<!-- - **F**irst **I**n, **F**irst **O**ut (FIFO) -->

<!-- Examples: -->

<!-- - Standing in line  -->
<!--     + first come, first serve -->
<!-- - Communication buffers -->
<!--     + reading from `InputStream in`, `in.get()` reads characters in order -->

<!-- ## The Queue -->

<!-- Another abstract data type: the **queue** -->

<!-- Supports two basic operations: -->

<!-- - `void enq(T item)` enqueue (add) an item to the end of the queue -->
<!-- - `T deq()` dequeue (remove) item from the front queue and return it -->

<!-- The first item enqueued is the first to be dequeued, etc -->

<!-- ## Visualizing Enqueue -->

<!-- ![](/assets/img/queues/enqueue-1.png){: width="100%"} -->

<!-- ## Enqueue Another -->

<!-- ![](/assets/img/queues/enqueue-2.png){: width="100%"} -->

<!-- ## And Another -->

<!-- ![](/assets/img/queues/enqueue-3.png){: width="100%"} -->

<!-- ## Dequeue Once -->

<!-- ![](/assets/img/queues/dequeue-1.png){: width="100%"} -->

<!-- ## Enqueue Again -->

<!-- ![](/assets/img/queues/enqueue-4.png){: width="100%"} -->

<!-- ## Dequeue Again -->

<!-- ![](/assets/img/queues/dequeue-2.png){: width="100%"} -->

<!-- ## Enqueue Last Time -->

<!-- ![](/assets/img/queues/enqueue-5.png){: width="100%"} -->

<!-- ## Implementing a Queue -->

<!-- How could we implement a queue with a linked list? -->

<!-- ## A `QueueList` -->

<!-- ![](/assets/img/queues/queue-list.png){: width="100%"} -->

<!-- ## Enqueue Step 1 -->

<!-- ![](/assets/img/queues/list-enqueue-1.png){: width="100%"} -->

<!-- ## Enqueue Step 2 -->

<!-- ![](/assets/img/queues/list-enqueue-2.png){: width="100%"} -->

<!-- ## Enqueue Step 3 -->

<!-- ![](/assets/img/queues/list-enqueue-3.png){: width="100%"} -->

<!-- ## Item Enqueued! Dequeue? -->

<!-- ![](/assets/img/queues/list-enqueue-4.png){: width="100%"} -->

<!-- ## Dequeue Step 1 -->

<!-- ![](/assets/img/queues/list-dequeue-1.png){: width="100%"} -->

<!-- ## Dequeue Step 2 -->

<!-- ![](/assets/img/queues/list-dequeue-2.png){: width="100%"} -->

<!-- ## Dequeue Step 3 -->

<!-- ![](/assets/img/queues/list-dequeue-3.png){: width="100%"} -->

<!-- ## To Do -->

<!-- Implement a `QueueList<T>` in Java! -->

