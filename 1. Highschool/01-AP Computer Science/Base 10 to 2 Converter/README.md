# Base 10 to 2 Converter

> **README generated:** 2026-02-22. This program was written in 1998-1999 as part of an AP Computer Science high school course.

## What It Does

Converts decimal (base 10) integers to their binary (base 2) representation. The user can enter multiple numbers in a single session. Results are printed to the console and also written to a data file (`binconvert.dat`). Supports numbers up to 32 bits.

## Tech Stack

- **Language:** C++
- **Compiler artifacts present:** `.obj`, `.exe` (Borland/Turbo C++ era toolchain)

## Building & Running

The precompiled `.exe` is a 16-bit DOS executable and will not run natively on 64-bit Windows. Use **DOSBox** to run it:

1. Install [DOSBox](https://www.dosbox.com/)
2. Mount the project folder and run the executable:
   ```
   mount c c:\path\to\Base 10 to 2 Converter
   c:
   binary_convert.exe
   ```

To recompile with a modern toolchain (MinGW g++):
```bash
g++ -o binary_convert.exe binary_convert.cpp
```
No external dependencies — this should compile cleanly with a modern compiler.

## Usage

1. Run the program
2. Enter a decimal (base 10) integer when prompted
3. The binary equivalent is displayed on screen and appended to `binconvert.dat`
4. Choose to convert another number or exit

## What Was Done Well

- Writes output to both the console and a file, giving the program persistence beyond a single run
- Handles the session loop cleanly, allowing multiple conversions without restarting
- Correctly uses modulo and integer division to implement the binary conversion algorithm

## What Could Be Improved

- No input validation — entering a negative number or non-integer is not handled
- The 32-bit maximum is enforced by a constant but the user is not warned about it at runtime
- Output file is always overwritten rather than appended, so previous results are lost
