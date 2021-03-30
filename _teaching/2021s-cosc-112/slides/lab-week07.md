---
title: "Lab Week 07: Substitution Cipher"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Project 02: Substitution Cipher

## Outline

1. Substitution Ciphers
2. Project Demo
3. Getting Started

# Substitution Ciphers

## Secrecy

Since ancient times, people have relied on secret storing and transmission of sensitive information:

- Military secrets
    + coordination
	+ battle plans
- Secret diary
- Secure payments (e.g., debit/credit card transactions)
- ...

## Setup

- start with **cleartext** message
    + message that contains sensitive info
- want to generate a **ciphertext**
    + obscures sensitive info in cleartext
	+ cleartext can be recovered from ciphertext
	    - but only if you know how!
    + **key** is secret that allows one to recover cleartext
	    - key might be e.g., password

## Ciphers and Keys

A **cipher** is a method of generating ciphertext from cleartext and vice versa

More formally, a cipher has two methods:

1. `encrypt : (cleartext, key) -> ciphertext`
2. `decrypt : (ciphertext, key) -> cleartext`

We require

- if cleartext encrypted and decrypted with same key, get the same message back

We hope

- without key, it is hard recover cleartext from ciphertext

## Caesar Cipher

Idea: map each letter to another letter

- use a single (cylic) shift $k$ (key)

Example: a cyclic shift of $k = +3$:

![](/assets/img/substitution-cipher/caesar.png){: width="100%"}

- to encrypt: follow arrow from each cleartext letter
- to decrypt: reverse direction

## Encrypt Example

Encrypt `RETREAT`

![](/assets/img/substitution-cipher/caesar.png){: width="100%"}

<div style="margin-bottom: 8em"></div>

## Decrypt Example

Decrypt `DWWDFN`

![](/assets/img/substitution-cipher/caesar.png){: width="100%"}

<div style="margin-bottom: 8em"></div>

## How Secure Caesar?

<div style="margin-bottom: 12em"></div>

## A More General Cipher

The **substitution cipher**

- choose an *arbitrary* one-to-one map between alphabets
- encryption/decryption the same as with Caesar

## Substitution Example

Encrypt `RETREAT`

![](/assets/img/substitution-cipher/substitution.png){: width="100%"}

<div style="margin-bottom: 8em"></div>

## Project 02

Implement a substitution cipher!

Two differences with previous example:

1. key should be a `long` value
    - create `Random` object with `long key` as seed
    - use `Random` to create substitution table
	- same `key` $\implies$ same table
	- use table to encrypt or decrypt
2. alphabet consists of all 255 possible `char` values
    - not just chars `A` through `Z`
	
## Program Demo

## Today

- Download and run code
- Caesar cipher already implemented
- Use code to decrypt a message
   + [secret-message.txt](/assets/java/2021s-cosc-112/project02-substitution-cipher/secret-message.txt)
   + the key is the year of Julius Caesar's assassination
   + [**paste the decrypted text here**](https://forms.gle/6EX7n15iG5oSY2w87)
