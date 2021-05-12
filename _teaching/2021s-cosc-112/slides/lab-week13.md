---
title: "Lab Week 13: Stable Matchings"
layout: reveal
---
{::options syntax_highlighter="nil" /}

# Lab Week 13: Stable Matchings

## Announcements

- Two short quizzes posted this week
    + one on stacks
	+ one on stable matchings
	+ can be completed any time before end of classes	
- Accountability group survey posted tomorrow

## Today

1. Project Hints and Suggestions
2. Time to Work

## `Agent.java`

Representation of an agent

- preferences stored in `ArrayList<Agent> prefList`
    + first element is most preferred `Agent`, etc
- current match stored as `Agent curMatch`
    + `curMatch = null` indicates agent is not matched
- `curIndex` is index of `curMatch` in `prefList` 
    + initially `curIndex = -1`
	
## `Agent` Instance Methods

- `boolean prefers(Agent a, Agent b)` returns `true` if this agent prefers `a` to `b`
    + all agents prefer being matched with `prefList` agent to not being matched (i.e., `curMatch == null`) to being matching with agent not in `prefList`
    + `null` parameter indicates not being matched

- `void proposal(Agent a)` this agent **receives** proposal from `Agent a`

- `void refusal()` this agent was refused by `curMatch`
    + if `curMatch` is last in `prefList`, unmatch and return
    + otherwise, set `curMatch` to next in `prefList`, and propose

## `SMInstance`

- Stores:
    + `ArrayList<Agent> hospitals`
	+ `ArrayList<Agent> residents`
	
- Matching is *implicit* in `hospitals`/`residents`
    + matching consists of pairs `(r, h)` where:
	    1. `h = r.curMatch`
		2. `r = h.curMatch`
	+ `Matching` a class for dealing with matchings
	    - stores an `ArrayList` of `Pair<Agent>`s
		
## `SMInstance` Methods

- `void computeStableMatching()` use the McVitie & Wilson algorithm to compute a stable matching
    + matching is implicit in state values of `curMatch`
	+ hint: make sure each resident `r` sets `curMatch` before making *first* proposal
	    + which method actually sets a *proposer's* match?
- `Matching getMatching()` return a `Matching` consisting of pairs `(r, h)` where `r = h.curMatch` and `h = r.curMatch`
- `void setMatching(Matching m)`
- `Pair<Agent> getBlockingPair()`
- `boolean isStable()`

## General Advice

- Start with `Agent` class
- Use `AgentTester` to test each method
- No method should require too much code
- Use print statements to help debug!

