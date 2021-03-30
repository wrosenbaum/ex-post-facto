---
title: "Project 02: Substitution Cipher"
description: "a program to encrypt and decrypt text"
layout: page
---

## Encryption Schemes

The goal of an **encryption scheme** is to convert a **cleartext**---a text whose contents you wish to remain secret---into a **ciphertext** that obscures the cleartext. Ideally, someone who sees only the ciphertext should not be able to gain any useful information about the contents of the cleartext. Further, the encryption should be reversible so that, given the right (secret) information, the ciphertext can be transformed back into the original cleartext. 

A bit more formally, an encryption scheme consists of two functions:

1. an **encryption method** that transforms a cleartext and a key into a ciphertext, and
2. a **decryption method** that transforms a ciphertext and a key into a (clear)text. 

Here, the **key** is some (secret) information, such as a password, that should not be shared with anyone except those with whom one wants to share the cleartext. Crucially, the encryption and decryption method should have the property that, given the same key, applying the decryption method to the ciphertext gives the original cleartext. If the scheme is to offer any real security, we would like for it to be difficult (or impossible) to find the cleartext from a ciphertext without the key, even if the encryption and decryption methods are known.

## The Caesar Cipher

One of the earliest encryption schemes is attributed to Julius Caesar. The idea of the scheme is to replace each letter in the alphabet by shifting the alphabet some number—$$k $$—letters later. For example, a shift of 1 maps A to B, B to C, and so on, while a shift of 2 maps A to C, B to D, etc. The shift “wraps around” the alphabet, so that, for example, a shift of 1 maps Z to A. To encrypt a message, one simply replaces each letter in the message with its shifted version. Here, the number of places, $$k $$, in the shift is the key to the encryption.

To encrypt the message “HELLO THERE” with a key (shift) of 5, we replace H with the letter 5 later, M, etc, so that we arrive at the ciphertext “MJQQT YMJWJ”. If we know the key, this process can easily be reversed. 

While the string of letters “MJQQT YMJWJ” is not immediately recognizable as an English phrase, the Caesar cipher is easy to crack (given a bit of patience). For example, there are only 26 possible keys/shifts (using a 26 letter alphabet), so one could simply try them all to see if a sensible decryption is produced. In order to make a more robust encryption scheme, we need to allow for more possible keys so that our messages cannot be decrypted by simply trying every key.

## Substitution Ciphers

The Caesar cipher is a special case of a more general encryption scheme known as a **substitution cipher**. Like the Caesar cipher, a substitution cipher forms a ciphertext by replacing each letter in the cleartext with a corresponding letter. In the Caesar cipher, the possible mappings from cleartext letters to ciphertext letters are severely restricted: all of the letters are mapped according to the same cyclic shift of the alphabet. In a general substitution cipher, we make no such restriction. Any one-to-one map from cleartext letters to ciphertext letters is possible. For example, we could form a (partial) **substitution table** as follows:

$$
\begin{align*}
A &\to Q\\
B &\to W\\
C &\to E\\
D &\to R\\
E &\to T\\
F &\to Y\\
&\vdots\\
Z &\to M
\end{align*}
$$

For example, encrypting the word “ACED”, we get the string “QETR”. In this case, the key consists of the substitution table itself. Thus, if the key (table) is known, the substitution process can easily be reversed so that an encrypted message can be decrypted. For example, if we see the ciphertext "QRM", we look up each ciphertext character in the column on the right, and write down its corresponding cleartext character on the left. In this case, we'd get the cleartext "ADZ".

Since the key in the scheme above is the substitution table itself, we can count the number of possible keys as follows. There are 26 possible letters that “A” can map to in a substitution cipher. Once a choice is made for “A”, there are 25 remaining choices for “B” (as “A” and “B” must map to different letters in order for the process to be reversible). Similarly, once values for “A” and and “B” are determined, there are 24 remaining choices for “C”, and so on. Overall, we end up with

$$26! = 26 \times 25 \times 24 \times \cdots \times 2 \times 1 \approx 4 \times 10^{26}$$

possible keys. With this many possible keys, it is no longer feasible to check every key in order to decrypt a message. Thus, a general substitution cipher offers stronger encryption than a Caesar cipher.

##### The Alphabet

The substitution cipher described above substitutes each letter of the 26 letter Roman alphabet with another letter. In your program, however, it will be more convenient (not to mention more secure) to use the entire range of 256 values that can be stored as a `char`. Thus, your program’s substitution table will have 256 entries instead of 26. 


##### The Substitution Table

In the substitution cipher described above, the key for the process is the substitution table itself. Generating and storing/remembering such a key, however, is inconvenient. Instead, it would be nice to have the key be something simpler, such as a password or sequence of numbers. To this end, we can employ a pseudorandom number generator. A **pseudorandom number generator** or **PRNG** is a function that takes an input, known as a **seed** and produces a sequence of numbers that “appears random.” Using such a generator, we can use our key/password as the seed to a PRNG, and then generate a substitution table as above using the “random” sequence generated by the PRNG.

