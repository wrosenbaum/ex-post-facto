---
title: "Multithreading"
layout: page
---

## Sequential and Multithreaded Programs

Typically, when learning how to program for the first time, we write programs that are **sequential.** That is, a program executes a sequence of operations in some prescribed order. For example, consider the following program ([source code here](/assets/java/multithreading/HelloThreads.java)):

```java
public class HelloWorld {
    private static int count = 0;
    
    private static void printHello() {
	    System.out.println("Hello, world! (" + ++count + ")");
    }
    
    public static void main(String[] args) {
	    printHello();
	    printHello();
    }
}
```

When we run the program with `java HelloWorld`, your computer executes the commands the order prescribed by the code. When the `main` method is executed, the program first executes the first line---a call to `printHello()`. When that method call completes, the second call to `printHello()` is executed. As a result, we get the following output:

```text
% java HelloWorld
Hello, world! (1)
Hello, world! (2)
```

This output will be the same for anyone executing this program on any machine, because the code unambiguously specifies the operations to be performed as well as the order in which the operations should be performed. 

Modern computers, however, have the ability to perform multiple operations---and indeed multiple sequences of operations---independently in parallel. This is done through a process called **multithreading**. Informally, a **thread** is a single sequence of operations to be performed in order. The `HelloWorld` program above is **single-threaded**. In fact, all programs written in Java are single-threaded, unless you explicitly make them multithreaded. (Of course, you may inadvertently write a multithreaded program by using a tool or library for Java that uses multithreading.)

This note will guide you through the basics of writing simple multithreaded programs in Java.

### Why Write Multithreaded Programs?

One of the main reasons you'd want to write multithreaded programs is performance. If a program must perform two tasks that can be done independently, then multithreading may allow a computer to perform those tasks at the same time. If the tasks take roughly the same time to complete, then by creating one thread to complete each task the program could run in roughly half the time. Typically, we will not get a $$ k$$-fold increase in performance by using $$ k$$ threads, but for some problems we can come close to this best-possible scenario. Oracle (the main developer of Java) lists some more specific [benefits of multithreading here](https://docs.oracle.com/cd/E19455-01/806-3461/6jck06gqj/index.html).

#### Terminology

We use the words "multithreaded," "concurrent," and "parallel," to describe different aspects of tasks and (executions of) programs. A program or procedure is **multithreaded** if it is contains multiple *logically independent* sequences of operations---i.e., threads. Multiple threads in a program are executed **concurrently** if multiple threads are active at the same time. That is, the execution of at least one thread is started before the execution of another thread finishes. Finally, a concurrent execution is a **parallel** execution if the concurrent executions of threads are actively evaluated at the same time. 

For a concrete example illustrating the difference between these terms, consider the following procedure for making a Spanish tortilla:

1. Slice onions.
2. Slice potatoes.
3. Fry onions and potatoes in olive oil.
4. Beat eggs.
5. Drain onions and potatoes and add to eggs.
6. Fry egg mixture in olive oil, flipping half way through.

The instructions (i.e., program) are multithreaded. For example steps 1,2, and 4 are logically independent and can be performed in any order. Moreover these steps can be performed concurrently, even by a single cook: I can start slicing one onion, then slice a potato, then go back to slicing onions, etc. While these tasks are being performed concurrently (I started slicing potatoes before I finished slicing onions), this execution is not a parallel execution because I am only actively slicing one ingredient at a time. If another person joins me in the kitchen so that I slice onions *while* my companion slices the potatoes, then the concurrent execution of the procedure becomes a parallel execution as well. Note that the concurrent execution with me cooking by myself isn't any faster than a sequential execution in which I finish slicing onions before I start slicing potatoes, etc. It is only when another cook joins me---i.e., a parallel execution---in which I am actually able to make my tortilla faster.

### Limits and Subtleties of Multithreading

