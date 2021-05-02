---
title: "Lecture 15: Thread Local Objects"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lecture 15: Thread Local Objects

## Overview

1. Queue Locking Strategy
2. Thread Objects
3. Implementing CLH Lock

## Last Time

Compared

- TAS lock
- TTAS lock
- Backoff lock

Shortcoming:

- Not starvation-free
- All threads access same variable concurrently

## Today

Implementing a queue-based lock

- Idea:
    + use linked list representation of a queue
	+ each thread controls a node, sees predecessor node
	+ use node to signal status to successor
- New construction:
    + thread local objects
	
## CLH Lock

Each thread has:
- `Node myNode` node "owned" by thread
- `Node myPred` node owned by predecessor thread

Each `Node` has:
- `boolean locked`:
    + `myNode.locked = true` signals I want/have lock
	+ `myNode.locked = false` signals I have released lock

Thread acquires lock when `myPred.locked` is `false`

## CLH Lock Initial State

![](/assets/img/clh-lock/initial.png){: width="100%"}

## Thread 1 Arrives

![](/assets/img/clh-lock/thread1-arrives.png){: width="100%"}

## Thread 1 Acquires Lock

![](/assets/img/clh-lock/thread1-acquires.png){: width="100%"}

## Thread 2 Arrives

![](/assets/img/clh-lock/thread2-arrives.png){: width="100%"}

## Thread 2 Locks

![](/assets/img/clh-lock/thread2-locks.png){: width="100%"}

## Thread 3 Arrives

![](/assets/img/clh-lock/thread3-arrives.png){: width="100%"}

## Thread 3 Locks

![](/assets/img/clh-lock/thread3-locks.png){: width="100%"}

## Thread 1 Unlocks (1)

![](/assets/img/clh-lock/thread1-unlocks-1.png){: width="100%"}

## Thread 1 Unlocks (2)

![](/assets/img/clh-lock/thread1-unlocks-2.png){: width="100%"}

## Thread 2 Unlocks

![](/assets/img/clh-lock/thread2-unlocks.png){: width="100%"}

## Thread 3 Unlocks

![](/assets/img/clh-lock/thread3-unlocks.png){: width="100%"}

## A Subtlety

When a node locks, 2 things happen:

- updates (shared) `tail`
- updates (own) `myPred`

In what order should these updates occur?

![](/assets/img/clh-lock/thread3-arrives.png){: width="50%"}

<div style="margin-bottom: 4em"></div>

## Swapping Atomically

Use `AtomicReference<...>` class

- has `getAndSet` method
    + returns previous value
	+ sets new value
	+ atomic
- use `myPred = tail.getAndSet(myNode)`

## Another Subtlety

Each thread *has own* variables

- `myNode`
- `myPred`

How do we get different variables for each thread???

## Interlude: Thread-local Objects

Java (generic) class: `ThreadLocal<T>`

- Make a `T` for each thread
- Some `ThreadLocal` methods:
    + `T get()` returns current thread's copy of variable
	+ `protected T initialValue()` returns current thread's initial value
	    + you'll want to `@Override` this to initialize
	+ `void set(T value)` set value of current thread's copy
	
## Ugly Syntax

Make a `MyClass` instance for each thread:

```java
public class TLObjects {
    private ThreadLocal<MyClass> myInstance =
        new ThreadLocal<MyClass>() {
            @Override
            protected MyClass initialValue() {
                return new MyClass(...args...);
            }
        };
    
}
```

Uses *anonymous inner class* construction
- syntax of constructor call, followed by `{...}`
- "`...`" implements/overrides methods

<div style="margin-bottom: 4em"></div>

## An Example: `ThreadId`

Make a class that assigns IDs to threads sequentially

Fields:

- `static AtomicInteger` that stores next ID to be assigned
- `static ThreadLocal<Integer>` that stores thread's ID

Methods:

- `static int get()` returns value of thread's ID

Why is everything `static`?

<div style="margin-bottom: 4em"></div>

## `ThreadID` in Code

## Using `ThreadLocal` for `CLHLock`

Fields:

- Shared reference to `tail` node
    + must be `AtomicReference` to `getAndSet` atomically
- Thread local (references to) nodes
    + `myNode`, the `Node` I initially own
	+ `predNode`, initially `null`
	
Methods:

- `lock()`
    + set `myNode.locked = ture`
	+ atomically:
	    + get shared `tail` node
		+ set `tail` to `myNode`
	+ wait until `myPred.locked == false`
- `unlock()`
    + set `myNode.locked = false` (releases lock)
	+ update `myNode` to `myPred`

## Testing `CLHLock`

## Next Time

- Linked Lists




