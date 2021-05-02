---
title: "Interfaces and Comparing Objects"
description: "comparing objects with the `Comparable` interface"
layout: page
---

In this note, we explore interfaces in Java. An **interface** is formal way of specifying a set of required functionalities that a class provides. If a class supports the requirements defined in an interface, the class is said to **implement** the interface. An interface specifies *what* a class must do to implement the interface, but it does not specify *how* the class provides the required functionality. 

An interface can be viewed as a contract of conditions that must be met in order to implement the interface. One valuable aspect of having such a contract is that a programmer can code a solution to some problem using the functionality described in the interface, and that functionality will then be available to every class implementing the interface. In what follows, we will show how implementing two interfaces, `Comparable` and `Comparator`, allow us to use existing algorithms defined in Java to sort user-defined classes.

Here is the code for the examples below so you can follow along!

- [`FirstNameComparaator.java`](/assets/java/interfaces/FirstNameComparator.java)
- [`Person.java`](/assets/java/interfaces/Person.java)
- [`PersonTester.java`](/assets/java/interfaces/PersonTester.java)


## `Person` and the `Comparable` Interface

Suppose we have a list of people that we would like to sort according to some criteria, such as alphabetically by first name, last name, or by age. Each individual is represented as an instance of the following `Person` class:

```java
    public class Person {
        
        private String firstName;
        private String lastName;
        private int age;
    
        Person (String firstName, String lastName, int age) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }
    
        public String getFirstName () { return firstName; }
        public String getLastName () { return lastName; }
        public int getAge () { return age; }
    
        public String toString() {
            return firstName + " " + lastName + " (" + age + ")";
        }
    }
```

For sorting an array of elements, [the `Arrays` class](https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html) in Java offers a generic (and efficient) sorting algorithm:

