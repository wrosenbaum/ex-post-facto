---
title: "Lab 02: Multithreaded Performance and Correctness"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Multithreaded Performance and Correctness

## Today

1. Download, run, and modify code.
2. Discuss what you see.
3. Conjecture about what is going on.
4. Conclusions?

[Download worksheet here](https://docs.google.com/document/d/1D8r9U_WnAMroZ3UbEM2rvOYPfB4o9Kgl4r0dB6BtAP8/edit?usp=sharing) (Amherst College login required)

# Part I: Bad Counter

## For Your Consideration

A simple counter object ([Counter.java](/assets/java/mt-performace-correctness/Counter.java))

```java
public class Counter {
    long count = 0;
    
    public long getCount () { return count; }
    public void increment () { count++; }
    public void reset () { count = 0; }
}
```

What happens when multiple threads access it concurrently?

## Download the Following Program

- [BadCounter.java](/assets/java/mt-performace-correctness/BadCounter.java)
- [Counter.java](/assets/java/mt-performace-correctness/Counter.java)
- [ThreadCount.java](/assets/java/mt-performace-correctness/ThreadCount.java)

# Questions

## Question 1

Compile and run the program with `NUM_THREADS = 1`. How many times is the counter incremented? How long does the program take to run? 

<div style="margin-bottom: 12em"></div>

## Question 2

Now set `NUM_THREADS = 2`. What happens when you run the program? Is the behavior what you'd expect?

<div style="margin-bottom: 12em"></div>

## Question 3

Try a variety of of values for `NUM_THREADS`... 2, 4, 10, 100, 1000. Do you notice a pattern in the outputs? 

<div style="margin-bottom: 12em"></div>

## Question 4

Is this what you'd expect? Can you explain the behavior?

<div style="margin-bottom: 12em"></div>

# Part II: Slow Samples

## Comparing `Random` and `ThreadLocalRandom`

- `java.util.Random` is **thread-safe** meaning that multiple threads can access it concurrently without causing issues with program correctness

- `java.util.concurrent.ThreadSafeRandom` is not thread-safe, but makes a separate random generator for each thread

Compare the behavior of both!

## Download the Following Program

- [RandomTester.java](/assets/java/mt-performace-correctness/RandomTester.java)
- [RandomWorker.java](/assets/java/mt-performace-correctness/RandomWorker.java)

# Questions

## Question 1

With `NUM_THREADS = 1`, how long does your program take to execute?

<div style="margin-bottom: 12em"></div>

## Question 2 

Run the program with various values of `NUM_THREADS` as before. What pattern do you notice?

<div style="margin-bottom: 12em"></div>

## Thread-local Random

Modify `RandomWorker.java` so that it uses a thread local random number generator within the run method instead of the shared `Random` object. That is, replace the line

```java
r.nextInt();
```

with

```java
ThreadLocalRandom.current().nextInt();
```

## Note 

The thread local random number generator creates a separate, independent number generator for each thread, rather than accessing the shared number generator passed to the constructor of the `RandomWorker` class.

## Question 3 

Now run the program with various values of `NUM_THREADS`. What pattern do you see? Is it a similar pattern to before? How might you explain the difference (if any)?

<div style="margin-bottom: 12em"></div>

# Conclusions

## What Did We Discover?

If we're not careful:

- get performance/speed without correctness (`BadCounter`)
- get correctness without performance (`RandomTester`)

Sometimes we can achieve both speed and performance (`RandomTester` with `ThreadLocalRandom`)
