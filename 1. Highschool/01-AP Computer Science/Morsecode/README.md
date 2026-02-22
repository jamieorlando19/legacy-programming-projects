# Morse Code Decoder

> **README generated:** 2026-02-22. This program was written in 1998-1999 as part of an AP Computer Science high school course.

## What It Does

Decodes Morse code input into English uppercase letters. The user enters dots (`.`) and dashes (`-`), with `/` separating letters and spaces separating words. The program traverses a binary tree — loaded from an external data file (`morsecode.dat`) — where left branches represent dots and right branches represent dashes, arriving at the correct letter at each leaf.

## Tech Stack

- **Language:** C++
- **Data structure:** Binary tree (height 4), built from an external data file
- **Class design:** `morsecode` class with a `convert()` method, split across `morsecode.h` and `morsecode.cpp`
- **Compiler artifacts present:** `.obj`, `.exe` (Borland/Turbo C++ era toolchain)

## Building & Running

The precompiled `.exe` is a 16-bit DOS executable and will not run natively on 64-bit Windows. Use **DOSBox** to run it:

1. Install [DOSBox](https://www.dosbox.com/)
2. Mount the project folder and run the executable:
   ```
   mount c c:\path\to\Morsecode
   c:
   morseconvert.exe
   ```

To recompile with a modern toolchain (MinGW g++):
```bash
g++ -o morseconvert.exe morseconvert.cpp morsecode.cpp
```
No external dependencies beyond the standard library. Ensure `morsecode.dat` is present in the same directory at runtime.

## Usage

1. Run the program; optionally view the directions when prompted
2. Enter Morse code using the following conventions:
   - `.` for dot
   - `-` for dash
   - `/` to separate letters
   - ` ` (space) to separate words
3. Example input: `... --- ...` decodes to `SOS`
4. Example input: `.... . .-.. .-.. ---` decodes to `HELLO`
5. The program outputs the decoded uppercase English text

**Note:** Only uppercase letters A–Z are supported. Digits and punctuation are not handled.

## What Was Done Well

- The binary tree is a natural and efficient data structure for Morse code — each path through the tree is exactly the dot/dash sequence for a letter
- Separating the class into a header and implementation file shows good object-oriented practice for the era
- Loading the tree from an external file keeps the code clean and the data easily modifiable

## What Could Be Improved

- Only decodes Morse code to English — no encoder (English to Morse) is included
- Only supports uppercase letters; digits and punctuation common in Morse code are not handled
- No handling for invalid sequences — an unrecognized dot/dash pattern has undefined behavior