While multithreading can improve the performance of our programs (sometimes significantly), multithreading has limitations. First of all, the benefits of multithreading are limited by the performance of the hardware (i.e., computer) on which we run a multithreaded program. Most modern computers can execute multiple threads concurrently using multi-core processors: my 2019-era MacBook pro has 4 physical and 8 virtual cores; [this \$25 Raspberry Pi](https://www.raspberrypi.org/products/raspberry-pi-3-model-a-plus/) microcomputer has 4 cores; even [this \$4 Raspberry Pi microcontroller](https://www.raspberrypi.org/products/raspberry-pi-pico/) has 2 cores. Thus, while a multithreaded program I run on my laptop may be able to perform 8 times as many operations per second as a single threaded program, multithreading alone cannot improve the performance of my program by more than a factor of 8. 

Another system-level issue affecting the performance of multithreaded programs is the computational overhead of creating and destroying threads. In Java, each thread is an instance of the `Thread` an class. Every time we create a new thread, the computer must allocate the resources to create the thread before it can be executed. For simple tasks, this overhead may actually be larger than the cost of performing the task itself. Thus increasing the number of threads may actually give worse performance.

But even in an idealized world where our computer can run as many threads concurrently as we want and the overhead of creating those threads is negligible, the benefits of parallelism (i.e., being able to execute multiple threads concurrently) are limited for many problems. Some tasks are inherently sequential; all steps must be completed in a prescribed order. For such problems multithreading cannot improve the performance (and will typically degrade the performance). [Amdahl's Law](https://en.wikipedia.org/wiki/Amdahl%27s_law)---which we will discuss soon---gives a quantitative upper bound on the amount by which parallelism can speed up the execution of a given tasks.

**Example.** Consider the problem educating college students. If we want to award a student an undergrad degree in computer science, say, to a student, that student must take several courses, many of which cannot be taken at the same time. Even if a student has the capacity to take many classes at once, they must follow the progression of completing COSC 111 before COSC 112, etc. Thus, the **speed** at which a student can complete their degree is limited to around 1 degree per 4 years. Adding more processors---i.e., professors---will not improve this time-to-degree. On the other hand, having more professors will allow a school to educate more students simultaneously. The speed of educating a single student does not increase by having more professors, but the **throughput** of the college increases. For example, Amherst College and its 300 academic staff produce roughly 450 graduates a year, while UMass and its 1,300 academic staff produce roughly 6,000 bachelors degrees per year. Amherst College and UMass Amherst operate at the same speed---4 years per student---but UMass's throughput of 6,000 degrees per year is more than 13 times Amherst College's throughput.

The final challenge of writing multithreaded programs is primarily conceptual. Simply put, multithreaded programs are difficult to reason about (and debug!). Unlike sequential programs, the order in which operations occur across different threads is not under the control of the programmer. Thus, a correct program for performing a task must be correct *for every possible execution* of the program. This issue becomes tremendously subtle when different threads must access shared resources (e.g., a memory location or data structure). As we will see, even the basic problem of deciding which of two threads can access a shared bit of memory at a given time requires sophisticated reasoning. 

Most of this course is devoted to appreciating, understanding, and taming the wilderness of multithreaded programming. To get started, we should write our first multithreaded program.

## Threads in Java

In Java, threads are instances of the `Thread` class (or subclasses thereof). You can read the [full documentation here](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Thread.html). There are several ways to create threads for performing a task in Java. The two most straightforward are:

1. Create a class that implements [the `Runnable` interface](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Runnable.html). 
2. Define a subclass of `Thread`.

In either case, we must define a method `void run()`. This is the method that will get called when we start a thread---you can think of `run` as the thread equivalent of the `main` method in a program. Here, we will focus on first method.

### Hello, Threads!

We are now ready to introduce our first multithreaded program: `HelloThreads` ([source code here](/assets/java/multithreading/HelloThreads.java)). Here is a basic version:

```java
public class HelloThreads implements Runnable {
    public static final int NUM_THREADS = 4;  // number of threads to run

    private String message;                   // message printed by this

    // constructor sets the message to be displayed by this thread
    public HelloThreads (String message) {
	    this.message = message;
    }

    // the run method required by the Runnable interface
    // this is the code that gets executed when we start the thread
    public void run () {
	    System.out.println(message);
    }
    
    
    public static void main (String[] args) {
	
    	// make an array of threads
	Thread[] threads = new Thread[NUM_THREADS];

        // initialize threads with a welcome message
	for (int i = 0; i < NUM_THREADS; i++) {
	    threads[i] = new Thread(new HelloThreads("Hello from thread " + i));
    	}

	System.out.println("Say hello, threads!");

        // start all of the threads
        for (Thread t : threads) {
            t.start();
        }

	System.out.println("Goodbye, threads!");
    }
}
```

When I run this program, I get the following output:

```text
% java HelloThreads
Say hello, threads!
Hello from thread 0
Hello from thread 1
Hello from thread 2
Goodbye, threads!
Hello from thread 3
```

Already, we can see something strange is going on. Threads 0, 1, and 2 printed their message before the final line, but then `Goodbye, threads!` is printed *before* thread 3 prints its greeting. Unlike our sequential `HelloWorld` program **statements in the multithreaded program may not be executed in the same order in which they appear in the control flow of the program.** We have no control over the relative order that different threads perform their operations (though *within each thread*, the statements will be executed in the order prescribed by the program). By setting `NUM_THREADS` to 8, I get the following output:

```text
Say hello, threads!
Hello from thread 0
Hello from thread 1
Hello from thread 3
Hello from thread 2
Hello from thread 4
Hello from thread 5
Goodbye, threads!
Hello from thread 6
Hello from thread 7
```

This looks even worse! The threads didn't even print their messages in the same order they were started. Not only that, but when I run the same program again (without modification), I get the following output:

```text
Say hello, threads!
Hello from thread 0
Hello from thread 1
Hello from thread 2
Goodbye, threads!
Hello from thread 6
Hello from thread 5
Hello from thread 3
Hello from thread 4
Hello from thread 7
```

This behavior again shows that multithreaded programs are inherently different from sequential programs: **two different executions of the same program may give different results.** This behavior is to be expected, however. Since the threads are being executed independently in parallel, we should not expect them to perform their tasks in any particular fixed order.

#### Waiting for threads to complete

One thing that is problematic with the execution above is that we say goodbye to the threads before they all have terminated. Often, we will want to wait until we know a thread has finished its task before moving on. We can wait until a task completes by using the `join` method.

```java
Thread t;
...
t.start();
...
t.join();

// any code after this will only be executed
// after t finishes

```

From [the documentation](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Thread.html#join()), we see that `join()` throws an `InterruptedException`, which we need to handle. (This exception gets thrown if a thread is interrupted---something we'll talk about later. For our purposes, we can safely ignore such an exception.) Our new `main` method is now

```java
    public static void main (String[] args) {
	// make an array of threads
	Thread[] threads = new Thread[NUM_THREADS];

	// initialize threads with a welcome message
	for (int i = 0; i < NUM_THREADS; i++) {
	    threads[i] = new Thread(new HelloThreads("Hello from thread " + i));
	}

	System.out.println("Say hello, threads!");

	// start all of the threads
	for (Thread t : threads) {
	    t.start();
	}

    // wait for all threads to complete
	for (Thread t : threads) {
	    try {
		    t.join();
	    }
	    catch (InterruptedException ignored) {
		    // don't care if t was interrupted
	    }
	}

	System.out.println("Goodbye, threads!");

    }
```

Now when I run this program, I get the following output:

```text
Say hello, threads!
Hello from thread 0
Hello from thread 1
Hello from thread 2
Hello from thread 3
Goodbye, threads!
```

We are still not guaranteed anything about the relative order of the messages printed by the individual threads. However, it will always be the case that `Goodbye, threads!` is printed *after* the messages from all threads.

#### Getting output from threads

We run threads using the `start()` method, and we can wait until a thread finishes by using the `join()` method. However, these methods neither take any arguments, nor return any values. So how can we get output from a thread, or get a thread to modify data that we will use later in our program?

In order to access data produced by a thread, we need to create an object *before* starting the thread. We can then pass (a reference to) the object to the constructor for our thread's class. The following program `SharedArray.java` illustrates how multiple threads can access the same array ([source code here](/assets/java/multithreading/SharedArray.java)).

```java
import java.util.Arrays;

public class SharedArray implements Runnable {
    public static final int NUM_THREADS = 4;  // number of threads to run

    private int id;                           // thread id
    private int[] array;                      // array


    public SharedArray (int id, int[] array) {
    	this.id = id;
    	this.array = array;
    }

    public void run () {
	// set the value of array[id] to be this id
	array[id] = id;
    }
    
    
    public static void main (String[] args) {
	// the shared array with one index for each thread
	int[] shared = new int[NUM_THREADS];
	
	// make an array of threads
	Thread[] threads = new Thread[NUM_THREADS];

        // initialize threads with a welcome message
	for (int i = 0; i < NUM_THREADS; i++) {
	    threads[i] = new Thread(new SharedArray(i, shared));
	}

    	// start all of the threads
	for (Thread t : threads) {
	    t.start();
	}

    	// wait for all threads to complete
	for (Thread t : threads) {
	    try {
		    t.join();
            }
    	    catch (InterruptedException ignored) {
     		// don't care if t was interrupted
	    }
    	}
	
	// print the contents of the array to the terminal 
	System.out.println("sharedArray = " + Arrays.toString(shared));
    }
}
```

Note that a *single* `int[] shared` is created in the `main` method. This same array (or rather, a reference to the array) is passed to the constructor of each `SharedArray` in

```java
	for (int i = 0; i < NUM_THREADS; i++) {
	        threads[i] = new Thread(new SharedArray(i, shared));
	}
```

In each `SharedArray` instance created, the local variable `array` references the `int[] shared` created in `main`. Therefore, when `run()` is invoked for each thread, all threads modify `shared` from the main method.

**Caution.** It is potentially dangerous to have multiple threads modifying the same object (in this case `shared`)! There is nothing stopping different threads from accessing the same entry in the array, thereby leading to unpredictable/unintended behavior. We will spend a significant a significant amount of time understanding how to create objects that are "thread-safe," meaning that they behave predictably when accessed by mutiple threads concurrently.

