---
title: "Defining New Objects"
description: "an introduction to creating and using new classes in Java"
layout: page
---

Follow along with the examples `Person.java` and `Dialog.java`.

- [`Person.java`](/assets/java/defining-new-objects/Person.java)
- [`Dialog.java`](/assets/java/defining-new-objects/Dialog.java)

## What are “Objects”

Conceptually, an **object** represents some entity, be it a physical entity or something existing only in our imaginations. Objects have **attributes** or **states** that we can use to describe them. They may also interact with one another or the “outside world.” Through these interactions, the state of an object may change.

To make things concrete, we can consider a person to be a type of object. Each person has many different attributes, such as

- a name
- an age
- species (Homo sapiens)

And how do people interact with each other? Here we list just a few ways:

- be born
- celebrate a birthday
- greet another person

Of course this set of attributes and interactions doesn’t fully specify human identity and behavior, but hopefully it gives some concrete realization of what objects, attributes, and interactions might look like.


## Representing objects in Java

In Java, (almost) everything is represented as an object. In order to define a new *type* of object, we use the keyword `class`. Specifically, the following code defines a new type of object, a `Person`:

```java
    public class Person {
        
    }
```

This syntax is probably already familiar to you, since *every* program we write in Java is written inside some `class`. 

The code above defines a `Person` class, but it isn’t yet a very interesting or useful class. We can implement the features described above, starting with the attributes name, and age. (We’ll talk about species a little later.) Someone interacting with a `Person` shouldn’t be able to change that person’s name or age, so we use the modifier `private`  to declare variables storing these values. For example, we might have:

```java
public class Person {
    private String name;
    private int age;
}
```

The variables `name` and `age` are known as **private instance variables.** Instance variables are variables whose values are determined for each individual **instance** of the object. That is, each `Person` has their own name and age. The **private** modifier means that these variables are not accessible outside of the `class Person` itself.

Next, we should deal with the creation of a new instance of a `Person`. We specify how a `Person` is initialized and built by defining a **constructor** for the `Person` class. The constructor is a method (1) whose name coincides with the class name (for us `Person`), and (2) does not have a return type (not even `void`). The constructor may take arguments needed for initialization. For us, it might be convenient to pass the constructor a `String` corresponding to the person’s name. Additionally, when a person is born, their age is 0, so we can write a constructor for `Person` as follows.

```java
    public class Person {
      private String name;
      private int age;
    
      Person (String theName) {
        name = theName;
        age = 0;
      }
    }
```

Now we can, in principle, create a new person with a given name. Still, we don’t have any way of interacting with our `Person` object once it is created. In order to use our `Person` class in a program, we can write **public instance methods** that will allow our program to interact with the `Person` instances it uses. The most basic interactions we could have with a person might be to access the information stored as private instance variables. Colloquially, these such methods are referred to as **getters**, and they typically follow the naming convention `getVariableName()`  (unless the variable is a `boolean`, in which case we use `isVariableName()`). For instance, here are two getter methods for a `Person`'s name and age:

```java
    public class Person {
      ...
      public String getName () {
        return name;
      }
    
      public int getAge() {
        return age;
      }
    }
```

If we wanted the user to be able to change a `Person`'s name, we could also define a **setter** method for this variable, such as 

```java
public void setName(String newName) {
    name = newName;
}
``` 

However, I don’t think it is prudent to allow a programmer to change a `Person`'s name (let alone age!) without their consent so I will not implement these functions here. 

The getter methods above are **public instance methods**. They use the modifier `public` to indicate that they are accessible to anyone using/creating a `Person` object. We will see the distinction between public and private below.

Now, let’s add a little more functionality to our `Person` class. Specifically, we can write public instance methods for the following features:

- `celebrateBirthday()`  increments a `Person`’s age and prints a celebratory message.
- `sayGreeting()` prints a greeting on the screen (along with the name of the speaker).
- `greet(Person p)` prints a personalized greeting for `p`.

Here is the code to implement these features:

```java
        public void celebrateBirthday () {
            age++;
            System.out.println("Happy Birthday, " + name + "!!!");
            System.out.println(name + " is now " + age + " years old.");
        }
    
        public void sayGreeting () {
            System.out.println(name + ": Hello, there!");
        }
    
        public void greet (Person p) {
            System.out.println(name + ": Hi, " + p.getName() + ".");
        }
```

Now we have a complete `Person` object! 


## Creating and using instances of the `Person` class

