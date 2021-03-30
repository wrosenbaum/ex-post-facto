---
title: "Handling Exceptions"
description: "an introduction to exception handling in Java"
layout: page
---

You should download this code and follow along!

- [`BadFraction.java`](/assets/java/handling-exceptions/BadFraction.java)
- [`DivisionByZeroException.java`](/assets/java/handling-exceptions/DivisionByZeroException.java)
- [`Fraction.java`](/assets/java/handling-exceptions/Fraction.java)
- [`FractionTester.java`](/assets/java/handling-exceptions/FractionTester.java)

Consider the following `Fraction` class (called `BadFraction` in the code above):

```java
    public class Fraction {
        
        private int num;
        private int den;
    
        public static int gcd(int n, int m) {
            if (m == 0) return n;
            return gcd(m, n % m);
        }
    
        Fraction () {
            num = 0;
            den = 1;
        }
    
        Fraction (int num, int den) {        
            this.num = num;
            this.den = den;
            reduce();
        }
    
        public void reduce () {
            int gcd = gcd(num, den);
    
            if (gcd != 0) {
                num /= gcd;
                den /= gcd;
            }
        }
    
        public int getNum () {return num;}
        public int getDen () {return den;}
    
        public Fraction plus (Fraction f) {
            return new Fraction(num * f.den + den * f.num, den * f.den);
        }
    
        public Fraction minus (Fraction f) {
            return this.plus(f.times(-1));
        }
    
        public Fraction reciprocal () {
            return new Fraction(den, num);
        }
    
        public Fraction times (Fraction f) {
            return new Fraction(num * f.num, den * f.den);
        }
    
        public Fraction times (int n) {
            return new Fraction(num * n, den);
        }
    
        public Fraction dividedBy (Fraction f) {
            return this.times(f.reciprocal());
        }
    
        public boolean equals (Fraction f) {
            return (num * f.den == f.num * den);
        }
    
        public String toString () {
            return (num + " / " + den);
        }
    }
```


To test our `Fraction` class, we can make some `Fraction`s:

```java
    Fraction a = new Fraction(1, 2);
    Fraction b = new Fraction(1, 3);
    Fraction c = new Fraction(1, 6);
```

We can do some arithmetic and get the expected results:

```java
    System.out.println("a + b = " + a.plus(b).toString());
    System.out.println("a - b = " + a.minus(b).toString());
    System.out.println("a + b - c = " + a.plus(b).minus(c).toString());
    System.out.println("c / (a + b) = " + c.dividedBy(a.plus(b)));
```

produces the following output

```text
    a + b = 5 / 6
    a - b = 1 / 6
    a + b - c = 2 / 3
    c / (a + b) = 1 / 5
```

as we’d expect. But something strange happens when we run the following code:

```java
    Fraction oops = a.minus(b).minus(c).dividedBy(a.minus(b).minus(c));
    if (oops.equals(a)) {
      System.out.println("Huh... (a - b - c) / (a - b - c) == a");
      System.out.println("That is strange, because a = " + a.toString());
    }
```

The resulting output is:

```text
    Huh... (a - b - c) / (a - b - c) == a
    That is strange, because a = 1 / 2
```

Why is this so strange? Well, because

$$\frac{a - b - c}{a - b - c} = \frac{1}{2} \Rightarrow 2 (a - b - c) = (a - b - c) \Rightarrow 2 = 1.$$

So it seems our program implies that $$2 = 1$$?!?! Well, no. This problem is revealed when we output the value of the fraction  $$(a - b - c) / (a - b - c)$$:

```java
    System.out.println("(a - b - c) / (a - b - c) = " + oops.toString());
```

prints 

```text
    (a - b - c) / (a - b - c) = 0 / 0
```

The real issue is that we divided by zero, and our program just lets us. In what follows, we will show how to use exception handling to fix this problem.

## Exceptions

Exceptions are a way that we can communicate that our program encountered some sort of error or unexpected input. You’ve probably encountered exceptions in your code resulting from mistakes in your code. For example if you have a program with

```java
    int a = 1;
    int b = 0; 
    int c = a / b;
```

your program will compile, but running it will result in:

