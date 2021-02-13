---
title: Getting Started with Java
description: a brief guide to get you started programming with Java
layout: page
toc: true
---

## Background

Java is among the most popular programming languages in use today. Due to the language's popularity there is a tremendous variety of tools available for developing programs in Java. However, this variety comes at a cost: how should you determine which tool is best for you? For our purposes, this mostly comes down to personal preference. To make an informed decision, it is important to understand the different options.

Making and running a Java program occurs in three steps:

1. Writing Java code and saving the code in one or more files with the `.java` extension (such as `HelloWorld.java`).

2. Compiling the `.java` file(s) into [Java bytecode](https://en.wikipedia.org/wiki/Java_bytecode). The resulting files will have the extension `.class`.

3. Running the program. More specifically, in this step, the bytecode resulting from step 2 is executed on the Java virtual machine (JVM).

In class, I will typically perform these separate steps by hand in a terminal application on my computer. However, in some development platforms, steps 2 and 3 might be merged together. 

In general, the three steps above are completed by three different programs on your computer. Step 1 is done by using a [text editor](https://en.wikipedia.org/wiki/Text_editor) to write the `*.java` file(s) as a plain text document. Step 2 uses a program called a Java compiler to convert the `*.java` files to `*.class` files. Finally, a third program executes the program on the JVM. 

There are several development philosophies and corresponding tools adhering to those philosophies. Below, I discuss different options in a bit more detail. To save you some time, **if you do not already have a text editor or IDE that you are comfortable with, I recommend using IntelliJ IDEA**, which you can [download here](https://www.jetbrains.com/idea/) (the community version is free, and more than sufficient for our needs).

If you don't want to read more about different workflow options, [skip to the IntelliJ IDEA workflow section below](#sample-intellij-workflow).

#### Integrated Development Environments (IDEs)

An *Integrated Development Environment* (or IDE) is an application that provides a large set of tools for development in a single package. At minimum, an IDE will have a text editor, compiler, and runtime environment all packaged together. They will typically include many other tools such as a debugger, navigation, [version control](https://en.wikipedia.org/wiki/Version_control), autocompletion, and tools for code analysis or visualization. IDEs can be incredibly helpful for building large, complicated projects. They also tend to be easy to start using. 

From the perspective of learning to code, IDEs have some drawbacks. First, they often provide many tools that are irrelevant or overkill for relatively simple programs. Thus, they may have a steep learning curve that is above and beyond the difficulties inherent to learning to programming. Additionally, an IDE's default settings may not be optimized for your own workflow, and it may be difficult to customize the IDE to work best for you.

Two of the most popular IDEs for Java development are [IntelliJ IDEA](https://www.jetbrains.com/idea/) and [Eclipse](https://www.eclipse.org/downloads/). 


#### General Purpose Text Editor and Command Line

The opposite philisophical extreme from an IDE is writing code with a general purpose text editor and compiling/running from a command line terminal. There are [countless general purpose text editors](https://en.wikipedia.org/wiki/List_of_text_editors), with varying levels of sophistication and custimization. I use Emacs (which is what you will see in lectures). General purpose text editors are great for writing any plain text---they are not optimized for one particular programming language or usage case. Thus, they are simpler and more flexible than IDEs. 

If you use a general purpose text editor, you will need to install Java separately (unless Java was already installed by another program such as an IDE). The easiest option for installing Java is probably [AdoptOpenJDK](https://adoptopenjdk.net/). I'd recommend installing OpenJDK 11 (with either JVM version). You will then need to compile and run your programs from the command line (e.g., Terminal.app for MacOS or Command Prompt in Windows). 

#### Between the Extremes

Recently, several applications that exist somewhere between full-blown IDE and humble text editor. Two popular choices are [Visual Studio Code](https://code.visualstudio.com/) (VSCode) and [Sublime Text](https://www.sublimetext.com/). These tools offer the flexibility of general purpose text editors with (many of) the language-specific tools of an IDE (available as extensions). VSCode in particular has become the most popular text editor among [developers in StackOverflow's annual survey](https://insights.stackoverflow.com/survey/2019#technology-_-most-popular-development-environments). 

## Sample IntelliJ Workflow

IntelliJ offers what is probably the simplest way to get started writing Java programs for theis course. To get started, I recommend making a directory/folder someplace safe on your computer (e.g., in a Dropbox folder). Then make a sub-directory for each assignment/project for this course. Finally download your desired Java file, say [`HelloGraphics.java`](/assets/teaching/2021s-cosc-112/lab01-hello-graphics/HelloGraphics.java), and save it to the appropriate location on your computer.

Now start up IntelliJ, and follow the "simple import" [instructions here](https://www.jetbrains.com/help/idea/import-project-or-module-wizard.html) to create an IntelliJ project for your program. Note that you need to open the **directory** containing the `*.java` file(s) for your project, and not the individual files themselves. If everything worked, your screen should look something like this:

![IntelliJ openening screen](/assets/img/getting-started-java/intellij-opening-screen.png){: width="100%"}


By clicking the "HelloGraphics" folder icon in the navigation pane on the left, you can see the contents of the directory, including your `HelloGraphics.java` file. (IntilliJ will have generated some other files as well, which you can safely ignore.)

To run the program, you'll first have to build it. You can do this by clicking the green hammer button in the upper-right portion of the screen:

![IntelliJ openening screen](/assets/img/getting-started-java/intellij-built.png){: width="100%"}


Once the program is built (assuming no errors) you can run it by clicking clicking the green triangle next to the first line of the main class declaration (`public class HelloGraphics...`):

![IntelliJ openening screen](/assets/img/getting-started-java/intellij-run.png) 


#### IntelliJ Terminal

Later in the class, we will want to compile and run our programs from the terminal. You can open the terminal by clicking the "Terminal" button in the lower corner of the screen. You'll see something like this:

![IntelliJ openening screen](/assets/img/getting-started-java/intellij-terminal.png){: width="100%"}


You can compile your program by issuing the command

```text
javac HelloGraphics.java
```

If there weren't any errors, nothing exciting will happen. If you issue the command `ls`, the terminal will list the files in the directory. You should now see a new file `HelloGraphics.class`. To run the program from the terminal, issue the command:

```text
java HelloGraphcs
```

(Note that you do not include the `.java` or `.class` extension in the file name here.) Now the program will run as before.

It will occasionally be more convenient to compile and run programs from the terminal instead of using IntelliJ's build/run commands.




