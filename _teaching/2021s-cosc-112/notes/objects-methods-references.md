---
title: "Objects, Methods, and References"
description: ""
layout: page
---

As we saw in class, objects in Java seem to behave quite differently than the [primitive datatypes](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html). Specifically, consider the following simple class `Number`:

```java
    public class Number {
      public int value;
    }
```

Below are two examples where the behavior of `Number`s is perhaps unexpected.

**Example 1**

```java
    Number a = new Number();
    Number b = new Number();
    a.value = 10;
    b = a;
    a.value = 20;
```

At the end of the execution, the `a.value` is 20, as one would expect. However, `b.value` is *also* 20! This is not the behavior we’d expect from `int`s. For example, if we had

```java
    int a;
    int b;
    a = 10;
    b = a;
    a = 20;
```

then at the end of the execution, `a` would have the value 20, while `b` has a value 10 (assigned to in line 4).

**Example 2**

Consider the method 

```java
    public static void setNumberValue(Number num, int value) {
      num.value = value;
    }
```

together with the function call

```java
    Number a = new Number();
    a.value = 10;
    setNumberValue(a, 20);
```
Since method calls are pass by reference (see [+Scope and Passing By Value](https://paper.dropbox.com/doc/Scope-and-Passing-By-Value-v1uHJW5Jf2EaXFH711rFX)) we might expect that calling `setNumberValue(a, 20)` would not change `a.value` after line 3 above is executed. But this is not the case! After executing the three lines above, we’d find that `a.value` is 20! What is this?


## Objects and their references

The key to understanding the behavior of the two examples above is to understand what is actually happening when we create a new `Number` (or any object) using

```java
    Number a = new Number();
```

Calling `new Number()` creates a new instance of the `Number` class, and returns a *reference* to the instance created, rather than the instance itself. Here, a **reference** is an address indicating where the actual object instance—in this case a `Number`—is stored. Thus, the variable `a` does not store the `Number` itself, but rather the address of where to find the `Number`. 

Now let’s revisit the two examples above. 

**Example 1, revisited.** 

Let’s go through the following code line-by-line:

```java
    Number a = new Number();
    Number b = new Number();
    a.value = 10;
    b = a;
    a.value = 20;
```

- Line 1. Create a new `Number` instance, and store a reference to that instance as the variable `a`
- Line 2. Create another new `Number` instance, and store the reference to that instance as the variable `b`
- Line 3. Set the `value` field of the `Number` referred to by `a` to be 10.
- Line 4. Set the reference stored at `b` to the *same reference* stored at `a`. **`b` and `a` now refer to the same instance of `Number`**. 
- Line 5. Set the `value` field of the `Number` referred to by `a` to be 20.

The unexpected behavior---namely that `b.value` is now also 20---happened as a result of Line 4. After executing line 4, `a` and `b` refer to the same `Number` instance created in line 1. 

**Exercise.** Suppose after line 5 above, we write `b.value = 10`. What is `a.value` after setting `b.value` in this way?

**Example 2, revisited.**

Consider again:

```java
    public static void setNumberValue(Number num, int value) {
      num.value = value;
    }
    ...
    Number a = new Number();
    a.value = 10;
    setNumberValue(a, 20);
```

Why is `a.value` 20 after the final line instead of 10? Again, we need to look closer at what information is being passed to the method `setNumberValue`. Going line-by-line again

- Line 5. `a` stores a reference to a new `Number` instance
- Line 6. The value of the `Number` referenced by `a` is set to `10`.
- Line 7. The call to `setNumberValue(a, 20);`
    - Line 1. The value of `a`—i.e., the *reference* is passed to `setNumberValue` by value—together with the integer value `20`. At the end of Line 1, `num` stores the reference to the `Number` initialized in line 5.
    - Line 2. The `Number` referenced by `num` has its `value` set to `20`.
    When Line 7 completes, the `Number` referenced by `a` has `a.value` set to 20 (from line 2).


## References and equality testing

The way object references are handled by Java also gives perhaps unexpected results for testing equality of objects. Consider the following code:

```java
    Number a = new Number();
    Number b = new Number();
    a.value = 10;
    b.value = 10;
    if (a == b) { System.out.println("They're equal!"); }
```

What does it do? Once again, the variables `a` and `b` store references to `Number` instances created in lines 1 and 2, respectively. Since `a`  and `b` refer to two different `Number`s, the values of these references are not the same. Thus `a != b`, even though `a.value` and `b.value` are equal.

If we wanted to test whether or not two `Number` instances (or more generally, any object instances) are equal in the sense of storing or representing the same value, then we would need to write a method for `Number` to do the check by hand. For example, we could write the following instance method for `Number`:

```java
    public class Number {
      public int value;
      public boolean equals(Number n) {
        return (value == n.value);
      }
    }
```

Now we can use the `equals` method to test if two `Number`s store the same value:

```java
    Number a = new Number();
    Number b = new Number();
    a.value = 10;
    b.value = 10;
    if (a.equals(b)) { System.out.println("They're equal!"); }
```

The code above now prints, `They're equal!`.

## Another subtlety of object references

**Example 1, revisited again.** Consider yet again the following code:

```java
    Number a = new Number();
    Number b = new Number();
    a.value = 10;
    b = a;
    a.value = 20;
```

There is another potential problem here. In lines 1 and 2, we created two `Number` instances. But after line 4, both `a` and `b` refer to the same instance—the one created in line 1. What happened to the `Number` instance created in line 2? Unfortunately, we can no longer access it! Once we overwrite the reference to the second `Number` instance in line 4, *we have no way of accessing the second* `*Number*`*!* It is effectively lost to us.

The process of creating a new object, then overwriting or otherwise losing the reference to the object is potentially problematic. Object instances created using the `new`  keyword are stored in a part of a program’s memory known as **heap memory**. Objects stored in the heap can, in principle, remain there throughout the entire execution of a program. (This behavior is in contrast to the so-called **stack memory** where local variables’ values are stored only so long as their scope is accessible.) Java employs a process called **garbage collection** to purge the heap memory of objects whose references have been lost, and are therefore inaccessible to the program. This is an incredibly helpful feature of Java!

## A cautionary tale

Consider the following class storing a pair of `Number` objects

```java
    public class PairOfNumbers {
      private Number first = new Number();
      private Number second = new Number();
    
      public PairOfNumbers (int firstVal, int secondVal) {
        first.value = firstVal;
        second.value = secondVal;   
      }
      
      public Number getFirst () {return first;}
      public Number getSecond () {return second;}
      public String toString () {return ("(" + first.value + ", " + second.value + ")");}
    }
```

**Question.** What is the result of the following code?

```java
    PairOfNumbers pair = new PairOfNumbers(1, 2);
    Number num = pair.getFirst();
    num.value = 3;
    System.out.println("pair = " + pair.toString());
```

Did we change `pair`’s private member variables!?!?

## Why object references?

Given the subtlety of dealing with object references, as opposed to dealing with objects directly, why does Java use object references? There are two main reasons:

1. Efficiency. If objects were always passed to methods by value (like primitive datatypes), we would need to make a copy of an object instance every time we wanted to pass that object to a method. Since objects can contain a lot of data, the process of copying the object could waste a lot of time and memory. 
2. Utility. It may not be obvious now, but having object instances that can persist throughout the execution of a program is incredibly useful. We will see many examples of this throughout the rest of the course.