```text
    Exception in thread "main" java.lang.ArithmeticException: divide by zero
            at DivideByZero.main(DivideByZero.java:6)
```

or something similar. Here, we will describe how to make and use exceptions so that we can deal with these errors in a graceful manner without crashing our programs.

##### Defining new exceptions

Java has many built-in exceptions (see the [Java API documentation](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Exception.html)---note that many of the listed direct subclasses have subclasses themselves!). If none of the exceptions listed there seem appropriate we can define a new exception by defining a subclass of the `Exception` class. Typically, all we need to do are to define two constructors for our exception that take, respectively, no arguments and a `String` as an argument. 

Here is the complete code for a `DivisionByZeroException` that we will use for our `Fraction` class:

```java
    public class DivisionByZeroException extends Exception {
      public DivisionByZeroException () {
        super();
      }
    
      public DivisionByZeroException (String message) {
        super(message);
      }
    }
```

That’s all we have to do to make a new exception class!

##### Throwing exceptions

When we encounter the exception that our code should deal with, we **throw** the exception, using the keyword `throw`. There are a few places in `Fraction` where a division by zero error could occur. The first place is in the constructor for Fraction itself. There is currently nothing stopping a programmer from writing `new Fraction(1, 0)` and carrying on. 

To avoid this scenario, we throw a division by zero exception by adding the following lines to the `Fraction` constructor:

```java
    if (den == 0) {
      throw new DivisionByZeroException();
    }
```

Whenever a method throws an exception, we must add the exception(s) that could be thrown to the method’s declaration. We do so by adding `throws …` to the method declaration after the parameter list, where `…` is the list of exceptions the method could throw. For example, our new constructor becomes:

```java
    Fraction (int num, int den) throws DivisionByZeroException {
      if (den == 0) {
        throw new DivisionByZeroException();
      }
            
      this.num = num;
      this.den = den;
      reduce();
    }
```

To make our exception more informative, we can add a message saying what happened by passing a `String` to the exception’s constructor, for example

```java
    throw new DivisionByZeroException("Tried to create Fraction with numerator " + num + " and denominator " + den);
```

Unfortunately, our work isn’t done yet. If we compile the new `Fraction` class, we’ll get errors such as:

```text
    Fraction.java:30: error: unreported exception DivisionByZeroException; must be caught or declared to be thrown
            return new Fraction(num * f.den + den * f.num, den * f.den);
```

Line 30 is in our `plus` method. The problem is that the method calls the constructor for `Fraction`, which could throw an exception. Thus, `plus` needs to either “catch” the exception (and figure out how to handle it), or throw the exception back to whatever method called `plus` in the first place. If we don’t know how to handle the exception within `plus`, we should opt for the latter option. To this end, we can just change the declaration of `plus` to indicate that it can throw a `DivisionByZeroException` as well:

```java
    public Fraction plus throws DivisionByZeroException (Fraction f) {
      return new Fraction(num * f.den + den * f.num, den * f.den);
    }
```

Then we can do the same with all methods that call the `Fraction` constructor and `plus` so that the `Fraction.java` compiles.

Now let’s try to test the `Fraction` class with the following `FractionTester` program:

```java
    public class FractionTester {
      public static void main (String[] args) throws DivisionByZeroException {
        System.out.println("Making some fractions...");
        Fraction a = new Fraction(1, 2);
        Fraction b = new Fraction(1, 3);
        Fraction c = new Fraction(1, 6);
        System.out.println("a = " + a.toString() +
                           ", b = " + b.toString() +
                           ", c = " + c.toString());
    
        System.out.println("Doing some arithmetic...");
    
        System.out.println("a + b = " + a.plus(b).toString());
        System.out.println("a - b = " + a.minus(b).toString());
        System.out.println("a + b - c = " + a.plus(b).minus(c).toString());
        System.out.println("c / (a + b) = " + c.dividedBy(a.plus(b)));
    
        System.out.println("Computing (a - b - c) / (a - b - c)...");
        Fraction oops = a.minus(b).minus(c).dividedBy(a.minus(b).minus(c));
    
        if (oops.equals(a)) {
          System.out.println("Huh... (a - b - c) / (a - b - c) == a");
          System.out.println("That is strange, because a = " + a.toString());
        }
    
        System.out.println("(a - b - c) / (a - b - c) = " + oops.toString());
      }
    }
```

