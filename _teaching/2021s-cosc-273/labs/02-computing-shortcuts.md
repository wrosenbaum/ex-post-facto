---
title: "Lab 02: Computing Shortcuts"
description: "exploiting parallelism to find shortcuts in a network"
layout: page
---

--------------------

**DUE:** Friday, February 26, 23:59 [AOE](https://time.is/Anywhere_on_Earth)

--------------------

#### Background

Suppose we have a network consisting of $$n $$ **nodes** and directed **links** or **edges** between each pair of nodes. That is, given nodes $$i $$ and $$j $$, there is an edge $$e = (i, j)$$ from $$i $$ to $$j $$. For example, the following figure illustrates a network with three nodes, and the arrows indicate the directions of the links: $$(i, j)$$ is indicated by an arrow from $$i $$ to $$j $$, while $$(i, j)$$ is indicated by an arrow from $$i $$ to $$j $$.

[insert figure]

Each edge $$(i, j)$$ has an associated **weight**, $$w(i, j)$$ that indicates the cost of moving from node $$i $$ to node $$j $$ along $$(i, j)$$. The weights of the edges are indicated in the figure above. For example, $$w(0, 1) = 2$$. Given the weights of the edges, it might be the case in order to move from $$i $$ to $$j $$, it would be less expensive to travel via an intermediate node $$k $$, i.e. travel from $$i $$ to some $$k $$, then from $$k $$ to $$j $$. Traveling in this way, we incur a cost of $$w(i, k) + w(k, j)$$. For example, in the figure above, traveling from $$0 $$ to $$2 $$ we incur a cost of $$w(0, 2) = 6$$, while traveling from $$0 $$ to $$1 $$ to $$2 $$ has a cost of $$w(0, 1) + w(1, 2) = 2 + 3 = 5$$. In this assignment, you will write a program that, given any network and weights, computes the cheapest route between every pair of nodes along a path of length at most 2 (i.e., with at most a single intermediate node).

##### Network Representation


#### Program Description

#### Testing Your Program

#### Hints and Resources

#### What to Turn In

#### Extensions
