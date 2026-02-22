# Infix to Postfix Converter

> **README generated:** 2026-02-22. This program was written in 1998-1999 as part of an AP Computer Science high school course.

## What It Does

Takes a standard mathematical expression in infix notation (e.g., `3 + 4 * 2`) and converts it to postfix notation (Reverse Polish Notation, e.g., `3 4 2 * +`), then evaluates the result numerically. Supports the four basic arithmetic operators (`+`, `-`, `*`, `/`) with correct precedence, and handles parentheses for grouping.

## Tech Stack

- **Language:** C++
- **Libraries:** AP Classes (`apstack`) — the College Board's AP CS standard library from the late 1990s
- **Compiler artifacts present:** `.obj`, `.exe` (Borland/Turbo C++ era toolchain)

## Building & Running

The precompiled `.exe` is a 16-bit DOS executable and will not run natively on 64-bit Windows. Use **DOSBox** to run it:

1. Install [DOSBox](https://www.dosbox.com/)
2. Mount the project folder and run the executable:
   ```
   mount c c:\path\to\Infix to Postfix Converter
   c:
   inpost.exe
   ```

To recompile with a modern toolchain, the AP Class `apstack` dependency must be resolved. It can be replaced with `std::stack` from the C++ standard library. Once substituted:
```bash
g++ -o inpost.exe inpost.cpp
```

## Usage

1. Run the program
2. Enter a mathematical expression in infix notation when prompted, e.g.:
   ```
   3 + 4 * 2
   (3 + 4) * 2
   10 / 2 - 3
   ```
3. The program outputs the postfix (RPN) form of the expression and its evaluated numeric result

**Note:** Single-digit operands only — multi-digit numbers are not supported.

## What Was Done Well

- Correctly implements the shunting-yard algorithm using an operator stack
- Handles operator precedence properly — multiplication and division bind tighter than addition and subtraction
- Evaluates the resulting postfix expression using a second stack, completing both phases of the problem

## What Could Be Improved

- Does not handle multi-digit numbers — the parser treats each character individually
- No error handling for malformed expressions (mismatched parentheses, division by zero, consecutive operators)
- No support for unary negation, exponentiation, or other common operators
