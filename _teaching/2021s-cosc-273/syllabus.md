---
layout: page
title: "Course Syllabus"
description: "COSC 273: Parallel and Distributed Computing, Spring 2021"
---

### Coordinates

*This course will be offered entirely remotely.* Zoom meeting information will be listed on the course Moodle page. All times are Eastern time.

- Lectures: 
    + W/F 2:20--3:10 PM 
- Labs:
    + Section 01:  M 2:20--3:10 PM
	+ Section 02:  M 3:50--4:40 PM

### Course Description

Computers are becoming increasingly parallel, with many cores or processors working concurrently to perform a single task. In order to utilize the full power of modern computers, it is essential to write programs that exploit parallelism. Yet utilizing the power of parallelism requires different ways of reasoning about problems, and parallelism introduces subtleties not present in sequential (i.e., non-parallel) programming. This course will introduce you to the art and science of writing parallel programs. We will consider both practical and theoretical aspects of parallel computing, and create software that is many times faster than any sequential program performing the same task. 

### Expected Background

You are expected to be comfortable writing programs in the Java programming language. In particular, we will readily use object oriented design, the class hierarchy (keyword `extends`), interfaces (keyword `implements`), exception handling, and generics (i.e., the main topics covered in COSC 112). You should be familiar with basic data structures---linked lists, stacks, queues, balanced trees---know their supported operations as well as the efficiency of these operations. 

### Text and Topics

**Suggested text:** *The Art of Multiprocessor Programming* by Herlihy, Shavit, Luchangco, and Spear. The course will follow this text fairly closely, though you will not be required to buy a copy (a digital version will be availble from the course reserves on the Moodle site). 

The core topics covered in the course include:

- multithreaded programming in Java,
- mutual exclusion,
- concurrent objects,
- locks and contention resolution,
- blocking synchronization,
- concurrent data structures (linked lists, queues, stacks, hash tables, and sets, skiplists and balanced search)
- scheduling, work distribution, and barriers,
- data parallelism (MapReduce and streams).

As time allows, we may cover additional topics such as the theoretical foundations of shared memory, sorting networks, fault tolerance, message passing models, and distributed graph algorithms.

### Course Structure 

This course will be conducted entirely remotely with synchronous meetings on Zoom. The course content will be delivered through a mixture of synchronous lectures and asynchronous material (notes, videos, and brief exercises). Synchronous meetings will consist of brief lectures and group discussion. In order to facilitate discussion, it is crucial that you prepare for the lecture sessions by reviewing the course materials **before** the lectures---relevant materials will be indicated on the course schedule. 

### Coursework & Evaluation

The coursework for this class will consist of coding/lab assignments, written/theoretical assignments, quizzes, and a final project. Additionally, you will be expected to actively participate in the synchronous lecture sessions, and meet weekly in small groups outside of class. (If you are unable to regularly attend the synchronous sessions, please be in touch with the instructor during the first week of class.)


#### Coding/Lab Assignments (~20%)

Coding/lab assignments will be assigned bi-weekly, for a total of approximately 6 assignments. These programs will be relatively small-scale assignments implementing ideas discussed in lecture. You are encouraged to discuss lab assignments with your peers on a conceptual level, however you should not share code on these assignments. Monday lab sections will be devoted to discussing these assignments.

#### Written/Theoretical Assignments (~20%)

Written assignments will also be assigned bi-weekly. They will cover more theoretical/conceptual material from the class. You are encouraged to collaborate on these assignments. In particular, you may work in small groups (up to 4 students) and submit a single assignment for the group. 

#### Written Quizzes (~20%)

Each week you will have a short quiz, typically related to the conceptual material covered in lectures. The quizzes will be administered through Moodle. The quizzes will have time limits, however you will be able to take the quizzes at your leisure. Please do not discuss the content of the quizzes with your classmates until after the quiz deadline has passed each week.

#### Class Participation and Group Work (~10%)

You will be expected to regularly participate in class discussion during the synchronous lab and lecture periods. Additionally, you will be assigned into accountability groups of 3 to 4 students. You will be expected to meet with your accountability group weekly and submit a brief report of what your group discussed. 

#### Final Project (~30%)

You will have an open-eded group final project for the course. You may work in groups of up to 4, and the topic of the project is entirely up to your group. The only requirement is that your project must employ parallel programming and analyze effect of parallelism on your program's performance. In addition to the program itself, you will submit a project proposal (around mid-way through the term), a proof of concept (a few weeks before the end of the term), and a detailed report that describes your program and indicates how parallelism was employed.

### Collaboration

The coursework will consist of a mixture of group and individual assignments. You are encouraged to discuss and collaborate throughout the course, although some submitted work should be completed individually.

#### Individual Work

**All quizzes and lab assignments must be submitted individually.** For the quizzes, you are encouraged to study/prepare in groups. However, once you begin a quiz, you are expected not to communicate with others (in the class or not) until you submit the quiz. You should not discuss the contents of a quiz with anyone who has not yet completed the quiz.

For the lab assignments, you are encouraged discuss the problems and work together on a conceptual level. However, you are expected to write and submit your own code. In particular, **you may not share your code for lab assignments with fellow students, nor ask others (whether enrolled in the course or not) to write code on your behalf.** 

#### Small Group Work

Written assignments and the final project can be completed by groups of up to 4 students. For the written assignments, a single document should be submitted for the group. The document should indicate the role of each student in preparing the submission, or indicate that all students share equal responsibility for the submission. If the group members feel that they deserve unequal credit for the assignment, this should be indicated on the submission as well.

### Academic Integrity

You are expected to uphold the Amherst College Honor Code [summarized here](https://www.amherst.edu/offices/student-affairs/community-standards/intellectual-responsibility-and-plagiarism/node/657748#:~:text=The%20Amherst%20College%20Honor%20Code,by%20students%2C%20faculty%20and%20staff.) (see the [Amherst College Student Code of Conduct](https://www.amherst.edu/offices/student-affairs/community-standards/student-code-of-conduct) for full details). In particular, you should not:

- share your code for assignments with other students in the class or ask others to share their code with you;
- discuss material appearing on a quiz before you or others have submitted the quiz;
- submit code written by someone else or derivative of someone else's code without crediting the orignal author/source. 

Failure to comply with these terms may result in loss of credit for the assignment or course, as well as possible disciplinary action by the college. 

If you would like to use code that you did not write, be sure to attribute the source of the code in the comments of your code. Attribution is required even if you modify the original code.


### Remark on Remarks

Throughout my written materials, I will use some abbreviations to indicate that you can ignore some of the material: IYI and UA. These stand for "if you're interested" and "unsolicited advice," respectively. Anything following these labels is not "officially" part of the course and can safely be ignored without concern that you skipping something I'll reference later.

IYI. I stole the IYI label from author, thinker, and Amherst alumnus, David Foster Wallace. Among other things, he is known for his extensive use of footnotes, tangents, and parentheticals in his writing. 