- [`static void sort (Object[])`](https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#sort-java.lang.Object:A-) Sorts the specified array of objects into ascending order, according to the [natural ordering](https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html) of its elements.

But what is the “natural ordering” for `Person`? Following the link in the description above, the natural ordering is determined by the `Comparable` interface:

```java
    public interface Comparable<T> {
      int compareTo(T o);
    }
```

Note that the method `compareTo` is not declared `public` or `private`. By default, methods in interfaces are `public`. This makes sense because the main purpose of having an interface is to define the externally visible methods implemented by a class. 

The `Comparable` interface is defined internally in Java. The [interface documentation](https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html) says that:


> `int compareTo(T o)` Compares this object with the specified object for order. Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.<br><br>
>
> The implementor must ensure `sgn(x.compareTo(y)) == -sgn(y.compareTo(x))` for all x and y. (This implies that `x.compareTo(y)` must throw an exception iff `y.compareTo(x)` throws an exception.)<br><br>
> 
> The implementor must also ensure that the relation is transitive: `(x.compareTo(y)>0 && y.compareTo(z)>0) implies x.compareTo(z)>0`.<br><br>
> 
> Finally, the implementor must ensure that `x.compareTo(y)==0` implies that `sgn(x.compareTo(z)) == sgn(y.compareTo(z))`, for all z.<br><br>
> 
> It is strongly recommended, but not strictly required that `(x.compareTo(y)==0) == (x.equals(y))`. Generally speaking, any class that implements the Comparable interface and violates this condition should clearly indicate this fact. The recommended language is "Note: this class has a natural ordering that is inconsistent with equals."<br><br>
> 
> In the foregoing description, the notation sgn(expression) designates the mathematical signum function, which is defined to return one of -1, 0, or 1 according to whether the value of expression is negative, zero or positive.<br><br>
> 
> **Parameters:** `o` - the object to be compared.<br><br>
> 
> **Returns:** a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.<br><br>
> 
> **Throws:** `NullPointerException` - if the specified object is null`ClassCastException` - if the specified object's type prevents it from being compared to this object. 

This description gives us all of the information that we need in order to implement the `Comparable` interface for person: we just need to add a `compareTo` method for the `Person` class. For now, suppose we want to sort everyone by last name. According to the above, given `Person`s `a` and `b`, `a.compareTo(b)` should return a negative number if `a`'s last name is alphabetically before `b`’s last name. If the names are the same, the method should return `0`, and otherwise, the method should return a positive number. 

Looking over the [documentation for `String`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), we see that this class already has such a method defined—in fact, `String` already implements the `Comparable` interface! So our `compareTo` method is quite simple:

```java
    public int compareTo (Person p) {
      return lastName.compareTo(p.lastName);
    }
```

**Exercise.** Update the `compareTo` method so that if two `Person`s have the same last name, the method will go on to compare their first names. Also, if two people have the same first and last names, the method should compare their ages.

Now that we’ve implemented our `compareTo` method, we need to add the fact that we’ve implemented `Comparable` to the class declaration. To do so, we add the keyword `implements` followed by the interface we’ve implemented, in this case `Comparable<Person>`:

```java
    public class Person implements Comparable<Person> {
      ...
    }
```

Now that we’ve updated `Person` so that it `implements Comparable<Person>`, we can use the `sort` method defined in `Arrays`. Here is a simple example program that generates and sorts a list, `aList` , of A-list celebrities who rose to prominence in the 90s:

```java
    import java.util.Arrays;
    public class PersonTester {
        public static void main(String[] args) {
            Person[] aList = new Person[5];
            aList[0] = new Person("Britney", "Spears", 38);
            aList[1] = new Person("Justin", "Timberlake", 39);
            aList[2] = new Person("Jennifer", "Lopez", 51);
            aList[3] = new Person("Christina", "Aguilera", 39);
            aList[4] = new Person("Shawn", "Carter", 50);
    
            System.out.println("aList celebrities from the 90's:");
            System.out.println(Arrays.toString(aList));
    
            Arrays.sort(aList);
    
            System.out.println("\nNow sorted by last name:");
            System.out.println(Arrays.toString(aList));        
        }
    }
```


Sure enough, running the program gives the desired output:

```text
    aList celebrities from the 90's:
    [Britney Spears (38), Justin Timberlake (39), Jennifer Lopez (51), Christina Aguilera (39), Shawn Carter (50)]
    
    Now sorted by last name:
    [Christina Aguilera (39), Shawn Carter (50), Jennifer Lopez (51), Britney Spears (38), Justin Timberlake (39)]
```

This is pretty great, and the example shows the utility of interfaces. Since the `sort` method defined in `Arrays` only requires the `Comparable` interface to work its magic, we need only implement the `Comparable` interface in order to use the built-in `sort` method. On the other hand, the designers of Java had no idea how to compare `Person`s in order to sort them, yet their sorting procedure applies to `Person` and every other (user defined) class implementing `Comparable`. 

## The `Comparator` Interface

As suggested above, there are several natural ways that we might order `Person`s. Above, we chose to order them alphabetically by last name. But maybe we want to order `Person`s by age, or first name, or some other criterion. What if we want to sort `Person`s by different criteria?

One solution would be to define a subclass of `Person` for each ordering and override the `compareTo` method for each subclass. But this would be cumbersome. Thankfully, [the `Arrays` documentation](https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html) offers another way of sorting elements in an array. Specifically, there is another version of `sort` with the following signature:

- `static <T> void sort(T[] a, Comparator<? super T> c)` Sorts the specified array of objects according to the order induced by the specified comparator.

This method allows us to pass in both an array of `T`s, and [a](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html) `[Comparator](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html)`. The `Comparator` interface requires us to implement a method: 

- `int compare (T o1,  T o2)` Compares its two arguments for order.

To use the version of `sort` above that takes an array and a `Comparator` as arguments, we need to define a class that implements `Comparator`  for the `Person` class. Here is such a class that compares `Person`s by first name:

```java
    import java.util.Comparator;
    
    public class FirstNameComparator implements Comparator<Person> {
        public int compare (Person p, Person q) {
            return p.getFirstName().compareTo(q.getFirstName());
        }
    }
```

Again, the `compare` method simply calls the `compareTo` method already defined for `String`s, though in this case we compare the `firstName`s rather than `lastName`s.

**Exercise.** Update the `FirstNameComparator` so that it compares last names then ages if the first names are equal.

**Exercise.** Define an `AgeComparator` that compares people by age. Recall that `compare(Person p, Person q)` should return a negative number if `p`’s age is less than `q`’s age, `0` if the ages are equal, and a positive number of `p`’s age is greater than `q`’s age.

Now we can use our `FirstNameComparator` to sort `Person`s by first name. We can simply pass a `FirstNameComparator` instance into the `Arrays.sort` method. Running

```java
    Arrays.sort(aList, new FirstNameComparator());
    
    System.out.println("Now sorted by first name:");
    System.out.println(Arrays.toString(aList));        
```

produces the output

```text
    Now sorted by first name:
    [Britney Spears (38), Christina Aguilera (39), Jennifer Lopez (51), Justin Timberlake (39), Shawn Carter (50)]
```

## Interfaces and Abstract Classes

Interfaces are similar to abstract classes in that both constructions stipulate that some method or behavior be present in a class. Like abstract classes, you cannot define an “instance” of an interface, but you can declare a variable to be (a class that implements) some interface. For example:

```java
    Comparable<Person> p;
    p = new Person();     // this is okay, provided Person implements Comparable<Person>
```

Interfaces differ from abstract classes in that (1) interfaces do not define member variables, and (2) interfaces do not typically provide implementations of their methods. (It is possible to implement a method in an interface using the keyword `default`, but we won’t dwell on this for now.) Another difference is that a class can `implement` multiple interfaces, whereas a class can only `extend` a single (abstract) class. To implement multiple interfaces, the 
syntax is as follows:

```java
    public class MyClass implements SomeInterface, AnotherInterface, ... {
      // some code
    }
```
