---
title: "Lecture 08: Objects and Memory"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 08: Objects and Memory

## Overview

1. Finishing and Testing `Fraction`
2. Objects and Memory

## Last Time

We started writing a `Fraction` class to represent fractional values

```java
public class Fraction {
    private long num; // numerator
    private long den; // denominator

    public Fraction (long numerator, long denominator) {
	num = numerator;
	den = denominator;
    }
	
    // Fraction g, Fraction h then g.add(h) returns "g + h"
    public Fraction add (Fraction f) {

	// (a / b) + (c / d) = (a * d + b * c) / b * d
	long newNum = num * f.den + den * f.num;
	long newDen = den * f.den;

	return new Fraction(newNum, newDen);
    }

    // compute the greatest common divisor of two longs
    public static long gcd (long a, long b) {
	if (b == 0) {
	    return a;
	}

	return gcd(b, a % b);
    }
}
```

## Today

Improving and testing the implementation

1. Storing fractions as reduced fractions
2. Converting to other formats:
    + `double`
	+ `String`
3. Comparing with `BadArithmetic` from last week

## Conclusion

We can design objects that do better arithmetic!

# Objects and Memory

## A Question

What does the following code do?

```java
    int a = 10;
    int b = 5;
    
    b = a;
    
    a = 20;
```

What are the values of `a` and `b` at the end of the execution?

<div style="margin-bottom: 8em"></div>


## Second Question

Consider the method

```java
    void setValue (int a, int value) {
      a = value;
    }
```

What is the result of

```java
    a = 20;
    setValue(a, 0);
```

What is the value of `a` at the end of the execution?

<div style="margin-bottom: 8em"></div>


## Again, but with objects

Consider the following simple `class`

```java
class Number {
    public int value;

    Number (int val) {
	value = val;
    }
}
```


## First Question, again

What does the following code do?

```java
    Number aNum = new Number(10);
    Number bNum = new Number(5);
    
    bNum = aNum;
    aNum.value = 20;
```

What are the values of `a` and `b` at the end of the execution?

<div style="margin-bottom: 8em"></div>


## Second Question, again

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


<!-- ## What is Going on Here? -->

<!-- <div style="margin-bottom: 12em"></div> -->

<!-- ## Primitive Datatypes -->

<!-- Java has 8 primitive datatypes: -->

<!-- - `byte`, `short`, `int`, `long` -->
<!-- - `float`, `double` -->
<!-- - `boolean` -->
<!-- - `char` -->

<!-- All other datatypes are objects (`class`es) -->

<!-- - instances created with keyword `new` -->

<!-- ## Value Assignment of Primitive Datatypes -->

<!-- - Assignment of primitive datatypes *copies* values -->

<!-- ```java -->
<!--     int a = 10; // assign value of 10 to a -->
<!--     int b = 5;  // assign value of 5 to b -->
    
<!--     b = a;      // copy value of a (10), assign to b -->
    
<!--     a = 20;     // assign value of 20 -->
<!-- ``` -->

<!-- <div style="margin-bottom: 4em"></div> -->


<!-- ## Passing Primitive Datatypes -->

<!-- - Method calls take *copies* of datatype values passed as arguments -->

<!--     + Copy of value of `a` passed to `setValue(a, 0)` -->

<!--     ```java -->
<!--         setValue(a, 0); -->
<!--     ``` -->

<!--     + `a` in method is a local copy; only local copy's value is changed -->

<!--     ```java -->
<!--         void setValue (int a, int value) { -->
<!--           a = value; -->
<!--         } -->
<!--     ``` -->
<!--     + original value of `a` in `main` is unchanged! -->

<!-- <div style="margin-bottom: 4em"></div> -->

<!-- ## Objects are Different -->

<!-- - `new Number(...)` creates a new instance of the `Number` class -->

<!-- - `aNum` stores a **reference** to the `Number` -->

<!-- ```java -->
<!--     Number aNum = new Number(10); -->
<!--     Number bNum = new Number(5); -->
<!-- ``` -->

<!-- - the variable `aNum` stores the reference, *not the `Number` itself!* -->

<!-- <div style="margin-bottom: 8em"></div> -->

<!-- ## Assignment -->

<!-- In this statement -->

<!-- ```java -->
<!--     Number aNum = new Number(10); -->
<!--     Number bNum = new Number(5); -->

<!--     bNum = aNum; -->
<!-- ``` -->

<!-- the reference stored in `aNum` is copied and stored in `bNum` -->

<!-- - `aNum` and `bNum` now refer to *the same* `Number` instance -->

<!-- <div style="margin-bottom: 12em"></div> -->

<!-- ## Passing Objects to Methods -->

<!-- In this statement -->

<!-- ```java -->
<!--     setNumberValue(aNum, 0); -->
<!-- ``` -->

<!-- the *reference* stored in variable `aNum` is passed to `setNumberValue` -->

<!-- ```java -->
<!--     void setNumberValue (Number a, int value) { -->
<!--       a.value = value; -->
<!--     } -->
<!-- ``` -->

<!-- The statement `a.value = value` sets the value  -->

<!-- ## In Pictures -->

<!-- ```java -->
<!-- Number aNum = new Number(10); -->
<!-- setNumberValue(aNum, 0); -->

<!-- ... -->

<!-- void setNumberValue (Number a, int value) { -->
<!--     a.value = value; -->
<!-- } -->
<!-- ``` -->

<!-- <div style="margin-bottom: 12em"></div> -->

<!-- ## Why References? -->

<!-- - Only a single copy of the object instance in computer memory -->
<!--     + more efficient -->
<!-- - More flexible behavior -->
<!--     + methods can modify multiple objects -->
<!--     + make a method "return" multiple values -->

<!-- Drawbacks? -->

<!-- - Need to be careful not to modify original object instance (if this is not what is intended) -->




