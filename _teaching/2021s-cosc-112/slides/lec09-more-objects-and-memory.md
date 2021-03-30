---
title: "Lecture 09: More Objects and Memory"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 09: More Objects and Memory

## Announcements

1. No class on Friday
2. Quiz 02 posted later today

## Accountability Groups

Why?

- Connect with peers in class
    + support structure
- Discuss material from class
    + what is confusing?
	+ what is exciting?
- Give feedback to me

## Last Time

A Simple `Number` Class

```java
class Number {
    public int value;

    Number (int val) {
	value = val;
    }
}
```

## First Question

What does the following code do?

```java
    Number aNum = new Number(10);
    Number bNum = new Number(5);
    
    bNum = aNum;
    aNum.value = 20;
```

What are `aNum.value` and `bNum.value` at the end of the execution?

<div style="margin-bottom: 8em"></div>

## Second Question

Consider the method

```java
    void setNumberValue (Number a, int value) {
      a.value = value;
    }
```

What is the result of

```java
    setNumberValue(aNum, 0);
```

What is the value of `aNum.value` at the end of the execution?

<div style="margin-bottom: 8em"></div>

## What is Going on Here?

<div style="margin-bottom: 12em"></div>

## Primitive Datatypes

Java has 8 primitive datatypes:

- `byte`, `short`, `int`, `long`
- `float`, `double`
- `boolean`
- `char`

All other datatypes are objects (`class`es)

- instances created with keyword `new`

## Value Assignment of Primitive Datatypes

- Assignment of primitive datatypes *copies* values

```java
    int a = 10; // assign value of 10 to a
    int b = 5;  // assign value of 5 to b
    
    b = a;      // copy value of a (10), assign to b
    
    a = 20;     // assign value of 20
```

<div style="margin-bottom: 4em"></div>


## Passing Primitive Datatypes

- Method calls take *copies* of datatype values passed as arguments

    + Copy of value of `a` passed to `setValue(a, 0)`

    ```java
        setValue(a, 0);
    ```

    + `a` in method is a local copy; only local copy's value is changed

    ```java
        void setValue (int a, int value) {
          a = value;
        }
    ```
    + original value of `a` in `main` is unchanged!

<div style="margin-bottom: 4em"></div>

## Objects are Different

- `new Number(...)` creates a new instance of the `Number` class

- `aNum` stores a **reference** to the `Number`

```java
    Number aNum = new Number(10);
    Number bNum = new Number(5);
```

- the variable `aNum` stores the reference, *not the `Number` itself!*

<div style="margin-bottom: 8em"></div>

## Assignment

In this statement

```java
    Number aNum = new Number(10);
    Number bNum = new Number(5);

    bNum = aNum;
```

the reference stored in `aNum` is copied and stored in `bNum`

- `aNum` and `bNum` now refer to *the same* `Number` instance

<div style="margin-bottom: 12em"></div>

## Passing Objects to Methods

In this statement

```java
    setNumberValue(aNum, 0);
```

the *reference* stored in variable `aNum` is passed to `setNumberValue`

```java
    void setNumberValue (Number a, int value) {
      a.value = value;
    }
```

The statement `a.value = value` sets the value

## In Pictures

```java
void setNumberValue (Number a, int value) {
    a.value = value;
}

public static void main (String[] args) {
    Number aNum = new Number(10);
    setNumberValue(aNum, 0);
}
```

<div style="margin-bottom: 12em"></div>

## Another Puzzle

What happens?

```java
int a = 10;
int b = 10;

if (a == b) {
    System.out.println("equal");
} else {
    System.out.println("not equal");
}
```

<div style="margin-bottom: 8em"></div>

## Another Puzzle, with Objects

What happens?

```java
Number aNum = new Number(10);
Number bNum = new Number(10);

if (aNum == bNum) {
    System.out.println("equal");
} else {
    System.out.println("not equal");
}
```

<div style="margin-bottom: 8em"></div>

## Why are `aNum` and `bNum` not Equal?

<div style="margin-bottom: 18em"></div>

## How Can we Check Equality of `Number`s?

<div style="margin-bottom: 18em"></div>

## A New Method

```java
public class Number {
    public int value;
	
    public Number (int aValue) {
        value = aValue;
    }
	
    public boolean equals (Number a) {
	    return (value == a.value);
	}
}
```

## Step By Step

```java
Number aNum = new Number(10);
Number bNum = new Number(10);
boolean areEqual = aNum.equals(bNum);
```

<div style="margin-bottom: 18em"></div>

## Why References?

- Only a single copy of the object instance in computer memory
    + more efficient
- More flexible behavior
    + methods can modify multiple objects
    + make a method "return" multiple values

Drawbacks?

- Need to be careful not to modify original object instance (if this is not what is intended)

## Next Week

1. Lab 04: Exception Handling
    + handling errors gracefully
2. Object Inheritance
    + defining relationships between objects