Unfortunately, now we can’t compile `FractionTester`  because we get a bunch of errors about unreported exceptions:

```text
    FractionTester.java:4: error: unreported exception DivisionByZeroException; must be caught or declared to be thrown
            Fraction a = new Fraction(1, 2);
```

We can fix all of these errors by declaring that our main method `throws DivisionByZeroException`s. It is bad form to have a main method throw exceptions (the whole point of having the exceptions is so that we can handle them), but here is what happens:

```text
    Making some fractions...
    a = 1 / 2, b = 1 / 3, c = 1 / 6
    Doing some arithmetic...
    a + b = 5 / 6
    a - b = 1 / 6
    a + b - c = 2 / 3
    c / (a + b) = 1 / 5
    Computing (a - b - c) / (a - b - c)...
    Exception in thread "main" DivisionByZeroException: Tried to create Fraction with numerator 1 and denominator 0
            at Fraction.<init>(Fraction.java:14)
            at Fraction.reciprocal(Fraction.java:43)
            at Fraction.dividedBy(Fraction.java:55)
            at FractionTester.main(FractionTester.java:19)
```

This is progress in the sense that the program at least alerts us that there is a problem with division by zero (unlike before). But this is still bad in the sense that our program is interrupted before we can try to fix the issue. 

##### Catching exceptions

In the programs above, exceptions are thrown, but we never attempt to fix or isolate the problem. As a result, when an exception is encountered, the program terminates (albeit with a helpful message indicating where the problem occurred). 

If we know how to handle a problem indicated by a thrown exception, we can “catch” the exception by using  **try-catch blocks**:

```java
    try {
      // some code that could throw SomeException
    }
    catch (SomeException e) {
      // code to run if SomeException is thrown
    }
```

When the code in the `try` block throws `SomeException`, execution of the `try` block is stopped, and the code in the `catch` block is executed. Since the code in the catch block deals with the exception, the method containing the try-catch blocks doesn’t need to throw `SomeException` itself.

**Note.** We can handle multiple exceptions by having multiple catch blocks:

```java
    try {
      // some code that could throw SomeException or AnotherException
    }
    catch (SomeException e) {
      // code to run if SomeException is thrown
    }
    catch (AnotherException e) {
      // code to run if AnotherException is thrown
    }
```

Now we can catch the exceptions thrown by `Fraction` in our `FractionTester` program:

```java
    public class FractionTester {
        public static void main (String[] args) {
            System.out.println("Making some fractions...");
    
            Fraction a = new Fraction();
            Fraction b = new Fraction();
            Fraction c = new Fraction();
    
            try {
                a = new Fraction(1, 2);
                b = new Fraction(1, 3);
                c = new Fraction(1, 6);
    
                System.out.println("a = " + a.toString() +
                                   ", b = " + b.toString() +
                                   ", c = " + c.toString());
            }
            catch (DivisionByZeroException e){
                // Since I defined the Fractions above not to be zero, I'm
                // not even going to write any code here. I *know* a
                // DivisionByZeroException won't occur
            }
            
            System.out.println("Doing some basic arithmetic...");
    
            try {
    
                System.out.println("a + b = " + a.plus(b).toString());
                System.out.println("a - b = " + a.minus(b).toString());
                System.out.println("a + b - c = " + a.plus(b).minus(c).toString());
                System.out.println("c / (a + b) = " + c.dividedBy(a.plus(b)));
                System.out.println("1 / (a - b - c) = " + a.minus(b).minus(c).reciprocal());
            }                
            catch (DivisionByZeroException e) {
                System.out.println("Oops. We divided by zero! " + e);
            }
        }
    }
```

Notice that we had to declare `Fraction`s `a`, `b`, and `c` outside of the first `try` block, where we called their zero-argument constructors (which do not throw exceptions). 

Running the program gives the following output:

