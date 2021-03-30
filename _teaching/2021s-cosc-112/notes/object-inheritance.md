---
title: "Object Inheritance"
description: "defining relationships between classes"
layout: page
---

It often happens that we would like to define `class`es that are closely related to each other: they may store similar data (i.e., have fields of the same names and types), or offer similar functionalities (i.e., have similar methods). Take for example the following two classes:

```java
    public class Person {
      private String name;
      private int age;
      
      public Person (String name, int age) {
        this.name = name;
        this.age = age;
      }
    
      public String getName () {return name;}
      public int getAge () {return age;}
    
      public void sayGreeting () {
        System.out.println(name + ": Hello there!");
      }
    
      public void greet (Person p) {
        System.out.println(name + ": Hello, " + p.getName() + "!");
      }
    }
```

```java
    public class Student {
      private String name;
      private String institution;
      private int age;
      
      public Student (String name, String major, int age) {
        this.name = name;
        this.major = institution;
        this.age = age;
      }
    
      public String getName () {return name;}
      public String getInstitution () {return institution;}
      public int getAge () {return age;}
    
      public void sayGreeting () {
        System.out.println(name + ": Can't talk now... I'm on my way to " + institution);
      }
    
      public void greet (Student s) {
        System.out.println(name + ": Hey, " + s.getName() + "! Can we talk after the exam?");
      }
    }
```

Notice that these `class`es have many features in common:

- fields for `name` and `age`
- getter methods for `name` and `age`
- methods called `sayGreeting()` and `greet(…)`

Since there is a lot of similar and duplicated code between the `class`es, it would be nice to not have to write this code twice. Thankfully, Java gives us a way of creating new `class`es by extending and modifying existing `class`se though *inheritance*.

## Code for this note

Note that `PersonTester.java` contains the `main` method.


- [`ACStudent.java`](/assets/java/object-inheritance/ACStudent.java)
- [`Being.java`](/assets/java/object-inheritance/Being.java)
- [`Employee.java`](/assets/java/object-inheritance/Employee.java)
- [`Person.java`](/assets/java/object-inheritance/Person.java)
- [`PersonTester.java`](/assets/java/object-inheritance/PersonTester.java)
- [`Student.java`](/assets/java/object-inheritance/Student.java)

## Inheritance

Inheritance allows us to define a new `class` as an extension of a previously defined `class`. The new `class` is known as a **subclass** of the previously defined `class`, and the the old is a **superclass** of the new `class`. A subclass inherits all of the fields and methods of the superclass by default, although *private methods and fields are not accessible to a subclass.* In order to define a subclass of an existing class, use the `extends` keyword. For example, we can define `Student` as a subclass of `Person`, by using

```java
    public class Student extends Person {
      ...
    }
```

Once `Person` is implemented, we need only implement (1) new functionality that a `Student` should offer that is not already present in `Person`, and (2) modify any functionality that should be different for a `Student` than for a `Person`. Note that `Person` already has


- fields for `name` and `age`
- a constructor and the following methods: getters for `name` and `age`, and methods `sayGreeting()` and `greet(Person p)`. 

Thus in our `Student` class, we only need to do the following:

1. add a field and getter method for `institution`,
2. add a constructor that allows us to set the institution of a student,
3. modify the `sayGreeting()` and `greet()` methods.

There are a couple things we need to be aware of to complete the implementation. The first is that `private` fields/methods from `Person` are not accessible to the subclass `Student`. Thus we need to use getters/setters (or use the modifier `protected` in the `Person` class instead of `private`—we’ll talk about this later). Since we don’t have access to, for example, `name` and `age` in `Student`'s constructor, we can call the constructor for `Person` explicitly using `super(…)` where `…` refers to whatever arguments we want to pass to the `Person` constructor.

Here is a complete implementation of `Student` that will give exactly the same functionality as before, but using the fields and methods inherited from the `Person` class:

```java
    public class Student extends Person {
      private String institution;
      
      public Student (String name, String institution, int age) {
        super(name, age);    // call the Person constructor with args name and age
        this.institution = institution;
      }
    
      public String getInstitution () {return institution;}
      
      public void sayGreeting () {
        System.out.println(getName() + ": Can't talk now... I'm on my way to " + institution);
      }
    
      public void greet (Person s) {
        System.out.println(getName() + ": Hey, " + s.getName() + "! Can we talk after the exam?");
      }
    }
```

**Exercise.** Notice that we used the getter methods `getName()` in `sayGreeting()` and `greet(Person s)`, where `Person` just uses the field  `name`. Modify the code above to use `name` instead of `getName()` for the greeting methods. What happens when you compile?

#### The “is a” rule

