# CISC 181 — Introduction to Computer Science

> **README generated:** 2026-02-22. These projects were completed in 1999-2000 as part of CISC 181 at the University of Delaware.

## Course Overview

Introductory computer science course covering programming fundamentals, object-oriented design, and basic algorithms. Projects were written primarily in C++ with one Java applet assignment.

---

## Project 1 — Easter Date Calculator (`proj01-easter.cc`)

### What It Does
Calculates Easter Sunday dates for a range of years (1900–2099) using the Computus algorithm. The user enters a start and end year; the program outputs Easter's date for each year in the range.

### Tech Stack
- **Language:** C++

### Building & Running
```bash
g++ -o easter proj01-easter.cc
./easter
```

### Usage
Enter a start year and end year when prompted. The program prints the Easter date (month and day) for each year in the range.

### What Was Done Well
- Implements the full Computus algorithm including adjustment cases for known exception years
- Loops over a range of years rather than requiring the program to be re-run for each one

### What Could Be Improved
- No validation on the year range — reversed start/end or out-of-bounds years aren't caught
- Exception years are uncommented magic numbers

---

## Project 2 — Grade Processor (`proj02-grades.cc`)

### What It Does
Reads student grade data and performs statistical calculations (averages, distributions, etc.).

### Tech Stack
- **Language:** C++

### Building & Running
```bash
g++ -o grades proj02-grades.cc
./grades
```

### What Was Done Well
- Straightforward application of arrays and basic arithmetic for grade processing

### What Could Be Improved
- No persistent storage — results are not written to a file

---

## Project 3 — Duplicate Grade Checker (`proj03-dupgrade.cc`)

### What It Does
Checks a set of grade records for duplicates or discrepancies.

### Tech Stack
- **Language:** C++

### Building & Running
```bash
g++ -o dupgrade proj03-dupgrade.cc
./dupgrade
```

### What Was Done Well
- Addresses a practical data quality problem (detecting duplicated records)

### What Could Be Improved
- Limited to the input format assumed by the program; no flexible file input

---

## Project 4 — Bank Account System (`bankacct.h`, `acctarray.h`)

### What It Does
An object-oriented banking system with two classes:
- **`bankacct`** — Models an individual bank account with an owner name, account number, and balance. Supports deposits, withdrawals, and monthly interest calculations. Overloads `<<` and `>>` operators for easy I/O.
- **`acctarray`** — Manages an array of accounts with sorting (by name or account number), transaction processing, and formatted printing.

### Tech Stack
- **Language:** C++
- **Concepts:** Classes, operator overloading, dynamic arrays, sorting

### Building & Running
These are header files (class definitions); a separate `main.cpp` driver is needed to compile and run. Include the headers and compile with:
```bash
g++ -o bank main.cpp
./bank
```

### What Was Done Well
- Operator overloading for `<<` and `>>` makes account I/O clean and idiomatic
- Sorting by multiple fields (name or account number) shows good flexibility in the design
- Separating account logic (`bankacct`) from collection management (`acctarray`) is solid object-oriented design

### What Could Be Improved
- No persistence — all account data is lost when the program exits
- No protection against overdraft beyond a simple check

---

## Misc — Video Conference Cost Calculator (`vidcalc.java`)

### What It Does
A Java Applet that compares the cost of holding a meeting via videoconference vs. flying attendees in person. Calculates travel expenses (airfare, hotel, meals, ground transport) against the cost of a videoconference setup.

### Tech Stack
- **Language:** Java
- **Type:** Java Applet (browser-embedded UI)

### Building & Running
> **Note:** Java Applets are deprecated and removed in modern JDKs (Java 11+). To run this, use **Java 8** with the `appletviewer` tool:
```bash
javac vidcalc.java
appletviewer vidcalc.html
```
A corresponding `.html` file with an `<applet>` tag is needed to launch it.

### What Was Done Well
- Solves a practical real-world cost comparison problem
- Uses a GUI (Applet) rather than a command-line interface for accessibility

### What Could Be Improved
- Applet technology is now entirely obsolete — a modern equivalent would use a web page or desktop GUI framework
- Cost figures may be hardcoded for the late-1990s pricing era
