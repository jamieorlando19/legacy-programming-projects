# Easter Sunday

> **README generated:** 2026-02-22. This program was written in 1998-1999 as part of an AP Computer Science high school course.

## What It Does

Calculates the date of Easter Sunday for any year between 1900 and 2099. Uses the Computus algorithm — a standard mathematical formula — to determine whether Easter falls in March or April and on which day. Includes hardcoded adjustment cases for the known exception years 1954, 1981, 2049, and 2076.

## Tech Stack

- **Language:** C++
- **Compiler artifacts present:** `.obj`, `.exe` (Borland/Turbo C++ era toolchain)

## Building & Running

The precompiled `.exe` is a 16-bit DOS executable and will not run natively on 64-bit Windows. Use **DOSBox** to run it:

1. Install [DOSBox](https://www.dosbox.com/)
2. Mount the project folder and run the executable:
   ```
   mount c c:\path\to\Easter Sunday
   c:
   easter.exe
   ```

To recompile with a modern toolchain (MinGW g++):
```bash
g++ -o easter.exe easter.cpp
```
No external dependencies — this should compile cleanly with a modern compiler.

## Usage

1. Run the program
2. Enter a year between 1900 and 2099 when prompted
3. The program outputs the month (March or April) and day that Easter falls on for that year

## What Was Done Well

- Implements a real, non-trivial mathematical algorithm correctly
- Properly handles the known edge-case exception years for the Computus formula
- Clean modular arithmetic — the computation steps are clearly separated

## What Could Be Improved

- The valid year range (1900–2099) is not enforced; entering an out-of-range year produces incorrect results without any warning
- No looping — the program calculates one year and exits, requiring a restart for another lookup
- The exception years are magic numbers with no explanation of why those specific years are special
