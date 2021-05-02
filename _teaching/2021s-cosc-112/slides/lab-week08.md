---
title: "Lab Week 08"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lab Week 08: Substitution Cipher Notes

## Today

1. Making a Substitution Table
2. `ArrayList`

# Making a Substitution Table

## `Random` and Keys

The `Random` class is a "psuedo-random number generator" (PRNG)

- Given a key, it produces a "random looking" sequence of numbers:

```java
long key = 2685452;
Random r = new Random(key); // use key for Random seed

r.nextInt(10); // a 'random' # from 0 to 9
r.nextInt(10); // another 'random' # from 0 to 9
r.nextInt(10); // another 'random' # from 0 to 9
```

- Given the same key, `Random` objects will produce the same sequence of numbers

<div style="margin-bottom: 8em"></div>

## Generating a Substitution Table 

`key` $\implies$ `new Random(key)` $\implies$ substitution table

What we want:

<div style="margin-bottom: 8em"></div>

## Substitution Table from `Random`?

<div style="margin-bottom: 18em"></div>

## Implementing this Procedure

How do we represent a "deck of cards"?

- Could use an array...

<div style="margin-bottom: 8em"></div>

## Using an `ArrayList`, Part I

- `ArrayList<type>` is a Java 
- `import java.util.ArrayList` to use
- Similar to arrays, but more flexible
- Make an `ArrayList` storing `int`s:

```java
ArrayList<Integer> al = new ArrayList<Integer>();
```

- Add some ints:

```java
al.add(1);
al.add(2);
al.add(3);
```

<div style="margin-bottom: 8em"></div>

## Using an `ArrayList`, Part II


- getting the size of an `ArrayList`

```java
ai.size(); // returns 3
```

- getting elements from an `ArrayList`

```java
ai.get(0); // returns 1
ai.get(1); // returns 2
ai.get(2); // returns 3
```

- removing elements from an `ArrayList`

```java
ai.remove(1); // returns 2, ArrayList now stores [1, 3]
ai.size();    // now returns 2 (new size after removal)
```

<div style="margin-bottom: 6em"></div>








