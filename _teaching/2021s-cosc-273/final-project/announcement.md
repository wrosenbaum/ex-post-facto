---
title: "Final Project Announcement"
description: "overview of your final project for Parallel and Distributed Computing"
layout: page
---

## Overview

The final project for COSC 273 *Parallel and Distributed Computing* is your opportunity to apply techniques of parallel programming to a larger-scale problem of your choosing. The topic is entirely open-ended, though your program should involve some computationally intensive component where parallel programming techniques could provide a substantial impact on the program's performance. The primary goal of the project is to understand and document the effect of parallelism on your problem. 

You may work in groups of up to 4 people. 

The emphasis of the project is on testing different implementations and their effects on your program's performance. Thus, the problem you address should be complex enough that there are multiple approaches and/or steps to optimize, but simple enough that you can test multiple versions of your program's components. For example, you might consider:

- partitioning and accessing the program's input in different ways
    + many small tasks versus fewer larger tasks?
	+ does cache locality have a significant effect on performance?
- is there a significant effect of using threads directly compared to the Java executor framework?
- do different multithreaded data structure implementations have a significant impact on performance?
    + which lock implementation gives best performance?
	+ is there a significant effect of using non-blocking data structures compared to blocking?

Program documentation is a central component of your project. You will be expected to rigorously test and document the effects of different implementations of the components of your program. 

## Project Components

The project consists of five main components: (1) a preliminary proposal, (2) a baseline implementation/proof of concept, (3) a short video explaining and demonstrating the project to your peers, (4) the final program itself, and (5) documentation for the final program. 

##### Preliminary Proposal

*Due: Friday, April 9*

Your preliminary proposal is a brief (~2 page) write-up explaining your plan for the project. It should introduce the topic of your project, identify a particular computational task to be performed, and explain why and/or how parallelism might be employed to improve your program's performance. 

The scale of the basic program should not be too large. Specifically, your group should be able to write a baseline implementation (i.e., without any parallel programming) in 2 to 3 weeks. You will then spend a more significant amount of time testing and documenting parallelized versions of your program.

- Here is [a link a proposal outline to get you started](https://docs.google.com/document/d/1POJsmWKC8caKLZXsApJBpsOAoudZhz-QjGgvrUxM35E/edit?usp=sharing). Note that responses to the questions don't need to be long---a few sentences suffices
- You may submit your proposals to [this shared folder](https://drive.google.com/drive/folders/10S8fZQS0c12G6yQTCzYx7CitsNMgAMkv?usp=sharing)

##### Baseline Implementation

*Due: Friday, May 7*

Before optimizing and testing your code, your group should implement a baseline (not parallelized) proof-of-concept program. This program should provide most of the functionality of your final program. In addition to the program itself, you will submit documentation on its usage, and provide some details on how you expect to parallelize/optimize the program.

##### Video

*Due: Wednesday, May 19*

You will submit a short (10-15 minute) video explaining your project. The intended audience is your peers in the class. The video should explain (1) the context and significance of the problem your program solves, (2) the baseline program, and (3) where and how parallelism can be employed to improve performance. You may also wish to address technical challenges you faced and how you overcame them. Your video should be largely conceptual, and you do not need to present final results. 

##### Final Submission

*Due: Friday, May 28*

Your final submission will include the finalized program together with documentation on the program's usage and testing. The documentation should include a comparison of the baseline and optimized performance, and provide a history of the different variations and tests you performed to arrive at the final version. While your aim is to achieve the best performance for your program, the documentation should demonstrate why and how this performance is achieved, even if the improvement over the baseline implementation is modest.

## Grading

The final project is worth 30% of your grade for the course. The different components are weighted as follows:

- 10% Preliminary proposal 
- 20% Baseline implementation
- 20% Video
- 50% Final submission
    + 30% documentation
	+ 20% final program

More detailed rubrics for the individual components will be provided in advance of their due dates.
