---
title: "Lab 01: Estimating Pi"
description: "estimating pi using a Monte Carlo simulation"
layout: page
---

--------------------

**DUE:** Friday, February 26, 23:59 [AOE](https://time.is/Anywhere_on_Earth)

--------------------

#### Prerequisites

- Read [Multithreading](/teaching/2021s-cosc-273/notes/multithreading)
- Read [Monte Carlo Simulation](/teaching/2021s-cosc-273/notes/monte-carlo-method)

#### Program Description

In this assignment, you will implement a multithreaded program that performs a Monte Carlo simulation to estimate the mathematical constant $$\pi \approx 3.14\ldots$$. A conceptual description of such a procedure is described in the notes [Monte Carlo Simulation](/teaching/2021s-cosc-273/notes/monte-carlo-method). Briefly, the idea of the procedure is to generate many random sample points (i.e., pairs of numbers) lying inside a square, and return the number of samples that lie inside a disk inscribed in the square. Then $$\pi $$ can be estimated from the proportion of samples that fall in the disk.

Your program should take two parameters---the number of samples, and the number of threads---and compute an estimate of $$\pi $$ by performing the specified number of samples evenly distributed across the threads. For example, if the number samples is `1000`, and the number of threads is `10`, each thread should perform `1000 / 10 = 100` samples (you may assume that the number of samples is evenly divisible by the number of threads). 

Specifically, your program must define a `PiEstimator` class that stores the number of samples and the number of threads used for its calculations. Be sure to use `long` for the number of samples, as this number could be larger than the maximum `int` value (which is a little over 2 billion). The `PiEstimator` class must include a public instance method `double getPiEstimate()` that returns the desired estimate of $$\pi $$. For example, here is a skeleton of the `PiEstimator` class to get you started:

```java
/**
  * Description: <describe your program here>
  *
  * @author <your name here>
  */

public class PiEstimator {
    // add any desired fields here

    // constructor taking in the number of sample points, numPoints, 
    // and the number of threads used to compute the estimate
    public PiEstimator (long numPoints, int numThreads) {
	    ...
    }

    // compute the estimate of pi (improve this description!)
    public double getPiEstimate () {
	    ...
    }
}

```

In order to make your program multithreaded, you will need to write a separate class that implements the `Runnable` interface. For example, you might define a class `PiThread` as follows in a separate file.

```java
public class PiThread implements Runnable {
    ...
}
```

Each instance of `PiThread` can then perform a prescribed number of samples, and record the number of samples falling within a disk in an appropriate location of a shared array. See the notes on [Multithreading](/teaching/2021s-cosc-273/notes/multithreading) for an example of how to define and use threads that modify a shared array.

##### Testing Your Program

To test your program, [download and run `PiTester.java`](/assets/java/2021s-cosc-273/lab01-estimating-pi/PiTester.java), reproduced below.

```java
public class PiTester {
    // powers of two that are close to powers of 10
    public static final long THOUSAND = 1_024;
    public static final long MILLION = 1_048_576;
    public static final long BILLION = 1_073_741_824;

    
    // array of thread counts to test performance
    public static final int[] THREAD_COUNTS = {1, 2, 4, 8, 16, 32, 64, 128, 256};

    // the total number of samples to be collected
    public static final long NUM_POINTS = BILLION;
    
    public static void main (String[] args) {
	System.out.println("Running Monte Carlo simulation with n = " + NUM_POINTS + " samples...\n");
	System.out.println( "n threads | pi estimate | time (ms)\n"
			   +"-----------------------------------");

	// start and end times of computation
	long start, end;

	// test
	for (int n : THREAD_COUNTS) {
	    
	    PiEstimator pe = new PiEstimator(NUM_POINTS, n);
	    
	    start = System.nanoTime();
	    
	    double est = pe.getPiEstimate();
	    
	    end = System.nanoTime();

	    System.out.printf("%9d |     %.5f | %6d\n", n, est, (end - start) / 1_000_000);    
	}

	System.out.println("-----------------------------------");
    }	
}

```

When I run the `PiTester` program on my computer, I get the following output (which takes a while to produce):

```text
% java PiTester
Running Monte Carlo simulation with n = 1073741824 samples...

n threads | pi estimate | time (ms)
-----------------------------------
        1 |     3.14158 |   8174
        2 |     3.14161 |   4690
        4 |     3.14161 |   2709
        8 |     3.14163 |   1735
       16 |     3.14156 |   1867
       32 |     3.14167 |   1938
       64 |     3.14156 |   1905
      128 |     3.14157 |   1907
      256 |     3.14164 |   1919
-----------------------------------
```

Note that the third column indicates the time needed to run approximately 1 billion samples.

#### Hints and Resources

In performing the Monte Carlo simulation, each thread must compute many random samples. In single-threaded programs, you would typically generate (pseudo)random numbers using `java.util.Random`, and using a method such as `nextDouble()`. Unfortunately, `java.util.Random` does not play well with multithreaded programs. Instead, your program should use a "thread local" random number generator so that each thread has its own independent stream of random numbers. Thankfully, Java has a built-in thread local generator, `ThreadLocalRandom` (see [documentation here](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/ThreadLocalRandom.html)). To use `ThreadLocalRandom`, you need to include it at the beginning of your program with

```java
import java.util.concurrent.ThreadLocalRandom;
```

Then to get, for example, a random `double`, you can use

```java
ThreadLocalRandom.current().nextDouble()
```

Note that `current()` is a static method that returns a `ThreadLocalRandom` object specific to the current thread.

#### What to Turn In

Please submit your program files to the Moodle submission site by **Friday, February 26, 23:59 [AOE](https://time.is/Anywhere_on_Earth)**. Be sure to submit everything your program needs to run: at minimum `PiEstimator.java` and `PiThread.java` (assuming you've put the `PiThread` class in a separate file).

Also, please complete [this survey](https://forms.gle/PsD2B9ZLL6SCUB6aA) to tell me about your experience with this lab.

#### Grading

Your programs will be graded on a 5 point scale according to the following criteria:

- **Correctness** (2 pts). Your program compiles and completes the task as specified in the program description above.

- **Style** (1 pt). Code is reasonably well-organized and readable (to a human). Variables, methods, and classes  have sensible, descriptive names. Code is well-commented.

- **Performance** (2 pts). Code performance is comparable to the instructor's implementation. Program shows a significant improvement in performance with multiple threads.

#### Extensions

As mentioned in the notes on [Monte Carlo Simulation](/teaching/2021s-cosc-273/notes/monte-carlo-method), there are many more applications of Monte Carlo simulation. For a relatively simple application, [this paper](https://doi.org/10.2307/2685243) describes a method for estimating Euler's number, $$e = 2.718\ldots$$. (If you are off campus, you can access the full text by using the Amherst College proxy, or by searching for the article through the library website.)




