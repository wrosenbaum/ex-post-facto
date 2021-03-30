---
title: "Lab 04: Exceptions"
description: "validating input and handling errors gracefully"
layout: page
---

In this lab, you will learn how to use exceptions to handle unexpected or erroneous input gracefully. For the assignment, you will write a program that parses integer values given by the user as input. That is, given a sequence of characters, your program will either (1) identify the string as representing an integer and store that value as an `int`, or (2) identify that the string does not represent an integer, and send an appropriate warning to the user. In case (2), the program should return to a “safe” state and continue to operate as expected despite the “bad” input given by the user.

**Note.** To get going with the lab, here are some extra resources for exception handling:

- [Handling Exceptions](/teaching/2021s-cosc-112/notes/handling-exceptions/) (notes)
- Chapter 11, *Exceptions* in Java Programming
- Oracle tutorial: [Exceptions](https://docs.oracle.com/javase/tutorial/essential/exceptions/index.html) 

I recommend writing a version of the program that does catch exceptions first, then modifing your program to handle exceptions. I also suggest working through the [*Handling Exceptions*](/teaching/2021s-cosc-112/notes/handling-exceptions/) notes before impelementing the exception handling part of the program.

Here is a sample interaction with the completed program:

```text
Enter a number (-1 to exit): 1
x = 1
Enter a number (-1 to exit): 142
x = 142
Enter a number (-1 to exit): -452
x = -452
Enter a number (-1 to exit): 1c34
Could not read integer. InvalidIntegerException: Non-numerical character 'c' encountered.
Enter a number (-1 to exit): 14.3
Could not read integer. InvalidIntegerException: Non-numerical character '.' encountered.
Enter a number (-1 to exit): -34.2
Could not read integer. InvalidIntegerException: Non-numerical character '.' encountered.
Enter a number (-1 to exit): 1 2 3 4 5
x = 1
Enter a number (-1 to exit): x = 2
Enter a number (-1 to exit): x = 3
Enter a number (-1 to exit): x = 4
Enter a number (-1 to exit): x = 5
Enter a number (-1 to exit): -1
x = -1
Goodbye!
```

In particular, the “expected” inputs (i.e., strings of characters representing integers) are handled as expected. “Unexpected” inputs result in a warning being printed the user, but the program does not crash. Instead, the program returns to a state where it can read the next input from the user. 

To get started, download the following files, linked to below:


- [`InvalidIntegerException.java`](/assets/java/2021s-cosc-112/lab04-exceptions/InvalidIntegerException.java)
- [`Parser.java`](/assets/java/2021s-cosc-112/lab04-exceptions/Parser.java)
- [`ParserTester.java`](/assets/java/2021s-cosc-112/lab04-exceptions/ParserTester.java)

There are two things you must complete in the code above:

1. In `Parser.java`, write the method `int readInt()`. The method should read characters from the `InputStream in` (an instance variable for the `Parser` class). Specifically, your method should read the successive characters from `in` until the next whitespace character or end of stream is encountered ([The method `Character.isWhitespace(char ch)`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Character.html#isWhitespace(char)) will probably be helpful here). If those characters constitute an integer—i.e., are all digits `0` through `9`, possibly starting with the `-` character—then `readInt()` should return the `int` whose decimal representation corresponds to these characters. If a non-numerical character is encountered, `readInt()` should do the following.
    1. Continue reading from `in` until the next whitespace character is read or the end of the stream is reached.
    2. Throw an `InvalidIntegerException` with a message indicating the *first* non-numerical character encountered in `in`. 
2. In `ParserTester.java`, modify the main loop so that the program also catches any `InvalidIntegerException` raised in `Parser`. When an `InvalidIntegerException` is caught, the program should print the message `Could not read integer.` to the screen, followed by the details of the caught exception. Note that an `InvalidIntegerException` should not break out of the main loop in the program—after an `InvalidIntegerException`, the user should be able to continue entering more values.

## Notes

The class `InputStream` offers only basic operations for reading input. Specifically, you will need to use the `read()` method to access each character from `in` individually. See the [`InputStream` documentation](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/InputStream.html) for more info. Note that `read()` throws an exception, so you must update the declaration of `readInt()` to deal with this exception as well as the `InvalidIntegerException` you define.

As indicated in the `InputStream` documentation, the `read()` method returns an `int` whose value is `-1` if the end of the stream is reached. Otherwise, the returned value is between 0 and 255 (inclusive), and represents the next byte stored in the stream. Each byte represents a single character in the stream. For ASCII text (i.e., the text encoding used for console applications), you can see the correspondence between `byte` (or `int`) values and their `char` equivalents [on this page](https://www.cs.cmu.edu/~pattis/15-1XX/common/handouts/ascii.html).  From the chart, we can see that, for example,  casting the character `'``W``'` as an `int`  will store a value of 87.

```java
    int capitalA = (int) 'A';     // stores value 65
    int lowerCaseB = (int) 'b';   // stores value 98
```

Thankfully, you don’t need to know the conversion from `char` to `int` explicitly to figure out the value of a digit associated to a char. It is enough to know that the characters `0`, `1`, …, `9` appear sequentially in the ASCII code. Thus, we can use the following trick to get an int whose value is the digit stored in a char `ch` :

```java
    int digit = (int) ch - (int) '0'; 
```

For example if `ch` stores the character `4`, the value of  `digit` will be `4` after executing the line above. This pattern will be helpful to get the numerical value associated to a digit read from `in`. You can also test that `ch` represents a digit by checking if `(int) 0 <= (int) ch` and `(int) ch <= (int) 9`.

##### A note on style

In order to refer to refer to a fixed `char` value, you should always use the literal character in your code rather than its ASCII (integer) value. For example:

```java
char c = 'A'; // GOOD: refer to the literal char
char d = 65;  // BAD: never do this
```

##### Requirement

Your program *must* implement the logic to parse an `int` from a sequence of `char`s. In particular, you may *not* use the [`Integer.parseInt(...)`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Integer.html#parseInt(java.lang.String)) method, or any similar method that handles the parsing for you.

## What to submit

Submit all code needed to run your program (`InvalidIntegerException.java`, `Parser.java`, `ParserTester.java`) to the Moodle site by **Friday, March 26th, 11:59 AoE.** Additionally, please fill out the survey linked to from the Moodle submission site.

## Grading

The lab will be graded on a 3 point scale as follows:

- **3** Everything compiles and runs as specified in this document; code is fairly readable and contains comments briefly describing the main functionality of methods defined/larger chunks of code.
- **2** The program produces more-or-less correct output, but is sloppy/hard to read; comments may be there, but are not helpful.
- **1** Program compiles, but is far from producing the expected output and/or does not run as specified; comments unhelpful or absent.
- **0** Program doesn't compile or outputs garbage; no comments explaining why.

Additionally, **extra credit** will be awarded for early submissions (up to 4 days early) as specified in the course syllabus.

## Extensions

Write a program that uses the `InputStream` and exception handling to validate other types of input. For example, you could write a password validator that reads a stream of characters (ending with whitespace) and determines if the text is a strong password. Here a strong password should:

1. be at least 8 characters in length;
2. contain at least one capital and one lower-case letter;
3. contain at least one numerical character;
4. contain at least one "special" character (not a letter or number).
