# Movie Line

> **README generated:** 2026-02-22. This program was written in 1998-1999 as part of an AP Computer Science high school course.

## What It Does

Simulates a movie theater ticket line using a queue data structure. The program starts with 5 people already in line (alternating adult/child). The user can process the person at the front (dequeue them and charge the appropriate ticket price), add a new adult or child to the back of the line, or mark the movie as sold out. At the end, a summary shows total revenue collected and how many people remain in line.

- Adult ticket: $4.00
- Child ticket: $2.50

## Tech Stack

- **Language:** C++
- **Libraries:** AP Classes (`apqueue`) — the College Board's AP CS standard library from the late 1990s
- **Compiler artifacts present:** `.obj`, `.exe` (Borland/Turbo C++ era toolchain)

## Building & Running

The precompiled `.exe` is a 16-bit DOS executable and will not run natively on 64-bit Windows. Use **DOSBox** to run it:

1. Install [DOSBox](https://www.dosbox.com/)
2. Mount the project folder and run the executable:
   ```
   mount c c:\path\to\Movie Line
   c:
   movieline.exe
   ```

To recompile with a modern toolchain, the AP Class `apqueue` dependency must be resolved. It can be replaced with `std::queue` from the C++ standard library. Once substituted:
```bash
g++ -o movieline.exe movieline.cpp
```

## Usage

After launching, the initial 5-person line is displayed. A menu is presented each turn:

| Option | Action |
|--------|--------|
| Process next person | Removes the person at the front of the line and charges their ticket price |
| Add adult | Adds an adult to the back of the line |
| Add child | Adds a child to the back of the line |
| Sold out | Marks the movie as sold out and ends ticket sales |

At the end, the program displays total revenue collected and the number of people still in line.

## What Was Done Well

- Cleanly demonstrates queue (FIFO) semantics in a relatable real-world scenario
- Tracks running total revenue across the entire session
- Final report accounts for both money collected and people still waiting

## What Could Be Improved

- The queue stores only a boolean (adult/child) — no name or other customer information
- No validation when adding to the line after sold out is declared
- The initial hardcoded 5-person line reduces the flexibility of the simulation
