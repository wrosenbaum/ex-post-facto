---
title: "The Euclidean Algorithm"
description: "implementing one of the earliest algorithms in recorded history"
layout: page
---

Recall that a **rational number** $$q$$ is a number that can be expressed as a ratio of integers $$r = \frac{a}{b}$$, where we assume $$b > 0$$. A single rational number has (infinitely) many representations as a fraction. For example, $$\frac{2}{3} = \frac{4}{6} = \frac{6}{9} = \cdots$$. When writing and manipulating fractions, we typically want to express fractions in **reduced form**. For example, we prefer the fraction $$2/3$$ to the equivalent fraction $$8/12$$, because 2 and 3 don’t share any common factors, while 8 and 12 are both divisible by 4. In this note, we will describe an efficient method of finding the greatest common divisor of two numbers, which we can then employ to reduce fractions in the `Fraction` class we will develop in lecture.


## Greatest common divisors

Given integers $$a$$ and $$d$$, we say that $$d$$ is a **divisor** of $$a$$ if $$a$$ can be written as a product $$a = d \cdot q$$ where $$q$$ is another integer. In this case, we call $$q$$ the **quotient** of  $$a$$ and $$q$$. 

Given two integers $$a$$ and $$b$$, we say that $$d$$ is a **common divisor** of $$a$$ and $$b$$ if $$d$$ is a divisor of both $$a$$ and $$d$$. That is, $$d$$ is a common divisor of $$a$$ and $$b$$ if there are two integers $$q_a$$ and $$q_b$$ such that $$a = d \cdot q_a$$ and $$b = d \cdot q_b$$. The **greatest common divisor** (**GCD**) of $$a$$ and $$b$$ is the largest integer $$d$$ that is a divisor of both $$a$$ and $$b$$. We denote the greatest common divisor of $$a$$ and $$b$$ by $$\mathrm{gcd}(a, b)$$.

**Example.** Consider $$a = 54$$ and $$b = 24$$. Then both $$a$$ and $$b$$ are divisible by 1, 2, 3, and 6, but they share no other divisors in common. Thus, we have $$\mathrm{gcd}(a, b) = 6$$.

**Note.** For any integer $$a$$, we have have $$\mathrm{gcd}(a, 0) = a$$.

## Computing the gcd

In school, I was taught the following method for computing the GCD of two numbers:

1. factor both numbers as a product of prime numbers
2. find all of the common factors (with multiplicities)
3. the GCD is the product of the common factors

Using the example above with $$a = 54$$ and $$b = 24$$, I’d have written

$$18 = 2 * 3 * 3 * 3$$

and

$$24 = 2 * 2 * 2 * 3$$

so the common factors are 2 and 3 with multiplicity 1 (2 only appears once in the factorization of 18, and 3 only appears once in the factorization of 24). Thus we have (correctly) found that $$\mathrm{gcd}(54, 24) = 6$$. 

The problem with this method of computing the GCD is that you first must factor both numbers. Factoring is fine for small numbers, but once we look at larger numbers, factoring becomes inefficient. In fact, it is unknown whether or not there is an efficient algorithm for factoring large numbers (at least for classical, i.e., non-quantum, computers). 

Thankfully, there is an older and more efficient way of computing GCDs: the Euclidean algorithm. To understand the Euclidean algorithm, we must start with long division. Recall that given two integers $$a$$ and $$b$$, long division allows us to find the quotient $$q$$ and remainder $$r$$ upon dividing $$a$$ by $$b$$. That is, we can find $$q$$ and $$r$$ satisfying $$a = q \cdot b + r$$ with $$0 \leq r < b$$.

In Java, we can compute $$q$$ and $$r$$ using the standard arithmetic operations `q = a / b` and `r = a % b`. Now this brings us to an important fact:

**Important Fact.** Suppose $$a = q \cdot b + r$$ with $$0 \leq r < b$$. Then $$\mathrm{gcd}(a, b) = \mathrm{gcd}(b, r)$$.

Why does this fact help us? Well, if we know that $$\mathrm{gcd}(a, b) = \mathrm{gcd}(b, r)$$, then it suffices to compute $$\mathrm{gcd}(b, r)$$. Also, $$r < b$$, so this second GCD involves smaller numbers than the original problem. 

But how do we compute $$\mathrm{gcd}(b, r)$$? By doing the same thing again! We can find $$q_2$$ and $$r_2$$ such that $$b = q_2 \cdot r + r_2$$, and then we have

