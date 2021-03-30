---
title: "Overloading and Static Methods"
description: "notes on overloading methods and static methods"
layout: page
---

## Background

In class, we defined a simple `Fraction` class to store and manipulate fractional values. Specifically, a `Fraction`  represents a fraction as a numerator and denominator. We implemented methods for adding and multiplying fractions. Here is the code we wrote:

```java
    public class Fraction {
    
        private int num;
        private int den;
    
        public Fraction (int numerator, int denominator) {
            num = numerator;
            den = denominator;
            reduce();
        }
        
        // public getters for numerator and denominator
        public int getNum() {return num;}
        public int getDen() {return den;}
    
        // get a double approximating the value of the Fraction
        public double getDouble() { return (double) num / den;}
        
        // get a String representing the fraction
        public String getString () {return (num + " / " + den);}
        
    
        // make a new Fraction that is the sum of this and f, and return
        // the result
        public Fraction plus (Fraction f) {
            // sum numerator
            int sumNum = num * f.getDen() + f.getNum() * den;
    
            // sum denominator
            int sumDen = den * f.getDen();
    
            return new Fraction(sumNum, sumDen);
        }
    
        // make a new Fraction that is the product of this and f, and
        // return the result
        public Fraction times (Fraction f) {
            int prodNum = num * f.getNum();
            int prodDen = den * f.getDen();
    
            return new Fraction(prodNum, prodDen);
    
        }
    
        // compute the greatest common divisor of two ints
        // this is utter magic
        private int gcd (int n, int m) {
            if (m == 0) return n;
            return gcd(m, n % m);
        }
    
        // reduce Fraction to lowest terms by dividing numerator and
        // denominator by the greatest common divisor
        private void reduce () {
            
            int div = gcd(num, den);
            
            num /= div;
            den /= div;
        }
    }
```

In writing the class above, we made a few design decisions that might make the class a little annoying to work with. For example:

1. To construct a new `Fraction`, you need to pass in a value for the numerator and a denominator. Perhaps the user would prefer to pass in a `Fraction` instance, or just an `int`, or even pass no arguments so the `Fraction`  takes some standard default value.
2. When adding two `Fraction`s, we have to pass in a `Fraction` object as a parameter to the `plus` method. What if we want to add an integer? Or a fraction with a known numerator and denominator, but we don’t want to create a new `Fraction` object to pass to the `plus` method?

In this note, we will address these questions, and show how to extend the `Fraction` class in such a way that will make it more flexible for the user. Our starting point will be the `FractionBasic` class (the same as our
`Fraction` class above, just with a different name to avoid confusion).

- [Download `FractionBasic.java`](/assets/java/overloading-and-static-methods/FractionBasic.java)

## Overloading Methods 

In Java, a **method declaration** consists of:

1. modifiers (e.g., `public`, `private`, `static`)
2. a return type
3. a name
4. a parameter list
5. an exception list (we haven’t seen these yet, but we’ll discuss later)
6. the method body

Together, these features specify the behavior of a method. However, sometimes we’d like to have two or more methods that perform similar operations, but require different arguments/parameters. 

For example, in our `Fraction` class, we might want to be able to add a `Fraction` to another `Fraction` *or* add an `int` to a `Fraction`. We could do this by defining different methods that both “add”, but require different arguments as follows:

```java
    public Fraction plusFraction (Fraction f) { ... }
    public Fraction plusInt (int n) { ... }
```

But this approach would be cumbersome to use if we need a method with a different name every time we want to provide another way of adding `Fraction` s. For example, we might end up having a method with name like:

```java
    public Fraction plusPairOfIntsRepresentingAFraction (int numerator, int denominator) {...}
```
 
While the name is descriptive, it would be a pain to type out every time we want to add! Instead, since all of these methods perform addition, it would be great to give them the same name. And we can!
 
The **signature** of a method consists of (1) the method name, and (2) the list of *types* represented in the parameter list. Java determines which code to execute during a method call based on the call’s signature, and not just from the method’s name. Thus, we can give multiple methods the same name, so long as they differ in their argument type list. **Method overloading** is the process of writing multiple methods with the same name, but different signatures (i.e., different lists of parameter types). Most of the time, overloading is not necessary (except in the case of constructors, where we are not free to choose the name of the method), but overloading can lead to simpler, more readable and usable code. 
 
