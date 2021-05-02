---
title: "Project 3: Stable Matchings"
description: "finding optimal allocations for matching markets"
layout: page
---

## Matching Markets and Preferences

Consider the problem of college admissions. The situation is as follows: prospective students pick one or more colleges that they might like to attend, and send in applications. Once the colleges receive all of their applications, they process the applications to decide who to admit. When a prospective student is accepted to more than one college, they should decide which college to attend based upon their own preferences. 

College admissions are an example of what is referred to as a (2-sided)  **matching market** in economics. More generally, in a 2-sided matching market, there are two sets of **agents**—in this case, prospective students and colleges. The goal of the matching market is to match agents between the two sets so as to achieve some common goal. In the case of college admissions, each student would like to attend their most favored school, while each college would like their most favored applicants to attend. How can we assign prospective students to colleges in such a way that both students and colleges are reasonably satisfied? What features should a “good” matching exhibit?

To make these questions more precise, we can model the colleges and prospective students in terms of their **preferences**. That is, assume that each prospective student has their own ranking of the colleges that they apply to, from most to least preferred. Similarly, each college ranks all of its applicants. These rankings are totally subjective—students don’t have to agree on their relative rankings of colleges and colleges don’t need to agree on their relative rankings of students. Given any two colleges to which a student applies, that student should be able to determine which they prefer. Similarly given any two applicants, each college should be able to determine which student it prefers.

