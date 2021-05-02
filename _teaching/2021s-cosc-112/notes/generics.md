---
title: "Generics"
description: "making containers to store any datatype"
layout: page
---

## `Zoo` as a Linked List

Previously, we defined an `abstract class Animal` and defined a new class `Zoo` to contain our `Animal`s. In order to make the `Zoo` dynamic---i.e., to support operations of adding and removing animals for the `Zoo` efficiently---we represented the `Zoo` as a **linked list** of `Animal`s. Specifically, we defined a `Node` class that stored (1) a reference to an `Animal`, and (2) a reference to the next `Node` in the collection. 

![](/assets/img/linked-list/initial.png){: width="100%"}

Using this representation we were able to store all of the `Animal`s in the `Zoo` without needing to specify or modify the `Zoo`'s capacity in advance. 

You can download the full code here:

- [`Zoo.zip`]()

## Generalizing our List

The linked list structure depicted above can be applied to storing any datatype---not just `Animal`s. In keeping with our philosophy of encapsulation, we would like to write a single linked list impelementation that works for every datatype. This way, we do not need to write duplicate code every time we want to make a list storing a different datatype.

<!-- object implementation -->

## Generic Types

#### Note on Primitives

## Coming Up

- iterating through our code
