---
title: "Lab Week 11: A Disk Game"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lab 06: A Disk Game

## Overview

1. Event listeners
2. Disk game
3. Demo
4. Getting started

## New Technology

Event Listeners 

- `KeyListener` interface allows us to get user keyboard input in real time
    - `void keyPressed(KeyEvent e)`
    - `void keyReleased(KeyEvent e)`
	- `void keyTyped(KeyEvent e)`
- `KeyEvent` gives info about which key was pressed.


## Disk Game

- Move a “ship” around in response to player keyboard input
- Arrow keys control acceleration
- Want ship to reach goals, avoid hazards

## Game Demo

## The Code

- Builds on “Bouncing Disks” lab
- You implement
    - `keyPressed` and `keyReleased` methods (set acceleration of ship)
    - methods that control game logic (when goals/hazards are encountered)

## Extensions

- Improve the game!