```text
    Making some fractions...
    a = 1 / 2, b = 1 / 3, c = 1 / 6
    Doing some basic arithmetic...
    a + b = 5 / 6
    a - b = 1 / 6
    a + b - c = 2 / 3
    c / (a + b) = 1 / 5
    Oops. We divided by zero! DivisionByZeroException: Tried to create Fraction with numerator 1 and denominator 0
```

Things are looking good, but our program can be improved. First of all, since the `Fraction` constructor throws an exception, we can assume that any `Fraction` instance does not have a zero denominator after construction. Since `den` is private and there is no method to set `den` directly, we can safely assume that adding, subtracting, and multiplying existing `Fraction` instances will not create `Fraction`s with zero denominators. Thus we can catch `DivisionByZeroExceptions` directly in these methods. For example:

```java
    public Fraction plus (Fraction f) {
        try {
          return new Fraction(num * f.den + den * f.num, den * f.den);
        }
        catch (DivisionByZeroException e) {
          return null;
        }
    }
```

Again, we can be certain that the exception we’re “catching” never occurs, so we don’t need to put any code here. We can modify `minus`  and `times` similarly. The methods `reciprocal` and `dividedBy`, however, might create zero denominators (if they are called with an argument whose value is `0`), so they should still throw exceptions.

Once we’ve made these modifications to `Fraction`, we don’t need to call `plus`, `minus`, or `times` in `FractionTester` inside a `try` block. Now we can separate the “dangerous” statements into their own try-catch blocks so that we can make more specific behavior:

```java
    try {
    
        System.out.println("c / (a + b) = " + c.dividedBy(a.plus(b)));
    }
    
    catch (DivisionByZeroException e) {
    
        System.out.println("Oops. You tried to divide " + c.toString() + " by " + a.plus(b).toString() +  ". " + e);
    }
    
    try {
        System.out.println("1 / (a - b - c) = " + a.minus(b).minus(c).reciprocal());
    }
    catch (DivisionByZeroException e) {
        System.out.println("Oops. You tried to take the reciprocal of " + a.minus(b).minus(c).toString() +  ". " + e);
    }
```

The resulting modification outputs:

```text
    Making some fractions...
    a = 1 / 2, b = 1 / 3, c = 1 / 6
    Doing some basic arithmetic...
    a + b = 5 / 6
    a - b = 1 / 6
    a + b - c = 2 / 3
    c / (a + b) = 1 / 5
    Oops. You tried to take the reciprocal of 0 / 1 DivisionByZeroException: Tried to create Fraction with numerator 1 and denominator 0
```

Finally, to see that this exception handling actually buys us something, see what happens if we move the first few arithmetic operations to *after* the try-catch blocks:

```java
    System.out.println("Doing some basic arithmetic...");
    System.out.println("a + b = " + a.plus(b).toString());
    try {
        System.out.println("c / (a + b) = " + c.dividedBy(a.plus(b)));
    }
    catch (DivisionByZeroException e) {   
        System.out.println("Oops. You tried to divide " + c.toString() + " by " + a.plus(b).toString() +  ". " + e);
    }
    
    try {
        System.out.println("1 / (a - b - c) = " + a.minus(b).minus(c).reciprocal());
    }
    catch (DivisionByZeroException e) {
        System.out.println("Oops. You tried to take the reciprocal of " + a.minus(b).minus(c).toString() +  ". " + e);
    }
    System.out.println("a - b = " + a.minus(b).toString());
    System.out.println("a + b - c = " + a.plus(b).minus(c).toString());
```

Now this code gives the following output:

```text
    Making some fractions...
    a = 1 / 2, b = 1 / 3, c = 1 / 6
    Doing some basic arithmetic...
    a + b = 5 / 6
    c / (a + b) = 1 / 5
    Oops. You tried to take the reciprocal of 0 / 1. DivisionByZeroException: Tried to create Fraction with numerator 1 and denominator 0
    a - b = 1 / 6
    a + b - c = 2 / 3
```

This is progress! We did some arithmetic that previously either crashed our program, or (worse!) gave a subtle error without us having any clue why. Now, we can detect mistakes in the program’s input/execution and deal with them in such a way that the program continues to function despite the exception.