Java’s `Random`  object provides a PRNG that will be helpful in this project. See the [official `Random` documentation here](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Random.html).  You can create a new `Random` object with a seed `long seed` as follows

```java
    Random rand = new Random(123456); // set the random seed to 123456
```

Now, we can generate “random” numbers using, for example,

```java
    rand.nextInt(10); // a "random" integer between 0 and 9
    rand.nextInt(10); // another "random" integer between 0 and 9
    rand.nextInt(10); // another "random" integer between 0 and 9
```

If we use the same seed, every time we run our program, the successive calls to `rand.nextInt(10)` will give the same sequence of digits. Using different seeds will generally result in different sequences of numbers. 

For this project, you will need to implement a method that constructs a substitution table using a `Random` object as PRNG. 

## To Begin

First, download the source code for the project:

- [`SubstitutionCipher.zip`](/assets/java/2021s-cosc-112/project02-substitution-cipher/CaesarCipher.java)

Professor Kaplan was nice enough to donate the code for this project (though it has been modified to use the `ArrayList`  class). I recommend you start early, as there is a lot of code to read through. You will be creating a new class in a new file. You should not change any code in the java files above, except to un-comment line 108 of `Cipher.java` (`cipher = new SubstitutionCipher(key)`) in order to test your substitution cipher. (If you implement another cipher as an extension, you’ll want to add that cipher to if-else block here too.) **You should not make any other changes to the java files above.**

The source code files are organized as follows:

- `Crypt.java`: The class that contains `main()` and its helper methods. This is the class you will run when invoking the program. It reads the input data, uses the requested cipher to perform the requested operation (encryption or decryption), and writes the result.
- `Cipher.java`: A class that defines how a specific cipher must be implemented as a subclass. It holds the secret key value used by any cipher, but the encryption and decryption methods must be overridden by subclasses.
- `CaesarCipher.java`: An example subclass of Cipher. It implements the Caesar cipher. More importantly, it provides a template for the class you must write. 