Why did we define `Student` as a subclass of `Person` and not the other way around? Well, because `Student` only adds and modifies fields/methods already present in `Person`. For a more general principle for deciding when and whether to use inheritance, consider the **is a rule**: Suppose you have to classes `ClassA` and `ClassB`. Is it aways the case that a `ClassB` *is a* `ClassA`? If so then it is probably appropriate to define `ClassB` as a subclass of `ClassA`. For example every `Student` is a `Person`, but not every `Person` is a `Student`. So it would make sense to define a `Student` as a subclass of `Person`, while the opposite would not make sense. 

Inheritance only allows you add and modify functionality to a superclass—you cannot *remove* a field or method present in a superclass.

#### Keyword `super`

Above we used a method call `super(name, age)` to call the constructor of `Student`'s superclass, `Person`. The keyword `super` can be used to access the (overridden) version of method in a superclass. For example, inside `Student`, we could use `super.greet(...)` to call the `Person` version of the `greet` method (which was overridden in `Student`).

**Question.** Suppose we redefine the `greet(Person p)` method for `Student` as follows:

```java
      public void greet (Person s) {
        super.greet(s);
        System.out.println("Can we talk after the exam?");
      }
```

What would be the result of the following code?:

```java
    Person alice = new Person("Alice", 23);
    Student bob = new Student("Bob", 19);
    bob.greet(alice);
```

#### `@Override`

In Java, you can use the annotation `@Override` before overriding a method from a superclass. For example, in our `Student` class we could have written: 

```java
    public class Student extends Person {
      ...
      @Override
      public void sayGreeting () {
        System.out.println(name + ": Can't talk now... I'm on my way to " + institution);
      }
      
      @Override
      public void greet (Person s) {
        System.out.println(name + ": Hey, " + s.getName() + "! Can we talk after the exam?");
      }
    }
```

The `@Override` annotation doesn’t affect how the code runs. Instead, the annotation tells the compiler that we intend for the next method defined to override a method in a superclass. The compiler will then verify that there is indeed a method with the same signature (i.e., name and parameter type list) in a superclass, and the compiler will raise an error if no such method is found. This behavior is incredibly helpful in debugging and maintaining your code.

**Exercise.** In the `Person` class, rename the method `greet(Person s)` to `great(Person s)` and compile. What error do you get? Remove the line `@Override` and compile again. Do you still get the error? What happens if you define a new method `public void greet (Person p, Person s)` for `Student` that greets two `Person`s and put `@Override` before the method definition?

#### Why should you use inheritance?

1. Encapsulation. Using inheritance allows you to cut down redundant code. This means when some functionality needs modification, you only need to change it in one place. 
2. Writing less code. Often, using inheritance allows to to get more functionality without having to re-write redundant code.
3. More clarity of code. Using `extends` makes your intentions more obvious to anyone reading your code (including your future self). 

## Inheritance Diagrams

Now that we’ve seen how inheritance can help us simplify, clarify, and encapsulate our code, we should be eager to apply inheritance whenever it seems appropriate. Above, we had a single (super)class `Person` and subclass `Student`. But there are many different types of `Person` we could envision, and even many types of `Student`. We can use inheritance so that a given class may have many subclasses, each of which has subclasses itself. However, *each* `*class*` *can extend only a single* `*class*`.  (Think about why this must be the case!)

For example, we might have a class `ACStudent`  that represents a student at Amherst College. For example, consider the definition:

```java
    public class ACStudent extends Student {
      public ACStudent (String name, int age) {
        super(name, "Amherst College", age);
      }
    
      @Override
      public void sayGreeting () {
        System.out.println(getName() + ": Can't talk now... I'm on my way to Val.");
      }
      
      @Override
      public void greet (Person s) {
        super.greet(s);
        System.out.println("Go Mammoths!");
      }
      
    }
```

We also might want to make another subclass of `Person` for employees:

```java
    public class Employee extends Person {
      private String title;
    
      public Employee (String name, String title, int age) {
        super(name, age);
        this.title = title;
      }
    
      public void getPaid(int amount) {
        System.out.println(getName() + ": I just got paid $" + amount + "!!!");
      }
    
      @Override
      public void sayGreeting () {
        System.out.println(getName() + ": Hello everyone! I'm a + " + title + "!");
      }
    } 
```

**Question.** Suppose we wanted to implement a `StudentEmployee` class for student employees. What would be the (conceptual) problem with defining a class that extends both the `Student` and `Employee` classes? (Hint: how would a `StudentEmployee` say a greeting?)

We can represent the relationships between the `class`es `Person`, `Student`, `ACStudent`, and `Employee` using an **inheritance diagram**. To produce an inheritance diagram, each class is represented by a node labeled with the class’s name. Whenever one class `extends` another class, we write the superclass above the subclass and draw a line between the classes. For the classes above we get the following inheritance diagram.

![](/assets/img/object-inheritance/inheritance-diagram.svg){: width="50%"}