One point of potential linguistic confusion is the distinction between “class” and an *instance* of the class. The term “class” refers to the *type* of object. For example the specification of the class  `Person`  above doesn’t refer to a particular individual. Rather, `Person` specifies traits and behaviors that all individuals exhibit: a name, an age, etc. An **instance** of the `Person` class refers to a particular individual with a specific name, age, etc.

Now that we’ve implemented the `Person` class, we can create new instances of the `Person` class and have them interact. You can declare and define new instances of `Person`  as follows:

```java
    Person alice = new Person("Alice");
    Person bob = new Person("Bob");
```

This code creates two instances of the class `Person`. Note that `new Person("Alice")` on the right calls the constructor for `Person`, which takes a string as an argument (the `Person`’s name). 

I’ve implemented this example in a separate file, `Dialog.java`. To follow along, be sure that `Dialog.java` and `Person.java` are in the same directory. To run the program, compile both files, then run `Dialog`:


```text
    javac Person.java
    javac Dialog.java
    java Dialog
```

Once we’ve created our new `Person` instances `alice` and `bob`, we can program a brief dialog. To call an instance method for `alice`, you use the syntax `alice.methodName(...)`. For example, we can first celebrate `alice` and `bob`'s birthdays with

```java
    alice.celebrateBirthday();
    bob.celebrateBirthday();
```

Then we can have `alice` and `bob` meet and greet each other with

```java
    alice.sayGreeting();
    bob.greet(alice);
```

Run `Dialog.java` and see how it works!

#### Public vs Private Methods and Variables

Now that we have created instances of the `Person` class, we can experiment a little with how the keywords `public` and `private` affect the behavior of the code. Consider the following modifications to the code given in `Person.java`  and `Dialog.java`. Make each modification individually, the try to compile/run the program again. What happens?


1. What happens if you change the modifier of `celebrateBirthday()` from `public` to `private`? 
2. Try changing `alice`’s name to `Carole` in `Dialog.java` by adding the line
    ```java
	    alice.name = "Carole";
    ```
	after `alice` is declared. What happens? 

3. Keep the changes in `Dialog.java` from step 2, but change the declaration `private String name` in `Person.java` to `public String name`, then compile both files. What happens now?

## Class variables and methods

In our `Person` class, all of the variables and methods defined so far are *instance* variables and methods. That is, their values and functionalities apply to instances of the `class` individually: each `Person` ‘s name, age, and birthday celebrations are are particular to that individual. 

However, there may be information common to all instances of a class, or functionality relevant to the class that doesn’t apply to individual instances. For example, all people belong to the same species, *Homo sapiens*. We don’t need to store a species name for each individual `Person`, yet we might want to store species information within the `class Person`. To indicate that we want a variable for the `class Person` rather than *instances* of the `Person` class, we use the keyword `static`. For example

```java
    public class Person {
      ...
      public static String SPECIES = "Homo sapiens";
      ...
    }
```

makes a single `String` common to every `Person`. This is called a **public class variable** (to distinguish it from a public *instance* variable). Since class variables are not specific to instances of the class, you can access them via the following syntax: `ClassName.variableName`. For example

```java
    System.out.println("A person belongs to the species " + Person.SPECIES);
```

will print the name of the human species. As `SPECIES` is declared above, we could change the species of all `Person`s by using, for example, `Person.SPECIES =` `"``Canis lupus``"`. We probably do not want to allow such tomfoolery. To make it so that the `SPECIES` cannot be changed, we can add the keyword `final` to the declaration above:

```java
    public static final String SPECIES = "Homo sapiens";
```

This declaration makes `SPECIES` a **class constant**. It can still be accessed via `Person.SPECIES`, but any attempt to modify `Person.SPECIES` will result in an error.

Like instance variables, class variables may be public or private. We might want to keep track of the number of `Person` instances we’ve created in a (private) class variable. We can declare and initialize one as follows:

```java
    private static population = 0;
```

Now we can add the line `population++;` to the constructor for `Person` so that whenever a new `Person` is created, the population increases by one. 

Since `population` is `private`, it cannot be accessed via `Person.population`. We can make the value of `population` accessible by defining a **public class method** that is a getter for `population`:

```java
    public static int getPopulation() {
      return population;
    }
```

Now the total population can be accessed from `Dialog.java` using `Person.getPopulation()`. See `Dialog.java` for an application.

##### Concepts

- the difference between a class and an *instance* of a class
- the difference between a public and private variable/method
- the difference between an instance variable/method and class variable/method


##### Vocabulary

- `class`
- object
- constructor
- instance
- instance variable
- instance method
- `public` 
- `private`
- getter
- setter
- class variable
- class method
- class constant
- `static`
- `final`

