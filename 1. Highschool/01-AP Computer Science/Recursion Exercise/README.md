# Recursion Exercise

> **README generated:** 2026-02-22. This program was written in 1998-1999 as part of an AP Computer Science high school course.

## What It Does

A pair of recursive function demonstrations in a single program:

1. **Reverse Digits** — Recursively prints the digits of a number in reverse order and returns the reversed value. Uses a reference parameter to count recursive calls.
2. **Fibonacci** — Recursively calculates the nth term of the Fibonacci sequence. Base case: the 1st and 2nd terms are both 1. Recursive case: `fib(n-1) + fib(n-2)`.

## Tech Stack

- **Language:** C++

## Building & Running

There are no precompiled artifacts for this project. To compile with a modern toolchain (MinGW g++):

```bash
g++ -o recursion.exe reverseandfib.cpp
```

No external dependencies — standard C++ only.

## Usage

1. Run the program
2. Enter an integer when prompted for the **reverse digits** demo — the digits are printed in reverse order and the reversed number is returned
3. Enter a positive integer `n` when prompted for the **Fibonacci** demo — the program outputs the nth Fibonacci number
   - Example: entering `7` outputs `13` (the sequence: 1, 1, 2, 3, 5, 8, **13**)

**Note:** Avoid large inputs for Fibonacci (e.g., above ~35) — the naive recursive algorithm has exponential time complexity and will become very slow.

## What Was Done Well

- Both functions are clean and correct demonstrations of recursion
- The reverse digits function uses a reference parameter counter, showing understanding of pass-by-reference alongside recursion
- The Fibonacci base case is correctly defined for both the 1st and 2nd terms

## What Could Be Improved

- The Fibonacci implementation is the naive recursive approach with exponential time complexity — computing `fib(n)` recalculates the same subproblems repeatedly; memoization or an iterative approach would be far more efficient
- No main program loop — the program demonstrates rather than being interactive
- No input bounds checking; large inputs to either function will cause a stack overflow
