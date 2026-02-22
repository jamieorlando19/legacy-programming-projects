# Polynomial

> **README generated:** 2026-02-22. This program was written in 1998-1999 as part of an AP Computer Science high school course.

## What It Does

Performs arithmetic on polynomials. The user enters two or more polynomials (each as a list of coefficient/degree pairs) and chooses to add, subtract, or multiply them. The result is displayed in standard polynomial notation (e.g., `3x^2 + 2x + 5`). Like terms are combined automatically and terms are kept sorted by degree from highest to lowest.

## Tech Stack

- **Language:** C++
- **Data structures:** A custom templated linked list class (`llist.h` / `llist.cpp`) used as the underlying container; a vector of linked lists holds multiple polynomials
- **Compiler artifacts present:** `.obj`, `.exe` (Borland/Turbo C++ era toolchain)

## Building & Running

The precompiled `.exe` is a 16-bit DOS executable and will not run natively on 64-bit Windows. Use **DOSBox** to run it:

1. Install [DOSBox](https://www.dosbox.com/)
2. Mount the project folder and run the executable:
   ```
   mount c c:\path\to\Polynomial
   c:
   polynomial.exe
   ```

To recompile with a modern toolchain (MinGW g++):
```bash
g++ -o polynomial.exe polynomial.cpp llist.cpp
```
No external dependencies — standard C++ only. Should compile cleanly with a modern compiler (minor warnings about older-style casts may appear).

## Usage

1. Run the program
2. Enter the number of polynomials to work with (minimum 2)
3. For each polynomial, enter its terms one at a time as `coefficient degree` pairs, e.g.:
   ```
   3 2    ← means 3x²
   2 1    ← means 2x
   5 0    ← means 5 (constant)
   ```
   Enter a sentinel value (as prompted) to finish each polynomial
4. Choose an operation: **Add**, **Subtract**, or **Multiply**
5. The result is displayed in polynomial notation, e.g.: `3x^2 + 2x + 5`

## What Was Done Well

- Implements a full generic linked list template from scratch with navigation, insertion, removal, and modification operations — substantial work for a high school project
- Subtraction is elegantly handled by negating all terms of the second polynomial before adding
- Terms are maintained in sorted order by degree, keeping output clean and mathematically correct

## What Could Be Improved

- Multiplication is O(n²) with no optimization — acceptable here but worth noting for large polynomials
- No support for polynomial division or evaluation at a specific x value
- The custom linked list reinvents what `std::list` already provided, though writing it from scratch was likely the pedagogical point
