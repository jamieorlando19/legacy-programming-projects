# CISC 370 — Object-Oriented Programming, Java and the World Wide Web

> **README generated:** 2026-02-22. These projects were completed in 2001-2002 as part of CISC 370 at the University of Delaware.

## Course Overview

Covers object-oriented programming principles — inheritance, polymorphism, interfaces, exceptions — using Java, with an emphasis on networked and web-connected applications. Projects progress from basic OOP to GUI development, networking, and a full multiplayer game.

---

## Assignment 1 — Introductory Exercises (`1.java`, `Problem1.java`, `Problem2.java`)

Basic Java warmup problems covering syntax, control flow, and simple class design.

---

## Assignment 2 — Employee Class Hierarchy

### What It Does
Models a corporate employee structure using inheritance. A base `Employee` class holds name, salary, and hire date. Subclasses add role-specific behavior:

| Class | Role |
|-------|------|
| `Employee` | Base class: name, salary, hire date |
| `Manager` | Manages a team |
| `Engineer` | Technical role |
| `SrEngineer` | Senior technical role (extends Engineer) |
| `Executive` | Executive-level employee |
| `Secretary` | Administrative role |

`ManagerTest.java` / `Bench.java` instantiate and exercise the hierarchy.

### Tech Stack
- **Language:** Java
- **Concepts:** Inheritance, polymorphism, `toString()` overrides

### Building & Running
```bash
javac *.java
java ManagerTest
```

### What Was Done Well
- Clean multi-level hierarchy (`Engineer` → `SrEngineer`) demonstrates realistic subclassing
- Polymorphism allows treating all employees uniformly through the base type

### What Could Be Improved
- Salary data is likely not encapsulated with access control (public fields vs. private + getters)
- No interface abstraction (e.g., `Payable`) that would make payroll processing more flexible

---

## Assignment 3 — Generic Sorter with Custom Exception

### What It Does
A generic sorting utility (`Assignment3.sort()`) that sorts any array of `Comparable` objects using selection sort. Throws a custom `ObjectIsNotComparableException` if a non-comparable object is passed.

### Tech Stack
- **Language:** Java
- **Concepts:** Interfaces (`Comparable`), custom exceptions, generics (pre-Java 5 style)

### Building & Running
```bash
javac *.java
java test
```

### What Was Done Well
- Demonstrates interface-based polymorphism — the sorter works on any type that implements `Comparable`
- Custom checked exception gives callers meaningful error information

### What Could Be Improved
- Selection sort is O(n²) — fine for an assignment, but `Arrays.sort()` would be preferred in practice
- Pre-generics Java means the array parameter is likely `Object[]`, losing type safety

---

## Assignment 4 — American Flag Graphics

### What It Does
Draws a proportionally accurate American flag using Java2D. 13 alternating red and white stripes, a blue canton with 50 stars arranged in the correct staggered pattern.

### Tech Stack
- **Language:** Java
- **Library:** Java2D (`Graphics2D`, `Polygon`)
- **GUI:** `JFrame` (Swing)

### Building & Running
```bash
javac *.java
java FlagTest
```

### What Was Done Well
- Uses `Graphics2D` and coordinate math to draw a correctly proportioned flag
- Stars are placed in a staggered 6×5 / 5×4 grid pattern matching the actual flag layout

### What Could Be Improved
- Window is likely a fixed size — no responsive scaling when the window is resized
- Could be refactored to use `paintComponent()` on a `JPanel` rather than painting directly on a frame

---

## Assignment 5 — File Transfer Client/Server (`FileClient.java`, `FileServer.java`)

### What It Does
A simple socket-based file transfer system. The server listens for connections; the client connects and sends or receives a file over the socket using buffered I/O streams.

### Tech Stack
- **Language:** Java
- **Networking:** `Socket`, `ServerSocket`
- **I/O:** `BufferedReader`, `PrintWriter`

### Building & Running
```bash
javac FileClient.java FileServer.java

# Terminal 1:
java FileServer

# Terminal 2:
java FileClient
```

### What Was Done Well
- Demonstrates bidirectional socket I/O with proper stream wrapping
- Clean separation of client and server responsibilities

### What Could Be Improved
- Likely transfers text only (via `PrintWriter`) rather than arbitrary binary files
- No error handling if the connection drops mid-transfer

---

## Assignment 6 — Hash Server

A client/server application that exposes a shared hash map over a network connection. Clients can store and retrieve key-value pairs from the server.

---

## Capstone — Scrabble (`Scrabble.java`, `Board.java`, `Rack.java`, `Letter.java`, etc.)

### What It Does
A fully playable networked Scrabble game with a Swing GUI.

| Class | Role |
|-------|------|
| `Board.java` | 15×15 game board with double/triple word and letter squares |
| `Letter.java` | Individual tile with letter and point value |
| `Rack.java` | Player's 7-tile rack |
| `LetterList.java` | Collection of tiles (bag/rack management) |
| `ScrabblePanel.java` | Swing GUI component rendering the board |
| `ScrabbleServer.java` | Network server for multiplayer games |
| `Scrabble.java` | Main game controller |

### Tech Stack
- **Language:** Java
- **GUI:** Swing (`JFrame`, `JPanel`, custom painting)
- **Networking:** Sockets for multiplayer
- **Data structures:** 2D array for the board, collections for tile management

### Building & Running
```bash
javac *.java

# Single player or host:
java Scrabble

# For multiplayer, start ScrabbleServer first:
java ScrabbleServer
```

### What Was Done Well
- Full board implementation with all special squares (double/triple word/letter) correctly mapped
- Multiplayer support via sockets is ambitious for a course project
- Separating board, rack, and letter into distinct classes reflects strong OOP design

### What Could Be Improved
- Dictionary validation (checking whether a placed word is real) may not be implemented
- Tile placement rules (must connect to existing words, first word must cross center) may be partially enforced
- No AI opponent — multiplayer only