Note that while `ACStudent` only extends `Student`, both `Student` and `Person` are superclasses of `ACStudent`. In general the superclass relationship is transitive: if `A` is a superclass of `B` and `B` is a superclass of `C`, then `A` is a superclass of `C`.

**Exercise.** Write a `StudentEmployee` class that extends the `Student` class. It should include all of the functionality of both the  `Student` and `Employee` classes. Update the inheritance diagrams to include your `StudentEmployee` class.

## Method Calls & Inheritance

When we use inheritance, it often happens that we have many methods with the same signature—namely the methods that are overridden. For example, `Person`, `Student`, and `ACStudent` all have methods called `sayGreeting()`. Yet for each type the expected behavior is slightly different. Consider the following example:

```java
    Person alice = new Person("Alice", 23);
    Student bob = new Student("Bob", "UMass", 19);
    ACStudent eve = new ACStudent("Eve", 20);
    
    alice.sayGreeting(); // prints "Alice: Hello there!"
    bob.sayGreeting();   // prints "Bob: Can't talk now... I'm on my way to UMass"
    eve.sayGreeting();   // prints "Eve: Can't talk now... I'm on my way to Val. "
```

That is, the program knows which version of `sayGreeting()` to call based on the `class` of each instance. All of the (non-overridden) methods for superclasses are also available to to subclasses. For example:

```java
    String inst;
    inst = alice.getInstitution(); // ERROR: no such method for Person or superclass
    inst = bob.getInstitution();   // calls method defined in Student
    inst = eve.getInstitution();   // calls method defined in Student (superclass)
```

Also:

```java
    String name;
    name = alice.getName(); // calls method defined in Person
    name = bob.getName();   // calls method defined in Person (superclass of Student)
    name = eve.getName();   // calls method defined in Person (superclass of ACStudent)
```

Since the there may be many methods with the same signature accessible to an instance of a subclass, Java does a sensible thing to resolve ambiguity: it always calls the method defined in the “closest” superclass with the correct signature. More specifically, consider the call 

```java
    eve.sayGreeting();
```

Since `eve` is an instance of `ACStudent` which is a subclass of `Student` and `Person`, there are three different methods `sayGreeting()` visible to to an `ACStudent`. Since `ACStudent` itself has a method `sayGreeting()`, this is the method that actually gets called. If `sayGreeting()` had not been overridden in `ACStudent`, the method call above would resolve to the version of `sayGreeting()` defined in `Student` because `ACStudent extends Student`. The version of `sayGreeting()` for `Person` would only get executed by `eve.sayGreeting()` if `sayGreeting()` was defined neither for `ACStudent` nor `Student`.

**Exercise.** Verify this! Delete the method `sayGreeting()` for `ACStudent`. What is the output of `eve.sayGreeting()`? 

## Abstract Classes

Sometimes we would like to define a `class` that captures and consolidates the features of many `class`es, but is so general that it doesn’t make sense to implement all of its methods. For example,  every person is a being. But beings include all sorts of other entities: dogs, cats, dragons, etc. All beings have, say, a name and an age. Also, all beings are capable of giving some form of greeting, but printing text to the screen might not be an appropriate representation of all beings’ greetings. For example, a dog might say a greeting by wagging its tail. Since this is very different from a typical greeting for a person, it doesn’t make sense to implement a single greeting method that is applicable to all beings. Yet all beings should offer *some* form a greeting.

Abstract classes allow us to define a `class` and *declare* its methods without *implementing* them. For example, we can define an **abstract class** for a `Being`  that declares a `sayGreeting()` method, but offers no implementation of `sayGreeting()`. All (non-abstract) subclasses are required to implement the `sayGreeting()` method. 

We use the `abstract` modifier to declare a method in a `class` without implementation. In order to use `abstract` inside a class, we also must declare the class itself as `abstract`. For example:

```java
    public abstract class Being {
      private String name;
      private int age;
    
      public Being(String name, int age) {
        this.name = name;
        this.age = age;
      }
    
      public String getName () { return name; }
      public int getAge () { return age; }
    
      // a method declaration without an implementation
      public abstract String sayGreeting ();
    }
```

**Exercise.** Implement `Person` as a subclass of `Being`:

```java
    public class Person extends Being {
      // you provide the details here!!!
    }
```

**Exercise.** Try compiling `Being.java` and your new version of  `Person.java` with `Person`'s implementation of `sayGreeting()` commented out. What error do you get?

Since abstract classes are missing implementations of methods they cannot be instantiated—we can only create instances of subclasses of `abstract class`es that are not themselves `abstract`. 

**Exercise.** Try creating an instance of a `Being` using `Being gaia = new Being("Gaia", 100000000);` What error do you get?

## Vocabulary

- inheritance
- subclass
- superclass
- `extends` keyword
- override
- `super` keyword
    - for calling a superclass constructor
    - for calling superclass methods
- `@Override` annotation
- “is a” rule
- inheritance diagram
- abstract class
- `abstract` keyword
