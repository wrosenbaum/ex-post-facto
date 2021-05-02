---
title: "Lecture 20: Stacks Continued"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 20: Stacks Continued

## Last Time

Introduced stack abstract data type

```java
public interface SimpleStack<T> {
    // push a new item on the top of the stack
    public void push(T item);

    // return the item currently at the top of the stack
    public T peek();

    // remove and return the item currently at the top of the stack
    public T pop();

    // check if the stack is empty
    public boolean isEmpty();
}
```

## Interpretation of Stack Operations

![](/assets/img/stacks/book-1.png){: width="100%"}

Push *Sequential...Algorithms* onto stack

## Stack Operations

![](/assets/img/stacks/book-2.png){: width="100%"}

Push *Art of Multiprocessor Programming* onto stack

## Stack Operations

![](/assets/img/stacks/book-3.png){: width="100%"}

Push *Java Concurrency in Practice* onto stack

## Stack Operations

![](/assets/img/stacks/book-3.png){: width="100%"}

Peek returns *Java Concurrency in Practice*

## Stack Operations

![](/assets/img/stacks/book-2.png){: width="100%"}

Pop returns & removes *Java Concurrency in Practice*

## Stack Operations

![](/assets/img/stacks/book-1.png){: width="100%"}

Pop returns & removes *Art of Multiprocessor Programming*

## You Were Asked

How could you use a linked list to store the contents of a stack? 

- How do you push a new item on the stack? 
- How do you pop an item off the stack?

<div style="margin-bottom: 12em"></div>

## Idea

1. Elements stored in linked list of `Node`s
2. Store a `Node top` referring to top of stack
3. Each `Node`'s `next` field refers to element below it

## `StackList` Illustrated

![](/assets/img/stacks/list-stack.png){: width="100%"}

## `push()` Step 1: Create Node

![](/assets/img/stacks/list-stack-push-1.png){: width="100%"}

## `push()` Step 2: Set `next`

![](/assets/img/stacks/list-stack-push-2.png){: width="100%"}

## `push()` Step 3: Set `head`

![](/assets/img/stacks/list-stack-push-3.png){: width="100%"}

## `push()` Complete

![](/assets/img/stacks/list-stack-push-4.png){: width="100%"}

## `pop()`?

![](/assets/img/stacks/list-stack-push-4.png){: width="100%"}

## `pop()` Step 1: Store `value`

![](/assets/img/stacks/list-stack-pop-1.png){: width="100%"}

## `pop()` Step 2: Update `head`

![](/assets/img/stacks/list-stack-pop-2.png){: width="100%"}

## `pop()` Step 3: Return `value`

![](/assets/img/stacks/list-stack-pop-3.png){: width="100%"}


## Implementing `StackList<T>`

```java
public class StackList<T> implements SimpleStack<T> {
    Node top = null;
    
    public void push(T item) {...}
    
    public T peek() {...}
    
    public T pop() {...}
    
    public boolean isEmpty() {...}

    class Node {
	T item;
	Node next;

	public Node (T item) {
	    this.item = item;
	    next = null;
	}
    }
```

## A Question

What is wrong with the following program?

```java
public class HelloWorld {
    public static void main(String[] args)} {
        System.out.println("Hello, World!");
    }
}
```

<div style="margin-bottom: 8em"></div>




## Application: Balanced Expressions

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

- What makes `{ } { { } { { } } } { }` okay?
- What makes `{ ( [ ] ) } { ( ) } }` not?

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

## Implementation












