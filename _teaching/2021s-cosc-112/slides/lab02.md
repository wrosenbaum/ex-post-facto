---
title: "Lab 02: Graphics + Recursion = Fractals"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lab 02: Graphics + Recursion = Fractals

## Outline

1. Announcements
2. Lab 02 Suggestions
3. Small group work

## Announcements

1. Accountability group survey
2. First quiz this week
    + topic will be recursion
	+ conceptual question
	+ related to Wednesday lecture material

# Lab 02 Suggestions

## Lab 02 Overview

![](/assets/img/2021s-cosc-112/lab02-recursive-graphics/output.png){: width="50%"}

[Instructions here](/teaching/2021s-cosc-112/labs/02-recursive-graphics/)

## A Closer Look

![](/assets/img/2021s-cosc-112/lab02-recursive-graphics/output-cropped.png){: width="50%"}

## How Was This Generated?

<div style="margin-bottom: 12em"></div>

## Notes on Overlap

- Drawing commands executed in order
- New drawings *on top* of old:

```java
drawSquareA();
drawSquareB();
```

Code above will show square B in foreground (if squares A and B overlap)

## Difference Between Images

![](/assets/img/2021s-cosc-112/lab02-recursive-graphics/output-overlap.png){: width="100%"}

## Recursion Depth

Draw picture to depth 0, 1, 2

<div style="margin-bottom: 12em"></div>

## Look at the Code

- Keep track of current depth
- Compiling with command line
- What happens with infinite loop?