Additionally, the file `SubstitutionCipher.zip` contains a program `CharToInt.java` that you can use to help debug your program, and a few encrypted messages, `secret-message.txt`, `caesar-01.txt`, `substitution-01.txt`, and `substitution-02.txt`. You should decrypt `secret-message.txt` using the instructions below. The other encrypted messages are left as challenges for you to decrypt---see the [extensions](#extensions).

##### Running from the Command Line

If you run the Crypt program with no arguments, you will see this usage message:

```text
    $ java Crypt
    USAGE: java Crypt <cipher [Caesar|Substitution]>
                      <operation [encrypt|decrypt]
                      <key>
```

The user must choose which cipher (encryption scheme) to use, whether to encrypt or decrypt, and the key value used in performing that operation. However, notice that no file names are expected. So how do you specify a file to encrypt, and where does the encrypted result go?

This program uses the *standard input* and *standard output* for reading and writing, respectively, of the cleartext and ciphertext. That is, the input is read from the console (you could type it), and the output it written to the console (you could see it printed). These channels for input and output are the defaults for any program we run, and are often abbreviated as *stdin* and *stdout*. You likely know them better, in Java, as `System.in` and `System.out`.

We do not really want to type in the input to this program, nor do we merely want to see the output appear in the console window. Luckily, at the command line, we can direct the program to use a file of our choosing as the standard input, and likewise use some file as the standard output. This trick is known as I/O *redirection*, and uses some special symbols on the command line to make it happen. Specifically, the *less-than* (<) symbol is used for *input redirection*, and the *greater-than* (>) symbol for *output redirection*. Using them would look like this:

```text
    $ java Crypt Caesar encrypt 42 < my-original-message.txt
                                   > my-encrypted-message.txt
```

Here, our program would perform a Caesar cipher encryption, using the key value of 42, on the data read from the file, `my-original-message.txt`. The result would then be written into the file, `my-encrypted-message.txt`.

When decrypting, the role of ciphertext and cleartext reverse: 2

```text
    $ java Crypt Caesar decrypt 42 < my-encrypted-message.txt
                                   > my-decrypted-message.txt
```

Notice that the contents of my-decrypted-message.txt should exactly match the contents of my-original-message.txt. You can, in fact, use the diff command to compare the two files automatically:

```text
    $ diff my-original-message.txt my-decrypted-message.txt
```

If the files match exactly, then no output is generated. If there are differences, the diff command will print them.

##### A Note About the Command Line

This project, more than prior projects, utilizes the command line. In particular, is uses I/O redirection (look on the web for “IO redirection linux”). If you typically run your code through an IDE, you’ll want to either figure out how it handles IO redirection, or switch to running the code with the command line (you can still use your IDE to develop, just run it from the command line). The terminals in MacOS and Windows behave the same as Linux (for the purposes of this project).

##### Checking Everything Works

To make sure everything is working properly, I've included a file `secret-message.txt` that was encrypted with a Caesar cipher. The cipher key is the year in which Julius Caesar was assassinated. Please decrypt the message and paste the decrypted text into [**this form**](https://forms.gle/RYCtRPRvhu5iYRFm8) (note that the decrypted text should be a few sensible English sentences).

## To Complete the Project

You need to create and implement a `SubstitutionCipher extends Cipher` class. Implement it as a public class in a file called `SubstitutionCipher.java`. You should use a `Random` object to randomly permute the alphabet of `char` values from 0 to 255. The key should be used to set the `Random` seed, allowing permutations to be recreated. 

Internally, your `SubstitutionCipher` can store the list of characters as an `ArrayList` (as is done in the provided `CaesarCipher.java`). We will discuss `ArrayList`s in more detail in the coming weeks, but you may find [**this tutorial**](https://www.w3schools.com/java/java_arraylist.asp) helpful to get the basics of using `ArrayList`s.

Please submit all `.java` files needed to run your program to the Moodle submission site. The project is due on **Friday, April 9, 11:59 PM, AoE**. When you submit your project, please also fill out the survey linked to from the Moodle submission site.

## Testing Your Solution

While it is impossible to test your substitution cipher with every possible key, I wrote a program that will test 3 key features of your cipher:

1. The substitution table generated by your program is a **permutation** of all 256 `char` values. That is, every `char` appears exactly once as both a cleartext and a ciphertext character in the substitution table.
2. The encryption is **consistent** in the sense that given the same key and a fixed cleartext character `ch`, `ch` is always encrypted as the same ciphertext character.
3. Your program generates a **uniform distribution** of ciphertext encodings of each cleartext character. That is, if we fix a cleartext character `ch` and encrypt it with many different keys, each of the 256 possible encryptions of `ch` is equally likely to occur.

The program below (which I will use to grade your submissions) checks that the three conditions above our satisfied. 

- [`CipherTester.java`](/assets/java/2021s-cosc-112/project02-substitution-cipher/CipherTester.java)

When you create a ciphertext, the may contain any of the 256 possible `char` values. Many of these characters are non-printing characters, so simply opening an enciphered text file will likely just display nonsense in your text editor. To help debugging, here is a tool that will print all of the ASCII codes of the characters in a given file:

- [`CharToInt.java`](/assets/java/2021s-cosc-112/project02-substitution-cipher/CharToInt.java)

After compiling `CharToInt.java` you can run the program with

```text
 $ java CharToInt < some-text-file.txt
```

The result will be to print the ASCII values of all of the characters in `some-text-file.txt` to the terminal. 

## Extensions

1. Implement another encryption scheme! A couple of historically significant schemes include the [Vigenère cipher](https://en.wikipedia.org/wiki/Vigen%C3%A8re_cipher) and the [Enigma machine](https://en.wikipedia.org/wiki/Enigma_machine). You can see some [other encryption schemes at this site](http://practicalcryptography.com/ciphers/).
2. Write a program that automatically deciphers any text enciphered using a Caesar cipher (with a 256 character alphabet). You can use your program to decrypt one of your professor’s favorite short stories:
    - [`caesar-01.txt`](/assets/java/2021s-cosc-112/project02-substitution-cipher/caesar-01.txt)
3. Figure out how to crack a substitution cipher, and write a program that helps you do so! Using your program, decrypt two more of your professor’s favorite short stories:
    - [`substitution-01.txt`](/assets/java/2021s-cosc-112/project02-substitution-cipher/substitution-01.txt)
    - [`substitution-02.txt`](/assets/java/2021s-cosc-112/project02-substitution-cipher/substitution-02.txt)

4. Answer the following questions:
    + By using a `long` to store the key, how many possible substitution tables can we represent? 
    + How many substitutions for our alphabet are there actually?
	
## Grading Criteria

The project will be graded out of 6 points (plus possible extra credit). The first 3 points are based on the usual rubric:


- **3** Everything compiles and runs as specified in this document (i.e., the program correctly encrypts and decrypts a message); code is fairly readable and contains comments briefly describing the main functionality of methods defined/larger chunks of code.
- **2** The program produces more-or-less correct output, but is sloppy/hard to read; comments may be there, but are not helpful.
- **1** Program compiles, but is far from producing the expected output and/or does not run as specified; comments unhelpful or absent.
- **0** Program doesn't compile or outputs garbage; no comments explaining why.

You will additionally receive 1 point per test passed according to the `CipherTester` program below. There are 3 tests, bringing the total number of points up to 6.

- [`CipherTester.java`](/assets/java/2021s-cosc-112/project02-substitution-cipher/CipherTester.java)

Additionally, **extra credit** will be awarded for extensions, as well as early submissions (up to 4 days early) as specified in the course syllabus.

