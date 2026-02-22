# CISC 361 — Operating Systems

> **README generated:** 2026-02-22. These projects were completed in 2001-2002 as part of CISC 361 at the University of Delaware.

## Course Overview

Operating Systems covers the design and implementation of OS components — process management, scheduling, concurrency, memory management, and file systems. Projects were written in C and run on Unix.

---

## Homework 2 — Pthreads Race Condition Demo (`tally.c`)

### What It Does

Demonstrates concurrency and race conditions using POSIX threads. Two threads simultaneously increment a shared global counter. Without synchronization, the final count is unpredictable due to interleaved reads and writes.

### Tech Stack
- **Language:** C
- **Library:** POSIX Threads (`pthreads`)

### Building & Running

> Requires a Unix/Linux/macOS system or WSL on Windows.

```bash
gcc -o tally tally.c -lpthread
./tally
```

### Usage
Run the program and observe the final counter value. Run it multiple times — the result will differ due to the race condition, illustrating why shared-state concurrency requires synchronization (e.g., mutexes).

### What Was Done Well
- Clearly demonstrates the core concept of a race condition with minimal code
- Uses `sleep()` to increase the likelihood of interleaving, making the problem reliably reproducible

### What Could Be Improved
- The race condition is intentional for demonstration, but there's no version with a mutex fix shown for comparison

---

## Project 1 — Unix Shell (`sh.c`, `main.c`, `get_path.c`)

### What It Does

A functional Unix shell interpreter. Parses user input, resolves commands via the PATH environment variable, and executes them. Supports a set of built-in commands and forks child processes for external programs.

**Built-in commands:**

| Command | Description |
|---------|-------------|
| `cd` | Change directory |
| `pwd` | Print working directory |
| `which` | Find first match of a command in PATH |
| `where` | Find all matches of a command in PATH |
| `list` | List directory contents |
| `pid` | Print current process ID |
| `kill` | Send signal to a process |
| `printenv` | Print environment variables |
| `setenv` | Set an environment variable |
| `prompt` | Change the shell prompt string |
| `exit` | Exit the shell |

### Tech Stack
- **Language:** C
- **System calls:** `fork()`, `execve()`, `chdir()`, `getcwd()`, `access()`
- **Data structures:** Linked list for PATH elements (`pathelement` struct)
- **Parsing:** `strtok()` for tokenizing input

### Building & Running

> Requires Unix/Linux/macOS or WSL.

```bash
gcc -o mysh main.c sh.c get_path.c
./mysh
```

### Usage
Once running, the shell presents a prompt. Type any command:
```
prompt> ls -l
prompt> cd /tmp
prompt> which gcc
prompt> pwd
prompt> pid
prompt> exit
```
External commands (not in the built-in list) are looked up in PATH and executed via `fork()` + `execve()`.

### What Was Done Well
- Full PATH resolution with a linked list — correct handling of multiple PATH directories in order
- `which` and `where` distinguish between "first match" and "all matches" — a real shell behavior
- Clean separation of concerns: `sh.c` handles shell logic, `get_path.c` handles PATH parsing, `main.c` is the entry point

### What Could Be Improved
- No support for pipes (`|`), redirection (`>`, `<`), or background jobs (`&`)
- No command history or line editing (arrow keys, etc.)
- Signal handling (Ctrl+C, Ctrl+Z) may not be fully implemented

---

## Projects 2 & 3 — Enhanced Shell

Iterative improvements on Project 1, adding error handling refinements and a custom `ls_X` directory listing command (`ls_X.c`, `ls_X.h`). The core shell architecture remains the same.