##### Overloading constructors

As mentioned above, we *must* use method overloading if we want to define constructors for a class that take different types of arguments (because a constructor is required to have the same name as its class). For example, the constructor for our `Fraction` class above takes two `int` s as its parameters: the numerator and denominator of the fraction. However, we might want to have constructors that initialize a `Fraction` with a default value, or an integer value. Thus, we could define, for example:

```java
    class Fraction {
      private int num;
      private int den;
    
      // initialize with a default value of 0
      public Fraction () {
        num = 0;
        den = 1;   
      } 
    
      // initialize with a value of numerator / 1
      public Fraction (int numerator) {
        num = numerator;
        den = 1;
      }
    
      // initialize with a value of numerator / denominator
      public Fraction (int numerator, int denominator) {
        num = numerator;
        den = denominator;
      }
      ...
    }
```

**Exercise.** Define a constructor for `Fraction` that takes another `Fraction f` as a parameter, and sets the value of the new `Fraction` to the same value stored in `f`.

##### Overloaded addition

Similar to the constructor overloading shown above, we can overload the arithmetic operations such as `plus`. Again, we might wish to (1) add a `Fraction`, (2) add an `int`, or (3) add a pair of `int`s representing the numerator and denominator of a fraction. Thus we could define 

```java
    // make a new Fraction that is the sum of this and f, and return the result
    public Fraction plus (Fraction f) {
        int sumNum = num * f.getDen() + f.getNum() * den;
        int sumDen = den * f.getDen();
        return new Fraction(sumNum, sumDen);
    }
    
    // make a new Fraction that is the sum of this and int value, and return the result
    public Fraction plus (int value) {
      int sumNum = num + value * den;
      int sumDen = den;
      return new Fraction(sumNum, sumDen);
    }
    
    // make a new Fraction that is the sum of this and (numerator / denominator), 
    // and return the result
    public Fraction plus (int numerator, int denominator) {
      int sumNum = num * denominator + numerator * den;
      int sumDen = den * denominator;
      return new Fraction(sumNum, sumDen);
    }
```
 
Notice that the code in all of the three methods above is very similar. This is potentially problematic for two reasons: (1) it means we’ve wasted our time writing almost the same function 3 times, and (2) if we need to change how addition is implemented, then we may need to change the code in 3 places rather than 1. This mean modifying and debugging the code will be three times as much work! A better practice would be to write a single method—the most “general” method—and simply have the other versions of `plus` call that method. For us, the third version of `plus` is the most general, and a better implementation of the code above would be:

```java
    // make a new Fraction that is the sum of this and f, and return the result
    public Fraction plus (Fraction f) {
        return plus(f.getNum(), f.getDen());
    }
    
    // make a new Fraction that is the sum of this and int value, and return the result
    public Fraction plus (int value) {
      return plus(value, 1);
    }
    
    // make a new Fraction that is the sum of this and (numerator / denominator), 
    // and return the result
    public Fraction plus (int numerator, int denominator) {
      int sumNum = num * denominator + numerator * den;
      int sumDen = den * denominator;
      return new Fraction(sumNum, sumDen);
    }
```
  
The third implementation of `plus` stays the same, but the first two become trivial.

##### Explicit and implicit parameters; keyword `this`

In the declaration of an instance method, the parameters appearing in the declaration are known as **explicit parameters**. Additionally, an **implicit parameter** gets passed to the method that consists of a reference to the object on which the method is invoked. The implicit parameter can be accessed using the keyword `this`. Thus, you can use `this`  to refer to member variables and methods of an instance. For example, consider the `plus` method defined above:

```java
    public Fraction plus (Fraction f) {
        return plus(f.getNum(), f.getDen());
    }
```

The method calls the `plus(int ..., int ...)` function for the same instance on which `plus(f)` is called. We could equivalently have written 

```java
    public Fraction plus (Fraction f) {
        return this.plus(f.getNum(), f.getDen());
    }
```