There are many real-life matching markets with the same features of college admissions described above. For the remainder of this note, we will focus on another such problem: that of assigning medical residents to hospitals. When a medical student completes medical school, they typically apply to a residency at a hospital to complete their training. As in the case of college admissions, medical residents have preferences about which hospital they would like to do their residency. Similarly, the hospitals have preferences over which residents they’d like to invite. Unlike college admissions, however, there is a centralized authority that matches residents and hospitals: the [National Resident Matching Program](https://www.nrmp.org/) (NRMP). Prospective residents submit their preferences directly to the NRMP, as do the hospitals participating in the match. The NRMP then determines the “best” matching, and residents are assigned to hospitals according to this matching. In this project, you will implement a variant of the algorithm used by the NRMP to match residents to hospitals based on the preferences of all parties involved. For simplicity, we will assume that every hospital will accept only a single resident.

## Blocking Pairs and Stability

The main goal of the NRMP is to achieve *stability.* Informally, stability means that if a resident, Alice, is assigned to hospital $$A$$, but prefers hospital $$B$$, then it should be the case that hospital $$B$$ has a resident that it prefers to Alice. Otherwise, Alice and hospital $$B$$ would have a mutual incentive to abandon their assignments and Alice would complete her residency at hospital $$B$$ instead of her assigned hospital, $$A$$. This situation is formalized by the notion of a “blocking pair:” we say that a resident $$a$$ and hospital $$B$$ form a **blocking pair** if the following conditions hold:

1. resident $$a$$ is not assigned to hospital $$B$$,
2. resident $$a$$ prefers hospital $$B$$ to her assigned hospital, and
3. hospital $$B$$ prefers resident $$a$$ to its assigned resident.

A matching between residents and hospitals is **stable** if there are no blocking pairs. In this way, under a stable matching, no resident and hospital has an incentive to deviate. The **stable matching problem** (a.k.a. stable marriage problem) is the problem of finding a stable matching.

----------

**Example 1**
Consider a matching market with two prospective residents—`a` and `b`—two hospitals `A` and `B`. We express each agent’s preferences as an ordered list, where most preferred matches appear first. For example, we can express `a`'s preferences as `a : A B` to indicate that `a`'s most preferred hospital is `A`, and `a`’s less preferred hospital is `B`. Below, we describe a complete **instance** of the stable matching problem by listing all agents (residents and hospitals), as well as preference lists indicating each agent’s preferences:

```text
    hospitals
    A B
    residents
    a b
    preferences
    a: A B
    b: A B
    A: a b
    B: a b
```

In the instance above, both residents `a` and `b` prefer hospital `A` to `B`, and both hospitals prefer resident `a` to `b`. Suppose, however, that we match resident `a` to hospital `B` and resident `b` to hospital `A`, as indicated below.

```text
    matching 1
    (a, B)
    (b, A)
```

Notice that `a` prefers `A` to their assigned hospital, and the feeling is mutual: hospital `A` prefers `a` to its assigned resident. Thus the pair `(a, A)` is a blocking pair, so that matching above is *not stable.* On the other hand, consider the matching

```text
    matching 2
    (a, A)
    (b, B)
```

In `matching 2`, resident `b` prefers `A` to their assigned hospital (`B`), but the feeling is *not* mutual: hospital `A` prefers its resident (`a`) to `b`. Therefore, the pair `(b, A)` is not a blocking pair. Similarly, hospital `B` prefers resident `a` to `b`, but resident `a` prefers her assigned hospital (`A`) to `B`. Therefore, `(a, B)` is also not a blocking pair. Thus, `matching 2` is stable.

----------

**Exercise 1** 
Consider the following stable matching instance.

```text
    hospitals
    A B
    residents
    a b
    preferences
    a: A B
    b: B A
    A: b a
    B: a b
```

Consider the matchings `matching 1` and `matching 2` listed below:

```text
    matching 1
    (a, A)
    (b, B)
    matching 2
    (a, B)
    (b, A)
```

Which, if any, of the two matchings are stable?


----------

In the examples above, we were able to find stable matchings. However, it should not be obvious at this point that a stable matching will *always* exist. That is, it could be the case that for some choice of resident/hospital preferences there will be blocking pairs no matter how we assign residents to hospitals. Remarkably, in 1962, mathematician/economists [David Gale](https://en.wikipedia.org/wiki/David_Gale) and [Lloyd Shapley](https://en.wikipedia.org/wiki/Lloyd_Shapley) proved that this is never the case: no matter how the residents rank hospitals and vice versa, there is always a stable matching between residents and hospitals. Moreover, Gale and Shapley devised an efficient algorithm for finding a stable matching, assuming that each resident provides a ranked list of hospitals indicating their preferences, and similarly each hospital provides a ranked list of prospective residents.

## A Stable Matching Algorithm 

Here we introduce a variant of Gale and Shapley’s algorithm described by McVitie and Wilson in 1971. At a high level, the algorithm proceeds as follows. Imagine that all of the residents and (representatives of) the hospitals meet in some centralized location to determine the matching assignment. Suppose the residents arrive one-by-one in some (arbitrary) order. When the first resident, say `a`, arrives, they **propose** to their most most preferred hospital—call it hospital `A`. Since hospital `A` has not received any other proposals yet, `A`  does not refuse `a`‘s proposal. Nor does hospital `A` accept `a`'s proposal, because a preferred resident may come along and propose to `A` at a later time. Instead, `a` and `A` are provisionally matched, and the final decision is deferred until later.

When the next resident, say `b`, arrives `b` proposes to their first choice hospital. If it is a hospital other than `A`, say `B`, then `b` and `B` are provisionally matched. If, on the other hand, `A` is also `b`'s first choice, then `A` has to make a decision: does it want to keep its provisional resident, `a`, or reject `a` and acquire `b` as its provisional resident? Specifically, hospital `A` does the following:

- If `A` prefers `a` to `b`, then `A` keeps `a` as its provisional resident and sends `b` a **rejection.**
- If, on the other hand, `A` prefers `b` to `a`, then `A` adopts `b` as its provisional match and sends `a` a rejection.

After the step above, one of the two residents `a` and `b` is rejected. Upon rejection, an agent proposes to their next most favored hospital.

In McVitie and Wilson’s algorithm, the process above is repeated for each resident. That is, whenever a resident arrives, they propose to their most favored hospital. The hospitals respond to proposals as follows:


Whenever a hospital receives a **proposal** it does the following:

1. if the hospital does not have a (provisional) match yet, the proposing resident is provisionally assigned to the hospital
2. if the hospital already has a provisional resident, it chooses between between its provisional resident and the new proposal
    + the preferred resident is provisionally matched with the hospital
    + the other resident is **refused** 

The residents respond to refusals as follows:

Whenever a resident receives a **refusal** from a hospital, they pick the next hospital on their list (immediately following the hospital from which they received a rejection), and send a proposal to that hospital. 

- if a resident is refused from all hospitals, then they stop participating in the matching process and leave without finding a match

Following the rules above, when a new resident arrives, their initial proposal may lead to a long sequence of proposals and refusals. All of these proposals and refusals should be resolved before the next resident arrives and makes their first proposal.

Once all residents have arrived and all proposals and refusals have been processed, the provisional matches are finalized. 


----------

**Example 2.** 
Consider again the matching instance from Example 1 above:

```text
    hospitals
    A B
    residents
    a b
    preferences
    a: A B
    b: A B
    A: a b
    B: a b
```

Applying McVitie and Wilson’s procedure, we get the following result:

```text
    resident a arrives
      a proposes to hospital A
      A provisionally accepts a's proposal
    resident b arrives
      b proposes to hospital A
      A refuses b's proposal
      b proposes to hospital B
      B provisionally accepts b's proposal
    all provisional matches are finalized:
    (a, A), (b, B) is the final stable matching 
```

In this case, we see that algorithm produces the same matching that we identified as stable in Example 1. 

----------

**Example 3.**
Consider the following matching instance with 3 hospitals and residents:

```text
    hospitals
    A B C
    residents
    a b c
    preferences
    a: B A C
    b: B A C
    c: A B C
    A: b a c
    B: c b a
    C: c b a
```

Applying McVitie and Wilson’s algorithm, we get the following execution:

```text
    resident a arrives
      a sends B proposal
      B provisionally accepts a's proposal
    resident b arrives
      b sends B proposal
      B sends a refusal, provisionally accepts b's proposal
      a sends A proposal
      A provisionally accepts a's proposal
    resident c arrives
      c sends A proposal
      A sends c refusal
      c sends B proposal
      B sends b refusal, provisionally accepts c's proposal
      b sends A proposal
      A sends a refusal, provisionally accepts b's proposal
      a sends C proposal
      C provisionally accepts a's proposal
    all provisional matches are finalized:
    (a, C), (b, A), (c, B) is the final stable matching
```

Observe that once again, the produced matching is stable. Resident `a` is matched with their least preferred hospital, `C`, but hospitals `A` and `B` are both assigned residents they prefer to `a`. Similarly, resident `b` prefers hospital `B` to their assigned hospital (`A`), but hospital `B` prefers its resident `c` to `b`. Finally, resident `c` prefers `A` to their assigned hospital (`B`), but `A` prefers its resident `b` to `c`. 


----------

**Exercise 3.** 
Repeat McVitie and Wilson’s procedure for finding a matching for the instance in Example 3, except have the residents arrive in a different order. That is, suppose resident `c` arrives first (making the first proposal), followed by resident `b`, followed by resident `a`. Does your procedure yield the same stable matching, or a different one?


----------

**Exercise 4.**
Consider the following instance:

```text
    hospitals
    A B C
    residents
    a b c
    preferences
    a: A B C
    b: B A C
    c: A B C
    A: b a c
    B: a b c
    C: a b c
``` 

1. Use McVitie and Wilson’s algorithm to find a stable matching.
2. Find a different stable matching.
3. Which of the two stable matchings do the residents prefer? Which do the hospitals prefer?
4. (Bonus challenge!) Suppose all of the agent’s preferences are as above and known to everyone. It is also known that McVitie and Wilson’s algorithm will be used to compute the matching. Can the hospital mis-report (i.e., lie about) their preferences in order to ensure that the hospitals get their preferred stable matching? That is, every hospital should have either the same or preferable resident to the matching you found for part 1, (and at least one hospital should have a strictly preferred resident to its resident from part 1).

----------

## Your Assignment

In this project, you will implement McVitie and Wilson’s algorithm for finding a stable matching given the preference lists of all agents (i.e., hospitals and residents). Specifically your program will be able to do the following:

1. Read preferences from a file formatted as in the examples above and create a corresponding instance of the stable matching problem.
2. Compute and print a stable matching for the instance using McVitie and Wilson’s algorithm.
3. Read a proposed matching from a file and determine if the matching is stable. If the matching is not stable, your program should report a blocking pair.

## Getting Started

Before writing any code, I strongly recommend doing the exercises above by hand to make sure you understand all of the concepts and procedures involved. Once you’re confident with the conceptual material, download the following code to start programming:

- [`StableMatchings.zip`](/assets/java/2021s-cosc-112/StableMatchings.zip)

The program should compile and run (`main` is located in `SMTester.java`).  However, the main functionality still needs to be implemented.

## To Complete the Project

To complete the project, you need to finish the implementations of `Agent.java` and `SMInstance.java`. These files represent, respectively, an `Agent` with preferences (a ranking) over other `Agents`, and a stable matching instance that stores. Specifically, you must write the following methods (whose functionalities are described in the comments in the code provided):

- In `Agent.java`:
    - `boolean prefers (Agent a, Agent b)`
    - `void proposal (Agent a)`
    - `void refusal ()`
- In `SMInstance.java`:
    - `void computeStableMatching ()`
    - `Matching getMatching ()`
    - `void setMatching (Matching matching)`
    - `Pair<Agent> getBlockingPair ()`
    - `boolean isStable ()`

None of the methods above require a lot of code, but the behavior of some methods is subtle.

## Testing Your Solution

To get you started with testing, the included file `SMTester.java` and associated `.txt` files will check that your code works with a few small examples. 

![](https://paper-attachments.dropbox.com/s_DAA7B93DE5FBB0737B86C40F4C38E316C933EF34AF350E35460E84C3B70DB30D_1604958110547_sm-example-01.png)

![](https://paper-attachments.dropbox.com/s_DAA7B93DE5FBB0737B86C40F4C38E316C933EF34AF350E35460E84C3B70DB30D_1604958121637_sm-example-02.png)

![](https://paper-attachments.dropbox.com/s_DAA7B93DE5FBB0737B86C40F4C38E316C933EF34AF350E35460E84C3B70DB30D_1604958139336_sm-example-03.png)


To test individual methods in your solution, please download and run the following programs. You can test one method at a time by commenting out the other test in the `main` method of the two programs.

https://www.dropbox.com/s/djiblrfthuae6vo/DummyAgent.java?dl=0
https://www.dropbox.com/s/pz6a5amw2dvdfyg/AgentTester.java?dl=0
https://www.dropbox.com/s/zq6f6p9va0bywxh/multiple-sm-instance.txt?dl=0

https://www.dropbox.com/s/4xcs7qoam4d6o9v/small-instance.txt?dl=0
https://www.dropbox.com/s/54mcz6xn6z1vgow/SMInstanceTester.java?dl=0

## What To Submit

Please submit everything you need for your program to run to the Moodle submission link by **5:00 pm EST on Friday December 11th**. When you submit, please respond to the usual survey asking about your experience with the project.

## Grading

The project will be graded out of 5 points (plus possible extra credit). The first 3 points are based on the usual rubric:


- **3** Everything compiles and runs as specified in this document (i.e., the program computes stable matchings); code is fairly readable and contains comments briefly describing the main functionality of methods defined/larger chunks of code.
- **2** The program produces more-or-less correct output, but is sloppy/hard to read; comments may be there, but are not helpful.
- **1** Program compiles, but is far from producing the expected output and/or does not run as specified; comments unhelpful or absent.
- **0** Program doesn't compile or outputs garbage; no comments explaining why.

You will additionally receive 2 points for your program passing all of the tests in `AgentTester.java` and `SMInstanceTester.java` java. 

Additionally, you may receive extra credit for submitting your project up to 4 days early and for implementing any of the extensions suggested below. **If you implement an extension, please include a** `**README.txt**` **file with your submission describing the extension.** 

## Extensions

- Modify your program so that it computes more than one stable matching, if another stable matching exists. To this end, the McVitie-Wilson algorithm with the residents proposing to hospitals always computes the best matching for the residents. How might you find the best stable matching for the hospitals (which is necessarily different from the resident-optimal solution when multiple stable matchings exist)?
- Implement McVitie and Wilson’s algorithm that finds *all* stable matchings for a given instance. See McVitie and Wilson’s paper linked to from the course Moodle site for details of the algorithm.
- Make program handle exceptions. Specifically, the program should verify that the preferences read from a file are valid in the following sense: every agent listed in some agent’s preferences is contained in the list of agents at the beginning of the file. Further, the preferences should be symmetric in the sense that if agent `a` appears in `A`’s preference list, then `A` appears in `a`'s preference list as well.
- Make a graphical interface for your program. It should illustrate an instance of the problem graphically and animate proposals/refusals in some way. 
- As mentioned above, McVitie and Wilson’s algorithm always finds the best stable matching for the residents (when the residents propose to hospitals), assuming all preference lists are listed faithfully. However, if the hospitals lie about (i.e., misreport) their true preferences, it could be the case that the algorithm finds a stable matching that is better for the hospitals in the sense that some hospital is matched with a resident they strictly prefer to their original resident, and no hospital is matched with a resident that is strictly worse than their original resident. Find a stable matching instance $$I$$ such that the hospitals can misreport their preferences such that McVitie and Wilson’s algorithm will compute a matching that is still stable for $$I$$, but is better for the hospitals than the stable matching if the hospitals truthfully report their preferences. 
- Suppose each hospital can have two residents. How would you modify McVitie and Wilson’s algorithm to find a stable matching? Implement your variant.
- Give an argument that the matching produced by McVitie and Wilson’s algorithm does not contain any blocking pairs. (Hint. Argue by contradiction. Suppose $$(a, A)$$ is a blocking pair. Since $$a$$ proposed to hospitals from most to least preferred, they must have proposed to $$A$$ and been rejected...)

