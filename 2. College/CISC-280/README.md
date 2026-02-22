# CISC 280 — Program Development Techniques

> **README generated:** 2026-02-22. These assignments were completed in 2000-2001 as part of CISC 280 at the University of Delaware.

## Course Overview

An introduction to non-imperative programming paradigms using **Scheme** (a Lisp dialect). Covers functional programming, recursion, higher-order functions, and list processing. A significant departure from the C++ used in prior courses.

---

## Homework 2 — Digit Manipulation Functions (`Homework2.scm`)

### What It Does

A set of pure functional utilities for working with the digits of integers:

| Function | Description |
|----------|-------------|
| `units-digit` | Returns the least significant digit of a number |
| `ith-digit` | Returns the digit at position `i` (iterative) |
| `leading-digit` | Returns the first (most significant) digit |
| `occurances` | Counts how many times a given digit appears |
| `digit-sum` | Recursively sums all digits |
| `digital-root` | Reduces a number by summing its digits repeatedly until a single digit remains |
| `fast-expt` | Fast exponentiation using recursive squaring |

### What Was Done Well
- `fast-expt` uses the efficient squaring approach (O(log n)) rather than naive repeated multiplication
- `digital-root` elegantly chains `digit-sum` recursively to reach the fixed point
- Clean functional style with no mutation or side effects

### What Could Be Improved
- `ith-digit` uses an iterative approach while most other functions are recursive — inconsistent style
- No input validation for edge cases (negative numbers, zero)

---

## Homework 3–8 (`hw3.scm` – `hw8.scm`, `game.scm`)

### What They Do

Progressive assignments building on Scheme fundamentals:

- **HW3–HW5** — Recursive and iterative list processing, higher-order functions, accumulator patterns
- **HW6** — Extended functional programming exercises
- **HW7** — Game logic implementation (`game.scm`) — a playable game built entirely in Scheme using recursive state management
- **HW8** — Advanced topics (likely continuations or tail recursion optimization)

### What Was Done Well
- The progression from simple digit functions to a full game demonstrates growing fluency in functional thinking
- Using recursion for state management in `game.scm` rather than mutable variables is idiomatic Scheme

### What Could Be Improved
- Functional Scheme programs can be hard to trace/debug; no test harnesses are present
- Some assignments include `.bak` backup files, suggesting iteration happened outside version control

---

## Tech Stack (All Assignments)

- **Language:** Scheme (R5RS standard)
- **Paradigm:** Functional programming — recursion, higher-order functions, closures, tail calls

## Building & Running

Any standard Scheme interpreter works. [Racket](https://racket-lang.org/) is the most accessible modern option:

```bash
# Install Racket, then:
racket Homework2.scm
racket hw3.scm
# etc.
```

Or with MIT Scheme:
```bash
mit-scheme --load Homework2.scm
```

For `game.scm`, load and then call the entry-point function:
```scheme
(load "game.scm")
(start-game)   ; or whatever the entry function is named
```