The two versions of `plus (Fraction f)` are completely equivalent. The only difference is that in the second version, using `this` emphasizes to the person reading your code that you are calling the `plus(int, int)` method on the same `Fraction` instance on which `plus(Fraction)` was called.

Within `plus (int, int)`, we can also us `this` to refer to the values of `num` and `den`, so that 

```java
    public Fraction plus (int numerator, int denominator) {
      int sumNum = num * denominator + numerator * den;
      int sumDen = den * denominator;
      return new Fraction(sumNum, sumDen);
    }
```

becomes

```java
    public Fraction plus (int numerator, int denominator) {
      int sumNum = this.num * denominator + numerator * this.den;
      int sumDen = this.den * denominator;
      return new Fraction(sumNum, sumDen);
    }
```

Again, there is no difference between these two method definitions as far as the Java compiler is concerned. The difference is merely stylistic. The latter is arguable more transparent in that it reminds the reader/user/programmer that `num` and `den` are instance variables for this `Fraction` instance. On the other hand, adding the keyword `this` everywhere makes for more text on the screen, and might not actually make things clearer for the reader/coder. 

There are two usage cases where `this` is frequently—--if not universally—--used. The first case is when you want the name of an argument passed to a method to be the same as the a member variable for the class. For example, our `Fraction` class has two instance variables `num` and `den` storing the numerator and denominator (respectively) of the fraction. We can call the parameters of  `plus(int, int)` `num` and `den` in order to be consistent with the usage of these names: they represent the numerator and denominator of the fraction being added to `this`. We can then reference `this`'s member variables using `this.num` and `this.den`. The resulting method would be 

```java
    public Fraction plus (int num, int den) {
      int sumNum = this.num * den + num * this.den;
      int sumDen = this.den * den;
      return new Fraction(sumNum, sumDen);
    }
```

Again, there is no difference in the behavior of this version of `plus` and the previous one. The only advantage is that by re-using the variable names `num` and `den`, we ensure naming consistency: variables with the same names have the same semantic interpretation (i.e., `num` always refers to a numerator, `den` always refers to a denominator, and we don’t need other variable names to denote numerators and denominators). In the example above, we *must* use `this.num` and `this.den` to refer to the member variables of the `Fraction`, as `num` and `den` are now the local variables passed to the method.

It is especially common to use the `this.memberVariable` pattern in constructors. For example, we could have written our third constructor for `Fraction` as

```java
    public Fraction (int num, int den) {
      this.num = num;
      this.den = den;
    }
```

The second usage case where it is common to see `this` used is to call a constructor with a different signature from within a constructor. You can call a constructor for an instance using `this(<arguments>)`. Using this pattern, we can often avoid writing redundant code in different constructors. For example, we could define the three constructors for our `Fraction` class as follows:

```java
    public Fraction () {
      // call the constructor Fraction(int, int) with args 0 and 1
      this(0, 1) 
    } 
    
    public Fraction (int num) {
      // call the constructor Fraction(int, int) with args num and 1
      this(num, 1);
    }
    
    // initialize with a value of num / den
    public Fraction (int num, int den) {
      this.num = num;
      this.den = den;
    }
```

The constructors defined above compartmentalize the code so that all of the important stuff happens only in the third constructor. Thus, the constructor definitions as above are potentially easier to understand than the original version.

## Static Fields and Methods

So far, we have focused our attention instance variables and methods. These are variables and methods that are specific to each *instance* of a class. For example, each instance of our `Fraction` class stores its own `num` and `den` variables. When we add two fractions using `f.plus(g)`, we are calling the `plus` method *for* `f` with argument `g`; for a `Fraction`  `h`  not equal to `f` , calling `h.plus(g)` will give a different result from `f.plus(g)`. 

Often it is useful to have **class variables** and **class methods**. These are variables and methods defined for the entire class rather than individual instances. For example, we might want to define a class method `add` for our `Fraction` that adds two `Fraction`s. Such a class would be called as follows:

```java
    Fraction.add(f, g) // return a new Fraction representing the sum of f and g
```