$$\mathrm{gcd}(r, r_2) = \mathrm{gcd}(b, r) = \mathrm{gcd}(a, b)$$ where $$r_2 < r < b$$.

Continuing this process, the GCD remains the same, but the second number continues to decrease. Since we get a decreasing sequence of non-negative integers, eventually (actually, quite quickly) we get a remainder of 0, so we find $$\mathrm{gcd}(a, b) = \cdots = \mathrm{gcd}(r_n, 0) = r_n$$.

**Example.** Using the example $$a = 54, b = 24$$ from before, we have

$$54 = 2 \cdot 24 + 6 \Rightarrow \mathrm{gcd}(54, 24) = \mathrm{gcd}(24, 6)$$

and

$$24 = 4 \cdot 6 + 0 \Rightarrow \mathrm{gcd}(24, 6) = \mathrm{gcd}(6, 0)$$.

Since $$\mathrm{gcd}(6, 0) = 6$$, we have $$\mathrm{gcd}(54, 24) = 6$$, as we computed before.

**Bigger example.** Let’s compute $$\mathrm{gcd}(270, 192)$$:


1. $$270 = 1 \cdot 192 + 78 \Rightarrow \mathrm{gcd}(270, 192) = \mathrm{gcd}(192, 78)$$
2. $$192 = 2 \cdot 78 + 36 \Rightarrow \mathrm{gcd}(192, 78) = \mathrm{gcd}(78, 36)$$
3. $$78 = 2 \cdot 36 + 6 \Rightarrow \mathrm{gcd}(78, 36) = \mathrm{gcd}(36, 6)$$
4. $$36 = 6 \cdot 6 + 0 \Rightarrow \gcd(36, 6) = \gcd(6, 0) = 6$$

So putting it all together, we get $$\mathrm{gcd}(270, 192) = 6$$.

The process above defines the **Euclidean Algorithm**. A bit more formally, we can write the process recursively as follows:


- $$\mathrm{gcd}(a, b)$$:
    - if $$b = 0$$, return $$a$$
    - otherwise, find $$q$$ and $$r$$ with $$a = q \cdot b + r$$ with $$0 \leq r < b$$
    - return $$\mathrm{gcd}(b, r)$$



## Implementing the Euclidean Algorithm

The recursive description of the Euclidean Algorithm above lends itself to a deceptively simple implementation in Java:


    public int gcd(int a, int b) {
      if (b == 0) {
        return a;
      }
    
      return gcd(b, a % b);
    }

This code is both concise and efficient.

## IYI: Proving the Important Fact

Here, we prove the important fact from before:

**Important Fact.** Suppose $$a = q \cdot b + r$$ with $$0 \leq r < b$$. Then $$\mathrm{gcd}(a, b) = \mathrm{gcd}(b, r)$$.

**Proof.** It suffices to show that $$a$$ and $$b$$ have precisely the same set of common divisors as $$b$$ and $$r$$. To this end, first suppose $$d$$ is a divisor of both $$a$$ and $$b$$. Then we can write $$a = q_a \cdot d$$ and $$b = q_b \cdot d$$ for some integers $$q_a$$ and $$q_b$$. Plugging these expressions for $$a$$ and $$b$$ into $$a = q \cdot b + r$$ gives

$$q_a \cdot d = q \cdot q_b \cdot d + r$$

Solving this expression for $$r$$ and combining like terms gives

$$r = q_a \cdot d - q \cdot q_b \cdot d = (q_a - q \cdot q_b) \cdot d$$.

In particular, this shows that $$d$$ is also a divisor of $$r$$. Therefore, every common divisor of $$a$$ and $$b$$ is a divisor of $$r$$.

Now assume that $$d$$ is a common divisor of $$b$$ and $$r$$. That is, we can write $$b = q_b \cdot d$$ and $$r = q_r \cdot d$$. Plugging these expressions in to $$a = q \cdot b + r$$ gives

$$a = q \cdot q_b \cdot d + q_r \cdot d = (q \cdot q_b + q_r) \cdot d$$.

Therefore, $$d$$ is also a divisor of $$a$$.

We have shown that (1) any common divisor of $$a$$ and $$b$$ is also a divisor of $$r$$, and (2) any common divisor of $$b$$ and $$r$$ is also a divisor of $$a$$. Therefore $$a$$ and $$b$$ have precisely the same common divisors as $$b$$ and $$r$$, hence $$\mathrm{gcd}(a, b) = \mathrm{gcd}(b, r)$$. □