Class methods and variables are declared using the `static` keyword. Thus, a method providing the functionality above could be declared as follows:

```java
    public static Fraction add (Fraction f, Fraction g) {
      int newNum = f.getNum() * g.getDen() + f.getDen() * g.getNum();
      int newDen = f.getDen() * g.getDen();
      return new Fraction(newDen, newNum);
    }
```

This code looks suspiciously similar to our various versions of `plus`. Since we’ve already implemented `plus`, it would be best simply to call the appropriate method! Here is a better implementation of `add`:

```java
    public static Fraction add (Fraction f, Fraction g) {
      return f.plus(g);
    }
```

Note that we could also have defined `public static Fraction add (…)` *first* and then defined the various versions of `plus` in terms of `add`. Again, the best practice is to implement only one of these methods directly and have all of the other related methods simply call the “general” method.

We might also want some class variables for `Fraction`. Specifically, it may be useful to have constants representing commonly used values, such as zero and one. Since these values are constant (i.e., they should not be modified in any way), we use the additional modifier `final` to their declaration.

```java
    public static final Fraction ZERO = new Fraction(0, 1);
    public static final Fraction ONE = new Fraction(1, 1);
```

We can now access these constant values using  `Fraction.ZERO` and `Fraction.ONE`.

## Exercises

1. Write yet another constructor for `Fraction` that takes a `Fraction f` as an argument and initializes the fraction to have the same value as `f`. Your constructor should call one of the other constructors above instead of assigning values directly.
2. Suppose you wanted to write yet another version of `plus` that adds the reciprocal of an integer value `n` to `this`. What would be the problem with further overloading `plus` as follows (in addition to the versions of `plus` defined above)?
    public Fraction plus (int reciprocal) { return this.plus(1, reciprocal); }
3. Define a public instance method `plusEquals` that takes a `Fraction` as an argument and adds the value of the argument to the instance calling `plusEquals`.
    - Can your method call one of the versions of `plus` already defined?
    - Could you define an equivalent class method for `plusEquals`?
	
## Vocabulary

- method declaration
- method signature
- method overloading
- explicit parameter
- implicit parameter
- `this` keyword
- class variable
- class method
- `static` keyword
- `final` keyword

## Solutions

See the code below for the implementations suggested in the exercises above.

2. The problem is that we’ve already defined `public Fraction plus (int value)`, which adds the integer `value` to the fraction. This function has the same signature as our `public Fraction plus (int reciprocal)`: the signature only consists of the method name and the argument *type* list. Since both methods called `plus` take a single `int` as their argument, the compiler would not be able to distinguish which method, say, `f.plus(2)` refers to. Notice that if you try to define two methods with the same signature, you will get an error when you compile:
    Fraction.java:58: error: method plus(int) is already defined in class Fraction
            public Fraction plus (int reciprocal) {
                            ^
    1 error

3a. In principle, you could use `plus` as follows:

```java
    public void plusEquals(Fraction f) {
      Fraction g = this.plus(f);
      this.num = g.getNum();
      this.den = g.getDen();
    }
```

However, this approach is wasteful, as it creates a new `Fraction` (namely `g`), that is immediately forgotten. A better approach (and the one implemented in `Fraction.java` below) is to modify `this.num` and `this.den` directly without creating a new `Fraction` object. 

Once `plusEquals` is implemented, however, we could go back and define `plus` in terms of `plusEquals`. For example, by writing

```java
    public void plus (int num, int den) {
      // make a new Fraction with value num / den
      Fraction f = new Fraction(num, den);
    
      // add value of this to f
      f.plusEquals(this);
    
      return f;
    }
```

Since the implementation of `plus` above uses `plusEquals`, we maintain the property that all addition operations are handled by a single function, `plusEquals`. Again, this feature is helpful for debugging, maintaining, and modifying code. Also there aren’t any “extra” `Fraction` s created as `plus` is supposed to return a new `Fraction` anyway.

3b. Since `plusEquals` modifies the value of an *instance* of `Fraction`, it would not make sense to have a class method with the same functionality. 


- [Download `Fraction.java`](/assets/java/overloading-and-static-methods/Fraction.java)
